package Model;

import java.io.*;
import java.util.*;

public class Admin extends Person {
    public Admin() {
        Product.loadProductsFromFile();
    }

    public Admin(String name, String username, String email, String password, String phone, String address) {
        super(name, username, email, password, phone, address);
        Product.loadProductsFromFile();
    }

    @Override
    public void signup(String name, String username, String email) {
        throw new UnsupportedOperationException("Admin signup is not supported.");
    }

    @Override
    public boolean signIn(String username, String password) {
        String adminUsername = "admin";
        String adminPassword = "admin";
        return username.equals(adminUsername) && password.equals(adminPassword);
    }

    public void addProduct(int id, String name, String company, double price, int stock) {
        Product product = new Product(id, name, company, price, stock);
        Product.addProduct(product);
    }

    public boolean removeProduct(int id) {
        Product product = Product.findProductById(id);
        if (product != null) {
            Product.getAllProducts().remove(product);
            Product.saveProductsToFile();
            return true;
        }
        return false;
    }

    public boolean updateProduct(int id, String name, String company, double price, int stock) {
        Product product = Product.findProductById(id);
        if (product != null) {
            if (!name.isEmpty()) product.setName(name);
            if (!company.isEmpty()) product.setCompany(company);
            if (price > 0) product.setPrice(price);
            if (stock >= 0) product.setStock(stock);
            Product.saveProductsToFile();
            return true;
        }
        return false;
    }

    public List<Order> viewOrders() throws IOException {
        return Order.loadOrders();
    }

    public boolean updateOrderStatus(String orderId, String newStatus) throws IOException {
        List<Order> orders = Order.loadOrders();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setOrderStatus(newStatus);
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Order.ORDER_FILE))) {
                    oos.writeObject(orders);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout() {
        Product.saveProductsToFile();
    }

    public List<Product> getProducts() {
        return Product.getAllProducts();
    }

    public Product getProductById(int id) {
        return Product.findProductById(id);
    }

    public Order getOrderById(String orderId) throws IOException {
        List<Order> orders = Order.loadOrders();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

