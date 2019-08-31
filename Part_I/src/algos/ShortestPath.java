package algos;

import java.awt.Point;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ShortestPath {
    public static Stack<Integer> a_star( ArrayList<Point> nodes, ArrayList< ArrayList<Integer> > adj_list ) {
        // Number of nodes in the graph
        int n_nodes = nodes.size();

        // Parent array; the parent of node i is placed in index i.
        int[] parent = new int[ n_nodes ];

        // the actual cost of getting to a node
        double[] cost_so_far = new double[ n_nodes ], priority = new double[ n_nodes ];
        /*
           Initialise values of cost_so_far to -1 so that the array can function as
           a 'visited' array as well.
        */
        Arrays.fill( cost_so_far, -1 );

        /*
           Initialise fringe
        */
    	PriorityQueue<Integer> fringe = new PriorityQueue<Integer>(new Comparator<Integer>() {
    		public int compare (Integer a, Integer b) {
    			if ( priority[ a ] > priority[ b ] ) return 1;
    			if ( priority[ a ] < priority[ b ] ) return -1;
    			return 0;
    		}
    	});
        
        /*
           Add start to fringe, parent, and cost_so_far as the first node to be processed
           with a cost of zero and with it being its own parent.
        */
        fringe.add( 0 );
    	parent[ 0 ] = 0;
    	cost_so_far[ 0 ] = 0;
    	
    	while ( !fringe.isEmpty() ) {
            // the current node being processed is taken from the head of the queue
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
            
            // fetch all of the neighbours of the current node
    		ArrayList<Integer> neighbours = adj_list.get( current );
            
            // for every other node in the graph
    		for ( int neighbour: neighbours ) {
                double new_cost = cost_so_far[ current ] + cost( nodes.get( current ), nodes.get( neighbour ) );
                if ( ( cost_so_far[ neighbour ] == -1 )
                        || ( new_cost < cost_so_far[ neighbour ] ) ) {
                    cost_so_far[ neighbour ] = new_cost;
                    double estimated_dist = new_cost + heuristic( nodes.get( neighbour ), nodes.get( 1 ) );
                    priority[ neighbour ] = estimated_dist;
                    parent[ neighbour ] = current;
                    if ( !fringe.contains( neighbour ) ) {
                        fringe.add( neighbour );
                    }
                }
    		}
        }
        
        Stack<Integer> no_path = new Stack<Integer>(); 
        no_path.push( -1 );
        return no_path;
    }

    // helper function for a_star that computes Manhattan distance between nodes.
    public static double cost(Point a, Point b) {
        double cost = Math.abs( a.x - b.x ) + Math.abs( a.y - b.y );
        return cost;
    }

    // helper function for a_star that computes Euclidean distance between nodes.
    public static double heuristic(Point node, Point goal) {
    	double heuristic = Math.sqrt( Math.pow( node.x - goal.x, 2 ) + Math.pow( node.y - goal.y, 2 ) );
    	return heuristic;
    }
}