/**
 * Class MailMan for finding and printing the optimal Chinese Postman tour of graphs.
 *
 * @author Alexandre Quiterio, 2012
 * Instituto Superior Tecnico
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class MailMan
{
    private ArrayList<ArrayList<Integer>> _graph;
    private int _Tnodes,_Tedges, _startPoint;    
    private Stack<Integer> _stack;
    private ArrayList<Integer> _circuit;
		
    public MailMan()
    {	
		_stack = new Stack<Integer>();
		_circuit = new ArrayList<Integer>();
	    Scanner reader = new Scanner(System.in);
		_Tnodes = reader.nextInt();
		_Tedges = reader.nextInt();
		_startPoint = reader.nextInt();
   		_graph = new ArrayList<ArrayList<Integer>>(_Tnodes);
		
		for(int ix = 0; ix < _Tnodes; ix++)
		{
		    _graph.add(new ArrayList<Integer>());
		}
		
		for(int ix = 0; ix < _Tedges; ix++)
		{
			insertValue(reader.nextInt()-1, reader.nextInt()-1);
		}
    }
	
	private void insertValue(int u, int v)
	{
        _graph.get(u).add(v);
        _graph.get(v).add(u);
	}
	
	private void DeleteEdge(int u, int v)
	{
	    _graph.get(u).remove((Object)v));
        _graph.get(v).remove((Object)u));
	}
    
    private boolean BuildEulerianGraph()
    {
        for(int ix = 0; ix < _Tnodes; ix++)
        {
            if(_graph.get(ix).size() % 2 != 0)
            {
                 return false;
            }
        }
        MakeEulerianPath(_startPoint-1);
        
        while(!_stack.empty())
        {
            if(_graph.get(_stack.peek()).size() == 0)
            {
                _circuit.add(_stack.pop());
            }
            else
            {
                MakeEulerianPath(_stack.pop());
            }
        }
        
        return true;
     }
    
    private void MakeEulerianPath(int currentPos)
	{
	    int _currentPosition = currentPos;
	    int LowerNeighbor;
	    while(_graph.get(_currentPosition).size() != 0)
	    {
	        LowerNeighbor = _graph.get(_currentPosition).get(0);
	        _stack.push(_currentPosition);
		    DeleteEdge(_currentPosition, LowerNeighbor);
		    _currentPosition = LowerNeighbor;
	    }
	    _stack.push(_currentPosition);
	}
	
	public void SolveProblem()
	{

		try
		{
            if(BuildEulerianGraph())
            {
		        printCircuit(_circuit);
		    }
		    else
		    {
		        System.out.println("-1");
		    }
		}
		catch(IOException ioe)
		{
		    System.err.println(ioe.getMessage());
		}
	}

    private void printCircuit(List<Integer> circuit) throws IOException 
    {
	    BufferedWriter out =
	        new BufferedWriter(
			           new OutputStreamWriter(
						          new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 
			           circuit.size() + circuit.size()*4);

	    out.write(""+(circuit.get(circuit.size()-1)+1));
	
	    for(int i = circuit.size()-2; i >= 0; i--)
	    {
	        out.write(" " + (circuit.get(i)+1));
	    }
	    out.write("\n");
	    out.flush();
    }	
	public static void main(String args[])
	{
	    MailMan mail = new MailMan();
	    mail.SolveProblem();
	}
}
