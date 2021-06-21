
public class MinHeap <T extends Comparable<T>>extends Heap<T> {

	public MinHeap(Class<T> clazz) {
		super(clazz);
	}

	@Override
	protected void siftDown(int index) {		
		//System.out.println("Sifting down : " + getElementAtIndex(index));
		
		int leftIndex = getLeftChildIndex(index);
		int rightIndex = getRightChildIndex(index);
		
		int smallerIndex = -1;
		
		if (leftIndex != -1 && rightIndex != -1) {
			T leftElement = getElementAtIndex(leftIndex);
			T rightElement = getElementAtIndex(rightIndex);
			
			smallerIndex = leftElement.compareTo(rightElement) < 0 ? leftIndex : rightIndex;
		} else if (leftIndex != -1) { 
			smallerIndex = leftIndex;
		} else if (rightIndex != -1) {
			smallerIndex = rightIndex;
		}
		
		if (smallerIndex == -1) {
			//System.out.println("Stop shifting down, found position");
			return;
		}
		
		//System.out.println("Larger Child : " + getElementAtIndex(largerIndex));
		
		if (getElementAtIndex(smallerIndex).compareTo(getElementAtIndex(index)) < 0) {
			swap(smallerIndex, index);
			//System.out.println("Swap : " + this.toString());
			siftDown(smallerIndex);
		}
					
	}

	@Override
	protected void siftUp(int index) {
		//System.out.println("Sifting up : " + getElementAtIndex(index));

		int parentIndex = getParentIndex(index);
		if (parentIndex == -1) {
			//System.out.println("Stop sifting up, found position");
			return;
		}
		
		//System.out.println("Parent: " + getElementAtIndex(parentIndex));
		if (getElementAtIndex(index).compareTo(getElementAtIndex(parentIndex)) < 0) {
			swap(parentIndex, index);
			//System.out.println("Swap : " + this.toString());
			siftUp(parentIndex);
		}
		
	}
	
	

}
