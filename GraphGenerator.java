/*
 * @author: Alexandre Quiterio
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class GraphGenerator
{

    private ArrayList<ArrayList<Integer>> _adjList;
    private int _nodes, _start;
    private Random _rand;

    public GraphGenerator(int start, int number_of_nodes)
    {
        _start = start;
        _nodes = number_of_nodes;
        _rand = new Random();
        _adjList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < number_of_nodes; i++)
        {
            initialize(i);
        }
        generateAdjList(number_of_nodes);
    }
    
    private void initialize(int i)
    {
        _adjList.add(new ArrayList<Integer>());
    }
    
    private void generateAdjList(int nodes)
    {
        for(int i = 0; i < _nodes-1; i++)
        {
            generateEdge(i,i+1);
        }
        normalize();
        return;
    }
    
    private void normalize()
    {
        // sorting
        int _right = 0;
        boolean canStopL = false;
        boolean canStopR = false;
        int _left = _nodes - 1;
        for(int i = 0; i < _nodes; i++)
        {
            if(_adjList.get(_right).size() % 2!= 0)
            {
                canStopR = true;
            }
            if(_adjList.get(_left).size() % 2 != 0)
            {
                canStopL = true;
            }
            if(!canStopR)
            {
                _right += 1;
                continue;
            }
            if(!canStopL)
            {
                _left -=1;
                continue;
            }
            if(canStopR && canStopL)
            {
                addEdge(_right, _left);
                canStopR = false;
                canStopL = false;
                _left -=1;
                _right +=1; 
                 if( _left == _right)
                {
                    return;
                }
            }
        }
   }
    
    private void generateEdge(int currentGen, int NewNode)
    {
        int connectionNode = _rand.nextInt(currentGen+1);
        addEdge(connectionNode, NewNode);
        diffuse(connectionNode, NewNode);
    }
    
    private void addEdge(int connect, int newer)
    {
        if(connect != newer)
        {
        _adjList.get(connect).add(newer);
        _adjList.get(newer).add(connect);
        }
    }
    
    private void diffuse(int connect, int newer)
    {
        ArrayList<Integer> _list = _adjList.get(connect);
        
        for(int i = 0; i < _list.size(); i++)
        {
            //connect a determinated probability
            if(_rand.nextInt(2) == 1)
            {
                continue;
            }
            else
            {
                addEdge(_list.get(i), newer);
            }
        }
    }
    
    public void printList()
    {
//        System.out.println(_adjList);
        int counterEdges = 0;
        for (int i = 0; i < _nodes; i++)
        {
            counterEdges += _adjList.get(i).size();
        }
        
        System.out.println(_nodes);
        System.err.print(_nodes + " ");
        System.out.println(counterEdges/2);
        System.err.println(counterEdges/2);
        System.out.println(_start);
        
        int length = 0;
        for(int i = 0; i < _nodes; i++)
        {
            length = _adjList.get(i).size();
            Collections.sort(_adjList.get(i));
            for(int ix = 0; ix < length; ix++)
            {
                if((_adjList.get(i).get(ix)+1) > (i+1))
                System.out.println((i+1) + " " + (_adjList.get(i).get(ix)+1));
            } 
        }
    }
    
    public final static void main (String [] args)
    {
        Scanner scan = new Scanner(System.in);
        GraphGenerator graph = new GraphGenerator(scan.nextInt(), scan.nextInt());
        graph.printList();
    }
}
