package Module;

public class PaymentInstrument {
    private String id;
    private String payCategory;
    private double score;
    public PaymentInstrument(String id, String payCategory, double score) {
        this.id = id;
        this.payCategory = payCategory;
        this.score = score;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
