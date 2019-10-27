/*This code is not used in the Assignment, but the implementation of search algorithms 
 * were based off the breadth first search code*/
import java.util.Iterator;
import java.io.*;
class Graph implements Serializable
{
    //Vertex class section
    private class Vertex implements Serializable
    { 
        private String label;
        private Object value;
        private DSALinkedList adjacentNodes;
        private boolean visited;
        
        public Vertex(String label, Object value)
        {
           this.label = label;
           this.value = value;
           adjacentNodes = new DSALinkedList();
           visited = false;
        }

        public String getLabel()
        {
            return this.label;
        }

        public Object getValue()
        {
            return this.value;
        }

        public void setVisited(boolean visit)
        {
            this.visited = visit;
        }

        public boolean isVisited()
        {
            return this.visited;
        }

        void link(Vertex v)
        {
            adjacentNodes.insertLast(v);
        }

        public void updateLinks()
        {
            this.adjacentNodes = sort(this.adjacentNodes);
        }

        public DSALinkedList getLinks()
        {
            return adjacentNodes;
        }
    }

    //Edge class section
    private class Edge implements Serializable
    {
        private Vertex from;
        private Vertex to;
        private double weight;
        
        public Edge(Vertex start, Vertex end, double weight)
        {
            this.from = start;
            this.to = end;
            this.weight = weight;
        }

        public double getWeight()
        {
            return this.weight;
        }

        public void setWeight(double newWeight)
        {
            this.weight = newWeight;
        }

        public Vertex getFrom()
        {
            return this.from;
        }

        public Vertex getTo()
        {
            return this.to;
        }

        public String toString()
        {
            return (from.getLabel() + " " + to.getLabel() + " " + weight);
        }
    }

    //Graph code Section

    private DSALinkedList vertices;
    private DSALinkedList vertexEdges;

    public Graph()
    {
        vertices = new DSALinkedList();
        vertexEdges = new DSALinkedList();

    }

    public void addVertex(String inLabel, Object value)
    {
        if(vertices.isEmpty())
        {
            Vertex newVertex = new Vertex(inLabel,value);
            vertices.insertLast(newVertex);
        }

        else if(!hasVertex(inLabel))
        {
            Vertex newVertex = new Vertex(inLabel,value);
            vertices.insertLast(newVertex);
            sort(vertices);
        }
    }

    private boolean hasVertex(String inLabel)
    {
        boolean exists = false;
        if(!vertices.isEmpty())
        {
            Iterator it = vertices.iterator();
            Vertex check = (Vertex)vertices.peekFirst();
            for(Object e:vertices)
            {
                check = (Vertex)it.next();
                if(check.getLabel().equals(inLabel))
                {
                    exists = true;
                }
            }
        }
        return exists;
    }

    //Adds edge
    public void addEdge(String label1, String label2, double weight)
    {
        Vertex from = get(label1);
        Vertex to = get(label2);
        if(from != null && to != null)
        {
            from.link(to);
            //to.link(from);
            Edge connect = new Edge(from,to,weight);
            vertexEdges.insertLast(connect);
            from.updateLinks();
            System.out.println("Edge length of " + weight + " connects from " + label1 + " to " + label2);
        }
        else
        {
            throw new IllegalArgumentException("Vertex " + label1 + " and Vertex " + label2 + " do not exist in the graph");
        }  
    }

    //Finds and returns Vertex corresponding to the taken parameter
    //Return null if the vertex doesn't exist
    public Vertex get(String label)
    {
        Vertex v = null;
        if(vertices.isEmpty())
        {
            throw new IllegalArgumentException("Invalid operation for an Empty graph");
        }

        else
        {
            Iterator it = vertices.iterator();
            for(Object e:vertices)
            {
                Vertex find = (Vertex)it.next();
                if(find.getLabel().equals(label))
                {
                    v = find;
                }
            }
        }
        return v;
    }

    //Iterate over the list to set all vertices unvisited
    private void clearVisited()
    {
        if(vertices.isEmpty())
        {
            throw new IllegalArgumentException("Invalid operation for empty graph");
        }
        else
        {
            Vertex v = null;
            Iterator it = vertices.iterator();
            for(Object e:vertices)
            {
                v = (Vertex)it.next();
                v.setVisited(false);
            }
        }
    }

    //Just prints out each vertex in the graph
    public void printList()
    {
        Vertex v = null;
        if(!vertices.isEmpty())
        {
            Iterator it = vertices.iterator();
            for(Object e:vertices)
            {
                v = (Vertex)it.next();
                System.out.println("Vertex: " + v.getLabel() + ", Value:" + v.getValue());
            }
        }
    }

    public BinarySearchTree DepthFirstSearch()
    {
        BinarySearchTree tree = null;
        //Check vertex list is not empty before recursing
        if(!vertices.isEmpty())
        {
            tree = new BinarySearchTree();
            Vertex v = (Vertex)vertices.peekFirst();
            DFSRecurse(tree,v);
            clearVisited();
        }
        else
        {
            throw new IllegalArgumentException("Invalid operation for empty graph.");
        }
        return tree;
    }

