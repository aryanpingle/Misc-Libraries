import java.util.*;

public class NEAT
{
    public static void main(String[] args)
    {
        Population.init(1);
        p.ln("------------------------------\nPrinting people:");
        Population.display();
        p.ln("------------------------------\nBest fitness:");
        Population.getBestFitness();
        for(int i = 0; i < 5; i++)
        {
            p.ln("------------------------------\nCHANGING:");
            Population.change();
            p.ln("------------------------------\nPrinting people:");
            Population.display();
            p.ln("------------------------------\nBest fitness:");
            Population.getBestFitness();
        }
    }
}