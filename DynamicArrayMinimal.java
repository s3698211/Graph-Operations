import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Array implementation that implements MyArray interface using a minimal approach.
 *
 * @author Jeffrey Chan, RMIT
 */
public class DynamicArrayMinimal implements MyArray
{
    /** Reference to the memory of array. */
    protected String array[];


    /**
     * Constructor.
     */
    public DynamicArrayMinimal() {
        
    	array = null;
    } // end of DynamicArray()


    /**
     * Sets/replaces the value at index.  Indices start at 0.
     *
     * @param index Position in array to set/replace value.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public void set(int index, String newValue) throws IndexOutOfBoundsException {
    	if (index >= array.length || index < 0) {
            throw new IndexOutOfBoundsException("Supplied index is invalid.");
        }

        array[index] = newValue;
    } // end of set()


    /**
     * Gets/retrieves the value at index.  Indices start at 0.
     *
     * @param index Position in array to retrieve value from.
     * @return value of array at specified index.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public String get(int index) throws IndexOutOfBoundsException {
    	
    	if(array == null) {
    		System.err.println("Index out of bound");
    		return "";
    	} else {
    		if (index >= array.length || index < 0) {
                throw new IndexOutOfBoundsException("Supplied index is invalid.");
            }
    		return array[index];
    	}
    	

        
    } // end of get()


    /**
     * Add value to end of array.
     *
     * @param newValue Value to add to array.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public void add(String newValue) {  	
    	
    	
    	//If array is null
    	if (array == null) {
    		array = new String[1];
    		array[0] = newValue;
    	}
    	//if Array has been allocated memory
    	else {
    		String [] newArray = new String[array.length +1];
    		for(int i = 0 ; i < array.length; i++) {
    			newArray[i] = array[i];
    		}
    		newArray[newArray.length -1] = newValue;    		
    		
    		array = new String[newArray.length];
    		for(int i = 0 ; i < newArray.length; i++) {
    			array[i] = newArray[i];
    		}
    	}
    } // end of add()


    /**
     * Insert value at position 'index'.  Indices start at 0.
     *
     * @param index Position in array to add new value to.
     * @param newValue Value to add to array.
     *
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public void insert(int index, String newValue) throws IndexOutOfBoundsException {
        
        if(array == null) {
        	System.out.println("array is null now");
        	array = new String[1];
        }
        else {
        	System.out.println(index + " " + array.length);        	
        	
        	if (index >= array.length || index < 0) {
                throw new IndexOutOfBoundsException("Supplied index is invalid.");
            }        	
        	
            String[] newArray = new String[array.length + 1];
            
            for(int i = 0 ; i <= index; i++) {
            	newArray[i] = array[i];
            	if(i == index) {
            		newArray[i] = newValue;
            	}
            }
            
            for(int i = index; i < array.length; i++) {
            	newArray[i+1] = array[i];
            }
            
            array = new String[newArray.length];
    		for(int i = 0 ; i < newArray.length; i++) {
    			array[i] = newArray[i];
    		}
        }
      

        
        
    } // end of insert()


    /**
     * Searches for the index that contains value.  If value is not present,
     * method returns -1 (OUT_OF_INDEX).
     * If there are multiple values that could be returned, return the one with
     * the smallest index.
     *
     * @param value Value to search for.
     * @return Index where value is located, otherwise returns -1 (OUT_OF_INDEX).
     */
    public int search(String value) {

        // IMPLEMENT ME!
    	if(array == null) {    		
    		return OUT_OF_INDEX;
    	}
    	
    	int index = -1;
    	for(int i  = 0 ; i < array.length; i++) {
    		if(array[i].equals(value)) {
    			index = i;
    		}
    	}
    	
    	if(index < 0) {
    		return OUT_OF_INDEX;
    	}
    	else{
    		return index;
    	}
    	
        // This will return -1.  Return this when we can't find the input value.
        
    } // end of search()


	@Override
	public int size() {
		// TODO Auto-generated method stub
		if(array == null) {
			return 0;
		}
		return array.length;
	}


	@Override
	public void delete(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if(index >= array.length) {
			throw new IndexOutOfBoundsException();
		}
		PrintWriter os = new PrintWriter(System.out, true);
		
		String[] newArray = new String[array.length - 1];
		int arrIndex = 0;
		for(int i = 0 ; i < newArray.length; i++ ) {
			if(index == i) {
				arrIndex++;
			}
			newArray[i] = array[arrIndex];
			arrIndex++;
		}
		
		
		array = new String[newArray.length];
		for(int i = 0 ; i < newArray.length; i++) {
			array[i] = newArray[i];
		}
	}


	@Override
	public String toString() {
		if(array == null) {
			return "[]";
		}
		return  Arrays.toString(array);
	}


	


  

} // end of class DynamicArrayMinimal
