import java.io.*;
import java.util.*;

public class Main
{
    static Node[] nodes;
    static LinkedList<Integer> leafs = new LinkedList<Integer>();
    static int nc = 0;
    static int forbidden;

    public static String readFile(String s) throws Exception
    {
        FileReader f = new FileReader(s);
        BufferedReader br = new BufferedReader(f);

        String str = null, sss="";
        while((str=br.readLine())!=null)
        {
            sss += str+"\n";
        }
        br.close();

        return sss;
    }
    
    static class Node
    {
        int value;
        int cost;
        int[] children;
        int index;
        
        public Node(int value, int cost)
        {
            this.value = value;
            this.cost = cost;
            this.children = new int[2];
            for(int i = 0; i < 2; i++)
            {
                this.children[i] = -1;
            }
            this.index = nc;
            nodes[nc++] = this;
            leafs.add(this.index);
        }
        
        public void create_set(int new_value, int cost)
        {
            if(new_value == this.value)
            return;
            else if (new_value == forbidden) {
                ln("FORBIDDEN! Adding 1 child to node "+this.index);
                leafs.remove(leafs.indexOf(this.index));
                this.children[0] = new Node(this.value, cost+this.cost).index;
            }
            else {
                ln("ADDING 2 children to node "+this.index);
                leafs.remove(leafs.indexOf(this.index));
                this.children[0] = new Node(new_value, this.cost).index;
                this.children[1] = new Node(this.value, this.cost+cost).index;
            }
        }
        
        public LinkedList<Integer> get_leafs()
        {
            LinkedList<Integer> list = new LinkedList<Integer>();
            
            if (this.children[0] == -1) {
                list.add(this.index);
            }
            else
            {
                for(int i = 0; i < 2; i++)
                {
                    if(this.children[i] == -1)
                    {
                        break;
                    }
                    list.addAll(nodes[this.children[i]].get_leafs());
                }
            }
            
            return list;
        }
    }
    
    public static LinkedList<Integer> create_sample(LinkedList<Integer> list_of_nodes, int value_to_be_searched)
    {
        LinkedList<Integer> list = new LinkedList<Integer>();
        
        for(int idx: list_of_nodes)
        {
            if(value_to_be_searched == nodes[idx].value)
            {
                list.addAll(nodes[idx].get_leafs());
            }
        }
        
        return list;
    }
    
    public static LinkedList<Integer> get_all_leafs(LinkedList<Integer> list_of_nodes)
    {
        ln("INSIDE GET ALL LEAFS");
        LinkedList<Integer> list = new LinkedList<Integer>();
        
        for(int idx: list_of_nodes)
        {
            ln("GETTING LEAFS FOR "+idx);
            list.addAll(nodes[idx].get_leafs());
        }
        
        return list;
    }

    public static int get_min()
    {
        int min = -1;
        for(int leaf: leafs)
        {
            if(min == -1 || min > nodes[leaf].cost)
            {
                min = nodes[leaf].cost;
            }
        }
        return min;
    }

    public static void print_nodes(LinkedList<Integer> list_of_nodes)
    {
        for(int leaf: list_of_nodes)
        {
            System.out.println("Node "+nodes[leaf].index+" value "+nodes[leaf].value+" cost "+nodes[leaf].cost);
        }
    }
    
    public static void main(String[] args) {
        String input = readFile("");

        Scanner ob = new Scanner(System.in);
        
        nodes = new Node[200000];
        
        new Node(0, 0);
        int T = ob.nextInt();
        forbidden = ob.nextInt();
        ob.nextLine();

        LinkedList<Integer> sample_sets_conditions = new LinkedList<Integer>();
        LinkedList<LinkedList<Integer>> sample_sets = new LinkedList<LinkedList<Integer>>();

        sample_sets_conditions.add(0);
        sample_sets.add(leafs);

        ln("LEAFS ARE :");
        print_nodes(leafs);

        String sp = "set";
        String ifp = "if";

        for(int bruh = 0; bruh < T; bruh++)
        {
            ln("LEAFS ARE :");
            print_nodes(leafs);
            sample_sets.set(sample_sets.size()-1, get_all_leafs(sample_sets.getLast()));
            ln("SAMPLE SET IS :");
            print_nodes(sample_sets.getLast());
            String[] cmd = ob.nextLine().split("\\s+");

            if(cmd[0].equals(sp))
            {
                ln("SETTING "+cmd[1]+" FOR "+cmd[2]);
                for(int idx : sample_sets.getLast())
                {
                    nodes[idx].create_set(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
                }
                ln("SETTING DONE");
            }
            else if(cmd[0].equals(ifp)) {
                ln("IFFING "+cmd[1]);
                sample_sets.add(create_sample(sample_sets.getLast(), Integer.parseInt(cmd[1])));
                sample_sets_conditions.add(Integer.parseInt(cmd[1]));
                ln("IFFING DONE");
            }
            else
            {
                sample_sets.pop();
                sample_sets_conditions.pop();
            }
        }

        System.out.println(get_min());

        ob.close();
    }

    public static void ln(String h)
    {
        System.out.println(h);
    }

    public static void n(String h)
    {
        System.out.print(h);
    }
}
