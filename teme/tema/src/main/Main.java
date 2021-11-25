package main;

import Action.Command.Command;
import Action.Queries.ActorQuery;
import Action.Queries.UserQuery;
import Action.Queries.VideoQuery;
import Action.Recommandations.PremiumRecommendation;
import Action.Recommandations.StandardRecommendation;
import Database.Database;
import Entities.*;
import actor.Actor;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import utils.ActionResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {

        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());
        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        Database.getInstance().clearDatabase();

        for(MovieInputData movie : input.getMovies()){
            Database.getInstance().addMovie(new Movie(movie.getTitle(), movie.getYear(), movie.getGenres(), movie.getCast(), movie.getDuration()));
        }
        for(SerialInputData show : input.getSerials()){
            Database.getInstance().addShow(new Show(show.getTitle(), show.getYear(), show.getGenres(), show.getCast(), show.getNumberSeason(), show.getSeasons()));
        }
        for(UserInputData user : input.getUsers()){
            Database.getInstance().users.add(new User(user.getUsername(), user.getSubscriptionType(), user.getHistory(), user.getFavoriteMovies()));
        }
        for(ActorInputData actor : input.getActors()){
            Database.getInstance().actors.add(new Actor(actor.getName(), actor.getCareerDescription(), actor.getFilmography(), actor.getAwards()));
        }
        for(ActionInputData actionInputData : input.getCommands()){
            ActionResponse actionResponse = new ActionResponse();
            switch (actionInputData.getActionType()){
                case Constants.COMMAND:
                    actionResponse = Command.solveCommands(actionInputData, Database.getInstance().users.stream().filter(u -> u.getUsername().equals(actionInputData.getUsername())).findFirst().orElse(null));
                    break;
                case Constants.QUERY:
                    switch(actionInputData.getObjectType()){
                        case Constants.ACTORS:
                            actionResponse = ActorQuery.solveQuery(actionInputData);
                            break;
                        case Constants.USERS:
                            actionResponse = UserQuery.solveQuery(actionInputData);
                            break;
                        case Constants.MOVIES: case Constants.SHOWS:
                            actionResponse = VideoQuery.solveQuery(actionInputData);
                            break;
                    }
                    break;
                case Constants.RECOMMENDATION:
                    User user = Database.getInstance().users.stream().filter(u -> u.getUsername().equals(actionInputData.getUsername())).findFirst().orElse(null);
                    if(user.getUserType() == UserType.BASIC){
                        actionResponse = StandardRecommendation.solveRecommendation(actionInputData, user);
                    }
                    else{
                        actionResponse = PremiumRecommendation.solveRecommendation(actionInputData, user);
                    }
                    break;
            }
            arrayResult.add(fileWriter.writeFile(actionResponse.getId(), "", actionResponse.getResponse()));
        }
        fileWriter.closeJSON(arrayResult);
    }
}
