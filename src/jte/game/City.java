package jte.game;

import java.util.ArrayList;

/**
 * Created by Aditya on 11/9/2014.
 */
public class City {
	String name;
	String color;
	int x;
	int y;
	int actualx;
	int actualy;

	ArrayList<City> landConnections;
	ArrayList<City> seaConnections;

	public String getName() {
		return name;
	}

	int quad;

	public int getX() {
		return x;
	}

	public String getColor() {
		return color;
	}

	public int getQuad() {

		return quad;
	}

	public int getY() {

		return y;
	}

	public City(String name, String color, int quad, int x, int y ) {
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
		this.quad = quad;
		if(quad == 1) {
			actualx = x;
			actualy = y;
		}
		else if(quad == 2) {
			actualx = x + 2000;
			actualy = y;
		}

		else if(quad == 3) {
			actualx = x;
			actualy = y + 2559;
		}

		else if(quad == 4) {
			actualx = x + 1980;
			actualy = y + 2559;
		}
		landConnections = new ArrayList<>();
		seaConnections = new ArrayList<>();
	}

	public ArrayList<City> getLandConnections() {
		return landConnections;
	}

	public ArrayList<City> getSeaConnections() {
		return seaConnections;
	}

	public int getActualy() {
		return actualy;
	}

	public int getActualx() {
		return actualx;
	}

	public String toString() {
		return color+"/"+name.toUpperCase()+".jpg";
	}
}
