package Utils;

public class MicrowaveTime
{
    // Limits for the different units of the time
    private final int LIMIT_1 = 10;
    private final int LIMIT_2 = 6;
    private final int LIMIT_3 = 10;
    private final int LIMIT_4 = 10;

    private int numberOfSeconds = 0;

    @Override
    public String toString()
    {
        return String.format("%d%d:%d%d", getMinuteTens(), getMinuteOnes(), getSecondTens(), getSecondOnes());
    }

    private int calculateUnit(int seconds, int iterativeLimit, int modLimit)
    {
        return Math.floorMod(Math.floorDiv(seconds, iterativeLimit), modLimit);
    }

    private int calculateTimeUnit(int seconds, int iterativeLimit, int modLimit)
    {
        return calculateUnit(seconds, iterativeLimit, modLimit) * (iterativeLimit);
    }

    public void addSeconds(int secondsToAdd)
    {
        numberOfSeconds = calculateTimeUnit(numberOfSeconds + secondsToAdd, LIMIT_3 * LIMIT_2 * LIMIT_1, LIMIT_4)
                + calculateTimeUnit(numberOfSeconds + secondsToAdd, LIMIT_2 * LIMIT_1, LIMIT_3)
                + calculateTimeUnit(numberOfSeconds + secondsToAdd, LIMIT_1, LIMIT_2)
                + calculateTimeUnit(numberOfSeconds + secondsToAdd, 1, LIMIT_1);
    }

    public int getMinuteTens()
    {
        return calculateUnit(numberOfSeconds,LIMIT_3 * LIMIT_2 * LIMIT_1, LIMIT_4);
    }

    public int getMinuteOnes()
    {
        return calculateUnit(numberOfSeconds, LIMIT_2 * LIMIT_1, LIMIT_3);
    }

    public int getSecondTens()
    {
        return calculateUnit(numberOfSeconds, LIMIT_1, LIMIT_2);
    }

    public int getSecondOnes()
    {
        return calculateUnit(numberOfSeconds, 1, LIMIT_1);
    }

    public int getSeconds()
    {
        return numberOfSeconds;
    }

    public void reset()
    {
        numberOfSeconds = 0;
    }

}
