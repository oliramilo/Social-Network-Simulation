import java.util.NoSuchElementException;

public class SocialSim
{
    public static void main(String[]args)
    {
        try
        {
            ui userInterface = new ui();
            if(args.length == 2 && args[1].equals("-i"))
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
        }

    }
}
