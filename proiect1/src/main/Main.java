package main;

import checker.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.Constants;
import org.json.simple.parser.ParseException;
import simulation.SimulationManager;
import utils.JSONOutput;
import utils.JSONReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

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
    public static void main(final String[] args) throws IOException, ParseException {
        File directory = new File(Constants.INPUT_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        int i = 1;

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            //System.out.println(i);
            JSONReader jsonReader = new JSONReader();
            jsonReader.parseFile(file);
            JSONOutput jsonOutput = SimulationManager.getInstance().startSimulation(jsonReader);
            objectMapper.writeValue(new File(Constants.OUTPUT_PATH + i + ".json"), jsonOutput);
            i++;
        }
        Checker.calculateScore();
    }
}
