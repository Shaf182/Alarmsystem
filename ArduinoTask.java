import java.util.TimerTask;
import java.io.IOException;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import java.util.HashMap;
import java.util.Map;
public class ArduinoTask extends TimerTask {
    private IODevice a;
    private Pin myLed;
    private Pin myPot;
    private long sampledPotValue;
    private int theVariable;
    private Map<Long, Long> potentiometerReadings;

    static final int REDLED = 4;
    static final int THEPOT = 14;

    //constructor
    public ArduinoTask(int theVariable, IODevice a) throws IOException {
        this.potentiometerReadings = new HashMap<>();
        this.a = a;
        this.theVariable = theVariable;
        //setup LED
        this.myLed = a.getPin(REDLED);
        try{
            myLed.setMode(Pin.Mode.OUTPUT);

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        //Setup the pot
        this.myPot = a.getPin(THEPOT);
        try{
            myPot.setMode(Pin.Mode.ANALOG);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }






    }
    public long getPotValue(){
        return sampledPotValue;
    }

    public void addPotentiometerReading(long timestamp, long value) {
        potentiometerReadings.put(timestamp, value);
    }


    public Map<Long, Long> getPotentiometerReadings() {
        return potentiometerReadings;
    }


    @Override
    public void run() {

        sampledPotValue = myPot.getValue();


        if(theVariable == 1)
        {
            try {
                myLed.setValue(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            theVariable = 0;
        }
        else{
            try {
                myLed.setValue(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            theVariable = 0;
        }

    }
}
