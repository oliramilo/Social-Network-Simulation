/*Data structure implemented from the practical workshop */
import java.io.*;
import java.util.*;

class DSALinkedList implements Iterable, Serializable
{
    private static final long serialVersionUID = 1L;
    private Node head;
    private Node tail;
    private int size;
    private class Node implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private Object element;
        private Node prev;
        private Node nextNd;

        Node(Object e)
        {
            element = e;
            nextNd = null;
            prev = null;
        }


        void setNext(Node nodePointer)
        {
            nextNd = nodePointer;
        }

        void setPrev(Node prevNode)
        {
            prev = prevNode;
        }

        Object getElement()
        {
            return this.element;
        }

        Node getNext()
        {
            return this.nextNd;
        }
    
        Node getPrev()
        {
            return this.prev;
        }

    }

    public Iterator iterator()
    {
        return new DSALinkedListIterator(this);
    }

    private class DSALinkedListIterator implements Iterator
    {
        private Node iterNext;
        public DSALinkedListIterator(DSALinkedList list) {
            iterNext = list.head;
        }

        //Check if there is a next item on list
        public boolean hasNext() {
            return iterNext != null;
        }

        //Get current item, iterate to the next item
        public Object next() {
            Object curVal = null;
            if(iterNext != null)
            {
                curVal = iterNext.getElement();
                iterNext = iterNext.getNext();
            }
            else 
            {
                System.out.println("No more items in the list");
            }
            return curVal;
        }
        
        public void remove() 
        {
            throw new UnsupportedOperationException("Operation remove unsupported");
        }
    }

    DSALinkedList()
    {
        head = null;
        size =0;
    }

    void insertFirst(Object e)
    {
        Node newNd = new Node(e);
        if(isEmpty())
        {
            head = newNd;
            tail = head;
        }

        else
        {
            head.setPrev(newNd);
            newNd.setNext(head);
            head = newNd;
        }
        size++;
    }

    void insertLast(Object e)
    {
        Node newNd = new Node(e);
        //When the list is empty, set head & tail the same
        if(isEmpty())
        {
            head = newNd;
            tail = head;
        }

        //When there is one item in the list, insert to tail
        //Connect the next pointer to tail
        else if(head.getNext() == null)
        { 
            tail = newNd;
            tail.setPrev(head);
            head.setNext(tail);
        }
        
        //When there more than two items on the list
        //Insert to tail and set a pointer to the old one
        else
        {
            tail.setNext(newNd);
            newNd.setPrev(tail);
            tail = newNd;
        }
        size++;
    }

    Object removeFirst()
    {
        Object firstElem = null;
        if(isEmpty())
        {
            throw new IllegalStateException("List is empty");
        }

        //Check if there is 1 item in the list
        //Set both head and tail null
        else if (head.getNext() == null)
        {
            firstElem = head.getElement();
            head = null;
            tail = null;
        }

        //When there is more than one item in the list
        //Remove the first item
        else
        {
            firstElem = head.getElement();
            head = head.getNext();
            head.setPrev(null);
        }
        size--;
        return firstElem;
    }

    Object removeLast()
    {
        Object lastElem = null;

        //Throw exception if empty
        if(isEmpty())
        {
            throw new IllegalStateException("List is empty");
        }

        //For when there is 1 item in the list
        else if(head.getNext() == null)
        {
            lastElem = tail.getElement();
            tail = null;
            head = null;
        }

        //Remove the last element and set the new tail to
        //the previous one
        else
        {
            lastElem = tail.getElement();
            tail = tail.getPrev();
            tail.setNext(null);
        }
        size--;
        return lastElem;
    }

    Object peekFirst()
    {
        Object first = null;
        if(isEmpty())
        {
            throw new IllegalStateException();
        }
        else
        {
            first = head.getElement();
        }
        return first;
    }

    Object peekLast()
    {
        Object last = null;
        if(isEmpty())
        {
            throw new IllegalStateException("List is empty");
        }
        else
        {
            last = tail.getElement();
        }
        return last;
    }

    boolean isEmpty()
    {
        return head == null;
    }

    public void save(DSALinkedList list,String file)
    {
        saveFile(list, file);
    }

    public void saveFile(DSALinkedList saveObj, String fileName)
    {
        FileOutputStream strm;
        ObjectOutputStream objStrm;
        try
        {
            strm = new FileOutputStream(fileName);
            objStrm = new ObjectOutputStream(strm);
            //Save object by writing it to file
            objStrm.writeObject(saveObj);

            objStrm.close();
            strm.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public DSALinkedList load(String file)
    {
        return loadFile(file);
    }

    /*Added the remove operation, iterates over the linkedlist to find the Node
      to be removed. Method, implemented for the assignment. This remove 
      method iterates over the linkedlist to find the Node with the matching
      object value of the parameter*/
    public void remove(Object e)
    {
        Node iter = head;
        if(!isEmpty())
        {
            boolean found = true;
            //Checkif we are removing the head
            if(iter.getElement().equals(e))
            {
                head = head.getNext();
                //head.setPrev(null);
                found = false;
                size--;
            }
            else
            {
                /*Iterate while null or found the Node position*/
                while(found && iter != null)
                {
                    //Check if we are removing the tail
                    if(iter.getElement().equals(tail.getElement()))
                    {
                        tail = tail.getPrev();
                        tail.setNext(null);
                        found = false;
                        size--;
                    }

                    else if(iter.getElement().equals(e))
                    {
                        size--;
                        found = false;
                        /*Sitches the previous and next pointer to point to 
                          each other, the current Node will not have a node pointing
                          to it*/
                        iter.getNext().setPrev(iter.getPrev());
                        iter.getPrev().setNext(iter.getNext());
                    }
                    iter = iter.getNext();
                }
            }
            /*Display message, that the remove operation was not found */
            if(found)
            {
                System.out.println("Node not found");
            }
        }
    }
    private DSALinkedList loadFile(String fileName)
    {
        FileInputStream strm;
        ObjectInputStream objStrm;
        DSALinkedList inObj = null;

        try 
        {   
            strm = new FileInputStream(fileName);
            objStrm = new ObjectInputStream(strm);
            //Read object and cast to a LinkedList
            inObj = (DSALinkedList)objStrm.readObject();
            objStrm.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return inObj;
    }

    public int size()
    {
        return this.size;
    }
}
