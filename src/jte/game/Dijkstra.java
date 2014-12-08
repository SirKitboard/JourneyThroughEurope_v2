package jte.game;

import jte.ui.JTEUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Aditya on 12/7/2014.
 */
public class Dijkstra {
    public static void computePaths(City source) {
        source.minDistance = 0.;
        PriorityQueue<City> vertexQueue = new PriorityQueue<City>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            City u = vertexQueue.poll();
// Visit each edge exiting u
            for(int i=0;i<u.getLandConnections().size();i++) {
                City v = u.getLandConnections().get(i);
                double weight = 1;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }

            for(int i=0;i<u.getSeaConnections().size();i++) {
                City v = u.getSeaConnections().get(i);
                double weight = 6;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }

            JTEUI ui = JTEUI.getUI();
            JTEGameStateManager gsm = ui.getGSM();
            JTEGameData jteGameData = gsm.getGameInProgress();
            for(int i=0;i<jteGameData.airports.size();i++) {
                City v = jteGameData.airports.get(i);
                double weight = 20;
                if(v.getAirport()==u.getAirport()) {
                    weight = 2;
                }
                else if (v.getAirport() == (u.getAirport()-2) || v.getAirport() == (u.getAirport()-2)) {
                    weight = 4;
                }
                else if(u.getAirport()%2 == 0) {
                    if(v.getAirport()==u.getAirport()-1) {
                        weight = 4;
                    }
                }
                else if (u.getAirport()%2 != 0){
                    if(v.getAirport() == (u.getAirport()+1)) {
                        weight = 4;
                    }
                }
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }
    public static List<City> getShortestPathTo(City target2) {
        JTEUI ui = JTEUI.getUI();
        City target = ui.getGSM().getGameInProgress().getCityData().get(target2.getName());
        List<City> path = new ArrayList<City>();
        for (City vertex = target; vertex != null; vertex = vertex.previous)
            if(!path.contains(vertex)) {
                path.add(vertex);
            }
            else {
                vertex.previous = null;
            }
        Collections.reverse(path);
        return path;
    }
}