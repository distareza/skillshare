import java.util.LinkedList;
import java.util.Queue;

public class Utility {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void printNodes(Node<Integer> root) {
		
		if (root == null) return;
		
		Queue<Pair<Node<Integer>, Integer>> queue = new LinkedList<>();
		queue.add(new Pair(root, 0));
		
		int currentLevel = 0;
		while (!queue.isEmpty()) {
			
			Pair<Node<Integer>, Integer> pair = queue.remove();
			Node<Integer> node = pair.key;
			int level = pair.value;
			
			String value = node.value.toString();
			String leftValue = "", rightValue = "";
			if (node.left != null) leftValue = node.left.value.toString();
			if (node.right != null) rightValue = node.right.value.toString();

			if (currentLevel != level) {
				System.out.println();
				currentLevel = level;
				System.out.print(String.format("LeveL %d :" , level));
			} else if (currentLevel > 0)
				System.out.print(", ");
			else
				System.out.print(String.format("Level %d :" , level));
			
			if (leftValue.length()>0 || rightValue.length()>0)
				System.out.print(String.format("%s -> { %s , %s }", value, leftValue, rightValue));
			else
				System.out.print(String.format("%s -> ", value));
			 
			if (node.left != null) queue.add(new Pair(node.left, level+1));
			if (node.right != null) queue.add(new Pair(node.right, level+1));
			
		}
		
		System.out.println();
	}
	
}
