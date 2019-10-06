import java.io.*;
import java.util.Iterator;
//Class to store 
public class ContainerClass implements Serializable, Iterable
{
    private static final long serialVersionUID = 1L;
    private DSAQueue container;

    public Iterator iterator()
    {
        return container.iterator();
    }

    public ContainerClass()
    {
        container = new Queue();
    }

    public void save(Object obj)
    {
        container.enQueue(obj);
    }

    public Object obtain()
    {
        return container.deQueue();
    }

    public int containerSize()
    {
        return container.size();
    }

    public Object check()
    {
        return container.peek();
    }

}
