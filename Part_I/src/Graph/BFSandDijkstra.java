package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class BFSandDijkstra {

    private int numberOfNodes; //Number of nodes
    private ArrayList<Integer>[] adjacencyList; //adjacencyList

    public BFSandDijkstra(int n){
        numberOfNodes = n;
        adjacencyList = new ArrayList[n];
        for(int i = 0; i < n; i++){
            adjacencyList[i] = new ArrayList<>(); //For every node in the adjacencyList create an arrayList that will contain all of its neighbours.
        }
    }

    //Add an edge to a node
    public void addEdge(int i, int j){
        adjacencyList[i].add(j);
    }

    public void BreadthFirstSearch(int startingNode) {
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

    public void Dijkstra(int startingNode, int endingNode, int[][] weightedMatrix){

        //Declare stuff
        int length = weightedMatrix.length;
        int max = Integer.MAX_VALUE;
        int[] pathLengths = new int[length];
        boolean[] marked = new boolean[length];
        int[] parentNodes = new int[length];
        int[] tempArray = new int[length];

        //Set the distances to infinity and set all the nodes to unmarked
        Arrays.fill(pathLengths, max);

        parentNodes[startingNode] = -1;
        pathLengths[startingNode] = 0;

        //This loop runs until graph is fully updated
        for(int i = 1; i < length; i++){
            int minimumLength = max;
            int closestNeighbour = -1;
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

            //This runs and recalculates the distance from the current node to its neighbours.
            //The closest Neighbour has been visited
            tempArray[closestNeighbour] = 1;
            marked[closestNeighbour] = true;
            for(int j = 0; j < length; j++){
                //Get the edge weight from the weightedMatrix
                int edgeWeight = weightedMatrix[closestNeighbour][j];
                //If there is an edge, check if the current smallest length plus the edge is less than the current path length. If it is, then update.

                if(Math.abs(edgeWeight) > 0){
                    if(((minimumLength + edgeWeight) < (pathLengths[j]))){
                        pathLengths[j] = minimumLength + edgeWeight;
                        parentNodes[j] = closestNeighbour;
                    }
                }
            }
        }

        ArrayList<Integer> path = new ArrayList<>(); //This array will hold the shortest path which will be printed
        int temp = endingNode; //Holder for the current node
        //Add nodes to path from end til start
        while(temp != -1){
            path.add(temp);
            temp = parentNodes[temp];
        }
        Collections.reverse(path); //Reverse the path sequence

        //Check if the path exists
        if(path.get(0) != startingNode || tempArray[0] != 1){
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
            System.out.print("\n" + pathLengths[endingNode] + "\n");  //Print the manhattan distance to the end node
        }
    }
}
