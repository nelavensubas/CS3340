/*
 * Assignment 1: Question 10a
 */

public class uandf {
	private int[] parent;
	private int[] rank;
	private boolean finalSet = false;

	/*
	 * Constructor to create a Disjoint-Set data structure with n elements.
	 * 
	 * @param n the number of elements for the data structure
	 */
	public uandf(int n) {
		parent = new int[n];
		rank = new int[n];
	}

	/*
	 * Create a new set whose only member is i
	 * 
	 * @param i the member and representative of the set
	 */
	public void makeSet(int i) {
		parent[i] = i;
	}

	/*
	 * Unite the dynamic sets that contains i and j into a new set that is the union
	 * of these two sets
	 * 
	 * @param i the first dynamic set
	 * 
	 * @param j the second dynamic set
	 */
	public void unionSets(int i, int j) {
		// Find the sets for i and j
		i = findSet(i);
		j = findSet(j);

		if (rank[i] > rank[j]) {
			parent[j] = parent[i];
		} else if (rank[i] < rank[j]) {
			parent[i] = parent[j];
		} else {
			parent[j] = parent[i];
			rank[i]++;
		}
	}

	/*
	 * Get the representative of the set containing i
	 * 
	 * @param i the set
	 * 
	 * @return the representative of the set that has i
	 */
	public int findSet(int i) {
		if (finalSet == false) {
			if (parent[i] != i) {
				return (parent[i] = findSet(parent[i]));
			} else {
				return i;
			}
		} else {
			return parent[i];
		}
	}

	/*
	 * Get the total number of current sets and finalize the current sets
	 * 
	 * @return the total number current sets with the finalized current sets
	 */
	public int finalSets() {
		for (int i = 1; i < parent.length; i++) {
			if (parent[i] != 0) {
				// Find the set that i belongs to
				findSet(i);
			}
		}

		int current = 1;

		for (int i = 1; i < parent.length; i++) {
			if (parent[i] == i) {
				parent[i] = current++;
				rank[i] = 1;
			} else {
				rank[i] = 0;
			}
		}

		for (int i = 1; i < parent.length; i++) {
			if (rank[i] == 0 && parent[i] > 0) {
				parent[i] = parent[parent[i]];
			}
		}

		finalSet = true;
		return current - 1;
	}
}