

import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

/*obstacle type, x, y is the center point position of the obstacle*/
public class obstacle {
    /*obstacle picture*/
    public BufferedImage image;
    public BufferedImage image1;
    /*obstacle picture x coordinate*/
    public int x;
    /*obstacle picture y coordinate*/
    public int y;
    /*obstacle image width*/
    public int width;
    /*obstacle picture height*/
    public int height;
    /*The gap between the upper and lower obstacles*/
    public int gap;
    /*Distance between two obstacles*/
    public int distance;

    /*Constructing a random function*/
    public Random random = new Random();

    /*Constructor: Initialize data, n represents the first few obstacles*/
    public obstacle(int n) throws Exception {
        image = ImageIO.read(getClass().getResource("obstacle1.png"));
        width = image.getWidth();
        height = image.getHeight();
        image1 = ImageIO.read(getClass().getResource("obstacle2.png"));
        width = image1.getWidth();
        height = image1.getHeight();
        gap = 144;
        distance = 245;
        x = 550+(n-1)*distance;
        y= random.nextInt(218)+132;
    }

    /*Add a move method to the column*/
    public void step() {
        x--;
        if(x==-width/2) {
            x=distance*2-width/2;
            y=random.nextInt(218)+132;
        }
    }

}
