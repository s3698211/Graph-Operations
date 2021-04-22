# To run the program, on Window:

1. Run the command: javac -cp .;jopt-simple-5.0.2.jar *.java
2. Run the command: java -cp .;jopt-simple-5.0.2.jar RmitCovidModelling.java <yourChoice> (adjlist/adjmat/incmat)
		- adjlist for adjacency list, adjmat for adjacency matrix, incmat for incidence 		matrix
Below is the basic command of the program.

* AV <vertLabel> { add a vertex with label ’vertLabel’ into the graph. Has default SIR state of susceptible (S). AE <srcLabel> <tarLabel> { add an edge with source vertex ’srcLabel’, target
vertex ’tarLabel’ into the graph.
* TV <vertLabel> { toggle the SIR state of vertex ’vertLabel’. Togglng means go to the next state, i.e., from S to I, from I to R (if in R, remain in R).
* DV <vertLabel> { delete vertex ’vertLabel’ from the graph.}
* DE <srcLabel> <tarLabel> { remove edge with source vertex ’srcLabel’ and target vertex ’tarLabel’ from the graph.
* KN <k> <vertLabel> { Return the set of all neighbours for vertex ’vertLabel’ that are up to k-hops away. The ordering of the neighbours does not matter. See below
for the required format.
* PV { prints the vertex set of the graph. See below for the required format. The
vertices can be printed in any order.
* PE { prints the edge set of the graph. See below for the required format. The edges
can be printed in any order.
* SIR <infected seed vertices, delimited by ;> <infection probability> <recover
probability> 
 	
