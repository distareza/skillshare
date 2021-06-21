import java.util.Stack;
import java.util.Set;
import java.util.HashSet;	

public class DeepthFirstPreOrderTraversal {

	/**
			   A
			/ 	  \
		   B  	    C
		  /	\      / \
		 D	 E    F    G
		/ \	     / \    \
       H   I    J   K     L
			 
	Deepth First Pre Order Traversal : A B D H I E C F J K G L
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
		
		stackPreOrder(A);
		System.out.println();
		recursionPreOrder(A);
	}
	
	public static void stackPreOrder(Node root) {
		 if (root == null) return;
		 
		 Set<Node> visitedNodes = new HashSet<>();
		 Stack<Node> stack = new Stack<Node>(); // Last In First Out Data Structure
		 stack.push(root);
		 
		 while(!stack.isEmpty()) {
			
			Node node = stack.pop();
			if (node.left == null && node.right == null) {
				System.out.print(node.value + " ");
			} else if ( visitedNodes.contains(node) ) {
				System.out.print(node.value + " ");
			} else {
				visitedNodes.add(node);
				
				if (node.right != null)
					stack.push(node.right);
				if (node.left != null)
					stack.push(node.left);
					
				stack.push(node);				
			}
			
		 }
	}

	public static void recursionPreOrder(Node node) {
		if (node == null) return;
		System.out.print(node.value + " ");
		recursionPreOrder(node.left);
		recursionPreOrder(node.right);
	}	

}