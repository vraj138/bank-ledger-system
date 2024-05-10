package dev.codescreen.transactionservice.model;

public class Amount {
    private String amount;
    private String currency;
    private String debitOrCredit;

    public Amount(String amount, String currency, String debitOrCredit){
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }

    
    /** 
     * @return String
     */
    public String getAmount() {
        return amount;
    }

    
    /** 
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    
    /** 
     * @return String
     */
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    
}
