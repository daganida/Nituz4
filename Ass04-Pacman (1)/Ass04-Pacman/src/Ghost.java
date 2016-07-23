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
		public Ghost(Controller controller, double startingPointX, double startingPointY, int gColor, int delay, StrategyInterface algorithm){
			this.deltaX=0;
			this.deltaY=0;
			this.gColor = gColor;
			this.speed = 10;
			this.startX = startingPointX;
			this.startY = startingPointY;
			this.x=startingPointX;
			this.y=startingPointY;
			this.controller = controller;
			this.strategy = algorithm;
			this.toChangeDirection = true;
			this.path = "Img/g" + gColor + ".png";
			this.image = new ImageIcon(getClass().getClassLoader().getResource(path));
			this.startRoundTime = System.currentTimeMillis();
			this.timer=new Timer(speed,new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource()==timer){
						if(toChangeDirection == true){
							nextMove();
							toChangeDirection = false;
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
			timer.start();
		}
		
		protected abstract void nextMove();

		public boolean checkMovementAblillity(int delay){
			if(System.currentTimeMillis() - startRoundTime > delay * 1000)
				return true;
			return false;
		}

		public void move(){				
				int xCoor = (int)Math.round((x+deltaX)/controller.getSquareWidth());
				int yCoor = (int)Math.round((y+deltaY)/controller.getSquareHeight());
				if(controller.getBoard().getCellType(yCoor, xCoor) instanceof WallCell)
					toChangeDirection=true;
				else if(yCoor >= 13 && yCoor <= 15 && xCoor >= 13 && xCoor <= 14){
					deltaX=0;
					deltaY=-1;
					y=y+deltaY;
				}
				else{
					this.x=x+deltaX;
					this.y=y+deltaY;
				}
		}

		public void resetRoundDelay(){
			this.isDelayed = true;
			this.startRoundTime = System.currentTimeMillis();
		}
}
