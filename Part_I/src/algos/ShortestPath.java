package algos;

import java.awt.Point;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ShortestPath {
    public static Stack<Integer> a_star( ArrayList<Point> nodes, int[][] adjacency ) {
        int n_nodes = nodes.size();
        int[] parent = new int[ n_nodes ];
        double[] cost_so_far = new double[ n_nodes ], priority = new double[ n_nodes ];

        Arrays.fill( cost_so_far, -1 );

    	PriorityQueue<Integer> fringe = new PriorityQueue<Integer>(new Comparator<Integer>() {
    		public int compare (Integer a, Integer b) {
    			if ( priority[ a ] > priority[ b ] ) return 1;
    			if ( priority[ a ] < priority[ b ] ) return -1;
    			return 0;
    		}
    	});
    	
    	fringe.add( 0 );
    	parent[ 0 ] = 0;
    	cost_so_far[ 0 ] = 0;
    	
    	while ( !fringe.isEmpty() ) {
    		int current = fringe.poll();
    		
    		if ( current == 1  ) {
                Stack<Integer> path = new Stack<Integer>();
                path.push( 1 );
                while ( parent[ path.peek() ] != 0) {
                    path.push( parent[ path.peek() ] );
                }
                path.push( 0 );
    			return path;
    		}
    		
    		int[] neighbours = adjacency[ current ];
    		
    		for ( int i = 0; i < n_nodes; ++i ) {
                if ( neighbours[ i ] != 0 ) {
                    int neighbour = i;
                    double new_cost = cost_so_far[ current ] + cost( nodes.get( current ), nodes.get( neighbour ) );
                    if ( ( cost_so_far[ neighbour ] == -1 )
                            || ( new_cost < cost_so_far[ neighbour ] ) ) {
                        cost_so_far[ neighbour ] = new_cost;
                        double estimated_dist = new_cost + heuristic( nodes.get( neighbour ), nodes.get( 1 ) );
                        priority[ neighbour ] = estimated_dist;
                        fringe.add(neighbour);
                        parent[ neighbour ] = current;
                    }
                }
    		}
        }
        
        Stack<Integer> no_path = new Stack<Integer>(); 
        no_path.push( -1 );
        return no_path;
    }

    // helper function for a_star that computes Euclidean distance between nodes.
    public static double cost(Point a, Point b) {
    	double cost = Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    	return cost;
    }

    // helper function for a_star that computes Euclidean distance between nodes.
    public static double heuristic(Point node, Point goal) {
        return Math.abs(node.x - goal.x) + Math.abs(node.y - goal.y);
    }
}