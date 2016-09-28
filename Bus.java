import java.util.concurrent.Semaphore;

/**
 * Class for the bus threads
 */
public class Bus implements Runnable {

    private final Semaphore riderBoardingSem;
    private final Semaphore busDepartureSem;
    private final Semaphore mutex;
    private final int index;
    private WaitingArea waitingArea;


    public Bus(Semaphore riderBoardingSem, Semaphore busDepartureSem, Semaphore mutex, int index, WaitingArea waitingArea) {
        this.riderBoardingSem = riderBoardingSem;
        this.busDepartureSem = busDepartureSem;
        this.mutex = mutex;
        this.index = index;
        this.waitingArea = waitingArea;
    }

    @Override
    public void run() {

        try {
            mutex.acquire();
                // Arrival of the bus
                arrived();

                // Checking if there are waiting riders
                if (waitingArea.getRidersCount() > 0) {

                    // Releasing the rider boarding semaphore allowing a rider to get into the bus
                    riderBoardingSem.release();
                    // Acquiring the bus depaarture semaphore to wait the bus until the riders get boarded
                    busDepartureSem.acquire();
                }
            mutex.release();

            // Depart the bus
            depart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void depart() {
        System.out.println("---- Bus : " + index + " departed");
    }

    public void arrived() {
        System.out.println("---- Bus : " + index + " arrived");
        System.out.println("---- Waiting rider count : " + waitingArea.getRidersCount());
    }
}