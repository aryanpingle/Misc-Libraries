import java.util.*;

// Basically The brain, also the structure, which can be trained, mutated and shit
public class Genome
{
    public static void main(String[] args)
    {
        NEAT.main(args);
    }
    
    LinkedList<Node> nodes;
    int NEXT_NODE;
    LinkedList<Connection> genes; // List of connextions
    
    int layers;
    
    int input_size;
    int output_size;
    
    int bias_node;
    
    int NEXT_CONNECTION;
    
    LinkedList<Node> network; // Ordered list of all the nodes such that they're called sequentially per layer
    
    public Genome(int input_size, int output_size, boolean empty)
    {
        nodes = new LinkedList<Node>();
        NEXT_NODE = 0;
        genes = new LinkedList<Connection>();
        NEXT_CONNECTION = 0;
        
        layers = 2; // For now 2, input and output. This may change later on.
        
        this.input_size = input_size;
        this.output_size = output_size;
        
        network = new LinkedList<Node>();
        
        if(empty) return;
        
        for(int i = 0; i < input_size; ++i)
        {
            Node node = new Node(i);
            nodes.add(node);
            ++NEXT_NODE;
            node.layer = 0;
        }
        
        for(int i = 0; i < output_size; ++i)
        {
            Node node = new Node(i+input_size);
            nodes.add(node);
            ++NEXT_NODE;
            node.layer = 1;
        }
        
        bias_node = NEXT_NODE++;
        Node temp = new Node(bias_node); // Bascially, the bias_node Node object
        temp.layer = 0;
        nodes.add(temp); 
        
        // Connect all the inputs to the outputs
        for(Node node: nodes)
        {
            if(node.layer == 0)
            {
                for(int i = 0; i < output_size; ++i)
                {
                    Node output_node = nodes.get(nodes.size()-2-i);
                    genes.add(new Connection(node, output_node, p.map(Math.random(), 0, 1, -1, 1), getInnovationNumber(Population.innovationHistory, node, output_node)));
                    ++NEXT_CONNECTION;
                }
            }
        }
    }
    
    /**
    * Genome p1 should always be the better / fitter genome
    */
    
    public Genome(Genome p1, Genome p2)
    {
        nodes = new LinkedList<Node>();
        NEXT_NODE = 0;
        genes = new LinkedList<Connection>();
        NEXT_CONNECTION = 0;
        
        layers = 2; // For now 2, input and output. This may change later on.
        
        this.input_size = p1.input_size;
        this.output_size = p1.output_size;
        
        network = new LinkedList<Node>();

        for(Node node: p1.nodes)
        {
            nodes.add(node.clone());
        }
        
        for(int i = 0; i < p1.genes.size(); ++i)
        {
            int p2gene = p1.matchingGene(p2, p1.genes.get(i).innovation_number);
            
            if(p2gene == -1) // The current connection in p1 does not exist in p2 i.e. it is disjoint or excess, so add it regardless
            {
                genes.add(p1.genes.get(i).clone(p1.genes.get(i).from_node, p1.genes.get(i).to_node));
            }
            else // The current connection matches that in p2
            {
                Connection connection = null;
                
                if(Math.random() < 0.5)
                {
                    connection = p1.genes.get(i);
                }
                else
                {
                    connection = p2.genes.get(p2gene);
                }
                genes.add(connection.clone(connection.from_node, connection.to_node));
                
                if(!p1.genes.get(i).enabled || !p2.genes.get(p2gene).enabled)
                {
                    if(Math.random() < 0.75)
                        connection.enabled = false;
                }
            }
        }

        connectNodes();
    }
    
    Node getNode(int index)
    {
        for(Node node: nodes)
        {
            if(node.index == index)
            {
                return node;
            }
        }
        
        return null;
    }
    
    /**
    * Resets all connections and connects them again
    */
    
    void connectNodes()
    {
        for(Node node: nodes)
        {
            node.output_connections = new LinkedList<Connection>();
        }
        
        for(Connection connection: genes)
        {
            connection.from_node.output_connections.add(connection);
        }
    }
    
