package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage extends JFrame {
    private JFrame mainFrame;

    public LandingPage() {
        showMainScreen();
    }

    private void showMainScreen() {
        mainFrame = new JFrame("ShopSphere E-commerce Application");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.getContentPane().setBackground(new Color(52, 73, 85));
        mainFrame.setLayout(null);

        JLabel label = new JLabel("Welcome to ShopSphere");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(199, 227, 225));
        label.setBounds(0, 50, 800, 50);
        mainFrame.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(250, 200, 300, 200);
        buttonPanel.setBackground(new Color(80, 114, 123, 255));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 160, 131), 2));
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton customerButton = new JButton("Customer Login");
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setBackground(new Color(0, 120, 215));
        customerButton.setForeground(Color.WHITE);
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new CustomerGUI();
            }
        });

        JButton adminButton = new JButton("Admin Login");
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setBackground(new Color(0, 120, 215));
        adminButton.setForeground(Color.WHITE);
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new AdminGUI();
            }
        });

        buttonPanel.add(customerButton);
        buttonPanel.add(adminButton);

        mainFrame.add(buttonPanel);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LandingPage();
            }
        });
    }
}

