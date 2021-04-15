package assign10;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class represents a BinarymaxHeap that implements the priority queue
 * interface.
 * 
 * 
 * @author Teigen Edmundson and Gabe Sherman
 * @version April 13, 2021
 *
 * @param <E>
 */
public class BinaryMaxHeap<E> implements PriorityQueue<E> {

	private E[] array;
	private int size;
	private Comparator<? super E> cmp;

	/**
	 * Creates an empty BinaryMaxHeap that assumes E extends comparable instead of
	 * using a provided comparator.
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap() {
		array = (E[]) new Object[100];
		size = 0;
		cmp = null;
	}

	/**
	 * Creates an empty BinaryMaxHeap that uses the provided comparator for
	 * comparing elements
	 * 
	 * @param cmp - comparable that is used to compare elements
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(Comparator<? super E> cmp) {
		this.cmp = cmp;
		array = (E[]) new Object[100];
		size = 0;
	}

	/**
	 * Creates a BinaryMaxHeap and populates it with elements from the provided list
	 * and assumes E extends comparable instead of using a provided comparator.
	 * 
	 * @param list - list that is used to populate the BinaryMaxHeap
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> list) {
		array = (E[]) new Object[list.size() + 200];
		cmp = null;
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
			size++;
		}
		for (int i = (size / 2); i >= 0; i--) {
			percolateDown(i);
		}
	}

	/**
	 * Creates a BinaryMaxHeap and populates it with the elements from the provided
	 * list using the provided comparator.
	 * 
	 * @param list - list that is used to populate the BinaryMaxHeap
	 * @param cmp  - comparable that is used to compare elements
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> list, Comparator<? super E> cmp) {
		this.cmp = cmp;
		array = (E[]) new Object[list.size() + 200];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
			size++;
		}
		for (int i = (size / 2); i >= 0; i--) {
			percolateDown(i);
		}
	}

	/**
	 * Private helper method that orders an element at the proper position in the
	 * heap.
	 * 
	 * @param index - index of the element that needs to be added.
	 */
	private void percolateDown(int index) {
		if (leftChildIndex(index) == -1 && rightChildIndex(index) == -1) { // leaf node
			return;

		}

		if (rightChildIndex(index) == -1) { // one child
			if (compare(array[index], leftChildData(index)) < 0) { // left child is bigger than parent
				E temp = array[index];
				array[index] = leftChildData(index);
				array[leftChildIndex(index)] = temp;
				return;
			} else {
				return; // left child is smaller than parent
			}
		}

		if (compare(array[index], leftChildData(index)) > 0 && compare(array[index], rightChildData(index)) > 0) {
			return;
		}

		if (compare(array[index], leftChildData(index)) < 0 || compare(array[index], rightChildData(index)) < 0) {
			E temp = array[index];
			if (compare(leftChildData(index), rightChildData(index)) > 0) { // left child is bigger
				array[index] = leftChildData(index);
				array[leftChildIndex(index)] = temp;
				percolateDown(leftChildIndex(index));
			} else {
				array[index] = rightChildData(index);
				array[rightChildIndex(index)] = temp;
				percolateDown(rightChildIndex(index));
			}
		}
	}

	/**
	 * Private helper method used for adding an element into the heap in the proper
	 * position.
	 * 
	 * @param index - index of the element that needs to be proper position
	 */
	private void percolateUp(int index) {
		if (index == 0) {
			return;
		}
		if (compare(parentData(index), array[index]) > 0) {
			return;
		}
		if (compare(parentData(index), array[index]) < 0) {
			E temp = array[index];
			array[index] = parentData(index);
			array[parentIndex(index)] = temp;
			percolateUp(parentIndex(index));
		}
	}

	/**
	 * returns the index of the parent of the element
	 * 
	 * @param index - the index of the current element
	 * @return - the index of the parent of the element
	 */
	private int parentIndex(int index) {
		return (index - 1) / 2;
	}

	/**
	 * 
	 * @param index - the index of the current element
	 * @return the data associated with the parent of the current element
	 */
	private E parentData(int index) {
		return array[parentIndex(index)];
	}

	/**
	 * @param i - the index of the current element
	 * @return the index of the left child of the element, or -1 if the left child
	 *         does not exist.
	 */
	private int leftChildIndex(int i) {
		int index = 2 * i + 1;
		if (index >= size) {
			return -1;
		}
		return index;
	}

	/**
	 * @param i - the index of the current element
	 * @return the index of the right child of the element, or -1 if the right child
	 *         does not exist.
	 */
	private int rightChildIndex(int i) {
		int index = 2 * (i + 1);
		if (index >= size) {
			return -1;
		}
		return index;
	}

	/**
	 * @param i - the index of the current element
	 * @return the data of the left child of the element, or null if the left child
	 *         does not exist.
	 */
	private E leftChildData(int i) {
		int index = 2 * i + 1;
		if (index >= size) {
			return null;
		}
		return array[index];
	}

	/**
	 * @param i - the index of the current element
	 * @return the data of the right child of the element, or null if the right
	 *         child does not exist.
	 */
	private E rightChildData(int i) {
		int index = 2 * (i + 1);
		if (index >= size) {
			return null;
		}
		return array[index];
	}

	/**
	 * A private helper method that determines whether to use the generic type's
	 * provided comparator or its comparable.
	 * 
	 * @param item1  - the first item to be compared
	 * @param item2- the item to be compared with item1
	 * @return an integer greater than 0 if item1 is greater than item2, an integer
	 *         equal to zero if item1 is equal to item2, and an integer less than
	 *         zerp if item1 is less than item2.
	 */
	@SuppressWarnings("unchecked")
	private int compare(E item1, E item2) {
		if (cmp == null) {
			return ((Comparable<? super E>) item1).compareTo(item2);
		}
		return cmp.compare(item1, item2);
	}

	/**
	 * A helper method that resizes the array to the size * 2.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		E[] tempArray = (E[]) new Object[size * 2];
		for (int i = 0; i < size; i++) {
			tempArray[i] = array[i];
		}
		array = tempArray;
	}

	/**
	 * Adds the given item to this priority queue.
	 * 
	 * @param item
	 */
	public void add(E item) {
		if (size + 1 > array.length) {
			resize();
		}
		array[size] = item;
		percolateUp(size);
		size++;
	}

	/**
	 * Returns, but does not remove, the maximum item this priority queue.
	 * 
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public E peek() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return array[0];
	}

	/**
	 * Returns and removes the maximum item this priority queue.
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public E extractMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		E returnVal = array[0];
		array[0] = array[size - 1];
		percolateDown(0);
		size--;
		return returnVal;
	}

	/**
	 * Returns the number of items in this priority queue.
	 * 
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns true if this priority queue is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Empties this priority queue of items.
	 */
	public void clear() {
		size = 0;

	}

	/**
	 * Creates and returns an array of the items in this priority queue, in the same
	 * order they appear in the backing array.
	 */
	public Object[] toArray() {
		@SuppressWarnings("unchecked")
		E[] returnArray = (E[]) new Object[size];
		for (int i = 0; i < size; i++) {
			returnArray[i] = array[i];
		}
		return returnArray;

	}
}
