package Module;

public class Cart {
    private String cartType;
    private int amount;
    public Cart(String cartType, int amount) {
        this.cartType = cartType;
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }
}
