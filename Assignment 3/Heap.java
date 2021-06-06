/*
 * Assignment 3: Question 9
 */

public class Heap implements HeapADT {
	private Edge[] A;
	private int[] H;
	private int max;
	private int hMax;

	/*
	 * Empty Heap constructor for initializing.
	 */
	public Heap() {
	}

	/*
	 * Initialize a heap with the array keys of n elements indexed from 1 to n,
	 * where key[i] is the key of the element whose id is i.
	 * 
	 * @param keys an array of edges to represent keys
	 * 
	 * @param n represent ids
	 */
	@Override
	public void heap(Edge[] keys, int n) {
		// reference to the array of edges
		A = keys;
		max = n - 1;
		H = new int[2 * max];
		hMax = 2 * max - 1;
		// create the heap
		heapify();
	}

	/*
	 * Create the heap by sorting and rearranging the ids.
	 */
	private void heapify() {
		for (int i = max; i <= hMax; i++) {
			H[i] = i - max + 1;
		}

		for (int i = max - 1; i >= 1; i--) {
			if (A[H[2 * i]].getWeight() < A[H[2 * i + 1]].getWeight()) {
				H[i] = H[2 * i];
			} else {
				H[i] = H[2 * i + 1];
			}
		}
	}

	/*
	 * Check if the element whose id is id is in the heap.
	 * 
	 * @param id the edge to check if it is in the heap
	 * 
	 * @return true if id is in the heap, false otherwise
	 */
	@Override
	public boolean in_heap(Edge id) {
		// create an edge to use for comparison
		Edge check = new Edge(0, 0, 0);

		for (int i = 0; i < A.length; i++) {
			check = A[i];

			// If the endpoints and weight of check match id, return true
			if (check.getFirstEndPoint() == id.getFirstEndPoint() && check.getSecondEndPoint() == id.getSecondEndPoint()
					&& check.getWeight() == id.getWeight()) {
				return true;
				// If endpoints are reverse but still match, return true
			} else if (check.getFirstEndPoint() == id.getSecondEndPoint()
					&& check.getSecondEndPoint() == id.getFirstEndPoint() && check.getWeight() == id.getWeight()) {
				return true;
			}
		}

		// id is not in the heap
		return false;
	}

	/*
	 * The minimum key of the heap.
	 * 
	 * @return the minimum key of the heap
	 */
	@Override
	public int min_key() {
		return A[H[1]].getWeight();
	}

	/*
	 * The id of the element with minimum key in the heap.
	 * 
	 * @return the minimum id of the heap
	 */
	@Override
	public int min_id() {
		return H[1];
	}

	/*
	 * The key of the element whose id is id in the heap.
	 * 
	 * @param the id of the key element
	 * 
	 * @return the key of the element whose id is id in the heap
	 */
	@Override
	public int key(int id) {
		return A[id].getWeight();
	}

	/*
	 * Deletes the element with minimum key from the heap.
	 * 
	 * @return the element that was deleted
	 */
	@Override
	public Edge delete_min() {
		// create an edge to reference removed
		Edge removed = new Edge(0, 0, Integer.MAX_VALUE);
		A[0] = removed;

		H[H[1] + max - 1] = 0;

		Edge v = A[H[1]];

		int i = (int) Math.floor((H[1] + max - 1) / 2);

		// rehapify
		while (i >= 1) {
			if (A[H[2 * i]].getWeight() < A[H[2 * i + 1]].getWeight()) {
				H[i] = H[2 * i];
			} else {
				H[i] = H[2 * i + 1];
			}

			i = (int) Math.floor(i / 2);
		}

		return v;
	}

	/*
	 * Sets the key of the element whose id is id to new_key if its current key is
	 * greater than new_key.
	 * 
	 * @param id the id of the key element
	 * 
	 * @param new_key the new key value
	 */
	@Override
	public void decrease_key(int id, int new_key) {
		A[id].setWeight(new_key);
		int i = (int) Math.floor((id + max - 1) / 2);

		// reheapfiy
		while (i >= 1) {
			if (A[H[2 * i]].getWeight() < A[H[2 * i + 1]].getWeight()) {
				H[i] = H[2 * i];
			} else {
				H[i] = H[2 * i + 1];
			}

			i = (int) Math.floor(i / 2);
		}
	}
}