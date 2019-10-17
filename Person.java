import java.util.Iterator;

//Person represents a vertex in the Network graph
//Person class stores list of followers,
//who they're following, posts
//name and registry which is used to determine
//Whether a person is deleted from the Network or not
public class Person
{
    /*Person class has a linked list of followers, who is following them,
      posts made by the user and a list of posts the user likes */
    private DSALinkedList followers;
    private DSALinkedList following;
    private DSALinkedList posts;
    private DSALinkedList likedPosts;
    /*A person in the graph is identified by their String name */
    private String name;
    public Person(String name)
    {
        this.name = name;

        posts = new DSALinkedList();
        followers = new DSALinkedList();
        following = new DSALinkedList();
        likedPosts = new DSALinkedList();
    }

    public void newFollower(Person p)
    {
        followers.insertLast(p);
    }

    public void followed(Person p)
    {
        following.insertLast(p);
    }

    public void statusUpdate(Object post)
    {
        posts.insertLast(post);
    }

    public void setLiked(Object post)
    {
        likedPosts.insertLast(post);
    }

    /* The two methods below set up the garbage collection for 
       unused items inn the linkedlist */
    public void setFollowing(DSALinkedList pFollowing)
    {
        this.following = pFollowing;
    }

    public void setFollowers(DSALinkedList pFollowers)
    {
        this.followers = pFollowers;
    }

    /*Assertion: Person p is a valid object and exists in the graph */
    public void unFollow(Person p)
    {
        if(!following.isEmpty())
        {
            following.remove(p);
        }
        else
        {
            throw new IllegalArgumentException( name+" is not following anyone");
        }
    }

    /*Assertion: Person p is a valid object and exists in the graph */
    public void unFollowed(Person p)
    {

        if(!followers.isEmpty())
        {
            followers.remove(p);
        }
        else
        {
            throw new IllegalArgumentException(p.getName()+ " is not being followed by + "+name);
        }
    }

    public int getPopularity()
    {
        return followers.size();
    }

    public int usersFollowed()
    {
        return following.size();
    }

    public DSALinkedList getPosts()
    {
        return this.posts;
    }

    public int totalPosts()
    {
        return posts.size();
    }

    public DSALinkedList getFollowers()
    {
        return followers;
    }

    public DSALinkedList getFollowing()
    {
        return following;
    }

    public DSALinkedList getPostsLiked()
    {
        return likedPosts;
    }

    public String getName()
    {
        return this.name;
    }

    public String toString()
    {
        return ("User: " + name + ", Following "+ usersFollowed() + " others, " + getPopularity() + " current followers, " + "Total posts: " + totalPosts());
    }

}
