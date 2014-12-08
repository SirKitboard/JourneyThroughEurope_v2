package jte.game;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Aditya on 12/7/2014.
 */
public class ListLengthComparator implements Comparator<List<City>> {
    public int compare(List<City> one, List<City> two) {
        if(one.size()>two.size()) {
            return 1;
        }
        else if(one.size() == two.size()) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
