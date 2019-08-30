package utils;

import java.io.FileWriter;
import java.io.File;

public class Results {
    public static boolean write(int input, double output, String filename, String output_name) {
        try {
            // if file doesn't exist, create file and add CSV column headers before writing data
            if ( !(new File( filename ).exists()) ) {
                FileWriter data_file = new FileWriter( filename, true );
                data_file.write("input," + output_name + "\n" + input + "," + output );
                data_file.close();
            } else {
                FileWriter data_file = new FileWriter(filename, true);
                data_file.write( "\n" + input + "," + output );
                data_file.close();
            }
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public static boolean write(int input, String output, String filename) {
        try {
            // if file doesn't exist, create file and add CSV column headers before writing data
            if ( !(new File( filename ).exists()) ) {
                FileWriter data_file = new FileWriter( filename, true );
                data_file.write("input,comparisons\n" + input + "," + output );
                data_file.close();
            } else {
                FileWriter data_file = new FileWriter(filename, true);
                data_file.write( "\n" + input + "," + output );
                data_file.close();
            }
        } catch (Exception e) {
            return false;
        }


        return true;
    }
}