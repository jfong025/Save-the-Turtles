// =============================================================================
/**
 * The Controls class is responsible for detecting keyboard, mouse, and button
 * actions, creating the instance of world, running the game, updating the
 * graphics, and drawing the game.
 **/
// =============================================================================


// =============================================================================
// IMPORTS

import javax.swing.*;
import java.awt.*;
/*
used https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
 as a reference for ActionListener and JButton
 used https://docs.oracle.com/javase/tutorial/uiswing/examples/events/MouseEventDemoProject/src/events/MouseEventDemo.java and https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
as a reference
 */
import java.awt.event.*;
// =============================================================================


// =============================================================================
public class Controls extends JPanel implements KeyListener, MouseListener, ActionListener {
// =============================================================================


    // =========================================================================
    //The set width of the screen.
    public static final int WIDTH = 1024;
    //The set height of the screen.
    public static final int HEIGHT = 768;
    //The set frames displayed per second
    public static final int FPS = 60;
    /* Holds the instance of the world created in Controls and is used to access
    field and methods in the world
    */
    World world;
    /* Represents the creation of the screen and allows for the button to be
    added to the JFrame */
    JFrame frame;
    // The button displayed when the game is over
    JButton b;
    //Keeps track of when the game is paused
    boolean paused;
    //The background image when the game is run
    public Image backgroundImage;
    // =========================================================================

    /**
     * The constructor, which creates a new instance of controls that adds in
     * the KeyListener, adds in the MouseListener, creates the button, adds in
     * the ActionListener, sets the size of the window, uploads the background
     * image, allows for updating, and allows for multiple tasks to run at the
     * same time, and sets the game as not paused.
     *
     * @param frame This is used to add the button to the frame.
     **/
    public Controls(JFrame frame) {
        this.frame = frame;
        addKeyListener(this);
        addMouseListener(this);
        b = new JButton("Play Again!");
        b.addActionListener(this);
        setFocusable(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.repaint();
        world = new World(WIDTH, HEIGHT, 50);
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
        paused = false;
        try {
            backgroundImage = new ImageIcon("ocean.gif").getImage();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }
    // =========================================================================

    /**
     * Updates the game as a function of time
     **/
    class Runner implements Runnable {
        public void run() {
            while (true) {
                if (world.titleScreenTimer > 0) {
                    world.titleScreenTimer -= 1;
                }
                if (world.titleScreenTimer == 0) {
                    world.gameIsRunning = true;
                    world.titleScreenTimer = -1;
                }
                if (world.gameIsRunning && !paused) {
                    if (world.flashTimer > 0) {
                        world.flashTimer -= 5;
                    }
                    world.updateTurtle(1.0 / (double) FPS);
                    world.updateObstacles(1.0 / (double) FPS);
                    world.updateBullets(1.0 / (double) FPS);
                    world.updateScore(1.0 / (double) (FPS / 10));
                    world.updateHearts();
                }
                repaint();
                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
            }

        }

    }
    // =========================================================================

    /**
     * Detects a keypress action that can be turned into a unicode character
     *
     * @param e The current KeyEvent
     **/
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
    }
    // =========================================================================

    /**
     * Detects a keypress action on the keyboard and carries out any action
     * that corresponds with its pressed key, which include pausing the game or
     * moving the turtle.
     *
     * @param e The current KeyEvent
     **/
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        int currentspeed = world.speed;
        // move up
        if (c == 'w' || c == 'W') {
            if (world.player.position.y >= 0) {
                world.player.velocity.y = -currentspeed;
            }
        }
        // move down
        if (c == 's' || c == 'S') {
            if (world.player.position.y + world.player.height <= HEIGHT) {
                world.player.velocity.y = currentspeed;
            }
        }
        // move left
        if (c == 'a' || c == 'A') {
            if (world.player.position.x >= 0) {
                world.player.velocity.x = -currentspeed;
            }
        }
        // move right
        if (c == 'd' || c == 'D') {
            if (world.player.position.x + world.player.width <= WIDTH) {
                world.player.velocity.x = currentspeed;
            }
        }
        /*
        used https://stackoverflow.com/questions/15628617/how-to-call-a-method-after-the-space-bar-is-pressed-in-a-jtextarea
        as a reference for spacebar */
        // pause the game
        if (c == KeyEvent.VK_SPACE) {
            if (world.gameIsRunning) {
                if (paused) {
                    paused = false;
                } else {
                    paused = true;
                }
            }
        }
    }
    // =========================================================================

