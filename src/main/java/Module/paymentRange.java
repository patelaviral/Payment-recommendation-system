package Module;

public class paymentRange {
    String payCategory;
    Double range;
    public paymentRange(String payCategory, Double range){
        this.payCategory = payCategory;
        this.range = range;
    }
    public String getPayCategory() {
        return payCategory;
    }
    public Double getRange(){
        return range;
    }
}
