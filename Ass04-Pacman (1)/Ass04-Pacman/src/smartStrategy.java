
public class smartStrategy implements StrategyInterface{

    


	@Override
	public void nextMove(Character ghost, Character pacman) {
		String options = "";
                

		Board board = ghost.getBoard().getBoard();
                //board size
		double width = ghost.getBoard().getSquareWidth();
		double height = ghost.getBoard().getSquareHeight();
                //pacman cradentials
                int pacmanX = (int)Math.round((pacman.getXIndexPosition())/width);
		int pacmanY = (int)Math.round((pacman.getYIndexPosition())/height);
                //ghost cradentials        
		int ghostX = (int)Math.round((ghost.getXIndexPosition())/width);
		int ghostY = (int)Math.round((ghost.getYIndexPosition())/height);

                
                options = fillAllPossibleOptions(ghostX,ghostY,board);
		String direction = "";
                //the start of the distance
		int minDistance = Integer.MAX_VALUE;
                int currentDistance;             
                boolean isMinDistance = false;
                double xDistanceFromPacman,yDistanceFromPacman;
		
		if(options.contains("L")){
                           
                               isMinDistance = isMinDistance(Math.abs(ghostX-1-pacmanX),Math.abs(ghostY-pacmanY),minDistance);
                            if(isMinDistance){
                            currentDistance = (int)calculateDistance(Math.abs(ghostX-1-pacmanX),Math.abs(ghostY-pacmanY));
                                      direction = "L";
                                   minDistance=currentDistance; 
                            }
		}
		if(options.contains("R")){
                             isMinDistance = isMinDistance(Math.abs(ghostX+1-pacmanX),Math.abs(ghostY-pacmanY),minDistance);
                            if(isMinDistance){
                            currentDistance = (int)calculateDistance(Math.abs(ghostX+1-pacmanX),Math.abs(ghostY-pacmanY));
                                      direction = "R";
                                   minDistance=currentDistance; 
                            }
		}	
		if(options.contains("U")){
                           isMinDistance = isMinDistance(Math.abs(ghostX-pacmanX),Math.abs(ghostY-1-pacmanY),minDistance);
                            if(isMinDistance){
                            currentDistance = (int)calculateDistance(Math.abs(ghostX-pacmanX),Math.abs(ghostY-1-pacmanY));
                                      direction = "U";
                                   minDistance=currentDistance; 
                            }
		}
		if(options.contains("D")){
                            isMinDistance = isMinDistance(Math.abs(ghostX-pacmanX),Math.abs(ghostY+1-pacmanY),minDistance);
                            if(isMinDistance){
                            currentDistance = (int)calculateDistance(Math.abs(ghostX-pacmanX),Math.abs(ghostY+1-pacmanY));
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
		int ghostX = (int)Math.round((ghost.getXIndexPosition())/squareWidth);
		int ghostY = (int)Math.round((ghost.getYIndexPosition())/squareHeight);
		int pacmanX = (int)Math.round((pacman.getXIndexPosition())/squareWidth);
		int pacmanY = (int)Math.round((pacman.getYIndexPosition())/squareHeight);
                return options;
                
    }

    private String fillAllPossibleOptions(int ghostX, int ghostY,Board board) 
    {
               String options = "";
        
              if(!(board.getCellType(ghostY,ghostX-1) instanceof WallCell) && ghostX > 1 ){ //left
			options+="L";
		}
		if(!(board.getCellType(ghostY,ghostX+1) instanceof WallCell) && ghostX < board.getHeight()-1){ //right
			options+="R";
		}
		if(!(board.getCellType(ghostY-1,ghostX)instanceof WallCell) && ghostY > 1){ //up
			options+="U";
		}
		if(!(board.getCellType(ghostY+1,ghostX) instanceof WallCell) && ghostY < board.getHeight()-1){ //down
			options+="D";
		}
                return options;    
    }

    private void moveGhostByDirection(String direction,Character ghost)
    {
        	switch(direction){
		case "L":{ghost.setMovementLeft();break;}
		case "R":{ghost.setMovementRight();break;}
		case "U":{ghost.setMovementUp();break;}
		case "D":{ghost.setMovementDown();break;}
		}
    }

    private boolean isMinDistance(double xDistance, double yDistance,int minDistance)
    {
                                  int currentDistance;
                                  xDistance = Math.pow(xDistance,2);	
                                  yDistance = Math.pow(yDistance,2); 
                                  currentDistance = (int)Math.sqrt(xDistance+yDistance);
                                       if(currentDistance < minDistance)
                                        return true;
                                       else return false; 
    }
    
    private double calculateDistance(double xDistance,double yDistance) 
    { 
        double result = 0 ;
        xDistance = Math.pow(xDistance,2);	
        yDistance = Math.pow(yDistance,2); 
        result = (int)Math.sqrt(xDistance+yDistance);
        return result;
  
    }
}
