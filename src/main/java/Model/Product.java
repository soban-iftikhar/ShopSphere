package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private static final String FILE_NAME = "products.txt";
    private static List<Product> productList = new ArrayList<>();

    private int id;
    private String name;
    private String company;
    private double price;
    private int stock;

    // Constructor
    public Product(int id, String name, String company, double price, int stock) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Methods for File Operations
    public static void saveProductsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(productList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProductsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            productList = (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            productList = new ArrayList<>();
        }
    }

    // Method to Add a Model.Product
    public static boolean addProduct(Product product) {
        if (findProductById(product.getId()) != null) {
            return false;
        }
        productList.add(product);
        saveProductsToFile();
        return true;
    }

    // Method to Find a Model.Product by ID
    public static Product findProductById(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Method to Get All Products
    public static List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Company: " + company + ", Price: " + price + ", Stock: " + stock;
    }
}

