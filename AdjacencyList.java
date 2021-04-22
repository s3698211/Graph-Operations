import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;




// import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjacencyList extends AbstractGraph
{

    
	/**
	 * Contructs empty graph.
	 */
	private Map<String, MyList> adjList;
	private Map<String, SIRState> state;
	private DynamicArrayMinimal vertexList;
	
    public AdjacencyList() {
    	 // Implement me!
    	adjList = new HashMap<String, MyList>();
    	state = new HashMap<String, SIRState>();
    	vertexList = new DynamicArrayMinimal();
    	

    } // end of AdjacencyList()


    public void addVertex(String vertLabel) {
        // Implement me!
    	//also create a list to store vertex's neighbors
    	if(vertexList.search(vertLabel) >= 0) {
    		System.err.println("vertex is already in the graph");
    		
    	} else {
    		
        	
        	MyList vertNeighbors = new SimpleLinkedList();
        	adjList.put(vertLabel, vertNeighbors);
        	//Set up the initial state for the vertex
        	state.put(vertLabel, SIRState.S);
        	vertexList.add(vertLabel);
        	
    	}
    	
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel) {
        // Implement me!
    	if(vertexList.search(tarLabel) < 0) {
    		System.err.println(tarLabel + " does not exist");
    		return;
    	}
    	//Add the tarLabel to neighborList's of src
    	MyList srcNeighbor = adjList.get(srcLabel);
    	srcNeighbor.add(tarLabel);
    	adjList.remove(srcLabel);
    	adjList.put(srcLabel, srcNeighbor);
    	
    	MyList tarNeighbor = adjList.get(tarLabel);
    	tarNeighbor.add(srcLabel);
    	adjList.remove(tarLabel);
    	adjList.put(tarLabel, tarNeighbor);
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
		if(state.get(vertLabel)== SIRState.S) {
			//delete the old state and update a new one
			state.remove(vertLabel);
			state.put(vertLabel, SIRState.I);
		}
    	
    		

    } // end of toggleVertexState()


    public void deleteEdge(String srcLabel, String tarLabel) {
        // Implement me!
    	int srcVertex = vertexList.search(srcLabel);
    	int tarVertex = vertexList.search(tarLabel);
    	if(srcVertex < 0 || tarVertex < 0 ) {
    		System.err.println("Invalid input");
    		return;
    	}
    	//delete vertex tarLabel in src neighbor and vice versa
    	//if target vertex is not connect with src vertex, return a warning message
    	MyList srcNeighbor = adjList.get(srcLabel);
    	MyList tarNeighbor = adjList.get(tarLabel);
    	if(srcNeighbor.search(tarLabel) < 0) {
    		System.err.println("2 above vertexes does not connect:" + srcLabel + "," +tarLabel);
    		return;
    	}
    	srcNeighbor.remove(tarLabel);
    	tarNeighbor.remove(srcLabel);
    	//Update the adjList 
    	adjList.remove(srcLabel);
    	adjList.remove(tarLabel);
    	adjList.put(srcLabel, srcNeighbor);
    	adjList.put(tarLabel, tarNeighbor);
    	
    } // end of deleteEdge()


    public void deleteVertex(String vertLabel) {
        //Check if the vertex is in the graph
    	
    	int vertex = vertexList.search(vertLabel);
    	if(vertex < 0) {
    		System.err.println("Vertex does not exist");
    		return;
    	}
    	// Delete the edge which contain vertLabel first
    	MyList verNeighbor = adjList.get(vertLabel);
    	int verNeighborSize = verNeighbor.size();
    	
    	
    	while(verNeighborSize > 0) {
    		
    		deleteEdge(vertLabel, verNeighbor.get(verNeighborSize-1));
    		verNeighborSize--;
    	}
    	//Delete the vertex's state, vertexList and in the adjList
    	state.remove(vertLabel);
    	vertexList.delete(vertexList.search(vertLabel));
    	adjList.remove(vertLabel);
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
        // Implement me!
    	PrintWriter os = new PrintWriter(System.out, true);
    	int vertex = vertexList.search(vertLabel);

    	DynamicArrayMinimal formEdgeVertList = new DynamicArrayMinimal();
    	
    	String[] result = {};
    	
    	//Create an array to store the neighbor of vertex in K distance
    	DynamicArrayMinimal record = new DynamicArrayMinimal();
    	//if vertex is not in the graph
    	if(vertex < 0) {
    		result = new String [0];
    		System.err.println("Vertex does not exist");
    		return result;
    	}	
    
    	//If the vertex doesn't have any neighbor, return an empty string
    	
    	if(adjList.get(vertLabel).size() == 0) {
    		return result;
    	}
    	//Find all the vertex that are not isolated
    	for(String key : adjList.keySet()) {
    		for(int i = 0 ; i < adjList.get(key).size(); i++) {
    			String currentVertex = adjList.get(key).get(i);
    			if(formEdgeVertList.search(currentVertex) < 0) {
    				formEdgeVertList.add(currentVertex);   				
    			}
    		}
    		
    	}
    	
    
		bfsToK(k ,vertLabel ,formEdgeVertList, record);   	
      		
    	

    	
    	result = new String [record.size()];
    	for(int i = 0; i < record.size(); i++) {
    		
    		result[i] = record.get(i);
    		
    	}
    
        // please update!
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
    public void bfsToK(int k, String node,DynamicArrayMinimal formEdgeVertList,DynamicArrayMinimal record) {
    	//mark all the vertices as not visited
    	boolean visited[] = new boolean[formEdgeVertList.size()];    	
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
    		
    		MyList nodeNeigbors = adjList.get(queue.get(0));
    		queue.removeByIndex(0);
    		
    		//Add node's neighbor to queue and and continue bfs them.
    		if(nodeNeigbors.size() != 0) {
    			
    			for(int i = 0 ; i < nodeNeigbors.size(); i++) {
    				int index = formEdgeVertList.search(nodeNeigbors.get(i));
    				String nextNode = nodeNeigbors.get(i);
    				
    				if(!visited[index]) {
    					visited[index] = true;
    					tempQueue.add(formEdgeVertList.get(index));
    					//record the visited nodes
    					record.add(formEdgeVertList.get(index));    					
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
   

    public void printVertices(PrintWriter os) {
        // Implement me!
    	for(String key : state.keySet()) {
    		os.print("(" + key + "," + state.get(key)+")" + " ");
    		os.flush();
    	}
    } // end of printVertices()


    public void printEdges(PrintWriter os) {       
    
    	for(String key : adjList.keySet()) {
    		for(int i = 0 ; i < adjList.get(key).size() ; i++) {
    			os.println(key  + " "+ adjList.get(key).get(i));
    		}
    	}
    } // end of printEdges()

	@Override
	public Map<String, SIRState> getState() {
		// TODO Auto-generated method stub
		return state;
	}   
    
   
} // end of class AdjacencyList

