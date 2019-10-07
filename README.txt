---------------------------------------------------------------------
Two object classes a made to represent a person in the network
and a post to represent information which can be spread throughout
the network. A person class contains the information of the person
name, their followers, who they're following and the posts they made.
These information are stored in a linked list to keep track of information
the person has without worrying about the size limit.
The Network.java class uses a HashTable and a LinkedList to keep
track of the people in the Network. The combination of these two
data structures results in a time complexity of O(n) to get a Person
object from the Network by name, rather than having to iterate over the
linked list to verify the matching person. The old graph prac had a
Vertex class which contained a boolean to mark the vertex as visited
which is used for graph traversal. With modifications in an improvement of
the old Graph, a HashTable is used to mark each Vertex as labeled in the
Breadth First Search and Depth First Search. The trade off here is that
we save memory for each Vertex class since we don't have to store extra
information of the visited vertices as well as an extra iteration for

