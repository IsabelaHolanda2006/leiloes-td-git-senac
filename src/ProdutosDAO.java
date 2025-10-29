/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    private Connection conn;
    private ResultSet resultset;
    private PreparedStatement prep;
    private ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement("INSERT into produtos (nome, valor, status) VALUES (?, ?, ?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();

            listagem.add(produto);
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Um erro ocorreu no SQL:" + exception.getMessage());
        }
    }

    public void venderProduto(int id) {
        try {
            conn = new conectaDAO().connectDB();
            String queryString = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            prep = conn.prepareStatement(queryString);
            prep.setInt(1, id);
            prep.executeUpdate();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Um erro ocorreu no SQL:" + exception.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos(boolean soldOnly) {
        try {
            conn = new conectaDAO().connectDB();
            String isSoldOnly = soldOnly ? " where status = 'Vendido'" : "";
            String queryString = "SELECT * FROM produtos" + isSoldOnly;
            prep = conn.prepareStatement(queryString);
            resultset = prep.executeQuery();
            listagem.clear();

            while (resultset.next()) {
                int id = resultset.getInt(1);
                String name = resultset.getString(2);
                int value = resultset.getInt(3);
                String status = resultset.getString(4);

                ProdutosDTO product = new ProdutosDTO();

                product.setId(id);
                product.setNome(name);
                product.setValor(value);
                product.setStatus(status);

                listagem.add(product);
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Um erro ocorreu no SQL:" + exception.getMessage());
        }
        return listagem;
    }
}
