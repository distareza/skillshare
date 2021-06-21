import java.util.Stack;
import java.util.Set;
import java.util.HashSet;	

public class DeepthFirstTraversal {

	/**
			  A
			/ 	\
		   B  	 C
			    / \
			   D   E
			  / \	\
			 F   G 	 H
			 
	Deepth First Pre-Order Traversal Order 	: A B C D F G E H
	Deepth First In-Order Traversal	Order 	: B A F D G C E H
	Deepth First Post-Order Traversal Order : B F G D H E C A
			 
	**/
	public static void main (String args[]) {
		System.out.println("Deepth First Traversal");
		System.out.println("          A             ");
		System.out.println("        /   \\          ");
		System.out.println("       B      C         ");
		System.out.println("             /  \\      ");
		System.out.println("            D    E      ");
		System.out.println("           / \\	  \\    ");
		System.out.println("          F   G    H    ");
		
		Node root = new Node("A", new Node("B"), new Node("C", new Node("D", new Node("F"), new Node("G")), new Node("E", null, new Node("H"))));
		
		System.out.println("Deepth First Pre-Order Traversal");
		recursionPreOrder(root);
		
		System.out.println();
		System.out.println("Deepth First In-Order Traversal");
		recursionInOrder(root);
		
		System.out.println();
		System.out.println("Deepth First Post-Order Traversal");
		recursionPostOrder(root);
		
	}
	
	public static void recursionPreOrder(Node node) {
		if (node == null) return;
		System.out.print(node.value + " ");
		recursionPreOrder(node.left);
		recursionPreOrder(node.right);
	}

	public static void recursionInOrder(Node node) {
		if (node == null) return;
		recursionInOrder(node.left);
		System.out.print(node.value + " ");
		recursionInOrder(node.right);
	}

	public static void recursionPostOrder(Node node) {
		if (node == null) return;
		if (node.left != null) recursionPostOrder(node.left);
		if (node.right != null) recursionPostOrder(node.right);
		System.out.print(node.value + " ");
	}
	
}