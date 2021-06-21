import java.util.HashMap;
import java.util.Map;

public class SolvingBinaryProblem3 {

	@SuppressWarnings("rawtypes")
	public static Node getPerfectBinaryTree() {
		System.out.println("Perfect Binary Tree");
		System.out.println("           A              	");
		System.out.println("       /        \\         	");
		System.out.println("      B           C         ");
		System.out.println("    /   \\      /   \\      ");
		System.out.println("   D     E    F     G        ");
		System.out.println("  / \\   / \\  / \\   /  \\  ");
		System.out.println(" H   I  J  K L  M   N   O    ");
		
		
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
		Node<String> O = new Node<>("O");
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(H,I);
		E.setNode(J,K);
		F.setNode(L,M);
		G.setNode(N,O);
		
		return A;
	}

	@SuppressWarnings("rawtypes")
	public static Node getFullBinaryTree() {
		System.out.println("Full Binary Tree");
		System.out.println("         A              ");
		System.out.println("       /    \\         	");
		System.out.println("      B       C         ");
		System.out.println("            /   \\      ");
		System.out.println("           D      E     ");
		System.out.println("          / \\          ");
		System.out.println("         F   G          ");
		
		
		Node<String> A = new Node<>("A");
		Node<String> B = new Node<>("B");
		Node<String> C = new Node<>("C");
		Node<String> D = new Node<>("D");
		Node<String> E = new Node<>("E");
		Node<String> F = new Node<>("F");
		Node<String> G = new Node<>("G");
		
		A.setNode(B,C);
		C.setNode(D,E);
		D.setNode(F,G);
		
		return A;
	}
	
	@SuppressWarnings("rawtypes")
	public static Node getNotFullBinaryTree() {
		System.out.println("Not Full Binary Tree");
		System.out.println("         A              ");
		System.out.println("       /    \\         	");
		System.out.println("      B       C         ");
		System.out.println("            /   \\      ");
		System.out.println("           D      E     ");
		System.out.println("          / \\     \\   ");
		System.out.println("         F   G      H    ");
		
		
		Node<String> A = new Node<>("A");
		Node<String> B = new Node<>("B");
		Node<String> C = new Node<>("C");
		Node<String> D = new Node<>("D");
		Node<String> E = new Node<>("E");
		Node<String> F = new Node<>("F");
		Node<String> G = new Node<>("G");
		Node<String> H = new Node<>("H");
		
		A.setNode(B,C);
		C.setNode(D,E);
		D.setNode(F,G);
		E.setNode(null, H);
		
		return A;
	}	
	
	@SuppressWarnings("rawtypes")
	public static Node getCompleteBinaryTree() {
		System.out.println("Complete Binary Tree");
		System.out.println("           A              	");
		System.out.println("       /        \\         	");
		System.out.println("      B           C         ");
		System.out.println("    /   \\      /   \\      ");
		System.out.println("   D     E    F     G       ");
		System.out.println("  /  						");
		System.out.println(" H       					");
		
		
		Node<String> A = new Node<>("A");
		Node<String> B = new Node<>("B");
		Node<String> C = new Node<>("C"); 
		Node<String> D = new Node<>("D");
		Node<String> E = new Node<>("E");
		Node<String> F = new Node<>("F");
		Node<String> G = new Node<>("G");
		Node<String> H = new Node<>("H");
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(H, null);
		
		return A;
	}
	

	@SuppressWarnings("rawtypes")
	public static Node getCompleteBinaryTree2() {
		System.out.println("Complete Binary Tree");
		System.out.println("           A              	");
		System.out.println("       /        \\         	");
		System.out.println("      B           C         ");
		System.out.println("    /   \\      /   \\      ");
		System.out.println("   D     E    F     G       ");
		System.out.println("  /\\  						");
		System.out.println(" H  I     					");
		
		
		Node<String> A = new Node<>("A");
		Node<String> B = new Node<>("B");
		Node<String> C = new Node<>("C"); 
		Node<String> D = new Node<>("D");
		Node<String> E = new Node<>("E");
		Node<String> F = new Node<>("F");
		Node<String> G = new Node<>("G");
		Node<String> H = new Node<>("H");
		Node<String> I = new Node<>("I");
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(H, I);
		
		return A;
	}

