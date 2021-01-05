// =============================================================================
/**
 * The Turtle class is responsible for storing information pertaining to the
 * player, drawing the turtle, and creating the bullets that come out of the
 * turtle.
 **/
// =============================================================================


// =============================================================================
// IMPORTS

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
// =============================================================================


// =============================================================================
public class Turtle extends Character {
// =============================================================================


    // =========================================================================

    // The sprite image of the Turtle.
    private BufferedImage imageT;
    //Keeps track of x & y positions, velocities, and accelerations
    Pair position;
    Pair velocity;
    Pair acceleration;
    //Width and height of the hit boxes.
    int width;
    int height;
    // =========================================================================

    /**
     * The constructor, which creates a new turtle instance with default
     * values.
     **/

    public Turtle() {
        //Loads turtle image
        try {
            imageT = ImageIO.read(new File("turtleImage.png"));
        } catch (Exception e) {
        }

        //Sets turtle position to be at center bottom
        position = new Pair(Controls.WIDTH / 2.2, (double) (Controls.HEIGHT - 40) / 1.2);
        velocity = new Pair(0, 0);
        acceleration = new Pair(0, 0);
        width = 70;
        height = 70;
    }
    // =========================================================================

    /**
     * Updates the turtle's position based on time
     *
     * @param time This value is used to update the turtle's position.
     **/
    public void update(double time) {
        // Prevents the turtle from going off the screen
        if (position.x <= 0) {
            position.x = 0;
        }
        if (position.x >= Controls.WIDTH - width) {
            position.x = Controls.WIDTH - width;
        }
        if (position.y >= Controls.HEIGHT - height) {
            position.y = Controls.HEIGHT - height;
        }
        if (position.y <= 0) {
            position.y = 0;
        }
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
    }
    // =========================================================================

    /**
     * Creates a new bullet
     *
     * @param finalxpos This value is used to create the x vector of the bullet.
     * @param finalypos This value is used to create the y vector of the bullet.
     **/
    public Bullet shoot(int finalxpos, int finalypos) { // move to bullets class
        //uses turtle's position to set the initial position of the bullet
        Bullet newBullet = new Bullet(position, finalxpos, finalypos);
        return newBullet;
    }
    // =========================================================================

    /**
     * Takes the image of the turtle and draws it at the current position
     *
     * @param g This is used to put images on the screen.
     **/
    public void draw(Graphics g) {
        // the width and height of the turtle are the size of the image
        // this is different from the width and height fields, which correspond
        // to the hit boxes.
        g.drawImage(imageT, (int) position.x, (int) position.y, 80, 80, null);
    }
}

