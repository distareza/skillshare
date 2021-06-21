
public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

	public MaxHeap(Class<T> clazz) {
		super(clazz);
	}

	@Override
	protected void siftDown(int index) {
		//System.out.println("Sifting down : " + getElementAtIndex(index));

		int leftIndex = getLeftChildIndex(index);
		int rightIndex = getRightChildIndex(index);
		
		int largerIndex = -1;
		
		if (leftIndex != -1 && rightIndex != -1) {
			T leftElement = getElementAtIndex(leftIndex);
			T rightElement = getElementAtIndex(rightIndex);
			
			largerIndex = leftElement.compareTo(rightElement) > 0 ? leftIndex : rightIndex;
		} else if (leftIndex != -1) { 
			largerIndex = leftIndex;
		} else if (rightIndex != -1) {
			largerIndex = rightIndex;
		}
		
		if (largerIndex == -1) {
			//System.out.println("Stop shifting down, found position");
			return;
		}
		
		//System.out.println("Larger Child : " + getElementAtIndex(largerIndex));
		
		if (getElementAtIndex(largerIndex).compareTo(getElementAtIndex(index)) > 0) {
			swap(largerIndex, index);
			//System.out.println("Swap : " + this.toString());
			siftDown(largerIndex);
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
		if (getElementAtIndex(index).compareTo(getElementAtIndex(parentIndex)) > 0) {
			swap(parentIndex, index);
			//System.out.println("Swap : " + this.toString());
			siftUp(parentIndex);
		}
		
	}
	
	

}
