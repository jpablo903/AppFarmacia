
package Views;

import Controllers.LoginController;
import Models.Employees;
import Models.EmployeesDao;

public class LoginApp extends javax.swing.JFrame {
    
    Employees employee = new Employees();
    EmployeesDao employee_dao = new EmployeesDao();

    public LoginApp() {
        initComponents();
        //Controlador del login
        LoginController employee_login = new LoginController(employee, employee_dao, this);
        setSize(930,415);
        setResizable(false);
        setTitle("Ingresar al Sistema");
        setLocationRelativeTo(null);
        this.repaint();
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_iniciarSesion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        txt_contrasenia = new javax.swing.JPasswordField();
        btn_ingresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Iniciar Sesion");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(40, 147, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clima-laboral.jpg"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 420));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 412));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_iniciarSesion.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txt_iniciarSesion.setForeground(new java.awt.Color(255, 255, 255));
        txt_iniciarSesion.setText("Iniciar Sesion");
        jPanel2.add(txt_iniciarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuario:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 67, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Contraseña:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));
        jPanel2.add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 210, 35));
        jPanel2.add(txt_contrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 210, 35));

        btn_ingresar.setText("Ingresar");
        btn_ingresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(btn_ingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 210, 35));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 490, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_ingresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPasswordField txt_contrasenia;
    private javax.swing.JLabel txt_iniciarSesion;
    public javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
