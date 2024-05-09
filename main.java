import java.util.Timer;
import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.*;
import java.io.IOException;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.ssd1306.MonochromeCanvas;
import jm.music.data.*;
import jm.util.Play;
import jm.constants.*;
import edu.princeton.cs.introcs.StdDraw;





public class main {
    static final String USBPORT = "/dev/cu.usbserial-0001";
    static final int REDLED = 4;
    static final int THEPOT = 14;
    static final int BUZZER = 5;
    static final int BUTTON = 6;


    public static void main(String[] args) throws IOException, InterruptedException {
        int sample = 1;

        IODevice a = new FirmataDevice(USBPORT);
        a.start();
        a.ensureInitializationIsDone();

        I2CDevice i2cObject = a.getI2CDevice((byte) 0x3C);
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);

        theOledObject.init();

        theOledObject.getCanvas().write("Welcome to Shaf's House");
        theOledObject.display();



        Pin myLed = a.getPin(REDLED);
        myLed.setMode(Pin.Mode.OUTPUT);

        Pin myBuzzer = a.getPin(BUZZER);
        myBuzzer.setMode(Pin.Mode.OUTPUT);

        Pin myButton = a.getPin(BUTTON);


        Thread.sleep(2000);

        myLed.setValue(0);


        var myTask = new ArduinoTask(0, a);
        new Timer().schedule(myTask,0,1000);


        StdDraw.setXscale(-3,100);
        StdDraw.setYscale(-30,1100);

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.CYAN);


        StdDraw.line(0,0,0,1000);
        StdDraw.line(0,0,100,0);


        StdDraw.text(50,-20,"[x]");
        StdDraw.text(-3,500,"[y]");
        StdDraw.text(50,1100,"Movement of Door");




        while(true) {

            System.out.println("the pot value is:" + myTask.getPotValue());

            StdDraw.text(sample,(double)myTask.getPotValue(),"*");
            Thread.sleep(500);



            if(myTask.getPotValue() > 80 && sample < 100)
            {
                myLed.setValue(1);
                myBuzzer.setValue(1);
                theOledObject.clear();
                theOledObject.getCanvas().write("Motion Detected!!!");
                theOledObject.display();
                sample++;

                while (true) {
                    Play.midi(new Note(Pitches.A5, RhythmValues.QUARTER_NOTE));
                }


            }
            else if (myTask.getPotValue() <= 500){
                myLed.setValue(0);
                myBuzzer.setValue(0);

            }


        }



    }

}
