import Utils.MicrowaveState;
import Utils.MicrowaveTime;
import controlP5.*;
import processing.core.PApplet;
import processing.sound.TriOsc;

public class MicrowaveApplet extends PApplet
{

    /* Global Variables */
    ControlP5 cp5;
    ControlFont buttonFont;
    MicrowaveTime microwaveTime;
    Group timeSetGroup;
    Group timeCookGroup;
    int currentTime_Cook;
    int currentTime_Sound;
    int powerLevel = 100;
    int wait = 1000;
    MicrowaveState microwaveState;
    TriOsc sound;
    boolean isSoundPlaying = false;
    boolean isTurnTableOn = true;

    /*----------------------- START Processing Methods -----------------------*/

    public void setup()
    {
        cp5 = new ControlP5(this);
        currentTime_Cook = millis(); //store the current time for the cook timer
        currentTime_Sound = millis(); //store the current time for the sound timer
        sound = new TriOsc(this);
        microwaveState = MicrowaveState.NOT_STARTED;
        microwaveTime = new MicrowaveTime();
        buttonFont = new ControlFont(createFont("Arial",20, true));
        setupTimeCook();
        setupTimeSet();
        timeCookGroup.hide();
        drawTimeSet();
    }

    // method used only for setting the size of the window
    public void settings()
    {
        size(560, 700);
    }

    public void draw()
    {
        //Sound Check
        if(isSoundPlaying && millis() - currentTime_Sound >= 150)
        {
            stopSound();
        }

        //check the difference between now and the previously stored time is greater than the wait interval
        if(millis() - currentTime_Cook >= wait)
        {

            if(microwaveState == MicrowaveState.STARTED)
            {
                microwaveTime.addSeconds(-1);
                drawTimeCook();
                currentTime_Cook = millis();
                if(microwaveTime.getSeconds() == 0)
                {
                    drawFinished();
                    microwaveState = MicrowaveState.FINISHED;
                }
            } else if(microwaveState == MicrowaveState.FINISHED)
            {
                stopButton(false);
            }
        }
    }
    /*----------------------- END Processing Methods -----------------------*/

    /*----------------------- START Utils -----------------------*/

    //Plays a sound a a specific frequency
    public void playSound(int freq)
    {
        currentTime_Sound = millis();
        sound.freq(freq);
        isSoundPlaying = true;
        sound.play();
    }

    //Stops playing sound
    public void stopSound()
    {
        isSoundPlaying = false;
        sound.stop();
    }

    /*----------------------- END Utils -----------------------*/

    /*----------------------- START Time Set Screen -----------------------*/

