// =============================================================================
/**
 * The OceanAudio class is responsible for uploads and plays ocean sounds.
 **/
// =============================================================================


// =============================================================================
// IMPORTS

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
// =============================================================================


// =============================================================================
public class Heart extends Character {
    // The sprite image of the Heart
    private BufferedImage imageHeart;
    // =========================================================================

    /**
     * The constructor, which creates a new Heart instance with default
     * values.
     **/
    public Heart() {
        super();
    }
    // =========================================================================

    /**
     * The constructor, which creates a new Heart instance with default
     * values.
     **/
    public Heart(Pair position) {
        //set position to current position
        this.position = position;
        //Loads heart image
        try {
            imageHeart = ImageIO.read(new File("heart.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    // =========================================================================

    /**
     * Takes the image of the turtle and draws it at the current position
     *
     * @param g This is used to put images on the screen.
     **/
    public void draw(Graphics g) {
        // the width and height of the heart are the size of the image
        g.drawImage(imageHeart, (int) position.x, (int) position.y, 60, 60, null);
    }
}
