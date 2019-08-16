package Graph;

import java.util.LinkedList;

public class Graph {

    private int nv; //Number of vertices
    private LinkedList<Integer>[] adjacencyList; //adjacencyList

    //Constructor
    private Graph(int n){
        nv = n;
        adjacencyList = new LinkedList[n];
        for(int i=0; i<n; i++){
            adjacencyList[i] = new LinkedList<>(); //For every node in the adjacencylist create a linkedlist that will contain all of its neighbours.
        }
    }

    private void addEdge(int i, int v){
        adjacencyList[i].add(v);
    } //Add an edge to a node

    private void BFS(int startingNode) {
        LinkedList<Integer> queue = new LinkedList<Integer>(); //Create queue

        int[] parent = new int[nv]; //Java by default sets all values to 0
        boolean[] marked = new boolean[nv]; //Java by default sets all values to false

        marked[startingNode] = true; //Mark the started node as visited
        queue.add(startingNode); //Add it to the queue

        while (queue.size() != 0) { //While size is not empty
            int w = queue.getFirst(); //Get first element in queue
            queue.removeFirst(); //Remove the first element because we have visited
            System.out.print(w + " "); //Print the value of the current node


            for(int i = 0; i < adjacencyList[w].size(); i++){ //Loop through all neighbours
                int x = adjacencyList[w].get(i); //Get the neighbour of the current node
                if(!marked[x]){ //If the neighbour is not marked
                    marked[x] = true; //Mark it
                    parent[x] = w; //Set its parent to the current node
                    queue.add(x); //Add it to the queue
                }
            }
        }
    }


public static void main(String[] args) {
    Graph g = new Graph(14); //How many nodes there are

    int[][] multi = new int[][]{
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

    for(int i = 0; i < multi[0].length; i++){
        for (int j = 0; j < multi[0].length; j++){
            if(multi[i][j] == 1){
                g.addEdge(i,j);
            }
        }
    }



    //g.addEdge(0, 1);
    //g.addEdge(0, 2);
   // g.addEdge(1, 2);
    //g.addEdge(2, 0);
    //g.addEdge(2, 3);
    //g.addEdge(3, 2);

    System.out.println("Following is Breadth First Traversal ");

    g.BFS(0);
    }
}
