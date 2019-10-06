import java.util.*;
import java.io.*;
class BinarySearchTree implements Serializable
{
    private static final long serialVersionUID=1;
    private TreeNode root;
    private class TreeNode implements Serializable
    {
        private static final long serialVersionUID=1;
        private String key;
        private Object element;
        private TreeNode left;
        private TreeNode right;

        TreeNode()
        {
            key = "";
            element = null;
            left = null;
            right = null;
        }

        TreeNode(String p_key,Object e)
        {
            String chKey = p_key.toUpperCase().trim();
            if(chKey.equals(null) || chKey.equals("")){
                throw new IllegalArgumentException("Invalid key");
            }
            key = chKey;
            element = e;
            left = null;
            right = null;
        }

        void setLeft(TreeNode leftNd)
        {
            left = leftNd;
        }

        void setRight(TreeNode rightNd)
        {
            right = rightNd;
        }

        TreeNode getLeft()
        {
            return this.left;
        }

        TreeNode getRight()
        {
            return this.right;
        }

        Object getElement()
        {
            return this.element;
        }

        String getKey()
        {
            return this.key;
        }
    }

    BinarySearchTree()
    {
        root = null;
    }

    public Object find(String fKey)
    {
        Object toFind = null;
        toFind = findRecursive(fKey,root);
        if(toFind == null)
        {
            throw new IllegalArgumentException("Cannot find item");
        }
        return toFind;
    }

    private Object findRecursive(String fKey,TreeNode curNd)
    {
        Object val = null;
        if(curNd != null)
        {
            val = curNd.getElement();
            if(fKey.equals(curNd.getKey()))
            {
                val = curNd.getElement();
            }
            else if(fKey.compareTo(curNd.getKey()) < 0)
            {
                val = findRecursive(fKey,curNd.getLeft());
            }
            else
            {
                val = findRecursive(fKey,curNd.getRight());
            }
        }
        return val;
    }

    void insert(String key, Object data)
    {
        if(data == null)
        {
            throw new IllegalArgumentException("Inserted data is null");
        }
        else if(root == null)
        {
            root = new TreeNode(key,data);
        }
        else
        
        {
            insertRecurse(key,data,root);
        }
    }

    private TreeNode insertRecurse(String key, Object data, TreeNode curNd)
    {
        TreeNode updateNode = curNd;
        if(updateNode == null)
        {
            curNd = new TreeNode(key,data);
            updateNode = curNd;
        }
        else if(key.compareTo(updateNode.getKey()) < 0)
        {
            curNd.setLeft(insertRecurse(key,data,updateNode.getLeft()));
        }
        else
        {
            curNd.setRight(insertRecurse(key,data,updateNode.getRight()));
        }
        return updateNode;
    }

    void delete(String key)
    {
        //If the node to be deleted is the root
        if(key.equals(root.getKey()))
        {
            if(root.getLeft() == null && root.getRight() == null)
            {
                root = null;
            }
            else if(root.getLeft() != null && root.getRight() == null)
            {
                root = root.getLeft();
            }
            else if(root.getLeft() == null && root.getRight() != null)
            {
                root = root.getRight();
            }
            else
            {
                TreeNode update = findSuccessorRecurse(root.getRight());
                if(update != root.getRight())
                {
                    update.setRight(root.getRight());
                }
                update.setLeft(root.getLeft());
                root = update;
            }
        }
        else
        {
            deleteRecurse(key,root);
        }
    }
    private TreeNode deleteNode(String key, TreeNode curNd)
    {
        //Best case when node has no child
        if(curNd.getLeft() == null && curNd.getRight() == null)
        {
            curNd = null;
        }
        //When node has left only
        else if(curNd.getLeft() != null && curNd.getRight() == null)
        {
            curNd = curNd.getLeft();
        }
        //When node has right only
        else if(curNd.getLeft() == null && curNd.getRight() != null)
        {
            curNd = curNd.getRight();
        }
        //When node has both
        else
        {
            //We need to find the appropriate node successor to replace the old one
            //Look to the right node
            TreeNode update = findSuccessorRecurse(curNd.getRight());
            if(update != curNd.getRight())
            {
                update.setRight(curNd.getRight());
            }
            update.setLeft(curNd.getLeft());
            curNd = update;
        }
        return curNd;
    }

