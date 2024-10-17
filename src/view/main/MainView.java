/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.main;

import enums.Role;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Customer;
import model.Product;
import model.ShoppingCart;
import model.Trainer;
import model.User;
import view.ManagmentCustomers.ManagmentCustomers;
import view.ManagmentCustomers.MembershipDetails;
import view.ManagmentProducts.ManagmentProducts;
import view.login.LoginView;
import view.ManagmentCustomers.UpdatedCustomer;
import view.ManagmentCustomers.RegisterCustomer;
import view.ManagmentProducts.EditProduct;
import view.ManagmentProducts.RegisterProduct;
import view.Sales.CartDetails;
import view.Sales.Sales;
import view.Sales.SalesControl;
import view.admin.ManageAccount;
import view.admin.ManagmentAdmin;
import view.trainers.ManagmentTrainer;
import view.trainers.RegisterTrainer;
import view.trainers.UpdateTrainer;
import view.user.ValidationMembership;

/**
 *
 * @author joanp
 */
public class MainView extends javax.swing.JFrame {

    private User user;
    private ValidationMembership viewValidation;
    private Timer timer;

    /**
     * Creates new form mainView
     */
    public MainView() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIcon();
        setTitle("Vista principal");
        setResizable(false);
        hideButtons();
        setNullLabelUsername();
        validateButtonSettingsUser();
        validateButtons();

