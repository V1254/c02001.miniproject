package Model;

// Number of Faces on the Dice
public enum Faces {
    SIX(6),
    EIGHT(8),
    Ten(10),
    TWENTY(20);

    private int numberOfSides;

    Faces(int x){
        numberOfSides = x;
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }
}