    //Used to find the successor tree node
    TreeNode findSuccessorRecurse(TreeNode curNd)
    {
        TreeNode successor = curNd;
        if(curNd.getLeft() != null)
        {
            successor = findSuccessorRecurse(curNd.getLeft());
            if(successor == curNd.getLeft()){
                curNd.setLeft(successor.getRight());
            }
        }
        return successor;
    }
    private TreeNode deleteRecurse(String key, TreeNode curNd)
    {
        //TreeNode nwNd = curNd;
        //Traversed through the BST, Key not found
        if(curNd == null)
        {
            throw new NoSuchElementException(key + " does not exist");
        }
        //Found match, now delete
        else if(key.equals(curNd.getKey()))
        {
            System.out.println("Deleting " + curNd.getKey());
            curNd = deleteNode(key,curNd);
        }
        else if(key.compareTo(curNd.getKey()) < 0)
        {
            curNd.setLeft(deleteRecurse(key,curNd.getLeft()));
        }
        else
        {
            curNd.setRight(deleteRecurse(key,curNd.getRight()));
        }
        return curNd;
    }
    
    //inorder traverse for the tree
    public  DSALinkedList inOrder()
    {
        DSALinkedList inOrderList = new DSALinkedList();
        if(root == null)
        {
            throw new NoSuchElementException("No Elements in the binary search tree");
        }
        else
        {
            inOrderList = inorderRecurse(root, inOrderList);
        }
        return inOrderList;
    }

    //Find the key of the max node
    public String max()
    {
        String maxKey = "";
        if(root == null)
        {
            throw new NoSuchElementException("Tree is empty");
        }
        else
        {
            maxKey = maxRecurse(root);
        }
        return maxKey;
    }
    
    //Recurses to the right most subtree for the max key
    private String maxRecurse(TreeNode curNd)
    {
        String maxKey = "";
        if(curNd.getRight() != null)
        {
            maxKey = maxRecurse(curNd.getRight());
        }
        else
        {
            maxKey = curNd.getKey();
        }
        return maxKey;
    }

    //Finds the total number of nodes in the tree
    public int size()
    {
        int size =0;
        if(root != null)
        {
            size = sizeTree(root);
        }
        return size;
    }

    //Counts the total number of nodes in the tree
    private int sizeTree(TreeNode curNd)
    {
        int size=0;
        if(curNd !=null)
        {
            size++;
            sizeTree(curNd.getLeft());
            sizeTree(curNd.getRight());
        }
        return size;
    }

    //Calculates how balanced the tree is
    float balance()
    {
        int min = minlv();
        int max = maxlv();
        int height = height();
        float balance = (1-((((float)min+(float)max)/2)/(float)(height)))*100;
        return balance;
    }

    //Finds the level where the max node is located
    public int maxlv()
    {
        return maxLevel(root);
    }

    //Finds the level where the min node is located
    public int minlv()
    {
        return minLevel(root);
    }

    //Recurses through the right most of the subtree 
    //Finds the level where max node is located
    private int maxLevel(TreeNode curNd)
    {
        int level = 0;
        while(curNd.getRight() != null)
        {
            level++;
            curNd = curNd.getRight();
        }
        return level;
    }

    //Recurses through the left most of the subtree 
    //Finds the level where them in node is located
    private int minLevel(TreeNode curNd)
    {
        int level = 0;
        while(curNd.getLeft() != null)
        {
            level++;
            curNd = curNd.getLeft();
        }
        return level;
    }

    //Gets the key value of the min node
    public String min()
    {
        String minKey = "";
        if(root == null)
        {
            throw new NoSuchElementException("No key found");
        }
        else
        {
            minKey = minRecurse(root);
        }
        return minKey;
    }

    //Recurses through the left most of the sub tree and return the min key
    private String minRecurse(TreeNode curNd)
    {
        String minKey  = "";

        if(curNd.getLeft() != null)
        {
            minKey = minRecurse(curNd.getLeft());
        }
        else
        {
            minKey = curNd.getKey();
        }
        return minKey;
    }

    //Calculates the height of the tree
    public int height()
    {
        int height = 0;
        if(root != null)
        {
            height = intHeightRec(root);
        }
        return height;
    }

