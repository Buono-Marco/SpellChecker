package com.marcobuono.sortingalgorithms;
import java.util.Arrays;

/**
 * Calculate the time it takes to sort arrays
 * with increasing lengths and different sorting methods
 * 
 * The values ​​of the array lengths are: 1000, 10000, and 100000
 * 
 * @author marcobuono
 *
 */
public class SortingAlgorithms {
	
	final static int oneThousand = 1000;
	final static int tenThousand = 10000;
	final static int oneHundredThousand = 100000;
	
	public static void main(String[] args) {
		int[] firstArray;
		int[] secondArray;
		
		System.out.println("Start first set ******");
		firstArray = populateArray(oneThousand);
		secondArray = firstArray.clone();
		sortAndCalculateTime(firstArray, secondArray);
		System.out.println("End first set ******");
		
		
		System.out.println("Start second set ******");
		firstArray = populateArray(tenThousand);
		secondArray = firstArray.clone();
		sortAndCalculateTime(firstArray, secondArray);
		System.out.println("End second set ******");
		
		
		System.out.println("Start third set ******");
		firstArray = populateArray(oneHundredThousand);
		secondArray = firstArray.clone();
		sortAndCalculateTime(firstArray, secondArray);
		System.out.println("End third set ******");
		
	}
	
	/**
	 * Order the two input array with different sort method and
	 * calculate time for each one
	 * @param A, the array to sort with Insertion or Selection method
	 * @param B, the array to sort with the Arrays.sort() method
	 * The Insertion or Selection method is chosen randomly
	 * 
	 */
	static void sortAndCalculateTime(int[] A, int[] B) {
		// A random integer in the range 0 to 1
		int sortNum = (int)(Math.random() * 2);
		
		// if sortNum is 0 the Insertion method is used, otherwise the Selection one
		if(sortNum == 0) {
			// Take the initial time
			long startTime = System.currentTimeMillis();
			// sort the A array with Insertion method
			insertionSort(A);
			// Calculate how much time has passed
			long runTime = (System.currentTimeMillis() - startTime);
			System.out.println("Time in millisecond to sort array with insertionSort(): " + runTime);
		} else {
			// Take the initial time
			long startTime = System.currentTimeMillis();
			// sort the A array with Selection method
			selectionSort(A);
			// Calculate how much time has passed
			long runTime = (System.currentTimeMillis() - startTime);
			System.out.println("Time in millisecond to sort array with selectionSort(): " + runTime);
		}
		
		// Take the initial time
		long startTime = System.currentTimeMillis();
		// sort the B array with Arrays.sort() method
		Arrays.sort(B);
		// Calculate how much time has passed
		long runTime = (System.currentTimeMillis() - startTime);
		System.out.println("Time in millisecond to sort array with Arrays.sort(): " + runTime);
	}
	
	/**
	 * Populate an array of the entered length
	 * @param lenght
	 * @return an array
	 */
	static int[] populateArray(int lenght) {
		int[] A = new int[lenght];
		
		for(int i=0; i < A.length; i++) {
			A[i] = (int)(Integer.MAX_VALUE * Math.random());
		}
		
		return A;
	}
	
	/**
	 * Sort the array A into increasing order
	 * @param A, The int array to be sorted
	 * @author Eck, D. J.
	 * Eck, D. J. (2019). Introduction to programming using Java, version 8.1.
	 * Hobart and William Smith Colleges. http://math.hws.edu/javanotes
	 * 
	 */
	static void insertionSort(int[] A) {
	      // Sort the array A into increasing order.
	      
	   int itemsSorted; // Number of items that have been sorted so far.

	   for (itemsSorted = 1; itemsSorted < A.length; itemsSorted++) {
	         // Assume that items A[0], A[1], ... A[itemsSorted-1] 
	         // have already been sorted.  Insert A[itemsSorted]
	         // into the sorted part of the list.
	         
	      int temp = A[itemsSorted];  // The item to be inserted.
	      int loc = itemsSorted - 1;  // Start at end of list.
	      
	      while (loc >= 0 && A[loc] > temp) {
	         A[loc + 1] = A[loc]; // Bump item from A[loc] up to loc+1.
	         loc = loc - 1;       // Go on to next location.
	      }
	      
	      A[loc + 1] = temp; // Put temp in last vacated space.
	   }
	}
	
	/**
	 * Sort the array A into increasing order
	 * @param A, The int array to be sorted
	 * @author Eck, D. J.
	 * Eck, D. J. (2019). Introduction to programming using Java, version 8.1.
	 * Hobart and William Smith Colleges. http://math.hws.edu/javanotes
	 * 
	 */
	static void selectionSort(int[] A) {
	      // Sort A into increasing order, using selection sort
	      
	   for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
	         // Find the largest item among A[0], A[1], ...,
	         // A[lastPlace], and move it into position lastPlace 
	         // by swapping it with the number that is currently 
	         // in position lastPlace.
	         
	      int maxLoc = 0;  // Location of largest item seen so far.
	      
	      for (int j = 1; j <= lastPlace; j++) {
	         if (A[j] > A[maxLoc]) {
	               // Since A[j] is bigger than the maximum we've seen
	               // so far, j is the new location of the maximum value
	               // we've seen so far.
	            maxLoc = j;  
	         }
	      }
	      
	      int temp = A[maxLoc];  // Swap largest item with A[lastPlace].
	      A[maxLoc] = A[lastPlace];
	      A[lastPlace] = temp;
	      
	   }  // end of for loop
	   
	}

}