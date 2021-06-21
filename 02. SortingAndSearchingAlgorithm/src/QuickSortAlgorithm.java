import java.util.Arrays;

/**
 * Quick Short does not require extra space
 * 
 * example
 * 
before sorting
[10, 2, 1, 7, 5, 6, 3, 8, 4, 9]
pivot:10	
			[9, 2, 1, 7, 5, 6, 3, 8, 4, 10]
pivot:9
			[4, 2, 1, 7, 5, 6, 3, 8, 9, 10]
pivot:4
			[4, 2, 1, 3, 5, 6, 7, 8, 9, 10]
			[3, 2, 1, 4, 5, 6, 7, 8, 9, 10]
pivot:3
			[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
pivot:1
			[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
pivot:5
			[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
pivot:6
			[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
pivot:7
			[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
after sorting
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]


 *
 */

public class QuickSortAlgorithm {

	public static void main(String[] args) {
		System.out.println("Quick sort algorithm ");
//		int[] data = new int[] {15, 32, 26, 11, 36, 19, 42, 44 ,14};
		int[] data = new int[] {10, 2, 1, 7, 5, 6, 3, 8, 4, 9};

		System.out.println("before sorting");
		System.out.println(Arrays.toString(data)); 

		quickSort(data, 0, data.length-1);
		
		System.out.println("after sorting");
		System.out.println(Arrays.toString(data));
	}

	public static void swap(int[] list, int i, int j) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}		
	
	
	public static int partition(int[] listToSort, int low, int high) {
		int pivot = listToSort[low];
		System.out.println("pivot:" + pivot);
		
		int l = low;
		int h = high;
		
		while (l<h) {
			
			while ( listToSort[l] <= pivot && l<h) 
				l++;
			while ( listToSort[h] > pivot) 
				h--;
			
			if (l<h) {
				swap(listToSort, l , h);
				System.out.println("\t" + Arrays.toString(listToSort));
			}
		}
		
		if (low != h) {
			swap(listToSort, low, h);
			System.out.println("\t" + Arrays.toString(listToSort));
		}
	
		return h;
	}
	
	public static void quickSort(int[] listToSort, int low, int high) {
		if (low >= high) return;
		
		int pivotIndex = partition(listToSort, low, high);
		
		quickSort(listToSort, low, pivotIndex - 1);
		quickSort(listToSort, pivotIndex + 1, high);
	}
	
}
