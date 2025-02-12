/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.login;

import controllers.LoginController;
import exceptions.CredentialsException;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import model.User;
import placeHolder.PlaceHolder;
import view.main.MainView;

/**
 *
 * @author joanp
 */
public class LoginView extends javax.swing.JInternalFrame {

    private final LoginController controller;
    private final MainView view;

    /**
     * Creates new form LoginView
     *
     * @param view
     */
    public LoginView(MainView view) {
        initComponents();
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        placeHolder();
        controller = new LoginController();
        this.view = view;
        validateSeePassword();

    }

    private void validateSeePassword() {
        if (txtPassword.getText().equals("")) {
            cbxSeePassword.setVisible(false);
        } else {
            cbxSeePassword.setVisible(true);
        }
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private void placeHolder() {
        new PlaceHolder("Ingrese su cédula...", txtUsername);
        new PlaceHolder("**********", txtPassword);
    }

    private boolean hasEmptyFields() {
        return (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty());
    }

    private void login() {
        if (hasEmptyFields()) {
            JOptionPane.showMessageDialog(null, "Ingrese los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        try {
            User user = controller.selectUser(username, password);

            if (!hasEmptyFields() && user != null) {

                view.setLabelUsername(user.getRole());
                view.setUser(user);
                view.validateButtonSettingsUser();
                view.validateButtons();

                switch (user.getRole()) {

                    case ADMIN:
                        view.validateButtonsAdmin();
                        JOptionPane.showMessageDialog(null, "Bienenido admin");
                        this.dispose();
                        break;

                    case ENTRENADOR:
                        view.validateButtonsTrainer();
                        JOptionPane.showMessageDialog(null, "Bienvenido entrenador");
                        this.dispose();
                        break;

                }

            } else {
                JOptionPane.showMessageDialog(null, "Cedula o contraseña incorrecta");
            }
        } catch (CredentialsException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnLogin = new javax.swing.JButton();
        btnLogout = new javax.swing.JLabel();
        cbxSeePassword = new javax.swing.JCheckBox();
        txtPassword = new javax.swing.JPasswordField();

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/key.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user (3).png"))); // NOI18N

        txtUsername.setBackground(new java.awt.Color(0, 0, 0));
        txtUsername.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(255, 255, 255));
        txtUsername.setBorder(null);
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsernameKeyTyped(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(254, 205, 77));
        jSeparator1.setForeground(new java.awt.Color(254, 205, 77));

        jSeparator3.setBackground(new java.awt.Color(254, 205, 77));
        jSeparator3.setForeground(new java.awt.Color(254, 205, 77));

        btnLogin.setBackground(new java.awt.Color(254, 205, 77));
        btnLogin.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(0, 0, 0));
        btnLogin.setText("Iniciar sesión");
        btnLogin.setBorder(null);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        cbxSeePassword.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        cbxSeePassword.setForeground(new java.awt.Color(255, 255, 255));
        cbxSeePassword.setText("Mostrar Contraseña");
        cbxSeePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSeePasswordActionPerformed(evt);
            }
        });

        txtPassword.setBackground(new java.awt.Color(0, 0, 0));
        txtPassword.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setBorder(null);
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxSeePassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(674, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLogout)
                        .addGap(239, 239, 239))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(435, 435, 435))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(129, 129, 129)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxSeePassword)
                .addGap(135, 135, 135)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btnLogout)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        login();

    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked

        if (view.getUser() != null) {
            view.setNullLabelUsername();
            view.setUser(null);
            view.validateButtonSettingsUser();
            view.validateButtons();
            //VALIDAR BOTONES Y DEMAS SIN ROL
            JOptionPane.showMessageDialog(null, "Se cerró sesión correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Debe iniciar sesión primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void cbxSeePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSeePasswordActionPerformed
        if (cbxSeePassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_cbxSeePasswordActionPerformed

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        validateSeePassword();
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void txtUsernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyTyped
        String cedula = txtUsername.getText();
        char c = evt.getKeyChar();

        if (!Character.isDigit(c) || cedula.length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtUsernameKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JCheckBox cbxSeePassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
