package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BFSandDijkstra {

    private int numberOfNodes; //Number of nodes
    private ArrayList<Integer>[] adjacencyList; //adjacencyList

    private BFSandDijkstra(int n){
        numberOfNodes = n;
        adjacencyList = new ArrayList[n];
        for(int i = 0; i < n; i++){
            adjacencyList[i] = new ArrayList<>(); //For every node in the adjacencyList create an arrayList that will contain all of its neighbours.
        }
    }

    //Add an edge to a node
    private void addEdge(int i, int j){
        adjacencyList[i].add(j);
    }

    private void BreadthFirstSearch(int startingNode) {
        ArrayList<Integer> bfsPath = new ArrayList<>();// Create an array to hold the bfs results
        ArrayList<Integer> queue = new ArrayList<>(); //Create queue

        int[] parent = new int[numberOfNodes]; //Java by default sets all values to 0
        boolean[] marked = new boolean[numberOfNodes]; //Java by default sets all values to false

        marked[startingNode] = true; //Mark the started node as visited
        queue.add(startingNode); //Add it to the queue

        while (queue.size() != 0) { //While size is not empty
            int w = queue.get(0); //Get first element in queue
            queue.remove(0); //Remove the first element because we have visited
            bfsPath.add(w); //Add the current node to the bfsPath array

            for(int i = 0; i < adjacencyList[w].size(); i++){ //Loop through all neighbours
                int x = adjacencyList[w].get(i); //Get the neighbour of the current node
                if(!marked[x]){ //If the neighbour is not marked
                    marked[x] = true; //Mark it
                    parent[x] = w; //Set its parent to the current node
                    queue.add(x); //Add it to the queue
                }
            }
        }

        //Printing the BFS
        for(int i = 0; i < bfsPath.size(); i++){
            //This is to prevent trailing spaces
            if(i == bfsPath.size()-1){
                System.out.print(bfsPath.get(i));
            }
            else {
                System.out.print(bfsPath.get(i) + " ");
            }
        }
        System.out.print("\n");
    }

    private static void Dijkstra(int startingNode, int endingNode, int[][] weightedMatrix){

        //Declare stuff
        int max = Integer.MAX_VALUE;
        int length = weightedMatrix.length;
        int[] pathLengths = new int[length];
        boolean[] marked = new boolean[length];
        int[] parentNodes = new int[length];

        //Set the distances to infinity and set all the nodes to unmarked
        Arrays.fill(pathLengths, max);
        Arrays.fill(marked, false);

        //Distance between a node and itself is 0;
        pathLengths[startingNode] = 0;

        //Set the root of the starting node to -1
        parentNodes[startingNode] = -1;

        //This loop runs 
        for(int i = 1; i < length; i++){
            int closestNeighbour = -1;
            int minimumLength = max;
            for(int j = 0; j < length; j++){
                //If the node j is not yet visited
                if(!marked[j]){
                    //If the current length is less than the current minimum length then set the current nodes closest Neighbour to j with its respective length
                    if((pathLengths[j] < minimumLength)){
                        minimumLength = pathLengths[j];
                        closestNeighbour = j;
                    }
                }
            }
            
            //The closest Neighbour has been visited
            marked[closestNeighbour] = true;

            //This runs and recalculates the distance from the current node to its neighbours.
            for(int j = 0; j < length; j++){
                //Get the edge weight from the weightedMatrix
                int edgeWeight = weightedMatrix[closestNeighbour][j];
                //If there is an edge, check if the current smallest length plus the edge is less than the current path length. If it is update.
                if(Math.abs(edgeWeight) > 0){
                    if(((minimumLength + edgeWeight) < (pathLengths[j]))){
                        pathLengths[j] = minimumLength + edgeWeight;
                        parentNodes[j] = closestNeighbour;
                    }
                }
            }
        }

        //This array will hold the shortest path which will be printed
        ArrayList<Integer> path = new ArrayList<>();

        //Holder for the current node
        int temp = endingNode;

        //Add nodes to path from end til start
        while(temp != -1){
            path.add(temp);
            temp = parentNodes[temp];
        }

        //Reverse the path sequence
        Collections.reverse(path);

        //Check if the path exists
        if(path.get(0) != startingNode){
            System.out.print(-1 + "\n");
            System.out.print(0);
        }
        else{
            //Print out the shortest path
            for(int i = 0; i < path.size(); i++){
                //This is to prevent trailing spaces
                if(i == path.size()-1){
                    System.out.print(path.get(i));
                }
                else {
                    System.out.print(path.get(i) + " ");
                }
            }
            //Print the manhattan distance to the end node
            System.out.print("\n" + pathLengths[endingNode]);
        }
    }

public static void main(String[] args) {
    int[][] weightedMatrix = {
            {0, 0, 0, 4, 12, 14, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 12, 0, 8, 0, 18, 15, 7},
            {0, 0, 0, 11, 3, 3, 0, 0, 0, 0, 0, 13, 8, 10},
            {4, 0, 0, 0, 10, 12, 0, 0, 0, 0, 10, 10, 13, 0},
            {0, 0, 3, 10, 0, 2, 0, 0, 0, 0, 0, 10, 5, 11},
            {0, 0, 3, 12, 2, 0, 0, 0, 0, 0, 0, 10, 5, 9},
            {20, 0, 0, 0, 0, 0, 0, 14, 10, 20, 16, 22, 0, 0},
            {0, 12, 0, 0, 0, 0, 14, 0, 4, 6, 12, 0, 0, 0},
            {0, 16, 0, 0, 0, 0, 10, 4, 0, 10, 14, 20, 0, 0},
            {0, 8, 0, 0, 0, 0, 0, 6, 10, 0, 18, 0, 0, 15},
            {0, 0, 0, 10, 0, 0, 0, 12, 0, 0, 0, 6, 11, 11},
            {0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 6, 0, 5, 11},
            {0, 0, 8, 0, 5, 5, 0, 0, 0, 0, 11, 5, 0, 8},
            {0, 7, 10, 0, 11, 9, 0, 0, 0, 0, 0, 11, 8, 0},
    };

    int[][] adjacencyMatrix = new int[][]{
            { 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1},
            { 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1},
            { 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0},
            { 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1},
            { 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            { 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
            { 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0},
            { 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0},
            { 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1},
            { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1},
            { 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1},
            { 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0},
    };

    BFSandDijkstra graph = new BFSandDijkstra(adjacencyMatrix[0].length); //How many nodes there are

    //Add edges for every adjacency
    for(int i = 0; i < adjacencyMatrix[0].length; i++){
        for (int j = 0; j < adjacencyMatrix[0].length; j++){
            if(adjacencyMatrix[i][j] == 1){
                graph.addEdge(i,j);
            }
        }
    }

    //Do the BFS and Dijkstra searches
    graph.BreadthFirstSearch(0);
    Dijkstra(0,1, weightedMatrix);
    }
}
