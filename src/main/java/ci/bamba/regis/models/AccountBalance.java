package ci.bamba.regis.models;

public class AccountBalance {

    private float availableBalance;
    private String currency;

    /**
     * @return the available balance
     */
    public float getAvailableBalance() {
        return availableBalance;
    }

    /**
     * @param availableBalance the available balance to set
     */
    public void setAvailableBalance(float availableBalance) {
        this.availableBalance = availableBalance;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }


}