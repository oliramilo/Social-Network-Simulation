import java.util.*;
//Testing the remove(Object) function in the linked list
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
        Iterator itr = list.iterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
        System.out.println("Removing Yo, what, Hello");
        list.remove("Yo");
        list.remove("what");
        list.remove("Hello");
        Iterator it = list.iterator();
        System.out.println("Result:");
        while(it.hasNext())
        {
            System.out.println(it.next());
        }
    }
}
