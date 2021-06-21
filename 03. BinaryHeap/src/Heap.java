
import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class Heap <T extends Comparable<T>> {

	private static int MAX_SIZE = 10;
	private T[] array;
	private int count = 0;
	
	@SuppressWarnings("unchecked")
	public Heap(Class<T> clazz) {
		array = (T[]) Array.newInstance(clazz, MAX_SIZE);
	}

	public int getCount() {
		return this.count;
	}
	
	public boolean isEmpty() {
		return this.count == 0;
	}
	
	public boolean isFull() {
		return this.count == this.array.length;
	}
	
	public T getElementAtIndex(int index) {
		return this.array[index];
	}
	
	public int getLeftChildIndex(int parentIndex) {
		if (parentIndex < 0) return -1;
		int leftChildIndex = 2 * parentIndex + 1;
		if (leftChildIndex >= count) return -1;
		return leftChildIndex;
	}

	public int getRightChildIndex(int parentIndex) {
		if (parentIndex < 0) return -1;
		int rightChildIndex = 2 * parentIndex + 2;
		if (rightChildIndex >= count) return -1;
		return rightChildIndex;
	}
	
	public int getParentIndex(int childIdx) {
		if (childIdx <=0 || childIdx >= count) return -1;
		return (childIdx - 1) / 2;
	}
	
	protected void swap(int idx1, int idx2) {
		T temp = array[idx1];
		array[idx1] = array[idx2];
		array[idx2] = temp;
	}
	
	protected abstract void siftDown(int idx);
	
	protected abstract void siftUp(int idx);

	public T getHighestPriority() throws HeapEmptyException {
		if (count == 0)
			throw new HeapEmptyException("Empty Heap!");
		
		return this.array[0];
	}
	
	public void insert(T value) throws HeapFullException {
		if (count >= this.array.length)
			throw new HeapFullException("Full Heap!");
		
		array[this.count] = value;
		this.count++;
		
		//System.out.println("Inserted : " + value + " " + this.toString());
		siftUp(this.count - 1);
	}
	
	public T removeHighestPriority() throws HeapEmptyException {
		T first = getHighestPriority();
		
		this.array[0] = this.array[this.count -1];
		this.array[this.count-1] = null;
		this.count--;
		
		siftDown(0);
		return first;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.array);
	}
	
}
