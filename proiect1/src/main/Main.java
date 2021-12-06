package main;

import checker.Checker;
import simulation.SimulationManager;
import utils.JSONOutput;
import utils.JSONReader;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) {
        JSONReader jsonReader = new JSONReader();
        JSONOutput jsonOutput = SimulationManager.getInstance().startSimulation(jsonReader);
        Checker.calculateScore();
    }
}
