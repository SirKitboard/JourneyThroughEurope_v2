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

	public String getFlagColor() {
		return flagColor;
	}

	public City getHome() {
		return home;
	}

	Player (ArrayList<City> cityList,int playerNo,int numCards){
		System.out.println("\nPlayer : " + playerNo);
		hand = new ArrayList<City>();
		String color;
		if(playerNo%3==0) {
			color = "red";
		} else if (playerNo%3==1) {
			color = "green";
		}
		else {
			color = "yellow";
		}
		int j =0;
		while(!cityList.get(j).getColor().equalsIgnoreCase(color)) {
			j++;
		}
		hand.add(cityList.get(j));
		System.out.println(cityList.remove(j));
		for(int i=0;i<numCards-1;i++) {
			hand.add(cityList.get(0));
			System.out.println(cityList.remove(0));
		}
		home = hand.get(0);
	}
	public ArrayList<City> getHand() {
		return hand;
	}
}
