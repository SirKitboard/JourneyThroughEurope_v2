package jte.game;

import jte.ui.JTEUI;
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class JTEGameStateManager {

    // THE GAME WILL ALWAYS BE IN
    // ONE OF THESE STATES
    public enum JTEGameState {
        GAME_NOT_STARTED, GAME_IN_PROGRESS, GAME_OVER
    }

    // STORES THE CURRENT STATE OF THIS GAME
    private JTEGameState currentGameState;

    // WHEN THE STATE OF THE GAME CHANGES IT WILL NEED TO BE
    // REFLECTED IN THE USER INTERFACE, SO THIS CLASS NEEDS
    // A REFERENCE TO THE UI

    // THIS IS THE GAME CURRENTLY BEING PLAYED
    private JTEGameData gameInProgress;

    // HOLDS ALL OF THE COMPLETED GAMES. NOTE THAT THE GAME
    // IN PROGRESS IS NOT ADDED UNTIL IT IS COMPLETED
    private ArrayList<JTEGameData> gamesHistory;

    private final String NEWLINE_DELIMITER = "\n";

    public JTEGameStateManager() {
        // WE HAVE NOT STARTED A GAME YET
        currentGameState = JTEGameState.GAME_NOT_STARTED;

        // NO GAMES HAVE BEEN PLAYED YET, BUT INITIALIZE
        // THE DATA STRCUTURE FOR PLACING COMPLETED GAMES
        gamesHistory = new ArrayList<>();

        // THE FIRST GAME HAS NOT BEEN STARTED YET
        gameInProgress = null;
    }

    // ACCESSOR METHODS
    /**
     * Accessor method for getting the jte.game currently being played.
     *
     * @return The jte.game currently being played.
     */
    public JTEGameData getGameInProgress() {
        return gameInProgress;
    }

    /**
     * Accessor method for getting the number of games that have been played.
     *
     * @return The total number of games that have been played during this jte.game
     * session.
     */
    public int getGamesPlayed() {
        return gamesHistory.size();
    }

    /**
     * Accessor method for getting all the games that have been completed.
     *
     * @return An Iterator that allows one to go through all the games that have
     * been played so far.
     */
    public Iterator<JTEGameData> getGamesHistoryIterator() {
        return gamesHistory.iterator();
    }

    /**
     * Accessor method for testing to see if any games have been started yet.
     *
     * @return true if at least one jte.game has already been started during this
     * session, false otherwise.
     */
    public boolean isGameNotStarted() {
        return currentGameState == JTEGameState.GAME_NOT_STARTED;
    }

    /**
     * Accessor method for testing to see if the current jte.game is over.
     *
     * @return true if the jte.game in progress has completed, false otherwise.
     */
    public boolean isGameOver() {
        return currentGameState == JTEGameState.GAME_OVER;
    }

    /**
     * Accessor method for testing to see if the current jte.game is in progress.
     *
     * @return true if a jte.game is in progress, false otherwise.
     */
    public boolean isGameInProgress() {
        return currentGameState == JTEGameState.GAME_IN_PROGRESS;
    }

    /**
     * Counts and returns the number of wins during this jte.game session.
     *
     * @return The number of games in that have been completed that the player
     * won.
     */
    public int getWins() {
        // ITERATE THROUGH ALL THE COMPLETED GAMES
        Iterator<JTEGameData> it = gamesHistory.iterator();
        int wins = 0;
        while (it.hasNext()) {
            // GET THE NEXT GAME IN THE SEQUENCE
            JTEGameData game = it.next();

            // TODO
            // IF IT ENDED IN A WIN, INC THE COUNTER
            //if (game.isWon()) {
            //    wins++;
            //}
        }
        return wins;
    }

    /**
     * Counts and returns the number of losses during this jte.game session.
     *
     * @return The number of games in that have been completed that the player
     * lost.
     */
    public int getLosses() {
        // ITERATE THROUGH ALL THE COMPLETED GAMES
        Iterator<JTEGameData> it = gamesHistory.iterator();
        int losses = 0;
        while (it.hasNext()) {
            // GET THE NEXT GAME IN THE SEQUENCE
            JTEGameData game = it.next();

            // TODO
            // IF IT ENDED IN A LOSS, INC THE COUNTER
            //if (game.isLost()) {
            //    losses++;
            //}
        }
        return losses;
    }

    /**
     * Finds the completed jte.game that the player won that required the least
     * amount of time.
     *
     * @return The completed jte.game that the player won requiring the least amount
     * of time.
     */
    /**
     * This method starts a new jte.game, initializing all the necessary data for
     * that new jte.game as well as recording the current jte.game (if it exists) in the
     * games history data structure. It also lets the user interface know about
     * this change of state such that it may reflect this change.
     */
    public void startNewGame(ArrayList<Integer> humanList,int numCards, ArrayList<String> names) {
        // IS THERE A GAME ALREADY UNDERWAY?
        // YES, SO END THAT GAME AS A LOSS
        if (!isGameNotStarted() && (!gamesHistory.contains(gameInProgress))) {
            gamesHistory.add(gameInProgress);
        }

        // IF THERE IS A GAME IN PROGRESS AND THE PLAYER HASN'T WON, THAT MEANS
        // THE PLAYER IS QUITTING, SO WE NEED TO SAVE THE GAME TO OUR HISTORY
        // DATA STRUCTURE. NOTE THAT IF THE PLAYER WON THE GAME, IT WOULD HAVE
        // ALREADY BEEN SAVED SINCE THERE WOULD BE NO GUARANTEE THE PLAYER WOULD
        // CHOOSE TO PLAY AGAIN

        // AND NOW MAKE A NEW GAME
        makeNewGame(humanList,numCards,names);

        // AND MAKE SURE THE UI REFLECTS A NEW GAME
        //ui.resetUI();
    }

    /**
     * This method chooses a secret word and uses it to create a new jte.game,
     * effectively starting it.
     */
    public void makeNewGame(ArrayList<Integer> list,int numCards,ArrayList<String> names) {
        // TODO: create a jte.game for a level
        gameInProgress = new JTEGameData(list,numCards,names);

        // THE GAME IS OFFICIALLY UNDERWAY
        currentGameState = JTEGameState.GAME_IN_PROGRESS;
    }

    public void loadGame(int numPlayers, ArrayList<Player> players) {
        gameInProgress = new JTEGameData(numPlayers,players);
    }

}
