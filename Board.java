import java.util.ArrayList;
public class Board {
    
    //All the necessary variables for our game
    public static final int W = -1;
    public static final int B = 1;
    public static final int EMPTY = 0;
    private Move lastMove;
    private int lastDiscPlayed;
    private int [][] gameboard;
    private static final int[] OFFSET_MOVE_ROW = {-1, -1, -1,  0,  0,  1,  1,  1}; // used to move in all 8 directions around the board
    private static final int[] OFFSET_MOVE_COL = {-1,  0,  1, -1,  1, -1,  0,  1}; // to validate and effect the moves
    private int blackdisks,whitedisks;
    private String letters="	0	1	2	3	4	5	6	7";
    private int avMoves;
    private static int[][] BOARD_VALUE = { //stores the board values according to the game rules and strategies 
            {100, -1, 5, 3, 3, 5, -1, 100},
            {-1, -10,1, 1, 1, 1,-10, -1},
            {5 , 1,  2, 2, 2, 2,  1,  5},
            {3 , 1,  2, 0, 0, 2,  1,  3},
            {3 , 1,  2, 0, 0, 2,  1,  3},
            {5 , 1,  2, 2, 2, 2,  1,  5},
            {-1,-10, 1, 1, 1, 1,-10, -1},
            {100, -1, 5, 3, 3, 5, -1, 100}};


