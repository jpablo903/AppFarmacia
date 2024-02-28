
package Models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDao {
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean registerCategoryQuery(Categories category){
        String query = "INSERT INTO categories (name, created, updated) VALUES (?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            pst.execute();
            return true;
        }catch(SQLException e){
             e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar la categoria");
            return false;
        }
        
        
    }
    
    public List listCategoriesQuery(String value){
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
            while(rs.next()){
                Categories category = new Categories();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al listar una categoria");
        }
        return list_categories;
    }
    
    public boolean updateCategoriesQuery(Categories category){
        String query = "UPDATE categories SET name = ?, updated = ? WHERE id = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar los datos de la categoria ");
            return false;
        }
    }
    
    public boolean deleteCategoryQuery(int id){
        String query = "DELETE FROM categories WHERE id = " + id;
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No puede eliminar una categoria que tenga relacion con otra tabla.");
            return false;
        }
    }
}
