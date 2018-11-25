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

    private void loadRedImages() {
        String location = "Images/red/";
        for (int i = 0; i < 6; i++) {
            redImages[i] = new Image(location + "dice" + (i + 1) + ".png");
        }
    }

    private void loadBlueImages() {
        String location = "Images/blue/";

        for (int i = 0; i < 6; i++) {
            blueImages[i] = new Image(location + "dice" + (i + 1) + ".png");
        }
    }

    public List<Image> getImageList(String text, int... ints) {
        List<Image> images = new ArrayList<>();

        if (text.equalsIgnoreCase("red")) {
            Arrays.stream(ints).forEach(x -> images.add(getImage(redImages, x)));
        } else {
            Arrays.stream(ints).forEach(x -> images.add(getImage(blueImages, x)));
        }
        return images;

    }

    private Image getImage(Image[] images, int x) {
        return images[x - 1];
    }
}
