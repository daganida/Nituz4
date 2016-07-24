import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Character extends JPanel implements Visited, Visitor {
	protected static final long serialVersionUID = 1L;
        protected double startingXPosition;
	protected double startingYPosition;
	protected gameManager gameManager;
	protected int characterSpeed;
	protected StrategyInterface strategy;
	protected boolean shouldChangeDirection;
	protected double xPosition;
	protected double yPosition;
	protected int deltaX;
	protected int deltaY;
	protected Timer myTimer;
	protected ImageIcon characterImage;
	
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(characterImage.getImage(), (int)xPosition, (int)yPosition, (int)gameManager.getSquareWidth(), (int) gameManager.getSquareHeight(), null);
	}
	
	/**
	 * This function sets the creature direction to be up 
	 */	
	public void setMovementUp(){
		deltaX=0;
                deltaY=-1;
	}
	/**
	 * This function sets the creature direction to be down 
	 */	
	public void setMovementDown(){
		deltaX=0;
                deltaY=1;
	}
	/**
	 * This function sets the creature direction to be right 
	 */	
	public void setMovementRight(){
		deltaX=1;
                deltaY=0;
	}
	/**
	 * This function sets the creature direction to be left 
	 */	
	public void setMovementLeft(){
		deltaX=-1;
                deltaY=0;
	}

	/**
	 * @return x = x index on the board
	 */	
	public double getXIndexPosition(){
		return this.xPosition;
	}
	
	/**
	 * @return y = y index on the board
	 */	
	public double getYIndexPosition(){
		return this.yPosition;
	}
	
	/**
	 * @param x = x index on the board
	 */	
	public void setXIndexPosition(double x){
		this.xPosition=x;
	}
        	public void replace(){
		this.xPosition = startingXPosition;
		this.yPosition = startingYPosition;
	}
	
	public int getDeltaX(){
		return deltaX;
	}
	
	public int getDeltaY(){
		return deltaY;
	}
	public gameManager getBoard(){
		return gameManager;
	}
	
	public boolean eats(Character character){
		return character.accept(this);
	}
	
	/**
	 * @param y = y index on the board
	 */	
	public void setYIndexPosition(double y){
		this.yPosition=y;
	}
	
	/**
	 * initial the creature direction
	 */	
	public void resetDeltaXAndY(){
		this.deltaX=0;
		this.deltaY=0;
	}
	
	public abstract void move();
	
	public void restartTimer() {
		this.myTimer.start();
	}
	
	public void stopTimer() {
		this.myTimer.stop();
	}
	

}
