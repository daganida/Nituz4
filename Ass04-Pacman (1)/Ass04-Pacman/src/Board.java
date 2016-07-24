
public class Board {

	private int width;
	private int height;
	private Cell[][] board;
	private GameData gameManager;
	
	public Board(int[][] b){
		gameManager = new GameData(3,0);
		width = b.length;
		height = b[0].length;
		board = new Cell[width][height];
		CellType type = null;
                fillBoard(b, type);
                
	}

    private void fillBoard(int[][] b, CellType type) {
        for(int i=0; i<width; i++)
            for(int j=0; j<height;j++){
                switch(b[i][j]){
                    case 1: type = new WallCell(); break;
                    case 2: type = new FoodCell(); break;
                    case 3: type = new EmptyCell(); break;
                }
                board[i][j] = new Cell(i,j,type);
            }
    }
    

	public Board(Board b) {
		this.gameManager = new GameData(3,0);
		this.width = b.width;
		this.height = b.height;
		this.board = new Cell[width][height];
                fillBoard(b);
	
	}
        
        public void  fillBoard(Board b) {
            	for(int i=0; i<width; i++)
			for(int j=0; j<height;j++){
				switch(b.getCellTypeValue(i, j)){
				case 1: board[i][j] = new Cell(i ,j, new WallCell()); break;
				case 2: board[i][j] = new Cell(i ,j, new FoodCell()); break;
				case 3: board[i][j] = new Cell(i ,j, new EmptyCell()); break;
				}
			}
            
        }

	public Cell[][] getBoard(){
		return board;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public int getMaximumFood() {
		int counter=0;
		for(int i= 0; i< width ; i++)
		{
			for (int j=0; j< height ; j++){
				CellType currentType = board[i][j].getCellType();
				if(currentType instanceof FoodCell){
					counter++;
				}
			}
		}
		return counter;
	}
	public int getFoodCurrentNumber(){
		int counter=0;
		for(int i= 0; i< width ; i++)
		{
			for (int j=0; j< height ; j++){
				if(board[i][j].getCellType() instanceof FoodCell){
					counter++;
				}
			}
		}
		return counter;
	}

	public CellType getCellType(int posX, int posY) {
		return board[posX][posY].getCellType();
	}
	
	public int getCellTypeValue(int posX, int posY) {
		return board[posX][posY].getCellTypeValue();
	}

	public void setCellType(int posX, int posY, int cellType) {
		switch(cellType){
		case 1: board[posX][posY].setCellType(new WallCell()); break;
		case 2: board[posX][posY].setCellType(new FoodCell()); break;
		case 3: board[posX][posY].setCellType(new EmptyCell()); break;
		case 4: board[posX][posY].setCellType(new MightyFoodCell()); break;
		case 5: board[posX][posY].setCellType(new SuperFoodCell()); break;
		}
	}
        public void addToCuurentScore(int scoreToAdd){
		gameManager.addScore(scoreToAdd);
	}
	public void setFinalTimer(String endString){
		gameManager.setEndTimer(endString);
	}
	public String getStartTimer(){
		return gameManager.getStartTimer();
	}
	public String getFinalTimer(){
		return gameManager.getEndTimer();
	}
	public String getFirstDecrementTimer(){
		return gameManager.getFirstDecTimer();
	}
	public void setFirstDecrementTimer(String dec){
		gameManager.setFirstDecTimer(dec);
	}
	public int getCuurentLives(){
		return gameManager.getLives();
	}
	public void decrementLives(){
		gameManager.decLives();
	}
	public int getCurrentScore(){
		return gameManager.getScore();
	}
	
}
