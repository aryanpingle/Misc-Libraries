import java.util.*;

public class Node
{
    public static void main(String[] args)
    {
        // NEAT.main(args);
        p.ln(new Node(1).sigmoid(0.548039-0.30626));
    }

    int index;
    double inputsum;
    double outputvalue;
    int layer;
    LinkedList<Connection> output_connections;

    public Node(int index)
    {
        this.index = index;
        inputsum = 0;
        outputvalue = 0;
        layer = 0;
        output_connections = new LinkedList<Connection>();
    }

    /**
     * Passes the outputsum to the connected nodes
     */

    void engage()
    {
        if(layer != 0)
        {
            outputvalue = sigmoid(inputsum);
        }

        for(Connection connection: output_connections)
        {
            if(connection.enabled)
            {
                connection.to_node.inputsum += connection.weight*outputvalue;
            }
        }
    }

    double sigmoid(double val)
    {
        return 1.0/(1+Math.exp(-val));
    }

    boolean isConnectedTo(Node node)
    {
        if(layer == node.layer || layer >= node.layer+2 || node.layer >= layer+2)
        {
            return false;
        }

        if(node.layer < layer) // node is below the current object
        {
            for(Connection connection: node.output_connections)
            {
                if(connection.to_node.equals(this))
                {
                    return true;
                }
            }
        }
        else if(node.layer > layer)
        {
            for(Connection connection: output_connections)
            {
                if(connection.to_node.equals(node))
                {
                    return true;
                }
                return false;
            }
        }

        return false;
    }

    public Node clone()
    {
        Node node = new Node(this.layer);
        node.layer = this.layer;
        return node;
    }
}