    /**
     * Detects a keyreleased action on the keyboard and stops the player when
     * the key is released
     *
     * @param e The current KeyEvent
     **/
    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'w' || c == 'W') {
            world.player.velocity.y = 0;
        }
        if (c == 's' || c == 'S') {
            world.player.velocity.y = 0;

        }
        if (c == 'a' || c == 'A') {
            world.player.velocity.x = 0;
        }
        if (c == 'd' || c == 'D') {
            world.player.velocity.x = 0;
        }
    }
    // =========================================================================

    /**
     * Allows for the JFrame to be displayed
     **/
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    // =========================================================================

    /**
     * Detects when the mouse has been clicked on the screen
     *
     * @param e The current MouseEvent
     **/
    public void mousePressed(MouseEvent e) {
    }
    // =========================================================================

    /**
     * Detects when the mouse is released on the screen
     *
     * @param e The current MouseEvent
     **/
    public void mouseReleased(MouseEvent e) {
    }
    // =========================================================================

    /**
     * Detects when the mouse enters the screen
     *
     * @param e The current MouseEvent
     **/
    public void mouseEntered(MouseEvent e) {
    }
    // =========================================================================

    /**
     * Detects when the mouse exits the screen
     *
     * @param e The current MouseEvent
     **/
    public void mouseExited(MouseEvent e) {
    }
    // =========================================================================

    /**
     * Detects when the mouse is clicked and keeps track of the coordinates,
     * which are used to locate the direction in which the bullet goes.
     *
     * @param e The current MouseEvent
     **/
    public void mouseClicked(MouseEvent e) {
        world.bullets.add(world.player.shoot(e.getX(), e.getY()));
    }
    // =========================================================================

    /**
     * Detects when the button has been pressed and restarts the game
     *
     * @param e The current ActionEvent
     **/
    public void actionPerformed(ActionEvent e) {
        world.restartGame();
    }
    // =========================================================================

    /**
     * Draws the game
     *
     * @param g This is used to put images on the screen.
     **/
    public void drawGame(Graphics g) {
        b.setVisible(false);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        world.drawBullets(g);
        world.drawTurtle(g);
        world.drawObstacles(g);
        world.drawHearts(g);
        g.setColor(Color.WHITE);
        Font font = new Font("Andale Mono Regular", Font.BOLD, 20);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        String score = "Score: " + (int) world.score + "  ";
        int w = fm.stringWidth(score);
        int h = fm.getAscent();
        g.drawString(score, WIDTH - 140, h * 2);
    }
    // =========================================================================

    /**
     * Draws red flashes on the screen when the turtle collides with the
     * harmful obstacles
     *
     * @param g This is used to put images on the screen.
     **/
    public void drawFlashes(Graphics g) {
        g.setColor(new Color(255, 0, 0, world.flashTimer));
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }
    // =========================================================================

    /**
     * Displays the word “PAUSED” when the player pauses the game by pressing
     * the spacebar
     *
     * @param g This is used to put images on the screen.
     **/
    public void drawPaused(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        int w = fm.stringWidth("PAUSED");
        int h = fm.getAscent();
        g.drawString("PAUSED", (WIDTH / 2) - (w / 2), (HEIGHT / 2) - h);
    }
    // =========================================================================

    /**
     * Draws the title screen right before the game is played, which includes
     * the title of the game and the instructions to play the game.
     *
     * @param g This is used to put images on the screen.
     **/
    public void drawTitleScreen(Graphics g) {
        /* used http://www.java2s.com/Code/Java/2D-Graphics-GUI/Drawcanvaswithcolorandtext.htm
        as reference for FontMetrics */
        FontMetrics fm = g.getFontMetrics();
        // draws background image
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        /* used https://www.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html
        as reference for fonts */
        // draws the title
        g.setColor(Color.WHITE);
        Font font = new Font("Andale Mono Regular", Font.BOLD, 75);
        g.setFont(font);
        String title = " SAVE THE TURTLES!";
        // draws the instructions
        int w = fm.stringWidth(title);
        int h = fm.getAscent();
        g.drawString(title, (WIDTH / 2) - (w * 4 / 2), (HEIGHT / 3) - (h));
        font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        String instructions1 = "Mr. Turtle is trying to navigate the perils of plastic waste and ";
        w = fm.stringWidth(instructions1);
        h = fm.getAscent();
        g.drawString(instructions1, (WIDTH / 2) - (w / 2), (HEIGHT * 3 / 5) - (h));
        String instructions2 = "the scary sharks as he swims through the ocean. Use the WASD ";
        w = fm.stringWidth(instructions2);
        g.drawString(instructions2, (WIDTH / 2) - (w / 2), (HEIGHT * 3 / 5));
        String instructions3 = "keys to move him up, left, down, and right, respectively. Click ";
        w = fm.stringWidth(instructions3);
        g.drawString(instructions3, (WIDTH / 2) - (w / 2), (HEIGHT * 3 / 5) + h);
        String instructions4 = "the mouse to shoot at his enemies. Collect the leaves, turtle ";
        w = fm.stringWidth(instructions4);
        h += fm.getAscent();
        g.drawString(instructions4, (WIDTH / 2) - (w / 2), (HEIGHT * 3 / 5) + h);
        String instructions5 = "food, to increase his score. Mr. Turtle has three lives. If ";
        w = fm.stringWidth(instructions5);
        h += fm.getAscent();
        g.drawString(instructions5, (WIDTH / 2) - (w / 2), (HEIGHT * 3 / 5) + h);
        String instructions6 = "needed, press the spacebar to pause the game.";
        w = fm.stringWidth(instructions6);
        h += fm.getAscent();
        g.drawString(instructions6, (WIDTH / 2) - (w / 2), (HEIGHT * 3 / 5) + h);
    }
    // =========================================================================

    /**
     * Draws the words “GAME OVER”, displays the ending score, and also
     * displays a button that says “Play Again!” when the game is over.
     *
     * @param g This is used to put images on the screen.
     **/
    public void drawGameOverScreen(Graphics g) {
        //displays the button
        b.setVisible(true);
        // draws "GAME OVER" words
        g.setColor(Color.WHITE);
        Font font = new Font("Andale Mono Regular", Font.BOLD, 100);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        String gameOver = "GAME OVER";
        int wGO = fm.stringWidth(gameOver);
        int hGO = fm.getAscent();
        g.drawString(gameOver, WIDTH / 2 - (wGO / 2), HEIGHT / 2 - (hGO));
        //draws the score
        String score = ("Your Score: " + (int) world.score);
        font = new Font("Andale Mono Regular", Font.PLAIN, 50);
        g.setFont(font);
        int w = fm.stringWidth(score);
        int h = fm.getAscent();
        g.drawString(score, WIDTH / 2 - (w / 4), HEIGHT / 2 + hGO);
        b.setFont(new Font("Arial", Font.BOLD, 25));
        b.setBounds((WIDTH / 2) - (150 / 2), HEIGHT * 2 / 3, 150, 50);
        // adds the button to the frame
        frame.add(b);
    }
    // =========================================================================

    /**
     * Draws all elements of the game
     *
     * @param g This is used to put images on the screen.
     **/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        if (paused) {
            drawPaused(g);
        }
        if (world.flashTimer > 0) {
            drawFlashes(g);
        }
        if (world.titleScreenTimer > 0) {
            drawTitleScreen(g);
        }
        if (world.gameOver()) {
            drawGameOverScreen(g);
        }
    }
}