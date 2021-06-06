import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * Assignment 3: Question 9
 */

public class ShortestPath {
	private static int V;

	/*
	 * Dijkstra single source shortest path algorithm for a weighted directed graph
	 * using a heap data structure.
	 * 
	 * @param graph the weighted directed graph
	 * 
	 * @param src the source vertex in the graph
	 */
	public static void dijkstra(ArrayList<LinkedList<Edge>> graph, int src) {
		Edge[] keys = new Edge[V + 1];
		int[] pi = new int[V + 1];

		// Initialize keys for the heap
		for (int i = 0; i <= V; i++) {
			// Each key defaults as 0,0, and INF, and its parent is -1
			Edge e = new Edge(0, 0, Integer.MAX_VALUE);
			keys[i] = e;
			pi[i] = -1;
		}

		keys[src].setWeight(0);
		keys[src].setFirstEndPoint(src);
		keys[src].setSecondEndPoint(src);
		pi[src] = src;

		// Create the heap based off of the keys
		Heap heap = new Heap();
		heap.heap(keys, keys.length);

		System.out.println("Dijkstra's Single Source Shortest Path Algorithm:");

		int cost = 0;
		Edge minEdge = new Edge(0, 0, 0);

		// Get the minimum value until all values have been used
		while ((cost = (minEdge = heap.delete_min()).getWeight()) != Integer.MAX_VALUE) {
			// Check if either endpoint does not point to 0
			if (minEdge.getFirstEndPoint() != 0 && minEdge.getSecondEndPoint() != 0) {
				// Get the list of adjacent edges for the second endpoint
				LinkedList<Edge> list = graph.get(minEdge.getSecondEndPoint());

				// Go through all adjacent edges
				for (int i = 0; i < list.size(); i++) {
					// Create a reference to the edge at position i of the list
					Edge listEdge = list.get(i);

					// Update keys, pi, and heap if the edge isn't in the heap and the weight is
					// less than the current record
					if (!heap.in_heap(listEdge)
							&& listEdge.getWeight() < keys[listEdge.getSecondEndPoint()].getWeight()) {
						keys[listEdge.getSecondEndPoint()] = listEdge;
						pi[listEdge.getSecondEndPoint()] = listEdge.getFirstEndPoint();
						heap.decrease_key(listEdge.getSecondEndPoint(), listEdge.getWeight());
					}
				}

				// If the weight of the edge is 0, then this is the root
				if (minEdge.getWeight() == 0) {
					System.out.println("(" + minEdge.getFirstEndPoint() + ", " + minEdge.getSecondEndPoint() + "): "
							+ minEdge.getWeight());
				} else {
					System.out.println("(" + minEdge.getSecondEndPoint() + ", " + pi[minEdge.getSecondEndPoint()]
							+ "): " + minEdge.getWeight());
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// Read the first integer from the file as it represents the number of vertices
		V = Integer.parseInt(in.nextLine());

		ArrayList<LinkedList<Edge>> adj_list = new ArrayList<LinkedList<Edge>>();

		for (int i = 0; i <= V; i++) {
			adj_list.add(new LinkedList<Edge>());
		}

		while (in.hasNext()) {
			String s = in.nextLine();

			int u, v, w;

			// Tokenize the string and set the values to u, v, and w
			StringTokenizer tok = new StringTokenizer(s);
			u = Integer.parseInt(tok.nextToken());
			v = Integer.parseInt(tok.nextToken());
			w = Integer.parseInt(tok.nextToken());

			// Create an edge going from u to v and v to u
			Edge uToV = new Edge(u, v, w);
			Edge vToU = new Edge(v, u, w);

			// Add the edge to the correct list
			adj_list.get(u).addLast(uToV);
			adj_list.get(v).addLast(vToU);
		}

		// Loop through the first lists
		for (int i = 1; i <= V; i++) {
			// Print out what node we are looking at
			System.out.print(adj_list.get(i).get(0).getFirstEndPoint() + ": ");

			// Loop through for the adjacent edges
			for (int j = 0; j < adj_list.get(i).size(); j++) {
				System.out.print(adj_list.get(i).get(j).getSecondEndPoint() + "(w:"
						+ adj_list.get(i).get(j).getWeight() + "), ");
			}

			System.out.println();
		}

		// Set the src to be the first possible node in the adjacency list
		int src = adj_list.get(1).get(0).getFirstEndPoint();
		System.out.println();
		dijkstra(adj_list, src);
		in.close();
	}
}