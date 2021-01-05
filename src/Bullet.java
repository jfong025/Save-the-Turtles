// =============================================================================
/**
 * The bullet class is responsible for creating the bullets that the turtle shoots at oncoming obstacles.
 **/
// =============================================================================


// =============================================================================
// IMPORTS

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Math;
// =============================================================================


public class Bullet extends Character {
    // Keeps track of the width and height of the bullet
    int width;
    int height;
    //keeps track of the x and y position of the bullet
    Pair position;
    //Keeps track of x & y positions, velocities, and accelerations
    private Pair velocity;
    private Pair acceleration;
    //keeps track of the x and y position where the mouse clicked
    private Pair endposition;
    //The distance from the starting position to the endposition
    private double magnitude;
    //keeps track of whether or not the bullet is alive
    boolean alive;
    //loaded sprite of the bullet
    private BufferedImage imagebullet;
    // =============================================================================

    /**
     * The constructor, which creates the bullet at the turtle's position and velocity
     * heading towards the end position.
     *
     * @param turtlePosition This sets the objects position to the turtle's x and y position
     * @param endxpos        keeps track of the end position x value
     * @param endypos        keeps track of the end position y value
     **/
    // =============================================================================
    public Bullet(Pair turtlePosition, int endxpos, int endypos) {
        position = new Pair(turtlePosition.x + 32, turtlePosition.y + 20);
        double pathX = endxpos - position.x;
        double pathY = endypos - position.y;
        magnitude = Math.sqrt(Math.pow(pathX, 2) + Math.pow(pathY, 2));
        velocity = new Pair((pathX / magnitude) * 300, (pathY / magnitude) * 300);
        acceleration = new Pair(0, 0);
        endposition = new Pair((double) endxpos, (double) endypos);
        width = 10;
        height = 15;
        alive = true;

        try {
            imagebullet = ImageIO.read(new File("bullet.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // =============================================================================

    /**
     * The method updates the current bullet on the screen.
     *
     * @param time This value is used to move the bullet.
     **/
    // =============================================================================
    public void update(double time) {
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
        if (position.x == endposition.x && position.y == endposition.y) {
            velocity.x = 0;
            velocity.y = 0;
        }
    }
    // =============================================================================

    /**
     * Takes the image of the bullet and draws it at the current position
     *
     * @param g This is used to put images on the screen.
     **/
    // =============================================================================
    public void draw(Graphics g) {
        g.drawImage(imagebullet, (int) position.x, (int) position.y, 25, 25, null);
    }
}
