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
    private DSALinkedList postList;
    private int userCount;
    private int postCount;

    public Network()
    {
        userCount = 0;
        postCount = 0;
        Users = new HashTable();
        postList = new DSALinkedList();
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
            userCount++;
        }
        else
        {
            p = (Person)Users.get(name);
            if(!userExist(name))
            {
                p = new Person(name);
                Users.add(name, p);
                userCount++;
            }

            else
            {
                throw new IllegalArgumentException("Error occurred: " + Error.USER_DUP_ERR);
            }
        }
    }

    /*Method updates the follower and following of the users 
     *Add edge method for following People in the network*/
    public void newConnection(String user1, String user2)
    {
        Person p = null;
        Person p2 = null;
        if(userExist(user1) && userExist(user2))
        {
            p = (Person)Users.get(user1);
            p2 = (Person)Users.get(user2);
            //Check if Person p has not followed Person p2
            if(!p.equals(p2))
            {
                if(!alreadyFollowing(p, p2))
                {
                    p2.newFollower(p);
                    p.followed(p2);
                }    
            }
            else
            {
                throw new IllegalArgumentException("User cannot follow themselves");
            }
        }

        else
        {
            throw new IllegalArgumentException("Error occurred: " + Error.USER_ERR);
        }
    }

    /*Posts will also represent a vertex, creates a post object to be stored
     *in a linkedlist of object posts. Post objects are used in the time step function*/
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

    /*for file reading, adds a new post to the linkedlist of posts*/
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

    /*an addEdge method for Post, linking user to the post, 
     *creates a link of the Person liking the post object*/
    void likePost(String name, Post post)
    {
        Person p = null;
        
        if(userExist(name))
        {
            p = (Person)Users.get(name);
            //Check if Person p already liked the post
            if(!alreadyLiked(p, post))
            {
                //add post to Person p's linkedlist of posts
                p.setLiked(post);
                //add Person p to the linkedlist of likes in the post object
                post.like(p);
            }
        }
    }

    /*Retrieves a Person class from the HashTable, each person object in the 
     * Hashtable is distinguished by their user name*/
    public Person get(String name)
    {
        Person toGet = null;
        if(userExist(name))
        {
            toGet = (Person)Users.get(name);
        }
        else
        {
            throw new IllegalArgumentException("Error Occurred: " + Error.USER_ERR);
        }
        return toGet;
    }

    /*Function for finding users in the Network by their name, fails if user
     * is not in the Network*/
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

    //Idk what this does
    /*public void test(int i)
    {
        Person user = (Person)Users.check(i);
        String s = user.toString();
        System.out.println(s);
    }*/

    /*removes user from the network hashtable and linkedlist of people
     * removing a Person from the hashtable is (or close to) O(1) as their names 
     * are key values for hash-indexing*/
    public void removeUser(String name)
    {
        Person p = null;
        //Check if Person p is a registered user
        if(userExist(name))
        {
            p = (Person)Users.get(name);
            /*Iterate over the post and set them as deleted*/
            DSALinkedList posts = p.getPosts(); 
            removePerson(p);
            Users.remove(name);
            userCount--;
        }
        else
        {
            throw new IllegalArgumentException("Error occurred: " + Error.USER_ERR);
        }
    }

    /*Used for when a person is removed from the Hashtable of Person,
     * their followers and who they're following must be removed from their
     * following/follower list, time complexity is O(n+k) as it has to
     * iterate through the list of following (n) and the list of 
     * followers (k)*/
    private void removePerson(Person p)
    {
        if(!p.getFollowing().isEmpty())
        {
            Person iter = null;
            for(Object next:p.getFollowing())
            {
                iter = (Person)next;
                iter.unFollowed(p);
            }
        }
        if(!p.getFollowers().isEmpty())
        {
            Person iter = null;
            for(Object next:p.getFollowers())
            {
                iter = (Person)next;
                iter.unFollow(p);
            }
        }
    }

    /*Removes the link between a person to another person */
    public void removeConnection(String name1,String name2)
    {
        /*The vertices must exist for this operation to occur*/
        if(userExist(name1) && userExist(name2))
        {
            Person p = (Person)Users.get(name1);
            Person p2 = (Person)Users.get(name2);
            /*Person p will unfollow Person p2*/
            p.unFollow(p2);
            /*Person p will be removed from Person p2's follower list*/
            p2.unFollowed(p);
        }
        else
        {
            throw new IllegalArgumentException("Users do not exist`");
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
            for(Object iter:Users.Set())
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
    public boolean userExist(String name)
    {
        Person p = (Person)Users.get(name);
        return (p != null);
    }

    //Returns the user count in the network
    public int userCount()
    {
        return userCount;
    }

    //Returns the post count in the network
    public int postCount()
    {
        return postCount;
    }

    //Multipupose method to place a record list of
    //Users, this heapifies the record list in highest
    //to lowest



    /*Displays the list of users and post in the order of popularity and displays the 
     *current size of the network*/
    public void display()
    {
        listUser();
        listPost();
        System.out.println("User count in Hash table: " + Users.getCount());
        System.out.println("Actual user count: " + userCount);
        System.out.println("Actual post count: " + postCount);

    }

    /*Lists the followers of Person p parameter by
     * iterating over the linkedlist of followers Person p has*/
    public void listFollowers(Person p)
    {
        DSALinkedList followers = p.getFollowers();
        if(!followers.isEmpty())
        {
            Person follow = null;
            System.out.println("Users who are following " + p.getName());
            for(Object iter: followers)
            {
                follow = (Person)iter;
                System.out.println(follow.toString());
            }
        }
        else
        {
            System.out.println("User " + p.getName() + " has no followers.");
        }
    }

    /*Used for Interactive mode, performs the same function as listFollowers() but
     * the parameter taken in by this function is a String. This function checks 
     * the hashtable if the String import hashes into a existing 
     * object and displays the followers by iterating over the linked list of followers
     * Person p has*/
    public void displayFollowers(String name)
    {
        Person p = (Person)Users.get(name);
        if(p != null && (!p.getFollowers().isEmpty()))
        {
            System.out.println("Followers of: " + name);
            //For each loop is used to iterate over the linked list
            for(Object iter:p.getFollowers())
            {
                //Type cast the stored object back into a Person object
                Person next = (Person)iter;
                if(userExist(next.getName()))
                {
                    System.out.println(next.toString());
                }
            }
        }
    }


    /*Prints out the lists of posts from most likes to the lowest.
     * Time complexity of O(nlog(n) +nlog(n)), it has to iterate over the linked list
     * to and add each item into the priority queue and removed again which takes
     * another O(nlog(n))*/
    public void listPost()
    {
        Post p = null;
        if(!postList.isEmpty())
        {
            //Store the items into a max priority queue
            PriorityQueue list = postOrder();
            while(!list.isEmpty())
            {
                /*Keep calling the remove function to remove items from
                 * highest to lowest*/
                p = (Post)list.remove();
                System.out.println(p.toString());
            }
        }
        else
        {
            System.out.println("No posts made");
        }
    }

    /*Prints out all active users from the highest popularity to 
      lowest*/
    public void listUser()
    {

        Person p = null;
        if(!Users.isEmpty())
        {
            PriorityQueue list = userOrder();
            while(!list.isEmpty())
            {
                p = (Person)list.remove();
                System.out.println(p.toString());
            }
       }
       else
       {
            System.out.println("No active users in the Social Network");
       }
    }
    
    /*Places the active users in the priority queue based on their popularity
      and returns that max priority queue*/
    public PriorityQueue userOrder()
    {
        Person p = null;
        PriorityQueue activeUser = null;
        if(!Users.isEmpty())
        {
            activeUser = new PriorityQueue();
            for(Object iter:Users.Set())
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
        /*probablity over 100 will return true*/
        if(chance > 100)
        {
            outcome = true;
        }

        else
        {
            /*Take a random number from 100 and check if the result number
             *is below integer k to return true*/
            Random rand = new Random();
            int num = rand.nextInt(101);
            if(num <= chance)
            {
                outcome = true;
            }
        }
        return outcome;
    }

    /*Probability function for clickbait factors*/
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

    /*Time step for simulation mode in the assignment.
     * Performs a time step and saves it into a file
     * the integer imports are the probabilities*/
    public void simulationTimeStep(String file, int k,int n)
    {
        try
        {
            DSAQueue q = timeStep(k, n);
            System.out.print(q.isEmpty());
            eventFileReader.eventToFile(file,this,q);
            display();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /*Goes over the linkedlist of users and compares with the
     *active users in the hashtable of users, hashtable returns null if 
     *user does not exist/deleted*/
    //OLD CODE, MIGHT DELETE LATER
    /*public void clearFollowers()
    {
        update();
        Person p = null;
        HashTable visited = new HashTable();
        for(Object iter:Users.Set())
        {
            p = (Person)iter;
            if(visited.isEmpty())
            {
                visited.add(p.getName(), p);
            }
            depthFirstSearch(p, visited);
        }
    }*/

    //OLD CODE, MAY DELETE LATER
    /*Update to remove followers from active users, a DFS approach*/
    /*private void depthFirstSearch(Person p, HashTable visited)
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
    }*/

    /*Puts the items from linked list to priorityQueue return null if no items*/
    private PriorityQueue toQueue()
    {
        PriorityQueue toQueue = null;
        if(!Users.isEmpty())
        {
            toQueue = new PriorityQueue();
            Person p = null;
            //Iterate over the current user list to check for live objects
            for(Object iter:Users.Set())
            {
                p = (Person)iter;
                //Skip removed objects
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
       // updatePosts();
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

    /*Method Specific only for private inner classes, 
     * sets Person p to follow Person v by adding each other to their 
     * linkedlist of followers and following list*/
    private void setFollow(Person p, Person v)
    {
        if(!alreadyFollowing(p, v))
        {
            v.newFollower(p);
            p.followed(v);
        }
        else
        {
            throw new IllegalArgumentException("User: " + v.getName() + "already following " + p.getName());        
        }
    }

    //Checks over the linked list of who Person p is following
    //Against Person v
    public boolean alreadyFollowing(Person p, Person v)
    {
        Person w = null;
        DSALinkedList pFollowing = p.getFollowing();
        boolean alreadyFollowed = false;
        //iterate over the following people person p is following
        //return true is p is already following v
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
            //Iterate over the like of likes checking if the post object exists in the linked list
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

    /*Returns null if the network is empty, else returns a Queue of strings
     * to save the network contents in a file for saving/loading*/
    public DSAQueue networkContents()
    {
        DSAQueue networkString = null;
        //check if post or users is empty
        //if both are empty, saving network will not occur
        if(!Users.isEmpty() || !postList.isEmpty())
        {
            DSALinkedList userList= Users.Set();
            networkString = new Queue();
            for(Object iter:userList)
            {
                Person person = (Person)iter;
                if(userExist(person.getName()))
                {
                    networkString.enQueue(((Person)iter).getName());    
                }
            }

            //Iterate over the post list and save them as strings to a queue
            for(Object iter:postList)
            {
                Post post = (Post)iter;
                networkString.enQueue("P:"+ post.getOP().getName() + ":" + post.getMessage());
            }

            Person v = null;
            //Iterate over every person and save their followers and following
            for(Object iter:userList)
            {
                v = (Person)iter;
                //Check if the follower list is empty then
                //Iterate over the person follower list
                if(!v.getFollowers().isEmpty())
                {
                    String save;
                    Person e = null;
                    for(Object it:v.getFollowers())
                    {
                        e=(Person)it;
                        networkString.enQueue("F:"+v.getName()+":"+e.getName());
                    }
                }
                //Check if the following list is empty then
                //Iterate over the person following list
                if(!v.getFollowing().isEmpty())
                {
                    String save;
                    Person e = null;
                    for(Object it:v.getFollowing())
                    {
                        e=(Person)it;
                        networkString.enQueue("F:"+e.getName()+":"+v.getName());
                    }
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Cannot save empty network");
        }
        return networkString;
    }

    /*Network Class will contain a linkedlist of posts from users
     * This way, accessing Posts from users will be easier, rather 
     * than having to check each person object for their linked list of posts
     * as a person vertex may not have made a post.*/

    private class Post
    {
        //Person object
        private Person op;
        //message
        private String status;
        //list of users who like the post
        private DSALinkedList likedBy;
        //Determines the likelhood of liking a post
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
                throw new IllegalArgumentException("Post cannot me created for non-existing user.");
            }
        }

        //For reading event files, containing click bait factors for messages
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

        //return the number of likes the post has
        public int getLikes()
        {
            return likedBy.size();
        }

        //return the click bait factor of the post
        public int getClickBaitFactor()
        {
            return clickBaitFactor;
        }

        //return the object person of the post
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

        //return the file string content for saving
        public String toFileString()
        {
            return ("P:" + op.getName() + ":" + getMessage());
        }

        //for displaying network posts
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
