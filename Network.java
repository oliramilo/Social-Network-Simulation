//Draft
import java.util.Random;

/*Network is a graph, made a new "graph" data structure for the assignment specifications
  most of the code from the graph is different to this one. Network class contains a hashtable
  and linkedlists of users/posts for every "Vertex" or edges that exist in the Social Network*/
public class Network
{
    /*The choice of a hashtable allows a Person to be mapped
      in the graph given by their name since get/remove/find operations using 
      a linkedlist costs O(n) time, the tradeoff with this choice is space which
      is O(2n) ~ O(n)since we're placing 1 object in 2 containers*/
    private HashTable Users;
    /*Linkedlist of Users and Posts*/
    private DSALinkedList userList;
    private DSALinkedList postList;
    private int userCount;
    private int postCount;

    /**/
    public Network()
    {
        userCount = 0;
        postCount = 0;
        Users = new HashTable();
        postList = new DSALinkedList();
        userList = new DSALinkedList();
    }

    //Create a Person and stored in a Hash Table
    //This is the addVertex function
    public void addUser(String name)
    {
        Person p = null;
        if(Users.isEmpty())
        {
            p = new Person(name);
            Users.add(name, p);
            userList.insertLast(p);
            userCount++;
        }
        else
        {
            p = (Person)Users.get(name);
            if(!userExist(name))
            {
                p = new Person(name);
                Users.add(name, p);
                userList.insertLast(p);
                userCount++;
            }

            else
            {
                System.out.print("User " + name + " exists");
                throw new IllegalArgumentException("Error occurred: " + Error.USER_DUP_ERR);
            }
        }
    }

    //Method updates the follower and following of the users
    //Add edge method
    public void newConnection(String user1, String user2)
    {
        Person p = null;
        Person p2 = null;
        if(userExist(user1) && userExist(user2))
        {
            p = (Person)Users.get(user1);
            p2 = (Person)Users.get(user2);
            //Check if Person p has not followed Person p2
            if(!alreadyFollowing(p, p2))
            {
                p2.newFollower(p);
                p.followed(p2);
            }
        }

        else
        {
            System.out.println("Users: " + user1 + " cannot follow " + user2);
            throw new IllegalArgumentException("Error occurred: " + Error.USER_ERR);
        }
    }

    /*Posts will also represent a vertex*/
    void addPost(String name, String status)
    {
        Person p = null;
        if(userExist(name))
        {
            p = (Person)Users.get(name);
            Post pPost = new Post(name,status);
            p.statusUpdate((Object)pPost);
            postList.insertLast(pPost);
            postCount++;
        }
        else
        {
            throw new IllegalArgumentException("Invalid operation for: " + Error.USER_ERR);
        }
    }

    void readPost(String name, String status,int likes)
    {
        Person p = null;
        if(userExist(name))
        {
            p = (Person)Users.get(name);
            Post pPost = new Post(name,status,likes);
            p.statusUpdate((Object)pPost);
            postList.insertLast(pPost);
            postCount++;
        }
        else
        {
            throw new IllegalArgumentException("Invalid operation for: " + Error.USER_ERR);
        }
    }

    //an addEdge method for Post, linking user to the post
    void likePost(String name, Post post)
    {
        Person p = null;
        if(userExist(name))
        {
            p = (Person)Users.get(name);
            //Check if Person p already liked the post
            if(!alreadyLiked(p, post))
            {
                p.setLiked(post);
                post.like(p);
            }
        }
    }

    public String find(String name)
    {
        Person toFind = null;
        String personInfo = null;
        if(userExist(name))
        {
            toFind = (Person)Users.get(name);
            personInfo = toFind.toString();
        }
        else
        {
            throw new IllegalArgumentException("Error Occurred: " + Error.USER_ERR);
        }
        return personInfo;
    }

    public void test(int i)
    {
        Person user = (Person)Users.check(i);
        String s = user.toString();
        System.out.println(s);
    }

    //Gets User info from the hashtable 
    //Deregisters the user which "deletes"
    public void removeUser(String name)
    {
        Person p = null;
        //Check if Person p is a registered user
        if(userExist(name))
        {
            p = (Person)Users.get(name);
            DSALinkedList posts = p.getPosts();
            Post postDlt = null;
            Users.remove(name);

            userCount--;
        }
        else
        {
            throw new IllegalArgumentException("Error occurred: " + Error.USER_ERR);
        }
    }

    /*Removes the link between a person to another person */
    public void removeConnection(String name1,String name2)
    {
        /*The vertex must exist*/
        if(userExist(name1) && userExist(name2))
        {
            Person p = (Person)Users.get(name1);
            Person p2 = (Person)Users.get(name2);
            /*Person p will unfollow Person p2*/
            p.unFollow(p2);
            /*Person p will be removed from Person p2's follower list*/
            p2.unFollowed(p);
        }
    }

