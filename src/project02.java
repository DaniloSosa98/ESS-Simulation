/**
 * project02 is the main program in which inputs are managed either through arguments
 * and standard input. After getting the necessary arguments the user is presented
 * with a menu with various options for the simulation. The menu will be in a loop
 * until the user decides to quit.
 * @author Danilo Sosa (dgs5678)
 * */

import java.util.*;

public class project02 {
    //global variables to maintain track of alive individuals and encounters
    public static int alive;
    public static int encounter;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int argSize = args.length;
        //Check if the required argument is available and make sure a max of 4 arguments were entered
        if(argSize==0 || argSize>4){
            System.err.println("Usage: ./project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]");
            System.exit(0);
        }
        //Default values for the simulation
        int popSize = Integer.parseInt(args[0]), pHawks = 20, resAmt = 50, lossHawk = 100;
        //This switch has cases depending on how many arguments were introduced
        switch (argSize){
            case 2:{
                pHawks = Integer.parseInt(args[1]);
                break;
            } case 3:{
                pHawks = Integer.parseInt(args[1]);
                resAmt = Integer.parseInt(args[2]);
                break;
            } case 4:{
                pHawks = Integer.parseInt(args[1]);
                resAmt = Integer.parseInt(args[2]);
                lossHawk = Integer.parseInt(args[3]);
                break;
            } default:{
                break;
            }
        }
        //initialize global variables
        alive = popSize;
        encounter = 0;
        //List that contains the Birds
        List<Bird> pop = new ArrayList<>();
        //Calculate how many hawks in the population
        int hawkSize = (int)Math.floor((popSize*pHawks)/100);
        //fill the list with the corresponding Birds
        for (int i = 0; i < popSize; i++) {
            if(i<hawkSize){
                pop.add(new Bird(Bird.Strategy.HAWK));
            }else{
                pop.add(new Bird(Bird.Strategy.DOVE));
            }
        }
        //variable for the menu option the user inputs
        int opt = 0;
        //Loop that keeps showing the menu until the user decides to quit
        do{
            System.out.print("\n===============MENU=============\n" +
                               "1 ) Starting Stats\n" +
                               "2 ) Display Individuals and Points\n" +
                               "3 ) Display Sorted\n" +
                               "4 ) Have 1000 interactions\n" +
                               "5 ) Have 10000 interactions\n" +
                               "6 ) Have N interactions\n" +
                               "7 ) Step through interactions \"Stop\" to return to menu\n" +
                               "8 ) Quit\n" +
                               "================================\n" +
                               ">");
            opt = Integer.parseInt(sc.nextLine());
            System.out.println();
            //This switch calls the respective functions depending on the user input
            switch(opt){
                case 1:{
                    startStats(popSize, pHawks, hawkSize, resAmt, lossHawk);
                    break;
                } case 2:{
                    indPoints(pop, alive);
                    break;
                } case 3:{
                    sorted(pop);
                    break;
                } case 4:{
                    for (int i = 0; i < 1000; i++) {
                        //check if there are enough alive to simulate
                        if(alive<=1){
                            System.out.println("Too many dead individuals, the simulation cannot continue.");
                            break;
                        }
                        interaction(pop, resAmt, lossHawk);
                    }
                    break;
                } case 5:{
                    for (int i = 0; i < 10000; i++) {
                        //check if there are enough alive to simulate
                        if(alive<=1){
                            System.out.println("Too many dead individuals, the simulation cannot continue.");
                            break;
                        }
                        interaction(pop, resAmt, lossHawk);
                    }
                    break;
                } case 6:{
                    //user input for how many encounters to simulate
                    int n = Integer.parseInt(sc.nextLine());
                    for (int i = 0; i < n; i++) {
                        //check if there are enough alive to simulate
                        if(alive<=1){
                            System.out.println("Too many dead individuals, the simulation cannot continue.");
                            break;
                        }
                        interaction(pop, resAmt, lossHawk);
                    }
                    break;
                } case 7:{
                    String s = "";
                    //loop that breaks if user inputs "Stop"
                    while (true){
                        s = sc.nextLine();
                        if(s.equals("Stop")){
                            break;
                        }
                        //check if there are enough alive to simulate
                        if(alive<=1){
                            System.out.println("Too many dead individuals, the simulation cannot continue.");
                            break;
                        }
                        interaction(pop, resAmt, lossHawk);
                    }
                    break;
                } default:{
                    break;
                }
            }
        }while(opt!=8);
    }

    /**
     * This function prints the stats of the population
     * @param popSize
     * @param pHawks
     * @param hawkSize
     * @param res
     * @param lossHawk
     */
    public static void startStats(int popSize, int pHawks, int hawkSize, int res, int lossHawk){
        System.out.println("Population size: " + popSize +"\n" +
                           "Percentage of Hawks: " + pHawks + "%\n" +
                           "Number of Hawks: " + hawkSize + "\n\n" +
                           "Percentage of Doves: " + (100-pHawks) + "%\n" +
                           "Number of Doves: " + (popSize-hawkSize) + "\n\n" +
                           "Each resource is worth: " + res + "\n" +
                           "Cost of Hawk-Hawk interaction: " + lossHawk);
    }

    /**
     * This function goes through the list from to 0 to size and print the index
     * of the individual, the strategy or if it is dead and the current resources.
     * @param pop
     * @param alive
     */
    public static void indPoints(List<Bird> pop, int alive){
        for (int i = 0; i < pop.size(); i++) {
            if(pop.get(i).isDead()){
                System.out.println(
                        "Individual["+i+"]=DEAD:"+pop.get(i).getRes());
            }else{
                System.out.println(
                        "Individual["+i+"]="+pop.get(i).getStrat().toString()+":"+pop.get(i).getRes());
            }
        }
        System.out.println("Living: "+alive);
    }

    /**
     * This function sorts the population in descending order of resources, it does it with a copy
     * so the original list is not changed. Then the sorted list is printed showing if they are dead
     * or their strategy and their resources
     * @param pop
     */
    public static void sorted(List<Bird> pop){
        List<Bird> copy = new ArrayList<>(pop);
        Collections.sort(copy, new Bird.descending());
        for (Bird b:copy) {
            if (b.isDead()){
                System.out.println("DEAD:"+b.getRes());
            }else {
                System.out.println(b.getStrat() + ":" + b.getRes());
            }
        }
    }

    /**
     * This function represents a single encounter simulation and handles all the
     * respective rules.
     * @param pop
     * @param resAmt
     * @param lossHawk
     */
    public static void interaction(List<Bird> pop, int resAmt, int lossHawk){
        Random rand = new Random();
        int a = -1, b = -1;
        //the loop generates random number in the range of the population size
        //it will keep going if a dead individual is chosen or the 2 numbers are the same
        do{
            a = rand.nextInt(pop.size());
            b = rand.nextInt(pop.size());
        }while(pop.get(a).isDead() || pop.get(b).isDead() || pop.get(a)==pop.get(b));
        //get individuals from the list
        Bird first = pop.get(a), second = pop.get(b);
        //Dove/Dove encounter
        if(first.getStrat().equals(Bird.Strategy.DOVE)
                && second.getStrat().equals(Bird.Strategy.DOVE)){
            //Divide resource by 2 and truncate
            int res  = (int)Math.floor(resAmt/2);
            //doves get half of the resource
            first.setRes(first.getRes()+res);
            second.setRes(second.getRes()+res);
            //update individuals in list
            pop.set(a,first);
            pop.set(b,second);
            //print encounter information
            System.out.println("Encounter: "+(++encounter)+"\n" +
                               "Individual: "+a+": "+first.getStrat().toString()+"\n" +
                               "Individual: "+b+": "+second.getStrat().toString()+"\n" +
                               "Dove/Dove: Dove: +"+res+"\tDove: +"+res+"\n" +
                               "Individual "+a+"="+ first.getRes()+"\tIndividual "+b+"="+second.getRes()+"\n");

        }else if(first.getStrat().equals(Bird.Strategy.HAWK)
                && second.getStrat().equals(Bird.Strategy.DOVE)){
            //Hawk/Dove encounter
            //Hawk gets all resources
            first.setRes(first.getRes()+resAmt);
            //update individuals in list
            pop.set(a,first);
            //print encounter information
            System.out.println("Encounter: "+(++encounter)+"\n" +
                               "Individual: "+a+": "+first.getStrat().toString()+"\n" +
                               "Individual: "+b+": "+second.getStrat().toString()+"\n" +
                               "Hawk/Dove: Hawk: +"+resAmt+"\tDove: +0\n" +
                               "Individual "+a+"="+ first.getRes()+"\tIndividual "+b+"="+second.getRes()+"\n");

        }else if(first.getStrat().equals(Bird.Strategy.DOVE)
                && second.getStrat().equals(Bird.Strategy.HAWK)){
            //Dove/Hawk encounter
            //Hawk gets all resources
            second.setRes(second.getRes()+resAmt);
            //update individuals in list
            pop.set(b,second);
            //print encounter information
            System.out.println("Encounter: "+(++encounter)+"\n" +
                               "Individual: "+a+": "+first.getStrat().toString()+"\n" +
                               "Individual: "+b+": "+second.getStrat().toString()+"\n" +
                               "Dove/Hawk: Dove: +0\tHawk: +"+resAmt+"\n" +
                               "Individual "+a+"="+ first.getRes()+"\tIndividual "+b+"="+second.getRes()+"\n");

        }else if(first.getStrat().equals(Bird.Strategy.HAWK)
                && second.getStrat().equals(Bird.Strategy.HAWK)){
            //Hawk/Hawk encounter
            //first hawk gets all resources but both loose the H-H encounter cost
            first.setRes(first.getRes()+resAmt-lossHawk);
            second.setRes(pop.get(b).getRes()-lossHawk);
            //print encounter information
            System.out.println("Encounter: "+(++encounter)+"\n" +
                    "Individual: "+a+": "+first.getStrat().toString()+"\n" +
                    "Individual: "+b+": "+second.getStrat().toString());
            //Check if resources from encounter end up negative or positive to print correctly
            if(resAmt<lossHawk){
                System.out.println("Hawk/Hawk: Hawk: "+(resAmt-lossHawk)+"\tHawk: "+(lossHawk*-1));
            }else{
                System.out.println("Hawk/Hawk: Hawk: +"+(resAmt-lossHawk)+"\tHawk: "+(lossHawk*-1));
            }
            //Check if any of the hawks died, this happens when their resources end up being less than 0
            if (first.getRes()<0){
                first.setDead(true);
                alive--;
                System.out.println("Hawk one has died!");
            }
            if (second.getRes()<0){
                second.setDead(true);
                alive--;
                System.out.println("Hawk two has died!");
            }
            System.out.println("Individual "+a+"="+ first.getRes()+"\tIndividual "+b+"="+second.getRes()+"\n");
            //update individuals in list
            pop.set(a,first);
            pop.set(b,second);

        }
    }
}
