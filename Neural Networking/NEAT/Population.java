import java.util.*;

public class Population
{
    public static void main(String[] args)
    {
        NEAT.main(args);
    }

    static LinkedList<Participant> list;
    static int size;
    static LinkedList<ConnectionHistory> innovationHistory;

    public static void init(int size)
    {
        Population.size = size;
        list = new LinkedList<Participant>();
        innovationHistory = new LinkedList<ConnectionHistory>();

        for(int i = 0; i < size; ++i)
        {
            list.add(new Participant(2, 1));
            list.get(i).brain.generateNetwork();
        }
    }

    static void change()
    {
        for(Participant person: list)
        {
            person.brain.mutate(innovationHistory);
        }
    }

    static void calculateFitness()
    {
        for(Participant person: list)
        {
            person.calculateFitness();
        }
    }

    static Participant getBestPlayer()
    {
        Participant goat = list.get(0);
        for(Participant person: list)
        {
            if(person.fitness > goat.fitness)
            {
                goat = person;
            }
        }

        return goat;
    }

    static void display()
    {
        for(Participant person: list)
        {
            person.brain.print();
        }
    }

    static void breed()
    {

    }
}