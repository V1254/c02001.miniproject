package Model;


import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DiceImage {
    private Image[] redImages = new Image[6];

    private Image[] blueImages = new Image[6];

    public DiceImage() {
        loadRedImages();
        loadBlueImages();
    }

//    public Image getImage(int face){
//        return images[face - 1];
//    }

    public void loadRedImages() {
        String location = "Images/red/";

        for (int i = 0; i < 6; i++) {
            redImages[i] = new Image(location + "dice" + (i + 1) + ".png");
        }


        System.out.println(redImages.length);

    }

    public void loadBlueImages(){
        String location = "Images/blue/";

        for (int i = 0; i < 6; i++) {
            blueImages[i] = new Image(location + "dice" + (i + 1) + ".png");
        }
    }

    public List<Image> getImages(String text, int... ints){
        List<Image> images = new ArrayList<>();

        if(text.equalsIgnoreCase("red")){
            Arrays.stream(ints).forEach(x -> images.add(getImage(redImages,x)));
        } else {
            Arrays.stream(ints).forEach(x -> images.add(getImage(blueImages,x)));
        }
        return images;

    }

    public Image getImage(Image[] images, int x){
        return images[x-1];
    }

    public Image getImage(String text, int x){
        if(text.equalsIgnoreCase("red")){
            return redImages[x-1];
        } else {
            return blueImages[x-1];
        }

    }
}
