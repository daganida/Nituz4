
public class WeakGhost extends Ghost{
	
	public WeakGhost(Controller controller, double startingPointX, double startingPointY, int gColor,
			int delay) {
            
		super(controller, startingPointX, startingPointY, gColor, delay, new RandomStrategy());
	}

	@Override
	protected void nextMove() {
		strategy.nextMove(this);
	}

	@Override
	public boolean accept(Visitor v) {
		return v.visit(this);		
	}

	@Override
	public boolean visit(WeakGhost weakGhost) {
		return false;
	}

	@Override
	public boolean visit(StrongGhost strongGhost) {
		return false;
	}

	@Override
	public boolean visit(RegularPacman regularPacman) {
		return true;
	}

	@Override
	public boolean visit(MightyPacman mightyPacman) {
		return false;
	}

	@Override
	public boolean visit(SuperPacman superPacman) {
		return false;
	}
}
