// =============================================================================

/**
 * The Pair class is a primitive data structure that holds a pair of x and y values
 **/
// =============================================================================
class Pair {
    //holds x and y values
    public double x;
    public double y;
// =============================================================================

    /**
     * The Constructor, which creates a pair and assigns two doubles to the x
     * and y values
     *
     * @param initX The X value of the pair
     * @param initY The Y value of the pair
     **/
// =============================================================================
    public Pair(double initX, double initY) {
        x = initX;
        y = initY;
    }
// =============================================================================

    /**
     * This method adds two pairs together, combining their respective x and y values.
     *
     * @param toAdd The pair that will be added
     * @return Pair     Returns the combined pair
     **/
// =============================================================================
    public Pair add(Pair toAdd) {
        return new Pair(x + toAdd.x, y + toAdd.y);
    }
// =============================================================================

    /**
     * This method multiplies a value to a pair. It takes the x value of the
     * original pair and multiplying it with the value, and the same for the y values.
     *
     * @param val The value that is multiplied
     * @return Pair     Returns the new pair
     **/
// =============================================================================
    public Pair times(double val) {
        return new Pair(x * val, y * val);
    }
}
// =============================================================================

/**
 * The character class is the superclass of all graphical objects on the screen
 * that interact with the game. (Heart, Obstacles, etc.)
 **/
// =============================================================================

public class Character {
    //Keeps track of x & y positions, velocities, and accelerations
    Pair position;
    Pair velocity;
    Pair acceleration;
    //Keeps track of the character's width and height
    int width;
    int height;
    // =============================================================================

    /**
     * The constructor, which serves as a framework for its children classes.
     **/
    // =============================================================================
    public Character() {
        position = new Pair(0, 0);
        velocity = new Pair(0, 0);
        acceleration = new Pair(0, 0);
        width = 0;
        height = 0;
    }
    // =============================================================================

    /**
     * The method updates the character sprites on the screen,
     * moving the obstacles.
     *
     * @param time This value is used to update the character.
     **/
    // =============================================================================
    public void update(double time) {
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
    }

}
