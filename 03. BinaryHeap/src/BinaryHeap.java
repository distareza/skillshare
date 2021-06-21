
/**
 *  Binary Heap
 * 	1. Complete Binary Tree : All levels of the heap binary tree are completely filled, except the last one, which is filled from the left 
 *  2. must satisfies the heap property. Max Heap or Min Heap 
 *  	For a max heap, every node should be larger than either of its child nodes. 
 *  	For a min heap, every node should be smaller than either of its child nodes.
 *  
 * Complete Binary Tree can be represented as array
 *                1
 *             /     \
 *          2           3
 *        /  \        /   \         ==> [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 ]
 *      4     5      6      7				root index (i)	==> [ i, (2i + 1), (2i + 2), ... ]
 *     / \   /  \   /  \   /  \								child index left : 	2i + 1 
 *    8   9 10  11 12  13 14  15							child index right : 2i + 2
 *    										child index (j) ==> [ (j-1) / 2, j, ... ]						
 *    														root index : (j-1) / 2
 *    				
 */

public class BinaryHeap {

	public static void main(String[] args) throws Exception {
		MaxHeap();
		System.out.println();
		MinHeap();
	}
	
	public static void MaxHeap() throws HeapFullException, HeapEmptyException{

		System.out.println("Max Heap Operation");
		
		MaxHeap<Integer> maxHeap = new MaxHeap<>(Integer.class);
		// maxHeap = [ null, null, null, null, null, null, null, null, null, null]
		
		
		maxHeap.insert(9); 	
		/**
		 *  maxHeap = [ 	 9, null, null, null, null, null, null, null, null, null]
		 */
		System.out.println(maxHeap);
		
		maxHeap.insert(4); 	
		/**
		 * 		9  sifting up    4
		 * 	   / 	   -->      /
		 * 	  4                9
		 * 
		 * maxHeap = [ 	 4,    9, null, null, null, null, null, null, null, null]
		 */
		System.out.println(maxHeap);
		
		maxHeap.insert(17);	
		/**
		 * 		9   sifting up     17
		 *     / \     -->        /  \
		 *    4  17              4    9
		 *    
		 *   maxHeap = [ 	 17,   4,    9, null, null, null, null, null, null, null]
		 */
		System.out.println(maxHeap);

		maxHeap.insert(6);	
		/**
		 * 		17                   17
		 *     /  \  sifting up     /  \
		 *    4    9   -->         6    9 
		 *   /                    /
		 *  6                    4
		 *  
		 *  maxHeap = [ 	 17,   6,    9,    4, null, null, null, null, null, null]
		 */
		System.out.println(maxHeap);

		maxHeap.insert(100); 
		/**
		 *      17                   17                    100
		 *     /  \  sifting up     /  \   sifting up     /   \
		 *    6    9   -->        100    9     -->      17     9 
		 *   / \                  / \                  /  \ 
		 *  4  100               4   6                4    6
		 *  
		 *  maxHeap = [ 	100,  17,    9,    4,    6, null, null, null, null, null]
		 */
		System.out.println(maxHeap);
		
		maxHeap.insert(144); 
		/**
		 *        100                  100                    144
		 *       /   \  sifting up    /   \    sifting up    /   \
		 *     17     9     -->     17    144      -->     17    100 
		 *    /  \   /             /  \   /               /  \   /
		 *   4    6 144           4    6 9               4    6 9
		 *   
		 *   maxHeap = [ 	144,  17,    100,    4,    6,   9, null, null, null, null]
		 */
		System.out.println(maxHeap);
		
		maxHeap.insert(47);
		/**
		 *      144
		 *     /   \
		 *   17    100   meet the criteria 
		 *  /  \   / \ 
		 * 4    6 9  47
		 * 
		 * maxHeap = [ 	144,  17,    100,    4,    6,   9, 47, null, null, null]
		 */
		System.out.println(maxHeap);
		
		maxHeap.insert(247);
		/**
		 *        144                      144                       144                      247
		 *       /   \    sifting up      /   \      sifting up     /   \    shifting up     /   \
		 *     17    100      -->        17   100       -->       247   100     -->        144   100 
		 *    /  \   / \                / \   /  \               /  \   /  \              /  \   /  \ 
		 *   4    6 9  47             247  6 9   47             17   6 9   47            17   6 9    47
		 *  /                         /                        /                        /
		 * 247                       4                        4                        4
		 *  
		 * maxHeap = [ 	247, 144,  100,    17,    6,   9,   47, 4, null, null]
		 */
		System.out.println(maxHeap);
		System.out.println();
		
		System.out.println(String.format("Highest Priority : %d", maxHeap.getHighestPriority()));
		// high priority of Binary Heap is the first index
		
		System.out.println();
		maxHeap.removeHighestPriority();
		/**
		 *        247                       4                      144                   144
		 *       /    \    remove root    /   \    sifting down   /   \   sifting down  /    \
		 *     144    100   and swap    144    100      -->      4    100     -->      17    100
		 *     /  \   /  \   last idx   /  \   / \              / \   /  \            /  \   /  \ 
		 *    17   6 9    47          17    6 9   47          17   6 9    47         4    6 9    47
		 *   /
		 *  4
		 * 
		 * maxHeap = [ 144, 17, 100, 4, 6, 9, 47, null, null, null]
		 */
		System.out.println(maxHeap);
	}

