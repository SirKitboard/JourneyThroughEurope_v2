package jte.game;

import jte.file.JTEFileLoader;
import jte.ui.JTEUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * JTEGameData stores the data necessary for a single Sokoban jte.game. Note
 * that this class works in concert with the JTEGameStateManager, so all
 * instance variables have default (package-level) access.
 */
public class JTEGameData {
	HashMap<String,City> cityData;
	ArrayList<String> cityNames;
	ArrayList<City> deck;
	JTEUI ui;
	JTEFileLoader fileLoader;
    // START AND END TIME WILL BE USED TO CALCULATE THE
    // TIME IT TAKES TO PLAY THIS GAME
    GregorianCalendar startTime;
    GregorianCalendar endTime;
	Player player;

    // THESE ARE USED FOR FORMATTING THE TIME OF GAME
    final long MILLIS_IN_A_SECOND = 1000;
    final long MILLIS_IN_A_MINUTE = 1000 * 60;
    final long MILLIS_IN_AN_HOUR = 1000 * 60 * 60;

	/*
     * Construct this object when a jte.game begins.
     */
    public JTEGameData(JTEUI ui) {
		this.ui = ui;
	    fileLoader = ui.getFileLoader();
	    startTime = new GregorianCalendar();
        endTime = null;
	    createCityData();
	    createCityNameList();
	    createDeck();
	    player = new Player(deck);
    }

	public Player getPlayer() {
		return player;
	}

	public HashMap<String, City> getCityData() {
		return cityData;
	}

	public ArrayList<String> getCityNames() {
		return cityNames;
	}

	public void createCityNameList() {
		cityNames = fileLoader.getCityList();
	}

	public void createCityData() {
		cityData = fileLoader.getCityData();
	}

	public void createDeck() {
		deck = new ArrayList<>();
		for(int i=0;i<cityNames.size();i++) {
			deck.add(cityData.get(cityNames.get(i)));
		}
		Collections.shuffle(deck);
	}

    // ACCESSOR METHODS
    /**
     * Gets the total time (in milliseconds) that this jte.game took.
     *
     * @return The time of the jte.game in milliseconds.
     */

    public long getTimeOfGame() {
        // IF THE GAME ISN'T OVER YET, THERE IS NO POINT IN CONTINUING
        if (endTime == null) {
            return -1;
        }

        // THE TIME OF THE GAME IS END-START
        long startTimeInMillis = startTime.getTimeInMillis();
        long endTimeInMillis = endTime.getTimeInMillis();

        // CALC THE DIFF AND RETURN IT
        long diff = endTimeInMillis - startTimeInMillis;
		diff/=1000;
        return diff;
    }

    /**
     * Called when a player quits a jte.game before ending the jte.game.
     */
    public void giveUp() {
        endTime = new GregorianCalendar();
    }


    /**
     * Check if the jte.game was won.
     */
    public boolean isWon() {
        // TODO
        return false;
    }

        /**
     * Check if the jte.game was lost.
     */
    public boolean isLost() {
        // TODO
        return true;
    }

	public City getCity(double x, double y, int quad) throws CityNotFoundException{
		for(int i=0;i<cityNames.size();i++) {
			City temp = cityData.get(cityNames.get(i));
			if(temp.getQuad() == quad) {
				if (Math.sqrt(Math.pow((x - temp.getX()), 2) + Math.pow(y - temp.getY(), 2)) < 15) {
					return temp;
				}
			}
		}
		throw new CityNotFoundException();
	}
}
