package jte.game;

/**
 * Created by Aditya on 11/9/2014.
 */
public class City {
	String name;
	String color;
	int x;
	int y;

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
	}

	public String toString() {
		return color+"/"+name.toUpperCase()+".jpg";
	}
}
