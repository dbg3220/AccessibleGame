
import javax.speech.Central;
import javax.speech.synthesis.*;
import java.util.Locale;

/**
 * Public class to write text to and immediately hear the
 * audio of that text
 */
public class SpeechGen {

    /** The synthesizer to speak the text */
    private Synthesizer synthesizer;

    public SpeechGen(){
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
            "freetts.voices",
                "com.sun.speech.freetts.en.us"
                    + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                "com.sun.speech.freetts"
                    + ".jsapi.FreeTTSEngineCentral");

            synthesizer = Central.createSynthesizer(
                new SynthesizerModeDesc(Locale.US)
            );

            synthesizer.allocate();

            synthesizer.resume();
        } catch (Exception e){
            System.err.println("Error in creating a text to speech generator");
            System.exit(1);
        }
    }

    /**
     * Speaks a string of valid english text
     * @param s The string to speak
     */
    public void speak(String s){
        try{
        synthesizer.speakPlainText(s, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e){}
    }

    /**
     * Call this method when you are done using the synthesizer
     */
    public void close(){
        try{
            synthesizer.deallocate();
        } catch(Exception e){}
    }

    /**
     * FOR TESTING ONLY
     * @param args
     */
    public static void main(String[] args) {
        SpeechGen gen = new SpeechGen();
        for(int i = 2; i < 100; i++){
            gen.speak("I have " + i + " cookies");
        }
        gen.close();
    }
}
