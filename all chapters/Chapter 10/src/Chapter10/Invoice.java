package chapter10;

public class Invoice implements Payable { // 
    private String partNumber;
    private String partDescription;
    private int quantity;
    private double pricePerItem;

    public Invoice(String partNumber, String partDescription, int quantity, double pricePerItem) {
        this.partNumber = partNumber;
        this.partDescription = partDescription;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }

    @Override
    public double getPaymentAmount() {
        return quantity * pricePerItem; // 
    }

    @Override
    public String toString() {
        return String.format("Invoice: Part %s (%s)%nQuantity: %d, Price: $%,.2f", 
            partNumber, partDescription, quantity, pricePerItem);
    }
}