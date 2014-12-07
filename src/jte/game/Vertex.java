package jte.game;

/**
 * Created by Aditya on 12/7/2014.
 */
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
class Vertex implements Comparable<Vertex> {
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) {
        name = argName;
    }
    public String toString() {
        return name;
    }
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
class Edge {
    public final City target;
    public final double weight;
    public Edge(City argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}
