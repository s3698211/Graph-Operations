import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Incidence matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class IncidenceMatrix extends AbstractGraph
{
	
	private DynamicArrayMinimal vertices;
	private DynamicArrayMinimal edges;
	private int numVertices;
	private int numEdges;
	private int[][] matrix;
	private Map<String, SIRState> state;
	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	// Implement me!
    	numVertices = 0;
    	numEdges = 0;
    	vertices = new DynamicArrayMinimal();
    	edges = new DynamicArrayMinimal();

    	state = new HashMap<String, SIRState>();
    	matrix = null;
    } // end of IncidenceMatrix()

    public void addVertex(String vertLabel) {
    	// Implement me!
    	if (!(vertices.search(vertLabel) < 0)) {
    		System.err.println("Given vertex already exists");
    	} else {
	        vertices.add(vertLabel);
	        state.put(vertLabel, SIRState.S);
	        numVertices++;
    	}
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel) {
    	// Implement me!
    	// Check if the vertices exist
        if (vertices.search(srcLabel) < 0 || vertices.search(tarLabel) < 0) {
        	System.err.println("Given vertex does not exist");
        // Disallow matching vertices
        } else if (srcLabel.equals(tarLabel)){
        	System.err.println("Source and target vertex cannot be the same");
        } else {
        	// Check if the edge already exists
        	int edge = edges.search(srcLabel + " " + tarLabel);
        	int edgeReverse = edges.search(tarLabel + " " + srcLabel);
        	int edgeIndex = Math.max(edge, edgeReverse);
        	
        	// If the edge index is valid, it already exists
        	if (edgeIndex >= 0) {
        		System.err.println("An edge already exists between the given vertices");
        	} else {
	        	// Create an empty matrix with an extra column for the new edge
	        	int[][] dummyMatrix = new int[numVertices][numEdges+1];
	        	
	        	// If the matrix is not empty, copy the existing entries
	        	if (matrix != null) {
	        		for (int i = 0; i < matrix.length; i++) {
	        			for (int j = 0; j < matrix[i].length; j++) {
	        				dummyMatrix[i][j] = matrix[i][j];
	        			}
	        		}
	        	} else {
	        		// Otherwise, give each vertex an initial value of 0
	        		for (int i = 0; i < dummyMatrix.length; i++) {
	        			dummyMatrix[i][0] = 0;
	        		}
	        	}
	        	
	        	// Update the values of the incident vertices to 1
	        	int srcVert = vertices.search(srcLabel);
	        	int tarVert = vertices.search(tarLabel);
	        	dummyMatrix[srcVert][numEdges] = 1;
	        	dummyMatrix[tarVert][numEdges] = 1;
	        	
	        	
	        	setMatrix(dummyMatrix, numVertices, numEdges+1);
	        	String edgeLabel = srcLabel + " " + tarLabel;
	        	edges.add(edgeLabel);
	        	numEdges++;
        	}
        }
    } // end of addEdge()
    
    public void setMatrix(int[][]src, int verts, int edges) {
    	// create an empty matrix
    	matrix = new int[verts][edges];
    	for (int i = 0; i < verts; i++) {
    		for (int j = 0; j < edges; j++) {
    			matrix[i][j] = src[i][j];
    		}
    	}
    }
    

    public void toggleVertexState(String vertLabel) {
    	// Implement me!
    	if (state.get(vertLabel) == null) {
    		System.err.println("Given vertex does not exist");
    	} else {
    		// S -> I -> R
	    	switch(state.get(vertLabel)) {
	    	case S:
	    		state.put(vertLabel, SIRState.I);
	    		break;
	    	case I:
	    		state.put(vertLabel, SIRState.R);
	    		break;
	    	case R:
	    		break;
	    	}
    	}
    } // end of toggleVertexState()


    public void deleteEdge(String srcLabel, String tarLabel) {
    	// 
    	int edgeIndex = edges.search(srcLabel + " " + tarLabel);
    	//int edgeReverse = edges.search(tarLabel + " " + srcLabel);
    	//int edgeIndex = Math.max(edge, edgeReverse);
    	
        if (edgeIndex < 0) {
        	System.err.println("Given edge does not exist");
        } else {
        	// An edge is a column, so we create a matrix with 1 fewer columns
        	int[][] dummyMatrix = new int[numVertices][numEdges-1];
        	
        	for (int i = 0; i < matrix.length; i++) {
        		for (int j = 0; j < matrix[i].length; j++) {
        			// If the current column is the column to delete, nothing happens -- it is not copied
        			// If the current column precedes the column to delete, copy as normal
        			if (j < edgeIndex) {
        				dummyMatrix[i][j] = matrix[i][j];
        			// If the current column succeeds the column to delete, copy the remaining columns 1 index down
        			} else if (j > edgeIndex) {
        				dummyMatrix[i][j-1] = matrix[i][j];
        			}
        		}
        	}
        	
        	setMatrix(dummyMatrix, numVertices, numEdges-1);
        	edges.delete(edgeIndex);
        	numEdges--;
        }
    } // end of deleteEdge()


    public void deleteVertex(String vertLabel) {
        // Implement me!
    	if (vertices.search(vertLabel) < 0) {
        	System.err.println("Given vertex does not exist");
        } else {
        	// Find which edges include this vertex to be deleted
        	// Start from the highest index and descend as the array grows smaller
        	for (int i = edges.size()-1; i >= 0; i--) {
        		String[] tokens = edges.get(i).split(" ");
        		if (tokens[0].equals(vertLabel) || tokens[1].equals(vertLabel)) {
        			deleteEdge(tokens[0], tokens[1]);
        		}
        	}
    		int vertIndex = vertices.search(vertLabel);
    		
        	if (matrix != null) {
        		// A vertex is a row, so we create a matrix with 1 fewer rows
        		int[][] dummyMatrix = new int[numVertices-1][numEdges];
        		
        		for (int i = 0; i < matrix.length; i++) {
        			for (int j = 0; j < matrix[i].length; j++) {
        				// If the current row is the row to delete, nothing happens -- it is not copied
            			// If the current row precedes the row to delete, copy as normal
        				if (i < vertIndex) {
        					dummyMatrix[i][j] = matrix[i][j];
        				// If the current row succeeds the row to delete, copy the remaining rows 1 index down
        				} else if (i > vertIndex) {
        					dummyMatrix[i-1][j] = matrix[i][j];
        				}
        			}
        		}
        		
        		setMatrix(dummyMatrix, numVertices-1, numEdges);
        	}
        	
        	vertices.delete(vertIndex);
        	numVertices--;	
        }
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
        // Implement me!
    	String[] result = {};
    	
    	int vertex = vertices.search(vertLabel);
    	if (vertex < 0) {
    		System.err.println("Vertex does not exist");
    		return result;
    	}
    	
    	if (matrix == null) {
    		return result;
    	}
    	
    	// Create array of visited vertices
    	boolean visited[] = new boolean[numVertices];
    	for (int i = 0; i < visited.length; i++) {
    		visited[i] = false;
    	}
    	visited[vertex] = true;
    	
    	SimpleLinkedList queue = new SimpleLinkedList();
    	SimpleLinkedList tempQueue = new SimpleLinkedList();
    	queue.add(vertLabel);
    	DynamicArrayMinimal record = new DynamicArrayMinimal();
    	
    	// perform bfs
    	while (queue.size() != 0 && k > 0) {

            // Find all neighbours for the currentNode
            String currentNode = queue.get(0);
            int nodeIndex = vertices.search(currentNode);
            queue.removeByIndex(0);

            String[] neighbours = getNeighbours(currentNode);
            
            for (int i = 0; i < neighbours.length; i++) {
                int index = vertices.search(neighbours[i]);
                if(!visited[index]) {
                    visited[index] = true;

                    // Each neighbour then gets added to the queue and wait their turn
                    tempQueue.add(vertices.get(index));
                    record.add(vertices.get(index));
                }
            }
            // If the current queue is empty, progress to the next breadth of vertices waiting in the tempQueue (go to the next hop)
            if (queue.size() == 0) {
            	if (tempQueue.size() > 0) {
            		queue = tempQueue;
            		tempQueue = new SimpleLinkedList();
            	}
            	
            	k--;
            }
        }
    	
    	result = new String[record.size()];
    	for(int i = 0; i < record.size(); i++) {
    		result[i] = record.get(i);
    	}
        return result;
    } // end of kHopNeighbours()

    public String[] getNeighbours(String vertLabel) {
    	int vertIndex = vertices.search(vertLabel);
    	DynamicArrayMinimal neighbours = new DynamicArrayMinimal();
    	String[] result;
    	
    	for (int i = 0; i < matrix[0].length; i++) {
			// Check if the current edge contains this node
			if (matrix[vertIndex][i] == 1) {
				// Iterate through each vertex in the edge for the neighbouring node
				for (int j = 0; j < matrix.length; j++) {
					// Make sure it is not the current node
					if (j != vertIndex && matrix[j][i] == 1) {
						neighbours.add(vertices.get(j));
					}
				}
			}
    	}
    	
    	result = new String[neighbours.size()];
    	for (int i = 0; i < neighbours.size(); i++) {
    		result[i] = neighbours.get(i);
    	}
        return result;
    }
    public void printVertices(PrintWriter os) {
        // Implement me!
    	// Loop through array of all vertices and use their label to find their corresponding SIRState
    	for (int i = 0; i < numVertices; i++) {
    		String vert = vertices.get(i);
    		SIRState s = state.get(vert);
    		os.printf("(%s,%s) ", vert, s);
    		os.flush();
    		
    	}
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        for (int i = 0; i < numEdges; i++) {
        	String[] tokens = edges.get(i).split(" ");
        	os.println(tokens[0] + " " + tokens[1]);
        	os.flush();
        }
        
        //printMatrix(os);
        
    } // end of printEdges()
    
    // Prints the matrix (for debug purposes)
    public void printMatrix(PrintWriter os) {
    	os.println("Matrix:");
    	if (matrix == null) {
    		System.err.println("Matrix is null");
    	} else {
		    for (int i = 0; i < matrix.length; i++) {
		    	for (int j = 0; j < matrix[0].length; j++) {
		    		os.print(matrix[i][j] + " ");
		    	}
		    	os.println();
		    }
    	}
    }
    
    @Override
	public Map<String, SIRState> getState() {
		// TODO Auto-generated method stub
		return state;
	} 
} // end of class IncidenceMatrix