
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Bird {
    /*bird picture*/
    public BufferedImage image;
    /*bird picture x coordinate*/
    public int x;
    /*bird picture y coordinate*/
    public int y;
    /*bird picture width*/
    public int width;
    /*bird picture height*/
    public int height;
    /*size of picture for collision test, this size means square side length*/
    public int size;

    /*Add attributes to the Bird class to calculate the position of the bird and achieve the bird's throw*/
    public double g;//Gravity acceleration
    public double t;//Interval between two locations
    public double v0;//Initial throwing speed
    public double speed;//Current throwing speed
    public double s;//Displacement after time t

    /*Defines a set of pictures that are animation frames of birds*/
    public BufferedImage[] images;
    /*Defines the subscript of an element in an animation frame array*/
    public int index;
    /*Define inclination variable*/
    public double alpha;//It's the tilt of the bird, a unit of radian


    /*Initialize the property variable of Bird*/
    public Bird() throws Exception {
        image = ImageIO.read(getClass().getResource("bird1.png"));
        width = image.getWidth();
        height = image.getHeight();
        x = 132;
        y = 280;
        size = 40;
        /*Initialize the attributes that are added to the move*/
        g=6;
        v0=20;
        t=0.25;
        speed=v0;
        s=0;

        /*Create an image array with eight elements*/
        images=new BufferedImage[8];
        /*Set specific images for each image*/
        for(int i=0;i<8;i++) {
            images[i]=ImageIO.read(getClass().getResource("bird"+i+".png"));
        }
        /*The initial index is 0*/
        index=0;
        /*Initialization dip*/
        alpha=0;
    }
    /*Add bird movement method*/
    public void step() {
        double v0=speed;
        s=v0*t+g*t*t/2;//Calculate the displacement of the throwing movement
        y=y-(int)s;//Calculate the coordinate position of the bird
        double v=v0-g*t;//Calculate the next speed
        speed=v;
        /*Calculate inclination: take advantage of the methods provided by the Java API*/
        alpha=Math.atan(s/8);
    }
    /*Add bird flapping method*/
    public void fly() {
        index++;
        image=images[(index/12)%8];
    }

    /*Reset the bird's initial speed and fly upwards again*/
    public void flappy() {
        speed = v0;
    }
    /*Collision ground detection method*/
    public boolean hit(int a) {
        boolean hit =y+size/2>a;
        if(hit) {
            /*Place the bird on the ground*/
            y=a-size/2;
            /*The effect of causing a bird to fall to the ground*/
            alpha= -3.14159265358979323/2;
        }
        return hit;
    }

    /* Collision column detection method */
    public boolean hit(obstacle  column) {
        /* Check if it is within the column (outside the column) */
        if (x > column.x - column.width / 2 - size / 2 && x < column.x + column.width / 2 + size / 2) {
            /* Check if it is in the crack of the column*/
            if (y > column.y - column.gap / 2 + size / 2 &&
                    y < column.y + 	column.gap / 2 - size / 2) {
                return false;
            }
            return true;
        }
        return false;
    }
}
