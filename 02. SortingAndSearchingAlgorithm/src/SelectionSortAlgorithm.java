import java.util.Arrays;

/**
 * Selection Sort is simpliest sort algorithm, it is not very efficient
 * 	1. Select one element at the time and compares it to all other elements in the list
 * 	2. the correct position for the selected element is found before moving on to the next element
 * 
 * Pro : 	simple logic and No need extra space
 * Cons :   Require number of comparison time for iteration = (N-1) + (N-2) + (N-3) .. (1) = N^2 
 * 			time complexity =  O(N^2) for swaps and comparison
 * 			not stable sort  = entities that are equal might be re-arrange 
 * 			not adaptive sort = need to iterate through the end 
 *  
 * example :
 * 
	Do selection sorting ( make sure first element iterate until the last element having ascending value, the early element should have less value from rest and next value )
	[2, 10, 1, 7, 5, 6, 3, 8, 4, 9]		.. check idx 1 : [2, 10,  1, ...] 				initial,  
	[1, 10, 2, 7, 5, 6, 3, 8, 4, 9]		.. check idx 1 : [1, 10,  2, ...] 				swap 3rd with 1st index 
	[1, 2, 10, 7, 5, 6, 3, 8, 4, 9]		.. check idx 2 : [..  2, 10,  7,  5, ...]			swap 3rd with 2nd index 
	[1, 2, 7, 10, 5, 6, 3, 8, 4, 9]		.. check idx 3 : [....... 7, 10,  5, ...]			swap 4th with 3rd index
	[1, 2, 5, 10, 7, 6, 3, 8, 4, 9]		.. check idx 3 : [....... 5, 10,  7,  6,  3, ...]	swap 5th with 3rd index  
	[1, 2, 3, 10, 7, 6, 5, 8, 4, 9]		.. check idx 3 : [....... 3, 10,  7,  6,  5, ...] 	swap 7th with 3rd index
	[1, 2, 3, 7, 10, 6, 5, 8, 4, 9]		.. check idx 4 : [..........  7, 10,  6,  5, ...]		 
	[1, 2, 3, 6, 10, 7, 5, 8, 4, 9]		.. check idx 4 : [..........  6, 10,  7,  5, ...]	 
	[1, 2, 3, 5, 10, 7, 6, 8, 4, 9]		.. check idx 4 : [..........  5, 10,  7,  6,  4, ...]	 
	[1, 2, 3, 4, 10, 7, 6, 8, 5, 9]		.. check idx 4 : [..........  4, 10,  7,  6,  5, ...]
	[1, 2, 3, 4, 7, 10, 6, 8, 5, 9]		.. check idx 5 : [..............  7, 10,  6,  5, ...]
	[1, 2, 3, 4, 6, 10, 7, 8, 5, 9]		.. check idx 5 : [..............  6, 10,  7,  8,  5, ...]
	[1, 2, 3, 4, 5, 10, 7, 8, 6, 9]		.. check idx 5 : [..............  5, 10,  7,  8,  6, ...]
	[1, 2, 3, 4, 5, 7, 10, 8, 6, 9]		.. check idx 6 : [..................  7, 10,  8,  6, ...]
	[1, 2, 3, 4, 5, 6, 10, 8, 7, 9]		.. check idx 6 : [..................  6, 10,  8,  7, ...]
	[1, 2, 3, 4, 5, 6, 8, 10, 7, 9]		.. check idx 7 : [......................  8, 10,  7, ...]
	[1, 2, 3, 4, 5, 6, 7, 10, 8, 9]		.. check idx 7 : [......................  7, 10,  8, ...]
	[1, 2, 3, 4, 5, 6, 7, 8, 10, 9]		.. check idx 8 : [..........................  8, 10,  9]
	[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]		.. check idx 9 : [..............................  9, 10]
 *
 */
public class SelectionSortAlgorithm {
	
	
	public static void main(String[] args) {
//		int[] data = new int[] {15, 32, 26, 11, 36, 19, 42, 44 ,14};
		int[] data = new int[] {10, 2, 1, 7, 5, 6, 3, 8, 4, 9};
		
		System.out.println("before sorting");
		System.out.println(Arrays.toString(data));
		
		System.out.println("after sorting");
		selectionSort(data);
		System.out.println(Arrays.toString(data));
		
	}
	
	public static void selectionSort(int[] data) {
		if (data == null) return;
		for (int i = 0; i < data.length-1; i++) {
			for (int j = i + 1; j < data.length; j++) {
				if ( data[i] > data[j]) { 
					swap(data, i, j);
					System.out.println("Loop " + (i+1) + "\t" + Arrays.toString(data));
				}
			}
		}
	}
	
	public static void swap(int[] list, int i, int j) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}
	

}