// =============================================================================
/**
 * The World class is responsible for storing any information that is drawn
 * on screen, detecting interactions between graphical objects, and tracking changes that
 * are shown in the game.
 **/
// IMPORTS
// =============================================================================

import java.util.LinkedList;
import java.awt.*;
import java.util.Random;
// =============================================================================

public class World {
    //width and height of the screen
    private int height;
    private int width;
    //number of obstacles in the game at once
    private int numObstacles;
    //detects the number of collisions for the turtle
    private int numCollisions;
    //speed of the turtle
    int speed;
    //number of lives for the player
    private int numLives;
    //keeps track of current score
    double score;
    //keeps track of the game's status
    boolean gameIsRunning;
    //timer for damage flash length
    int flashTimer;
    //timer for title screen length
    int titleScreenTimer;
    //data structure for all obstacles in the game
    private LinkedList<Obstacles> obstacles;
    //stores an instance of the turtle
    Turtle player;
    //data structure for all bullet instances in the game
    LinkedList<Bullet> bullets;
    //data structure for the number of lives the player has, represented by hearts
    private MyGenericDS<Heart> hearts;
    /*
    World class (Responsible for movement of player and storing variables behind the board/screen
     */
// ============================================================================================

    /**
     * The constructor, which creates a new world instance with the number of obstacles,
     * height, and width of the screen. The obstacles, turtle, and lives are initialized to
     * their default values.
     *
     * @param initHeight       height of the screen
     * @param initWidth        width of the screen
     * @param initNumObstacles number of obstacles in the game
     **/
    public World(int initWidth, int initHeight, int initNumObstacles) {
        width = initWidth;
        height = initHeight;
        Random r = new Random();
        numObstacles = initNumObstacles;
        player = new Turtle();
        speed = 100;
        obstacles = new LinkedList<Obstacles>();
        for (int i = 0; i < numObstacles; i++) {
            //characters are added to the list and randomly initialized to their default position.
            obstacles.add(new Obstacles(new Pair(r.nextInt(width - 100), -125 + r.nextInt(height) - (height - 40 / 1.3)),
                    new Pair(0, 100)));
        }
        bullets = new LinkedList<Bullet>();
        numLives = 3;
        hearts = new MyGenericDS<Heart>();
        for (int k = 0; k < numLives; k++) {
            hearts.append((new Heart((new Pair(825 - (30 * k), -7)))));
        }
        gameIsRunning = false;
        titleScreenTimer = Controls.FPS * 10;
    }
// =============================================================================

    /**
     * This method initially draws the bullet sprites
     *
     * @param g This value is used to draw the bullets.
     **/
    public void drawBullets(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
    }

    /**
     * The method initially draws the turtle sprite to the screen
     *
     * @param g This value is used to draw the turtle.
     **/
    public void drawTurtle(Graphics g) {
        player.draw(g);
    }

    /**
     * The method initially draws the obstacle sprites to the screen
     *
     * @param g This value is used to draw the obstacles.
     **/
    public void drawObstacles(Graphics g) {
        if (obstacles == null) {
            return;
        }
        for (Obstacles o : obstacles) {
            o.draw(g);
        }
    }

    /**
     * The method initially draws the heart sprites to the screen
     *
     * @param g This value is used to draw the hearts.
     **/
    public void drawHearts(Graphics g) {
        for (int i = 0; i < hearts.length(); i++) {
            hearts.get(i).draw(g);
        }
    }

    /**
     * The method updates the obstacle sprites on the screen,
     * moving the obstacles.
     *
     * @param time This value is used to update the obstacles.
     **/
    public void updateObstacles(double time) {
        Random r = new Random();
        //counts the number of obstacles that should be removed
        int deathcount = 0;
        if (obstacles == null) {
            return;
        }
        for (Obstacles o : obstacles) {
            if (turtleCollisions(o)) {
                if (o.isFood) {
                    //if the turtle collides with food it increases the score and does not reduce lives
                    score += 100;
                    o.alive = false;
                    deathcount++;
                } else {
                    o.alive = false;
                    deathcount++;

                    numCollisions++;
                    flashTimer = Controls.FPS * 3;
                    if (numLives > 0) {
                        numLives--;
                    }
                }
            }
            if (o.position.y + o.height > height) {
                o.alive = false;
                deathcount++;
            }
        }
        //loops through a copy of the list rather than the original list
        for (Obstacles o : new LinkedList<>(obstacles)) {
            if (!o.alive) {
                //allows you to remove the list
                obstacles.remove(o);
            }
        }
        for (int i = 0; i < deathcount; i++) {
            //respawns the obstacles that were removed because of bulletcollisions, turtlecollisions, or leaving the screen.
            obstacles.add(new Obstacles(new Pair(r.nextInt(width) - 100, -125),
                    new Pair(0, 100.0 + ((((Math.random() * 100)) * (score / 500.0))))));
        }
        for (Obstacles o : obstacles) {
            o.update(time);
        }

    }

