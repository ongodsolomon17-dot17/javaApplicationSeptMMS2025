package chapter10;

public class PieceWorker extends Employee {
    private double wage; // Wage per piece
    private int pieces;  // Number of pieces produced

    public PieceWorker(String firstName, String lastName, String socialSecurityNumber, 
                       Date birthDate, double wage, int pieces) {
        super(firstName, lastName, socialSecurityNumber, birthDate);
        setWage(wage);
        setPieces(pieces);
    }

    public void setWage(double wage) {
        if (wage < 0.0) {
            throw new IllegalArgumentException("Wage per piece must be >= 0.0");
        }
        this.wage = wage;
    }

    public double getWage() { 
        return wage; 
    }

    public void setPieces(int pieces) {
        if (pieces < 0) {
            throw new IllegalArgumentException("Pieces produced must be >= 0");
        }
        this.pieces = pieces;
    }

    public int getPieces() { 
        return pieces; 
    }

    @Override
    public double earnings() {
        return getWage() * getPieces(); // Calculates earnings based on merchandise produced [cite: 17]
    }

    @Override
    public String toString() {
        return String.format("PieceWorker: %s%nWage per piece: $%,.2f; Pieces produced: %d", 
            super.toString(), getWage(), getPieces());
    }
}