import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;




/**
 * Adjacency matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class AdjacencyMatrix extends AbstractGraph
{

	/**
	 * Contructs empty graph.
	 * 
	 */
	private int vertexNum;
	private DynamicArrayMinimal vertexList;
	
	private Map<String, SIRState> state;
	private int [][]matrix;
	
    public AdjacencyMatrix() {
    	// Implement me!
    	vertexNum = 0;
    	vertexList = new DynamicArrayMinimal();
    	
    	state = new HashMap<String, SIRState>();
    	matrix=null;
    } // end of AdjacencyMatrix()


    public void addVertex(String vertLabel) {
        // Implement me!
    	if(vertexList.search(vertLabel) >= 0) {
    		System.err.println("vertex is already in the graph");    		
    	} else {
    		vertexNum++;
        	vertexList.add(vertLabel);    	
        	state.put(vertLabel, SIRState.S);
    	}
    	
    	
    	
    } // end of addVertex()
    
    public void setMatrix(int[][]src, int srcLength) {
    	matrix = new int [srcLength][srcLength];
    	for(int i = 0; i < srcLength; i++) {
    		for(int j = 0 ; j < srcLength; j++) {
    			matrix[i][j] = src[i][j];
    		}
    	}
    }

    public void addEdge(String srcLabel, String tarLabel) {
        
    	// Implement me!
    	//Check if the srcLabel and tarLable belongs to the graph
    	if(vertexList.search(srcLabel) < 0 || vertexList.search(tarLabel) < 0) {
    		System.out.println("The given vertex does not exist");
    	} else {
    		//extend the matrix    		
    		
        	int[][] dummyMatrix = new int[vertexNum][vertexNum];
        	
        	
        	int start = vertexList.search(srcLabel);
        	int end = vertexList.search(tarLabel);
        	//1. If matrix is empty        	
        	if(matrix == null) {
        		        	       	
            	dummyMatrix[start][end] = 1;
            	dummyMatrix[end][start] = 1;
            	setMatrix(dummyMatrix,vertexNum);
            	
        	} 
        	//If not
        	//copy the data from old Matrix to dummyMatrix, add changes to dummyMatrix and then make it become our a new matrix
        	else {
        		for(int i = 0; i < matrix[0].length; i++) {
    	    		for(int j = 0 ; j < matrix[0].length; j++) {
    	    			dummyMatrix[i][j] = matrix[i][j];
    	    			
    	    		}
            	}
            	
            	        	       	
            	dummyMatrix[start][end] = 1;
            	dummyMatrix[end][start] = 1;
            	setMatrix(dummyMatrix,vertexNum);
        	}
        	
    	}
    	
    } // end of addEdge()
    

    public void toggleVertexState(String vertLabel) {
        // Implement me!
    	//If the vertex is not in graph, return a warning message
    	if(vertexList.search(vertLabel) < 0) {
    		System.err.println("Vertex " + vertLabel + " does not exist");
    		return;
    	}
    	
    	
    	if(state.get(vertLabel) == SIRState.I) {
			//delete the old state and update a new one
			state.remove(vertLabel);
			state.put(vertLabel, SIRState.R);
		}
		if(state.get(vertLabel) == SIRState.S) {
			//delete the old state and update a new one
			state.remove(vertLabel);
			state.put(vertLabel, SIRState.I);
		} 
    	
    } // end of toggleVertexState()


    public void deleteEdge(String srcLabel, String tarLabel) {
        // Implement me!
    	int srcIndex = vertexList.search(srcLabel);
    	int tarIndex = vertexList.search(tarLabel);        	
    	
    	matrix[srcIndex][tarIndex] = 0;
    	matrix[tarIndex][srcIndex] = 0;
    } // end of deleteEdge()


    public void deleteVertex(String vertLabel) {
        // Implement me!
    	int vertex = vertexList.search(vertLabel);
    	
    	if(vertex < 0) {
    		System.err.println("The given vertex does not exist");
    	}
    	else {
    		//copy the all the vertex in the matrix except the deleted vertex
    		
    		vertexNum--;
    		int [][] dummyMatrix = new int[vertexNum][vertexNum];
    		Map<Integer, DynamicArrayMinimal> tempMap = new HashMap<Integer, DynamicArrayMinimal>();
    		if (matrix != null) {
    			for(int i = 0; i < matrix[0].length; i++) {
    				if(i == vertex) continue;
        			DynamicArrayMinimal tmpArr = new DynamicArrayMinimal();
        			for(int j = 0; j < matrix[0].length; j++) {
        				if (j == vertex) continue;
        				tmpArr.add(Integer.toString(matrix[i][j]));
        				tempMap.put(i, tmpArr);
        			}
        		}
    		}
    		int count = 0;
    		for(int key : tempMap.keySet()) {   			
    			
    			
    			for(int i = 0 ; i < tempMap.get(key).size() && count < dummyMatrix.length; i++) {
    				
    				dummyMatrix[count][i] = Integer.parseInt(tempMap.get(key).get(i));
    				
    			}
    			count++;
    		}

    		vertexList.delete(vertex);
    		state.remove(vertLabel);
    		setMatrix(dummyMatrix, vertexNum);
    		
    	}
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
        // Implement me!
    	
    	int vertex = vertexList.search(vertLabel);
    	String[] result = {};
    	DynamicArrayMinimal formEdgeVertList = new DynamicArrayMinimal();   	
    	
    	//Create an array to store the neighbor of vertex in K distance
    	DynamicArrayMinimal record = new DynamicArrayMinimal();
    	if(vertex < 0) {
    		result = new String [0];
    		System.err.println("Vertex does not exist");
    		return result;
    	}
    	   	
    	if(matrix == null) {
    		return result;
    	}
    	
    	if(!hasNeighbors(vertLabel)) return result;
    	//find out all the vertices that are no isolated
    	for(int i = 0 ; i < matrix[vertex].length; i++) {
    		for(int j = 0; j < matrix[0].length; j++) {
    			if(matrix[i][j] == 1 && formEdgeVertList.search(vertexList.get(j)) <0 ) {
    				formEdgeVertList.add(vertexList.get(j));
    			}
    		}
    	}
    	
    	//if the vertLabel is isolated
    	if(formEdgeVertList.search(vertLabel) < 0) return result;   	

    	bfsToK(k,  vertLabel, formEdgeVertList,record);   	
    	
    	result = new String [record.size()];
    	
    	for(int i = 0; i < record.size(); i++) {    		
    		result[i] = record.get(i);    		
    	}  	
        
        return result;
    } // end of kHopNeighbours()

  
    /**
	 * BFS to distance k, method used in k-hop neighbor
	 *
	 * @param k: distance k
	 * @param node: node to search
	 * @param formEdgeVertList: list of vertices that are not isolated
	 * @param record: represent for the final result in k-hop neighbor function 
	 */
    public void bfsToK(int k, String node, DynamicArrayMinimal formEdgeVertList,DynamicArrayMinimal record) {
    	//mark all the vertices as not visited
    	boolean visited[] = new boolean[formEdgeVertList.size()];
    	PrintWriter os = new PrintWriter(System.out, true);
    	SimpleLinkedList queue = new SimpleLinkedList();
    	SimpleLinkedList tempQueue = new SimpleLinkedList();
    	//mark the current node as visited and enqueue it
    	for(int i = 0 ; i < visited.length; i++) {
    		visited[i] = false;
    	}
    	visited[formEdgeVertList.search(node)] = true;
    	queue.add(node);
    	
    	while(queue.size() != 0 && k > 0) {
    	 		
    		//dequeue a vertex from queue 		
    		String currentNode = queue.get(0);
    		//Find the index of currentNOde in matrix
    		int matrixIndex = vertexList.search(currentNode);
    		queue.removeByIndex(0);
    		
    		//Find the neighbor of the currentNode, record and record them
    		if(matrix[matrixIndex].length != 0) {
    			
    			for(int i = 0 ; i < matrix[matrixIndex].length; i++) {
    				if(matrix[matrixIndex][i] == 1) {
    					String nextNode = vertexList.get(i);
    					
        				if(!visited[formEdgeVertList.search(nextNode)]) {
        					visited[formEdgeVertList.search(nextNode)] = true;
        					
        					//add to the queue 
        					tempQueue.add(vertexList.get(i));
        					record.add(vertexList.get(i));    					
        				}
    				}   				   				
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
		
    }
    
    public boolean hasNeighbors(String vertLabel) {
    	int vertex = vertexList.search(vertLabel);
    	for(int i = 0 ; i < matrix[vertex].length; i++) {
    		if(matrix[vertex][i] == 1) return true;
    	}
    	
    	return false;
    }
    public void printVertices(PrintWriter os) {  	
		
    	for(int i = 0 ; i < vertexNum; i++) {
    		os.print("(" +vertexList.get(i) + "," + state.get(vertexList.get(i)) + ") ");
    		os.flush();
    	}
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        // Implement me!
    	if(matrix == null) {
    		System.err.println("There is no edge.");
    	} else {
    		for(int i = 0; i < matrix[0].length; i++) {
        		for(int j = 0 ; j <  matrix[0].length; j++) {
        			
        			if(matrix[i][j] == 1) {
        				
        				os.println(vertexList.get(i) + " " + vertexList.get(j));
        				
        			}
        		}
        	}    		
    	}
    	
    } // end of printEdges()
    
    @Override
	public Map<String, SIRState> getState() {
		// TODO Auto-generated method stub
		return state;
	} 
} // end of class AdjacencyMatrix