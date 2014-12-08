package jte.game;

import com.sun.media.jfxmedia.events.PlayerEvent;
import jte.file.JTEFileLoader;
import jte.ui.JTEUI;

import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * JTEGameData stores the data necessary for a single Sokoban jte.game. Note
 * that this class works in concert with the JTEGameStateManager, so all
 * instance variables have default (package-level) access.
 */
public class JTEGameData implements Serializable{
	HashMap<String,City> cityData;
	ArrayList<String> cityNames;
	ArrayList<City> deck;
	ArrayList<City> airports;
	JTEUI ui;
	JTEFileLoader fileLoader;
    ArrayList<Player> player;


	/*
     * Construct this object when a jte.game begins.
     */
    public JTEGameData(ArrayList<Integer> humanslist,int numCards, ArrayList<String> names) {
		this.ui = JTEUI.getUI();
	    fileLoader = ui.getFileLoader();
	    createCityData();
	    createCityNameList();
	    createDeck();
		airports = new ArrayList<>();
		for(int i=0;i<cityNames.size();i++) {
			if(cityData.get(cityNames.get(i)).getAirport()!=0) {
				airports.add(cityData.get(cityNames.get(i)));
			}
		}
	    player = new ArrayList<Player>();
		for(int i=0;i<humanslist.size();i++) {
			player.add(new Player(names.get(i), deck, i, numCards,(humanslist.get(i)==0)));
		}
    }

	public JTEGameData(int numplayers, ArrayList<Player> players) {
		this.ui = JTEUI.getUI();
		fileLoader = ui.getFileLoader();
		createCityData();
		createCityNameList();
		createDeck();
		this.player = players;
		airports = new ArrayList<>();
		for(int i=0;i<cityNames.size();i++) {
			if(cityData.get(cityNames.get(i)).getAirport()!=0) {
				airports.add(cityData.get(cityNames.get(i)));
			}
		}
	}

	public ArrayList<Player> getPlayer() {
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

	public City getCity(double x, double y) throws CityNotFoundException{
		for(int i=0;i<cityNames.size();i++) {
			City temp = cityData.get(cityNames.get(i));
			if (Math.sqrt(Math.pow((x - temp.getActualx()), 2) + Math.pow(y - temp.getActualy(), 2)) < 20) {
				return temp;
			}
		}
		throw new CityNotFoundException();
	}

	public void resetAllCalculations() {
		for (int i = 0; i < cityNames.size(); i++) {
			cityData.get(cityNames.get(i)).minDistance = Double.POSITIVE_INFINITY;
			cityData.get(cityNames.get(i)).previous = null;
		}
	}
}
