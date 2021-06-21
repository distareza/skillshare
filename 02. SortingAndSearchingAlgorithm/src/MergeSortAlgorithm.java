import java.util.Arrays;

/**
 * Pro : stable sort
 * 
 * Con : not adaptive sort
 * 		 requires extra space O(N * Log N) 
 *
 *   example
 *   
[10, 2, 1, 7, 5, 6, 3, 8, 4, 9]				.. initial
[10, 2, 1, 7, 5] 		[6, 3, 8, 4, 9]		.. split into 2 part
[10, 2] [1] [7, 5] 		[6, 3] [8] [4, 9]	.. split into 2 part of each part
[10] [2] [1] [7] [5] 	[6] [3] [8] [4] [9] .. split until 1 element
[2, 10] [1] [5, 7]		[3, 6] [8] [4, 9]	.. merge 
[1, 2, 10] [5, 7]		[3, 6, 8] [4, 9]	.. merge	
[1, 2, 5, 7, 10] 		[3, 4, 6, 8, 9]		.. merge
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]				.. merge
 *
 */

public class MergeSortAlgorithm {

	public static void main(String[] args) {
		System.out.println("Merge sort algorithm ");
		int[] data = new int[] {15, 32, 26, 11, 36, 19, 42, 44 ,14};
//		int[] data = new int[] {10, 2, 1, 7, 5, 6, 3, 8, 4, 9};

		System.out.println("before sorting");
		System.out.println(Arrays.toString(data)); 

		mergeSort(data);
		
		System.out.println("after sorting");
		System.out.println(Arrays.toString(data));
	}
	
	public static void split(int[] listToSort, int[] firstHalfList, int[] secondHalfList) {
		int firstHalfLength = firstHalfList.length;
		for (int i = 0; i< listToSort.length; i++) {
			if (i < firstHalfLength) {
				firstHalfList[i] = listToSort[i];
			} else {
				secondHalfList[i-firstHalfLength] = listToSort[i];
			}
		}
	}
	
	public static void merge(int[] listToSort, int[] firstList, int[] secondList) {
		int mergeIdx = 0;
		int firstHalfIdx = 0;
		int secondHalfIdx = 0;
		
		while(firstHalfIdx < firstList.length && secondHalfIdx < secondList.length) {
			
			if (firstList[firstHalfIdx] < secondList[secondHalfIdx]) {
				listToSort[mergeIdx] = firstList[firstHalfIdx++];
			} else if (secondHalfIdx < secondList.length) {
				listToSort[mergeIdx] = secondList[secondHalfIdx++];
			}
			mergeIdx++;
		}
		
		if ( firstHalfIdx < firstList.length) {
			while (mergeIdx < listToSort.length) {
				listToSort[mergeIdx++] = firstList[firstHalfIdx++];
			}
		}
				
	}

	
	public static void mergeSort(int[] listToSort) {
		if (listToSort.length == 1) return;
		
		int midIdx = listToSort.length / 2 + listToSort.length % 2;
		int[] firstList = new int[midIdx];
		int[] secondList = new int[listToSort.length - midIdx];
		split(listToSort, firstList, secondList);

		System.out.println(Arrays.toString(firstList) + " " + Arrays.toString(secondList));
		mergeSort(firstList);
		mergeSort(secondList);

		System.out.println(Arrays.toString(firstList) + " " + Arrays.toString(secondList));

		merge(listToSort, firstList, secondList);		
		System.out.println(Arrays.toString(listToSort));
	}
	
}
