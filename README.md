# Payment Recommendation Engine (CRED-Style)

A Java-based system that filters and ranks a user’s payment instruments during checkout.
The engine applies device rules, line-of-business rules, transaction limits, and relevance-score sorting to return the best payment options.

# 🚀 Features

* Device-aware filtering (e.g., hide UPI if device doesn’t support it)
* LoB-based rules (Commerce, Credit Card Bill Pay, Investment)
* Transaction limit enforcement per payment type
* Smart ranking: LoB priority → relevance score
* Clean, extensible OOP design
* Automated test runner with multiple PASS/FAIL test cases

# 🧩 How It Works
1. Input: User, UserContext, Cart
2. Remove unsupported payment types (e.g., UPI when device disables UPI)
3. Apply LoB rules (allowed/blocked types)
4. Validate transaction limits
5. Sort remaining instruments:
   * LoB order
   * relevanceScore (descending)

6. Return ordered list of valid payment methods

# 📁 Project Structure
java/

  ├── Main/
  
         ├── InputMain.java
  ├── Module/
  
         ├── Cart.java
         ├── DeviceContext.java
         ├── PaymentInstrument.java
         ├── User.java
         ├── UserContext.java
         ├── DeviceContext.java
         ├── Cart.java
  ├── Service/
  
         ├── PaymentRecommendationService.java
         ├── MakeOrderService.java
         ├── paymentRange.java

# 🧪 Test Cases

InputMain.java includes automated tests for:
* UPI disabled
* LoB-based filtering
* Sorting by relevanceScore
* Limit-based removal
* Mixed rules
* Edge case: all instruments removed

Each test reports PASS / FAIL.

# 🛠 Requirements
* Java 8+
* No external libraries required

# ▶️ Run
* javac *.java
* java InputMain

🤝 Contributing

PRs welcome — feel free to add new LoBs, payment types, rule expansions, or more test cases.
