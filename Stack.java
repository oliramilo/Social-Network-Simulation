/*Stack data structure implementation from the practicals ,nothing has been modified*/
import java.io.*;
import java.util.*;
public class Stack extends DSAStack implements Iterable, Serializable
{
    private static final long serialVersionUID = 1L;

    public Iterator iterator()
    {
        return stackList.iterator();
    }

    public Stack()
    {
        super();
    }

    @Override
    public void push(Object obj)
    {
        stackList.insertLast(obj);
    }
    
    @Override
    public Object pop()                                                         
    {
        Object top = null;
        if(isEmpty())
        {
            throw new StackEmptyException("Stack Empty");
        }
        else
        {
            top = stackList.removeLast();
        }
        return top;
    }

    @Override
    public Object peek()                                                        
    {
        Object top = null;
        if(isEmpty())
        {
            throw new StackEmptyException("Stack empty");
        }
        else
        {
            top = stackList.peekLast();
        }
        return top;
    }
}
