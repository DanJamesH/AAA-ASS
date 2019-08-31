import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.Stack;

import java.awt.Point;

import algos.ShortestPath;

import Graph.BFSandDijkstra;
import Graph.PRM;

import utils.Results;




public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner( System.in );

        System.out.print("Input Largest Power Of 10: ");
        int max_n = in.nextInt(), n_tests = 100;

        Random random = new Random();

        for ( int pow = 1; pow <= max_n; ++pow ) {
            for ( int k = 1; k < 10; ++k ) {
                int n_samples = ( int ) Math.pow( 10, pow ) * k;

                System.out.println( "Beginning Experiment Of Size: " + n_samples + "\n" );

                ArrayList<Point> samples = new ArrayList<Point>();
                for ( int i = 0; i < n_samples; ++i) {
                    samples.add( new Point( random.nextInt( 100 ), random.nextInt( 100 ) ) );
                }

                PRM prm = new PRM( samples, n_samples );

                ArrayList< ArrayList<Integer> > adj_list = prm.get_adjList();
                ArrayList<Point> nodes = prm.get_nodes();

                Stack<Integer> s = new Stack< Integer >();
                double ave_time = 0;
                for ( int j = 0; j < n_tests; ++j ) {
                    double start = System.nanoTime();
                    s = ShortestPath.a_star( nodes, adj_list );
                    ave_time += ( System.nanoTime() - start ) / n_tests;
                }

                while ( !s.empty() ) {
                    System.out.print(s.pop() + " ");
                }
                System.out.println("\n");

                System.out.println( "Average Time For Size " + n_samples + ": " + ave_time + "\n" );

                System.out.println( "Appending  Results To Data File" );
                Results.write( n_samples, ave_time, "AAA_ASS.csv", "time" );
                
                System.out.println( "\n#################### Input Of Size " + n_samples + " DONE!!! ####################\n" );

            }
        }

        System.out.println( "\n#################### All Inputs DONE!!! ####################\n" );
        
        in.close();
    }
}