    /**
     * The method updates the score on the screen.
     *
     * @param time This value is used to add to the score.
     **/
    public void updateScore(double time) {
        score += time;
    }

    /**
     * The method updates the turtle on the screen.
     *
     * @param time This value is used to move the turtle.
     **/
    public void updateTurtle(double time) {
        player.update(time);
    }

    /**
     * The method updates the bullets on the screen.
     *
     * @param time This value is used to move the bullets.
     **/
    public void updateBullets(double time) {
        Random r = new Random();
        if (bullets == null) {
            return;
        }
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).position.x >= width || bullets.get(i).position.y <= 0 || bullets.get(i).position.y >= height || bullets.get(i).position.x <= 0) {
                bullets.get(i).alive = false;
            }
            for (Obstacles o : new LinkedList<>(obstacles)) {
                if (bullets.size() >= 1 && !o.isFood) {
                    if (bulletCollisions(bullets.get(i), o)) {
                        bullets.get(i).alive = false;
                        o.alive = false;
                        obstacles.add(new Obstacles(new Pair(r.nextInt(width) - 100, -125),
                                new Pair(0, 100.0 + ((((Math.random() * 100)) * (score / 500.0))))));

                    }
                }
            }
        }
        for (Bullet bullet : new LinkedList<>(bullets)) {
            if (!bullet.alive) {
                bullets.remove(bullet);
            }
        }
        for (Bullet bullet : new LinkedList<>(bullets)) {
            bullet.update(time);
        }
    }

    /**
     * The method updates the number of hearts on the screen.
     **/
    public void updateHearts() {
        if (hearts.length() > numLives) {
            hearts.pop();
        }
    }

    /**
     * The method checks whether or not the turtle is colliding with the obstacles.
     *
     * @param obstacle This is the current object that this is checking
     * @return returns whether or not the turtle is colliding
     **/
    //Algorithm was referenced from the pseudo-code in the following post.
    // https://stackoverflow.com/questions/31022269/collision-detection-between-two-rectangles-in-java
    public boolean turtleCollisions(Obstacles obstacle) {
        Rectangle obstaclebound = obstacle.getBoundaries();
        //If one of these conditions are true then it is impossible to be overlapping with that object.
        if ((player.position.x + player.width < obstaclebound.x)
                || (obstaclebound.x + obstaclebound.width < player.position.x)
                || (obstaclebound.y + obstaclebound.height < player.position.y)
                || (player.position.y + player.height < obstaclebound.y)) {
            return false;
        }

        return true;
    }

    /**
     * The method checks whether or not the current bullet is colliding with the obstacle.
     *
     * @param obstacle This is the current obstacle that is being checked
     * @param b        This is the current bullet that is being checked
     * @return returns whether or not the bullet is colliding
     **/

    public boolean bulletCollisions(Bullet b, Obstacles obstacle) {
        Rectangle obstaclebound = obstacle.getBoundaries();
        if (b == null) {
        } else if ((b.position.x + b.width < obstaclebound.x)
                || (obstaclebound.x + obstaclebound.width < b.position.x)
                || (obstaclebound.y + obstaclebound.height < b.position.y)
                || (b.position.y + b.height < obstaclebound.y)) {
            return false;
        }
        return true;
    }

    /**
     * The method checks whether or not the game is over.
     *
     * @return returns whether or not the game is over.
     **/

    public boolean gameOver() {
        if (numCollisions >= 3) {
            flashTimer = 0;
            gameIsRunning = false;
            return true;
        }
        return false;
    }

    /**
     * The method resets the world to its default values
     * and restarts the game.
     **/
    public void restartGame() {
        titleScreenTimer = Controls.FPS * 5;
        player.position = new Pair(Controls.WIDTH / 2.2, (double) (Controls.HEIGHT - 40) / 1.2);
        score = 0;
        numLives = 3;
        numCollisions = 0;
        hearts = new MyGenericDS<Heart>();
        for (int i = 0; i < numLives; i++) {
            hearts.append((new Heart((new Pair(825 - (30 * i), -7)))));
        }
        bullets = new LinkedList<Bullet>();
        Random r = new Random();
        obstacles = new LinkedList<Obstacles>();
        for (int i = 0; i < numObstacles; i++) {
            obstacles.add(new Obstacles(new Pair(r.nextInt(width - 100), -125 + r.nextInt(height) - (height - 40 / 1.3)),
                    new Pair(0, 100)));
        }
    }


}




