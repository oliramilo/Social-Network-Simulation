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
        interactionMode();
    }

    public void usage()
    {
        System.out.println("Usage: ");
    }

    public void interactionMode()
    {
        int select = 0;
        boolean exit = false;
        while(!exit)
        {
            System.out.println("Default probabilities");
            System.out.println("Following: " + prob_foll);
            System.out.println("Liking a post" + prob_like);
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
                    exit = true;
                    break;

                default:
                    System.out.println("No option for recent input");
                    break;
            }
        }
    }


    public void simulationMode()
    {

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
        sc.close();
        return input;
    }

    public char modeChoice()
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
        sc.close();
        return c;
    }

    public String fileInput()
    {
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();
        sc.close();
        return file;
    }

    public String stringInput()
    {
        Scanner sc = new Scanner(System.in);
        String person = sc.nextLine();
        sc.close();
        return person;
    }

    public void loadNetwork()
    {
        System.out.print("Enter file name: ");
        String file = fileInput();
        try
        {
            eventFileReader.readFile(file, socialSim);
            System.out.println("File loaded");
        }
        catch(IllegalArgumentException e)
        {
            System.out.print("File failed to load");
        }
    }

    public void setProbabilities()
    {
        System.out.println("Changing probabilities");
        int prob = input();
        int pLike = input();
        if(prob < 0 && pLike < 0)
        {
            prob_foll = 0;
            prob_like = 0;
        }
        else
        {
            System.out.println("Probability of following: " + prob + "%d");
            System.out.println("Probability of liking: " + pLike + "%d");
            System.out.println("Run a time step to see results");
            prob_foll = prob;
            prob_like = pLike;
        }
    }

    public void nodeOperations()
    {
        System.out.println("1. Find\n");
    }

    public void edgeOperations()
    {
        System.out.println("edge operation not yet implemented");
    }

    public void newPost()
    {
        System.out.println("Who made a post?");
        String person = stringInput();
        try
        {
            String message;
            System.out.println(socialSim.find(person));
            System.out.print("What does the message say?");
            message = stringInput();
            socialSim.addPost(person, message);
        }
        catch(Exception e)
        {
            System.out.print("User " + person + " does not exist.\n");
        }
    }

    public void displayNetwork()
    {
        System.out.println("display network not yet implemented");
    }

    public void displayStats()
    {
        System.out.println("display stats not yet implemented");
    }

    public void update()
    {
        System.out.println("Running a time-step");
        System.out.println("Set probabilities: " + prob_foll + "% following" + prob_like + "% like" );
        socialSim.timeStep(prob_like, prob_foll);
    }

    public void save()
    {
        System.out.println("save not yet implemented");
    }

    public void menuChoice()
    {
        System.out.println("(1) Load Network");
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
