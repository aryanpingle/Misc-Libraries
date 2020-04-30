import java.util.*;

public class Participant
{
    public static void main(String[] args)
    {
        NEAT.main(args);
    }

    double fitness;
    Genome brain;
    double[] inputs;
    int input_size;
    double[] outputs;
    int output_size;

    int gen;

    double fitness;

    public Participant(int input_size, int output_size)
    {
        fitness = 0;
        this.input_size = input_size;
        this.output_size = output_size;
        brain = new Genome(input_size, output_size, false);

        gen = 1;

        fitness = 0;
    }

    void think()
    {
        inputs = new double[]{0, 1};
        outputs = brain.feedForward(inputs);
    }

    void think(double[] inputs)
    {
        this.inputs = inputs.clone();
        outputs = brain.feedForward(inputs);
    }

    void calculateFitness()
    {
        if()
    }
}