	@SuppressWarnings("rawtypes")
	public static Node getNotCompleteBinaryTree() {
		System.out.println("Not Complete Binary Tree");
		System.out.println("           A              	");
		System.out.println("       /        \\         	");
		System.out.println("      B           C         ");
		System.out.println("    /   \\      /   \\      ");
		System.out.println("   D     E    F     G       ");
		System.out.println("   \\  						");
		System.out.println("    H     					");
		
		
		Node<String> A = new Node<>("A");
		Node<String> B = new Node<>("B");
		Node<String> C = new Node<>("C"); 
		Node<String> D = new Node<>("D");
		Node<String> E = new Node<>("E");
		Node<String> F = new Node<>("F");
		Node<String> G = new Node<>("G");
		Node<String> H = new Node<>("H");
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(null, H);
		
		return A;
	}

	@SuppressWarnings("rawtypes")
	public static Node getBalanceBinaryTree() {
		System.out.println("Perfect Binary Tree");
		System.out.println("           A              	");
		System.out.println("       /        \\         	");
		System.out.println("      B           C         ");
		System.out.println("    /   \\      /   \\      ");
		System.out.println("   D     E    F     G        ");
		System.out.println("  /                    \\  ");
		System.out.println(" H                      O    ");
		
		
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
		Node<String> O = new Node<>("O");
		
		A.setNode(B,C);
		B.setNode(D,E);
		C.setNode(F,G);
		D.setNode(H,I);
		E.setNode(J,K);
		F.setNode(L,M);
		G.setNode(N,O);
		
		return A;
	}
	
	public static void main(String[] args) {
		
		// Full Binary Tree : having 2 child or No child at all
		System.out.println(isFullBinaryTree(getFullBinaryTree()));
		System.out.println(isFullBinaryTree(getNotFullBinaryTree()));
		
		// Perfect Binary Tree : count left child must be equal to right one
		System.out.println(isPerfectBinaryTree(getPerfectBinaryTree()));

		// Complete Binary Tree : all levels are completely field except the lowest one which is from the left 
		Node completeNode = getCompleteBinaryTree();
		System.out.println(isComplete(completeNode, 0, countNodes(completeNode)));
		completeNode = getCompleteBinaryTree2();
		System.out.println(isComplete(completeNode, 0, countNodes(completeNode)));
		completeNode = getNotCompleteBinaryTree();
		System.out.println(isComplete(completeNode, 0, countNodes(completeNode)));

		// Balance Binary Tree : height of the left and right subtree of any node differ by no more than 1
		System.out.println(isBalance(getBalanceBinaryTree(), new HashMap<>()));
		
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isFullBinaryTree(Node node) {
		if (node == null) return true;
		
		if (node.left == null && node.right == null) return true;
		if (node.left != null && node.right != null) return isFullBinaryTree(node.left) && isFullBinaryTree(node.right);
		
		return false;
	}

	public static int getLeftDepth(Node root) {
		int leftDepth = 0;
		Node node = root;
		while(node != null) {
			leftDepth++;
			node = node.left;
		}
		return leftDepth-1;
	}

	public static boolean isPerfectRecursive(Node root, int leftDepth, int currentLevel) {
		if (root == null) return true;
		
		if (root.left == null && root.right == null) 
			return leftDepth == currentLevel;
				
		if (root.left == null || root.right == null) 
			return false;
		
		return isPerfectRecursive(root.left, leftDepth, currentLevel + 1) && isPerfectRecursive(root.right, leftDepth, currentLevel+1);
		
	}

	public static boolean isPerfectBinaryTree(Node root) {
		int leftDepth = getLeftDepth(root);
		
		return isPerfectRecursive(root, leftDepth, 0);
	}

	public static int countNodes(Node root) {
		if (root == null) return 0;
		
		return 1 + countNodes(root.left) + countNodes(root.right);
	}
	
	public static boolean isComplete(Node root, int currIdx, int totalNodes) {
		if (root == null) return true;
		if (currIdx >= totalNodes) return false;
		
		int leftIdx = 2 * currIdx + 1;
		int rightIdx = 2 * currIdx + 2;
		
		return isComplete(root.left, leftIdx, totalNodes) && isComplete(root.right, rightIdx, totalNodes);
	}
	
	public static boolean isBalance(Node root, Map<Node, Integer> nodeHeighttMap) {
		if (root == null) return true;
		
		boolean isLeftBalanced = isBalance(root.left, nodeHeighttMap);
		boolean isRightBalanced = isBalance(root.right, nodeHeighttMap);
		
		int leftHeight = nodeHeighttMap.getOrDefault(root.left, 0);
		int rightHeight = nodeHeighttMap.getOrDefault(root.right, 0);
		
		nodeHeighttMap.put(root, Math.max(leftHeight, rightHeight) + 1);
		if (Math.abs(leftHeight-rightHeight) <= 1)
			return isLeftBalanced && isRightBalanced;
		
		return false;
		
	}
	
}
