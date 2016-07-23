
public class StrongGhost extends Ghost{

	public StrongGhost(Controller controller, double startingPointX, double startingPointY, int gColor,
			int delay) {
		super(controller, startingPointX, startingPointY, gColor, delay, new smartStrategy());
	}

	@Override
	protected void nextMove() {
		strategy.nextMove(this, controller.getPacman());
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
		return true;
	}

	@Override
	public boolean visit(MightyPacman mightyPacman) {
		return true;
	}

	@Override
	public boolean visit(SuperPacman superPacman) {
		return false;
	}
}