    private int intHeightRec(TreeNode curNd)
    {
        int leftHeight=0;
        int rightHeight=0;
        int totalHeight=0;
        if(curNd != null)
        {
            leftHeight = intHeightRec(curNd.getLeft());
            rightHeight = intHeightRec(curNd.getRight());
            if(leftHeight > rightHeight)
            {
                totalHeight = leftHeight+1;
            }
            else
            {
                totalHeight = rightHeight+1;
            }
        }
        return totalHeight;
    }

    //inorder traversal of the tree
    private DSALinkedList inorderRecurse(TreeNode curNd, DSALinkedList inorderList)
    {
        if(curNd != null)
        {
            inorderRecurse(curNd.getLeft(),inorderList);
            inorderList.insertLast(curNd.getElement());
            inorderRecurse(curNd.getRight(), inorderList);
        }
        return inorderList;
    }

    //preorder traversal of the tree
    public DSAQueue preOrder()
    {
        DSAQueue preOrderQ = new Queue();
        if(root == null)
        {
            throw new NoSuchElementException("Empty tree");
        }
        else
        {
            preOrderQ = preorderRecurse(root,preOrderQ);
        }
        return preOrderQ;
    }

    private DSAQueue preorderRecurse(TreeNode curNd, DSAQueue preorderList)
    {
        if(curNd != null)
        {
            preorderList.enQueue(curNd.getKey());
            preorderRecurse(curNd.getLeft(),preorderList);
            preorderRecurse(curNd.getRight(),preorderList);
        }
        return preorderList;
    }

    //postorder traversal of the tree
    public DSAQueue postOrder()
    {
        DSAQueue post = new Queue();
        if(root != null)
        {
            post = postorderRecurse(root,post);
        }
        else{
            throw new NoSuchElementException("Empty Tree");
        }
        return post;
    }
    private DSAQueue postorderRecurse(TreeNode curNd, DSAQueue postorder)
    {
        if(curNd != null)
        {
            postorderRecurse(curNd.getLeft(),postorder);
            postorderRecurse(curNd.getRight(),postorder);
            postorder.enQueue(curNd.getKey());
        }
        return postorder;
    }

    //Serialization of binary search tree
    public void save(String file)
    {
        saveTree(this,file);
    }

    private void saveTree(BinarySearchTree tree, String file)
    {
        FileOutputStream strm;
        ObjectOutputStream obj;
        try
        {
            strm = new FileOutputStream(file);
            obj = new ObjectOutputStream(strm);
            obj.writeObject(tree);

            strm.close();
            obj.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }   
    }

    //Load function serialization of the binary search tree
    public BinarySearchTree load(String file)
    {
        return loadTree(file);
    }

    private BinarySearchTree loadTree(String file)
    {
        FileInputStream strm;
        ObjectInputStream obj;
        BinarySearchTree t = null;
        try
        {
            strm = new FileInputStream(file);
            obj = new ObjectInputStream(strm);
            t = (BinarySearchTree)obj.readObject();

            strm.close();
            obj.close();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return t;
    }

    //File reader of values, then inserted to the search tree 
    void read(String file, BinarySearchTree t)
    {
        FileInputStream fStrm = null;
        InputStreamReader rdr;
        BufferedReader bfrR;
        String line;

        try
        {
            fStrm = new FileInputStream(file);
            rdr = new InputStreamReader(fStrm);
            bfrR = new BufferedReader(rdr);
            line = bfrR.readLine();
            while(line != null)
            {
                processLine(line,t);
                line = bfrR.readLine();
            }
            fStrm.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
    }

    //Process line for read function
    void processLine(String line, BinarySearchTree t)
    {
        String sep[] = line.split(",");
        t.insert(sep[0],sep[1]);
    }

    //Bianry search tree to csv
    void toCsv(String file,DSAQueue order)
    {
        PrintWriter write;
        FileOutputStream toFile;
        try
        {
            toFile = new FileOutputStream(file);
            write = new PrintWriter(toFile);
            while(!order.isEmpty())
            {
                write.println(order.deQueue());
            }
            write.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //I use this to check the root is being deleted correctly
    void printRoot()
    {
        try
        {
            System.out.println("Root is: " + root.getKey());
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}

