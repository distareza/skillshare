import java.util.Arrays;

public class BinarySearchAlgorithm {

	public static void main(String[] args) {
		String[] unSortedList = new String[] 
				{	"Alex", "Dora", "Carl",	"Ben", "Olivia",
					"Elsa", "Fiona", "Gerald", "Harry", "Peter",
					"Irene", "Jeff", "Kris", "Lewis", "Mary", "Nora"
				};
		
		Arrays.sort(unSortedList);
		System.out.println(Arrays.toString(unSortedList));
		
		System.out.println("Search \"Harry\" in index " + binarySearch(unSortedList, "Harry"));
		System.out.println("Search \"Jeff\" in index " + binarySearch(unSortedList, "Jeff", 0, unSortedList.length-1));
		
	}
	
	public static void sortList(String[] args) {
		
	}
	
	public static int binarySearch(String[] list, String search) {
		
		int low = 0;
		int high = list.length - 1;
		while (low < high) {
			int mid = (low + high) / 2;
			if (list[mid].contentEquals(search))
				return mid;
			else if (list[mid].compareTo(search)<0) 
				low = mid + 1;
			else
				high = mid -1;
	
		}
		
		return -1;
	}
	
	public static int binarySearch(String[] list, String search, int low, int high) {
		if (low > high) return -1;
		
		int mid = (low+high) / 2;
		
		if (list[mid].equals(search))
			return mid;
		else if (list[mid].compareTo(search) < 0) 
			return binarySearch(list, search, mid+1, high);
		else
			return binarySearch(list, search, low, mid-1);
	}
}
