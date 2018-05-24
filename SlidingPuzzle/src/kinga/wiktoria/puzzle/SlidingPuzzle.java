package kinga.wiktoria.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.Point;

import sac.graph.AStar;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphSearchConfigurator;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;
import sac.graph.OpenSet;

public class SlidingPuzzle extends GraphStateImpl
{
    
    public static byte n = 3;
    public static byte n2 = (byte) (n * n);
    public static final Random rand = new Random();
    public byte emptyRow;
    public byte emptyColumn;
    
    public byte[][] board=null;//

    
    public SlidingPuzzle(){
	board = new byte[n][n];
	byte k = 0;
	for(int i = 0; i < n; i++) {
	    for(int j = 0; j < n;j++)
	    {
		board[i][j] = k;
		k++;
	    }
	}
	emptyRow = 0;
	emptyColumn = 0;
    }
    
    public SlidingPuzzle(SlidingPuzzle parent)
    {
	board = new byte[n][n];
	
	for(int i = 0; i < n; i++) 
	    for(int j = 0; j < n; j++)
		board[i][j] = parent.board[i][j];
	
	emptyRow = parent.emptyRow;
	emptyColumn = parent.emptyColumn;
    }
    
    public byte[][] getBoard()
    {
	return board;
    }
    
    @Override
    public int hashCode()
    {
	byte[] linearPuzzle = new byte[n2];
	int k = 0;
	
	for(int i = 0; i < n; i++)
	    for(int j = 0; j < n; j++)
		linearPuzzle[k++] = board[i][j];
        return Arrays.hashCode(linearPuzzle);
    }
    
    @Override
    public String toString()
    {
	StringBuilder builder = new StringBuilder();
    builder.append("-------------\n");
	for(int i = 0; i < n; i++)
	{
		builder.append("| ");
	    for(int j = 0; j < n; j++)
		builder.append(board[i][j] + " | ");
	    
	    builder.append("\n-------------\n");
	}
	
        return builder.toString();
    }
    
    public void makeMove(int newRow, int newColumn)
    {
	board[emptyRow][emptyColumn] = board[newRow][newColumn]; 
	board[newRow][newColumn] = 0;
	emptyRow = (byte) newRow;
	emptyColumn = (byte) newColumn;
    }
    
    public ArrayList<Point> getMoves()
    {
	ArrayList<Point> list = new ArrayList<Point>(4);
	for(int k = 0; k < 4; k++)
	    list.add(null);
	
	if(emptyColumn != 0)//left
	{
	    list.add(0, new Point((int)emptyRow, (int)(emptyColumn-1)));

	}
	
	if(emptyColumn != n - 1)//right
	{
	    list.add(1, new Point((int)emptyRow, (int)(emptyColumn+1)));

	}
	
	if(emptyRow != 0)//up
	{
	    list.add(2, new Point((int)(emptyRow-1), (int)emptyColumn));
	}

	
	if(emptyRow != (n -1))//down
	{
	    list.add(3, new Point((int)(emptyRow+1), (int)emptyColumn)); 
	}
	
	return list;
    }
    
    public void shuffle(int numberOfMoves)
    {
	int randIndex;
	ArrayList<Point> list;
	
	for(int index = 0; index < numberOfMoves; index++)
	{
	    
	    list = getMoves();
	    randIndex = rand.nextInt(list.size());
	    if(list.get(randIndex) != null)
		makeMove((int)list.get(randIndex).getX(), (int)list.get(randIndex).getY());
	    else
		numberOfMoves++;
	}
    }
    
    @Override
    public List<GraphState> generateChildren()
    {
	List<GraphState> children = new ArrayList<GraphState>();
	List<Point> list;
	for(int index = 0; index < 4; index++)
	{
	    SlidingPuzzle child = new SlidingPuzzle(this);
	    list = child.getMoves();
	    
	    if(list.get(index) != null)
	    {
		child.makeMove((int)list.get(index).getX(), (int)list.get(index).getY());
		    
		if(index == 0)
		    child.setMoveName("L");
		    
		if(index == 1)
		    child.setMoveName("R");
		    
		if(index == 2)
		    child.setMoveName("U");
	    
		if(index == 3)
		    child.setMoveName("D");
		    
		children.add(child);
	    }
	
	}
	return children;
    }

