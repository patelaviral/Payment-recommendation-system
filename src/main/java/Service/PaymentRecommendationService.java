package Service;

import java.util.*;

import Module.*;

public class PaymentRecommendationService {

    private final Set<String> LOB;
    private final HashMap<String, Set<String>> notAllow;
    private final HashMap<String, List<paymentRange>> limitAllowed;
    private final MakeOrderService makeOrderService;
    public PaymentRecommendationService() {
        LOB = new HashSet<>();
        notAllow = new HashMap<>();
        limitAllowed = new HashMap<>();
        makeOrderService = new MakeOrderService();
    }
    public void addNewLOB(String newLOB) {
        LOB.add(newLOB);
    }
    public void addNewRule(String newLOB, String rule){
        notAllow.put(newLOB, new HashSet<>());
        notAllow.get(newLOB).add(rule);
    }
    public void addRangePay(String newLOB, List<paymentRange> ranges){
        limitAllowed.put(newLOB, ranges);
    }


    public List<PaymentInstrument> getRecommendations(User user1, Cart cart1){
        List<PaymentInstrument> filtered = null;

        if(LOB.contains(cart1.getCartType())) filtered = filterOnTheBasisOfCartType(user1.getPaymentInstruments(), cart1.getCartType());
        else filtered = user1.getPaymentInstruments();

        DeviceContext deviceContext = user1.getUserContext().getDeviceContext();
        if(!deviceContext.isUPIEnabled()){
            filtered = removeUPI(filtered);
        }

        filtered = filterOnTheBaisOfLimit(filtered, cart1.getAmount(), cart1.getCartType());

        return makeOrderService.makerOrder(filtered, cart1.getCartType());
    }

    // filter on the basis of payment limit
    public List<PaymentInstrument> filterOnTheBaisOfLimit(List<PaymentInstrument> filtered, double limit, String cartType){
        List<PaymentInstrument> filteredList = new ArrayList<>();
        List<paymentRange> ranges = limitAllowed.get(cartType);

        for(PaymentInstrument paymentInstrument : filtered){

            String payCategory = paymentInstrument.getPayCategory();
            for(paymentRange paymentRange : ranges){

                if(payCategory.equals(paymentRange.getPayCategory())){
                    if(limit <= paymentRange.getRange()){
                        filteredList.add(paymentInstrument);
                    }
                }
            }
        }
        return filteredList;
    }

    // filter on the basis of UPIEnable
    public List<PaymentInstrument> removeUPI(List<PaymentInstrument> filtered){
        List<PaymentInstrument> filteredList = new ArrayList<>();
        for(PaymentInstrument paymentInstrument : filtered){
            if(!paymentInstrument.getPayCategory().equals("UPI")){
                filteredList.add(paymentInstrument);
            }
        }
        return filteredList;
    }

    // filter on the basis of LOB
    public List<PaymentInstrument> filterOnTheBasisOfCartType(List<PaymentInstrument> paymentInstruments, String cartType){
        Set<String> rules = notAllow.get(cartType);
        List<PaymentInstrument> filteredPaymentInstruments = new ArrayList<>();

        for(PaymentInstrument payInst: paymentInstruments){
            if(!rules.contains(payInst.getPayCategory())){
                filteredPaymentInstruments.add(payInst);
            }
        }

        return filteredPaymentInstruments;
    }
}
