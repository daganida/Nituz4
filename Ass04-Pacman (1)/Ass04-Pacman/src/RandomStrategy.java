
public class RandomStrategy implements StrategyInterface{

	public void nextMove(Character ghost) {
		String options = "";
		Board b = ghost.getBoard().getBoard();
                //board cradentials
		double width = ghost.getBoard().getSquareWidth();
		double height = ghost.getBoard().getSquareHeight();
                //ghost cradentials
		int ghostX = (int)Math.round((ghost.getXIndexPosition())/width);
		int ghostY = (int)Math.round((ghost.getYIndexPosition())/height);
                //get all possible directions
                options = fillAllPossibleOptions(ghostX,ghostY,b);
                //choose randomaly
		int random = (int)(Math.random() * options.length() + 1);
		int direction = options.charAt(random-1) - '0';
                
                makeMovement(direction,ghost);

	}

	@Override
	public void nextMove(Character sCharacter, Character dCharacter2) {		
	}

    private String fillAllPossibleOptions(int ghostX, int ghostY, Board board) {
              String options = "";
        
              if(!(board.getCellType(ghostY,ghostX-1) instanceof WallCell) && ghostX > 1 ){ //Can Move Left
			options+="1";
		}
		if(!(board.getCellType(ghostY,ghostX+1) instanceof WallCell) && ghostX < board.getHeight()-1){ //Can Move Right
			options+="2";
		}
		if(!(board.getCellType(ghostY-1,ghostX)instanceof WallCell) && ghostY > 1){ //Can Move Up
			options+="3";
		}
		if(!(board.getCellType(ghostY+1,ghostX) instanceof WallCell) && ghostY < board.getHeight()-1){
			options+="4";
		}
                return options;  
    }

    private void makeMovement(int direction,Character character) 
    {
        
		switch(direction){
			case 1:{character.setMovementLeft();break;}
			case 2:{character.setMovementRight();break;}
			case 3:{character.setMovementUp();break;}
			case 4:{character.setMovementDown();break;}
		}
    }
}