    public void setupTimeSet()
    {
        timeSetGroup = cp5.addGroup("timeSetGroup");

        cp5.addButton("minutesTensUpButton")
                .setLabel("+1")
                .setPosition(50,160)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
        cp5.addButton("minutesTensDownButton")
                .setLabel("-1")
                .setPosition(50,220)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
        cp5.addButton("minutesOnesUpButton")
                .setLabel("+1")
                .setPosition(170,160)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
        cp5.addButton("minutesOnesDownButton")
                .setLabel("-1")
                .setPosition(170,220)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
        cp5.addButton("secondsTensUpButton")
                .setLabel("+1")
                .setPosition(290,160)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
        cp5.addButton("secondsTensDownButton")
                .setLabel("-1")
                .setPosition(290,220)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
        cp5.addButton("secondsOnesUpButton")
                .setLabel("+1")
                .setPosition(410,160)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
        cp5.addButton("secondsOnesDownButton")
                .setLabel("-1")
                .setPosition(410,220)
                .setSize(100,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("quick1Min")
                .setLabel("1:00")
                .setPosition(50,340)
                .setSize(130,60)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("quick1HalfMin")
                .setLabel("1:30")
                .setPosition(215,340)
                .setSize(130,60)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("quick2Min")
                .setLabel("2:00")
                .setPosition(380,340)
                .setSize(130,60)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("powerUp")
                .setLabel("+10")
                .setPosition(240,480)
                .setSize(270,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("powerDown")
                .setLabel("-10")
                .setPosition(240,540)
                .setSize(270,40)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("startButton")
                .setLabel("Start")
                .setPosition(30,620)
                .setSize(500,60)
                .setColorBackground(0xFF189D25)
                .setColorForeground(0xFF158D21)
                .setColorActive(0xFF127E1D)
                .setGroup(timeSetGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
    }

    public void drawTimeSet()
    {
        background(0xFFFFFFFF);

        //Time Set Area
        {
            rect(30, 20, 500, 260);

            {
                updateMinuteTensValue();
            }

            textSize(14);
            fill(0);
            text("Minutes", 135, 155);
            fill(0xFFFFFFFF);
            textSize(12);

            {
                updateMinuteOnesValue();
            }

            textSize(32);
            fill(0);
            text(":", 276, 90);
            fill(0xFFFFFFFF);
            textSize(12);

            {
                updateSecondTensValue();
            }

            textSize(14);
            fill(0);
            text("Seconds", 375, 155);
            fill(0xFFFFFFFF);
            textSize(12);

            {
                updateSecondOnesValue();
            }
        }

        //Quick Time
        {
            rect(30, 300, 500, 120);
            textSize(20);
            fill(0);
            text("Quick Time", 225, 320);
            fill(0xFFFFFFFF);
            textSize(12);
        }

        //Power Level
        {
            rect(30, 440, 500, 160);
            textSize(20);
            fill(0);
            text("Power Level", 225, 460);
            fill(0xFFFFFFFF);
            textSize(12);

            rect(50, 480, 170, 100);
            {
                textSize(20);
                fill(0);
                text(powerLevel, 115, 535);
                fill(0xFFFFFFFF);
                textSize(12);
            }
        }

    }

    //Updates the time
    public void updateTimeLabels()
    {
        updateMinuteTensValue();
        updateMinuteOnesValue();
        updateSecondTensValue();
        updateSecondOnesValue();
    }

    public void updateMinuteTensValue()
    {
        rect(50, 30, 100, 100);
        textSize(32);
        fill(0);
        text(microwaveTime.getMinuteTens(), 90, 95);
        fill(0xFFFFFFFF);
        textSize(12);
    }

    public void updateMinuteOnesValue()
    {
        rect(170, 30, 100, 100);
        textSize(32);
        fill(0);
        text(microwaveTime.getMinuteOnes(), 210, 95);
        fill(0xFFFFFFFF);
        textSize(12);
    }

    public void updateSecondTensValue()
    {
        rect(290, 30, 100, 100);
        textSize(32);
        fill(0);
        text(microwaveTime.getSecondTens(), 330, 95);
        fill(0xFFFFFFFF);
        textSize(12);
    }

    public void updateSecondOnesValue()
    {
        rect(410, 30, 100, 100);
        textSize(32);
        fill(0);
        text(microwaveTime.getSecondOnes(), 450, 95);
        fill(0xFFFFFFFF);
        textSize(12);
    }

    public void startButton()
    {
        if(microwaveTime.getSeconds() > 0)
        {
            playSound(250);
            timeSetGroup.hide();
            timeCookGroup.show();
            microwaveState = MicrowaveState.STARTED;
            currentTime_Cook = millis();
            drawTimeCook();
        } else {
            playSound(75);
        }
    }

    public void quick1Min()
    {
        microwaveTime.reset();
        microwaveTime.addSeconds(60);
        startButton();
    }

    public void quick1HalfMin()
    {
        microwaveTime.reset();
        microwaveTime.addSeconds(90);
        startButton();
    }

    public void quick2Min()
    {
        microwaveTime.reset();
        microwaveTime.addSeconds(120);
        startButton();
    }

    public void powerUp()
    {
        if(powerLevel < 100)
        {
            playSound(250);
            powerLevel += 10;
            drawTimeSet();
        } else {
            playSound(75);
        }
    }

    public void powerDown()
    {
        if(powerLevel > 0)
        {
            playSound(250);
            powerLevel -= 10;
            drawTimeSet();
        } else {
            playSound(75);
        }
    }

    public void minutesTensUpButton()
    {
        playSound(250);
        microwaveTime.addSeconds(600);
        updateTimeLabels();
    }

    public void minutesTensDownButton()
    {
        playSound(250);
        microwaveTime.addSeconds(-600);
        updateTimeLabels();
    }

    public void minutesOnesUpButton()
    {
        playSound(250);
        microwaveTime.addSeconds(60);
        updateTimeLabels();
    }

    public void minutesOnesDownButton()
    {
        playSound(250);
        microwaveTime.addSeconds(-60);
        updateTimeLabels();
    }

    public void secondsTensUpButton()
    {
        playSound(250);
        microwaveTime.addSeconds(10);
        updateTimeLabels();
    }

    public void secondsTensDownButton()
    {
        playSound(250);
        microwaveTime.addSeconds(-10);
        updateTimeLabels();
    }

    public void secondsOnesUpButton()
    {
        playSound(250);
        microwaveTime.addSeconds(1);
        updateTimeLabels();
    }

    public void secondsOnesDownButton()
    {
        playSound(250);
        microwaveTime.addSeconds(-1);
        updateTimeLabels();
    }

    /*----------------------- END Time Set Screen -----------------------*/

    /*----------------------- START Time Cook Screen -----------------------*/

    public void setupTimeCook()
    {
        timeCookGroup = cp5.addGroup("timeCookGroup");

        cp5.addButton("add30SecondsButton")
                .setLabel("+30 Seconds")
                .setPosition(30,380)
                .setSize(500,100)
                .setGroup(timeCookGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("turntableButton")
                .setLabel("Turntable Status: On")
                .setPosition(30,500)
                .setSize(500,65)
                .setGroup(timeCookGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);

        cp5.addButton("stopButton")
                .setLabel("Stop")
                .setPosition(30,580)
                .setSize(500,100)
                .setColorBackground(0xFFDB3522)
                .setColorActive(0xFF9D2518)
                .setColorForeground(0xFFAF2A1B)
                .setGroup(timeCookGroup)
                .setFont(buttonFont)
                .getCaptionLabel().toUpperCase(false);
    }

    public void drawTimeCook()
    {
        background(0xFFFFFFFF);
        rect(30, 20, 500, 340);

        {
            textSize(100);
            fill(0);
            text(microwaveTime.toString(), 145, 215);
            fill(0xFFFFFFFF);
            textSize(12);
        }
    }

    public void add30SecondsButton()
    {
        playSound(250);
        microwaveTime.addSeconds(30);
        currentTime_Cook = millis();
        drawTimeCook();
    }

    public void turntableButton()
    {
        playSound(250);
        isTurnTableOn = !isTurnTableOn;
        cp5.getController("turntableButton")
                .setLabel("Turntable Status: " + (isTurnTableOn ? "On" : "Off"));
    }

    public void stopButton(boolean shouldPlaySound)
    {
        if(shouldPlaySound)
        {
            playSound(250);
        }
        isTurnTableOn = true;
        timeCookGroup.hide();
        timeSetGroup.show();
        microwaveState = MicrowaveState.NOT_STARTED;
        microwaveTime.reset();
        drawTimeSet();
    }

    /*----------------------- END Time Cook Screen -----------------------*/

    /*----------------------- START Finished Screen -----------------------*/

    public void drawFinished()
    {
        timeCookGroup.hide();
        background(0xFFFFFFFF);
        textSize(100);
        fill(0);
        text("Finished", 75, 260);
        fill(0xFFFFFFFF);
        textSize(12);
    }

    /*----------------------- END Finished Screen -----------------------*/
}
