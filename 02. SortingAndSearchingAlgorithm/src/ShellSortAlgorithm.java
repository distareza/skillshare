import java.util.Arrays;

/**
 * 
 * Shell Sort is based on insertion sort algorithm
 * rather than comparing adjacent elements , elements a certain interval apart are compared 
 *
 * shell Sort is sort element far away from one another and then reduce interval for sorting
 * shell sort uses insertion sort to sort element that are separated by certain interval 
 * 
 * it is adaptive and stable sort
 * 
 * 
 * example
 *
[10, 2, 1, 7, 5, 6, 3, 8, 4, 9] -> [6, 2, 1, 7, 5, 10, 3, 8, 4, 9] 	 divider :5 swap idx 1 with idx 6 ( 6 <-> 10 )
[6, 2, 1, 7, 5, 10, 3, 8, 4, 9] -> [6, 2, 1, 4, 5, 10, 3, 8, 7, 9] 	 divider :5 swap idx 4 with idx 9 ( 4 <-> 7 )
[6, 2, 1, 4, 5, 10, 3, 8, 7, 9] -> [1, 2, 6, 4, 5, 10, 3, 8, 7, 9] 	 divider :2 swap idx 1 with idx 3 ( 1 <-> 6 )
[1, 2, 6, 4, 5, 10, 3, 8, 7, 9] -> [1, 2, 5, 4, 6, 10, 3, 8, 7, 9] 	 divider :2 swap idx 3 with idx 5 ( 5 <-> 6 )
[1, 2, 5, 4, 6, 10, 3, 8, 7, 9] -> [1, 2, 5, 4, 3, 10, 6, 8, 7, 9] 	 divider :2 swap idx 5 with idx 7 ( 3 <-> 6 )
[1, 2, 5, 4, 3, 10, 6, 8, 7, 9] -> [1, 2, 3, 4, 5, 10, 6, 8, 7, 9] 	 divider :2 swap idx 3 with idx 5 ( 3 <-> 5 )
[1, 2, 3, 4, 5, 10, 6, 8, 7, 9] -> [1, 2, 3, 4, 5, 8, 6, 10, 7, 9] 	 divider :2 swap idx 6 with idx 8 ( 8 <-> 10 )
[1, 2, 3, 4, 5, 8, 6, 10, 7, 9] -> [1, 2, 3, 4, 5, 8, 6, 9, 7, 10] 	 divider :2 swap idx 8 with idx 10 ( 9 <-> 10 )
[1, 2, 3, 4, 5, 8, 6, 9, 7, 10] -> [1, 2, 3, 4, 5, 6, 8, 9, 7, 10] 	 divider :1 swap idx 6 with idx 7 ( 6 <-> 8 )
[1, 2, 3, 4, 5, 6, 8, 9, 7, 10] -> [1, 2, 3, 4, 5, 6, 8, 7, 9, 10] 	 divider :1 swap idx 8 with idx 9 ( 7 <-> 9 )
[1, 2, 3, 4, 5, 6, 8, 7, 9, 10] -> [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] 	 divider :1 swap idx 7 with idx 8 ( 7 <-> 8 )
 *
 * 
 */

public class ShellSortAlgorithm {

	public static void main(String[] args) {
		System.out.println("shell sorting algorithm ");
//		int[] data = new int[] {15, 32, 26, 11, 36, 19, 42, 44 ,14};
		int[] data = new int[] {10, 2, 1, 7, 5, 6, 3, 8, 4, 9};

		System.out.println("before sorting");
		System.out.println(Arrays.toString(data)); 
		
		shellSort(data);
		
		System.out.println("after sorting");
		System.out.println(Arrays.toString(data));
	}
	
	public static void swap(int[] list, int i, int j) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}		
	
	public static void insertionSort(int[] data, int increment) {
		for (int i=0; i + increment <data.length; i++) {
			for (int j=i+increment; j-increment>=0; j-= increment) {
				if (data[j] < data[j-increment]) {
					System.out.print(Arrays.toString(data));
					swap(data, j, j-increment);
					System.out.println(" -> "+ Arrays.toString(data) + " \t divider :" + increment + " swap idx " + (j-increment+1) + " with idx " + (j+1) + " ( " + data[j-increment] + " <-> " + data[j] + " )");
				}
			}
		}
	}
	
	public static void shellSort(int[] data) {
		int increment = data.length / 2;
		while (increment >= 1) {
			insertionSort(data, increment);
			increment = increment/2;
		}
	}

}
