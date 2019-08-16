package Graph;

import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.sound.midi.SysexMessage;

import java.awt.Point;

import utils.PointUtils;

import algos.Sort;
import algos.Search;

public class PRM {

    public static double[][] generate() {
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

        // remove samples that overlap with obstacles and update n_samples accordingly
        removeSamples(samples, top_left, bottom_right);
        n_samples = samples.size();

        // initialise adjacency matrix adj; fill with zeros
        double[][] adj = new double[n_samples][n_samples];
        for (double[] row: adj) {
            Arrays.fill(row, 0);
        }
        /*
         - Create adjacency between every node
         - For each adjacency in adj, add the distance between the two nodes
        */
        for ( int i = 0; i < n_samples - 1; ++i ) {
            for ( int j = i + 1; j < n_samples; ++j ) {
                if ( i == j ) {
                    adj[i][j] = 0;
                } else {
                    adj[i][j] = PointUtils.distance( samples.get(i), samples.get(j) );
                    adj[j][i] = adj[i][j];
                }
            }
        }

        /*
         - Find k nearest neighbours by sorting each row so that the first k values (after 
           the first which is zero; distance between a node and itself) are the distances 
           to the k nearest neighbours.
         - Set the value in the adjacency matrix of each neighbour to negative one.
        */
        for ( double[] row: adj ) {
            int n_neighbours = n_samples - 1;
            while ( n_neighbours > k ) {
                double max = Arrays.stream( row ).max().getAsDouble();
                row[ Search.linearSearch(row, max) ] = 0;
                --n_neighbours;
            }
            
            for ( int i = 0; i < n_samples; ++i ) {
                if ( row[i] != 0 ) {
                    row[i] = 1;
                }
            }
        }

        // remove edges that overlap obstacles
        removeEdges(adj, samples, top_left, bottom_right);

        for (double[] row: adj) {
            String x = Arrays.toString( row );
            String y = x.replace(".0", "");
            System.out.println( y );
        }

        in.close();

        return adj;
    }

    // helper function for generate; removes samples that removes samples that overlap obstacles
    public static void removeSamples(ArrayList<Point> samples,  ArrayList<Point> top_left,  ArrayList<Point> bottom_right) {
        ArrayList<Point> samples_clone = (ArrayList<Point>) samples.clone();
        // number of obstacles = number of top-left corners = number of bottom-right corners
        int n_obst = top_left.size();
        for ( Point sample: samples_clone ) {
            for ( int i = 0; i < n_obst; ++i ) {
                /*
                  if a samples x and y coordinates are between the top-left and bottom-right corners' x and y
                  coordinates remove the sample.
                */
                if ( sample.x >= top_left.get(i).x && sample.x <= bottom_right.get(i).x ) {
                    if ( sample.y >= top_left.get(i).y && sample.y <= bottom_right.get(i).y ) {
                        samples.remove( sample );
                        break;
                    }
                }

            }
        }
    }

    /*
       - Check if the lines between two nodes crosses any of the 
       boundary lines of each obstacle. If it does. remove the
       edge between them.
       - Represent the line as a function of x [line_0] and evaluate it at
       each boundary x-value. Remove an edge if the output value
       of the line is in between the boundary y-values of
       the obstacle.
       - Represent the line as a function of y [line_1] and evaluate it at
       each boundary y-value. Remove an edge if the output value
       of the line is in between the boundary x-values of
       the obstacle.
    */
    public static void removeEdges(double[][] adj, ArrayList<Point> nodes, ArrayList<Point> top_left,
            ArrayList<Point> bottom_right) {
        int n_nodes = adj.length, n_obst = top_left.size();
        for ( int i = 0; i < n_nodes; ++i ) {
            for ( int j = i + 1; j < n_nodes; ++j ) {
                if ( adj[i][j] == 0 ) {
                    continue;
                }

                // get the nodes that make the adjacency
                Point a = nodes.get(i);
                Point b = nodes.get(j);

                // top-left and bottom-right corners of the rectangle formed by nodes a and b
                Point edge_tl = new Point( Math.min( a.x, b.x ), Math.min( a.y, b.y ) );
                Point edge_br = new Point( Math.max( a.x, b.x ), Math.max( a.y, b.y ) );


                /*
                 - if y values are equal, check that the value is within the horizontal boundaries of
                   the obstacle and that the x values are outside of the vertical boundaries of the
                   obstacle.
                 - if x values are equal, check that the value is within the vertical boundaries of
                   the obstacle and that the y values are outside of the horizontal boundaries of the
                   obstacle.
                */
                if ( a.y == b.y ) {
                    for ( int k = 0; k < n_obst; ++k ) {
                        if ((top_left.get(k).y <= a.y && a.y <= bottom_right.get(k).y)
                                && (a.x <= top_left.get(k).x && bottom_right.get(k).x <= a.x)) {
                            adj[i][j] = 0;
                            adj[j][i] = 0;
                            break;
                        }
                    }
                } else if ( a.x == b.x ) {
                    for ( int k = 0; k < n_obst; ++k ) {
                        if ((top_left.get(k).x <= a.x && a.x <= bottom_right.get(k).x)
                                && (a.y <= top_left.get(k).y && bottom_right.get(k).y <= a.y)) {
                            adj[i][j] = 0;
                            adj[j][i] = 0;
                            break;
                        }
                    }
                } else {
                    // slope of the line_0
                    double m_y = (double) (b.y - a.y) / (b.x - a.x);
                    // slope of the line_1
                    double m_x = (double) (b.x - a.x) / (b.y - a.y);
    
                    // y intercept of the line_0
                    double c_y = b.y - b.x * m_y;
                    // x intercept of the line_1
                    double c_x = b.x - b.y * m_x;
    
                    /*
                       for every obstacle, check if the output of each line at each boundary value
                       is within the relevant boundary range (i.e. y-range of obstacle for line_0
                       and x-range of obstacle for line_1)
                    */
                    for ( int k = 0; k < n_obst; ++k ) {
                        double x_left = top_left.get(k).x,
                               x_right = bottom_right.get(k).x,
                               y_top = top_left.get(k).y,
                               y_bottom = bottom_right.get(k).y;

                        /*
                           The obstacle must intersect the rectangle created by the nodes. If this isn't the
                           case, then the line through the points may intersect the obstacle elsewhere i.e.
                           another part of the line which isn't the edge between the nodes overlaps the ob-
                           stacle.
                        */
                        if ((edge_tl.y <= y_top && y_top <= edge_br.y)
                                || (edge_tl.y <= y_bottom && y_bottom <= edge_br.y)
                                || (edge_tl.x <= x_left && x_left <= edge_br.x)
                                || (edge_tl.x <= x_right && x_right <= edge_br.x)) {
                                    
                            // evaluate line_0 at boundary x-values
                            double y_0 = m_y * x_left + c_y;
                            double y_1 = m_y * x_right + c_y;
        
                            // evaluate line_1 at boundary y-values
                            double x_0 = m_x * y_top + c_x;
                            double x_1 = m_x * y_bottom + c_x;
        
        
                            if ((y_top <= y_0 && y_0 <= y_bottom)
                                    || (y_top <= y_1 && y_1 <= y_bottom)
                                    || (x_left <= x_0 && x_0 <= x_right)
                                    || (x_left <= x_1 && x_1 <= x_right)) {
    
                                adj[i][j] = 0;
                                adj[j][i] = 0;
                                break;
                            }

                        }
                    }
                }
            }
        }
    }
}