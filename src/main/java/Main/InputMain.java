package Main;

import Service.*;
import Module.*;


import java.util.Arrays;
import java.util.List;

public class InputMain {
    public static void main(String[] args) {

        PaymentRecommendationService service = new PaymentRecommendationService();

        // adding new LOB
        service.addNewLOB("Commerce");
        service.addNewRule("Commerce", "NetBanking");
        service.addRangePay("Commerce",
                Arrays.asList(
                        new paymentRange("UPI", 100000.00),
                        new paymentRange("Debit", 200000.00),
                        new paymentRange("Credit", 250000.00)
                ));

        service.addNewLOB("CreditCardBill");
        service.addNewRule("CreditCardBill", "Credit");
        service.addRangePay("CreditCardBill",
                Arrays.asList(
                        new paymentRange("UPI", 200000.00),
                        new paymentRange("Debit", 200000.00),
                        new paymentRange("NetBanking", 200000.00)
                ));

        service.addNewLOB("Investment");
        service.addNewRule("Investment", "Credit");
        service.addRangePay("Investment",
                Arrays.asList(
                        new paymentRange("UPI", 100000.00),
                        new paymentRange("Debit", 150000.00),
                        new paymentRange("NetBanking", 150000.00)
                ));


        // -------------------------- TEST CASE 1 --------------------------
        System.out.println("===== Test Case 1: UPI Disabled on Device =====");
        User user1 = new User(
                "U1",
                Arrays.asList(
                        new PaymentInstrument("U1", "UPI", 0.9),
                        new PaymentInstrument("D1", "Debit", 0.6),
                        new PaymentInstrument("C1", "Credit", 0.7)
                ),
                new UserContext(new DeviceContext(false)) // UPI Disabled!
        );
        Cart cart1 = new Cart("Commerce", 500);

        printAny(service.getRecommendations(user1, cart1));


        // -------------------------- TEST CASE 2 --------------------------
        System.out.println("\n===== Test Case 2: Commerce (NetBanking not allowed) =====");
        User user2 = new User(
                "U2",
                Arrays.asList(
                        new PaymentInstrument("U1", "UPI", 0.8),
                        new PaymentInstrument("N1", "NetBanking", 0.5),
                        new PaymentInstrument("D1", "Debit", 0.9)
                ),
                new UserContext(new DeviceContext(true))
        );
        Cart cart2 = new Cart("Commerce", 800);

        printAny(service.getRecommendations(user2, cart2));


        // -------------------------- TEST CASE 3 --------------------------
        System.out.println("\n===== Test Case 3: Credit Card Bill Payment =====");
        User user3 = new User(
                "U3",
                Arrays.asList(
                        new PaymentInstrument("C1", "Credit", 0.9),
                        new PaymentInstrument("U1", "UPI", 0.6),
                        new PaymentInstrument("D1", "Debit", 0.3),
                        new PaymentInstrument("N1", "NetBanking", 0.2)
                ),
                new UserContext(new DeviceContext(true))
        );
        Cart cart3 = new Cart("CreditCardBill", 30000);
        printAny(service.getRecommendations(user3, cart3));


        // -------------------------- TEST CASE 4 --------------------------
        System.out.println("\n===== Test Case 4: UPI Limit Exceeded =====");
        User user4 = new User(
                "U4",
                Arrays.asList(
                        new PaymentInstrument("U1", "UPI", 0.9),
                        new PaymentInstrument("U2", "UPI", 0.4),
                        new PaymentInstrument("D1", "Debit", 0.8)
                ),
                new UserContext(new DeviceContext(true))
        );
        Cart cart4 = new Cart("Commerce", 150000); // 1.5L
        printAny(service.getRecommendations(user4, cart4));


        // -------------------------- TEST CASE 5 --------------------------
        System.out.println("\n===== Test Case 5: Relevance Sort Within UPI =====");
        User user5 = new User(
                "U5",
                Arrays.asList(
                        new PaymentInstrument("U1", "UPI", 0.3),
                        new PaymentInstrument("U2", "UPI", 0.8),
                        new PaymentInstrument("U3", "UPI", 0.5)
                ),
                new UserContext(new DeviceContext(true))
        );
        Cart cart5 = new Cart("Investment", 80000);
        printAny(service.getRecommendations(user5, cart5));


        // -------------------------- TEST CASE 6 --------------------------
        System.out.println("\n===== Test Case 6: Everything Removed =====");
        User user6 = new User(
                "U6",
                Arrays.asList(
                        new PaymentInstrument("U1", "UPI", 0.9),
                        new PaymentInstrument("U2", "UPI", 0.4),
                        new PaymentInstrument("C1", "Credit", 0.7),
                        new PaymentInstrument("D1", "Debit", 0.2),
                        new PaymentInstrument("N1", "NetBanking", 0.3)
                ),
                new UserContext(new DeviceContext(false)) // UPI disabled
        );
        Cart cart6 = new Cart("Investment", 200000); // 2L
        printAny(service.getRecommendations(user6, cart6));


        // -------------------------- TEST CASE 7 --------------------------
        System.out.println("\n===== Test Case 7: Mixed Filters =====");
        User user7 = new User(
                "U7",
                Arrays.asList(
                        new PaymentInstrument("U1", "UPI", 0.9),
                        new PaymentInstrument("C1", "Credit", 0.6),
                        new PaymentInstrument("C2", "Credit", 0.8),
                        new PaymentInstrument("D1", "Debit", 0.7)
                ),
                new UserContext(new DeviceContext(true))
        );
        Cart cart7 = new Cart("Commerce", 200000); // 2L
        printAny(service.getRecommendations(user7, cart7));
    }
    public static void printAny(List<PaymentInstrument> list){
        for(PaymentInstrument pay : list){
            System.out.println("type:"+pay.getPayCategory()+", id:"+pay.getId()+", Score:"+pay.getScore());
        }
    }
}
