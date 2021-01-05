// =============================================================================
/**
 * The Obstacle class is responsible for creating the obstacles drawn
 * on screen, and changing the image sprites of the obstacle.
 **/
// IMPORTS
// =============================================================================

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// =============================================================================
public class Obstacles extends Character {
    //loaded sprites of the obstacles
    private BufferedImage imageStraw;
    private BufferedImage imagePlastic;
    private BufferedImage imageShark;
    private BufferedImage imageFood;
    //the image that will be displayed on the screen
    private Image image;
    //width and height of the obstacle hitbox
    int width;
    int height;
    //shows whether or not the object is alive
    boolean alive;
    //tracks if the obstacle should help or harm the player
    boolean isFood;
    // =============================================================================

    /**
     * The constructor from the character parent class.
     **/
    // =============================================================================
    public Obstacles() {
        super();
    }
    // =============================================================================

    /**
     * The constructor, which randomly determines whether or not it is a helpful
     * obstacle like food food or an obstacle that can hurt the turtle, like sharks.
     *
     * @param position This sets the objects position to an x and y value
     * @param v        This sets the objects velocity to a fixed value.
     **/
    // =============================================================================
    public Obstacles(Pair position, Pair v) {

        try {
            imageStraw = ImageIO.read(new File("straw.png"));

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            imagePlastic = ImageIO.read(new File("plastic.png"));

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            imageShark = ImageIO.read(new File("shark.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            imageFood = ImageIO.read(new File("food.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        width = 80;
        height = 80;
        this.position = position;
        this.velocity = v;
        Image[] obstacleImage = new Image[]{imageStraw, imagePlastic, imageShark};
        //randomly choosing what sprite to display
        int index = (int) (Math.random() * (obstacleImage.length));
        image = obstacleImage[index];
        isFood = false;
        //randomly choosing if this obstacle will be a food obstacle
        if (((int) (Math.random() * 100)) == 50) {
            image = imageFood;
            isFood = true;
        }

        alive = true;
    }
    // =============================================================================

    /**
     * The method updates the individual obstacle sprite,
     * moving the obstacles.
     *
     * @param time This value is used to update the obstacle.
     **/
    // =============================================================================
    public void update(double time) {
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
    }
    // =============================================================================

    /**
     * The method initially draws the specific sprite that is associated
     * with the obstacle
     *
     * @param g This value is used to draw the obstacle.
     **/
    // =============================================================================
    public void draw(Graphics g) {
        // Sets the hit boxes to fit the image
        if (image == imageStraw) {
            width = 30;
            height = 60;
            g.drawImage(image, (int) position.x, (int) position.y, 80, 80, null);
        } else if (image == imagePlastic) {
            width = 55;
            height = 40;
            g.drawImage(image, (int) position.x, (int) position.y, 80, 80, null);
        } else if (image == imageShark) {
            width = 100;
            height = 95;
            g.drawImage(image, (int) position.x, (int) position.y, 150, 150, null);
        } else {
            width = 50;
            height = 50;
            g.drawImage(image, (int) position.x, (int) position.y, 60, 60, null);
        }

    }
    // =============================================================================

    /**
     * The method returns a rectangle that corresponds with the
     * hitbox of the current obstacle.
     *
     * @return Rectangle   This value is used to detect obstacle collisions.
     **/
    // =============================================================================
    public Rectangle getBoundaries() {
        return new Rectangle((int) position.x, (int) position.y, width, height);
    }
}
