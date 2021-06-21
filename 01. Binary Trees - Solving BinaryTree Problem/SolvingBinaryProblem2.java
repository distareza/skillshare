import java.util.LinkedList;
import java.util.Queue;

public class SolvingBinaryProblem2 {


	/**
			   1
			/ 	  \
		   2  	    3
		  /	\      / \
		 6	 7    8    9
		/ \	     / \    \
       4   5    10   12     11
       
       Has Summary Path : 
       	1+2+6+4  : 13
       	1+2+6+5  : 14
       	1+2+7	 : 10
       	1+3+8+10 : 22
       	1+3+8+12 : 24
       	1+3+9+11 : 24
			 
	**/
	
	public static void main(String[] args) {
		System.out.println("Solving Binary Problem");
//		System.out.println("           A(1)           ");
//		System.out.println("       /       \\         ");
//		System.out.println("      B(2)      C(3)       ");
//		System.out.println("    /   \\     /   \\      ");
//		System.out.println("   D(6)  E(7) F(8)  G(9)   ");
//		System.out.println("  / \\        /  \\      \\  ");
//		System.out.println(" H(4)I(5)   J(10)K(12)   L(11) ");
		
		
		Node<Integer> A = new Node<>(1);
		Node<Integer> B = new Node<>(2);
		Node<Integer> C = new Node<>(3);
		Node<Integer> D = new Node<>(6);
		Node<Integer> E = new Node<>(7);
		Node<Integer> F = new Node<>(8);
		Node<Integer> G = new Node<>(9);
		Node<Integer> H = new Node<>(4);
		Node<Integer> I = new Node<>(5);
		Node<Integer> J = new Node<>(10);
		Node<Integer> K = new Node<>(12);
		Node<Integer> L = new Node<>(11);
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(H,I);
		F.setNode(J,K);
		G.setNode(null,L);
		
		BTreePrinter.printNode(A);
		
		for (int i : new Integer[] {10, 13,14,16,22,24,25})
			System.out.println("Has Summary " + i + " Node Value : " + hasSumPath(A, i));

		printNode(A);
		
		mirror(A);
		printNode(A);
	}
	
	/**
	 * Summing the Nodes along a Path in Binary Tree
	 */
	public static boolean hasSumPath(Node<Integer> node, int sum) {
		
		if (node == null) return false;
		int value = node.value;
		//System.out.println(String.format(" sum (%d) - value (%d) : %d", sum, value, (sum - value)));
		if (node.left == null && node.right == null) {
			return (sum-value == 0);
		}
		boolean isExists = false;
		if (node.left != null) isExists = isExists || hasSumPath(node.left, sum-value);
		if (node.right != null) isExists = isExists || hasSumPath(node.right, sum-value);
		
		return isExists;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void printNode(Node<Integer> root) {
		
		if (root == null) return;
		
		Queue<Pair<Node<Integer>, Integer>> queue = new LinkedList<>();
		queue.add(new Pair(root, 1));
		
		int currentLevel = 0;
		while (!queue.isEmpty()) {
			
			Pair<Node<Integer>, Integer> pair = queue.remove();
			Node<Integer> node = pair.key;
			int nodeValue = node.value;
			int nodeLevel = pair.value;

			if (currentLevel != nodeLevel) {
				System.out.println();
				currentLevel = nodeLevel;
			}
			System.out.print(nodeValue + " ");
			
			if (node.left != null) queue.add(new Pair(node.left, nodeLevel + 1));
			if (node.right != null) queue.add(new Pair(node.right, nodeLevel + 1));
			
		}
		
	}
	
	public static <T> void mirror(Node<T> root) {
		
		if (root == null) return;
		Node<T> temp = root.left;
		
		root.left = root.right;
		root.right = temp;
		
		mirror(root.left);
		mirror(root.right);
		
	}
	
}
