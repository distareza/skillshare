public class Node {
	public String value;
	public Node left;
	public Node right;
	
	public Node (String value){
		this.value = value;			
	}
	
	public Node(String value, Node left, Node right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	public void setNode(Node left, Node right) {
		this.left = left;
		this.right = right;
	}
}