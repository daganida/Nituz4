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
	public Pacman(gameManager gameManager, double xStartingPosition, double yStartingPosition, int typeOfPlayer, int deltaX, int deltaY){
		setPacmanPreferences(typeOfPlayer, deltaX, deltaY, xStartingPosition, yStartingPosition, gameManager);
		this.myTimer=new Timer(characterSpeed/5,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grabFocus();
				if (e.getSource()==myTimer){
					if(typeOfPlayer == 1)
						move();
					else
						moveRandomaly();
					if(isRegular == false){
						if(checkIfWeNeedToChangeBackToRegular() == true){
							isRegular=true;
							gameManager.resetPacman();
						}
					}
				}
				repaint();
			}
		});
		
		this.setVisible(true);
		if(typeOfPlayer == 1)
			this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus(true);
		myTimer.start();
	}

    private void setPacmanPreferences(int typeOfPlayer, int deltaX1, int deltaY1, double xStartingPosition, double yStartingPosition, gameManager gameManager1) {
        this.playerType = typeOfPlayer;
        this.startingYPosition = yStartingPosition;
        this.xPosition=xStartingPosition;
        this.yPosition=yStartingPosition;
        this.isRegular = true;
        this.gameManager = gameManager1;
        this.characterImage = getCorrectIcon();
        this.shouldChangeDirection = true;
        this.strategy = new RandomStrategy();
        this.deltaX = deltaX1;
        this.deltaY = deltaY1;
        this.characterSpeed=10;
        this.startingXPosition = xStartingPosition;
    
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

	protected boolean checkIfWeNeedToChangeBackToRegular() {
		if(System.currentTimeMillis() - startRoundTime > 10 * 1000)
			return true;
		return false;
	}

	public int getCurrentPlayerType(){
		return playerType;
	}

	public void move(){
		if(gameManager.canMove(xPosition,yPosition) == true){
			xPosition=xPosition+deltaX;
			yPosition=yPosition+deltaY;
		}	
	}
	public void moveRandomaly(){
		if(shouldChangeDirection == true){
			strategy.nextMove(this);
			shouldChangeDirection = false;
		}
		int xCordinate = (int)Math.round((xPosition+deltaX)/gameManager.getSquareWidth());
		int yCordinates = (int)Math.round((yPosition+deltaY)/gameManager.getSquareHeight());

		if(!(gameManager.getBoard().getCellType(yCordinates, xCordinate).getCellType() == 1)) { 
                    updateGameManagerMovement();                                     
                }
		else{
                    shouldChangeDirection=true;

		}
	}

    private void updateGameManagerMovement() {
        this.xPosition=xPosition+deltaX;
        this.yPosition=yPosition+deltaY;
        if(deltaX == 1){
            this.characterImage = rightIcone();
        }
        else if(deltaX == -1){
            this.characterImage = leftIcone();
        }
        else if(deltaY ==1){
            this.characterImage = downIcone();
        }
        else
            this.characterImage = upIcone();
        gameManager.canMove(xPosition, yPosition);
    }

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT){
			characterImage = leftIcone();
			setMovementLeft();
		}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT){
			characterImage = rightIcone();
			setMovementRight();
		}
		if (e.getKeyCode()==KeyEvent.VK_UP){
			characterImage = upIcone();
			setMovementUp();
		}
		if (e.getKeyCode()==KeyEvent.VK_DOWN){
			characterImage = downIcone();
			setMovementDown();
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
		this.shouldChangeDirection = true;
	}
	public void initializeTransitionTimers(){
		this.startRoundTime = System.currentTimeMillis();
		this.isRegular = false;
	}
	
	public void deletePacmanFromMap(){
            String result = "";
            this.setVisible(false);
		this.removeKeyListener(this);
		this.setFocusable(false);
		this.requestFocus(false);
		this.strategy = null;
		this.myTimer.stop();
		
	}
	@Override
	public void replace(){
		this.xPosition = 14 * gameManager.getSquareWidth();
		this.yPosition = 23 * gameManager.getSquareHeight();
	}
}
