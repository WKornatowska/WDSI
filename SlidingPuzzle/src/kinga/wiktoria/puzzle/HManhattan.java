package kinga.wiktoria.puzzle;

import sac.State;
import sac.StateFunction;

public class HManhattan extends StateFunction
{
    @Override
    public double calculate(State state)
    {
	byte board[][] = ((SlidingPuzzle) state).getBoard();
	int manhattan = 0;
	
	for(int i = 0; i < SlidingPuzzle.n; i++)
	    for(int j = 0; j < SlidingPuzzle.n; j++)
	    {
		if (board[i][i] == 0)
		    continue;
		
		manhattan += Math.abs(i - Math.floor(board[i][j] / SlidingPuzzle.n)) + Math.abs(j - board[i][j] % SlidingPuzzle.n);
	    }
        return manhattan;
    }
    
}