    //Private method for Depth First traversal using recursion
    private BinarySearchTree DFSRecurse(BinarySearchTree tree, Vertex e)
    {
        e.setVisited(true);
        //Check we don't iterate through null values
        if(!e.getLinks().isEmpty())
        {
            sort(e.getLinks());
            Iterator it = e.getLinks().iterator();
            //Iterate through all unvisited adjacent nodes of the 
            //current visiting vertex
            for(Object o:e.getLinks())
            {
                //Visit the next adjacent vertex
                Vertex v = (Vertex)it.next();
                //Recurse again unless Vertex v is visited
                if(!v.isVisited())
                {
                    v.setVisited(true);
                    System.out.println("Visited: " + v.getLabel());
                    String vertexKey = v.getLabel();
                    //insert Vertex v to the tree
                    tree.insert(vertexKey, v);
                    DFSRecurse(tree,v);
                }
            }
        }
        return tree;
    }

    public BinarySearchTree BreadthFirstSearch()
    {
        BinarySearchTree tree = null;
        //Check the vertex list before recursing
        if(!vertices.isEmpty())
        {
            tree = new BinarySearchTree();
            DSAQueue vertexQ = new Queue();
            vertexQ.enQueue((Vertex)vertices.peekFirst());
            BFSRecurse(tree,vertexQ, (Vertex)vertices.peekFirst());
            clearVisited();
        }
        else
        {
            throw new IllegalArgumentException("Invalid operation for empty graph");
        }
        return tree;
    }

    //Recursive method for Breadth First graph traversal
    private BinarySearchTree BFSRecurse(BinarySearchTree tree,DSAQueue vertexQ, Vertex v)
    {
        v.setVisited(true);
        if(!vertexQ.isEmpty())
        {
            Iterator t = v.getLinks().iterator();
            Vertex w = null;
            //Iterate through all the adjacent nodes of the current vertex
            for(Object e:v.getLinks())
            {
                w = (Vertex)t.next();
                //Set the current unvisited node to visited
                //and add to the queue
                if(!w.isVisited())
                {
                    System.out.println("Visited: " + w.getLabel());
                    w.setVisited(true);
                    vertexQ.enQueue(w);
                } 
            }
            //Add dequeue revisited nodes to the tree
            Vertex alreadyVisited = (Vertex)vertexQ.deQueue();
            String vertexKey = alreadyVisited.getLabel();
            tree.insert(vertexKey,alreadyVisited);
            //Need to check if the queue is empty
            //Before recursing again
            if(!vertexQ.isEmpty())
            {
                v = (Vertex)vertexQ.peek();
                BFSRecurse(tree,vertexQ, v);
            }
        }
        return tree;
    }

    //Prints out the adjacent nodes of a specific vertex label
    void printEdges(String label)
    {
        Vertex v = get(label);
        Iterator t = v.getLinks().iterator();
        System.out.println("Edges of: " + v.getLabel());
        for(Object e:v.getLinks())
        {
            v = (Vertex)t.next();
            System.out.println(v.getLabel());
        }
    }

    //Displays the current edges in the graph
    void display()
    {
        if(!vertexEdges.isEmpty())
        {
            Edge e = null;
            Iterator edgeIt = vertexEdges.iterator();
            for(Object o:vertexEdges)
            {        
                e = (Edge)edgeIt.next();        
                System.out.println(e.toString());
            }
        }
        else
        {
            System.out.println("Empty graph");
        }
    }

    //Serialize current graph by adding to a container class
    //Then the container class is serialized
    private void SerializeFile(String file,Graph g,ContainerClass container)
    {
        FileOutputStream out;
        ObjectOutputStream obj;
        try
        {
            container.save(g);
            out = new FileOutputStream(file);
            obj = new ObjectOutputStream(out);
            obj.writeObject(container);

            out.close();
            obj.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveGraph(String file, ContainerClass contain)
    {
        if(!vertices.isEmpty())
        {
            SerializeFile(file,this,contain);
        }
        
        else
        {
            throw new IllegalArgumentException("Cannot save empty graph");
        }
    }

    //Reads file and returns a container class
    //Container class stores all Objects that were serialized into a
    //Linked list that has a behaviour of a queue
    private ContainerClass readObjectFile(String file)
    {
        FileInputStream in;
        ObjectInputStream obj;
        ContainerClass contain = null;
        try
        {
            in = new FileInputStream(file);
            obj = new ObjectInputStream(in);
            contain = (ContainerClass)obj.readObject();

            in.close();
            obj.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return contain;
    }

    //ContainerClass public method for loading
    public ContainerClass loadGraph(String file)
    {
        return readObjectFile(file);
    }

    //Sorts the order of the vertex lists
    //By iterating through the list, adding 
    //them to a BST then returns the inorder list
    public DSALinkedList sort(DSALinkedList list)
    {
        BinarySearchTree graphTree = null;
        if(!list.isEmpty())
        {
            graphTree = new BinarySearchTree();
            Iterator t = list.iterator();
            Vertex v = null;
            for(Object e:list)
            {
                v = (Vertex)t.next();
                graphTree.insert(v.getLabel(), v);
            }
        }

        return graphTree.inOrder();
    }
}
