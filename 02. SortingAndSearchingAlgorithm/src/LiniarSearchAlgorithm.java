
public class LiniarSearchAlgorithm {

	public static void main(String[] args) {
		String[] unSortedList = new String[] 
				{	"Alex", "Dora", "Carl",	"Ben", "Olivia",
					"Elsa", "Fiona", "Gerald", "Harry", "Peter",
					"Irene", "Jeff", "Kris", "Lewis", "Mary", "Nora"
				};
		
		System.out.print("Search \"Harry\" in index " + linierSearch(unSortedList, "Harry"));
		
	}
	
	public static int linierSearch(String[] list, String search) {
		for (int i = 0 ; i<list.length; i++) {
			if (list[i].equals(search))
					return i;
		}
		return -1;
	}
}
