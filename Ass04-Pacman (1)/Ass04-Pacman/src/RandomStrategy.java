
public class RandomStrategy implements StrategyInterface{

	public void nextMove(Character character) {
		String options = "";
		Board board = character.getBoard().getBoard();
		double squareWidth = character.getBoard().getSquareWidth();
		double squareHeight = character.getBoard().getSquareHeight();
		int ghostX = (int)Math.round((character.getXIndex())/squareWidth);
		int ghostY = (int)Math.round((character.getYIndex())/squareHeight);		
		
		if(ghostX > 1 && (board.getCellType(ghostY,ghostX-1).getCellType() != 1)){ //Can Move Left
			options+="1";
		}
		if(ghostX < board.getWidth()-1 && (board.getCellType(ghostY,ghostX+1).getCellType() != 1)){ //Can Move Right
			options+="2";
		}
		if(ghostY > 1 && (board.getCellType(ghostY-1,ghostX).getCellType() != 1)){ //Can Move Up
			options+="3";
		}
		if(ghostY < board.getHeight()-1 && (board.getCellType(ghostY+1,ghostX).getCellType() != 1)){
			options+="4";
		}

		int random = (int)(Math.random() * options.length() + 1);
		int choice = options.charAt(random-1)-'0';
		switch(choice){
			case 1:{character.setMoveLeft();break;}
			case 2:{character.setMoveRight();break;}
			case 3:{character.setMoveUp();break;}
			case 4:{character.setMoveDown();break;}
		}
	}

	@Override
	public void nextMove(Character sCharacter, Character dCharacter2) {		
	}
}
