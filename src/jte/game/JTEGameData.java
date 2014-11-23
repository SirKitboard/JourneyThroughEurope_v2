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
    Player player;


	/*
     * Construct this object when a jte.game begins.
     */
    public JTEGameData(JTEUI ui) {
		this.ui = ui;
	    fileLoader = ui.getFileLoader();
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
