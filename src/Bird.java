/**
 * This class is used to represent a Bird.
 * The strategy of the bird is represented by an enum that also
 * has a toString() for better printing.
 * The boolean dead is to know if the indivual is alive or not.
 * The comparator descending is uses to sort the Birds by resource.
 * @author Danilo Sosa (dgs5678)
 * */

import java.util.Comparator;

public class Bird {
    enum Strategy{
        DOVE {
            @Override
            public String toString() {
                return "Dove";
            }
        },
        HAWK{
            @Override
            public String toString() {
                return "Hawk";
            }
        }
    }
    private Strategy strat;
    private int res;
    private boolean dead;

    public Bird() {
    }

    public Bird(Strategy strat) {
        this.strat = strat;
        this.res = 0;
        this.dead = false;
    }

    public Strategy getStrat() {
        return strat;
    }

    public int getRes() {
        return res;
    }

    public boolean isDead() {
        return dead;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public static class descending implements Comparator<Bird>{
        public int compare(Bird a, Bird b) {return b.getRes()-a.getRes();}
    }
}
