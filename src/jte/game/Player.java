package jte.game;

import java.util.ArrayList;

/**
 * Created by Aditya on 11/9/2014.
 */
public class Player {
	ArrayList<City> hand;
	String flagColor;
	City position;
	City home;

	public City getHome() {
		return home;
	}

	Player (ArrayList<City> cityList){
		hand = new ArrayList<City>();
		for(int i=0;i<7;i++) {
			hand.add(cityList.get(0));
			cityList.remove(0);
		}
		home = hand.get(0);
	}
	public ArrayList<City> getHand() {
		return hand;
	}
}
