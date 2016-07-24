import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public abstract class Ghost extends Character{
	protected static final long serialVersionUID = 1L;

	private String ghostPath;
	private int ghostColor;
	private long startRoundTripTime;
	private boolean isDelayed=true;

	//Constructor
		public Ghost(gameManager gameManager, double xStartPosition, double yStartPosition, int ghostColor, int currentDelay, StrategyInterface strategyChosen){
			setGhostPreferences(ghostColor, xStartPosition, yStartPosition);
			this.gameManager = gameManager;
			this.strategy = strategyChosen;
			this.shouldChangeDirection = true;
			this.ghostPath = "Img/g" + ghostColor + ".png";
			this.characterImage = new ImageIcon(getClass().getClassLoader().getResource(ghostPath));
			this.startRoundTripTime = System.currentTimeMillis();
			setTimerEvent(currentDelay);
			setVisibilityAndStartTimer();
		}

    private void setTimerEvent(int currentDelay) {
        this.myTimer=new Timer(characterSpeed,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==myTimer){
                    if(shouldChangeDirection == true){
                        nextMove();
                        shouldChangeDirection = false;
                    }
                    if(isDelayed){
                        if(checkTheAbilityToMove(currentDelay) == true)
                            isDelayed = false;
                    }
                    else
                        move();
                }
                repaint();
            }
        });
    }

                private void setVisibilityAndStartTimer() {
                    this.setVisible(true);
                    this.setFocusable(true);
                    this.requestFocus(true);
                    myTimer.start();
                }

                private void setGhostPreferences(int ghostColor1, double xStartPosition, double yStartPosition) {
                    this.deltaX=0;
                    this.deltaY=0;
                    this.ghostColor = ghostColor1;
                    this.characterSpeed = 10;
                    this.startingXPosition = xStartPosition;
                    this.startingYPosition = yStartPosition;
                    this.xPosition=xStartPosition;
                    this.yPosition=yStartPosition;
                }
		
		protected abstract void nextMove();

		public boolean checkTheAbilityToMove(int delay){
			if(System.currentTimeMillis() - startRoundTripTime > delay * 1000)
				return true;
			return false;
		}

		public void move(){				
				int xCordination = (int)Math.round((xPosition+deltaX)/gameManager.getSquareWidth());
				int yCordination = (int)Math.round((yPosition+deltaY)/gameManager.getSquareHeight());
                                
                              tryToMove(yCordination, xCordination);
		}

                private void tryToMove(int yCordination, int xCordination) {
                    if(gameManager.getBoard().getCellType(yCordination, xCordination) instanceof WallCell)
                        shouldChangeDirection=true;
                    else if(xCordination >= 13 && xCordination <= 14 && yCordination >= 13 && yCordination <= 15){
                        deltaX=0;
                        deltaY=-1;
                        yPosition=yPosition+deltaY;
                    }
                    else{
                        this.xPosition=xPosition+deltaX;
                        this.yPosition=yPosition+deltaY;
                    }
                }

		public void resetRoundTripDelay(){
			this.isDelayed = true;
			this.startRoundTripTime = System.currentTimeMillis();
		}
}
