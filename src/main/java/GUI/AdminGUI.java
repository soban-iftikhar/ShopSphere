package GUI;
import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;


public class AdminGUI extends JFrame {
    private Admin admin;
    private JFrame loginFrame;
    private JFrame adminFrame;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public AdminGUI() {
        admin = new Admin();
        showLoginScreen();
    }

    private void showLoginScreen() {
        loginFrame = new JFrame("E-commerce Application");
        loginFrame.setSize(800, 600);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.getContentPane().setBackground(new Color(52, 73, 85));
        loginFrame.setLayout(null);

        JLabel label = new JLabel("Welcome to ShopSphere");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(199, 227, 225));
        label.setBounds(0, 50, 800, 50);
        loginFrame.add(label);

        JPanel glassPanel = new JPanel();
        glassPanel.setBounds(250, 150, 300, 300);
        glassPanel.setBackground(new Color(80, 114, 123, 255));
        glassPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 160, 131), 2));
        glassPanel.setLayout(null);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        usernameField.setBounds(50, 30, 200, 40);
        glassPanel.add(usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordField.setBounds(50, 90, 200, 40);
        glassPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(100, 150, 100, 30);
        glassPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (admin.signIn(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    loginFrame.dispose();
                    showAdminDashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        loginFrame.add(glassPanel);
        loginFrame.setVisible(true);
    }

    private void showAdminDashboard() {
        adminFrame = new JFrame("Admin Dashboard - ShopSphere");
        adminFrame.setSize(1000, 600);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButtons(buttonPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        adminFrame.add(mainPanel);
        adminFrame.setVisible(true);
    }

    private void setupProductTable() {
        contentPanel.removeAll();

        String[] columns = {"ID", "Name", "Company", "Price", "Stock"};
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        refreshProductTable();

        JScrollPane scrollPane = new JScrollPane(productTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> showMainMenu());
        contentPanel.add(backButton, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMainMenu() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void refreshProductTable() {
        tableModel.setRowCount(0);
        List<Product> products = admin.getProducts();
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getCompany(),
                    product.getPrice(),
                    product.getStock()
            });
        }
    }

    private void addButtons(JPanel buttonPanel) {
        JButton showProductsButton = new JButton("Show Products");
        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        JButton updateButton = new JButton("Update Product");
        JButton viewOrdersButton = new JButton("View Orders");
        JButton logoutButton = new JButton("Logout");

        showProductsButton.addActionListener(e -> setupProductTable());
        addButton.addActionListener(e -> showAddProductDialog());
        removeButton.addActionListener(e -> showRemoveProductDialog());
        updateButton.addActionListener(e -> showUpdateProductDialog());
        viewOrdersButton.addActionListener(e -> showOrders());
        logoutButton.addActionListener(e -> logout());

        buttonPanel.add(showProductsButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(logoutButton);
    }

    private void showAddProductDialog() {
        JDialog dialog = new JDialog(adminFrame, "Add Product", true);
        dialog.setLayout(new GridLayout(6, 2, 5, 5));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField companyField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();

        dialog.add(new JLabel("Product ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Company:"));
        dialog.add(companyField);
        dialog.add(new JLabel("Price:"));
        dialog.add(priceField);
        dialog.add(new JLabel("Stock:"));
        dialog.add(stockField);

        JButton submitButton = new JButton("Add");
        submitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String company = companyField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());

                admin.addProduct(id, name, company, price, stock);
                refreshProductTable();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers");
            }
        });

        dialog.add(submitButton);
        dialog.pack();
        dialog.setLocationRelativeTo(adminFrame);
        dialog.setVisible(true);
    }

    private void showRemoveProductDialog() {
        String idStr = JOptionPane.showInputDialog(adminFrame, "Enter Product ID to remove:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                if (admin.removeProduct(id)) {
                    refreshProductTable();
                    JOptionPane.showMessageDialog(adminFrame, "Product removed successfully");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Product not found");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(adminFrame, "Please enter a valid ID");
            }
        }
    }

    private void showUpdateProductDialog() {
        String idStr = JOptionPane.showInputDialog(adminFrame, "Enter Product ID to update:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Product product = admin.getProductById(id);
                if (product != null) {
                    JDialog dialog = new JDialog(adminFrame, "Update Product", true);
                    dialog.setLayout(new GridLayout(6, 2, 5, 5));

                    JTextField nameField = new JTextField(product.getName());
                    JTextField companyField = new JTextField(product.getCompany());
                    JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
                    JTextField stockField = new JTextField(String.valueOf(product.getStock()));

                    dialog.add(new JLabel("Name:"));
                    dialog.add(nameField);
                    dialog.add(new JLabel("Company:"));
                    dialog.add(companyField);
                    dialog.add(new JLabel("Price:"));
                    dialog.add(priceField);
                    dialog.add(new JLabel("Stock:"));
                    dialog.add(stockField);

                    JButton submitButton = new JButton("Update");
                    submitButton.addActionListener(e -> {
                        try {
                            String name = nameField.getText();
                            String company = companyField.getText();
                            double price = Double.parseDouble(priceField.getText());
                            int stock = Integer.parseInt(stockField.getText());

                            if (admin.updateProduct(id, name, company, price, stock)) {
                                refreshProductTable();
                                dialog.dispose();
                                JOptionPane.showMessageDialog(adminFrame, "Product updated successfully");
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Failed to update product");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(dialog, "Please enter valid numbers");
                        }
                    });

                    dialog.add(submitButton);
                    dialog.pack();
                    dialog.setLocationRelativeTo(adminFrame);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Product not found");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(adminFrame, "Please enter a valid ID");
            }
        }
    }

    private void showOrders() {
        try {
            List<Order> orders = admin.viewOrders();
            JDialog ordersDialog = new JDialog(adminFrame, "Orders", true);
            ordersDialog.setLayout(new BorderLayout());

            String[] columns = {"Order ID", "Customer", "Total", "Status"};
            DefaultTableModel orderTableModel = new DefaultTableModel(columns, 0);
            JTable orderTable = new JTable(orderTableModel);

//            for (Order order : orders) {
//                orderTableModel.addRow(new Object[]{
//                        order.getId(),
//                        order.getCustomerName(),
//                        order.getTotal(),
//                        order.getStatus()
//                });
//            }

            JScrollPane scrollPane = new JScrollPane(orderTable);
            ordersDialog.add(scrollPane, BorderLayout.CENTER);

            ordersDialog.setSize(400, 300);
            ordersDialog.setLocationRelativeTo(adminFrame);
            ordersDialog.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(adminFrame, "Error loading orders: " + e.getMessage());
        }
    }

    private void logout() {
        admin.logout();
        adminFrame.dispose();
        showLoginScreen();
    }
}