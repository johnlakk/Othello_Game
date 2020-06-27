import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        boolean flag;
        Scanner sc = new Scanner (System.in);
        System.out.println("Welcome to the game. Please select difficulty:\n1.EASY\n2.MEDIUM\n3.HARD\n(Select the number you want, then press enter)");
        int diff = sc.nextInt();

        System.out.println("Do you want to play first? (Y/N)");
        char first= sc.next().charAt(0); 

        //Setting who plays first. If the answer is 'Y' then the machine has 'W', otherwise it has 'B'.
        //The validity of the user's answer is checked. Only Y/N are valid letters.
		 while(first!='Y' && first!='N'){
			 System.out.println("Wrong answer.Please try again");
			 System.out.println("Do you want to play first? (Y/N)");
			 first= sc.next().charAt(0); //if Y then the machine has 'O' else 'Y'
		 }

		 //Setting the difficulty of the game (the max depth of the MiniMax algorithm)
        int maxDepth=0;
        if(diff==1){
            maxDepth=1;
        }else if (diff==2) {
            maxDepth=2;
        }else if(diff>2){
            maxDepth=3;
        }else{
            System.out.println("Error!");
        }

            int pl;
            int p2;
		 if(first=='Y'){
             pl =Board.W;
             p2=Board.B;

		 }else{
             pl =Board.B;
             p2=Board.W;
		 }
		 
         //Creating the AI player.
        GamePlayer comp= new GamePlayer(maxDepth,pl);

		 //Initializing the Board.
        Board board=new Board();
        board.print();

        int col;
        int row;

        while(!board.isTerminal()){
        	
        	//this is the case which neither player has an availabe move, so the game ends tie
        	if(board.AvailableMoves(Board.W)==0 && board.AvailableMoves(Board.B)==0){
            	System.out.println("There are no other available moves for both players.");
            	break;
            }else{
            	
            	flag=true;
            	System.out.println();
            	System.out.println("BLACK: " + board.getBlackDisks() + " WHITE: " + board.getWhiteDisks());

            	//Skipping the player's turn if necessary
            	board.changePlayer(pl);
            	board.changePlayer(p2);
            

            	switch (board.getLastDiscPlayed())
            	{
            		//If B played last, it's W's turn
                    //If the answer above was 'Y', our player has 'B' so the computer plays now.
                    //The opposite for the 'else' case.
                    //For the players turn it promps the player to insert the row and the column of their next move.
                    //If the player's move is valid then the board gets updated and we move on to the computer's turn.
                    //If not, then the program checks if there are any available moves or not for the player.
                    //If yes, then it promps them to try again with a different move.
                	case Board.B:

						if(first=='Y'){
							System.out.println("W player's turn");
                            System.out.println("AVAILABLE MOVES : " + board.AvailableMoves(Board.W) );
							Move WMove =comp.MiniMax(board);
							board.makeMove(WMove.getRow(), WMove.getCol(), Board.W);


						}else{
							System.out.println("W player's turn");
                            System.out.println("AVAILABLE MOVES : " + board.AvailableMoves(Board.W) );
                            while(flag){
                            	System.out.println("Please make your next move by typing column and row separated by a space: ");
                            	col= sc.nextInt();
                            	row= sc.nextInt();
                            	if(board.isValidMove(row, col, Board.W) ){
                            		board.makeMove(row,col,Board.W);
                            		flag=false;
                            	}else{
                            		if (board.AvailableMoves(Board.W)==0){
                            			board.setLastDiscPlayed(Board.W);
                            			flag =true;
                            		}
                            		System.out.println("Invalid move. Please make your move again.");

                            	}	
                            }
						}
						break;

					//If W played last, it's B's turn
                    //Same exact logic as the 'case' above.
                	case Board.W:

                		if(first=='Y'){
                			System.out.println("B player's turn");
                			System.out.println("AVAILABLE MOVES : " + board.AvailableMoves( Board.B) );
                			while(flag){
                				System.out.println("Please make your next move by typing column and row separated by a space: ");
                				col= sc.nextInt();
                				row= sc.nextInt();
                				if(board.isValidMove(row, col, Board.B)){
                					board.makeMove(row,col,Board.B);
                					flag=false;
                				}else{
                					if (board.AvailableMoves(Board.B)==0){
                						board.setLastDiscPlayed(Board.B);
                						flag =true;
                					}
                					System.out.println("Invalid move. Please make your move again.");
                				}
                			}

							}else{
								System.out.println("B player's turn");
								System.out.println("AVAILABLE MOVES : " + board.AvailableMoves( Board.B) );
								Move BMove =comp.MiniMax(board);
								board.makeMove(BMove.getRow(), BMove.getCol(), Board.B);

							}

                		break;

                	default:
                		break;
            	}
            	board.print();
            }
        }
        
        //The score is calculated, when the game is over, by counting the discs on the final board.    
            if (board.getBlackDisks()>board.getWhiteDisks()){
            System.out.println("THE BLACK PLAYER WINS!!!!");
        }else if  (board.getBlackDisks()<board.getWhiteDisks()) {
            System.out.println("THE WHITE PLAYER WINS!!!!");
        }else{
            System.out.println("TIE GAME!!!!");
        }
    }
}

