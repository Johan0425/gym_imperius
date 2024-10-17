/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.ManagmentCustomers;

import controllers.CustomerController;
import controllers.CustomerDayController;
import enums.MembershipType;
import exceptions.UserDeleteException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.sql.SQLException;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Customer;
import view.main.MainView;

/**
 *
 * @author joanp
 */
public class ManagmentCustomers extends javax.swing.JInternalFrame {

    private final MainView view;
    private final CustomerController controller;
    private final CustomerDayController controllerDay;
    private TableRowSorter<DefaultTableModel> sorter;

    /**
     * Creates new form ManagmentCustomers
     *
     * @param view
     */
    public ManagmentCustomers(MainView view) {
        initComponents();
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        controller = new CustomerController();
        controllerDay = new CustomerDayController();
        this.view = view;
        addTabChangeListener();
        fillCustomersTable();
        setNumberPersonsGreen();
        setNumberPersonsGray();
    }
    

    private void setNumberPersonsGreen() {
        int numberP = countActivePersons();
        txtNumberPersonsActive.setText(String.valueOf(numberP));

    }
    
    private void setNumberPersonsGray() {
        int numberP = countInactivePersons();
        txtNumberPersonsInactive.setText(String.valueOf(numberP));

    }

    private int countActivePersons() {
        int number = 0;
        DefaultTableModel model = (DefaultTableModel) customersTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int remainingDays = (int) model.getValueAt(i, 10);
            if (remainingDays != 0) {
                number++;
            }
        }
        return number;
    }
    
    private int countInactivePersons() {
        int number = 0;
        DefaultTableModel model = (DefaultTableModel) customersTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int remainingDays = (int) model.getValueAt(i, 10);
            if (remainingDays == 0) {
                number++;
            }
        }
        return number;
    }

    private void validateColorsTable() {

    }

    private void addTabChangeListener() {
        tabbedTables.addChangeListener((ChangeEvent e) -> {
            int selectedIndex = tabbedTables.getSelectedIndex();
            if (selectedIndex == 0) {
                fillCustomersTable();
            } else if (selectedIndex == 1) {
                fillCustomersDayTable();
            }
        });
    }

    public final void fillCustomersDayTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> customers = controllerDay.listCustomersDay();
        model.setColumnIdentifiers(new Object[]{
            "Cédula", "Nombre completo", "Teléfono", "Membresía", "Fecha venc."
        });

        customersDayTable.setModel(model);
        tableCustomizationDay();
        customersDayTable.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(model);
        customersDayTable.setRowSorter(sorter);

        for (int i = 0; i < customers.size(); i++) {
            Customer cus = (Customer) customers.get(i);
            if (cus.getMembership().getType() == MembershipType.DIA) {
                model.addRow(new Object[]{
                    cus.getCedula(),
                    cus.getFullname(),
                    cus.getPhoneNumber(),
                    cus.getMembership().getType().getName(),
                    cus.getMembership().getEndDate()
                });
            }
        }
    }

    public final void fillCustomersTable() {

    // Cambia el color de la fila dependiendo de la condición
    customersTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            int remainingDays = (int) table.getValueAt(row, 10); // 10 es el índice de la columna "Días restantes"

            // Verifica si remainingDays es igual a 0 y cambia el color de fondo de la fila
            if (remainingDays == 0) {
                c.setBackground(new Color(153, 0, 0)); // Color personalizado para días restantes igual a 0
                c.setForeground(Color.WHITE); // Puedes cambiar el color del texto si es necesario
            } else if (isSelected) {
                c.setBackground(new Color(254, 205, 77)); // Color personalizado para fila seleccionada
                c.setForeground(Color.WHITE); // Puedes cambiar el color del texto si es necesario
            } else if (remainingDays > 0) {
                c.setBackground(new Color(51, 102, 0)); // Color verde para personas activas (al menos 1 día restante)
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(table.getBackground()); // Fondo predeterminado para otros
                c.setForeground(table.getForeground());
            }

            return c;
        }
    });

    customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    customersTable.setSelectionBackground(new Color(254, 205, 77)); // Color personalizado para fila seleccionada

    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    ArrayList<Object> customers = controller.listCustomers();
    model.setColumnIdentifiers(new Object[]{
        "Cédula", "Nombre completo", "Teléfono", "Fecha Nacimiento", "Edad", "Info. Salud", "Nombre c. emerg", "Teléfono c. emerg",
        "Membresía", "Fecha venc.", "Días restantes"
    });

    customersTable.setModel(model);
    tableCustomization();
    customersTable.setAutoCreateRowSorter(true);
    sorter = new TableRowSorter<>(model);
    customersTable.setRowSorter(sorter);

    // Añadir las filas a la tabla
    for (int i = 0; i < customers.size(); i++) {
        Customer cus = (Customer) customers.get(i);

        int remainingDays = cus.getMembership().getRemainingDays();
        if (remainingDays < 0) {
            remainingDays = 0;
        }

        // Añade solo los clientes con al menos un día restante
        if (remainingDays > 0) {
            model.addRow(new Object[]{
                cus.getCedula(),
                cus.getFullname(),
                cus.getPhoneNumber(),
                cus.getBirthDate(),
                cus.getAge(),
                cus.getHealthInformation(),
                cus.getEmergencyContact().getFullname(),
                cus.getEmergencyContact().getPhoneNumber(),
                cus.getMembership().getType().getName(),
                cus.getMembership().getEndDate(),
                remainingDays
            });
        }
    }

    // Configurar el ordenamiento para que los días restantes se muestren en orden descendente
    ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
    sortKeys.add(new RowSorter.SortKey(10, SortOrder.DESCENDING)); // 10 es el índice de la columna "Días restantes"
    sorter.setSortKeys(sortKeys);
}


    private void filter() {
        sorter.setRowFilter(RowFilter.regexFilter(txtFilter.getText()));
    }

    private void tableCustomization() {
        customersTable.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 14));
        customersTable.getTableHeader().setOpaque(true);
        customersTable.getTableHeader().setBackground(new Color(255, 255, 255));
        customersTable.getTableHeader().setForeground(new Color(0, 0, 0));
    }

    private void tableCustomizationDay() {
        customersDayTable.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 14));
        customersDayTable.getTableHeader().setOpaque(true);
        customersDayTable.getTableHeader().setBackground(new Color(255, 255, 255));
        customersDayTable.getTableHeader().setForeground(new Color(0, 0, 0));
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
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNewCustomer = new javax.swing.JButton();
        tabbedTables = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        customersTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        customersDayTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtNumberPersonsActive = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNumberPersonsInactive = new javax.swing.JLabel();

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

        btnNewCustomer.setBackground(new java.awt.Color(254, 205, 77));
        btnNewCustomer.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnNewCustomer.setForeground(new java.awt.Color(0, 0, 0));
        btnNewCustomer.setText("Nuevo");
        btnNewCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCustomerActionPerformed(evt);
            }
        });

        tabbedTables.setForeground(new java.awt.Color(255, 255, 255));
        tabbedTables.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N

        customersTable.setBackground(new java.awt.Color(255, 255, 255));
        customersTable.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        customersTable.setForeground(new java.awt.Color(0, 0, 0));
        customersTable.setModel(new javax.swing.table.DefaultTableModel(
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
        customersTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customersTable.setSelectionBackground(new java.awt.Color(254, 205, 77));
        jScrollPane1.setViewportView(customersTable);

        tabbedTables.addTab("Clientes", jScrollPane1);

        customersDayTable.setBackground(new java.awt.Color(255, 255, 255));
        customersDayTable.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        customersDayTable.setForeground(new java.awt.Color(0, 0, 0));
        customersDayTable.setModel(new javax.swing.table.DefaultTableModel(
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
        customersDayTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customersDayTable.setSelectionBackground(new java.awt.Color(254, 205, 77));
        jScrollPane2.setViewportView(customersDayTable);

        tabbedTables.addTab("Clases", jScrollPane2);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user (3) 33.png"))); // NOI18N

        txtNumberPersonsActive.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        txtNumberPersonsActive.setForeground(new java.awt.Color(255, 255, 255));
        txtNumberPersonsActive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNumberPersonsActive.setText("0");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user (3) 33 - copia.png"))); // NOI18N

        txtNumberPersonsInactive.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        txtNumberPersonsInactive.setForeground(new java.awt.Color(255, 255, 255));
        txtNumberPersonsInactive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNumberPersonsInactive.setText("0");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(298, 298, 298)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(310, 310, 310)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabbedTables, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(273, 273, 273)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(txtNumberPersonsActive, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(txtNumberPersonsInactive, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(177, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNumberPersonsActive, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addComponent(txtNumberPersonsInactive, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedTables, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewCustomer)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseClicked
        customersTable.clearSelection();
        customersDayTable.clearSelection();
    }//GEN-LAST:event_mainPanelMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int selection = customersTable.getSelectedRow();

        if (selection < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente antes de continuar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cedula = customersTable.getValueAt(selection, 0).toString();
        Customer customer = controller.selectCustomer(cedula);

        if (customer != null) {
            view.openEditCustomerView(this, customer);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selection = customersTable.getSelectedRow();

        if (selection < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente antes de continuar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cedula = customersTable.getValueAt(selection, 0).toString();

        int answer = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el cliente?", "Advertencia", JOptionPane.YES_NO_OPTION);

        try {

            if (answer == 0) {
                controller.deleteCustomer(cedula);
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
                fillCustomersTable();
            }
        } catch (UserDeleteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCustomerActionPerformed
        view.openRegisterCustomerView(this);
    }//GEN-LAST:event_btnNewCustomerActionPerformed

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        filter();
    }//GEN-LAST:event_txtFilterKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JTable customersDayTable;
    private javax.swing.JTable customersTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTabbedPane tabbedTables;
    private javax.swing.JTextField txtFilter;
    private javax.swing.JLabel txtNumberPersonsActive;
    private javax.swing.JLabel txtNumberPersonsInactive;
    // End of variables declaration//GEN-END:variables

}
