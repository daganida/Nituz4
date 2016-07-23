
public class smartStrategy implements StrategyInterface{

    


	@Override
	public void nextMove(Character ghost, Character pacman) {
		String options = "";
                //test

		Board board = ghost.getBoard().getBoard();
                //board size
		double width = ghost.getBoard().getSquareWidth();
		double height = ghost.getBoard().getSquareHeight();
                //pacman cradentials
                int pacmanX = (int)Math.round((pacman.getXIndex())/width);
		int pacmanY = (int)Math.round((pacman.getYIndex())/height);
                //ghost cradentials        
		int ghostX = (int)Math.round((ghost.getXIndex())/width);
		int ghostY = (int)Math.round((ghost.getYIndex())/height);

                
                options = fillAllPossibleOptions(ghostX,ghostY,board);
		String direction = "";
                //the start of the distance
		int minDistance = Integer.MAX_VALUE;
                int currentDistance;
                
                double xDistanceFromPacman,yDistanceFromPacman;
		
		if(options.contains("L")== true){
                            //
                             xDistanceFromPacman = Math.abs(ghostX-1-pacmanX);
                             yDistanceFromPacman = Math.abs(ghostY-pacmanY);
                            xDistanceFromPacman = Math.pow(xDistanceFromPacman,2);	
                            yDistanceFromPacman = Math.pow(yDistanceFromPacman,2); 
                             currentDistance = (int)Math.sqrt(xDistanceFromPacman+yDistanceFromPacman);
                            if (currentDistance<minDistance)
                            {
                                    direction = "L";
                                    minDistance=currentDistance; 
                            }
		}
		if(options.contains("R")== true){
                            xDistanceFromPacman = Math.abs(ghostX+1-pacmanX);
                            yDistanceFromPacman = Math.abs(ghostY-pacmanY);
                           xDistanceFromPacman = Math.pow(xDistanceFromPacman,2);	
                           yDistanceFromPacman = Math.pow(yDistanceFromPacman,2); 
                            currentDistance = (int)Math.pow(xDistanceFromPacman+yDistanceFromPacman, 0.5);
                           if (currentDistance<minDistance)
                           {
                                   direction = "R";
                                   minDistance=currentDistance; 
                           }
		}	
		if(options.contains("U")== true){
                            xDistanceFromPacman = Math.abs(ghostX-pacmanX);
                            yDistanceFromPacman = Math.abs(ghostY-1-pacmanY);
                           xDistanceFromPacman = Math.pow(xDistanceFromPacman,2);	
                           yDistanceFromPacman = Math.pow(yDistanceFromPacman,2); 
                            currentDistance = (int)Math.pow(xDistanceFromPacman+yDistanceFromPacman, 0.5);
                           if (currentDistance<minDistance){
                                   direction = "U";
                                   minDistance=currentDistance; 
                           }
		}
		if(options.contains("D")== true){
                            xDistanceFromPacman = Math.abs(ghostX-pacmanX);
                            yDistanceFromPacman = Math.abs(ghostY+1-pacmanY);
                            xDistanceFromPacman = Math.pow(xDistanceFromPacman,2);	
                            yDistanceFromPacman = Math.pow(yDistanceFromPacman,2); 
                            currentDistance = (int)Math.pow(xDistanceFromPacman+yDistanceFromPacman, 0.5);
                           if (currentDistance<minDistance){
                                   direction = "D";
                                   minDistance=currentDistance; 
                           }
		}
                
                moveGhostByDirection(direction,ghost);
	
	}

	@Override
	public void nextMove(Character character) {
		
	}

    private String fillAllPossibleOptions(Character ghost, Character pacman) 
    {
                String options = "";
		Board board = ghost.getBoard().getBoard();
		double squareWidth = ghost.getBoard().getSquareWidth();
		double squareHeight = ghost.getBoard().getSquareHeight();
		int ghostX = (int)Math.round((ghost.getXIndex())/squareWidth);
		int ghostY = (int)Math.round((ghost.getYIndex())/squareHeight);
		int pacmanX = (int)Math.round((pacman.getXIndex())/squareWidth);
		int pacmanY = (int)Math.round((pacman.getYIndex())/squareHeight);
                return options;
                
    }

    private String fillAllPossibleOptions(int ghostX, int ghostY,Board board) 
    {
               String options = "";
        
              if(!(board.getCellType(ghostY,ghostX-1) instanceof WallCell) && ghostX > 1 ){ //Can Move Left
			options+="L";
		}
		if(!(board.getCellType(ghostY,ghostX+1) instanceof WallCell) && ghostX < board.getHeight()-1){ //Can Move Right
			options+="R";
		}
		if(!(board.getCellType(ghostY-1,ghostX)instanceof WallCell) && ghostY > 1){ //Can Move Up
			options+="U";
		}
		if(!(board.getCellType(ghostY+1,ghostX) instanceof WallCell) && ghostY < board.getHeight()-1){
			options+="D";
		}
                return options;    
    }

    private void moveGhostByDirection(String direction,Character ghost)
    {
        	switch(direction){
		case "L":{ghost.setMoveLeft();break;}
		case "R":{ghost.setMoveRight();break;}
		case "U":{ghost.setMoveUp();break;}
		case "D":{ghost.setMoveDown();break;}
		}
    }
}
