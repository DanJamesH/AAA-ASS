package algos;

public class Search {
    // linear search, key is in the list
    public static int linearSearch(double[] myList, double key) {
        int index = 0, n = myList.length;

        while ( myList[index] != key ) {
            index += 1;
        }

        return index;
    }
}