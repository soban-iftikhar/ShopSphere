package Model;

import GUI.LandingPage;

public class Main {
    public static void main(String[] args) {
        Product.loadProductsFromFile(); // Add this line
        LandingPage landingPage = new LandingPage();
    }
}

