
package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import static Models.EmployeesDao.id_user;
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

public class EmployeesController implements ActionListener, MouseListener, KeyListener {
    private Employees employee;
    private EmployeesDao employeeDao;
    private SistemaApp views;
    //Rol
    String rol = rol_user;
    
    DefaultTableModel model = new DefaultTableModel();

    public EmployeesController(Employees employee, EmployeesDao employeeDao, SistemaApp views) {
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.views = views;
        //Boton de registrar empleado
        this.views.btn_register_employee.addActionListener(this);
        //Boton de modificar empleado
        this.views.btn_update_employee.addActionListener(this);
        //Boton de eliminar empleado
        this.views.btn_delete_employee.addActionListener(this);
        //Boton de cancelar
        this.views.btn_cancelar_employee.addActionListener(this);
        //Boton de cambiar contrasenia
        this.views.btn_modify_data.addActionListener(this);
        //Colocar label en escucha
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.employee_table.addMouseListener(this);
        this.views.txt_search_employee.addKeyListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==views.btn_register_employee){
            //Verificar si los campos estan vacios
            if(views.txt_employee_id.getText().equals("") 
                    ||views.txt_employee_fullname.getText().equals("") 
                    ||views.txt_employee_username.getText().equals("")
                    ||views.txt_employee_address.getText().equals("")
                    ||views.txt_employee_telephone.getText().equals("")
                    ||views.txt_employee_email.getText().equals("")
                    ||views.cmd_rol.getSelectedItem().toString().equals("")
                    ||String.valueOf(views.txt_employee_password.getPassword()).equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorio");
            }else{
                //Realizar la insercion
                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setFull_name(views.txt_employee_fullname.getText().trim());
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setRol(views.cmd_rol.getSelectedItem().toString());
                
                if(employeeDao.registerEmployeeQuery(employee)){
                    cleanTable();
                    cleanFields();
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Empleado registrado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al empleado");
                }
            }
            
        }else if(e.getSource()== views.btn_update_employee){
            if(views.txt_employee_id.equals("")){
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            }else{
                //Verificar si los campos estan vacios
                if(views.txt_employee_id.getText().equals("")
                        || views.txt_employee_fullname.getText().equals("")
                        || views.cmd_rol.getSelectedItem().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                }else{
                    employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                    employee.setFull_name(views.txt_employee_fullname.getText().trim());
                    employee.setUsername(views.txt_employee_username.getText().trim());
                    employee.setAddress(views.txt_employee_address.getText().trim());
                    employee.setTelephone(views.txt_employee_telephone.getText().trim());
                    employee.setEmail(views.txt_employee_email.getText().trim());
                    employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                    employee.setRol(views.cmd_rol.getSelectedItem().toString());
                    
                    if(employeeDao.updateEmployeeQuery(employee)){
                        cleanTable();
                        cleanFields();
                        listAllEmployees();
                        views.btn_register_employee.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos del empleado modificados con exitos");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar al empleado");
                    }
                }
            }
        }else if(e.getSource()== views.btn_delete_employee){
            int row = views.employee_table.getSelectedRow();
            
            if(row == -1){  
                JOptionPane.showMessageDialog(null, "Debes seleccionar un empleado para eliminar");        
            }else if(views.employee_table.getValueAt(row, 0).equals(id_user)){
                JOptionPane.showMessageDialog(null, "No puede eliminar al usuario autenticado");
            }else{
                int id = Integer.parseInt(views.employee_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar a este empleado?");
                        
                if(question == 0 && employeeDao.deleteEmployeeQuery(id) != false){
                    cleanTable();
                    cleanFields();
                    views.btn_register_employee.setEnabled(true);
                    views.txt_employee_password.setEnabled(true);
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Empleado eliminado con exitos");
                }
                
            }
        }else if(e.getSource() == views.btn_cancelar_employee){
            cleanFields();
            views.btn_register_employee.setEnabled(true);
            views.txt_employee_password.setEnabled(true);
            views.txt_employee_id.setEnabled(true);
        }else if(e.getSource() == views.btn_modify_data){
            //Recolectar informacion de las cajas de password
            String password = String.valueOf(views.txt_password_modify.getPassword());
            String confirm_password = String.valueOf(views.txt_password_modify_confirm.getPassword());
            //Verificar que los password no estan vacias
            if(!password.equals("") && !confirm_password.equals("")){
                //Verificar que los password son iguales
                if(password.equals(confirm_password)){
                    employee.setPassword(String.valueOf(views.txt_password_modify.getPassword()));
                    //Validar
                    if(employeeDao.updateEmployeePassword(employee) != false){
                        JOptionPane.showMessageDialog(null, "Password modificado con exito!");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la password");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Las password no coinciden");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorio");
            }
        }
    }
    
    //Listar todos los empleados
    public void listAllEmployees(){
        if(rol.equals("Administrador")){
            List<Employees> list = employeeDao.listEmployeeQuery(views.txt_search_employee.getText());
            model = (DefaultTableModel) views.employee_table.getModel();
            Object[] row = new Object[7];
            for(int i=0; i<list.size(); i++){
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                model.addRow(row);
            }
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()== views.employee_table){
            int row = views.employee_table.rowAtPoint(e.getPoint());
            
            views.txt_employee_id.setText(views.employee_table.getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(views.employee_table.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.employee_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.employee_table.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.employee_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.employee_table.getValueAt(row, 5).toString());
            views.cmd_rol.setSelectedItem(views.employee_table.getValueAt(row, 6).toString());
            
            //Deshabilitar
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.btn_register_employee.setEnabled(false);
            
        }else if(e.getSource() == views.jLabelEmployees){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(4);
                //Limpiar tabla
                cleanTable();
                //Limpiar campos
                cleanFields();
                //Listar empleados
                listAllEmployees();
            }else{
                views.jTabbedPane1.setEnabledAt(4, false);
                views.jLabelEmployees.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");
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
        if(e.getSource()==views.txt_search_employee){
            cleanTable();
            listAllEmployees();
        }
    }
    
    //Limpiar campos
    public void cleanFields(){
        views.txt_employee_id.setText("");
        views.txt_employee_id.setEditable(true);
        views.txt_employee_fullname.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        views.txt_employee_password.setText("");
        views.cmd_rol.setSelectedIndex(0);
    }
    
    public void cleanTable(){
        for(int i=0; i<model.getRowCount(); i++){
            model.removeRow(i);
            i=i - 1;
        }
    }
    
}
