package Model;


import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiceImage {
    private Image[] redImages = new Image[6];

    private Image[] blueImages = new Image[6];

    public DiceImage() {
        loadRedImages();
        loadBlueImages();
    }

    /**
     * Populates redImages array with the red dice images under Images/red.
     */

    private void loadRedImages() {
        for (int i = 0; i < 6; i++) {
            redImages[i] = new Image(getClass().getResourceAsStream("/Images/red/dice" + (i+1) + ".png"));
        }
    }

    /**
     * Populates blueImages array with the blue dice images under Images/blue.
     */

    private void loadBlueImages() {
        for (int i = 0; i < 6; i++) {
            blueImages[i] = new Image(getClass().getResourceAsStream("/Images/blue/dice" + (i+1) + ".png"));
        }
    }

    /**
     * Returns a List of dice images.
     * @param Color The color of the dice (red or blue).
     * @param diceRolls an Array of each individual dice roll.
     * @return List of images based on the value of each diceRoll and the Color.
     */

    public List<Image> getImageList(String Color, int... diceRolls) {
        List<Image> images = new ArrayList<>();
        if (Color.equalsIgnoreCase("red")) {
            Arrays.stream(diceRolls).forEach(x -> images.add(getImage(redImages, x)));
        } else {
            Arrays.stream(diceRolls).forEach(x -> images.add(getImage(blueImages, x)));
        }
        return images;
    }

    /**
     * Returns the corresponding image based on passed in x value.
     * @param images
     * @param x
     * @return
     */

    private Image getImage(Image[] images, int x) {
        return images[x - 1];
    }
}
