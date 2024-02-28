package Controllers;

import Models.DynamicCombobox;
import static Models.EmployeesDao.rol_user;
import Models.Products;
import Models.ProductsDao;
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

public class ProductsController implements ActionListener, MouseListener, KeyListener {

    private Products product;
    private ProductsDao productDao;
    private SistemaApp views;

    String rol = rol_user;

    DefaultTableModel model = new DefaultTableModel();

    public ProductsController(Products product, ProductsDao productDao, SistemaApp views) {
        this.product = product;
        this.productDao = productDao;
        this.views = views;
        //Boton registrar producto
        this.views.btn_register_product.addActionListener(this);
        //Boton de modificar producto
        this.views.btn_update_product.addActionListener(this);
        //Boton eliminar producto
        this.views.btn_delete_product.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_product.addActionListener(this);
        this.views.products_table.addMouseListener(this);
        this.views.txt_search_product.addKeyListener(this);
        this.views.jLabelProducts.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_product) {
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.cmd_product_category.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorio");
            } else {
                product.setCode(Integer.parseInt(views.txt_product_code.getText()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                DynamicCombobox category_id = (DynamicCombobox) views.cmd_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());

                if (productDao.registerProductQuery(product)) {
                    //Limpiar tabla
                    cleanTable();
                    cleanFields();
                    //Limpuar clientes
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Producto registrado con exitos");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al producto");
                }

            }
        } else if (e.getSource() == views.btn_update_product) {
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.cmd_product_category.getSelectedItem().toString().equals("")) {
                
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorio");

            } else {
                product.setCode(Integer.parseInt(views.txt_product_code.getText()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                //Obtener el id de la categoria
                DynamicCombobox category_id = (DynamicCombobox) views.cmd_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());
                //Pasar id al metodo
                product.setId(Integer.parseInt(views.txt_product_id.getText()));

                if (productDao.updateProductQuery(product)) {
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Datos del producto modificado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del producto");
                }
            }
        }else if(e.getSource() == views.btn_delete_product){
            int row = views.products_table.getSelectedRow();
            
            if(row == -1){  
                JOptionPane.showMessageDialog(null, "Debes seleccionar un producto para eliminar");        
            }else{
                int id = Integer.parseInt(views.products_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar a este producto?");
                        
                if(question == 0 && productDao.deleteProductQuery(id) != false){
                    cleanTable();
                    cleanFields();
                    views.btn_register_product.setEnabled(true);
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Producto eliminado con exitos");
                }
            }
        }else if(e.getSource() == views.btn_cancel_product){
            cleanFields();
            views.btn_register_product.setEnabled(true);
        }
    }

    public void listAllProducts() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Products> list = productDao.listProductsQuery(views.txt_search_product.getText());
            model = (DefaultTableModel) views.products_table.getModel();
            Object[] row = new Object[7];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCode();
                row[2] = list.get(i).getName();
                row[3] = list.get(i).getDescription();
                row[4] = list.get(i).getUnit_price();
                row[5] = list.get(i).getProduct_quantity();
                row[6] = list.get(i).getCaterory_name();

                model.addRow(row);
            }
            views.products_table.setModel(model);

            if (rol.equals("Auxiliar")) {
                views.btn_register_product.setEnabled(false);
                views.btn_update_product.setEnabled(false);
                views.btn_delete_product.setEnabled(false);
                views.btn_cancel_product.setEnabled(false);
                views.txt_product_code.setEnabled(false);
                views.txt_product_description.setEnabled(false);
                views.txt_product_id.setEditable(false);
                views.txt_product_name.setEditable(false);
                views.txt_product_unit_price.setEditable(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.products_table) {
            int row = views.products_table.rowAtPoint(e.getPoint());

            views.txt_product_id.setText(views.products_table.getValueAt(row, 0).toString());
            product = productDao.searchProduct(Integer.parseInt(views.txt_product_id.getText()));
            views.txt_product_code.setText("" + product.getCode());
            views.txt_product_name.setText(product.getName());
            views.txt_product_description.setText(product.getDescription());
            views.txt_product_unit_price.setText("" + product.getUnit_price());
            views.cmd_product_category.setSelectedItem(new DynamicCombobox(product.getCategory_id(), product.getCaterory_name()));

            //Dehabilitar botones
            views.btn_register_product.setEnabled(false);
        }else if(e.getSource() == views.jLabelProducts){
            views.jTabbedPane1.setSelectedIndex(0);
                //Limpiar tabla
                cleanTable();
                //Limpiar campos
                cleanFields();
                //Listar empleados
                listAllProducts();
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
        if (e.getSource() == views.txt_search_product) {
            cleanTable();
            listAllProducts();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    //Limpiar campos
    public void cleanFields() {
        views.txt_product_id.setText("");
        views.txt_product_code.setText("");
        views.txt_product_name.setText("");
        views.txt_product_description.setText("");
        views.txt_product_unit_price.setText("");      
    }

}
