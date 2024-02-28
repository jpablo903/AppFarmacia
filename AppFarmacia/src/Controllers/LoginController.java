package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import Views.LoginApp;
import Views.SistemaApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener, KeyListener {

    private Employees employee;
    private EmployeesDao employees_dao;
    private LoginApp login_view;
    
    public LoginController(Employees employee, EmployeesDao employees_dao, LoginApp login_view) {
        this.employee = employee;
        this.employees_dao = employees_dao;
        this.login_view = login_view;
        this.login_view.btn_ingresar.addActionListener(this);
        this.login_view.txt_usuario.addKeyListener(this);
        this.login_view.txt_contrasenia.addKeyListener(this);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Obtener los datos de las vista
        String user = login_view.txt_usuario.getText().trim();
        String pass = String.valueOf(login_view.txt_contrasenia.getPassword());
        
        if (e.getSource() == login_view.btn_ingresar) {
            //Validar que los campos no esten vacios
            if (!user.equals("") && !pass.equals("")) {
                //Pasar los parametros al metodo login
                employee = employees_dao.loginQuery(user, pass);
                //Verificar la exitencia del usuario
                if (employee.getUsername() != null) {
                    if (employee.getRol().equals("Administrador")) {
                        SistemaApp admin = new SistemaApp();
                        admin.setVisible(true);
                    } else {
                        SistemaApp aux = new SistemaApp();
                        aux.setVisible(true);
                    }
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        }

    @Override
    public void keyPressed(KeyEvent e) {
        //Obtener los datos de las vista
        String user = login_view.txt_usuario.getText().trim();
        String pass = String.valueOf(login_view.txt_contrasenia.getPassword());
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //Validar que los campos no esten vacios
            if (!user.equals("") && !pass.equals("")) {
                //Pasar los parametros al metodo login
                employee = employees_dao.loginQuery(user, pass);
                //Verificar la exitencia del usuario
                if (employee.getUsername() != null) {
                    if (employee.getRol().equals("Administrador")) {
                        SistemaApp admin = new SistemaApp();
                        admin.setVisible(true);
                    } else {
                        SistemaApp aux = new SistemaApp();
                        aux.setVisible(true);
                    }
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        }
    
}