    double[] feedForward(double[] inputs)
    {
        double[] outputs = new double[output_size];
        
        // Set all the outputs for the input nodes, so that the values can be passed through their connections
        for(int i = 0; i < input_size; ++i)
        {
            nodes.get(i).outputvalue = inputs[i];
        }
        
        for(Node node: network)
        {
            node.engage();
        }
        
        for(int i = 0; i < output_size; ++i)
        {
            outputs[i] = nodes.get(input_size+i).outputvalue;
        }
        
        // Reset all the inputsums for the next feedforward
        for(int i = 0; i < input_size; ++i)
        {
            nodes.get(i).inputsum = 0;
        }
        
        return outputs;
    }
    
    /**
    * Generated a network of all the nodes, including inputs, all according to the layers i.e. in order
    */
    
    void generateNetwork()
    {
        connectNodes();
        
        if(network.size() == 0)
        network = new LinkedList<Node>();
        
        for(int layer = 0; layer < this.layers; ++layer)
        {
            for(Node node: nodes)
            {
                if(node.layer == layer)
                {
                    network.add(node);
                }
            }
        }
    }
    
    /**
    * Returns whether the network is fully connected or not
    */
    
    boolean fullyConnected()
    {
        long max_connections = 0;
        int[] nodesInLayers = new int[this.layers];
        
        for(int i = 0; i < nodesInLayers.length; ++i)
        {
            nodesInLayers[i] = 0;
        }
        
        for(Node node: nodes)
        {
            nodesInLayers[node.layer] += 1;
        }
        
        for(int i = 0; i < this.layers-1; ++i)
        {
            int nodesInFront = 0;
            for(int j = i+1; j < this.layers; ++j)
            {
                nodesInFront += nodesInLayers[j];
            }
            max_connections += nodesInLayers[i]*nodesInFront;
        }
        
        if(max_connections <= genes.size())
        return true;
        
        return false;
    }
    
    void addNode(LinkedList<ConnectionHistory> innovationHistory)
    {
        if(genes.size() == 0)
        {
            addConnection(innovationHistory);
            return;
        }
        
        // First step - select a random connection and disable it
        int random_connection_number = (int)(Math.random()*genes.size());
        while(genes.get(random_connection_number).from_node.equals(nodes.get(bias_node)) && genes.size() != 1) // Don't disconnect bias
        {
            random_connection_number = (int)(Math.random()*genes.size());
        }
        Connection deleted_connection = genes.get(random_connection_number);
        deleted_connection.enabled = false;
        
        Node from_node = deleted_connection.from_node;
        Node to_node = deleted_connection.to_node;
        int middle = NEXT_NODE++;
        Node middle_node = new Node(middle);
        nodes.add(middle_node);
        
        // Add a connection from the from_node to the middle_node / new node with a weight of 1
        int innovation_number = getInnovationNumber(innovationHistory, from_node, middle_node);
        genes.add(new Connection(from_node, middle_node, 1, innovation_number));
        
        // Add a connection from the middle_node / new node to the to_node with a weight of the deleted connection
        innovation_number = getInnovationNumber(innovationHistory, middle_node, to_node);
        genes.add(new Connection(middle_node, to_node, deleted_connection.weight, innovation_number));
        middle_node.layer = from_node.layer+1;
        
        // push all layers above or equal up by 1
        for(int i = 0; i < nodes.size()-1; ++i) // All nodes except the newest one
        {
            if(nodes.get(i).layer > middle_node.layer)
            {
                nodes.get(i).layer += 1;
            }
        }
        
        connectNodes();
    }
    
