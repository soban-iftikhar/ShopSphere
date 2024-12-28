package Model;

import java.io.*;
import java.util.*;

public class Customer extends Person implements Serializable {
    private static final String FILE_NAME = "credentials.txt";

    // Lists to store usernames, emails, and passwords for user management
    private final ArrayList<String> usernames = new ArrayList<>();
    private final ArrayList<String> emails = new ArrayList<>();
    private final ArrayList<String> passwords = new ArrayList<>();
    private Cart cart;

    // Constructor
    public Customer() {
        this.cart = new Cart();
        loadUserCredentials();
        Product.loadProductsFromFile(); // Add this line
    }

    public Customer(String name, String username, String email, String password, String phone, String address) {
        super(name, username, email, password, phone, address);
        this.cart = new Cart();
        loadUserCredentials();
    }
    public void updateProductStock(int productId, int newStock) {
        Product.updateStock(productId, newStock);
    }
    @Override
    public void signup(String username, String email, String password) {
        if (usernames.contains(username)) {
            throw new IllegalArgumentException("Username already exists. Please choose a different one.");
        }
        usernames.add(username);
        emails.add(email);
        passwords.add(password);
        saveUserCredentials();
    }

    @Override
    public boolean signIn(String username, String password) {
        for (int i = 0; i < usernames.size(); i++) {
            if ((username.equals(usernames.get(i)) || username.equals(emails.get(i))) && password.equals(passwords.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout() {
        // Perform any necessary logout operations
    }

    @Override
    public List<Product> getProducts() {
        return Product.getAllProducts();
    }

    @Override
    public Product getProductById(int id) {
        return Product.findProductById(id);
    }

    // Save user credentials to a file
    private void saveUserCredentials() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(usernames);
            oos.writeObject(emails);
            oos.writeObject(passwords);
        } catch (IOException e) {
            throw new RuntimeException("Error saving credentials: " + e.getMessage());
        }
    }

    // Load user credentials from a file
    @SuppressWarnings("unchecked")
    private void loadUserCredentials() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            usernames.clear();
            emails.clear();
            passwords.clear();
            usernames.addAll((ArrayList<String>) ois.readObject());
            emails.addAll((ArrayList<String>) ois.readObject());
            passwords.addAll((ArrayList<String>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous credentials found. Starting fresh.");
        }
    }

    // Cart Management
    public void addItemToCart(OrderItem item) {
        cart.addItem(item);
    }

    public void removeItemFromCart(int productId) {
        cart.removeItem(productId);
    }

    public void updateItemQuantityInCart(int productId, int newQuantity) {
        cart.updateItemQuantity(productId, newQuantity);
    }

    public void clearCart() {
        cart.clearCart();
    }

    public List<OrderItem> viewCartItems() {
        return cart.getItems();
    }

    public double getTotalCartPrice() {
        return cart.getTotalPrice();
    }

    // Order Management
    public void placeOrder(String customerAddress) throws IOException {
        Order order = new Order(customerAddress);
        for (OrderItem item : cart.getItems()) {
            order.addItem(item);
        }
        order.saveOrder();
        clearCart();
    }

    public List<Order> viewOrders() throws IOException, ClassNotFoundException {
        return Order.loadOrders();
    }
}
