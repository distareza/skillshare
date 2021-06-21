import java.util.Arrays;

/**
 * the best jump length = sqrt(N)
 * the jump search improvement of linier search
 * 
 *
 */

public class JumpSearchAlgorithm {

	public static void main(String[] args) {
		String[] unSortedList = new String[] 
				{	"Alex", "Dora", "Carl",	"Ben", "Olivia",
					"Elsa", "Fiona", "Gerald", "Harry", "Peter",
					"Irene", "Jeff", "Kris", "Lewis", "Mary", "Nora"
				};
		
		Arrays.sort(unSortedList);
		System.out.println(Arrays.toString(unSortedList));
		
		System.out.println("Search \"Harry\" in index " + jumpSearch(unSortedList, "Harry", 4) );
		System.out.println("Search \"Jeff\" in index " + jumpSearch(unSortedList, "Zoe", 4));

	}

	public static int jumpSearch(String list[], String search, int jumpLength) {
		System.out.println("Searching " + search);
		int i = 0;
		
		while(list[i].compareTo(search) <= 0) {
			System.out.println("Element is geater than or equal to " + list[i]);
			i = i + jumpLength;
			if (i >= list.length) break;
		}
		
		int startIndex = i - jumpLength;
		int endIndex = Math.min(i,  list.length);
		
		System.out.println("Looking between " + startIndex + " and " + endIndex);
		
		for (int j = startIndex; j < endIndex ; j++) {
			if (list[j].equals(search))
				return j;
		}
		
		return -1;
	}
}
