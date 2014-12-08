package jte.game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aditya on 11/9/2014.
 */
public class Player implements Serializable{
	String name;
	ArrayList<City> hand;
	String flagColor;
	City position;
	City home;
	boolean isAI;
	public String getFlagColor() {
		return flagColor;
	}

	public String getName() {
		return name;
	}

	public City getHome() {
		return home;
	}

	Player (String name, ArrayList<City> cityList,int playerNo,int numCards, boolean isAI){
		System.out.println("\nPlayer : " + playerNo);
		this.name = name;
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
		position = home;
		this.isAI = isAI;
	}
	public ArrayList<City> getHand() {
		return hand;
	}

	public City getPosition() {
		return position;
	}

	public void setPosition(City position) {

		this.position = position;
	}

	public boolean isAI() {
		return isAI;
	}

	public void setAI(boolean ai) {
		isAI = ai;
	}
}
