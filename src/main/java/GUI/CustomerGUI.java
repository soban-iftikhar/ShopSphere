package GUI;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class CustomerGUI extends JFrame {
    private Customer customer;
    private JFrame loginFrame;
    private JFrame customerFrame;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public CustomerGUI() {
        customer = new Customer();
        showLoginScreen();
    }

    private void showLoginScreen() {
        loginFrame = new JFrame("E-commerce Application - Customer Login");
        loginFrame.setSize(800, 600);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.getContentPane().setBackground(new Color(52, 73, 85));
        loginFrame.setLayout(null);

        JLabel label = new JLabel("Welcome to ShopSphere - Customer Portal");
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

                if (customer.signIn(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    loginFrame.dispose();
                    showCustomerDashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        loginFrame.add(glassPanel);

        JLabel signupLabel = new JLabel("Don't have an account? Sign up here");
        signupLabel.setForeground(new Color(199, 227, 225));
        signupLabel.setBounds(300, 460, 200, 30);
        loginFrame.add(signupLabel);

        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginFrame.dispose();
                showSignupScreen();
            }
        });

        JButton backToLandingButton = new JButton("Back to Landing Page");
        backToLandingButton.setFont(new Font("Arial", Font.BOLD, 14));
        backToLandingButton.setBackground(new Color(0, 120, 215));
        backToLandingButton.setForeground(Color.WHITE);
        backToLandingButton.setBounds(300, 500, 200, 30);
        backToLandingButton.addActionListener(e -> returnToLandingPage());
        loginFrame.add(backToLandingButton);

        loginFrame.setVisible(true);
    }

    private void showSignupScreen() {
        JFrame signupFrame = new JFrame("E-commerce Application - Customer Signup");
        signupFrame.setSize(800, 600);
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setResizable(false);
        signupFrame.getContentPane().setBackground(new Color(52, 73, 85));
        signupFrame.setLayout(null);

        JLabel label = new JLabel("Sign Up for ShopSphere");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(199, 227, 225));
        label.setBounds(0, 50, 800, 50);
        signupFrame.add(label);

        JPanel glassPanel = new JPanel();
        glassPanel.setBounds(250, 150, 300, 350);
        glassPanel.setBackground(new Color(80, 114, 123, 255));
        glassPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 160, 131), 2));
        glassPanel.setLayout(null);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createTitledBorder("Name"));
        nameField.setBounds(50, 30, 200, 40);
        glassPanel.add(nameField);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        usernameField.setBounds(50, 80, 200, 40);
        glassPanel.add(usernameField);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        emailField.setBounds(50, 130, 200, 40);
        glassPanel.add(emailField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordField.setBounds(50, 180, 200, 40);
        glassPanel.add(passwordField);

        JTextField phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneField.setBorder(BorderFactory.createTitledBorder("Phone"));
        phoneField.setBounds(50, 230, 200, 40);
        glassPanel.add(phoneField);

        JTextField addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 16));
        addressField.setBorder(BorderFactory.createTitledBorder("Address"));
        addressField.setBounds(50, 280, 200, 40);
        glassPanel.add(addressField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setBackground(new Color(0, 120, 215));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBounds(100, 330, 100, 30);
        glassPanel.add(signupButton);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String phone = phoneField.getText();
                String address = addressField.getText();

                try {
                    customer.signup(username, email, password);
                    JOptionPane.showMessageDialog(null, "Signup successful!");
                    signupFrame.dispose();
                    showLoginScreen();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        signupFrame.add(glassPanel);
        signupFrame.setVisible(true);
    }

    private void showCustomerDashboard() {
        customerFrame = new JFrame("Customer Dashboard - ShopSphere");
        customerFrame.setSize(1000, 600);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButtons(buttonPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        customerFrame.add(mainPanel);
        customerFrame.setVisible(true);
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
        List<Product> products = customer.getProducts();
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
        JButton viewCartButton = new JButton("View Cart");
        JButton viewOrdersButton = new JButton("View Orders");
        JButton logoutButton = new JButton("Logout");
        JButton returnToLandingButton = new JButton("Return to Landing Page");

        showProductsButton.addActionListener(e -> setupProductTable());
        viewCartButton.addActionListener(e -> viewCart());
        viewOrdersButton.addActionListener(e -> viewOrders());
        logoutButton.addActionListener(e -> logout());
        returnToLandingButton.addActionListener(e -> returnToLandingPage());

        buttonPanel.add(showProductsButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(returnToLandingButton);
    }

    private void viewCart() {
        contentPanel.removeAll();

        String[] columns = {"Product", "Quantity", "Price"};
        DefaultTableModel cartTableModel = new DefaultTableModel(columns, 0);
        JTable cartTable = new JTable(cartTableModel);

        List<OrderItem> cartItems = customer.viewCartItems();
        for (OrderItem item : cartItems) {
            cartTableModel.addRow(new Object[]{
                    item.getProductName(),
                    item.getQuantity(),
                    item.getTotalPrice()
            });
        }

        JScrollPane scrollPane = new JScrollPane(cartTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());
        bottomPanel.add(checkoutButton);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> showMainMenu());
        bottomPanel.add(backButton);

        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void checkout() {
        String address = JOptionPane.showInputDialog(customerFrame, "Enter delivery address:");
        if (address != null && !address.isEmpty()) {
            try {
                customer.placeOrder(address);
                JOptionPane.showMessageDialog(customerFrame, "Order placed successfully!");
                showMainMenu();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(customerFrame, "Error placing order: " + e.getMessage());
            }
        }
    }

    private void viewOrders() {
        contentPanel.removeAll();

        String[] columns = {"Order ID", "Date", "Total", "Status"};
        DefaultTableModel orderTableModel = new DefaultTableModel(columns, 0);
        JTable orderTable = new JTable(orderTableModel);

        try {
            List<Order> orders = customer.viewOrders();
            for (Order order : orders) {
                orderTableModel.addRow(new Object[]{
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getOrderStatus()
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(customerFrame, "Error loading orders: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(orderTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> showMainMenu());
        contentPanel.add(backButton, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void logout() {
        customer.logout();
        customerFrame.dispose();
        showLoginScreen();
    }

    private void returnToLandingPage() {
        loginFrame.dispose();
        new LandingPage();
    }
}

