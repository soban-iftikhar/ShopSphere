package Model;

import java.io.IOException;
import java.util.*;

public class Admin extends Person {
    public Admin() {
        // Load products when Model.Admin is instantiated
        Product.loadProductsFromFile();
    }

    public Admin(String name, String username, String email, String password, String phone, String address) {
        super(name, username, email, password, phone, address);
        // Load products when Model.Admin is instantiated
        Product.loadProductsFromFile();
    }

    @Override
    public void signup(String name, String username, String email) {
        throw new UnsupportedOperationException("Model.Admin signup is not supported.");
    }

    @Override
    public boolean signIn(String username, String password) {
        // Hard-coded admin credentials (for demonstration only; replace with secure authentication)
        String adminUsername = "admin";
        String adminPassword = "admin";
        if(username.equals(adminUsername) && password.equals(adminPassword)) {
            return true;
        }
       return false;
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
        // Assuming Model.Order class has a static method to load orders
        return Order.loadOrders();
    }

    @Override
    public void logout() {
        // Save products before logging out
        Product.saveProductsToFile();
    }

    public List<Product> getProducts() {
        return Product.getAllProducts();
    }

    public Product getProductById(int id) {
        return Product.findProductById(id);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

