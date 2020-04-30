import java.util.*;

public class Connection
{
    public static void main(String[] args)
    {
        NEAT.main(args);
    }

    Node from_node;
    Node to_node;
    double weight;
    boolean enabled;
    int innovation_number;

    public Connection(Node from_node, Node to_node, double weight, int innovation_number)
    {
        this.from_node = from_node;
        this.to_node = to_node;
        this.weight = weight;
        this.innovation_number = innovation_number;

        this.enabled = true;
    }

    void mutate()
    {
        weight += Math.random() / 50;

        if(weight > 1) weight = 1;
        if(weight < -1) weight = -1;
    }

    Connection clone(Node from_node, Node to_node)
    {
        Connection connection = new Connection(from_node, to_node, this.weight, this.innovation_number);
        connection.enabled = this.enabled;

        return connection;
    }
}