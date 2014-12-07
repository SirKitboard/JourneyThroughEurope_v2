package jte.game;

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

            if(u.getAirport() != 0) {

            }
        }
    }
    public static List<City> getShortestPathTo(City target) {
        List<City> path = new ArrayList<City>();
        for (City vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
    /*public static void main(String[] args) {
        Vertex v0 = new Vertex("London");
        Vertex v1 = new Vertex("Dover");
        Vertex v2 = new Vertex("Calais");
        Vertex v3 = new Vertex("Paris");
        Vertex v4 = new Vertex("Rotterdam");
        Vertex v5 = new Vertex("Brussels");
        Vertex v6 = new Vertex("Lille");
        v0.adjacencies = new Edge[] { new Edge(v1, 1), new Edge(v4, 6), new Edge(v3, 2) };
        v1.adjacencies = new Edge[] { new Edge(v0, 1), new Edge(v2, 1) };
        v2.adjacencies = new Edge[] { new Edge(v1, 1), new Edge(v3, 1), new Edge(v6, 1) };
        v3.adjacencies = new Edge[] { new Edge(v0, 2), new Edge(v2, 1), new Edge(v6, 1) };
        v4.adjacencies = new Edge[] { new Edge(v0, 6), new Edge(v5, 1) };
        v5.adjacencies = new Edge[] { new Edge(v4, 1), new Edge(v6, 1) };
        v6.adjacencies = new Edge[] { new Edge(v3, 1), new Edge(v5, 1) };
        Vertex[] vertices = { v0, v1, v2, v3, v4, v5, v6 };
// Paths from London
        computePaths(v0);
        for (Vertex v : vertices) {
            System.out.println("Distance from London to " + v + ": "
                    + v.minDistance);
            List<Vertex> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }*/
}