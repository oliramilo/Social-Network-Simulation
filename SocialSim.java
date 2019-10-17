
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
            if(args.length == 1 && args[0].equals("-i"))
            {
                System.out.println("Selected interactive mode\n");
                userInterface.menuSelect();
            }
            else if(args.length == 5)
            {
                int prob_foll = 0;
                int prob_like = 0;
                try
                {
                    prob_foll = Integer.parseInt(args[3]);
                    prob_like = Integer.parseInt(args[4]);
                    System.out.println(prob_foll + " " + prob_like);
                    Network n = new Network();
                    eventFileReader.readFile(args[1],n);
                    eventFileReader.readFile(args[2],n);
                    System.out.println("Enter file name");
                    String file = userInterface.fileInput();
                    n.simulationTimeStep(file,prob_like,prob_foll);
                    n.listPost();
                    n.listUser();
                }
                catch(NumberFormatException ex)
                {
                    System.out.println("Invalid set probabilities");
                }
            }
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
