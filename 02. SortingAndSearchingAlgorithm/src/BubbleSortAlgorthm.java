import java.util.Arrays;

/**
 * For each iteration compare each element with its neighbour
 * the first of loop iteration makes the Maximum value of list elements is swap to the end of element
 * the next iteration count will having lesser element to check, as we knows that the end of elements is already swap to the maximum value, thus the iteration can break early.
 * 
 *   Pro : 	adaptive sort = can break early or no need to iterate through the end (stop the sorting process early) if the list is sorted
 *   		stable sort : entities that are equal are not re-arrange
 *   		 
 *   Con :  perform sorting in place takes extra addition space
 *   		time complexity =  O(N^2) for comparation and swaps = same as Selection Sort
 *   		takes the most time consumption when sorting descending list
 *   
 * example :
 * 
	Do bubble sorting ( make a swap if next value having less value, iterate the element from the last element down to the first element, repeat until the iteration went empty)
	[2, 10, 1, 7, 5, 6, 3, 8, 4, 9]		.. check idx 2 : [2, 10,  1 ...........................] swap idx 2  with idx 3 ( 10 -> 1 )
	[2, 1, 10, 7, 5, 6, 3, 8, 4, 9]		.. check idx 3 : [..  1, 10,  7 .......................] swap idx 3  with idx 4 ( 10 -> 7 )
	[2, 1, 7, 10, 5, 6, 3, 8, 4, 9]		.. check idx 4 : [......  7, 10,  5 ...................] swap idx 4  with idx 5 ( 10 -> 5 )
	[2, 1, 7, 5, 10, 6, 3, 8, 4, 9]		.. check idx 5 : [........... 5, 10,  6 ...............] swap idx 5  with idx 6 ( 10 -> 6 )
	[2, 1, 7, 5, 6, 10, 3, 8, 4, 9]		.. check idx 6 : [..............  6, 10,  3 ...........] swap idx 6  with idx 7 ( 10 -> 3 )
	[2, 1, 7, 5, 6, 3, 10, 8, 4, 9]		.. check idx 7 : [..................  3, 10, 8 ........] swap idx 7  with idx 8 ( 10 -> 8 )
	[2, 1, 7, 5, 6, 3, 8, 10, 4, 9]		.. check idx 8 : [......................  8, 10, 4 ....] swap idx 8  with idx 9 ( 10 -> 4 )
	[2, 1, 7, 5, 6, 3, 8, 4, 10, 9]		.. check idx 9 : [..........................  4, 10,  9] swap idx 9  with idx 10( 10 -> 9 )
	[2, 1, 7, 5, 6, 3, 8, 4, 9, 10]		.. check idx 1 : [2,  1 .......................   9, 10] swap idx 1  with idx 2 ( 1 -> 2 )
	[1, 2, 7, 5, 6, 3, 8, 4, 9, 10]		.. check idx 3 : [1,  2,  7,  5,  .....................] swap idx 3  with idx 4 ( 7 -> 5 )
	[1, 2, 5, 7, 6, 3, 8, 4, 9, 10]		.. check idx 4 : [......  5,  7,  6, ..................] swap idx 4  with idx 5 ( 7 -> 6 )
	[1, 2, 5, 6, 7, 3, 8, 4, 9, 10]		.. check idx 5 : [..........  6,  7,  3,  .............] swap idx 5  with idx 6 ( 7 -> 3 )
	[1, 2, 5, 6, 3, 7, 8, 4, 9, 10]		.. check idx 7 : [..............  3,  7,  8,  4,  .....] swap idx 7  with idx 8 ( 8 -> 4 )
	[1, 2, 5, 6, 3, 7, 4, 8, 9, 10]		.. check idx 4 : [1,  2,  5,  6,  3,  7,  4,  8,  .....] swap idx 4  with idx 5 ( 6 -> 3 )
	[1, 2, 5, 3, 6, 7, 4, 8, 9, 10]		.. check idx 6 : [..........  3,  6,  7,  4,  .........] swap idx 6  with idx 7 ( 7 -> 4 )
	[1, 2, 5, 3, 6, 4, 7, 8, 9, 10]		.. check idx 3 : [1,  2,  5,  3,  6,  4,  7, ..........] swap idx 3  with idx 4 ( 5 -> 3 )
	[1, 2, 3, 5, 6, 4, 7, 8, 9, 10]		.. check idx 5 : [......  3,  5,  6,  4,  .............] swap idx 5  with idx 6 ( 6 -> 4 )
	[1, 2, 3, 5, 4, 6, 7, 8, 9, 10]		.. check idx 4 : [..........  5,  4,  6,  .............] swap idx 4  with idx 5 ( 5 -> 4 )
	[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]		..               [..........  4,  5,  .................]
	
 *
 */
public class BubbleSortAlgorthm {

	public static void main(String[] args) {
//		int[] data = new int[] {15, 32, 26, 11, 36, 19, 42, 44 ,14};
		int[] data = new int[] {10, 2, 1, 7, 5, 6, 3, 8, 4, 9};
		System.out.println("before sorting");
		System.out.println(Arrays.toString(data));
		
		System.out.println();
		System.out.println("do bubble sorting");
		bubbleSort(data);
		
		System.out.println("after sorting");
		System.out.println(Arrays.toString(data));
	}
	
	public static void bubbleSort(int data[]) {
		for (int i=data.length-1; i > 0; i--) {
			boolean swapped = false;
			for (int j = 0; j < i ; j++) {
				if (data[j] > data[j+1]) {
					swap(data, j, j+1);
					System.out.println("idx " + (data.length-i) + " , " + (j+1) + "  --> " + Arrays.toString(data));
					swapped = true;
				}
			}
			if (!swapped)
				break;
		}
	}
	
	public static void swap(int[] list, int i, int j) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}	
	
}
