
public abstract class CellType {
	protected int myType;
	
	public CellType(int type){
		this.myType=type;
	}
	
	public int getCellType(){
		return myType;
	}
}
