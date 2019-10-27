
public class UnitTestNetwork
{
    public static void main (String [] args)
    {
        testSimulation("test.txt", 100, 100);
        /*try
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

        /*try
        {
            testRandom(10, 25);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/

        /*try
        {
            testDelete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
        //test(100,100);
//        testOrder();
       // testOrder2();
        //testTimeStep(100, 100);
        long result = runTimeTest(100,100);
        System.out.println("TIMED RESULT FOR TIME STEP TOY STORY NETWORK: "+ result);
    }


    public static long runTimeTest(int k,int m)
    {
        long start = System.nanoTime();
        test(k, m);
        long end = System.nanoTime();
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
        catch(Exception e)
        {

            System.out.println("Test passed, Kira was deleted");
            n.timeStep(100,100);
            n.listUser();
            n.display();
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
            //n.removeUser("Jen");
            //n.update();
            n.listUser();
            n.listPost();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void testOrder2()
    {
        Network n = new Network();
        eventFileReader.readFile("network1.txt", n);
        eventFileReader.readFile("events1.txt", n);
        try
        {
            DSAQueue q = n.timeStep(100, 100);
            //n.removeUser("Jen");
            n.update();
            n.listPost();
            n.listUser();
            n.display();
            eventFileReader.eventToFile("result.txt", n, q);
            try
            {
                System.out.println(n.find("Jen"));
                System.out.println("Test passed");
            }
            catch(Exception ex)
            {
                //System.out.println("Test passed");
                //ex.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void testTimeStep(int k, int m)
    {
        Network n = new Network();
        eventFileReader.readFile("network1.txt", n);
        eventFileReader.readFile("events1.txt", n);
        try
        {
           // n.listUser();
           //n.removeUser("Jen");
          // n.removeUser("Kira");

            n.timeStep(k, m);
          //  System.out.println("\n");
            n.listUser();
            n.listPost();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void testSimulation(String file, int k, int m)
    {
        Network n = new Network();
        eventFileReader.readFile("networkTS1b.txt", n);
        eventFileReader.readFile("eventsTS1b.txt", n);
        try
        {
            //n.simulationTimeStep(file, k, m);
            //n.timeStep(k, m);
            //n.removeUser("Kira");
            //n.listUser();
            //n.listPost();
            //n.addPost("Aughra", "Hello world!");
            //n.simulationTimeStep("result2", k, m);
            //n.display();
            //n.listPost();
            n.simulationTimeStep(file, k, m);
            n.timeStep(k, m);
            n.listPost();
            n.listUser();
            n.displayFollowers("Buzz Lightyear");

        }
        catch(Exception e)
        {
            e.printStackTrace();
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
