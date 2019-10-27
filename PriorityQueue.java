/*Priority Queue class implemented from the DSA prac tutorial for max heap*/
class PriorityQueue
{
    private Heap heap;
    public PriorityQueue()
    {
        heap = new Heap();
    }

    void add(int priority, Object val)
    {
        heap.add(priority, val);
    }

    Object remove()
    {
        return heap.remove();
    }

    Object[] get()
    {
        return heap.getItems();
    }

    boolean isEmpty()
    {
        return heap.isEmpty();
    }

    int size()
    {
        return heap.size();
    }

    void sort()
    {
        heap.sortThis();
    }
}
