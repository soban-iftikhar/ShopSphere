package Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int productId;
    private final String productName;
    private final BigDecimal productPrice;
    private final int quantity;

    public OrderItem(int productId, String productName, BigDecimal productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return String.format("Model.OrderItem[id=%d, name=%s, price=%s, quantity=%d]",
                productId, productName, productPrice, quantity);
    }
}

