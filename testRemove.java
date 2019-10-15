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
        list.remove("Yo");
        list.remove("what");
        list.remove("Hello");
        Iterator it = list.iterator();
        while(it.hasNext())
        {
            System.out.println(it.next());
        }
    }
}
