package jte.game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aditya on 11/9/2014.
 */
public class City implements Serializable, Comparable<City> {
	String name;
	String color;
	City previous;
	int x;
	int y;
	public double minDistance = Double.POSITIVE_INFINITY;
	int actualx;
	int actualy;
	int airport;
	String data;
	ArrayList<City> landConnections;
	ArrayList<City> seaConnections;

	public int compareTo(City other) {
		return Double.compare(minDistance, other.minDistance);
	}

	public int getAirport() {
		return airport;
	}

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public City(String name, String color, int quad, int x, int y, int airport) {
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
		this.quad = quad;
		this.airport = airport;
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

	@Override
	public boolean equals(Object object)
	{
		boolean sameSame = false;

		if (object != null && object instanceof City)
		{
			if(this.name.equalsIgnoreCase(((City) object).getName())) {
				return true;
			}
		}
		return false;
	}
}
