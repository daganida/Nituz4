import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Character extends JPanel implements Visited, Visitor {
	protected static final long serialVersionUID = 1L;

	protected double x;
	protected double y;
	protected int deltaX;
	protected int deltaY;
	protected Timer timer;
	protected ImageIcon image;
	protected double startX;
	protected double startY;
	protected Controller controller;
	protected int speed;
	protected StrategyInterface algorithm;
	protected boolean toChangeDirection;
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(image.getImage(), (int)x, (int)y, (int)controller.getSquareWidth(), (int) controller.getSquareHeight(), null);
	}
	
	/**
	 * This function sets the creature direction to be up 
	 */	
	public void setMoveUp(){
		deltaX=0; deltaY=-1;
	}
	/**
	 * This function sets the creature direction to be down 
	 */	
	public void setMoveDown(){
		deltaX=0; deltaY=1;
	}
	/**
	 * This function sets the creature direction to be right 
	 */	
	public void setMoveRight(){
		deltaX=1; deltaY=0;
	}
	/**
	 * This function sets the creature direction to be left 
	 */	
	public void setMoveLeft(){
		deltaX=-1; deltaY=0;
	}

	/**
	 * @return x = x index on the board
	 */	
	public double getXIndex(){
		return this.x;
	}
	
	/**
	 * @return y = y index on the board
	 */	
	public double getYIndex(){
		return this.y;
	}
	
	/**
	 * @param x = x index on the board
	 */	
	public void setXindex(double x){
		this.x=x;
	}
	
	/**
	 * @param y = y index on the board
	 */	
	public void setYindex(double y){
		this.y=y;
	}
	
	/**
	 * initial the creature direction
	 */	
	public void zeroDeltaXY(){
		this.deltaX=0;
		this.deltaY=0;
	}
	
	public abstract void move();
	
	public void restart() {
		this.timer.start();
	}
	
	public void stop() {
		this.timer.stop();
	}
	
	public void replace(){
		this.x = startX;
		this.y = startY;
	}
	
	public int getDeltaX(){
		return deltaX;
	}
	
	public int getDeltaY(){
		return deltaY;
	}
	public Controller getBoard(){
		return controller;
	}
	
	public boolean eats(Character character){
		return character.accept(this);
	}
}