    public Board(){ //Initializes the board with the four default discs (two black and two white) in the middle.
        lastMove = new Move();
        lastDiscPlayed = W;
        gameboard = new int [8][8];
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                gameboard[i][j] = EMPTY;
            }
        }
        this.gameboard[3][3]=W;
        this.gameboard[3][4]=B;
        this.gameboard[4][3]=B;
        this.gameboard[4][4]=W;

    }

    public Board(Board board){
        lastMove = board.lastMove;
        lastDiscPlayed = board.lastDiscPlayed;
        gameboard = new int [8][8];
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                gameboard[i][j]= board.gameboard[i][j];
            }
        }
    }

    public Move getLastMove(){
        return lastMove;
    }

    public int getLastDiscPlayed(){
        return lastDiscPlayed;
    }

    public int [][] getGameboard(){
        return gameboard;
    }

    public void setLastMove(Move lastMove){
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol((lastMove.getCol()));
        this.lastMove.setValue(lastMove.getValue());
    }

    public void setLastDiscPlayed(int lastDiscPlayed){
        this.lastDiscPlayed = lastDiscPlayed;
    }

    public void setGameBoard(int[][] gameboard)
    {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                this.gameboard[i][j] = gameboard[i][j];
            }
        }
    }

    
    //The move is made through the effectMove method. The last move and disc played are updated. 
    public void makeMove (int row,int col,int disc){
            gameboard[row][col] = disc;
            effectMove(gameboard,row,col,disc);
            lastMove =new Move(row,col);
            lastDiscPlayed =disc;
    }


    // returns the number of available moves of a player for the current turn 
    public int AvailableMoves(int disc){
        avMoves =0;
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if (isValidMove(i,j,disc))
                    avMoves++;
           }
        }
     return avMoves;
    }

    //skips your turn if there are no available moves
    public void changePlayer(int disc){
       if (AvailableMoves(disc)==0){
            lastDiscPlayed = disc;

            if(disc==B){
                System.out.println("B player has no availabe moves!");
            }else {
                System.out.println("W player has no other moves!");
            }
        }
    }
    
    //checks if your move is out of bounds 
    //checking if the move in the board is unavailable
    //checking if a move is valid based on the game rules
    //OFFSET_MOVE_ROW/COLUMN are used to help us check all 8 directions 
    public boolean isValidMove(int row,int col,int disc){
        if(row<0||row>7||col<0||col>7)
            return false;

        if (gameboard[row][col] != EMPTY)
            return false;

        int oppDisc = (disc ==1) ? -1 : 1;

        boolean isValid =false;

        for (int i=0; i<8; i++){
            int currow = row + OFFSET_MOVE_ROW[i];
            int curcol = col + OFFSET_MOVE_COL[i];
            boolean  hasOppDiscBetween = false;
            while (currow >=0 && currow < 8 && curcol >= 0 && curcol < 8) {

                if (gameboard[currow][curcol]==oppDisc)
                    hasOppDiscBetween =true;
                else if ((gameboard[currow][curcol]==disc) && hasOppDiscBetween){
                    isValid =true;
                    break;
                }else
                    break;
                currow += OFFSET_MOVE_ROW[i];
                curcol += OFFSET_MOVE_COL[i];
            }
            if (isValid)
                break;
        }

        return isValid;
    }


    //changes the discs that need to be "flipped" according to the valid move the player has made
    //OFFSET_MOVE_ROW/COLUMN again are used to check and flip the necessary discs in all 8 directions  
    public static int[][] effectMove(int[][] gameboard,int row,int col,int disc){

        for (int i=0; i<8; i++){
            int currow = row + OFFSET_MOVE_ROW[i];
            int curcol = col + OFFSET_MOVE_COL[i];
            boolean  hasOppDiscBetween = false;
            while (currow >=0 && currow < 8 && curcol >= 0 && curcol < 8) {
                if (gameboard[currow][curcol]==EMPTY)
                    break;

                if (gameboard[currow][curcol]!=disc)
                    hasOppDiscBetween =true;

                if ((gameboard[currow][curcol] ==disc) && hasOppDiscBetween){
                    int effectDiscRow = row + OFFSET_MOVE_ROW[i];
                    int effectDiscCol = col + OFFSET_MOVE_COL[i];
                    while (effectDiscRow != currow || effectDiscCol != curcol){
                        gameboard[effectDiscRow][effectDiscCol] = disc;
                        effectDiscRow += OFFSET_MOVE_ROW[i];
                        effectDiscCol += OFFSET_MOVE_COL[i];
                    }
                    break;
                }
                currow += OFFSET_MOVE_ROW[i];
                curcol += OFFSET_MOVE_COL[i];
            }
        }
        return gameboard;

    }

    //stores the state/board of all valid moves for the current player's turn in 'children'
    //used in GamePlayer class for the Minimax algorithm
    public ArrayList <Board> getChildren(int disc){
        ArrayList<Board> children = new ArrayList<Board>();
        for(int row=0; row<8; row++){
            for (int col=0; col<8; col++){
                if (isValidMove(row,col,disc)){
                    Board child = new Board(this);
                    child.makeMove(row,col,disc); //stin proti sosti kinisi pou vriskei apla tin paizei? what's the point
                    children.add(child);
                }else{
                    lastDiscPlayed=disc;

                }
            }
        }
        return children;
    }

    //checks if the board has reached a Terminal state, meaning there are no empty squares left
    public boolean isTerminal(){
        for(int row=0; row<8; row++)
        {
            for(int col=0; col<8; col++)
            {
                if(gameboard[row][col] == EMPTY)
                {
                    return false;
                }
            }
        }
        return true;

    }

    //counts the black disks
    //used mainly to count the final score 
    public void CountBlackDisks(){
        int counter =0;
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if (gameboard[i][j]==B){
                    counter++;
                }
            }
        }
        blackdisks = counter;	
    }

    //counts the white disks
    //used mainly to count the final score 
    public void CountWhiteDisks(){
        int counter =0;
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if (gameboard[i][j]==W){
                    counter++;
                }
            }
        }
        whitedisks = counter;
    }

    public int getBlackDisks() {
        CountBlackDisks();
        return blackdisks;
    }

    public int getWhiteDisks() {
        CountWhiteDisks();
        return whitedisks;
    }

    
    //the 2 heuretics used for this game
    //their weight of importance can be seen in the return value
    //'eval1' attempts to capture the relative difference between the number of possible moves
    // for the max and the min players, with the intent of restricting the opponent’s mobility
    // and increasing one’s own mobility
    //'eval2' counts the board value using 'BOARD_VALUE', which is based on the game rules/strategies
    public int evaluate(){
        this.CountBlackDisks();
        this.CountWhiteDisks();

        int eval1 = AvailableMoves(B) - AvailableMoves(W);

        int eval2 =0;
        for (int r =0; r<8; r++){
            for (int c =0; c<8; c++){
                if (gameboard[r][c] ==B )
                    eval2 +=BOARD_VALUE[r][c];
                else if (gameboard[r][c] ==W )
                    eval2 -= BOARD_VALUE[r][c];
            }
        }
        return (30*eval1 + 70*eval2)/100;
    }


    //basic method to print out the updated board at every turn
    public void print()
    {
        System.out.println("------------------------------------------------------------------");
        System.out.println(this.letters);
        System.out.println();
        for(int row=0; row<8; row++)
        {
            System.out.print((row)+"	");
            for(int col=0; col<8; col++)
            {
                switch (gameboard[row][col])
                {
                    case B:
                        System.out.print("B ");
                        break;
                    case W:
                        System.out.print("W ");
                        break;
                    case EMPTY:
                        System.out.print("- ");
                        break;
                    default:
                        break;
                }
                if(col < 7)
                {
                    System.out.print('\t');
                }

            }
            System.out.println();
        }
        System.out.println("------------------------------------------------------------------");
    }
}







