/*Do Re Mi network test*/
public class testDRM
{
    public static void main(String [] args)
    {
        testEventRead2("NetworkDRM.txt", "EventsDRM2.txt");
        testPostLike("NetworkDRM.txt", "EventsDRM2.txt");
    }

    public static void testPerson(String file,String file2)
    {
        try
        {
            Network n = new Network();
            eventFileReader.readFile(file,n);
            eventFileReader.readFile(file2,n);
            try
            {
                String person = n.get("Do").toString();
                if(person.equals("Do"))
                {
                    System.out.println("TEST PERSON RETRIEVAL PASSED FOR: " + person  + " == " + "Do");
                }
                else
                {
                    System.out.println("TEST FAILED FOR PERSON RETRIEVAL: EXPECTED" + "Do, actual" + person); 
                }
            }
            catch(Exception ex)
            {
                System.out.println("TEST FAILED FOR METHOD: get()");
            }
        }
        catch(Exception e)
        {
            System.out.println("TEST FAILED CREATING NETWORK: ");
        }
    }

    public static void testTimeStep(String file,String file2)
    {
        Network n = new Network();
        eventFileReader.readFile(file,n);
        eventFileReader.readFile(file2,n);
        try
        {
            n.timeStep(100,100);
            Person p = n.get("Mi");
            if(p.getName().equals("Mi"))
            {
                System.out.println("TEST PASSED POST TIME STEP get() FOR: " + p.getName());
                System.out.println("EXPECTED: " + "Mi" + " RESULT: " + p.getName());
            }
            else
            {
                System.out.println("TEST FAILED FOR POST TIME STEP get() FOR " + "Mi");
                System.out.println("EXPECT: " + "Mi" + " RESULT: " + p.getName());
            }
        }
        catch(Exception e)
        {
            System.out.println("TEST FAILED CREATING NETWORK FOR TEST TIME STEP");
        }
    }

    public static void testEventRead2(String file,String file2)
    {
        try
        {
            Network n = new Network();
            eventFileReader.readFile(file,n);
            eventFileReader.readFile(file2,n);
            try
            {
                n.timeStep(100,100);
                Person p = n.get("Do");
                System.out.println(p.toString());
                if(p.getFollowers().size() != 7)
                {
                    System.out.println("TEST TIME STEP FAILED FOR: " + p.getName() + " EXPECTED FOLLWOERS = 7");
                }
                else
                {
                    System.out.println("TEST PASSED FOR: " + p.toString());
                    System.out.println("EXPECTED FOLLOWERS: " + 7 + " RESULT " + p.getFollowers().size());
                }
            }
            catch(Exception ex)
            {
                System.out.println("TEST FAILED FOR TIME STEP");
            }
        }
        catch(Exception e)
        {
            System.out.println("TEST FAILED CREATING NETWORK");
        }
    }

    public static void testPostLike(String file,String file2)
    {
        try
        {
            Network n = new Network();
            eventFileReader.readFile(file,n);
            eventFileReader.readFile(file2,n);
            try
            {
                n.timeStep(100,100);
                Person p = n.get("Do");
                if(p.getName().equals("Do"))
                {
                    if(!p.getPosts().isEmpty() && p.getPosts().size() == 1)
                    {
                        System.out.println("TEST PASSED FOR POST COUNT FOR:" + p.getName());
                    }
                    else
                    {
                        System.out.println("TEST FAILED FOR POST COUNT FOR: " + p.getName());
                        System.out.println("EXPECT POST COUNT 1");
                        throw new IllegalArgumentException();
                    }
                }

            }
            catch(IllegalArgumentException e)
            {
                System.out.println("TEST FAILED");
                throw new IllegalArgumentException();
            }
        }
        catch(Exception e)
        {
            System.out.println("TEST FAILED FOR CREATING NETWORK");
        }
    }
}
