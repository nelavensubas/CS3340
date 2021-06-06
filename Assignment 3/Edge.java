/*
 * Assignment 3: Question 9
 */

public class Edge {
	private int u;
	private int v;
	private int weight;

	/*
	 * Constructor that creates an edge with a first and second end point with its
	 * weight.
	 * 
	 * @param first first end point of the edge
	 * 
	 * @param second second end point of the edge
	 * 
	 * @param weight weight of the edge
	 */
	public Edge(int first, int second, int weight) {
		this.u = first;
		this.v = second;
		this.weight = weight;
	}

	/*
	 * Get the first end point of the edge.
	 * 
	 * @return the first end point
	 */
	public int getFirstEndPoint() {
		return this.u;
	}

	/*
	 * Set the first end point of the edge.
	 * 
	 * @param the new first end point
	 */
	public void setFirstEndPoint(int u) {
		this.u = u;
	}

	/*
	 * Get the second end point of the edge.
	 * 
	 * @return the new second end point
	 */
	public int getSecondEndPoint() {
		return this.v;
	}

	/*
	 * Set the second end point of the edge.
	 * 
	 * @param the new second end point
	 */
	public void setSecondEndPoint(int v) {
		this.v = v;
	}

	/*
	 * Get the weight of the edge.
	 * 
	 * @return the weight of the edge
	 */
	public int getWeight() {
		return this.weight;
	}

	/*
	 * Set the weight of the edge.
	 * 
	 * @param the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/*
	 * Print out the edge in string form.
	 * 
	 * @return the components of an edge
	 */
	public String toString() {
		return "U = " + this.u + " V = " + this.v + " Weight = " + this.weight + "\n";
	}
}