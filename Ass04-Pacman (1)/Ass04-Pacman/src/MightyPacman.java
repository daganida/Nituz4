import javax.swing.ImageIcon;

public class MightyPacman extends Pacman{
	protected static final long serialVersionUID = 1L;

	public MightyPacman(gameManager controller, double startingPointX, double startingPointY, int playerType, int deltaX, int deltaY) {
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
	
	@Override
	public ImageIcon leftIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/MightyPacmanLeft.png"));
	}
	public ImageIcon rightIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/MightyPacmanRight.png"));
	}
	public ImageIcon upIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/MightyPacmanUp.png"));
	}
	public ImageIcon downIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/MightyPacmanDown.png"));
	}
}
