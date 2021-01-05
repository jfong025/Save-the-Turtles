// =============================================================================
/**
 * The OceanAudio class is responsible for Uploads and plays ocean sounds.
 **/
// =============================================================================


// =============================================================================
// IMPORTS

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
// =============================================================================


// =============================================================================
public class OceanAudio {
    // creates Clip variable
    Clip clip;
    // creates AudioInputStream variable
    AudioInputStream audioInputStream;
    // =========================================================================

    /**
     * The constructor, which creates a new OceanAudio instance with default
     * values.
     **/

    public OceanAudio()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        // creates AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File("oceanAudio.wav").getAbsoluteFile());

        // creates clip reference
        clip = AudioSystem.getClip();

        // opens audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    // =========================================================================

    /**
     * Starts playing the .wav file.
     **/
    public void play() {
        //starts the clip
        clip.start();

    }

}


