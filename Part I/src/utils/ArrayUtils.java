package utils;

public class ArrayUtils {
    public static void swop(int [] list, int first, int second) {
        int temp = list[first];
        list[first] = second;
        list[second] = temp;
    }

    public static void swop(double [] list, int first, int second) {
        double temp = list[first];
        list[first] = second;
        list[second] = temp;
    }
}