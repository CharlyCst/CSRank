package main;

public class Main {
    static int nbBot = 4;
    static int nbExplorationsPerBot = 200;
//    static String baseUrl="http://mythicspoiler.com/";
//    static String regex="http.+mythic.*";

//    static String baseUrl = "http://www.polytechnique.edu/";
//    static String regex = "http.+polytechnique.*";

//    static String baseUrl="http://www.centralesupelec.fr/";
//    static String regex = "http.+centralesupelec.*";

    static String baseUrl="https://www.insa-lyon.fr/";
    static String regex = "http.+insa-lyon.*";


    public static void main(String[] args) throws InterruptedException {

        long t = System.nanoTime();
        long tExploration;
        long tWalkers;
        long tCSComputation;

        Concurrent_WebGraph web = new Concurrent_WebGraph();
        /*Bot[] bot = new Bot[nbBot];
        for (int i = 0; i < nbBot; i++) {
            bot[i] = new Bot(web);
            bot[i].start();
        }

        Displayer disp = new Displayer(web);
        disp.start();
        for (int i = 0; i < nbBot; i++) {
            bot[i].join();
        }*/

//        Displayer disp = new Displayer(web);
//        disp.start();

        //Exploration du graphe
        tExploration=System.nanoTime();
        ExplorationManager em = new ExplorationManager(web, baseUrl, regex);
        em.startExploration(nbBot, 4);
        tExploration=(System.nanoTime()-tExploration)/ 1000000000;


        // Détermination du PageRank.
        tWalkers=System.nanoTime();
        RandomWalker[] walkers = new RandomWalker[nbBot];
        for (int i = 0; i < nbBot; i++) {
            walkers[i] = new RandomWalker(web, nbExplorationsPerBot);
            walkers[i].start();
        }
        for (int i = 0; i < nbBot; i++) {
            walkers[i].join();
        }
        tWalkers=(System.nanoTime()-tWalkers)/ 1000000000;


        //Computing CSRank
        tCSComputation=System.nanoTime();
        web.computeCSRank();
        tCSComputation=(System.nanoTime()-tCSComputation)/ 1000000000;

        t = (System.nanoTime() - t) / 1000000000;

        System.out.println("Done in " + t + " s"
        +"\nSpending:\n"
        +tExploration+" s in exploration\n"
        +tWalkers+" s in random walks\n"
        +tCSComputation+" s in CS Rank computation\n"
        );

    }

}