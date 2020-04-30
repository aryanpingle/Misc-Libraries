import java.util.*;

class ConnectionHistory
{
    public static void main(String[] args)
    {
        NEAT.main(args);
    }

    Node to_node;
    Node from_node;
    
    int innovative_connection;
    LinkedList<Integer> innovationNumbers;
    
    public ConnectionHistory(Node from_node, Node to_node, int innovative_connection, LinkedList<Integer> innovationNumbers)
    {
        this.to_node = to_node;
        this.from_node = from_node;
        this.innovative_connection = innovative_connection;

        innovationNumbers = new LinkedList<Integer>();
        for(int i: innovationNumbers)
        {
            this.innovationNumbers.add(i);
        }
    }
    
    /**
    * Test whether the innovation between from_node and to_node was actually done before
    */
    
    public boolean matches(Genome genome, Node from_node, Node to_node)
    {
        if(innovationNumbers == null) return false;
        if(genome.genes.size() == innovationNumbers.size())
        {
            if(from_node.index == this.from_node.index && to_node.index == this.to_node.index)
            {
                for(Connection connection: genome.genes)
                {
                    if(connection.innovation_number == innovative_connection)
                    {
                        return true;
                    }
                }
                return false;
            }
        }
        
        return false;
    }
}