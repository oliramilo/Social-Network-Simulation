import java.io.*;

public class eventFileReader
{
    public static void readFile(String file, Network n)
    {
        FileInputStream strm = null;
        InputStreamReader rdr = null;
        BufferedReader bfr = null;
        String line = null;

        try
        {
            strm = new FileInputStream(file);
            rdr = new InputStreamReader(strm);
            bfr = new BufferedReader(rdr);
            line = bfr.readLine();
            int count = 1;

            while(line != null)
            {
                try
                {
                    lineProcessor(line, n);
                }
                catch(IllegalArgumentException ex)
                {
                }
                line = bfr.readLine();
                count++;
            }
        }
        catch(IOException e)
        {
            System.out.println("Error occurred: " + Error.NO_FILE);
        }
    }

    public static void lineProcessor(String line, Network n)
    {
        String s[] = line.split(":");
        if(s.length == 1)
        {
            s[0] = s[0].trim();
            n.addUser(s[0]);
        }

        else if(s[0].length() == 1 && s[0].charAt(0) == 'A')
        {
                s[1] = s[1].trim();
                n.addUser(s[1]);
        }

        else if (s.length == 2)
        {
            s[0] = s[0].trim();
            s[1] = s[1].trim();
            if(!s[0].equals("") && !s[1].equals(""))
            {
                n.newConnection(s[1],s[0]);
            }
            else
            {
                System.out.println("User is blank...");
            }
        }
        else if(s.length == 3)
        {
            char action = s[0].charAt(0);
            s[1] = s[1].trim();
            s[2] = s[2].trim();
            event(action,s[1],s[2],n);
        }
        else if(s.length == 4)
        {
            try
            {
                int numLikes = Integer.parseInt(s[3]);
                s[1] = s[1].trim();
                s[2] = s[2].trim();
                n.readPost(s[1],s[2],numLikes);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Argument at supplied, is not a number");
            }
        }
        else 
        {
            throw new IllegalArgumentException("Error occurred: " + Error.FILE_ERR);
        }
    }

    //In such an event for when the program requires to process 3 strings
    //this method is called for event files
    public static void event(char action, String firstAction, String secondAction, Network n)
    {
        switch(action)
        {
            case 'F':
                n.newConnection(secondAction,firstAction);
                break;
            case 'P':
                n.addPost(firstAction,secondAction);
                break;
            case 'U':
                n.removeConnection(firstAction, secondAction);
                break;
            default:
                throw new IllegalArgumentException("Error occurred: " + Error.FILE_ERR);
        }
    }


    public static void eventToFile(String file, Network n, DSAQueue events)
    {
        DSAQueue timeStepEvent = null;
        if(!events.isEmpty())
        {
            FileOutputStream outStrm;
            PrintWriter pw = null;
            try
            {
                outStrm = new FileOutputStream(file);
                pw = new PrintWriter(outStrm);
                for(Object o: events)
                {
                    timeStepEvent = (DSAQueue)o;
                    for(Object iter:timeStepEvent)
                    {
                        pw.println(String.valueOf(iter));
                    }
                }
                pw.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Nothing happened");
        }
    }

    public static void saveFile(String file, Network n)
    {
        FileOutputStream outStrm = null;
        PrintWriter writer = null;
        try
        {
            outStrm = new FileOutputStream(file);
            writer = new PrintWriter(file);
            for(Object iter:n.networkContents())
            {
                writer.println(String.valueOf(iter));
            }
            outStrm.close();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
