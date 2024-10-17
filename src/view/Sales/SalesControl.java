/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.Sales;

import controllers.SaleController;
import enums.Role;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.Membership;
import model.Sale;
import model.Trainer;

/**
 *
 * @author joanp
 */
public class SalesControl extends javax.swing.JInternalFrame {

    private final SaleController controller;

    /**
     * Creates new form SalesControl
     */
    public SalesControl() {
        initComponents();
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        controller = new SaleController();
        addTabChangeListener();
        fillProductsSalesTable();
    }

    private void tableCustomizationP() {
        productsTable.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 14));
        productsTable.getTableHeader().setOpaque(true);
        productsTable.getTableHeader().setBackground(new Color(255, 255, 255));
        productsTable.getTableHeader().setForeground(new Color(0, 0, 0));
    }

    private void tableCustomizationM() {
        membershipsTable.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 14));
        membershipsTable.getTableHeader().setOpaque(true);
        membershipsTable.getTableHeader().setBackground(new Color(255, 255, 255));
        membershipsTable.getTableHeader().setForeground(new Color(0, 0, 0));
    }

    private void addTabChangeListener() {
        jTabbedPane1.addChangeListener((ChangeEvent e) -> {
            int selectedIndex = jTabbedPane1.getSelectedIndex();
            if (selectedIndex == 0) {
                fillProductsSalesTable();
            } else if (selectedIndex == 1) {
                fillMembershipsSalesTable();
            }
        });
    }

    private void fillProductsSalesTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Sale> sales = controller.listProductsSales();
        model.setColumnIdentifiers(new Object[]{
            "#", "Fecha", "Vendedor", "Producto", "Total", "Metodo de pago"
        });

        productsTable.setModel(model);
        tableCustomizationP();

        float total = 0;

        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);

            Trainer trainer = null;
            String trainerName = null;
            if (sale.getUser().getRole() == Role.ENTRENADOR) {
                trainer = controller.selectTrainer(sale.getUser().getCedula());
            }

            if (trainer != null) {
                trainerName = trainer.getFullname();
            }

            total += sales.get(i).getTotal();
            model.addRow(new Object[]{
                sale.getId(),
                sale.getDate(),
                sale.getUser().getRole() == Role.ADMIN ? "Administrador" : trainerName,
                sale.getProduct().getName(),
                sale.getProduct().getPrice(),
                sale.getPaymentMethod()

            });
        }
        showTotal(total);
    }

    private void fillMembershipsSalesTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Membership> memberships = controller.listMembershipsSales();
        model.setColumnIdentifiers(new Object[]{
            "#", "Fecha", "Vendedor", "Tipo membresía", "Total", "Metodo de pago"
        });

        membershipsTable.setModel(model);
        tableCustomizationM();

        float total = 0;

        for (int i = 0; i < memberships.size(); i++) {
            Membership membership = memberships.get(i);

            Trainer trainer = null;
            String trainerName = null;
            if (membership.getSeller().getRole() == Role.ENTRENADOR) {
                trainer = controller.selectTrainer(membership.getSeller().getCedula());
            }

            if (trainer != null) {
                trainerName = trainer.getFullname();
            }

            total += memberships.get(i).getType().getPrice();
            model.addRow(new Object[]{
                membership.getId(),
                membership.getStartDate(),
                membership.getSeller().getRole() == Role.ADMIN ? "Administrador" : trainerName,
                membership.getType().getName(),
                membership.getType().getPrice(),
                membership.getPaymentMethod()
            });
        }
        showTotal(total);
    }

    private void showTotal(float total) {
        lblTotal.setText(String.valueOf(total));
        lblTotal.setVisible(true);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        membershipsTable = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N

        productsTable.setBackground(new java.awt.Color(255, 255, 255));
        productsTable.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
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

        jTabbedPane1.addTab("PRODUCTOS", jScrollPane1);

        membershipsTable.setBackground(new java.awt.Color(255, 255, 255));
        membershipsTable.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        membershipsTable.setForeground(new java.awt.Color(0, 0, 0));
        membershipsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        membershipsTable.setSelectionBackground(new java.awt.Color(254, 205, 77));
        jScrollPane2.setViewportView(membershipsTable);

        jTabbedPane1.addTab("MEMBRESIAS", jScrollPane2);

        lblTotal.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("50.000");

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Total:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap(398, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblTotal))
                .addContainerGap(242, Short.MAX_VALUE))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable membershipsTable;
    private javax.swing.JTable productsTable;
    // End of variables declaration//GEN-END:variables
}
