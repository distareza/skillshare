import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstTraversal {
	
	/**
			   A
			/ 	  \
		   B  	    C
		  /	\      / \
		 D	 E    F    G
		/ \	     / \    \
       H   I    J   K     L
			 
	Breadth First Traversal	Order : A B C D E F G H I J K L
	**/
	public static void main (String args[]) {
		System.out.println("Breadth First Traversal");
		System.out.println("           A              ");
		System.out.println("       /       \\         ");
		System.out.println("      B         C          ");
		System.out.println("    /   \\     /   \\      ");
		System.out.println("   D     E   F     G      ");
		System.out.println("  / \\      /  \\     \\  ");
		System.out.println(" H   I     J   K      L   ");
		
		
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		Node F = new Node("F");
		Node G = new Node("G");
		Node H = new Node("H");
		Node I = new Node("I");
		Node J = new Node("J");
		Node K = new Node("K");
		Node L = new Node("L");
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(H,I);
		F.setNode(J,K);
		G.setNode(null,L);
		
		breadthFirst(A);
	}
	
	public static void breadthFirst(Node root) {
		if (root == null ) return;
		
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			Node node = queue.remove();
			System.out.print(node.value + " ");
			
			if (node.left != null) queue.add(node.left);
			if (node.right != null) queue.add(node.right);
		
		}
		
	}
}