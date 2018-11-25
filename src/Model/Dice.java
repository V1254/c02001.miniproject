package Model;

import java.util.Random;

public class Dice {
    private Random random;
    private Faces sides;

    public Dice() {
        this(Faces.SIX);
    }

    public Dice(Faces faces) {
        this.sides = faces;
        this.random = new Random();
    }

    /**
     * Generates and returns a random value between 1 and number of sides on the Dice.
     * @return
     */

    public int roll() {
        return this.random.nextInt(getSides()) + 1;
    }

    public int getSides() {
        return sides.getNumberOfSides();
    }
}
