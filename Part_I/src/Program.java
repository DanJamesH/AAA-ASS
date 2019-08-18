import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.stream.Collectors;

import java.awt.Point;

import Graph.PRM;
import Graph.BFSandDijkstra;

import java.util.Arrays;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        /*
         - read first line of input; convert each to integer
         - convert result of split to stream in order to use stream class's map function
         - collect result in a list
        */
        List<Integer> input = Arrays.stream( in.nextLine().split(" ") ).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        
        // assign first line of input to variables
        int k = input.get(0), 
            n_obstacles = input.get(1), 
            n_samples = input.get(2), 
            dim = input.get(3); 
        
        // get start; x is column and y is row in the adjacency matrix
        String[] start_string = in.nextLine().split(",");
        Point start = new Point( Integer.parseInt( start_string[0] ), Integer.parseInt( start_string[1] ) );

        // get end
        String[] end_string = in.nextLine().split(",");
        Point end = new Point( Integer.parseInt( end_string[0] ), Integer.parseInt( end_string[1] ) );

        // read obstacles

        /*
         - Collection of coordinates of top-left and bottom-right of obstacles as
           two parallel lists. One list contains the coordinates of the top left
           corner of each obstacle while the other contains the coordinates of the
           bottom right corner fo each obstacle
        */

        // collection of the coordinates of the top-left corners of every rectangle
        ArrayList<Point> top_left = new ArrayList<Point>();

        // / collection of the coordinates of the bottom-right corners of every rectangle
        ArrayList<Point> bottom_right = new ArrayList<Point>();
        for ( int i = 0; i < n_obstacles; ++i ) {
            // read top left and bottom right points as strings
            String[] obst_points = in.nextLine().split(";");
            
            // split around the comma; convert results to integers
            // components of the top-left corner's coordinates
            List<Integer> top_left_comps = Arrays.stream( obst_points[0].split(",") ).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            
            // components of the bottom-right's corner's coordinates
            List<Integer> bottom_right_comps = Arrays.stream( obst_points[1].split(",") ).map(s -> Integer.parseInt(s)).collect(Collectors.toList());

            // point holding the coordinates of the top-left corner
            Point top_left_point = new Point( top_left_comps.get(0), top_left_comps.get(1) );

            // point holding the coordinates of the bottom-right corner
            Point bottom_right_point = new Point( bottom_right_comps.get(0), bottom_right_comps.get(1) );

            // add above points to the relevant ArrayList
            top_left.add( top_left_point );
            bottom_right.add( bottom_right_point );
        }

        // read samples
        
        // collection of samples points as points
        ArrayList<Point> samples = new ArrayList<Point>();
        // add start and end as first and second nodes respectively
        samples.add( start );
        samples.add( end );
        for ( int i = 0; i < n_samples; ++i) {
            // read point; split around ',' and convert each coordinate to integer. Collect results in a list
            List<Integer> sample = Arrays.stream( in.nextLine().split(",") ).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            
            samples.add ( new Point( sample.get(0), sample.get(1) ) );
        }
        // increase n_samples to include start and end
        n_samples += 2;



        PRM prm = new PRM( samples, top_left, bottom_right, k, n_obstacles, n_samples, dim );

        int[][] weightedMatrix = prm.manhattanAdj();

        int[][] adjacencyMatrix = prm.get_adjacency();

        BFSandDijkstra graph = new BFSandDijkstra( prm.get_n_nodes() );

        for(int i = 0; i < adjacencyMatrix[0].length; i++){
            for (int j = 0; j < adjacencyMatrix[0].length; j++){
                if(adjacencyMatrix[i][j] == 1){
                    graph.addEdge(i,j);
                }
            }
        }


        System.out.println("\nOutput of BFS:");
        graph.BreadthFirstSearch(0);

        System.out.println("\nOutput of Dijkstra's Algorithm: ");
        graph.Dijkstra(0,1, weightedMatrix);



        in.close();
    }
}