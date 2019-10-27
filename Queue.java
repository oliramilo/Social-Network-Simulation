/*Queue Class implemented from the DSA prac tutorials*/
import java.util.*;
import java.io.*;
public class Queue extends DSAQueue implements Iterable, Serializable
{
    private static final long serialVersionUID = 1L;

    public Iterator iterator()
    {
        return queueList.iterator();
    }

    public Queue()
    {
        super();
    }

    @Override
    public void enQueue(Object obj)
    {
        queueList.insertLast(obj);
    }

    @Override
    public Object deQueue()
    {
        Object firstElem = null;
        if(isEmpty())
        {
            throw new QueueEmptyException("Queue is Empty");
        }

        else
        {
            firstElem = queueList.removeFirst();
        }
        return firstElem;
    }

    @Override
    public Object peek()
    {
        Object top = null;;
        if(isEmpty())
        {
            throw new QueueEmptyException("Queue is Empty");
        }
        else
        {
            top = queueList.peekFirst();
        }
        return top;
    }
}
