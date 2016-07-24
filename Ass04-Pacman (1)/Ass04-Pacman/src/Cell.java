
public class Cell {
	private int posX;
	private int posY;
	private CellType cellType;
	
	public Cell(int xCord, int yCord, CellType cellType){
		this.posX = xCord;
		this.posY = yCord;
		this.cellType = cellType;
	}
	
	public CellType getCellType(){
		return cellType;
	}
	public int getCellTypeValue(){
		return cellType.getCellType();
	}
	public void setCellType(CellType type){
		this.cellType = type;
	}
}
