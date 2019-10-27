
/*Driver method to run the program for Social Simulator */
import java.util.*;
import java.util.NoSuchElementException;

public class SocialSim
{
    public static void main(String[]args)
    {

        ui userInterface = new ui();
        try
        {
            //Interactive move is supplied with "-i" string
            if(args.length == 1 && args[0].equals("-i"))
            {
                System.out.println("Selected interactive mode\n");
                userInterface.menuSelect();
            }

            /*When supplied with -s string*/
            else if(args.length == 5 && args[0].equals("-s"))
            {
                int prob_foll = 0;
                int prob_like = 0;
                try
                {
                    //Read probability from the given argument
                    //parse into integer probabilities
                    prob_foll = Integer.parseInt(args[3]);
                    prob_like = Integer.parseInt(args[4]);
                    System.out.println(prob_foll + " " + prob_like);
                    Network n = new Network();
                    //Read the file and store event and network to the network
                    eventFileReader.readFile(args[1],n);
                    eventFileReader.readFile(args[2],n);
                    //Results save to file
                    System.out.println("Enter file name");
                    String file = userInterface.fileInput();
                    //Time step caller for simulation mode
                    n.simulationTimeStep(file,prob_like,prob_foll);
                    n.listPost();
                    n.listUser();
                }
                catch(NumberFormatException ex)
                {
                    System.out.println("Invalid set probabilities");
                }
            }
            //When user gives invalid arguments
            else 
            {
                System.out.println("Usage:");
                System.out.println("Interactive Mode: SocialSim -i");
                System.out.println("Simulation Mode: SocialSim -s netfile eventfile prob-like prob-foll");
            }
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Forced exit.");
            e.printStackTrace();
        }
    }
}
