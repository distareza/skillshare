public class SolvingBinaryProblem {

	/**
			   A
			/ 	  \
		   B  	    C
		  /	\      / \
		 D	 E    F    G
		/ \	     / \    \
       H   I    J   K     L
			\
			 M
		    /
		   N 	 
			 
	Count Nodes : 14
	Maxumum Depth : 5
	**/
	
	public static void main(String[] args) {
//		System.out.println("           A              ");
//		System.out.println("       /       \\         ");
//		System.out.println("      B         C          ");
//		System.out.println("    /   \\     /   \\      ");
//		System.out.println("   D     E   F     G      ");
//		System.out.println("  / \\      /  \\     \\  ");
//		System.out.println(" H   I     J   K      L   ");
//		System.out.println("      \\                  ");
//		System.out.println("       M                  ");
//		System.out.println("      /                   ");
//		System.out.println("     N                    ");
		
		
		Node<String> A = new Node<>("A");
		Node<String> B = new Node<>("B");
		Node<String> C = new Node<>("C");
		Node<String> D = new Node<>("D");
		Node<String> E = new Node<>("E");
		Node<String> F = new Node<>("F");
		Node<String> G = new Node<>("G");
		Node<String> H = new Node<>("H");
		Node<String> I = new Node<>("I");
		Node<String> J = new Node<>("J");
		Node<String> K = new Node<>("K");
		Node<String> L = new Node<>("L");
		Node<String> M = new Node<>("M");
		Node<String> N = new Node<>("N");
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(H,I);
		F.setNode(J,K);
		G.setNode(null,L);
		I.setNode(null, M);
		M.setNode(N, null);
		
		System.out.println("Solving Binary Problem");
		BTreePrinter.printNode(A);
		
		System.out.println("Count Nodes : " + countNodes(A));
		System.out.println("Maximum Depth : " + maximumDepth(A));
		
		
		
	}
	
	public static int countNodes(Node<String> root) {
		if (root == null) return 0;
		
		int count = 1;
		if (root.left!=null) count+=countNodes(root.left);
		if (root.right!=null) count+=countNodes(root.right);
		
		return count;
	}
	
	public static int maximumDepth(Node<String> root) {
		if (root == null) return 0;
		
		int depth = 1, leftDepth = 0, rightDepth = 0;
		if (root.left==null & root.right==null) return 0;
		if (root.left!=null) leftDepth = maximumDepth(root.left);
		if (root.right!=null) rightDepth = maximumDepth(root.right);
		
		return leftDepth>rightDepth?depth+leftDepth:depth+rightDepth;
	}
	
	
	
}