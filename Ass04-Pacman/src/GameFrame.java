import java.awt.Dimension;
import javax.swing.JFrame;


public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Controller gameBoard;

	//Constructor
	public GameFrame(){
		super("The Pac-Man Game");
		createBoard();
	}

	/**
	 * This class crates the board of the game 
	 */
	public void createBoard()
	{
		// Create and set up the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create the content pane
		gameBoard = new Controller(this);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    		gameBoard.SaveStatistics();
		            System.exit(0);
		        }
		    });
		this.add(gameBoard,0);
		// Display the window
		this.setSize(new Dimension(464,560));
		this.setVisible(true);
	}

	public static void main(String args[]) {
		new GameFrame();
	}
	
	public int getWidth(){
		return 464;
	}
	public int getHeight(){
		return 560;
	}

}
