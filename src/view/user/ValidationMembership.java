/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.user;

import controllers.CustomerController;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import model.Product;
import model.ShoppingCart;
import placeHolder.PlaceHolder;
import view.information.Panel;
import view.main.MainView;

/**
 *
 * @author joanp
 */
public class ValidationMembership extends javax.swing.JFrame {

    private final MainView view;
    private final CustomerController controller;
    private Timer timer;

    /**
     * Creates new form ValidationMembership
     *
     * @param view
     */
    public ValidationMembership(MainView view) {
        initComponents();
        setTitle("Validación entrada de usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        controller = new CustomerController();
        this.view = view;
        lblPressEnter.setVisible(false);
        lblAlert.setVisible(false);
        setIcon();
        placeHolder();
        hidePanel();
        hidelblT();
        hideCartPanel();

        timer = new Timer(5000, (ActionEvent e) -> {
            hidePanel();
            lblAlert.setVisible(false);
            txtValidateId.setText("");
            hidelblT();
            timer.stop();
        }
        );

    }

    public final void showLblThxs() {
        lblAlert.setVisible(true);
        timer.start();
    }

    private void validateAlert(String cedula) {
        Customer customer = controller.selectCustomer(cedula);
        if (customer.getMembership().getRemainingDays() <= 5) {
            lblAlert.setText("PRONTO VENCERÁ SU MEMBRESÍA");
            lblAlert.setForeground(Color.orange);
            lblAlert.setVisible(true);
        } else if (customer.getMembership().getRemainingDays() > 5) {
            lblAlert.setText("ESTA AL DIA CON SU MEMBRESIA");
            lblAlert.setForeground(Color.green);
            lblAlert.setVisible(true);
        } else if (customer.getMembership().getRemainingDays() == 0) {
            lblAlert.setText("HA VENCIDO SU MEMBRESÍA");
            lblAlert.setForeground(Color.red);
            lblAlert.setVisible(true);
        }

    }

    private void hidePanel() {
        panelInformation.setVisible(false);
    }

    public final void hideCartPanel() {
        panelCart.setVisible(false);
    }

    private void hidelblT() {
        lblThxs.setVisible(false);
    }

    public void showInformationCustomer(String cedula) {
        Customer customer = controller.selectCustomer(cedula);

        txtName.setText(customer.getFullname());
        txtEndDate.setText(customer.getMembership().getEndDate().toString());
        txtDays.setText(String.valueOf(customer.getMembership().getRemainingDays()));

        panelInformation.setVisible(true);
        timer.start();
    }

    public void showCartInformation(ShoppingCart cart) {
        if (cart != null) {
            fillProductsTable(cart);
            setTotal(cart);
            panelInformation.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Debe tener un carrito activo");
        }

    }

    private void tableCustomization() {
        productsTable.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 14));
        productsTable.getTableHeader().setOpaque(true);
        productsTable.getTableHeader().setBackground(new Color(255, 255, 255));
        productsTable.getTableHeader().setForeground(new Color(0, 0, 0));
    }

    private void fillProductsTable(ShoppingCart cart) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Product> products = cart.getProducts();
        model.setColumnIdentifiers(new Object[]{
            "#", "Nombre", "Categoría", "Precio"
        });

        productsTable.setModel(model);
        tableCustomization();

        for (int i = 0; i < products.size(); i++) {
            Product prod = (Product) products.get(i);
            model.addRow(new Object[]{
                prod.getId(),
                prod.getName(),
                prod.getCategory(),
                prod.getPrice()
            });
        }

    }

    private void setTotal(ShoppingCart cart) {
        lblTotal.setText(String.valueOf(cart.getTotal()));
    }

    public void openInSecondScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();

        if (devices.length > 1) {
            GraphicsDevice secondScreen = devices[1];
            Rectangle bounds = secondScreen.getDefaultConfiguration().getBounds();

            // Obtener la posición central de la pantalla
            int x = bounds.x + bounds.width / 2 - this.getWidth() / 2;
            int y = bounds.y + bounds.height / 2 - this.getHeight() / 2;

            // Establecer la ubicación en la posición central calculada
            this.setLocation(x, y);

            this.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Hacer visible la ventana
            this.setVisible(true);
        } else {
            // Si no hay una segunda pantalla conectada muestra el mensaje
            JOptionPane.showMessageDialog(null, "No hay una segunda pantalla disponible");
            this.dispose();
        }
    }

    public void requestFocusAndEdit() {
        txtValidateId.requestFocusInWindow();
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private void placeHolder() {
        new PlaceHolder("Ingrese su número de documento", txtValidateId);
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
        txtValidateId = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        panelInformation = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        txtEndDate = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txtDays = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        lblPressEnter = new javax.swing.JLabel();
        lblAlert = new javax.swing.JLabel();
        panelCart = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblThxs = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo.png"))); // NOI18N

        txtValidateId.setBackground(new java.awt.Color(0, 0, 0));
        txtValidateId.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        txtValidateId.setForeground(new java.awt.Color(255, 255, 255));
        txtValidateId.setBorder(null);
        txtValidateId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValidateIdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValidateIdKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValidateIdKeyTyped(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(254, 205, 77));
        jSeparator1.setForeground(new java.awt.Color(254, 205, 77));

        panelInformation.setBackground(new java.awt.Color(0, 0, 0));
        panelInformation.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jLabel1.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha vencimiento:");

        jLabel3.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Dias restantes:");

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(0, 0, 0));
        txtName.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtName.setForeground(new java.awt.Color(255, 255, 255));
        txtName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtName.setBorder(null);

        txtEndDate.setEditable(false);
        txtEndDate.setBackground(new java.awt.Color(0, 0, 0));
        txtEndDate.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtEndDate.setForeground(new java.awt.Color(255, 255, 255));
        txtEndDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEndDate.setBorder(null);

        txtDays.setEditable(false);
        txtDays.setBackground(new java.awt.Color(0, 0, 0));
        txtDays.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtDays.setForeground(new java.awt.Color(255, 255, 255));
        txtDays.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDays.setBorder(null);

        javax.swing.GroupLayout panelInformationLayout = new javax.swing.GroupLayout(panelInformation);
        panelInformation.setLayout(panelInformationLayout);
        panelInformationLayout.setHorizontalGroup(
            panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator2)
                        .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                    .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator3)
                        .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator4)
                        .addComponent(txtDays, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
        panelInformationLayout.setVerticalGroup(
            panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(panelInformationLayout.createSequentialGroup()
                        .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(panelInformationLayout.createSequentialGroup()
                        .addComponent(txtDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
        );

        lblPressEnter.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        lblPressEnter.setForeground(new java.awt.Color(255, 255, 255));
        lblPressEnter.setText("Presione enter para continuar");

        lblAlert.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        lblAlert.setForeground(new java.awt.Color(255, 255, 255));
        lblAlert.setText("jLabel4");

        panelCart.setBackground(new java.awt.Color(0, 0, 0));
        panelCart.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        productsTable.setBackground(new java.awt.Color(255, 255, 255));
        productsTable.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        productsTable.setForeground(new java.awt.Color(0, 0, 0));
        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        productsTable.setSelectionBackground(new java.awt.Color(254, 205, 77));
        jScrollPane1.setViewportView(productsTable);

        jLabel4.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total:");

        lblTotal.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("50.000");

        javax.swing.GroupLayout panelCartLayout = new javax.swing.GroupLayout(panelCart);
        panelCart.setLayout(panelCartLayout);
        panelCartLayout.setHorizontalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCartLayout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(jLabel4)
                .addGap(51, 51, 51)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCartLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(686, 686, 686)))
        );
        panelCartLayout.setVerticalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTotal))
                .addContainerGap())
            .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCartLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(40, Short.MAX_VALUE)))
        );

        lblThxs.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        lblThxs.setForeground(new java.awt.Color(255, 255, 255));
        lblThxs.setText("¡GRACIAS POR SU COMPRA!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addComponent(lblAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 899, Short.MAX_VALUE)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(313, 313, 313)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator1)
                                    .addComponent(txtValidateId, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(275, 275, 275)
                                .addComponent(panelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(393, 393, 393)
                                .addComponent(lblPressEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(134, 134, 134)
                        .addComponent(panelCart, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(325, 325, 325)
                        .addComponent(lblThxs, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(lblAlert)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(lblThxs)
                        .addGap(56, 56, 56)
                        .addComponent(txtValidateId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPressEnter)
                        .addGap(50, 50, 50)
                        .addComponent(panelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(247, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(180, 180, 180))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtValidateIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValidateIdKeyTyped
        String id = txtValidateId.getText();
        char c = evt.getKeyChar();

        if (!Character.isDigit(c) || id.length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtValidateIdKeyTyped

    private void txtValidateIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValidateIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String cedula = txtValidateId.getText();
            lblAlert.setVisible(false);
            lblPressEnter.setVisible(false);

            if (cedula.equals("")) {
                JOptionPane.showMessageDialog(this, "Ingrese su cédula");
                return;
            }
            Customer customer = controller.selectCustomer(cedula);
            if (customer != null) {
                showInformationCustomer(cedula);
                validateAlert(cedula);
                new Panel(cedula).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el cliente");
            }
        }
    }//GEN-LAST:event_txtValidateIdKeyPressed

    private void txtValidateIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValidateIdKeyReleased
        //cuando el usuario ingrese la cedula debe mostrar un mensaje de "presione enter para continuar" o algo que le indique lo que debe de hacer
        String id = txtValidateId.getText();

        if (id.length() >= 7) {
            lblPressEnter.setVisible(true);
        } else {
            lblPressEnter.setVisible(false);
        }
    }//GEN-LAST:event_txtValidateIdKeyReleased

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAlert;
    private javax.swing.JLabel lblPressEnter;
    private javax.swing.JLabel lblThxs;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panelCart;
    private javax.swing.JPanel panelInformation;
    private javax.swing.JTable productsTable;
    private javax.swing.JTextField txtDays;
    private javax.swing.JTextField txtEndDate;
    private javax.swing.JTextField txtName;
    public static javax.swing.JTextField txtValidateId;
    // End of variables declaration//GEN-END:variables
}
