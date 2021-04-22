


/**
 * Singly linked list.
 *
 * @author Jeffrey Chan, RMIT
 */
public class SimpleLinkedList implements MyList
{
    /** Reference to head node. */
    protected Node mHead;

    /** Length of list. */
    protected int mLength;


    /**
     * Default constructor.
     */
    public SimpleLinkedList() {
        mHead = null;
        mLength = 0;
    } // end of SimpleLinkedList()


    /**
     * Add a new value to the start of the list.
     *
     * @param newValue Value to add to list.
     */
    public void add(String newValue) {
        Node newNode = new Node(newValue);

        // If head is empty, then list is empty and head reference need to be initialised.
        if (mHead == null) {
            mHead = newNode;
        }
        // otherwise, add node to the head of list.
        else {
            newNode.setNext(mHead);
            mHead = newNode;
        }

        mLength++;
    } // end of add()


    /**
     * Insert value (and corresponding node) at position 'index'.  Indices start at 0.
     *
     * @param index Position in list to add new value to.
     * @param newValue Value to add to list.
     *
     * @throws IndexOutOfBoundsException Index are out of bounds.
     */
    public void insert(int index, String newValue) throws IndexOutOfBoundsException {
        if (index >= mLength || index < 0) {
            throw new IndexOutOfBoundsException("Supplied index is invalid.");
        }

        Node newNode = new Node(newValue);

        if (mHead == null) {
            mHead = newNode;
        }
        // list is not empty
        else {
            // if index = 0, we should replace mHead with newNode
            if (index == 0) {
                newNode.setNext(mHead);
                mHead = newNode;
            }
            else {
                Node currNode = mHead;
                for (int i = 0; i < index-1; ++i) {
                    currNode = currNode.getNext();
                }

                newNode.setNext(currNode.getNext());
                currNode.setNext(newNode);
            }
        }

        mLength += 1;
    } // end of insert()


    /**
     * Returns the value stored in node at position 'index' of list.
     *
     * @param index Position in list to get new value for.
     * @return Value of element at specified position in list.
     *
     * @throws IndexOutOfBoundsException Index is out of bounds.
     */
    public String get(int index) throws IndexOutOfBoundsException {
        if (index >= mLength || index < 0) {
            throw new IndexOutOfBoundsException("Supplied index is invalid.");
        }
        
        Node currNode = mHead;
        
        if(index < 1) {
        	return currNode.getValue();
        }
        for (int i = 0; i < index; ++i) {
            currNode = currNode.getNext();
        }

        return currNode.getValue();
    } // end of get()
    
  


    /**
     * Searches for the index that contains value.  If value is not present,
     * method returns -1 (NOT_IN_ARRAY).
     * If there are multiple values that could be returned, return the one with
     * the smallest index.
     *
     * @param value Value to search for.
     * @return Index where value is located, otherwise returns -1 (NOT_IN_ARRAY).
     */
    public int search(String value) {
        Node currNode = mHead;
        for (int i = 0; i < mLength; ++i) {
            if (currNode.getValue().equals(value)) {
                return i;
            }
            currNode = currNode.getNext();
        }

        return NOT_IN_LIST;
    } // end of search()



    /**
     * Delete given value from list (delete first instance found).
     *
     * @param value Value to remove.
     * @return True if deletion was successful, otherwise false.
     */
    public boolean remove(String value) {
        // IMPLEMENT ME!
    	Node prev = null;
    	Node curNode = mHead;
    	
    	if(curNode != null && curNode.getValue().equals(value)) {
    		mHead = mHead.getNext();
    		mLength--;
    		return true;
    	}
    	
    	while(curNode != null && !curNode.getValue().equals(value)) {
    		prev = curNode;
    		curNode = curNode.getNext();    		
    		
    	}
    	
    	if(curNode == null) {
    		return false;
    	}
    	prev.setNext(curNode.getNext());
    	mLength--;
        return true;
    } // end of remove()

    @Override
	public int size() {
		// TODO Auto-generated method stub
		return mLength;
	}
    /**
     * Delete value (and corresponding node) at position 'index'.  Indices start at 0.
     *
     * @param index Position in list to get new value for.
     * @param dummy Dummy variable, serves no use apart from distinguishing overloaded methods.
     * @return Value of node that was deleted.
     *
     * @throws IndexOutOfBoundsException Index is out of bounds.
     */
    public int removeByIndex(int index) throws IndexOutOfBoundsException{
        if (index >= mLength || index < 0) {
            throw new IndexOutOfBoundsException("Supplied index is invalid.");
        }

        // IMPLEMENT ME!
        if(mHead == null) {
        	System.out.println("head currently is null");
        	return -1;
        }        
        
        Node curNode = mHead;
        Node prev = null;
        if(index ==0) {
        	if(mHead.getNext() != null) {
        		mHead = mHead.getNext();
        		mLength --;
            	return 1;
        	} else {
        		mHead = null;
        		mLength--;
        		return 1;        		
        	}
        	
        }
        for(int i = 0 ; i < index; i++ ) {
        	prev = curNode;
        	curNode = curNode.getNext();
        	
        }
        if(curNode.getNext() == null) {        	
        	prev.setNext(null);
        	return 1;
        }
        prev.setNext(curNode.getNext());
        
        return 1;
    } // end of remove()





    /**
     * Print the list in head to tail.
     */
    public void print() {
        System.out.println(toString());
    } // end of print()



    /**
     * Print the list from tail to head.
     */
    
    public String toString() {
        Node currNode = mHead;

        StringBuffer str = new StringBuffer();

        while (currNode != null) {
            str.append(currNode.getValue() + " ");
            currNode = currNode.getNext();
        }

        return str.toString();
    } // end of toString();



    /**
     * Node type, inner private class.
     */
    private class Node
    {
        /** Stored value of node. */
        protected String mValue;
        
        


		/** Reference to next node. */
        protected Node mNext;

        public Node(String value) {
            mValue = value;
            mNext = null;
        }

        public String getValue() {
            return mValue;
        }


        public Node getNext() {
            return mNext;
        }


        public void setValue(String value) {
            mValue = value;
        }


        public void setNext(Node next) {
            mNext = next;
        }
        
        
    } // end of inner class Node



	

} // end of class SimpleLinkedList
