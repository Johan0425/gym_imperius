/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.ManagmentProducts;

import controllers.ProductController;
import controllers.ShoppingCartController;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Product;
import model.ShoppingCart;
import model.User;
import view.Sales.CartDetails;
import view.main.MainView;

/**
 *
 * @author joanp
 */
public class ManagmentProducts extends javax.swing.JInternalFrame {

    private final MainView view;
    private final ProductController controller;

    private final ShoppingCart cart;
    private final ShoppingCartController shoppingController;

    private TableRowSorter<DefaultTableModel> sorter;
    private final User user;

    /**
     * Creates new form ManagmentProducts
     *
     * @param view
     * @param user
     */
    public ManagmentProducts(MainView view, User user) {
        initComponents();
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        controller = new ProductController();
        this.view = view;
        cart = new ShoppingCart();
        shoppingController = new ShoppingCartController(cart);
        fillProductsTable();
        this.user = user;
    }

    public final void fillProductsTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> products = controller.listProducts();
        model.setColumnIdentifiers(new Object[]{
            "#", "Nombre", "Categoría", "Stock disponible", "Precio"
        });

        productsTable.setModel(model);
        tableCustomization();
        productsTable.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(model);
        productsTable.setRowSorter(sorter);

        for (int i = 0; i < products.size(); i++) {
            Product prod = (Product) products.get(i);
            model.addRow(new Object[]{
                prod.getId(),
                prod.getName(),
                prod.getCategory(),
                prod.getQuantity(),
                prod.getPrice()
            });
        }
    }

    private void filter() {
        sorter.setRowFilter(RowFilter.regexFilter(txtFilter.getText()));
    }

    private void tableCustomization() {
        productsTable.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 14));
        productsTable.getTableHeader().setOpaque(true);
        productsTable.getTableHeader().setBackground(new Color(255, 255, 255));
        productsTable.getTableHeader().setForeground(new Color(0, 0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtFilter = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSell = new javax.swing.JButton();
        btnNewProduct = new javax.swing.JButton();
        btnAddToCart = new javax.swing.JButton();

        mainPanel.setBackground(new java.awt.Color(0, 0, 0));
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainPanelMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buscar:");

        jSeparator1.setForeground(new java.awt.Color(254, 205, 77));

        txtFilter.setBackground(new java.awt.Color(0, 0, 0));
        txtFilter.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtFilter.setForeground(new java.awt.Color(255, 255, 255));
        txtFilter.setBorder(null);
        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setForeground(new java.awt.Color(254, 205, 77));

        productsTable.setBackground(new java.awt.Color(255, 255, 255));
        productsTable.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        productsTable.setForeground(new java.awt.Color(0, 0, 0));
        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Bebidas", "Agua", "200",  new Double(2000.0)},
                {"Bebidas", "Powerade", "100",  new Double(2700.0)}
            },
            new String [] {
                "Tipo", "Nombre", "Cantidad", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productsTable.setFocusable(false);
        productsTable.setGridColor(new java.awt.Color(128, 128, 128));
        productsTable.setRowHeight(25);
        productsTable.setSelectionBackground(new java.awt.Color(254, 205, 77));
        jScrollPane1.setViewportView(productsTable);

        btnEdit.setBackground(new java.awt.Color(254, 205, 77));
        btnEdit.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(0, 0, 0));
        btnEdit.setText("Editar");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(254, 205, 77));
        btnDelete.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(0, 0, 0));
        btnDelete.setText("Eliminar");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSell.setBackground(new java.awt.Color(254, 205, 77));
        btnSell.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnSell.setForeground(new java.awt.Color(0, 0, 0));
        btnSell.setText("Vender");
        btnSell.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSellActionPerformed(evt);
            }
        });

        btnNewProduct.setBackground(new java.awt.Color(254, 205, 77));
        btnNewProduct.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnNewProduct.setForeground(new java.awt.Color(0, 0, 0));
        btnNewProduct.setText("Nuevo");
        btnNewProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNewProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewProductActionPerformed(evt);
            }
        });

        btnAddToCart.setBackground(new java.awt.Color(0, 0, 0));
        btnAddToCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/carts.png"))); // NOI18N
        btnAddToCart.setBorder(null);
        btnAddToCart.setContentAreaFilled(false);
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(btnAddToCart)
                        .addGap(56, 56, 56)
                        .addComponent(btnNewProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112)
                        .addComponent(btnSell, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(360, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddToCart)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNewProduct)
                        .addComponent(btnEdit)
                        .addComponent(btnDelete)
                        .addComponent(btnSell)))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
            int selection = productsTable.getSelectedRow();

        if (selection < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto antes de continuar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(productsTable.getValueAt(selection, 0).toString());
        Product product = controller.selectProduct(id);

        if (product != null) {
            view.openEditProductView(this, product);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selection = productsTable.getSelectedRow();

        if (selection < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto antes de continuar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(productsTable.getValueAt(selection, 0).toString());
        Product product = controller.selectProduct(id);

        String strQuantity;

        do {
            strQuantity = JOptionPane.showInputDialog("Ingrese la cantidad que desea eliminar");

            if (strQuantity == null) {
                return;
            }

            if (strQuantity.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese la cantidad que desea eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } while (strQuantity.isEmpty());

        try {
            int quantity = Integer.parseInt(strQuantity);

            if (quantity > product.getQuantity()) {
                JOptionPane.showMessageDialog(null, "Excede el número, la cantidad que hay es: " + product.getQuantity(), "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.deleteProduct(id, quantity);
            JOptionPane.showMessageDialog(null, "El producto ha sido eliminado correctamente");
            fillProductsTable();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Valor inválido", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSellActionPerformed
        if (cart.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El carrito de compras se encuentra vacío");
            return;
        }

        view.openCartDetailsView(this, cart);
    }//GEN-LAST:event_btnSellActionPerformed

    private void btnNewProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewProductActionPerformed
        view.openRegisterProductView(this);
    }//GEN-LAST:event_btnNewProductActionPerformed

    private void mainPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseClicked
        productsTable.clearSelection();
    }//GEN-LAST:event_mainPanelMouseClicked

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        filter();
    }//GEN-LAST:event_txtFilterKeyReleased

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        int selection = productsTable.getSelectedRow();

        if (selection < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto antes de continuar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(productsTable.getValueAt(selection, 0).toString());
        Product product = shoppingController.selectProduct(id);

        if (product.getQuantity() == 0) {
            JOptionPane.showMessageDialog(null, "No hay stock disponible");
            return;
        }

        shoppingController.addProduct(product);
        JOptionPane.showMessageDialog(null, "El producto " + product.getName() + " ha sido añadido al carrito");
        fillProductsTable();
    }//GEN-LAST:event_btnAddToCartActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNewProduct;
    private javax.swing.JButton btnSell;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable productsTable;
    private javax.swing.JTextField txtFilter;
    // End of variables declaration//GEN-END:variables
}
