// =============================================================================
/**
 * The Main class is responsible combining all elements of the game including
 * the graphics and the audio.
 **/
// =============================================================================


// =============================================================================
// IMPORTS

import javax.swing.JFrame;
import java.util.Scanner;
// =============================================================================


// =============================================================================


public class Main {
    // =============================================================================


    // =========================================================================

    //the creation of the screen
    private static JFrame frame;

    public static void main(String[] args) {
        //creates a new screen
        frame = new JFrame("SaveTheTurtles!!");
        //creates a new instance of oceanAudio and plays the audio file
        try {
            OceanAudio audioPlayer = new OceanAudio();

            audioPlayer.play();
            Scanner sc = new Scanner(System.in);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //closes the screen when the exit is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //creates a new instance of controls
        Controls mainInstance = new Controls(frame);
        //set screen to the maininstance
        frame.setContentPane(mainInstance);
        //sizes the frame so that all its contents are at or above their preferred sizes
        frame.pack();
        //makes the frame visible
        frame.setVisible(true);

    }

}

