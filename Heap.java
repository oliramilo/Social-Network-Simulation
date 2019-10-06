
public class Heap 
{
    
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

    //Default size for a heap array
    public static final int DEFAULT_SIZE = 8191;
    private heapItem[] heapArr;
    private int size;
    public Heap()
    {
        heapArr = new heapItem[DEFAULT_SIZE];
        size = 0;
    }

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
            trickleUp();
            size++;

        }
    }

    Object remove()
    {
        return removeItem().get();
    }

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

    //Method used to change the priority of the items in the heap
    //An alternative approach would be having an iterator to execute
    //the same operation
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

    heapItem[] heapify(heapItem[] arr,int size)
    {
        for(int i=(size/2)-1;i>=1;i--)
        {
            trickleDown(arr,i,size);
        }
        return arr;
    }

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

    void testSorted()
    {
        heapItem check = null;
        boolean sorted = true;
        sortThis();
        for(int i =0;i<size-1;i++)
        {
            check = heapArr[i];
            System.out.println(check.getKey());
            if(check.getKey() > heapArr[i+1].getKey())
            {
                sorted = false;
            }
        }
        System.out.println(heapArr[size-1].getKey());
        System.out.println("Heap is sorted: " + sorted);
        if(sorted == false)
        {
            throw new IllegalArgumentException("Array is unsorted");
        }
    }
}
