import java.util.Random;

public class Dice {
    private Random random;
    private Faces sides;

    public Dice(){
        // default will be 6 sides
        this(Faces.SIX);
    }

    public Dice(Faces faces){
        this.sides = faces;
        this.random = new Random();
    }

    public int roll(){
        return this.random.nextInt(getSides()) + 1;
    }

    public int getSides(){
        return sides.getNumberOfSides();
    }


}
