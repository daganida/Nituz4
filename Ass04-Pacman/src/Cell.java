
public class Cell {
	private int x;
	private int y;
	private CellType type;
	
	public Cell(int x, int y, CellType type){
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public CellType getCellType(){
		return type;
	}
	public int getCellTypeValue(){
		return type.getCellType();
	}
	public void setCellType(CellType type){
		this.type = type;
	}
}