	public static void MinHeap() throws HeapFullException, HeapEmptyException{
		System.out.println("Min Heap Operation");
		
		MinHeap<Integer> minHeap = new MinHeap<>(Integer.class);
		
		minHeap.insert(9);
		/**
		 *  minHeap = [ 9, null, null, null, null, null, null, null, null, null]
		 */
		System.out.println(minHeap);
		
		minHeap.insert(4);
		/**
		 * 		9  shiting up    4
		 * 	   /      -->       /
		 *    4	               9
		 * 
		 *  minHeap = [ 4, 9, null, null, null, null, null, null, null, null]
		 */		
		System.out.println(minHeap);
		
		minHeap.insert(17);
		/**
		 * 		4  
		 * 	   / \  
		 *    9	  17 
		 * 
		 *  minHeap = [ 4, 9, 17, null, null, null, null, null, null, null]
		 */
		System.out.println(minHeap);
		
		minHeap.insert(6);
		/**
		 * 	    	4                       4  	
		 * 	       / \    shifting up      / \
		 *        9	  17     -->          6  17
		 *       /                       /
		 *      6                       9
		 *   
		 * 
		 *  minHeap = [ 4, 6, 17, 9, null, null, null, null, null, null]
		 */
		System.out.println(minHeap);
		
		minHeap.insert(100);
		/**
		 * 	    	4       	
		 * 	       / \    
		 *        6	  17  
		 *       / \      
		 *      9  100    
		 *   
		 * 
		 *  minHeap = [ 4, 6, 17, 9, 100, null, null, null, null, null]
		 */
		System.out.println(minHeap);
		
		minHeap.insert(3);
		/**
		 * 	    	   4       	                 4                       3
		 * 	        /     \   sifting up     /      \   sifting up    /      \ 
		 *        6	       17    -->        6        3     -->       6        4 
		 *       / \      /                / \      /               / \      /
		 *      9  100   3                9  100   17              9  100   17
		 *    
		 * 
		 *  minHeap = [ 3, 6, 4, 9, 100, 17, null, null, null, null]
		 */
		System.out.println(minHeap);
		
		minHeap.insert(13);
		/**
		 * 	    	   3       
		 * 	        /     \    
		 *        6	       4   
		 *       / \      / \ 
		 *      9  100   17  13 
		 *     
		 * 
		 *  minHeap = [ 3, 6, 4, 9, 100, 17, 13, null, null, null]
		 */
		System.out.println(minHeap);
		
		minHeap.insert(23);
		/**
		 * 	    	   3       
		 * 	        /     \    
		 *        6	       4   
		 *       / \      / \ 
		 *      9  100   17  13 
		 *     /    
		 *    23
		 *    
		 *  minHeap = [ 3, 6, 4, 9, 100, 17, 13, 23, null, null]
		 */		
		System.out.println(minHeap);
		
		System.out.println(String.format("Highest Priority : %d", minHeap.getHighestPriority()));
		// high priority of Binary Heap is the first index
		
		System.out.println();
		minHeap.removeHighestPriority();
		/**
		 * 	    	   3       	                       23                        4                           4                           4
		 * 	        /     \                          /     \                   /    \                     /      \                    /      \
		 *        6	       4  	   Remove Root      6       4     Sift down   6      23    Sift Down     6       23     Shift Down   6        13
		 *       / \      / \     and Swap with    / \     / \       -->     / \    /  \     -->        / \     /  \       -->      / \      /  \
		 *      9  100   17  13    last index     9  100  17  13            9  100 17  13              9  100  17   13             9  100   17   23
		 *     /    
		 *    23
		 * 
		 * minHeap = [ 4, 6, 23, 9, 100, 17, 23, null, null, null]
		 */
		System.out.println(minHeap);
		

	}
}
