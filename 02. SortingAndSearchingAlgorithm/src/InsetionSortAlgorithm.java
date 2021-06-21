import java.util.Arrays;

/**
 * Insertion Sort : By inserting into as sorted sub-list at every step, the sub-list soon grows to be te entire list
 *					this sort first assumes a sorted list of size 1 and inserts additional elements in the right position
 * time complexity =  O(N^2) for comparation and swaps = same as Selection Sort and Bubbile sort
 * 
 *  
 * Pros : 	stable sort - as entities entities bubble to the correct position in the sub-list that sorted, the original order of entities is maintained for equal elements
 * 		  	adaptive sort =  nearly sorted lists complete very quickly
 * 			has very low overheads and is traditionally the sort of choice when used with faster algorithms that follows the divide and conquer approach
 * 			
 * 			bubble sort requires an additional pass over all elements to ensure tat the list is fully sorted
 * 			insertion sort can stop comparing elements when the right position in the sorted list is found
 * 
 * 			bubble sort has to do O(N) comparisons at every step
 * 			insertion sort has to do O(N) comparisons at every stop but it is possible to stop early	 
 * 
 * Cons : it takes extra space, it sorts in place
 * 
 * example 
 * 
 * do Insertion Sort : 
	[10, 2, 1, 7, 5, 6, 3, 8, 4, 9]		.. check idx 1 : [10,  2,  ............................]  swap idx 1 with idx 2 ( 10 -> 2 )			
	[2, 10, 1, 7, 5, 6, 3, 8, 4, 9]		.. check idx 2 : [... 10,  1 ..........................]  swap idx 2 with idx 3 ( 10 -> 1 )		
	[2, 1, 10, 7, 5, 6, 3, 8, 4, 9]		.. check idx 1 : [ 2,  1, .............................]  swap idx 1 with idx 2 (  2 -> 1 )
	[1, 2, 10, 7, 5, 6, 3, 8, 4, 9]		.. check idx 3 : [ 1,  2, 10,  7 ......................]  swap idx 3 with idx 4 ( 10 -> 7 )
	[1, 2, 7, 10, 5, 6, 3, 8, 4, 9]		.. check idx 4 : [ ......  7, 10, 5 ...................]  swap idx 4 with idx 5 ( 10 -> 5 )
	[1, 2, 7, 5, 10, 6, 3, 8, 4, 9]		.. check idx 3 : [ 1,  2,  7,  5, 10, .................]  swap idx 3 with idx 4 (  7 -> 5 )
	[1, 2, 5, 7, 10, 6, 3, 8, 4, 9]		.. check idx 5 : [........ 5,  7, 10,  6 ..............]  swap idx 5 with idx 6 ( 10 -> 6 )
	[1, 2, 5, 7, 6, 10, 3, 8, 4, 9]		.. check idx 4 : [ 1,  2,  5,  7,  6, 10,  ............]  swap idx 4 with idx 6 (  7 -> 6 )
	[1, 2, 5, 6, 7, 10, 3, 8, 4, 9]		.. check idx 6 : [............ 6,  7, 10, 3 ...........]  swap idx 6 with idx 7 ( 10 -> 3 )
	[1, 2, 5, 6, 7, 3, 10, 8, 4, 9]		.. check idx 5 : [ 1,  2,  5,  6,  7,  3, 10 ..........]  swap idx 5 with idx 6 (  7 -> 3 )
	[1, 2, 5, 6, 3, 7, 10, 8, 4, 9]		.. check idx 4 : [ 1,  2,  5,  6,  3,  7, .............]  swap idx 4 with idx 5 (  6 -> 3 )
	[1, 2, 5, 3, 6, 7, 10, 8, 4, 9]		.. check idx 3 : [ 1,  2,  5,  3,  6, .................]  swap idx 3 with idx 4 (  5 -> 3 )
	[1, 2, 3, 5, 6, 7, 10, 8, 4, 9]		.. check idx 7 : [........ 3,  5,  6,  7, 10, 8 .......]  swap idx 7 with idx 8 ( 10 -> 8 )
	[1, 2, 3, 5, 6, 7, 8, 10, 4, 9]		.. check idx 8 : [........................ 8, 10, 4....]  swap idx 8 with idx 9 ( 10 -> 4 )
	[1, 2, 3, 5, 6, 7, 8, 4, 10, 9]		.. check idx 7 : [ 1,  2,  3,  5,  6,  7,  8,  4, 10 ..]  swap idx 7 with idx 8 (  8 -> 4 )
	[1, 2, 3, 5, 6, 7, 4, 8, 10, 9]		.. check idx 6 : [ 1,  2,  3,  5,  6,  7,  4,  8, 10 ..]  swap idx 6 with idx 7 (  7 -> 4 )
	[1, 2, 3, 5, 6, 4, 7, 8, 10, 9]		.. check idx 5 : [ 1,  2,  3,  5,  6,  4,  7,  8, .....]  swap idx 5 with idx 6 (  6 -> 4 )
	[1, 2, 3, 5, 4, 6, 7, 8, 10, 9]		.. check idx 4 : [ 1,  2,  3,  5,  4,  6,  7, .........]  swap idx 4 with idx 5 (  5 -> 4 )
	[1, 2, 3, 4, 5, 6, 7, 8, 10, 9]		.. check idx 9 : [............ 4,  5,  6,  7,  8, 10, 9]  swap idx 9 with idx 10 ( 10->9)
	[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]		.. 				  
 *
 */
public class InsetionSortAlgorithm {

	public static void main(String[] args) {
//		int[] data = new int[] {15, 32, 26, 11, 36, 19, 42, 44 ,14};
		int[] data = new int[] {10, 2, 1, 7, 5, 6, 3, 8, 4, 9};

		System.out.println("before sorting");
		System.out.println(Arrays.toString(data)); 
		
		insertionSort(data);
		
		System.out.println("after sorting");
		System.out.println(Arrays.toString(data));
		
	}
	
	public static void insertionSort(int[] data) {
		
		for (int i=0; i<data.length-1; i++) {
			for (int j=i+1; j>0; j--) {
				if (data[j] < data[j-1]) {
					swap(data, j, j-1);
					System.out.println("idx " + (i) + " , " + (j) + "  --> " + Arrays.toString(data));
				} else {
					break;
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
