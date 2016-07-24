import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public abstract class Ghost extends Character{
	protected static final long serialVersionUID = 1L;

	private String path;
	private int gColor;
	private long startRoundTime;
	private boolean isDelayed=true;

	//Constructor
		public Ghost(gameManager controller, double startingPointX, double startingPointY, int gColor, int delay, StrategyInterface algorithm){
			this.deltaX=0;
			this.deltaY=0;
			this.gColor = gColor;
			this.characterSpeed = 10;
			this.startingXPosition = startingPointX;
			this.startingYPosition = startingPointY;
			this.xPosition=startingPointX;
			this.yPosition=startingPointY;
			this.gameManager = controller;
			this.strategy = algorithm;
			this.shouldChangeDirection = true;
			this.path = "Img/g" + gColor + ".png";
			this.characterImage = new ImageIcon(getClass().getClassLoader().getResource(path));
			this.startRoundTime = System.currentTimeMillis();
			this.myTimer=new Timer(characterSpeed,new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource()==myTimer){
						if(shouldChangeDirection == true){
							nextMove();
							shouldChangeDirection = false;
						}
						if(isDelayed){
							if(checkMovementAblillity(delay) == true)
								isDelayed = false;
						}
						else
							move();
					}
					repaint();
				}
			});
			this.setVisible(true);
			this.setFocusable(true);
			this.requestFocus(true);
			myTimer.start();
		}
		
		protected abstract void nextMove();

		public boolean checkMovementAblillity(int delay){
			if(System.currentTimeMillis() - startRoundTime > delay * 1000)
				return true;
			return false;
		}

		public void move(){				
				int xCoor = (int)Math.round((xPosition+deltaX)/gameManager.getSquareWidth());
				int yCoor = (int)Math.round((yPosition+deltaY)/gameManager.getSquareHeight());
				if(gameManager.getBoard().getCellType(yCoor, xCoor) instanceof WallCell)
					shouldChangeDirection=true;
				else if(yCoor >= 13 && yCoor <= 15 && xCoor >= 13 && xCoor <= 14){
					deltaX=0;
					deltaY=-1;
					yPosition=yPosition+deltaY;
				}
				else{
					this.xPosition=xPosition+deltaX;
					this.yPosition=yPosition+deltaY;
				}
		}

		public void resetRoundDelay(){
			this.isDelayed = true;
			this.startRoundTime = System.currentTimeMillis();
		}
}
