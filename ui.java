import java.util.*;

public class ui
{
    private Network socialSim;
    private int prob_foll;
    private int prob_like;

    public ui()
    {
        socialSim = new Network();
        prob_foll = 35;
        prob_like = 35;
    }

    public void menuSelect()
    {
        boolean exit = true;
        while(exit)
        {
            exit = interactionMode();
        }
    }

    private boolean interactionMode()
    {
        int select = 0;
        boolean exit = true;
        System.out.println("\nDefault probabilities");
        System.out.println("Following: " + prob_foll);
        System.out.println("Liking a post " + prob_like);
        menuChoice();
        select = input();
        switch(select)
        {
            case 1:
                loadNetwork();
                break;

            case 2:
                setProbabilities();
                break;

            case 3:
                nodeOperations();
                break;

            case 4:
                edgeOperations();
                break;

            case 5:
                newPost();
                break;

            case 6:
                displayNetwork();
                break;

            case 7:
                displayStats();
                break;

            case 8:
                update();
                break;

            case 9:
                save();
                break;

            case 10:
                System.out.println("Exited wowwowowo");
                exit = false;
                break;

            default:
                System.out.println("No option for recent input");
        }
        return exit;
    }

    public int input()
    {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        try
        {
            input = sc.nextInt();
        }

        catch(InputMismatchException e)
        {
        }
        return input;
    }

    /*public char modeChoice()
    {
        Scanner sc = new Scanner(System.in);
        char c = ' ';
        try
        {
            c = sc.nextLine().charAt(0);
        }
        catch(InputMismatchException e)
        {
        }
        return c;
    }*/

    public String fileInput()
    {
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();
        return file;
    }

    public char choiceInput()
    {
        Scanner sc = new Scanner(System.in);
        char choice = sc.nextLine().charAt(0);

        return choice;
    }

    public String stringInput()
    {
        Scanner sc = new Scanner(System.in);
        String person = sc.nextLine();
        return person;
    }

    public void loadNetwork()
    {
        System.out.print("Enter file name: ");
        String file = fileInput();
        try
        {
            eventFileReader.readFile(file, socialSim);
        }
        catch(IllegalArgumentException e)
        {
            System.out.print("File failed to load");
        }
    }

    public void setProbabilities()
    {
        System.out.println("Changing probabilities");
        System.out.print("Enter like probability: ");
        int prob = input();
        System.out.print("Enter follow probability: ");
        int pLike = input();
        if(prob < 0 && pLike < 0)
        {
            prob_foll = 0;
            prob_like = 0;
        }
        else
        {
            System.out.println("Probability of following: " + pLike + "%");
            System.out.println("Probability of liking: " + prob + "%");
            System.out.println("Run a time step to see results");
            prob_foll = pLike;
            prob_like = prob;
        }
    }

    public void nodeOperations()
    {
        int operation = 0;
        System.out.println("1. Find");
        System.out.println("2. Make");
        System.out.println("3. Delete");
        operation = input();
        switch(operation)
        {
            case 1:
                findUser();
                break;
            case 2:
                newUser();
                break;
            case 3:
                deleteOperation();
                break;
            default:
                System.out.println("Invalid selection...");
        }
    }

    public void edgeOperations()
    {
        int operation = 0;
        System.out.println("1. find");
        System.out.println("2. make/follow");
        System.out.println("3. remove/unfollow");
        operation = input();
        switch(operation)
        {
            case 1:
                break;
            case 2:
                addEdgeOperation();
                break;
            case 3:
                deleteEdgeOperation();
                break;
            default:
                System.out.println("Invalid selection...");
        }
    }

    public void newPost()
    {
        System.out.println("Who made a post?");
        String person = stringInput();
        try
        {
            String message;
            System.out.println(socialSim.find(person));
            System.out.println("What does the message say?");
            message = stringInput();
            if(!message.trim().equals(""))
            {
                socialSim.addPost(person, message);
            }
            else
            {
                System.out.println("You cannot post empty messages");
            }
        }
        catch(Exception e)
        {
            System.out.print("User " + person + " does not exist.\n");
        }
    }

    public void displayStats()
    {
        socialSim.display();
    }

    public void displayNetwork()
    {
        socialSim.listUser();
    }

    public void update()
    {
        System.out.println("Running a time-step");
        System.out.println("Set probabilities: " + prob_foll + "% following "
                                                     + prob_like + " % like" );
        long start = System.currentTimeMillis();
        socialSim.timeStep(prob_like, prob_foll);
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("Operation executed: " + elapsed + "ms");
    }

    //Save network to a file
    public void save()
    {
        try
        {
            System.out.print("Enter file to save to: ");
            String file = fileInput();
            eventFileReader.saveFile(file,socialSim);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Error occured: " + Error.EMPTY);
        }
    }

