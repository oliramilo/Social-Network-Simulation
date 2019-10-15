import java.util.*;
class testRemove
{
    public static void main(String [] args)
    {
        DSALinkedList list = new DSALinkedList();
        String[] arr = {"Yo", "Hello", "what", "The word", "Easy"};
        for(int i = 0;i<5;i++)
        {
            list.insertLast(arr[i]);
        }
        Iterator it = list.iterator();
        while(it.hasNext())
        {
            if(it.next().equals("what"))
            {
                it.remove();
            }
        }
        Iterator it2 = list.iterator();
        while(it2.hasNext())
        {
            System.out.println(it2.next());
        }
    }
}
