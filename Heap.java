/*FILE: Heap.java
* AUTHOR: Olimar Ramilo
* UNIT: COMP1002
* PURPOSE: Maximum heap data structure to store
*          any datatype as an object based on their
*          priority key values represented by an integer
* REFERENCE: trickleDown algorithm based off Robert Lafore
*            Data Structures and Algorithms 2nd Edition */
/*Heap data structure implemented during the practicals,
* some method sand their functions have been changed to meet the
* specifications of the assignment*/
public class Heap 
{
    /*Private inner class to hold the object and priority values */
    private class heapItem
    {
        private int priority;
        private Object item;
        public heapItem(int priority,Object item)
        {
            this.priority = priority;
            this.item = item;
        }

        Object get()
        {
            return this.item;
        }

        int getKey()
        {
            return this.priority;
        }

        void setKey(int key)
        {
            this.priority = key;
        }
    }

    /*Default size for a heap array*/
    public static final int DEFAULT_SIZE = 8191;
    private heapItem[] heapArr;
    private int size;
    public Heap()
    {
        heapArr = new heapItem[DEFAULT_SIZE];
        size = 0;
    }

    /*Adds items to the Heap array*/
    void add(int priority, Object item)
    {
        heapItem entry = new heapItem(priority, item);
        if(isEmpty())
        {
            heapArr[0] = entry;
            size++;
        }

        else
        {
            heapArr[size] = entry;
            /*Trickle up must be called to arrange the array so
            * that the the array is ordered from highest priority
            * to lowest*/
            trickleUp();
            size++;

        }
    }

    /*Returns the Object value stored in the heapItem object*/
    Object remove()
    {
        return removeItem().get();
    }

    /*Returns a heap object from the 0th index of the
    * heap array*/
    heapItem removeItem()
    {
        heapItem ret = null;
        if(isEmpty())
        {
            throw new IllegalArgumentException("Empty heap");
        }
        
        else if(size == 1)
        {
            ret = heapArr[0];
            heapArr[0] = null;
            size--;
        }

        else
        {
            ret = heapArr[0];
            size--;
            heapArr[0] = heapArr[size];
            trickleDown(heapArr,0,size);
        }
        return ret;
    }

    /*Method used to change the priority of the items in the heap
      An alternative approach would be having an iterator to execute
      the same operation, implemented during the assignment as it may
      be needed*/
    void change(int newKey, Object val)
    {
        int i =0;
        int originalKey = 0;
        boolean found = false;
        while(i<size && !found)
        {
            if(heapArr[i].get().equals(val))
            {
                found = true;
                originalKey = heapArr[i].getKey();
                heapArr[i].setKey(newKey);
                if(originalKey < newKey)
                {
                    trickleUpRec(i);
                }
                else
                {
                    trickleDown(heapArr, i, size);
                }
            }
            i++;
        }

        if(!found)
        {
            throw new IllegalArgumentException("Item not found");
        }
    }
    
    boolean isEmpty()
    {
        return size == 0;
    }

    int getLeft(int pos)
    {
        return (pos*2)+1;
    }

    int getRight(int pos)
    {
        return (pos*2)+1;
    }

    int getParent(int pos)
    {
        return (pos-1)/2;
    }

    void trickleUp()
    {
        if(size >= 0)
        {
            trickleUpRec(size);
        }

        else
        {
            throw new IllegalArgumentException("Empty heap");
        }
    }

    /**/
    private void trickleUpRec(int pos)
    {
        if(pos >= 0)
        {
            int parent = getParent(pos);
            if(heapArr[pos].getKey() > heapArr[parent].getKey())
            {
                heapItem temp = heapArr[parent];
                heapArr[parent] = heapArr[pos];
                heapArr[pos] = temp;
                trickleUpRec(parent);
            }
        }
        else
        {
            throw new IllegalArgumentException("Empty heap");
        }
    }

    void trickleDown(heapItem heapArr[],int pos, int size)
    {
        if(size > 0)
        {
            boolean done = true;
            trickleDownRec(heapArr,pos,size,done);
        }
    }

    /*This algorithm is based off Robert Lafore's Data Structures
    * Aand Algorithms in Java 2nd Edition. The code from the book
    * shows the trickle down algorithm iteratively, during the practicals
    * I implemented a recursively function for it*/
    private void trickleDownRec(heapItem heapArr[],int pos, int size, boolean done)
    {
        int largest = 0;
        heapItem temp = heapArr[pos];
        if(pos < size/2 && done)
        {
            done = false;
            int left = getLeft(pos);
            int right = left+1;
            if(right < size && heapArr[left].getKey() < heapArr[right].getKey())
            {
                largest = right;
            }
            else
            {
                largest = left;
            }
            if(temp.getKey() < heapArr[largest].getKey())
            {
                done = true;
                heapArr[pos] = heapArr[largest];
                pos = largest;
            }
        heapArr[pos] = temp;
        trickleDownRec(heapArr,pos,size,done);
        }
    }
    
    void sortThis()
    {
        heapSort(this.heapArr,this.size);
    }

    /*Heapifies an already heap array...*/
    heapItem[] heapify(heapItem[] arr,int size)
    {
        for(int i=(size/2)-1;i>=1;i--)
        {
            trickleDown(arr,i,size);
        }
        return arr;
    }

    /*Heap sort algorithm of a heap O(n*log(n))
    * which iterates over the heap array and places
    * the current position into the correct spot by the
    * priority value of its children nodes being greater*/
    void heapSort(heapItem arr[], int size)
    {
        heapify(arr,size);
        for(int i=size-1;i>=1;i--)
        {
            heapItem temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            trickleDown(arr,0,i);
        }
    }

    int size()
    {
        return this.size;
    }

    //Dead code, delete later
    void setSize(int size)
    {
        this.size = size;
    }

    void setHeap(heapItem[] h)
    {
        this.heapArr = h;
    }

    heapItem[] getHeap()
    {
        return this.heapArr;
    }

    //Only for sorted heap
    Object[] getItems()
    {
        Object[] arr = null;
        if(isEmpty())
        {
            throw new IllegalArgumentException("Empty heap");
        }
        else
        {
            arr = new Object[size];
            for(int i=0;i<size;i++)
            {
                arr[i] = heapArr[i].get();
            }
        }
        return arr;
    }

    void print()
    {
        Heap temp = this;
        for(int i =0;i<temp.size;i++)
        {
            System.out.println(heapArr[i].get());
        }
    }

    void printPos(int pos)
    {
        System.out.println("Pos: "+pos+" "+heapArr[pos].getKey()+" "+heapArr[pos].get());
    }

}
