package producer;

public class Company {
    private String company;
    int tradeNumber;
    String registeredName;

    public Company(String company, int tradeNumber, String registeredName) {
        this.company = company;
        this.tradeNumber = tradeNumber;
        this.registeredName = registeredName;
    }

    public String getCompany() {
        return company;
    }

    public int getTradeNumber() {
        return tradeNumber;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setTradeNumber(int tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }
}
