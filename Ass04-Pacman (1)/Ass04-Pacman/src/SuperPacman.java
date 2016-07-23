import javax.swing.ImageIcon;

public class SuperPacman extends Pacman{
	protected static final long serialVersionUID = 1L;

	public SuperPacman(Controller controller, double startingPointX, double startingPointY, int playerType, int deltaX, int deltaY) {
		super(controller, startingPointX, startingPointY, playerType, deltaX, deltaY);
		super.initializeTransitionTimers();
	}

	@Override
	public boolean accept(Visitor v) {
		return v.visit(this);		
	}

	@Override
	public boolean visit(WeakGhost weakGhost) {
		return true;
	}

	@Override
	public boolean visit(StrongGhost strongGhost) {
		return true;
	}

	@Override
	public boolean visit(RegularPacman regularPacman) {
		return false;
	}

	@Override
	public boolean visit(MightyPacman mightyPacman) {
		return false;
	}

	@Override
	public boolean visit(SuperPacman superPacman) {
		return false;
	}
	
	@Override
	public ImageIcon leftIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/SuperPacmanLeft.png"));
	}
	public ImageIcon rightIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/SuperPacmanRight.png"));
	}
	public ImageIcon upIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/SuperPacmanUp.png"));
	}
	public ImageIcon downIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/SuperPacmanDown.png"));
	}
}
