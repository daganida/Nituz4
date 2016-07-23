
public class RegularPacman extends Pacman{
	protected static final long serialVersionUID = 1L;

	public RegularPacman(Controller controller, double startingPointX, double startingPointY, int playerType, int deltaX, int deltaY) {
		super(controller, startingPointX, startingPointY, playerType, deltaX, deltaY);
	}

	@Override
	public boolean accept(Visitor v) {
		return v.visit(this);		
	}

	@Override
	public boolean visit(WeakGhost weakGhost) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean visit(StrongGhost strongGhost) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean visit(RegularPacman regularPacman) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean visit(MightyPacman mightyPacman) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean visit(SuperPacman superPacman) {
		// TODO Auto-generated method stub
		return false;
	}
}