        timer = new Timer(5000, (ActionEvent e) -> {
            validateDesktop();
            timer.stop();
        }
        );
    }

    public final void validateButtonsAdmin() {
        if (user != null && user.getRole().equals(Role.ADMIN)) {
            btnLogin.setVisible(true);
            btnHome.setVisible(true);
            btnManagment.setVisible(true);
            btnControl.setVisible(true);
            btnValidate.setVisible(true);
            btnCashRegister.setVisible(true);
        }
    }

    public final void validateButtonsTrainer() {
        if (user != null && user.getRole().equals(Role.ENTRENADOR)) {
            btnLogin.setVisible(true);
            btnHome.setVisible(true);
            btnManagment.setVisible(true);
            btnControl.setVisible(false);
            btnValidate.setVisible(true);
            btnCashRegister.setVisible(true);
        }
    }

    public final void validateButtons() {
        if (user == null) {
            btnLogin.setVisible(true);
            btnHome.setVisible(true);
            btnManagment.setVisible(false);
            btnControl.setVisible(false);
            btnValidate.setVisible(false);
            btnCashRegister.setVisible(false);
        } else {
            btnLogin.setVisible(true);
            btnHome.setVisible(true);
            btnManagment.setVisible(true);
            btnControl.setVisible(true);
            btnValidate.setVisible(true);
            btnCashRegister.setVisible(true);
        }
    }

    public final void validateButtonSettingsUser() {
        if (user != null) {
            btnSettings.setVisible(true);
        } else {
            btnSettings.setVisible(false);
        }
    }

    private void openSecondView() {
        if (viewValidation == null) {
            viewValidation = new ValidationMembership(this);
        }
        viewValidation.setVisible(true);
    }

    // se maneja la informacion del usuario loggeado en el sistema.
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getUserLogged() {
        return lblUser.getText();
    }

    public final void setNullLabelUsername() {
        lblUser.setText("");
    }

    public void setLabelUsername(Role role) {
        String roleUser = String.valueOf(role);
        lblUser.setText(roleUser);
    }

    private void setIcon() {
        ImageIcon icono = scaleImage("/icon/logo.png", 200, 150);
        if (icono != null) {
            logo.setIcon(icono);
        } else {
            System.out.println("No se pudo cargar la imagen.");
        }
    }

    private ImageIcon scaleImage(String route, int width, int height) {
        try {
            Image imagen = ImageIO.read(getClass().getResource(route));
            Image imagenEscalada = imagen.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            return null;
        }

    }

    private void hideButtons() {
        btnCustomers.setVisible(false);
        btnProducts.setVisible(false);
        btnTrainers.setVisible(false);
    }

    private void showButtons() {
        btnCustomers.setVisible(true);
        btnProducts.setVisible(true);
        btnTrainers.setVisible(true);
    }

    public void validateDesktop() {
        if (dsMain.getComponentCount() > 0) {
            dsMain.getComponent(0).setVisible(false);
            dsMain.remove(0);
        }
    }

    public void openUpdateTrainerView(ManagmentTrainer view1, Trainer trainer) {
        validateDesktop();
        UpdateTrainer view = new UpdateTrainer(view1, trainer);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openRegisterTrainerView(ManagmentTrainer view1) {
        validateDesktop();
        RegisterTrainer view = new RegisterTrainer(view1);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openCartDetailsView(ManagmentProducts view1, ShoppingCart cart) {
        validateDesktop();
        CartDetails view = new CartDetails(view1, user, cart);
        dsMain.add(view);
        view.setVisible(true);

    }

    public void openEditProductView(ManagmentProducts view1, Product product) {
        validateDesktop();
        EditProduct view = new EditProduct(view1, product);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openMembershipDetailsView(Customer customer, ManagmentCustomers view1) {
        validateDesktop();
        MembershipDetails view = new MembershipDetails(customer, view1, user, this);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openSalesControlView() {
        validateDesktop();
        SalesControl view = new SalesControl();
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openSalesView() {
        validateDesktop();
        Sales view = new Sales(user);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openManageAccountView() {
        validateDesktop();
        ManageAccount view = new ManageAccount(user);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openLoginView() {
        validateDesktop();
        LoginView view = new LoginView(this);
        dsMain.add(view);
        view.setVisible(true);

    }

    public void openRegisterEmployeeView(ManagmentTrainer view1) {
        validateDesktop();
        RegisterTrainer view = new RegisterTrainer(view1);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openRegisterCustomerView(ManagmentCustomers viewC) {
        validateDesktop();
        RegisterCustomer view = new RegisterCustomer(viewC, user, this);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openEditCustomerView(ManagmentCustomers view1, Customer customer) {
        validateDesktop();
        UpdatedCustomer view = new UpdatedCustomer(view1, customer, user, this);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openManagmentCustomersView() {
        validateDesktop();
        ManagmentCustomers view = new ManagmentCustomers(this);
        dsMain.add(view);
        view.setVisible(true);
    }
    
    public void openManagmentTrainersView() {
        validateDesktop();
        ManagmentTrainer view = new ManagmentTrainer(this);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openManagmentProductsView() {
        validateDesktop();
        ManagmentProducts view = new ManagmentProducts(this, user);
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openManagmentAdminView() {
        validateDesktop();
        ManagmentAdmin view = new ManagmentAdmin();
        dsMain.add(view);
        view.setVisible(true);
    }

    public void openRegisterProductView(ManagmentProducts view1) {
        validateDesktop();
        RegisterProduct view = new RegisterProduct(view1);
        dsMain.add(view);
        view.setVisible(true);
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
        logo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        iconUser = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        btnManagment = new javax.swing.JButton();
        btnControl = new javax.swing.JButton();
        btnCashRegister = new javax.swing.JButton();
        btnValidate = new javax.swing.JButton();
        btnSettings = new javax.swing.JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/blackWP.jpeg"));
        Image image = icon.getImage();
        dsMain = new javax.swing.JDesktopPane() {
            public void paintComponent(Graphics g) {
                g.drawImage(image,0,0,getWidth(), getWidth(), this);
            }
        };
        btnCustomers = new javax.swing.JButton();
        btnProducts = new javax.swing.JButton();
        btnTrainers = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(254, 205, 77));

        iconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user (1).png"))); // NOI18N

        lblUser.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        lblUser.setForeground(new java.awt.Color(0, 0, 0));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("ADMIN");

        btnHome.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnHome.setForeground(new java.awt.Color(0, 0, 0));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N
        btnHome.setText("       INICIO      ");
        btnHome.setContentAreaFilled(false);
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(0, 0, 0));
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/login.png"))); // NOI18N
        btnLogin.setText("        LOGIN       ");
        btnLogin.setContentAreaFilled(false);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnManagment.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnManagment.setForeground(new java.awt.Color(0, 0, 0));
        btnManagment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/setting.png"))); // NOI18N
        btnManagment.setText("   GESTIONES  ");
        btnManagment.setContentAreaFilled(false);
        btnManagment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManagment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagmentActionPerformed(evt);
            }
        });

        btnControl.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnControl.setForeground(new java.awt.Color(0, 0, 0));
        btnControl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/security.png"))); // NOI18N
        btnControl.setText("    CONTROL    ");
        btnControl.setContentAreaFilled(false);
        btnControl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnControlActionPerformed(evt);
            }
        });

        btnCashRegister.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnCashRegister.setForeground(new java.awt.Color(0, 0, 0));
        btnCashRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cash-register.png"))); // NOI18N
        btnCashRegister.setText("        CAJA          ");
        btnCashRegister.setContentAreaFilled(false);
        btnCashRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCashRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCashRegisterActionPerformed(evt);
            }
        });

        btnValidate.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnValidate.setForeground(new java.awt.Color(0, 0, 0));
        btnValidate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/check.png"))); // NOI18N
        btnValidate.setText("     VALIDAR     ");
        btnValidate.setContentAreaFilled(false);
        btnValidate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed(evt);
            }
        });

        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/setting.png"))); // NOI18N
        btnSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSettingsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnManagment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(btnControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCashRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnValidate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnSettings))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(iconUser)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnSettings)
                .addGap(18, 18, 18)
                .addComponent(iconUser)
                .addGap(18, 18, 18)
                .addComponent(lblUser)
                .addGap(118, 118, 118)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnManagment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCashRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnValidate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnControl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dsMain.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout dsMainLayout = new javax.swing.GroupLayout(dsMain);
        dsMain.setLayout(dsMainLayout);
        dsMainLayout.setHorizontalGroup(
            dsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1620, Short.MAX_VALUE)
        );
        dsMainLayout.setVerticalGroup(
            dsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 832, Short.MAX_VALUE)
        );

        btnCustomers.setBackground(new java.awt.Color(0, 0, 0));
        btnCustomers.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnCustomers.setForeground(new java.awt.Color(254, 205, 77));
        btnCustomers.setText("Clientes");
        btnCustomers.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 205, 77), 2, true));
        btnCustomers.setContentAreaFilled(false);
        btnCustomers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomersActionPerformed(evt);
            }
        });

        btnProducts.setBackground(new java.awt.Color(0, 0, 0));
        btnProducts.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnProducts.setForeground(new java.awt.Color(254, 205, 77));
        btnProducts.setText("Productos");
        btnProducts.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 205, 77), 2, true));
        btnProducts.setContentAreaFilled(false);
        btnProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductsActionPerformed(evt);
            }
        });

        btnTrainers.setBackground(new java.awt.Color(0, 0, 0));
        btnTrainers.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnTrainers.setForeground(new java.awt.Color(254, 205, 77));
        btnTrainers.setText("Entrenadores");
        btnTrainers.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 205, 77), 2, true));
        btnTrainers.setContentAreaFilled(false);
        btnTrainers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTrainers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrainersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dsMain))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(btnCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(btnProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(btnTrainers, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTrainers, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addComponent(dsMain))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        openLoginView();
        hideButtons();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        validateDesktop();
        hideButtons();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnManagmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagmentActionPerformed
        validateDesktop();
        showButtons();
    }//GEN-LAST:event_btnManagmentActionPerformed

    private void btnCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomersActionPerformed
        openManagmentCustomersView();
    }//GEN-LAST:event_btnCustomersActionPerformed

    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        openManagmentProductsView();
    }//GEN-LAST:event_btnProductsActionPerformed

    private void btnControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnControlActionPerformed
        openSalesControlView();
        hideButtons();
    }//GEN-LAST:event_btnControlActionPerformed

    private void btnCashRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCashRegisterActionPerformed
        openSalesView();
        hideButtons();
    }//GEN-LAST:event_btnCashRegisterActionPerformed

    private void btnValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidateActionPerformed
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        validateDesktop();
        hideButtons();

        if (devices.length <= 1) {
            JOptionPane.showMessageDialog(null, "No hay una segunda pantalla disponible");
        } else {

            openSecondView();
            viewValidation.openInSecondScreen();
            viewValidation.requestFocusAndEdit();
        }
    }//GEN-LAST:event_btnValidateActionPerformed

    private void btnSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseClicked
        openManageAccountView();
    }//GEN-LAST:event_btnSettingsMouseClicked

    private void btnTrainersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrainersActionPerformed
        openManagmentTrainersView();
    }//GEN-LAST:event_btnTrainersActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCashRegister;
    private javax.swing.JButton btnControl;
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnManagment;
    private javax.swing.JButton btnProducts;
    private javax.swing.JLabel btnSettings;
    private javax.swing.JButton btnTrainers;
    private javax.swing.JButton btnValidate;
    private javax.swing.JDesktopPane dsMain;
    private javax.swing.JLabel iconUser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
