
/**
 * 
 * Interpolation Search = improvement of binary search
 * 
 *
 */
public class InterpolationSearchAlgorithm {

	public static void main(String[] args) {
		
		int sortedList[] = new int[] {10, 30, 40, 50, 60, 80, 90, 100, 110, 120,130, 140,160,180};
		
		System.out.println(interpolactionSearch(sortedList, 80));
		System.out.println(interpolactionSearch(sortedList, 130));
		
	}
	
	public static int interpolactionSearch(int[] list, int element) {
		
		System.out.println("Searching " + element);
		
		int low = 0;
		int high = list.length - 1;
		
		while (low <= high) {
			
			int mid = low + ((element - list[low]) * (high - low)) / (list[high] - list[low]); // interpolar search
			//int mid = (low + high )/2; // binary search
			
			System.out.println("Low " + low + " High " + high + " mid : " + mid + " value " + list[mid]);
			
			if (list[mid] == element) 
				return mid;
			else if (list[mid] < element)
				low = mid + 1;
			else
				high = mid = 1;
			
		}
		return -1;		
	}
	
	

}
