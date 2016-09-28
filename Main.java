import java.util.Scanner;

/**
 * Main Class for the Senate Bus Problem
 *
 * Running the program
 *      javac Main.java
 *      java Main
 *
 *Stopping the simulation
 *      Press any key
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        float riderArrivalMeanTime = 30f * 1000;
        float busArrivalMeanTime = 20 * 60f * 1000 ;
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        WaitingArea waitingArea = new WaitingArea();

        System.out.println("\n*******  Press any key to exit.  *******\n" );

        RiderGenerator riderGenerator = new RiderGenerator(riderArrivalMeanTime, waitingArea);
        (new Thread(riderGenerator)).start();

        BusGenerator busGenerator = new BusGenerator(busArrivalMeanTime,waitingArea);
        (new Thread(busGenerator)).start();

        // Program Termination with a user input
        while(true){
            userInput = scanner.nextLine();
            if(userInput != null)
                System.exit(0);
        }
    }
}
