import java.util.ArrayList;
import java.util.Random;
import java.lang.*;

public class GamePlayer{
    private int maxDepth;

    private int playerDisc;

/*This class is all about the implementation of the Minimax algorithm. The maximizer works to get the highest score, 
while the minimizer gets the lowest score by trying to counter moves. In our case, only the AI player makes moves 
automatically using this algorithm, if the human player has chosen to play first, the min method is used to counter 
the move, otherwise the max method is used throughout the game to take the initiative with the best possible move. 
The two methods (max,min) do a DFS, searching for the best possible moves using getChinldren() and our two euretics 
with evaluate(). We have also added a-b prunning inside these methods, in order to avoid searching all the branches 
of the tree. Instead, if the algorith realizes that going down a branch won't result in a better move, then it will 
ignore it completely.
*/

    public GamePlayer(int maxDepth,int playerDisc)
    {
        this.maxDepth =maxDepth;
        this.playerDisc =playerDisc;
    }

    public Move MiniMax(Board board){
        if (playerDisc  == Board.B){
            return max(new Board(board) ,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
        else {
            return min(new Board(board),0,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
    }

    public Move max(Board board,int depth,int alpha,int beta){
        Random r = new Random();

        if ((board.isTerminal()) || (depth ==maxDepth)){
            Move lastMove = new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),board.evaluate());
            return lastMove;
        }
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.B));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children){
            Move move = min(child,depth+1,alpha,beta);
            if (move.getValue()>=maxMove.getValue()){
                if ((move.getValue()==maxMove.getValue())){
                    if (r.nextInt(2) == 0)
                    {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else
                {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }

            }
            if(maxMove.getValue()>=beta){
                return maxMove;}

            alpha=Math.max(alpha, maxMove.getValue());
        }
        return maxMove;
    }
    public Move min(Board board, int depth,int alpha,int beta)
    {
        Random r = new Random();

        if((board.isTerminal()) || (depth == maxDepth))
        {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            return lastMove;
        }
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.W));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board child : children)
        {
            Move move = max(child, depth + 1,alpha,beta);
            if(move.getValue() <= minMove.getValue())
            {
                if ((move.getValue() == minMove.getValue()))
                {
                    if (r.nextInt(2) == 0)
                    {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else
                {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
                if(minMove.getValue()<=alpha){
                    return minMove;
                }
                beta=Math.min(beta, minMove.getValue());
            }
        }
        return minMove;
    }

}