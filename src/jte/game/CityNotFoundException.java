package jte.game;

/**
 * Created by Aditya on 11/9/2014.
 */
public class CityNotFoundException extends Exception{
	public CityNotFoundException() {
		super("No City at specified coordinates");
	}
}
