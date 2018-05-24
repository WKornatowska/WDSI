package kinga.wiktoria.puzzle;

import sac.State;
import sac.StateFunction;

public class HMisplacedTiles extends StateFunction
{
    @Override
    public double calculate(State state)
    {
	int counter = 0;
	double h = 0.0;
	
	byte[][] board = ((SlidingPuzzle) state).getBoard();
	
	for(int i = 0; i < SlidingPuzzle.n; i++)
	    for(int j = 0; j < SlidingPuzzle.n; j++)
		if(board[i][j] != counter++ && board[i][j] != 0)
		    h += 1.0;
	return h;
    }
}
