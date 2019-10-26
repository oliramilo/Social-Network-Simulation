/*Data structure implemented in the practicals */
import java.io.*;
class HashTable implements Serializable
{
    private static final long serialVersionUID = 1L;
    /*Private inner class hashItem that will store a String key,
     * state, and value of the object*/
    private class hashItem implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private String key;
        private Object item;
        private char state;

        public hashItem(String key,Object item)
        {
            this.key = key;
            this.item = item;
            this.state = 't';
        }

        public Object getItem()
        {
            return this.item;
        }

        public String getKey()
        {
            return this.key;
        }

        public void setState()
        {
            this.state = 'f';
        }

        public char getState()
        {
            return this.state;
        }

        public String toString()
        {
            return (this.key + ","+this.item);
        }
    }

    //Intitial fixed size for a HashTable, 503 is a prime number
    public static final int DEFAULT_SIZE = 503;

    private int count;
    public int primeSize;
    public hashItem[] hashArray;

    public HashTable()
    {
        hashArray = new hashItem[DEFAULT_SIZE];
        primeSize = DEFAULT_SIZE;
        count = 0;
    }

    //Function for adding items into the hash table array
    public void add(String key, Object value)
    {
        hashItem item = new hashItem(key,value);
        int hashKey = hashKey(key,hashArray.length);
        int stepSize = stepSize(hashKey);

        //Empty spot for insertion
        if(hashArray[hashKey] == null || hashArray[hashKey].getState() == 'f')
        {
            hashArray[hashKey] = item;
        }

        //Space is occupied, step size required
        else
        {
            //Search for a spot in the array to fill in
            while(hashArray[hashKey] != null && hashArray[hashKey].getState() == 't')
            {
                hashKey += stepSize;
                hashKey %= hashArray.length;
            }
            hashArray[hashKey] = item;
        }

        /*Keep a tally of how many items were added into the hash table
          The load factor in the hash table is checked before increasing 
          its size, the condition requires a >=60% full array*/
        count++;
        reSize();
    }


    public void remove(String key)
    {
    
        int hashKey = hashKey(key,hashArray.length);
        int stepSize = stepSize(hashKey);
        /*Key found in initial hash index, checks for spaces in the 
          array if it isn't null and contains marked items deleted or 
          live objects*/
        if(hashArray[hashKey] != null && hashArray[hashKey].getKey().equals(key) 
                                            && hashArray[hashKey].getState() == 't')
        {
            hashArray[hashKey].setState();
            count--;
        }

        //Space is occupied, step size required
        else
        {
            boolean found = false;
            //Loop until null we land in a null pointer or the item is found
            while(hashArray[hashKey] != null && !found)
            {
                /*The state of the item needs to be checked
                  If its been deleted or not, key also needs to be compared*/
                if(hashArray[hashKey].getState() == 't' && 
                            hashArray[hashKey].getKey().equals(key))
                {
                    hashArray[hashKey].setState();
                    found = true;
                    count--;
                }
                //Jump and check to the next item by the step size
                hashKey += stepSize;
                hashKey %= hashArray.length;
            }

            if(found == false)
            {
                throw new IllegalArgumentException("Item not found.");
            }
        }
        //Checks the load factor of after every removal
        shrink();
    }

    //Returns the object of the item that needs to be found
    public Object get(String key)
    {
        hashItem obj = find(key);
        Object item  = null;
        //function called find returns null if the item doesnt exist
        if(obj != null && obj.getState() == 't')
        {
            item = obj.getItem();
        }
        return item;
    }

    /*Method returns a boolean T/F if the String parameter hashes into a cell
     * space of a non-null/active object value*/
    public boolean hasItem(String item)
    {
        hashItem itemSpace = find(item);
        return  itemSpace != null && itemSpace.getState() == 't';
    }

    //function thats finds 
    private hashItem find(String key)
    {
        boolean found = false;
        int hashVal = hashKey(key,hashArray.length);
        int stepSize = stepSize(hashVal);
        hashItem item= null;
        //Loop until a null value is reached or the state of the 
        //containing value is false
        //Stop looping if item is found
        //Returns null if item is not found
        while(hashArray[hashVal] != null && hashArray[hashVal].getState() != 'f' 
                                                        && found == false)
        {
            if(hashArray[hashVal].getKey().equals(key))
            {
                //System.out.println("Item exists in " + hashVal);
                item = hashArray[hashVal];
                found = true;
            }
            hashVal += stepSize;
            hashVal %= hashArray.length;
        }
        return item;
    }

    //Step size required to jump index an the hashkey index is occupied
    private int stepSize(int hashKey)
    {
        return 5 - (hashKey % 5);
    }

    //Turn string characters to the hash key for insertion
    private int hashKey(String key,int size)
    {
        int hashKey = 0;
        for(int i = 0; i < key.length();i++)
        {
            hashKey += (int)key.charAt(i);
        }
        return (hashKey*5) % size;
    }

    /*When >60% of the array is filled with heap objects
      resizing is required by twice the amount*/
    private void reSize()
    {
        //Check if array is >60% full
        if(loadFactor())
        {
            int newHashIdx =0;
            int stepSize =0;
            //sets the next prime number
            setSize(true);
            hashItem[] newHashTable = new hashItem[primeSize];
            //Transfer items from the old array into the new one
            for(int i=0;i<hashArray.length;i++)
            {
                if(hashArray[i] != null && hashArray[i].getState() == 't')
                {
                    //Rehashing is required for each item in the old array and 
                    //placed into the new one
                    newHashIdx = hashKey(hashArray[i].getKey(),newHashTable.length);
                    stepSize = stepSize(newHashIdx);
                    while(newHashTable[newHashIdx] != null && newHashTable[newHashIdx].getState() == 't')
                    {
                        newHashIdx +=stepSize;
                        newHashIdx %=newHashTable.length;
                    }
                    newHashTable[newHashIdx] = hashArray[i];   
                }    
            }
            System.out.println("Hash table resized to: " + newHashTable.length);
            this.hashArray = newHashTable;
        }
    }

    boolean loadFactor()
    {
        //System.out.println(((double)count/(double)hashArray.length));
        return (((double)count/(double)hashArray.length) > 0.60);
    }

    //Sets the prime number to the next prime/
    //that is twice the size of the original
    private void setSize(boolean b)
    {
        if(b == true)
        {
            nextPrime();
        }
        //Only decrease to the previous prime number only if
        //the current prime number is greater than the default
        //size 
        else if(primeSize > DEFAULT_SIZE)
        {
            prevPrime();
        }
    }

    //Find a prime number twice the size 
    //of current prime number
    private void nextPrime()
    {
        int n = (primeSize*2);
        if(n % 2 == 0)
        {
            n++;
        }

        while(!isPrime(n))
        {
            n+=2;
        }
        this.primeSize = n;
    }

    //Sets primeSize to the previous prime half its size
    private void prevPrime()
    {
        int n = (primeSize/2);
        while(!isPrime(n))
        {
            n-=1;
        }
        System.out.println("Prime previous is " + n);
        this.primeSize = n;
    }

    //Checks integers if they're prime
    public boolean isPrime(int n)
    {
        int i = 2;
        boolean isPrime = true;
        while(i*i <= n && isPrime)
        {
            if(n % i == 0)
            {
                isPrime = false;
            }
            i++;
        }
        return isPrime;
    }

    /*Shrink method for the Hashtable, when only 30% of the space is being
      occupied after removing items, the table shrinks but not less than the
      original size  */
    private void shrink()
    {
        int newHashIdx = 0;
        int stepSize = 0;
        //Shrink only if not default size and less than 30% is used
        if(hashArray.length > DEFAULT_SIZE && (((double)count/(double)hashArray.length) < 0.3))
        {

            setSize(false);
            System.out.println("Load factor " +(double)count/(double)hashArray.length);
            System.out.println("Array shrinking down to " + primeSize);
            hashItem[] newArray = new hashItem[this.primeSize];
            for(int i=0;i<hashArray.length;i++)
            {
                if(hashArray[i] != null && hashArray[i].getState() != 'f')
                {
                    /*Require to re hash each item in the old table
                      and place it in the new one of a different size*/
                    newHashIdx = hashKey(hashArray[i].getKey(), newArray.length);
                    stepSize = stepSize(newHashIdx);
                    while(newArray[newHashIdx] != null && newArray[newHashIdx].getState() != 'f')
                    {
                        newHashIdx += stepSize;
                        newHashIdx %= newArray.length;
                    } 
                    newArray[newHashIdx] = hashArray[i];
                }
            }
            this.hashArray = newArray;
        }
    }

    public int getSize()
    {
        return hashArray.length;
    }

    public int getCount()
    {
        return this.count;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    //returns a string array of all the hash entry toString
    public String[] csvString()
    {
        int i=0;
        int items = 0;
        String[] s = new String[count];
        while(i<hashArray.length-1)
        {
            if(hashArray[i] != null && hashArray[i].getState() != 'f')
            {

                s[items] = hashArray[i].toString();
                items++;
            }
            i++;
        }
        return s;
    }

    //Saves the hash table to a container class which can be serialized
    public void serializeTable(String file,ContainerClass c)
    {
        FileOutputStream strm;
        ObjectOutputStream objOut;
        try
        {
            strm = new FileOutputStream(file);
            objOut = new ObjectOutputStream(strm);
            c.save(this);
            objOut.writeObject(c);

            objOut.close();
            strm.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //loads a container class object that contains the
    //hash table contents
    public ContainerClass loadContainer(String file)
    {
        FileInputStream strm;
        ObjectInputStream objIn;
        ContainerClass c = null;
        try
        {
            strm = new FileInputStream(file);
            objIn = new ObjectInputStream(strm);
            c = (ContainerClass)objIn.readObject();

            strm.close();
            objIn.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return c;
    }

    public void check()
    {
        for(int i=0;i<hashArray.length;i++)
        {
            if(hashArray[i] != null && hashArray[i].getState() != 'f')
            {
                System.out.println("Item exists in " + i);
            }
        }
    }

    public Object check(int i)
    {
        return hashArray[i].getItem();
    }

    /*Return a set of object values from the hash table*/
    public DSALinkedList Set()
    {
        DSALinkedList set = null;
        if(!isEmpty())
        {
            set = new DSALinkedList();
            for(int i=0;i<hashArray.length;i++)
            {
                if(hashArray[i] != null && hashArray[i].getState() == 't')
                {
                    set.insertLast(hashArray[i].getItem());
                }
            }
        }
        return set;
    }
}


