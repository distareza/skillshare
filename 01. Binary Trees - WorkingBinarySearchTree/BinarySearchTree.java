public class BinarySearchTree {

/**
	
			  A:8
			/ 	  \
		 B:3  	   C:10
		  /	\        \
	    D:1 E:6      F:14
		    / \      / 
         G:4  H:7  I:13
       
**/
		
	public static void main(String[] args) {
		Node<Integer> myBTree = new Node<Integer> (8);
		for (int i : new Integer[] {3,10,1,6,14,4,7,13})
			insertTree(myBTree, i);
		
		BTreePrinter.printNode(myBTree);
		Utility.printNodes(myBTree);
		inOrder(myBTree);
		System.out.println();
		System.out.println();
		
		System.out.println("Adding 2");
		insertTree(myBTree, 2);
		BTreePrinter.printNode(myBTree);
		inOrder(myBTree);
		System.out.println();
		System.out.println();

		System.out.println("Adding 12");
		insertTree(myBTree, 12);
		BTreePrinter.printNode(myBTree);
		inOrder(myBTree);
		System.out.println();
		System.out.println();

		for (int i : new Integer[] {2,3,5,7,11,12,13,15})
			System.out.println(String.format("Look %s : %s", i, lookUp(myBTree, i) != null));
		
		System.out.println(String.format("min value : %d", getMinValue(myBTree)));
		System.out.println(String.format("max value : %d", getMaxValue(myBTree)));
		
		System.out.println("range node from 5 to 13 ");
		printRange(myBTree, 5, 13);
		
		System.out.println(String.format("is myBTree is binary search tree : "+  isBinarySearchTree(myBTree)));
		
		Node<Integer> myNewTree = new Node<Integer>(8);
		myNewTree.setNode(new Node<Integer>(10), new Node<Integer>(5, new Node<Integer>(3), new Node<Integer>(7)));
		inOrder(myNewTree);
		System.out.println();
		System.out.println(String.format("is myNewTree is binary search tree : "+  isBinarySearchTree(myNewTree)));
	}
	
	public static Node<Integer> lookUp(Node<Integer> node, int searchValue) {
		
		if (node == null) return null;
				
		if (searchValue == node.value) return node;
		if (searchValue < node.value) 
			return lookUp(node.left, searchValue);
		if (searchValue > node.value)
			return lookUp(node.right,  searchValue);
		
		return null;
	}
	
	public static Node<Integer> insertTree(Node<Integer> node, int insertValue) {
		if (node == null) return new Node<>(insertValue);
		
		if (insertValue < node.value) {
			node.left = insertTree(node.left, insertValue);
		} else if (insertValue > node.value) {
			node.right = insertTree(node.right, insertValue);
		}
		
		return node;
	}
	
	/**
	 * print value of Binary Tree in ascending order 
	 */
	public static void inOrder(Node<Integer> node) {
		if (node == null) return;
		inOrder(node.left);
		System.out.print(String.format("%s->", node.value));
		inOrder(node.right);
	}
	
	public static int getMinValue(Node<Integer> node) {
		 int minValue = Integer.MAX_VALUE;
		 while (node != null) {
			 minValue = node.value;
			 node = node.left;
		 }
		 return minValue;
	}
	
	
	public static int getMaxValue(Node<Integer> node) {
		 int minValue = Integer.MIN_VALUE;
		 while (node != null) {
			 minValue = node.value;
			 node = node.right;
		 }
		 return minValue;
	}

	public static void printRange(Node<Integer> node, int low, int high) {
		if (node == null) return ;
		
		if (low <= node.value) 
			printRange(node.left, low, high);
		
		if (low<= node.value && node.value <= high)
			System.out.println(node.value);
		
		if (high > node.value)
			printRange(node.right, low, high);
	}
	
	public static boolean isBinarySearchTree(Node<Integer> node) {
		
		if (node == null) return true;
		if (node.left != null && node.left.value > node.value) return false; 
		if (node.right != null && node.right.value < node.value) return false;
		
		return isBinarySearchTree(node.left) && isBinarySearchTree(node.right);
	}
	
}
