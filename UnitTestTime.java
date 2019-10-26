public class UnitTestTime
{
    public static void main (String args[])
    {
        long toySAvg=0;
        long doReMiAvg=0;
        long darkCrystalAvg=0;
        long toyStory[] = new long[1000];
        long doReMi[] = new long[1000];
        long darkCrystal[] = new long[1000];
        for(int i=0;i<1000;i++)
        {
            toyStory[i] = testToyStory();
            doReMi[i] = testDoReMi();
            darkCrystal[i] = testDarkCrystal();
            toySAvg+=toyStory[i];
            doReMiAvg+=doReMi[i];
            darkCrystalAvg+=darkCrystal[i];
        }
        toySAvg = toySAvg/1000;
        doReMiAvg= doReMiAvg/1000;
        darkCrystalAvg= darkCrystalAvg/1000;
        System.out.println("RESULTS");
        System.out.println("AVERAGE RESULT FOR TOY STORY NETWORK: " + toySAvg + "ns");
        System.out.println("AVERAGE RESULT FOR DOREMI NETWORK " + doReMiAvg  + "ns");
        System.out.println("AVERAGE RESULT FOR DARK CRYSTAL NETWORK " + darkCrystalAvg + "ns");
    }

    public static long testToyStory()
    {
        Network n = new Network();
        eventFileReader.readFile("networkTS1b.txt",n);
        eventFileReader.readFile("eventsTS1b.txt",n);
        long start = System.nanoTime();
        n.timeStep(100,100);
        long end = System.nanoTime();
        long elapsed = end - start;
        return elapsed;
    }

    public static long testDoReMi()
    {
        Network n = new Network();
        eventFileReader.readFile("NetworkDRM.txt",n);
        eventFileReader.readFile("EventsDRM2.txt",n);
        long start = System.nanoTime();
        n.timeStep(100,100);
        long end = System.nanoTime();
        long elapsed = end - start;
        return elapsed;
    }

    public static long testDarkCrystal()
    {
        Network n = new Network();
        eventFileReader.readFile("network1.txt",n);
        eventFileReader.readFile("events1.txt",n);
        long start = System.nanoTime();
        n.timeStep(100,100);
        long end = System.nanoTime();
        long elapsed = end - start;
        return elapsed;
    }
}
