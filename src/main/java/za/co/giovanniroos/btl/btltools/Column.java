package za.co.giovanniroos.btl.btltools;

public enum Column {
    ID("ID", 1, 1),
    DATE("Date", 2,2),
    ACCOUNT("Amount", 3,3),
    TX_TYPE("TX Type", 4,4),
    AMOUNT("Amount", 5,5),
    AMOUNT_UNIT("Amount Unit", 6,6),
    FEE("Fee", 8,7),
    FEE_UNIT("Fee Unit", 8,8),
    STATUS("Status", 9,9),
    BALANCE_BTL("Balance BTL", 10,10),
    BALANCE_BTC("Balance BTC", 11,11),
    TX_INFO("TX Info", 12,12),
    PLAN_NAME("Plan Name", 13,13),
    PARTNER_LEVEL("Partner Level", 14,14),
    PARTNER_ID("Partner ID",15,15),
    BONUS_PERS("Bonus %", 7,16),
    WEAK_LEG_VOLUME("Weak Leg Volume", 17,17),
    PLAN_ID("Plan ID", 18,18),
    ADDRESS("Address", 19,19);

    private String name;
    private int readIndex;
    private int writeIndex;

    Column(String name, int readIndex, int writeIndex) {
        this.name = name;
        this.readIndex = readIndex;
        this.writeIndex = writeIndex;
    }

    public String getName() {
        return name;
    }

    public int getReadIndex() {
        return readIndex;
    }

    public int getWriteIndex() {
        return writeIndex;
    }
}
