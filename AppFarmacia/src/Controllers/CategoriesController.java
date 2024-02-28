package Controllers;

import Models.Categories;
import Models.CategoriesDao;
import Models.DynamicCombobox;
import static Models.EmployeesDao.rol_user;
import Views.SistemaApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    private Categories category;
    private CategoriesDao categoryDao;
    private SistemaApp views;

    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories categorie, CategoriesDao categorieDao, SistemaApp views) {
        this.category = categorie;
        this.categoryDao = categorieDao;
        this.views = views;
        //Boton de registrar
        this.views.btn_register_category.addActionListener(this);
        //Boton modificar
        this.views.btn_update_category.addActionListener(this);
        //Boton eliminar categoria
        this.views.btn_delete_category.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_category.addActionListener(this);
        this.views.categories_table.addMouseListener(this);
        this.views.txt_search_category.addKeyListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        getCategoryName();
        AutoCompleteDecorator.decorate(views.cmd_product_category);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_category) {
            if (views.txt_category_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                category.setName(views.txt_category_name.getText().trim());

                if (categoryDao.registerCategoryQuery(category)) {
                    cleanTable();
                    cleanFields();
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria registrada con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar la categoria");
                }
            }
        } else if (e.getSource() == views.btn_update_category) {
            if (views.txt_category_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_category_id.getText().equals("")
                        || views.txt_category_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorio");
                } else {
                    category.setId(Integer.parseInt(views.txt_category_id.getText()));
                    category.setName(views.txt_category_name.getText().trim());

                    if (categoryDao.updateCategoriesQuery(category)) {
                        cleanTable();
                        cleanFields();
                        listAllCategories();
                        JOptionPane.showMessageDialog(null, "Datos de categoria modificados con exitos");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar cambiar la categoria");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_category) {
            int row = views.categories_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes selecionar una categoria para eliminar");
            } else {
                int id = Integer.parseInt(views.categories_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar a esta categoria?");

                if (question == 0 && categoryDao.deleteCategoryQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_category.setEnabled(true);
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria eliminado con exitos");
                }
            }
        }else if(e.getSource()== views.btn_cancel_category){
            cleanFields();
            views.btn_register_category.setEnabled(true);
        }
    }

    public void listAllCategories() {
        if (rol.equals("Administrador")) {
            List<Categories> list = categoryDao.listCategoriesQuery(views.txt_search_category.getText());
            model = (DefaultTableModel) views.categories_table.getModel();
            Object[] row = new Object[2];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                model.addRow(row);
            }
            views.categories_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(e.getPoint());
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
            views.btn_register_category.setEnabled(false);
        }else if(e.getSource()==views.jLabelCategories){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(6);
                cleanTable();
                cleanFields();
                listAllCategories();
            }else{
                views.jTabbedPane1.setEnabledAt(6, false);
                views.jLabelCategories.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador par accerder a esta vista");
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_category) {
            //Limpiar tabla
            cleanTable();
            //Listar categories
            listAllCategories();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
    }
    
    //Metodo para mostrar el metodo de las categoria
    public void getCategoryName(){
        List<Categories> list = categoryDao.listCategoriesQuery(views.txt_search_category.getText());
        for(int i=0; i<list.size(); i++){
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            views.cmd_product_category.addItem(new DynamicCombobox(id, name));
        }
    }

}
