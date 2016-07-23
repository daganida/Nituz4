import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public abstract class Pacman extends Character implements KeyListener{
	protected static final long serialVersionUID = 1L;

	private int playerType;
	private boolean isRegular;
	private long startRoundTime;

	//Constructor
	public Pacman(gameManager controller, double startingPointX, double startingPointY, int playerType, int deltaX, int deltaY){
		this.playerType = playerType;
		this.deltaX=deltaX;
		this.deltaY=deltaY;
		this.speed=10;
		this.startX = startingPointX;
		this.startY = startingPointY;
		this.x=startingPointX;
		this.y=startingPointY;
		this.isRegular = true;
		this.controller = controller;
		this.image = getCorrectIcon();
		this.toChangeDirection = true;
		this.strategy = new RandomStrategy();
		this.timer=new Timer(speed/5,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grabFocus();
				if (e.getSource()==timer){
					if(playerType == 1)
						move();
					else
						moveRandom();
					if(isRegular == false){
						if(checkTimerToChangedBack() == true){
							isRegular=true;
							controller.resetPacman();
						}
					}
				}
				repaint();
			}
		});
		
		this.setVisible(true);
		if(playerType == 1)
			this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus(true);
		timer.start();
	}

	private ImageIcon getCorrectIcon() {
		if(deltaX == 1){
			return rightIcone();
		}
		if(deltaX == -1){
			return leftIcone();
		}
		if(deltaY == 1){
			return downIcone();
		}
		if(deltaY == -1){
			return upIcone();
		}
		return leftIcone();
	}

	protected boolean checkTimerToChangedBack() {
		if(System.currentTimeMillis() - startRoundTime > 10 * 1000)
			return true;
		return false;
	}

	public int getPlayerType(){
		return playerType;
	}

	public void move(){
		if(controller.canMove(x,y) == true){
			x=x+deltaX;
			y=y+deltaY;
		}	
	}
	public void moveRandom(){
		if(toChangeDirection == true){
			strategy.nextMove(this);
			toChangeDirection = false;
		}
		int xCoor = (int)Math.round((x+deltaX)/controller.getSquareWidth());
		int yCoor = (int)Math.round((y+deltaY)/controller.getSquareHeight());

		if(controller.getBoard().getCellType(yCoor, xCoor).getCellType() == 1)
			toChangeDirection=true;
		else{
			this.x=x+deltaX;
			this.y=y+deltaY;
			if(deltaX == 1){
				this.image = rightIcone();
			}
			else if(deltaX == -1){
				this.image = leftIcone();
			}
			else if(deltaY ==1){
				this.image = downIcone();
			}
			else
				this.image = upIcone();
			controller.canMove(x, y);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT){
			image = leftIcone();
			setMoveLeft();
		}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT){
			image = rightIcone();
			setMoveRight();
		}
		if (e.getKeyCode()==KeyEvent.VK_UP){
			image = upIcone();
			setMoveUp();
		}
		if (e.getKeyCode()==KeyEvent.VK_DOWN){
			image = downIcone();
			setMoveDown();
		}
	}
	public ImageIcon leftIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/PacmanLeft.png"));
	}
	public ImageIcon rightIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/PacmanRight.png"));
	}
	public ImageIcon upIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/PacmanUp.png"));
	}
	public ImageIcon downIcone() {
		return new ImageIcon(getClass().getClassLoader().getResource("Img/PacmanDown.png"));
	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
	}
	public void setDirectionToTrue() {
		this.toChangeDirection = true;
	}
	public void initializeTransitionTimers(){
		this.startRoundTime = System.currentTimeMillis();
		this.isRegular = false;
	}
	
	public void destroyPacman(){
		this.strategy = null;
		this.timer.stop();
		this.setVisible(false);
		this.removeKeyListener(this);
		this.setFocusable(false);
		this.requestFocus(false);
	}
	@Override
	public void replace(){
		this.x = 14 * controller.getSquareWidth();
		this.y = 23 * controller.getSquareHeight();
	}
}
