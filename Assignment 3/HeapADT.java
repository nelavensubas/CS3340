/*
 * Assignment 3: Question 9
 */

public interface HeapADT {
	/*
	 * Initialize a heap with the array keys of n elements indexed from 1 to n,
	 * where key[i] is the key of the element whose id is i.
	 * 
	 * @param keys an array of edges to represent keys
	 * 
	 * @param n represent ids
	 */
	void heap(Edge keys[], int n);

	/*
	 * Check if the element whose id is id is in the heap.
	 * 
	 * @param id the edge to check if it is in the heap
	 * 
	 * @return true if id is in the heap, false otherwise
	 */
	boolean in_heap(Edge id);

	/*
	 * The minimum key of the heap.
	 * 
	 * @return the minimum key of the heap
	 */
	int min_key();

	/*
	 * The id of the element with minimum key in the heap.
	 * 
	 * @return the minimum id of the heap
	 */
	int min_id();

	/*
	 * The key of the element whose id is id in the heap.
	 * 
	 * @param the id of the key element
	 * 
	 * @return the key of the element whose id is id in the heap
	 */
	int key(int id);

	/*
	 * Deletes the element with minimum key from the heap.
	 * 
	 * @return the element that was deleted
	 */
	Edge delete_min();

	/*
	 * Sets the key of the element whose id is id to new_key if its current key is
	 * greater than new_key.
	 * 
	 * @param id the id of the key element
	 * 
	 * @param new_key the new key value
	 */
	void decrease_key(int id, int new_key);
}