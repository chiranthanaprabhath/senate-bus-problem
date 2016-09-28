import java.util.concurrent.Semaphore;

/**
 * Class for the rider threads
 */
class Rider implements Runnable {

    private final Semaphore riderWaitingAreaEntranceSem;
    private final Semaphore riderBoardingSem;
    private final Semaphore busDepartureSem;
    private final Semaphore mutex;
    private final int index;
    private WaitingArea waitingArea;

    public Rider(Semaphore riderWaitingAreaEntranceSem, Semaphore riderBoardingSem, Semaphore busDepartureSem, Semaphore mutex, int index, WaitingArea waitingArea) {
        this.riderWaitingAreaEntranceSem = riderWaitingAreaEntranceSem;
        this.riderBoardingSem = riderBoardingSem;
        this.busDepartureSem = busDepartureSem;
        this.index = index;
        this.mutex = mutex;
        this.waitingArea = waitingArea;
    }

    @Override
    public void run() {

        try {
            // Acquiring the semaphore in trying to enter the rider waiting area, only 50 allowed at a given time
            riderWaitingAreaEntranceSem.acquire();

                // Entering the boarding area and incrementing the riders count
                mutex.acquire();
                    enterBoardingArea();
                    waitingArea.incrementRidersCount();
                mutex.release();

                //Acquiring the semaphore to board the bus
                riderBoardingSem.acquire();
                boardBus();

            // Releasing the semaphore for enter waiting area
            riderWaitingAreaEntranceSem.release();

            // Decrementing the rider count once boarded
            waitingArea.decrementRidersCount();

            // When the riders are boarded, allowing the bus to depart by releasing the bus departure semaphore
            if (waitingArea.getRidersCount() == 0)
                busDepartureSem.release();
            // If there are more riders waiting, allowing them to get into the bus
            else
                riderBoardingSem.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void boardBus() {
        System.out.println("Rider :" + index + " boarded");
    }

    public void enterBoardingArea() {
        System.out.println("Rider :" + index + " entered boarding area");
    }
}