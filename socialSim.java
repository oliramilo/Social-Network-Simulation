import java.util.NoSuchElementException;

public class socialSim
{
    public static void main(String[]args)
    {
        try
        {
            ui userInterface = new ui();
            userInterface.menuSelect();
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Forced exit.");
        }
    }
}
