
/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    private Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }
    
    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }

    /** Returns the CharData of the first element in this list. */
    public CharData getFirst() {
        // Your code goes here
        return first.cp;
    }

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        CharData myChr = new CharData(chr);
        Node node = new Node(myChr, this.first);
        this.size++;
        this.first = node;

    }
    
    /** GIVE Textual representation of this list. */
    public String toString() {
        Node copyNode = this.first;

        String str = "(";

        for (int i = 0; i < this.getSize()-1; i++) {
            str += copyNode.toString();
            str +=" ";
            copyNode = copyNode.next;
            
            
        }
        //last node
        str += copyNode;
        str += ")";
       
        return str;

    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr) {
        int index = 0;

        if (this.getSize() == 0) return -1;
        if (this.getFirst().equals(chr)) return 0;



        
        Node copyNode = this.first;
        while(copyNode != null){
            if (copyNode.cp.equals(chr)){
                return index;
            }
            else{
                index++;
                copyNode = copyNode.next;
            }
        }

        return -1;
    }


        public int instanceCount(){
            int index = 0;
            int counter = 0;
            while (index != this.getSize()){
                counter += this.get(index).count;

                index++;
                this.listIterator(index);

            }

            return counter;
        }
        
    

    

    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr) {
        
        if (this.getSize() == 0 || indexOf(chr) == -1){
            this.addFirst(chr);
        }
        else{
            Node copyNode = this.first;

            while(copyNode != null){
                if (copyNode.cp.equals(chr)){
                    copyNode.cp.count++;
                    break;
                }
                else{
                    copyNode = copyNode.next;  
                }
            }

        }

    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr) {
        Node copyNode = this.first;
            if (this.first == null) return false;

            else if (this.size == 1 && this.first.cp.equals(chr)){
                this.first = null;
                return true;
            }
            else if (this.indexOf(chr) != -1){
                for (int i = 0; i < this.indexOf(chr)-1; i++) {
                    copyNode = copyNode.next;
                }
                copyNode.next = copyNode.next.next;
                this.size--;  
                return true;
            }

        return false;
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {
        Node copyNode = this.first;
        if (index > this.getSize() || index < 0){
            throw new IndexOutOfBoundsException();
        }
        else {
            for (int i = 0; i < index; i++) {
                copyNode = copyNode.next;
                
            }
            return copyNode.cp;

        }
        
    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
	    // If the list is empty, there is nothing to iterate   
	    if (size == 0) return null;
	    // Gets the element in position index of this list
	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(current);
        
    }


}