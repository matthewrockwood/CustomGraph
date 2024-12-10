/** Main class that implements A  MST and the shortest path method*/
package org.example;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyGraph graph = new MyGraph();

        //creating the vertexes
        for (int i = 0; i < 10; i++) {
            graph.addVertex(i);
        }
        graph.addEdge(0,1,10);
        graph.addEdge(0,2,1);
        graph.addEdge(0,3,8);
        graph.addEdge(1,3,2);
        graph.addEdge(2,4,1);
        graph.addEdge(2,5,7);
        graph.addEdge(3,5,8);
        graph.addEdge(3,6,1);
        graph.addEdge(4,7,1);
        graph.addEdge(5,7,1);
        graph.addEdge(5,8,9);
        graph.addEdge(6,8,1);
        graph.addEdge(6,9,9);
        graph.addEdge(7,8,2);
        graph.addEdge(8,9,1);

        shortestPath(graph,0);


        MyGraph g = minimumSpanningTree(graph,0);

        System.out.println("Source Graph");
        System.out.print("V = { ");
        for (int i = 0; i < graph.lst.size(); i++) {
            System.out.print(graph.lst.get(i) + " ");
        }
        System.out.println("}");
        System.out.println("Adjacent Lists");

        for (int i = 0; i < graph.lst.size(); i++) {
            System.out.print(graph.lst.get(i) +": ");
            List<Edge> tempList = graph.adjList.get(i);

            for (int j = 0; j < tempList.size(); j++){
                Edge edge = tempList.get(j);
                System.out.print("( " + edge.v1 + ", " + edge.v2 + ", "+ edge.weight + " )");
                if(j<=tempList.size()){
                    System.out.print(" ");
                }
            }
            System.out.println();

        }
        System.out.println();
        System.out.println("MST Graph");
        System.out.print("V = { ");
        for (int i = 0; i < g.lst.size(); i++) {
            System.out.print(g.lst.get(i) + " ");
        }
        System.out.println("}");
        System.out.println("Adjacent Lists");

        for (int i = 0; i < g.lst.size(); i++) {
            System.out.print(g.lst.get(i) +": ");
            List<Edge> tempList = g.adjList.get(i);

            for (int j = 0; j < tempList.size(); j++){
                Edge edge = tempList.get(j);
                System.out.print("( " + edge.v1 + ", " + edge.v2 + ", "+ edge.weight + " )");
                if(j<=tempList.size()){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }



    }
    /** A helper method that gets the closest unvisited vertex to the source*/
    public static int getMinDistVertex(MyGraph g, List<Integer> unvisitedList, int[] dist){
        int minNeighborVertex = unvisitedList.getFirst();
        int minNeighborDistance = dist[minNeighborVertex];
        for (int i = 1; i < unvisitedList.size(); i++) {
            if(dist[unvisitedList.get(i)]<minNeighborDistance){ // gotta do this cause the lists are not in sync
                minNeighborDistance = dist[unvisitedList.get(i)];
                minNeighborVertex = unvisitedList.get(i);
            }
        }
        return  minNeighborVertex;
    }
    /** A method that implements dikstras Algorithm to make a chart that has the shortest path */
    public static void shortestPath(MyGraph g, int startingVertex){
        boolean[] visited = new boolean[g.lst.size()];
        int[] previousNode = new int[g.lst.size()];
        int [] distance = new int[g.lst.size()];
        //using LL b/c we may remove anywhere in the list
        LinkedList<Integer> unvisitedList = new LinkedList<>();
        int possibleDistance;

        for (int i = 0; i < g.lst.size(); i++) {
            visited[i] = false;
            previousNode[i] = -1;
            distance[i] = Integer.MAX_VALUE;
            unvisitedList.addLast(g.lst.get(i));

        }

        distance[startingVertex] = 0;

        while(!unvisitedList.isEmpty()){
            int minDistanceVertex = getMinDistVertex(g,unvisitedList,distance);

            unvisitedList.remove(Integer.valueOf(minDistanceVertex));//
            visited[minDistanceVertex] = true;
            Edge tempEdge;

            for (int i = 0; i < g.adjList.get(minDistanceVertex).size(); i++) {

                tempEdge = g.adjList.get(minDistanceVertex).get(i);
                int currNeighbor = (tempEdge.v1 == minDistanceVertex) ? tempEdge.v2 : tempEdge.v1;;

                if(!visited[currNeighbor]) {
                    possibleDistance = distance[minDistanceVertex] + tempEdge.weight;
                    if (possibleDistance < distance[currNeighbor]){
                        distance[currNeighbor] = possibleDistance;
                        previousNode[currNeighbor] = minDistanceVertex;
                    }

                }
            }

        }
        System.out.println("Source Graph");
        System.out.print("V = { ");
        for (int i = 0; i < g.lst.size(); i++) {
            System.out.print(g.lst.get(i) + " ");
        }
        System.out.println("}");
        System.out.println("Adjacent Lists");

        for (int i = 0; i < g.lst.size(); i++) {
            System.out.print(g.lst.get(i) +": ");
            List<Edge> tempList = g.adjList.get(i);

            for (int j = 0; j < tempList.size(); j++){
                Edge edge = tempList.get(j);
                System.out.print("( " + edge.v1 + ", " + edge.v2 + ", "+ edge.weight + " )");
                if(j<=tempList.size()){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("Shortest Path Data\nStarting Vertex  : " + startingVertex + "\n   Vertex   Distance");
        for (int i = 0; i < distance.length; i++) {
            System.out.println("   " + i + "          " + distance[i]);
        }
        System.out.println("   Vertex" + "   Previous");
        for (int i = 0; i < previousNode.length; i++) {
            System.out.println("   " + i + "          " + previousNode[i]);
        }

    }
    /** A helper method to create a MST that gets the minimun frontier Edge */
    public static Edge getMinFrontierEdge(MyGraph g, boolean[] visited){
        Edge minEdge = new Edge(0,0, Integer.MAX_VALUE);
        for(int i =0; i < visited.length;i++){
            if(visited[i]){
                //calls the hashmap to return a list of edges based on a vertex i
                List<Edge> tempListOfEdges = g.adjList.get(i);
                for (int j = 0; j < tempListOfEdges.size(); j++) {
                    Edge tempEdge = tempListOfEdges.get(j);
                    //long but works i think
                    if((!visited[tempEdge.v1] || !visited[tempEdge.v2]) && tempListOfEdges.get(j).weight< minEdge.weight){
                        minEdge = tempListOfEdges.get(j);
                    }
                }
            }
        }
        return minEdge;
    }
    /** A method that takes a graph and returns a MST of that graph */
    public static MyGraph minimumSpanningTree(MyGraph g, int startingVertex){
        MyGraph MSTgraph = new MyGraph();
        boolean[] visitedVertexs = new boolean[g.lst.size()];
        visitedVertexs[g.lst.indexOf(startingVertex)] = true;

        for (int i = 0; i < g.lst.size(); i++) {
            MSTgraph.addVertex(g.lst.get(i));
        }
        int countOfVisited = 1;
            while(countOfVisited<g.lst.size()) {

                Edge minEdge = getMinFrontierEdge(g, visitedVertexs);
                visitedVertexs[minEdge.v1] = true;
                visitedVertexs[minEdge.v2] = true;
                MSTgraph.addEdge(minEdge.v1, minEdge.v2, minEdge.weight);
                countOfVisited++;
            }
        return MSTgraph;
    }

}