    @Override
    public boolean isSolution()
    {
	byte cell = 0;
	
	for(int i = 0; i < SlidingPuzzle.n; i++)
	    for(int j = 0; j < SlidingPuzzle.n; j++)
		if(board[i][j] != cell++)
		    return false;
			
	return true;
    }


public static void main(String[] args)
{
	List<Long>dur_manh= new ArrayList<>();

	List<Integer>cl_manh = new ArrayList<>();
	List<Integer>op_manh= new ArrayList<>();
	List<Long>dur_mt= new ArrayList<>();
	List<Integer>cl_mt= new ArrayList<>();
	List<Integer>op_mt= new ArrayList<>();
	long t_manh=0, t_mt=0;
    long t_manh_av=0, t_mt_av=0;
    int o_manh=0, o_mt=0, c_manh=0, c_mt=0, o_manh_av=0, o_mt_av=0, c_manh_av=0, c_mt_av=0;   
    
System.out.println("=====Sliding Puzzle=====\n");
	int test=100;
for (int i = 0; i < test; i++)
{
	
     	SlidingPuzzle puzzle = new SlidingPuzzle();
     	SlidingPuzzle.setHFunction(new HManhattan());
	puzzle.shuffle(1000);
	if(i < 5)
	{
		System.out.println("\nMethod: MANHATTAN\n");
		System.out.println("Shuffled puzzle:\n");
		System.out.println(puzzle);
		System.out.println("Solved Puzzle:\n");
	}
	GraphSearchAlgorithm algorithm = new AStar(puzzle);
	
	
	algorithm.execute();
	
	List<GraphState> solutions = algorithm.getSolutions();
	
	for (GraphState solution : solutions)
	{
	    if (i<5)
	    {
		System.out.println(solution);
	    System.out.println("PATH LENGTH: " + solution.getPath().size());
	    System.out.println("MOVES ALONG PATH: " + solution.getMovesAlongPath());
	    }
	}
	
	if (i<5)
	{
	System.out.println("DURATION TIME: " + algorithm.getDurationTime() + "ms");
	System.out.println("CLOSED: " + algorithm.getClosedStatesCount());
	System.out.println("OPEN: " + algorithm.getOpenSet().size());
	System.out.println("SOLUTIONS: " + algorithm.getSolutions().size());	
	System.out.println();
	System.out.println();
	}
	dur_manh.add(algorithm.getDurationTime());
	cl_manh.add(algorithm.getClosedStatesCount());
	op_manh.add(algorithm.getOpenSet().size());


}


for (int i = 0; i < test; i++)
{

     	SlidingPuzzle puzzle = new SlidingPuzzle();
     	SlidingPuzzle.setHFunction(new HMisplacedTiles());
	puzzle.shuffle(1000);
	
	if (i <5)
	{
	System.out.println("=======================================\n");
	System.out.println("Method: MISPLACED TILES\n\n");
	System.out.println("Shuffled puzzle:\n");
	System.out.println(puzzle);
	System.out.println("Solved puzzle:\n");
	}
	
	GraphSearchAlgorithm algorithm = new AStar(puzzle);
	
	algorithm.execute();
	
	List<GraphState> solutions = algorithm.getSolutions();
	
	for (GraphState solution : solutions)
	{
	    if (i < 5)
	    {
		System.out.println(solution);
	    System.out.println("PATH LENGTH: " + solution.getPath().size());
	    System.out.println("MOVES ALONG PATH: " + solution.getMovesAlongPath());
	    }
	}	
	
	if (i<5)
	{
	System.out.println("DURATION TIME: " + algorithm.getDurationTime() + "ms");
	System.out.println("CLOSED: " + algorithm.getClosedStatesCount());
	System.out.println("OPEN: " + algorithm.getOpenSet().size());
	System.out.println("SOLUTIONS: " + algorithm.getSolutions().size());
	System.out.println();
	System.out.println();
	}
	
	dur_mt.add(algorithm.getDurationTime());
	cl_mt.add(algorithm.getClosedStatesCount());
	op_mt.add(algorithm.getOpenSet().size());

	
}
for(int i=0; i<test; i++) {
	t_manh=t_manh + dur_manh.get(i);
	t_mt=t_mt + dur_mt.get(i);
	o_manh=o_manh+op_manh.get(i);
	o_mt=o_mt+op_mt.get(i);
	c_manh=c_manh+cl_manh.get(i);
	c_mt=c_mt+cl_mt.get(i);	 
}
t_manh_av=t_manh/test;
t_mt_av=t_mt/test;
o_manh_av=o_manh/test;
o_mt_av=o_mt/test;
c_manh_av=c_manh/test;
c_mt_av=c_mt/test;
System.out.print("\nMANHATTAN average duration: " + t_manh_av);
System.out.print("\nMANHATTAN average Closed states: " + c_manh_av);
System.out.print("\nMANHATTAN average Open states: " + o_manh_av);
System.out.print("\nMISPLACED TILES average duration: " + t_mt_av);
System.out.print("\nMISPLACED TILES average Closed states: " + c_mt_av);
System.out.print("\nMISPLACED TILES average Open states: " + o_mt_av);
}}