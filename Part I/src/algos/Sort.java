
package algos;

import utils.ArrayUtils;

public class Sort {
// bubble sort without escape condition
    public static int[] bubbleSortNEsc(int[] myList) {
        int n = myList.length;
        for (int i = n - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if ( myList[j] > myList[ j + 1 ] ) {
                    ArrayUtils.swop( myList, j, j + 1 );
                }
            }
        }

        return myList;
    }

// bubble sort with escape condition
    public static int[] bubbleSortEsc(int[] myList) {
        int n = myList.length, i = n - 1;
        boolean swopped;
        while ( i >= 1 ) {
            swopped = false;
            for( int j = 0; j < i ; ++j ) {
                if (myList[j] > myList[ j + 1 ]) {
                    ArrayUtils.swop( myList, j, j + 1 );
                    swopped = true;
                }
            }

            if (!swopped) {
                break;
            }

            i = i - 1;
        }
        
        return myList;
    }

// merge sort
    public static int[] mergeSort(int[] myList, int left, int right) {
        if ( right > left ) {
            int mid = Math.floorDiv( left + right, 2 );
            mergeSort( myList, left, mid );
            mergeSort( myList, mid + 1, right );
            
            /*
                difference between indices plus one is the number of elements from left to right,
                inclusive on both sides
            */
            int len_temp = ( right - left ) + 1;
            int[] temp = new int[ len_temp ];

            for ( int i = left; i <= mid; ++i ) {
                temp[ i - left ] = myList[ i ];
            }

            for ( int i = mid + 1; i <= right; ++i ) {

                /* 
                    NOTE: Fix bug; Has a problem with input of size one.
                */

                temp[ ( len_temp - 1 ) - i + mid + 1 ] = myList[ i ];
            }

            int i = 0;
            int j = len_temp - 1;

            for ( int k = left; k <= right; ++k ) {
                if ( temp[ i ] < temp[ j ] ) {
                    myList[ k ] = temp[ i ];
                    i += 1;
                } else {
                    myList[ k ] = temp[ j ];
                    j -= 1;
                }
            }

        }
        
        return myList;
    }

// quick sort
    public static void quickSort(double[] list, int left, int right) {
		if ( left < right ) {
			int pivot = partition( list, left, right );
			quickSort( list, left, pivot - 1 );
			quickSort( list, pivot + 1, right );
		}
	}

    // helper method for quick sort; determines index of partition
	public static int partition(double[] list, int left, int right) {
        double right_most = list[right], temp;;
        int i = left - 1;
		for( int j = left; j < right; j++ ) {
			if ( list[j] <= right_most ) {
				i += 1;
				temp = list[i];
				list[i] = list[j];
				list[j] = temp;
			}
		}
		temp = list[ i+1 ];
		list[ i+1 ] = list[right];
		list[right] = temp;
		return i + 1;
	}
}