    void addConnection(LinkedList<ConnectionHistory> innovationHistory)
    {
        if(fullyConnected()) return;
        
        int n1 = (int)(Math.random()*nodes.size());
        int n2 = (int)(Math.random()*nodes.size());
        while(isRandomConnectionValid(n1, n2))
        {
            n1 = (int)(Math.random()*nodes.size());
            n2 = (int)(Math.random()*nodes.size());
        }
        
        if(nodes.get(n1).layer > nodes.get(n2).layer)
        {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }
        
        int connecion_innovation_number = getInnovationNumber(innovationHistory, nodes.get(n1), nodes.get(n2));
        
        genes.add(new Connection(nodes.get(n1), nodes.get(n2), p.map(Math.random(), 0, 1, -1, 1), connecion_innovation_number));
        connectNodes();
    }
    
    /**
    * Returns the innovation number associated with the two nodes if it exists, otherwise returns the next_connection number and updates it
    */
    
    int getInnovationNumber(LinkedList<ConnectionHistory> innovationHistory, Node from_node, Node to_node)
    {
        boolean isNew = true;
        
        int connecion_innovation_number = NEXT_CONNECTION;
        
        for(int i = 0; i < innovationHistory.size(); ++i)
        {
            if(innovationHistory.get(i).matches(this, from_node, to_node))
            {
                isNew = false;
                break;
            }
        }
        
        if(isNew) // If this is a new mutation, add it to the innovationhistory and update next_connection
        {
            LinkedList<Integer> innovationNumbers = new LinkedList<Integer>();
            for(Connection connection: genes)
            {
                innovationNumbers.add(connection.innovation_number);
            }
            innovationHistory.add(new ConnectionHistory(from_node, to_node, connecion_innovation_number, innovationNumbers));
            ++NEXT_CONNECTION;
        }
        
        return connecion_innovation_number;
    }
    
    /**
    * Returns the index of the matching gene in parent2
    */
    
    int matchingGene(Genome parent2, int innovation_number)
    {
        for(int i = 0; i < parent2.genes.size(); ++i)
        {
            Connection connection = parent2.genes.get(i);
            if(connection.innovation_number == innovation_number)
            {
                return i;
            }
        }
        
        return -1;
    }
    
    boolean isRandomConnectionValid(int index1, int index2)
    {
        if(nodes.get(index1).layer == nodes.get(index2).layer) return false;
        if(nodes.get(index1).isConnectedTo(nodes.get(index2))) return false;
        
        return true;
    }
    
    void mutate(LinkedList<ConnectionHistory> innovationHistory)
    {
        if(genes.size() == 0)
        {
            addConnection(innovationHistory);
        }
        
        if(Math.random() < 0.8)
        {
            System.out.println("Mutating the weight of a connection");
            for(Connection connection: genes)
            {
                connection.mutate();
            }
        }
        
        // 5% of the time, add a connection
        if(Math.random() < 0.05)
        {
            System.out.println("Adding a connection");
            addConnection(innovationHistory);
        }
        
        // 1% of the time, add a node
        if(Math.random() < 0.5)
        {
            System.out.println("Adding a node");
            addNode(innovationHistory);
        }
    }
    
    void print()
    {
        p.ln("------------------------------------------------------------------------");
        for(Node node: nodes)
        {
            System.out.println("Node "+node.index+" with "+node.output_connections.size()+" connections");
        }
        p.ln();
        for(Connection connection: genes)
        {
            System.out.println("Connection "+connection.innovation_number+" with weight "+connection.weight+" from node "+connection.from_node.index+(connection.from_node.index==bias_node?" (BIAS NODE)":"")+", to node "+connection.to_node.index);
        }
    }

    public Genome clone()
    {
        Genome clone = new Genome(input_size, output_size, true);

        clone.layers = this.layers;
        clone.bias_node = this.bias_node;
        clone.NEXT_CONNECTION = this.NEXT_CONNECTION;
        clone.NEXT_NODE = this.NEXT_NODE;

        for(Node node: nodes)
        {
            clone.nodes.add(node.clone());
        }
        for(Connection connection: genes)
        {
            clone.genes.add(connection.clone(connection.from_node, connection.to_node));
        }

        return clone;
    }
}
