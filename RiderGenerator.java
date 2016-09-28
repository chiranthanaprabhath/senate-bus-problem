import java.util.Random;

/**
 * Class to spawn the threads for the riders arrival
 */
public class RiderGenerator implements Runnable {

    private float arrivalMeanTime;
    private WaitingArea waitingArea;
    private static Random random;

    public RiderGenerator(float arrivalMeanTime, WaitingArea waitingArea) {
        this.arrivalMeanTime = arrivalMeanTime;
        this.waitingArea = waitingArea;
        random = new Random();
    }

    @Override
    public void run() {

        int riderIndex = 1;
        // Spawning rider threads for the user specified value
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initializing and starting the rider threads
                Rider rider = new Rider(waitingArea.getRiderWaitingAreaEntranceSem(), waitingArea.getRiderBoardingAreaEntranceSem(), waitingArea.getBusDepartureSem(), waitingArea.getMutex(), riderIndex, waitingArea);
                (new Thread(rider)).start();

                riderIndex++;
                // Sleeping the thread to obtain the inter arrival time between the threads
                Thread.sleep(getExponentiallyDistributedRiderInterArrivalTime());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long getExponentiallyDistributedRiderInterArrivalTime() {
        float lambda = 1 / arrivalMeanTime;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}