    /*Creates an edge/follow between user1 and user2*/
    private void addEdgeOperation()
    {
        char choice;
        String name;
        String name2;
        /*User inputs for entering user names of the people we want to make a
         * connection to*/
        System.out.print("Enter name to make connection: ");
        name = stringInput();
        System.out.print("Enter second User: ");
        name2 = stringInput();
        /*Check if both users exist within the graph as get a null user
         * throws exception*/
        if(socialSim.userExist(name) && socialSim.userExist(name2))
        {
            System.out.print(name + " will follow " + name2 + "confirm? (y/n) ");
            choice = choiceInput();
            if(choice == 'y' || choice == 'Y')
            {
                try
                {
                    //Connection will occur and the edges will be created for the follow
                    socialSim.newConnection(name,name2);
                    System.out.println("Follow set: " + name + ":" + name2);
                }
                //Exception is caught for when the link already exists
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                System.out.println("Follow set did not occur");
            }
        }
        else
        {
            System.out.println("Error: " + name + "/" + name2 + "may not exist");
        }
    }

    /*Finds user/vertex in the network, optional to display the user followers*/
    private void findUser()
    {
        char choice;
        String name;
        System.out.print("Enter Username: " );
        name = stringInput();
        try
        {
            //retrieve the person class from the the hashtable, vai hash indexing
            Person p = socialSim.get(name);
            System.out.println(p.toString());
            //optional display followers of the Vertex
            System.out.print("List followers? (y/n) ");
            choice = choiceInput();
            if(choice == 'Y' || choice == 'y')
            {
                socialSim.listFollowers(p);
            }
        }
        //Exception caught for when user does not exist
        catch(IllegalArgumentException e)
        {
            System.out.println("User " + name + " does not exist.");
        }
    }

    /*Method for creating new user*/
    private void newUser()
    {
        char choice;
        String name;
        System.out.print("Enter Username: ");
        name = stringInput();
        boolean confirm = true;
        while(confirm)
        {
            //Make user confirm y/n for adding the name to the network
            System.out.println(name + " will be the name.\nConfirm? (y/n)");
            choice = choiceInput();
            if(choice == 'n' || choice == 'N')
            {
                System.out.print("Enter Username: ");
                name = stringInput();
            }
            else
            {
                confirm = false;
            }
        }
        //Add user to network, it will fail if the user already exists
        try
        {
            socialSim.addUser(name);
            System.out.println(name + " Is added into the network");    
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("User: " + name + " already exists");
        }
    }

    /*Method for deleting users in the network*/
    private void deleteOperation()
    {
        char choice;
        String name;
        System.out.print("Who to delete from the network? ");
        name = stringInput();
        boolean confirm = true;
        try
        {
            //Find and Print out the user name, will throw an exception if the user does not 
            //exist
            System.out.println(socialSim.find(name));
            System.out.println("Removing " + name + " from the network" 
                                    + " are you sure? (y/n)");
            choice = choiceInput();
            //upon confirmation the user name will be deleted from the vertex 
            if(choice == 'y' || choice == 'Y')
            {
                socialSim.removeUser(name);
                System.out.println("Deleted user: " + name);
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Couldn't find user: " + name);
        }
    }

    /*Removes the links/follow between user1 and user2 in the graph*/
    private void deleteEdgeOperation()
    {
        char choice;
        String name;
        String name2;
        System.out.print("Enter user link 1: ");
        name = stringInput();
        System.out.print("Enter user link 2: ");
        name2 = stringInput();
        try
        {
            System.out.println(socialSim.find(name));
            System.out.println(socialSim.find(name2));
            System.out.println("Are you sure you want to delete edge? (y/n)");
            choice = choiceInput();
            //Upon confirmation the user links will be removed, name will unfollow name2
            if(choice == 'y' || choice == 'Y')
            {
                socialSim.removeConnection(name,name2);
            }
            else
            {
                System.out.println("Failed to delete edge");
            }
        }
        //Exception is caught for when either users do not exist in the Network
        catch(IllegalArgumentException e)
        {
            System.out.println("Cannot find users in the network");
        }
    }

    //Displays the menu choices for the main menu screen
    private void menuChoice()
    {
        System.out.println("\n(1) Load Network");
        System.out.println("(2) Set probabilities");
        System.out.println("(3) Node operations");
        System.out.println("(4) Edge operations");
        System.out.println("(5) New post");
        System.out.println("(6) Display Network");
        System.out.println("(7) Display Statistics");
        System.out.println("(8) Update");
        System.out.println("(9) Save");
        System.out.println("(10) Exit");
    }
}
