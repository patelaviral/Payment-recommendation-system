package Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import Module.*;

public class MakeOrderService {
    private List<PaymentInstrument> filtered;
    private List<PaymentInstrument> credit;
    private List<PaymentInstrument> upi;
    private List<PaymentInstrument> debit;
    private List<PaymentInstrument> netBanking;

    private List<PaymentInstrument> result;
    public List<PaymentInstrument> makerOrder(List<PaymentInstrument> filtered, String cartType){
        result = new ArrayList<>();
        credit = new ArrayList<>();
        upi = new ArrayList<>();
        debit = new ArrayList<>();
        netBanking = new ArrayList<>();
        this.filtered = filtered;


        if(cartType.equals("Commerce")){
            makeOrderCommerce();
        }
        else if(cartType.equals("CreditCardBill")){
            makerOrderCredit();
        }
        else{
            makerOrderInvestment();
        }
        return result;
    }

    public void makeOrderCommerce(){

        for(PaymentInstrument paymentInstrument : filtered){
            if(paymentInstrument.getPayCategory().equals("Credit")){
                credit.add(paymentInstrument);
            }
            else if(paymentInstrument.getPayCategory().equals("UPI")){
                upi.add(paymentInstrument);
            }
            else{
                debit.add(paymentInstrument);
            }
        }
        sortByScoreDescending(credit);
        sortByScoreDescending(upi);
        sortByScoreDescending(debit);

        result.addAll(credit);
        result.addAll(upi);
        result.addAll(debit);

    }

    public void makerOrderCredit(){

        for(PaymentInstrument paymentInstrument : filtered){
            if(paymentInstrument.getPayCategory().equals("NetBanking")){
                netBanking.add(paymentInstrument);
            }
            else if(paymentInstrument.getPayCategory().equals("UPI")){
                upi.add(paymentInstrument);
            }
            else{
                debit.add(paymentInstrument);
            }
        }
        sortByScoreDescending(netBanking);
        sortByScoreDescending(upi);
        sortByScoreDescending(debit);

        result.addAll(upi);
        result.addAll(netBanking);
        result.addAll(debit);

    }

    public void makerOrderInvestment(){
        for(PaymentInstrument paymentInstrument : filtered){
            if(paymentInstrument.getPayCategory().equals("NetBanking")){
                netBanking.add(paymentInstrument);
            }
            else if(paymentInstrument.getPayCategory().equals("UPI")){
                upi.add(paymentInstrument);
            }
        }
        sortByScoreDescending(netBanking);
        sortByScoreDescending(upi);

        result.addAll(upi);
        result.addAll(netBanking);
    }

    public static void sortByScoreDescending(List<PaymentInstrument> list) {
        list.sort(new Comparator<PaymentInstrument>() {
            @Override
            public int compare(PaymentInstrument p1, PaymentInstrument p2) {
                // For decreasing order, compare p2 to p1
                return Double.compare(p2.getScore(), p1.getScore());
            }
        });
    }
}
