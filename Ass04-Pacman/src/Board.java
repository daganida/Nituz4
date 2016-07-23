
public class Board {

	private int width;
	private int height;
	private Cell[][] board;
	private GameData gameData;
	
	public Board(int[][] b){
		this.gameData = new GameData(3,0);
		this.width = b.length;
		this.height = b[0].length;
		board = new Cell[width][height];
		CellType type = null;
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

	public Board(Board copy) {
		this.gameData = new GameData(3,0);
		this.width = copy.width;
		this.height = copy.height;
		this.board = new Cell[width][height];
		for(int i=0; i<width; i++)
			for(int j=0; j<height;j++){
				switch(copy.getCellTypeValue(i, j)){
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

	public int getMaxFood() {
		int count=0;
		for(int i= 0; i< width ; i++)
		{
			for (int j=0; j< height ; j++){
				CellType currType = board[i][j].getCellType();
				if(currType instanceof FoodCell){
					count++;
				}
			}
		}
		return count;
	}
	public int getCurrentFoodNumber(){
		int count=0;
		for(int i= 0; i< width ; i++)
		{
			for (int j=0; j< height ; j++){
				if(board[i][j].getCellType() instanceof FoodCell){
					count++;
				}
			}
		}
		return count;
	}

	public CellType getCellType(int i, int j) {
		return board[i][j].getCellType();
	}
	
	public int getCellTypeValue(int i, int j) {
		return board[i][j].getCellTypeValue();
	}

	public void setCellType(int yCoor, int xCoor, int type) {
		switch(type){
		case 1: board[yCoor][xCoor].setCellType(new WallCell()); break;
		case 2: board[yCoor][xCoor].setCellType(new FoodCell()); break;
		case 3: board[yCoor][xCoor].setCellType(new EmptyCell()); break;
		case 4: board[yCoor][xCoor].setCellType(new MightyFoodCell()); break;
		case 5: board[yCoor][xCoor].setCellType(new SuperFoodCell()); break;
		}
	}
	public int getLives(){
		return gameData.getLives();
	}
	public void decLives(){
		gameData.decLives();
	}
	public int getScore(){
		return gameData.getScore();
	}
	public void addScore(int i){
		gameData.addScore(i);
	}
	public void setEndTimer(String end){
		gameData.setEndTimer(end);
	}
	public String getStartTimer(){
		return gameData.getStartTimer();
	}
	public String getEndTimer(){
		return gameData.getEndTimer();
	}
	public String getFirstDecTimer(){
		return gameData.getFirstDecTimer();
	}
	public void setFirstDecTimer(String dec){
		gameData.setFirstDecTimer(dec);
	}
}
