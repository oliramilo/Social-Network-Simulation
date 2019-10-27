README

Usage:
Interaction mode: java SocialSim -i
Simulation mode: java SocialSim -s fileNetwork fileEvent prob_like prob_foll

eventFileReader.java:
    reads file networks/events. Each line will be added to the Network
    graph. Delimiters are required in order to create Posts, follows,
    additions and removals of Vertex Persons from the Network. The
    delimeter of these actions are separated by a ":" character
    Key Characters
    'A' - Followed by a non-existing username in the Network, will add the
          user to the Network class
    'F' - Followed by two String usernames that must exist in the Network
          class will create an edge/follow between the second String and
          the firt String, these names are separate by the delimeter ":"
    'U' - Followed by two String username that must exist in the Network class
          will remove the existing edge/set unfollow between the two People
          Vertex
    'R' - Followed by an existing username in the Network, will remove the
          Person Vertex from the Network class
    'P' - Followed by two Strings, the first must be an existing username
          from the Network and the second string is a message.
          Optional to add an integer at the end to represent the click bait
          factor of the post which represents as the multiplier for liking
          this particular message

Network.java:
    Private Inner Class --Post--
    -----------------------------------------------------------------
    Contains the Person Vertex class, linked list of people who like
    the post, String message and click bait factor represented by an
    integer, a Post created without click bait factor supplied to the
    constructor call will have a default factor of 1. Post class
    methods contain the setters/getters. The purpose of this inner
    class is to store Posts in a LinkedList, iterating over them for
    breadth first searching and time steps.
    -----------------------------------------------------------------
    The Network class contains a HashTable of Vertices and LinkedList
    of posts and the count of Users and Posts in the Network.
    add/remove/find functions take in a String in the parameter to
    represent Vertices in the Network. Due to the HashTable value
    indexing, the  function for add/get/find are as close to O(1).

    probability() takes in an integer which represented the probablity,
    returning true/false. The function uses Java's built in Random function
    to generate a random number from 0-100, if the number generated is less
    than or equal to the paramter integer, the function returns true.
    This function returns false other-wise. If an integer greater than 100
    is given, the function automatically is set to return true.
    Another function that performs the same as this takes in 2 integers.
    The first one represents the probability and the second represents a
    multiplier based on the

    breadthFirstSearch() returns a Queue of events stored as strings for
    the likes and follows of a particular post and person that happened
    in the search. The search is triggered by the probability function

    timeStep() takes in 2 integers representing prob_like and prob_foll
    respectively. This function is a nested for-each loop that iterates
    over the list of posts and the followers of the original poster,
    calling a breadthFirstSearch giving all adjacent nodes of the
    original poster a chance to like the post.

Person.java:
    Person class contains the String name of the vertex Person, their followers,
    who they're following and their posts content stored in a linked list.