    /*The Vertices that were removed were not actually removed but were 
      marked deleted, this functions to create a new HashTable placing 
      only "live" objects in the HashTable of current users (Vertex)*/
    private void updateTable()
    {
        Person p = null;
        HashTable update = new HashTable();
        if(userCount !=0)
        {
            for(Object iter:userList)
            {
                p = (Person)iter;
                if(userExist(p.getName()))
                {
                    update.add(p.getName(),p);
                }
            }
            this.Users = update;
        }
    }

    //This method is used only inside this class
    private boolean userExist(String name)
    {
        Person p = (Person)Users.get(name);
        return (p != null);
    }

    /*Method is used for updating when an event occurs
      Such as a User liking a post, their followers need to be notified
      If event is triggered */
    public void onEvent(boolean b)
    {

    }

    public int userCount()
    {
        return userCount;
    }

    public int postCount()
    {
        return postCount;
    }

    //Multipupose method to place a record list of
    //Users, this heapifies the record list in highest
    //to lowest



    public void display()
    {
        listUser();
        listPost();
        System.out.println("User count in linked list: " + userList.size());
        System.out.println("User count in Hash table: " + Users.getCount());
        System.out.println("Actual user count: " + userCount);
        System.out.println("Actual post count: " + postCount);

    }

    public void listPost()
    {
        PriorityQueue list = postOrder();
        Post p = null;
        while(!list.isEmpty())
        {
            p = (Post)list.remove();
            System.out.println(p.toString());
        }
    }

    public void listUser()
    {
        PriorityQueue list = userOrder();
        Person p = null;
        while(!list.isEmpty())
        {
            p = (Person)list.remove();
            System.out.println(p.toString());
        }
    }

