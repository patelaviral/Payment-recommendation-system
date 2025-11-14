package Module;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String userId;
    private final List<PaymentInstrument> paymentInstruments;
    private final UserContext userContext;
    public User(String userId, List<PaymentInstrument> paymentInstruments, UserContext userContext) {
        this.userId = userId;
        this.paymentInstruments = paymentInstruments;
        this.userContext = userContext;
    }

    public String getUserId() {
        return userId;
    }

    public List<PaymentInstrument> getPaymentInstruments() {
        return paymentInstruments;
    }

    public UserContext getUserContext() {
        return userContext;
    }
}
