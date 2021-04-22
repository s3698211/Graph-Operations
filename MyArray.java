/**
 * Interface for the (dynamic) array implementations we will develop in this lab.
 * Note, as we do not seek to implement all the functionality in the built-in
 * Collections interface, we have developed our own interface.
 * Also note that we can implement other data structures from this MyArray,
 * and following labs and assignments will take a similar structure, hence it
 * is worth revising what interfaces and the classes implementing these interfaces
 * are doing.
 *
 * @author Jeffrey Chan, RMIT
 */
public interface MyArray
{

    // Default value to return for search when value isn't found in array.
    public static final int OUT_OF_INDEX = -1;

    /**
     * Sets/replaces the value at index.  Indices start at 0.
     *
     * @param index Position in array to set/replace value.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public abstract void set(int index, String newValue) throws IndexOutOfBoundsException;

    /**
     * Gets/retrieves the value at index.  Indices start at 0.
     *
     * @param index Position in array to retrieve value from.
     * @return value of array at specified index.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public abstract String get(int index) throws IndexOutOfBoundsException;


    /**
     * Add value to end of array.
     *
     * @param newValue Value to add to array.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public abstract void add(String newValue) throws IndexOutOfBoundsException;


    /**
     * Insert value at position 'index'.  Indices start at 0.
     *
     * @param index Position in array to add new value to.
     * @param newValue Value to add to array.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public abstract void insert(int index, String newValue) throws IndexOutOfBoundsException;


    /**
     * Searches for the index that contains value.  If value is not present,
     * method returns -1 (OUT_OF_INDEX).
     * If there are multiple values that could be returned, return the one with
     * the smallest index.
     *
     * @param value Value to search for.
     * @return Index where value is located, otherwise returns -1 (OUT_OF_INDEX).
     */
    
    public abstract void delete(int index) throws IndexOutOfBoundsException;


    /**
     * Delete a value at specific index.  If value is not present,
     * Throw IndexOutOfBound exception
     
     
     * @param value Value to search for.
   	 * @throws IndexOutOfBoundsException
     */ 
    public abstract int search(String value);
    
    /**
     * Return the size of the array
     *
     * 
     * @return an integer represents for the size of the array.
     */
    public abstract int size();


    /**
     * Print the array from front to end (index 0 to end).
     */
    

} // end of interface MyArray
