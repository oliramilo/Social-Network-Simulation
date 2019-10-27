/*Network tester for all the functions, if a function fails to execute, a stack trace is displayed for the error
 * other wise all test passes*/
public class TestNetwork
{
    public static void main (String [] args)
    {
        try
        {
            testSimulation("test.txt", 100, 100);
            try
            {
                testPerson();
                System.out.println("Test passed");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                testNetwork();
                System.out.println("Test passed");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                testRandom(10, 25);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                testDelete();
            }
            catch(Exception e)
            {
            }
        
            testTimeStep(100, 100);
            long testRun[] = new long[100];
            for(int i =0;i<100;i++)
            {
                testRun[i] = runTimeTest(100, 100);
            }
            System.out.println("Testing iterative search");
            double avg=0;
            for(int i=0;i<100;i++)
            {
                avg+=testRun[i];
            }
            System.out.print("Average is " + (avg/100) + "ms");
            System.out.print("Test passed");
        }
        catch(Exception e)
        {
            System.out.println("Test failed");
        }
    }


    public static long runTimeTest(int k,int m)
    {
        long start = System.currentTimeMillis();
        test(k, m);
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        return elapsed;
    }

    public static void testPerson()
    {
        Person p = new Person("Kira");
        System.out.println(p.toString());
    }

    public static void testNetwork()
    {
        Network n = new Network();
        eventFileReader.readFile("network1.txt",n);
        eventFileReader.readFile("events1.txt",n);
        System.out.println(n.find("skekUng"));
        //n.find("Kira");
        System.out.println(n.find("Aughra"));
        //n.test(225);
        System.out.println("Network size is: " + n.userCount());
    }

    public static void testRandom(int times,int prob)
    {
        Network n = new Network();
        for(int i=0;i<times;i++)
        {
            n.probablity(prob);
        }
    }

    public static void testDelete()
    {
        Network n = new Network();
        eventFileReader.readFile("network1.txt", n);
        eventFileReader.readFile("events1.txt", n);
        n.removeUser("Kira");
        try
        {
            System.out.println(n.find("Kira"));
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Test passed, Kira was deleted");        
        }
    }

    public static void testOrder()
    {
        Network n = new Network();
        eventFileReader.readFile("network1.txt", n);
        eventFileReader.readFile("events1.txt", n);
        try
        {
            n.timeStep(100, 100);
            n.removeUser("Jen");
            n.listUser();
            n.listPost();
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Test failed for listing users");
        }
    }

    public static void testTimeStep(int k, int m)
    {
        Network n = new Network();
        eventFileReader.readFile("network1.txt", n);
        eventFileReader.readFile("events1.txt", n);
        try
        {
            n.timeStep(k, m);
            n.listUser();
            n.listPost();
        }
        catch(Exception e)
        {
            System.out.println("Test failed for testTimeStep()");
        }
    }

    public static void testSimulation(String file, int k, int m)
    {
        Network n = new Network();
        eventFileReader.readFile("networkTS1b.txt", n);
        eventFileReader.readFile("eventsTS1b.txt", n);
        try
        { 
            n.simulationTimeStep(file, k, m);
            n.timeStep(k, m);
            n.listPost();
            n.listUser();
            n.displayFollowers("Buzz Lightyear");

        }
        catch(Exception e)
        {
            System.out.println("Test failed for testSimulation()");
        }
    }

    public static void fill(Network n)
    {
        String nameArr[] = {"Daphne", "Velma", "Scooby", "Patrick", "Shaggy",
                             "Fred", "Spongebob","Mr.Krabs"};
        for(int i = 0;i<nameArr.length;i++)
        {
            n.addUser(nameArr[i]);
        }
        eventFileReader.readFile("testEvent.txt", n);   
    }

    public static void test(int k,int m)
    {
        Network n = new Network();
        fill(n);
        eventFileReader.readFile("networkTS1b.txt", n);
        eventFileReader.readFile("eventsTS1b.txt", n);
        n.timeStep(k, m);
        n.display();
    }
}
