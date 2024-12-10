/** This is a class that is used to make the graph itself. It uses the edges from the Edge class */
package org.example;

import java.util.*;

public class MyGraph {
    List<Integer> lst;
    Map<Integer, List<Edge>> adjList;

    /** This is the default constructor. It uses a ArrayList as the list of vertexes and a HashMap as a way to store the edges of each vertex */
    public MyGraph(){
        lst = new ArrayList<>();
        adjList = new HashMap<>();
    }
    /**Method used to add a vertex to a graph*/
    public void addVertex(int v){
        lst.add(v);
        adjList.put(v, new ArrayList<>());
    }
    /** Method used to add a edge between vertexes */
    public void addEdge(int v1, int v2, int weight){
        Edge e = new Edge(v1,v2,weight);

        List<Edge> v1AdjList = adjList.get(v1);
        List<Edge> v2AdjList = adjList.get(v2);

        v1AdjList.add(e);
        v2AdjList.add(e);
    }


}