    public PriorityQueue userOrder()
    {
        Person p = null;
        PriorityQueue activeUser = null;
        if(!userList.isEmpty())
        {
            activeUser = new PriorityQueue();
            for(Object iter:userList)
            {
                p = (Person)iter;
                if(userExist(p.getName()))
                {
                    activeUser.add(p.getPopularity(), p);
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("No active users");
        }
        return activeUser;
    }

    //Returns a max priorityQueue of the unsorted posts 
    public PriorityQueue postOrder()
    {
        PriorityQueue pQ = null;
        //Check the linked list if its empty or null
        if(!postList.isEmpty())
        {
            pQ = new PriorityQueue();
            if(postList.peekFirst() instanceof Post)
            {
                Post p = null;
                for(Object iter:postList)
                {
                    p = (Post)iter;
                    if(!p.isDeleted())
                    {
                        pQ.add(p.getLikes(), p);
                    }
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Error occurred: " + Error.EMPTY_LIST);
        }
        return pQ;
    }

    /*Generates a chance number based on percentage k
      Return true or false which will trigger event*/
    public boolean probablity(int k)
    {
        int chance = k;
        boolean outcome = false;
        if(chance > 100)
        {
            outcome = true;
        }

        else
        {
            Random rand = new Random();
            int num = rand.nextInt(101);
            if(num <= chance)
            {
                outcome = true;
            }
        }
        return outcome;
    }

    public boolean probablity(int k, int clickBaitFactor)
    {
        int chance = k*clickBaitFactor;
        boolean outcome = false;
        if(chance > 100)
        {
            outcome = true;
        }
        else
        {
            Random rand = new Random();
            int num = rand.nextInt(101);
            if(num <= chance)
            {
                outcome = true;
            }
        }
        return outcome;
    }
    public void simulationTimeStep(String file, int k,int n)
    {
        try
        {
            DSAQueue q = timeStep(k, n);
            System.out.print(q.isEmpty());
            eventFileReader.eventToFile(file,this,q);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void clearFollowers()
    {
        update();
        Person p = null;
        HashTable visited = new HashTable();
        /*Iterate through the p*/
        for(Object iter:userList)
        {
            p = (Person)iter;
            if(visited.isEmpty())
            {
                visited.add(p.getName(), p);
            }
            depthFirstSearch(p, visited);
        }
    }
    /*Update to remove followers from active users, a DFS approach*/
    private void depthFirstSearch(Person p, HashTable visited)
    {
        DSAStack searchOrder = new Stack();
        searchOrder.push(p);
        while(!searchOrder.isEmpty())
        {
            p = (Person)searchOrder.pop();
            Person w = null;
            for(Object iter:p.getFollowers())
            {
                w = (Person)iter;
                if(userExist(w.getName()) && visited.get(w.getName()) != null)
                {
                    searchOrder.push(w);
                    visited.add(w.getName(), w);
                }
            }
            updateFollowers(p);
            updateFollowing(p);
        }
    }

    /*Puts the items from linked list to priorityQueue return null if no items*/
    private PriorityQueue toQueue()
    {
        PriorityQueue toQueue = null;
        if(!userList.isEmpty())
        {
            toQueue = new PriorityQueue();
            Person p = null;
            //Iterate over the current user list to check for live objects
            for(Object iter:userList)
            {
                p = (Person)iter;
                //Skip dead objects
                if(userExist(p.getName()))
                {
                    toQueue.add(p.getPopularity(), p);
                }
            }
        }
        return toQueue;
    }

    /*Time step method, for all the posts that are active, each person that is 
      following the original poster will be notified and have a probability of 
      liking the post and following the original poster, given by interger k and n.
      Assertion: The original poster's followers are actually following them
      Method returns a queue of queues that contain strings of information
      that happened during the time step*/
    public DSAQueue timeStep(int k,int n)
    {
        //Update the linkedlist and HashTable before time stepping 
        update();
        DSAQueue orderList = new Queue();
        /*Iterate over the post of linkedlists */
        for(Object iter:postList)
        {
            Post p = (Post)iter;
            /*The post must be active and not deleted */
            if(!p.isDeleted())
            {
                Person op = p.getOP();
                if(!op.getFollowers().isEmpty())
                {
                    /*Go over the linkedlist of followers that the original
                      poster has */
                    for(Object iterVertex:op.getFollowers())
                    {
                        Person followerOfOP = (Person)iterVertex;

                        DSAQueue event = new Queue();
                        System.out.println("Performing a breadth first search...");
                        orderList.enQueue(breadthFirstSearch(followerOfOP, p , event, k, n));
                    }
                }
            }        
        }
        return orderList;
    }

    /*Breadth first search for the time step, returns a queue of strings displaying
      information of what happened during the time step. The original breadth first search 
      originally was a recursive function but in an instance of having a lot of People in the 
      graph, may occur a stack overflow due to too many recursive calls*/
    private DSAQueue breadthFirstSearch(Person pFollow, Post pPost, DSAQueue event, int k, int n)
    {
        DSAQueue searchOrder = new Queue();
        searchOrder.enQueue(pFollow);  
        /*Probability of liking a post triggers the breadth first search */
        Person p = null;
        //This is how we will determine which nodes have been visited
        HashTable visited = new HashTable();
        while(!searchOrder.isEmpty())
        {
            p = (Person)searchOrder.deQueue();
            if(probablity(k,pPost.getClickBaitFactor()) && !pPost.getOP().equals(p))
            {
                /*The original poster cannot like their own posts, that's sad.
                  And check if the person already likes the post*/
                if(!alreadyLiked(p, pPost) && !p.equals(pPost.getOP()))
                {
                    String e = ("L:"+p.getName() + ":P:" + pPost.getOP().getName() + ":" + pPost.getMessage());
                    System.out.println(e);
                    event.enQueue(e);
                    p.setLiked(pPost);
                    pPost.like(p);
                }
                /*Probabiltiy of following*/
                if(probablity(n))
                {
                    //Person cannot follow themselves
                    if(!p.equals(pPost.getOP()) && !alreadyFollowing(p, pPost.getOP()))
                    {
                        String e2 = ("F:"+ p.getName() + ":" + pPost.getOP().getName());
                        System.out.println(e2);
                        event.enQueue(e2);
                        setFollow(p, pPost.getOP());
                    }
                }
                /*Make sure the current follower actually has followers */
                if(!p.getFollowers().isEmpty())
                {
                    Person next = null;
                    for(Object iter:p.getFollowers())
                    {
                        next = (Person)iter;
                        if(userExist(next.getName()) && visited.get(next.getName()) == null)
                        {
                            visited.add(next.getName(), next);
                            searchOrder.enQueue(next);
                        }
                    }
                }
            }
        }
        return event;
    }

    /* Method for updates, It takes in the probability of liking
       and following the Author of the post represented by integer k and n
       respectively. If the Person pFollow likes the post, their followers will
       be notified and influence them to follow the post as well using breadth first
       search, it didnt make sense to use depth first search as that method would
       prioritise going deep into the path as possible rather than notifying all of the
       Person's followers first. */

  
    public void update()
    {
        updateTable();
        updateList();
       // updatePosts();
    }

    public void updateList()
    {
        DSALinkedList list = new DSALinkedList();
        for(Object iter:userList)
        {
            if(userExist(((Person)iter).getName()))
            {
                list.insertLast((Person)iter);
            }
        }
        this.userList = list;
    }

    /*Updates the linked list of posts for the user Person p */
    public void updatePosts()
    {
        DSALinkedList update = new DSALinkedList();
        Post post = null;
        if(!postList.isEmpty())
        {
            for(Object iter:postList)
            {
                post = (Post)iter;
                if(!post.isDeleted())
                {
                    update.insertLast(post);
                }
            }
        }
        this.postList = update;
    }

    /**
     * Updates the followers of Person p
     * @param p
     */
    private void updateFollowers(Person p)
    {
        DSALinkedList updateFollowers = new DSALinkedList();
        for(Object check:p.getFollowers())
        {
            //Check if vertex exists in hash table
            if(userExist(((Person)check).getName()))
            {
                updateFollowers.insertLast(check);
            }
        }
        p.setFollowers(updateFollowers);
    }

    /**
     * Updates Person p's list of following 
     * @param p
     */
    private void updateFollowing(Person p)
    {
        DSALinkedList updateFollowing = new DSALinkedList();
        for(Object check:p.getFollowing())
        {
            if(userExist(((Person)check).getName()))
            {
                updateFollowing.insertLast(check);
            }
        }
        p.setFollowing(updateFollowing);
    }

    /*Method Specific only for private inner classes*/
    private void setFollow(Person p, Person v)
    {
        if(!alreadyFollowing(p, v))
        {
            v.newFollower(p);
            p.followed(v);
        }
    }

    //Checks over the linked list of who Person p is following
    //Against Person v
    public boolean alreadyFollowing(Person p, Person v)
    {
        Person w = null;
        DSALinkedList pFollowing = p.getFollowing();
        boolean alreadyFollowed = false;
        for(Object next:pFollowing)
        {
            w = (Person)next;
            if(w.equals(v))
            {
                alreadyFollowed = true;
            }
        }
        return alreadyFollowed;
    }

    /*Only used inside this class for BFS search and event riggers*/
    private boolean alreadyLiked(Person p, Post pPost)
    {
        DSALinkedList listOfLikes = null;
        boolean alreadyLiked = false;
        if(!p.getPostsLiked().isEmpty())
        {
            listOfLikes = p.getPostsLiked();
            Post liked = null;
            for(Object next:listOfLikes)
            {
                liked = (Post)next;
                if(liked.equals(pPost))
                {
                    alreadyLiked = true;
                }
            }
        }
        return alreadyLiked;
    }

    /*Returns null if the network is empty  */
    public DSAQueue networkContents()
    {
        DSAQueue networkString = null;
        if(!userList.isEmpty() && !postList.isEmpty())
        {
            networkString = new Queue();
            for(Object iter:userList)
            {
                networkString.enQueue(((Person)iter).getName());
            }
        }
        else
        {
            throw new IllegalArgumentException("Cannot save empty network");
        }
        return networkString;
    }

    /*Network Class will contain the pool of posts from users
      This way, accessing Posts from users will be easier

      Post class will have a Person Object representing
      The original poster, Status is the post message
      A linked list is used to keep a list of users who liked the post
      It would've been easier and faster to just return the size
      of the likedBy list rather than have a like counter for the post
      But for when deletion comes, setting the list to null would cause
      the size calling to be prone to NPEs. Hence why there is a counter*/

    private class Post
    {
        private Person op;
        private String status;
        private DSALinkedList likedBy;
        private int clickBaitFactor;
        public Post(String name, String message)
        {
            if(userExist(name))
            {
                op = (Person)Users.get(name);
                clickBaitFactor = 1;
                status = message;
                likedBy = new DSALinkedList();
            }
            else
            {
                throw new IllegalArgumentException("Post cannot me created");
            }
        }

        public Post(String name, String message,int clickBaitFactor)
        {
            op = (Person)Users.get(name);
            status = message;
            likedBy = new DSALinkedList();
            this.clickBaitFactor = clickBaitFactor;
            /*To make a post, the Person must be in the Network graph*/
            if(!userExist(op.getName()))
            {
                throw new IllegalArgumentException("Error occurred: " + Error.USER_ERR);
            }
        }

        public void setClickBaitFactor(int clickBaitFactor)
        {
            this.clickBaitFactor = clickBaitFactor;
        }

        //Adds user to the list of likes
        public void like(Person p)
        {
            
            likedBy.insertLast(p);
        }

        public int getLikes()
        {
            return likedBy.size();
        }

        public int getClickBaitFactor()
        {
            return clickBaitFactor;
        }

        public Person getOP()
        {
            return op;
        }
        //This is used when a post is deleted
        //Import will often be 0

        //When a User is deleted from the Social Network
        //Their posts must be deleted along with the 
        //likes history, only deleted if OP is deregistered
        /*public void postDelete()
        {
            if(userExist(op.getName()))
            {
                delete = true;
            }
        }*/

        //method to check if current post is deleted
        //A post is deleted only if a user is deleted
        public boolean isDeleted()
        {
            return Users.get(op.getName()) == null;
        }

        public String toFileString()
        {
            return ("P:" + op.getName() + ":" + getMessage());
        }

        public String toString()
        {
            return (op.getName()+ ": " + status + "\n" + getLikes() + " Liked this post");
        }

        public String getMessage()
        {
            return status;
        }
    }
}
