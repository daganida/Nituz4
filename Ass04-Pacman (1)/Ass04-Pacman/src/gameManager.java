import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class gameManager extends JPanel{
	private static final long serialVersionUID = 1L;
	private Pacman pacman;
	private JPanel background;
	private JPanel mainBackground;
	private JPanel gameOverPanel;
	private JPanel winGamePanel;
	private JPanel itemsBoard;
	private JPanel gameDataPanel;
	private JLayeredPane multiBoard;
	private int mapHeight;
	private int mapWidth;
	private int frameHeight;
	private int frameWidth;
	private int bottomPanelHeight = 25;
	private double squareHeight;
	private double squareWidth;
	private ArrayList<User> users;
	private ArrayList<Integer> statistics;
	private int initPacX;
	private int initPacY;	
	private boolean movedBefore = false;
	private Ghost[] ghosts;
	private int maxFood=0;
	private Color bgColor = Color.decode("#110e3d");
	private int playerMode = 0;
	private int gameMode = 0;
	private String membersPath;
	private String statisticsPath;
	private String currentUser;
	final int W=1; // Wall.
	final int F=2; // Crossroads with food 
	final int E=3; // Empty crossroads
	private int[][] bMat = {
			//-----------------------X-----------------------------//
			{W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W},
			{W,F,F,F,F,F,F,F,F,F,F,F,F,W,W,F,F,F,F,F,F,F,F,F,F,F,F,W},
			{W,F,W,W,W,W,F,W,W,W,W,W,F,W,W,F,W,W,W,W,W,F,W,W,W,W,F,W},
			{W,F,W,W,W,W,F,W,W,W,W,W,F,W,W,F,W,W,W,W,W,F,W,W,W,W,F,W},
			{W,F,W,W,W,W,F,W,W,W,W,W,F,W,W,F,W,W,W,W,W,F,W,W,W,W,F,W},
			{W,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,W},
			{W,F,W,W,W,W,F,W,W,F,W,W,W,W,W,W,W,W,F,W,W,F,W,W,W,W,F,W},
			{W,F,W,W,W,W,F,W,W,F,W,W,W,W,W,W,W,W,F,W,W,F,W,W,W,W,F,W},
			{W,F,F,F,F,F,F,W,W,F,F,F,F,W,W,F,F,F,F,W,W,F,F,F,F,F,F,W},
			{W,W,W,W,W,W,F,W,W,W,W,W,F,W,W,F,W,W,W,W,W,F,W,W,W,W,W,W},
			{E,E,E,E,E,W,F,W,W,W,W,W,F,W,W,F,W,W,W,W,W,F,W,E,E,E,E,E},
			{E,E,E,E,E,W,F,W,W,F,F,F,F,F,F,F,F,F,F,W,W,F,W,E,E,E,E,E},
			{E,E,E,E,E,W,F,W,W,F,W,W,W,E,E,W,W,W,F,W,W,F,W,E,E,E,E,E},
			{W,W,W,W,W,W,F,W,W,F,W,E,E,E,E,E,E,W,F,W,W,F,W,W,W,W,W,W},
			{F,F,F,F,F,F,F,F,F,F,W,E,E,E,E,E,E,W,F,F,F,F,F,F,F,F,F,F},
			{W,W,W,W,W,W,F,W,W,F,W,E,E,E,E,E,E,W,F,W,W,F,W,W,W,W,W,W},
			{E,E,E,E,E,W,F,W,W,F,W,W,W,W,W,W,W,W,F,W,W,F,W,E,E,E,E,E},
			{E,E,E,E,E,W,F,W,W,F,F,F,F,F,F,F,F,F,F,W,W,F,W,E,E,E,E,E},
			{E,E,E,E,E,W,F,W,W,F,W,W,W,W,W,W,W,W,F,W,W,F,W,E,E,E,E,E},
			{W,W,W,W,W,W,F,W,W,F,W,W,W,W,W,W,W,W,F,W,W,F,W,W,W,W,W,W},
			{W,F,F,F,F,F,F,F,F,F,F,F,F,W,W,F,F,F,F,F,F,F,F,F,F,F,F,W},
			{W,F,W,W,W,W,F,W,W,W,W,W,F,W,W,F,W,W,W,W,W,F,W,W,W,W,F,W},
			{W,F,W,W,W,W,F,W,W,W,W,W,F,W,W,F,W,W,W,W,W,F,W,W,W,W,F,W},
			{W,F,F,F,W,W,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,W,W,F,F,F,W},
			{W,W,W,F,W,W,F,W,W,F,W,W,W,W,W,W,W,W,F,W,W,F,W,W,F,W,W,W},
			{W,W,W,F,W,W,F,W,W,F,W,W,W,W,W,W,W,W,F,W,W,F,W,W,F,W,W,W},
			{W,F,F,F,F,F,F,W,W,F,F,F,F,W,W,F,F,F,F,W,W,F,F,F,F,F,F,W},
			{W,F,W,W,W,W,W,W,W,W,W,W,F,W,W,F,W,W,W,W,W,W,W,W,W,W,F,W},
			{W,F,W,W,W,W,W,W,W,W,W,W,F,W,W,F,W,W,W,W,W,W,W,W,W,W,F,W},
			{W,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,W},
			{W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W}
	};
	
	private Board initialBoard;
	private Board currentBoard;
    private final int currentWidth;
    private final int currentHeight;
	
	//Constructor
	public gameManager(GameFrame thisGame){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.initialBoard = new Board(bMat);
		this.frameWidth=thisGame.getWidth();
                this.currentWidth = frameWidth;
		this.frameHeight=thisGame.getHeight();
                this.currentHeight = frameHeight;
		this.membersPath = new File("").getAbsolutePath() + "\\pacmanMembers.txt";
		this.statisticsPath = new File("").getAbsolutePath() + "\\pacmanStatistics.txt";
		this.currentUser = "";
		this.users = new ArrayList<>();
		this.statistics = new ArrayList<>();
		mapHeight=496;
		mapWidth=448;
		squareHeight=mapHeight/31;
		squareWidth=mapWidth/28;
		maxFood = initialBoard.getMaximumFood();
		
		//Exit Symbol
		thisGame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        	SaveStatistics();
		            System.exit(0);
		    }
		});
		
		// Creates and set-up the layered pane.
		multiBoard = new JLayeredPane();
		multiBoard.setPreferredSize(new Dimension(mapHeight, mapWidth));
		
		LoadUsers();
		LoadStatistics();
		setBoardMainScreen();

		// Final stuff
		multiBoard.setVisible(true);
		this.add(multiBoard);
	}
	private void LoadUsers() {
		try {
                  ReadUsersFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

    private void ReadUsersFromFile() throws NumberFormatException, IOException {
        File currentUserInFile = new File(membersPath);
        if(currentUserInFile.exists()){
            BufferedReader bReader = new BufferedReader(new FileReader(currentUserInFile));
            String user = bReader.readLine();
            while(user != null){
                String[] allPartsOfUser = user.split(";");
                users.add(new User(allPartsOfUser[0],allPartsOfUser[1].charAt(0),Integer.parseInt(allPartsOfUser[2]),allPartsOfUser[3],Double.parseDouble(allPartsOfUser[4]),Integer.parseInt(allPartsOfUser[5]),Integer.parseInt(allPartsOfUser[6])));
                user = bReader.readLine();
            }
            bReader.close();
        }
        else{
            currentUserInFile.createNewFile();
        }
    }
	private void setBoardMainScreen() {
		multiBoard.removeAll();
		multiBoard.repaint();
		//removeComponents(); //board, ghosts & pacman
		
		JPanel mainScreenBackground = createMainBackground();
		mainScreenBackground.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
		multiBoard.add(mainScreenBackground, new Integer(0));
		
		JPanel mainScreenButtons = createMainButtons();
		mainScreenButtons.setOpaque(false);
		mainScreenButtons.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
		multiBoard.add(mainScreenButtons, new Integer(1));
		
	}
	/////////////////////////////////JPanels/////////////////////////////////
	private JPanel CreateRegistrationLPanel(){
            
            
		int lblWidth = 100;
		int lblHeight = 20;
		int txtBWidth = lblWidth + lblWidth/2;
		JPanel signPanel = new JPanel();
		signPanel.setLayout(null);
		signPanel.setBackground(Color.BLACK);
		
		JLabel lblTitle = new JLabel("Sign Up!");
                lblTitle.setFont(new Font("Arial", Font.BOLD,20));
                //update labels
                
                setLabelProperties(lblTitle,"White",currentWidth / 2 -30, currentHeight / 8 + 100, 230, 40);
                /*
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(currentWidth / 2 -30, currentHeight / 8 + 100, 230, 40);
                */
		
		JLabel lblName = new JLabel("Name:");
                setLabelProperties(lblName,"White",currentWidth / 4, currentHeight / 4 +135, lblWidth, lblHeight);
                /*
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(currentWidth / 4, currentHeight / 4 +135, lblWidth, lblHeight);
                */
		
		JLabel lblSex = new JLabel("Sex: M/F");
                setLabelProperties(lblSex,"White",currentWidth / 4, currentHeight / 4 + 160, lblWidth, lblHeight);
                /*

		lblSex.setForeground(Color.WHITE);
		lblSex.setBounds(currentWidth / 4, currentHeight / 4 + 160, lblWidth, lblHeight);
                */


		JLabel lblAge = new JLabel("Age:");
                setLabelProperties(lblAge,"White",currentWidth / 4, currentHeight / 4 + 180, lblWidth, lblHeight);
                /*
		lblAge.setForeground(Color.WHITE);
		lblAge.setBounds(currentWidth / 4, currentHeight / 4 + 180, lblWidth, lblHeight);
                */


		JLabel lblPass = new JLabel("Password:");
                setLabelProperties(lblPass,"White",currentWidth / 4, currentHeight / 4 + 200, lblWidth, lblHeight);
                /*
		lblPass.setForeground(Color.WHITE);
		lblPass.setBounds(currentWidth / 4, currentHeight / 4 + 200, lblWidth, lblHeight);
                */

		JTextField txtName = new JTextField();
		txtName.setBounds(currentWidth / 4 + lblWidth/2 + 30, currentHeight / 4 +140, txtBWidth, lblHeight);

		JTextField txtSex = new JTextField();
		txtSex.setBounds(currentWidth / 4 + lblWidth/2 +30, currentHeight / 4 + 160, txtBWidth / 7, lblHeight);
		

		JTextField txtAge = new JTextField();
		txtAge.setBounds(currentWidth / 4 + lblWidth/2 + 30, currentHeight / 4 + 180, txtBWidth, lblHeight);
		

		JPasswordField txtPass = new JPasswordField();
		txtPass.setBounds(currentWidth / 4 + lblWidth/2 +30, currentHeight / 4 + 200, txtBWidth, lblHeight);
		
		
		JButton signUpBtn = new JButton("Sign Up");
		signUpBtn.setBounds(currentWidth /4, currentHeight/4 + 240, lblWidth, lblHeight);
		signUpBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(addNewMember(txtName.getText(), txtSex.getText(), txtAge.getText(), String.valueOf(txtPass.getPassword())) == true){
		    		JOptionPane.showMessageDialog(signPanel, "Registration Succeed!", "Confirm Window",1);
			        setBoardMainScreen();
		    	}
		    	else{
		    		JOptionPane.showMessageDialog(signPanel, "Wrong details - Check again", "Error Window",0);
		    	}
		    }
		});
                
		
		JButton btnBack = new JButton("Back");
                defineBackButtonEvent(btnBack,lblWidth,lblHeight);
                
		addControlsToPanel(signPanel, lblTitle, lblName, lblSex, lblAge, lblPass, txtName, txtSex, txtAge, txtPass, signUpBtn, btnBack);
				
		return signPanel;
	}

    private void addControlsToPanel(JPanel signPanel, JLabel lblTitle, JLabel lblName, JLabel lblSex, JLabel lblAge, JLabel lblPass, JTextField txtName, JTextField txtSex, JTextField txtAge, JPasswordField txtPass, JButton signUpBtn, JButton btnBack) {
        signPanel.add(lblTitle);
        signPanel.add(lblName);
        signPanel.add(lblSex);
        signPanel.add(lblAge);
        signPanel.add(lblPass);
        signPanel.add(txtName);
        signPanel.add(txtSex);
        signPanel.add(txtAge);
        signPanel.add(txtPass);
        signPanel.add(signUpBtn);
        signPanel.add(btnBack);
    }
	private JPanel createNewGamePanel(){
		int labelWidth = 100;
		int labelHeight = 20;
		playerMode = 0;
		gameMode = 0;
		
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBackground(Color.black);
		
		JLabel lblTitle = new JLabel("Configurations");
                lblTitle.setFont(new Font("Arial", Font.BOLD,15));
                setLabelProperties(lblTitle,"White",currentWidth / 2 - 40, currentHeight / 8, 200, 40);
                
		JButton btnRealPlayer = new JButton("Single Player");
                setBtnProperties(btnRealPlayer,frameWidth /4 , frameHeight/2 - 100, labelWidth +10, labelHeight);
                /*
		realPlayerBtn.setBounds(frameWidth /4 , frameHeight/2 - 100, labelWidth +10, labelHeight);
                */
		
		JButton btnComputer = new JButton("Computer");
                setBtnProperties(btnComputer,frameWidth /4 + labelWidth + 20, frameHeight/2 - 100, labelWidth +10, labelHeight);
                /*
		demoPlayerBtn.setBounds(frameWidth /4 + labelWidth + 20, frameHeight/2 - 100, labelWidth +10, labelHeight);
                */
		
		btnRealPlayer.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	btnComputer.setBorder(new LineBorder(Color.red,2));
		    	btnRealPlayer.setBorder(UIManager.getBorder("Button.border"));
		    	playerMode = 1;
		    }
		});
		
		btnComputer.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	btnRealPlayer.setBorder(new LineBorder(Color.red,3));
		    	btnComputer.setBorder(UIManager.getBorder("Button.border"));
		    	playerMode=2;
		    }
		});
		// Easy - Hard
           
		JButton easyLevelBtn = new JButton("Easy");
                easyLevelBtn.setContentAreaFilled(true);
                setBtnProperties(easyLevelBtn,frameWidth /4 , frameHeight/2 - 60, labelWidth +10, labelHeight);
                /*
		easyLevelBtn.setBounds(frameWidth /4 , frameHeight/2 - 60, labelWidth +10, labelHeight);
                */
		
		JButton hardBtn = new JButton("Hard");
                                hardBtn.setContentAreaFilled(true);
                                setBtnProperties(hardBtn,frameWidth /4 + labelWidth + 20, frameHeight/2 - 60 , labelWidth +10, labelHeight);
                                /*
		hardBtn.setBounds(frameWidth /4 + labelWidth + 20, frameHeight/2 - 60 , labelWidth +10, labelHeight);
                                */

		
		easyLevelBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	hardBtn.setBorder(new LineBorder(Color.red,3));
		    	easyLevelBtn.setBorder(UIManager.getBorder("Button.border"));
		    	gameMode=1;
		    }
		});
		
		hardBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	easyLevelBtn.setBorder(new LineBorder(Color.red,3));
		    	hardBtn.setBorder(UIManager.getBorder("Button.border"));
		    	gameMode=2;
		    }
		});
		
		JLabel lblTimer = new JLabel("Ghost Delay(0-10):");
		lblTimer.setForeground(Color.WHITE);
		lblTimer.setBounds(frameWidth /4, frameHeight/2 - 20, labelWidth*2 + 30, labelHeight);
                JTextField txtBTimer = new JTextField();
		txtBTimer.setBounds(frameWidth / 4 + labelWidth + 70,frameHeight/2 - 20, 50, labelHeight);
		
		JButton btnStart = new JButton("Start");
                setBtnProperties(btnStart,frameWidth /4, frameHeight/2+40 , labelWidth, labelHeight);
                /*
		btnStart.setBounds(frameWidth /4, frameHeight/2+40 , labelWidth, labelHeight);
                */
		btnStart.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(checkConfigDetails(gamePanel) == true){
    				setGameScreen(playerMode, gameMode, Integer.parseInt(txtBTimer.getText()));
		    	}
		    }
		});
		
		JButton backBtn = new JButton("Back");
                setBtnProperties(backBtn,frameWidth /4 + 110, frameHeight/2+40, labelWidth, labelHeight);
                
		backBtn.setBounds(frameWidth /4 + 110, frameHeight/2+40, labelWidth, labelHeight);
		backBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        setBoardMainScreen();
		    }
		});
		
		gamePanel.add(lblTitle);
                gamePanel.add(txtBTimer);
		gamePanel.add(btnRealPlayer);
		gamePanel.add(btnComputer);
		gamePanel.add(easyLevelBtn);
		gamePanel.add(hardBtn);
		gamePanel.add(lblTimer);
                /*
		newGamePanel.add(ghostTimerSlider);
*/
		gamePanel.add(btnStart);
		gamePanel.add(backBtn);
		
		return gamePanel;
	}
	private boolean checkConfigDetails(JPanel parent){
		if(playerMode!= 0 && gameMode != 0){
    		if(playerMode == 1){ //Real Player
				String username = JOptionPane.showInputDialog(null, "Enter Username:", "Username", 1);
				String password = JOptionPane.showInputDialog(null, "Enter Password:", "Username", 1);
    			if(isValidMember(username,password) == true){
    				currentUser = username;
    				return true;
    			}
    			else
    				JOptionPane.showMessageDialog(parent, "Invalid Username / Password, Please Try Again", "Error Window",0);
    		}	
    		else{ //Demo Player
    			currentUser = "@demo";
    			addNewMember(currentUser,"d","0","0");
    			statistics.set(2, statistics.get(2)+1);
    			return true;
    		}
    	}
    	else{
    		JOptionPane.showMessageDialog(parent, "Make Sure You Chose Player & Game Mode", "Error Window",0);
    	}
		return false;
	}
	private JPanel createHighScoresPanel(){
		sortScores();
		int[] ageCounter = new int[7];
		JPanel highScoresPanel = new JPanel();
		highScoresPanel.setLayout(null);
		highScoresPanel.setBackground(Color.BLACK);
		
		JLabel titleLabel = new JLabel("High Scores");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Arial", Font.BOLD,20));
		titleLabel.setBounds(frameWidth / 2 - 65, frameHeight / 8 - 50, 200, 40);
		
		highScoresPanel.add(titleLabel);
		
		JLabel nameLabel = new JLabel("Username");
		nameLabel.setForeground(Color.blue);
		nameLabel.setBounds(frameWidth / 10 - 20, frameHeight / 8 , 100, 20);
		highScoresPanel.add(nameLabel);
		
		JLabel scoreLabel = new JLabel("Score");
		scoreLabel.setForeground(Color.blue);
		scoreLabel.setBounds((frameWidth / 10) + 80 , frameHeight / 8 , 80, 20);
		highScoresPanel.add(scoreLabel);
		
		JLabel bestTimeLabel = new JLabel("Best Time");
		bestTimeLabel.setForeground(Color.blue);
		bestTimeLabel.setBounds((frameWidth / 10) + 160, frameHeight / 8 , 100, 20);
		highScoresPanel.add(bestTimeLabel);
		
		JLabel bestTimeUntilCol = new JLabel("Collision Best Time");
		bestTimeUntilCol.setForeground(Color.blue);
		bestTimeUntilCol.setBounds((frameWidth / 10) + 260, frameHeight / 8 , 150, 20);
		highScoresPanel.add(bestTimeUntilCol);
		
		for(int i=0; i < users.size(); i++){
			JLabel currName = new JLabel();
			currName.setForeground(Color.WHITE);
			JLabel currScore = new JLabel();
			currScore.setForeground(Color.WHITE);
			JLabel currBestTime = new JLabel();
			currBestTime.setForeground(Color.WHITE);
			JLabel currBestCollTime = new JLabel();
			currBestCollTime.setForeground(Color.WHITE);
			
			currName.setText(users.get(i).getUsername());
			if(!currName.getText().equals("@demo") && users.get(i).getScore() != 0){
				int counterPlace = (int)((users.get(i).getAge()-1)/10);
				if(counterPlace > 6)
					ageCounter[6]++;
				else
					ageCounter[counterPlace]++;
			}
			currScore.setText(users.get(i).getScore() + "");
			currBestTime.setText(users.get(i).getBestTime()+"");
			currBestCollTime.setText(users.get(i).getBestColTime()+"");
			currName.setBounds(frameWidth / 10 - 20, frameHeight / 8 + 50 * (i+1), 100, 20);
			currScore.setBounds((frameWidth / 10) + 80 , frameHeight / 8 + 50 * (i+1), 80, 20);
			currBestTime.setBounds((frameWidth / 10) + 160, frameHeight / 8 + 50 * (i+1), 100, 20);
			currBestCollTime.setBounds((frameWidth / 10) + 260, frameHeight / 8 + 50 * (i+1), 150, 20);
			highScoresPanel.add(currName);
			highScoresPanel.add(currScore);
			highScoresPanel.add(currBestTime);
			highScoresPanel.add(currBestCollTime);
		}
		JButton statBtn = new JButton("Game Statistics");
		statBtn.setBounds(frameWidth /2 - 70, frameHeight - 120, 140, 20);
		statBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	double totalGames = statistics.get(0) + statistics.get(1) + statistics.get(2);
		        String genderStats = "Gender:: Men: 0% Women: 0% Computers: 0%";
		        String ageStats = "Ages:: 0-10: " + ageCounter[0] + " 11-20: "+ ageCounter[1] +" 21-30: "+ ageCounter[2] +" 31-40: "+ ageCounter[3] +" 41-50: "+ ageCounter[4] +" 51-60: "+ ageCounter[5] +" 60+: "+ ageCounter[6];
		        if(totalGames != 0)
		        	genderStats = "Gender:: Men: " + Math.round((double)(statistics.get(0)/totalGames)*100) + "% Women: " + Math.round((double)(statistics.get(1)/totalGames)*100) + "% Computers: " + Math.round((double)(statistics.get(2)/totalGames)*100) + "%";
		        JOptionPane.showMessageDialog(highScoresPanel, genderStats + "\n" + ageStats, "Statistics Window", 3);
		    }
		});
		highScoresPanel.add(statBtn);
		
		JButton backBtn = new JButton("Back");
		backBtn.setBounds(frameWidth /2 - 40, frameHeight - 80, 80, 20);
		backBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        setBoardMainScreen();
		    }
		});
		highScoresPanel.add(backBtn);
		
		
		return highScoresPanel;
	}
	private JPanel createMainButtons() {
		int btnWidth = 150;
		int btnHeight = 30;
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);

		JButton newGameBtn = new JButton("New Game");
		newGameBtn.setBounds(frameWidth/2 - btnWidth/2, frameHeight/2 - btnHeight, btnWidth, btnHeight);
		newGameBtn.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        multiBoard.removeAll();
		        multiBoard.repaint();
		        JPanel newGamePanel = createNewGamePanel();
		        newGamePanel.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
				multiBoard.add(newGamePanel, new Integer(0));
		    }
		});
		
		JButton signUpBtn = new JButton("Sign Up");
		signUpBtn.setBounds(frameWidth/2 - btnWidth/2, frameHeight/2 - btnHeight + 1*btnHeight, btnWidth, btnHeight);
		signUpBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        multiBoard.removeAll();
		        multiBoard.repaint();
		        JPanel signUpPanel = CreateRegistrationLPanel();
		        signUpPanel.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
				multiBoard.add(signUpPanel, new Integer(0));
		    }
		});
		
		JButton scoresBtn = new JButton("High Scores");
		scoresBtn.setBounds(frameWidth/2 - btnWidth/2, frameHeight/2 - btnHeight + 2*btnHeight, btnWidth, btnHeight);
		scoresBtn.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        multiBoard.removeAll();
		        multiBoard.repaint();
		        JPanel highScoresPanel = createHighScoresPanel();
		        highScoresPanel.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
				multiBoard.add(highScoresPanel, new Integer(0));
		    }
		});
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBounds(frameWidth/2 - btnWidth/2, frameHeight/2 - btnHeight + 3*btnHeight, btnWidth, btnHeight);
		exitBtn.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	SaveStatistics();
		        System.exit(0);
		    }
		});
		
		buttonsPanel.add(newGameBtn);
		buttonsPanel.add(signUpBtn);
		buttonsPanel.add(scoresBtn);
		buttonsPanel.add(exitBtn);
		return buttonsPanel;
	}
	private JPanel createMainBackground() {
		try {
			mainBackground = new JPanel(){
				private static final long serialVersionUID = 1L;
				private Image img = ImageIO.read(getClass().getClassLoader().getResource("Img/newWelcomeScreen.jpg"));
				public void paint( Graphics g ) { 
					super.paint(g);
					g.drawImage(img,0,0,frameWidth , frameHeight, null);
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mainBackground;
	}
	private JPanel createBackground(){
		try {
			background = new JPanel(){
				private static final long serialVersionUID = 1L;
				private Image wallImg = ImageIO.read(getClass().getClassLoader().getResource("Img/myWall.jpg"));
				private Image emptyImg = ImageIO.read(getClass().getClassLoader().getResource("Img/emptyImage.jpg"));
				public void paint( Graphics g ) { 
					super.paint(g);
					for (int i=0; i<currentBoard.getWidth(); i++)
						for (int j=0; j<currentBoard.getHeight(); j++) {
							if(currentBoard.getCellType(i,j).getCellType() == 1){
								g.drawImage(wallImg, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareHeight, (int) squareHeight,null);
							}
							else if(currentBoard.getCellType(i,j).getCellType() == 3){
								g.drawImage(emptyImg, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareHeight, (int) squareHeight,null);
							}
						}
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setVisible(true);
		return background;
	}
	private JPanel createGameDataPanel() {
		gameDataPanel = new JPanel();
		gameDataPanel.setLayout(null);
		gameDataPanel.setBackground(Color.BLACK);
		gameDataPanel.setVisible(true);
		try {
			Image lifeImage = ImageIO.read(getClass().getClassLoader().getResource("Img/lifeImage.png"));
			Image resizedLifeImage = lifeImage.getScaledInstance((int)squareWidth, (int)squareHeight, Image.SCALE_DEFAULT);
			JLabel lifeLabel1 = new JLabel(new ImageIcon(resizedLifeImage));
			lifeLabel1.setBounds(0, 0, 20, 20);
			JLabel lifeLabel2 = new JLabel(new ImageIcon(resizedLifeImage));
			lifeLabel2.setBounds(20, 0, 20, 20);
			JLabel lifeLabel3 = new JLabel(new ImageIcon(resizedLifeImage));
			lifeLabel3.setBounds(40, 0, 20, 20);

			gameDataPanel.add(lifeLabel1,0);
			gameDataPanel.add(lifeLabel2,1);
			gameDataPanel.add(lifeLabel3,2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel scoreTitle = new JLabel("Score: ");
		scoreTitle.setBounds(frameWidth/2 - 50, 0, 50, 20);
		JLabel scoreValue = new JLabel("0");
		scoreValue.setBounds(frameWidth/2, 0, 100, 20);
		scoreTitle.setForeground(Color.WHITE);
		scoreValue.setForeground(Color.WHITE);
		
		gameDataPanel.add(scoreTitle,3);
		gameDataPanel.add(scoreValue,4);
		
		JButton backBtn = new JButton("Back");
		backBtn.setFont(new Font("Arial",Font.BOLD, 10));
		backBtn.setMargin(new Insets (0, 0, 0, 0));
		backBtn.setBounds(frameWidth - 67, 2, 50, 20);
		backBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				removeComponents();
		        setBoardMainScreen();
		    }
		});

		gameDataPanel.add(backBtn);
		
		return gameDataPanel;
	}
	//////////////////////////////END-JPanels///////////////////////////////
	private boolean addNewMember(String name, String sex, String age, String password) {
		if(name == "")
			return false;
		if(sex == "" || (!name.equals("@demo") && !(sex.toLowerCase().equals("m") || sex.toLowerCase().equals("f"))))
			return false;
		if(age == "" || !tryParseInt(age))
			return false;
		if(password == "")
			return false;
		
		for(int i=0; i<users.size(); i++){
			if(users.get(0).getUsername().equals(name)){
				if(!name.equals("@demo")){
					JOptionPane.showMessageDialog(null, "Username is already Exist", "Error Window",0);
					return false;
				}
				else{
					return false;
				}
			}
		}
		users.add(new User(name, sex.charAt(0), Integer.parseInt(age), password));
		return true;
	}
	private void LoadStatistics(){
		try {
			File statisticsFile = new File(statisticsPath);
			if(statisticsFile.exists()){
				BufferedReader br = new BufferedReader(new FileReader(statisticsFile));
				String[] stats = br.readLine().split(";");
				br.close();
				for(int i=0;i<stats.length;i++)
					statistics.add(Integer.parseInt(stats[i]));
			}
			else{
				statisticsFile.createNewFile();
				FileWriter fw = new FileWriter(new File(statisticsPath),false);
				fw.write("0;0;0"); //Men - Women - Comp
				fw.close();
				for(int i=0;i<3;i++)
					statistics.add(i, new Integer(0));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void SaveStatistics(){
		try {
			FileWriter fw = new FileWriter(new File(statisticsPath),false);
			String newStats = "";
			for (int d : statistics)
			{
				newStats += d + ";";
			}
			newStats = newStats.substring(0, newStats.length()-1);
			fw.write(newStats);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter fw = new FileWriter(new File(membersPath),false); //New Content
			for(int i=0; i< users.size(); i++){
				fw.write(users.get(i).getString() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private User getUser(String name) {
		for(int i=0; i < users.size(); i++){
			if(users.get(i).getUsername().equals(name))
				return users.get(i);
		}
		return null;
	}
	private boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	private boolean isValidMember(String username, String password) {
		User userRecord = getUser(username);
		if(userRecord == null){
			return false;
		}
		else{
			if(password.equals(userRecord.getPassword())){
				if(userRecord.getSex() == 'm')
	    			statistics.set(0, statistics.get(0)+1);
				else if(userRecord.getSex() == 'f')
	    			statistics.set(1, statistics.get(1)+1);
				return true;
			}
		}
		return false;
	}
	private void setGameScreen(int playerType, int gameMode, int ghostDelay) {
		multiBoard.removeAll();
		multiBoard.repaint();
		currentBoard = new Board(initialBoard);
		
		// Creates the background.
		JPanel background = createBackground();
		background.setBounds(0, 0, mapWidth, mapHeight);
		background.setBackground(Color.BLACK);
		multiBoard.add(background, new Integer(0));
		
		// Creates the GameData Panel
		gameDataPanel = createGameDataPanel();
		gameDataPanel.setBounds(0,496,frameWidth,bottomPanelHeight);
		multiBoard.add(gameDataPanel, new Integer(4));
		
		// Creates the items board - Placing food in proper places
		CreateItemBoard();
		itemsBoard.setOpaque(false);
		itemsBoard.setSize(mapWidth, mapHeight);
		itemsBoard.setBackground(null);
		multiBoard.add(itemsBoard,new Integer (1));
		
		// Places Ghosts on the board
		if(gameMode == 1) //Easy Mode
			ghosts = new Ghost[2];
		else if(gameMode == 2)
			ghosts = new Ghost[4];
		
		for(int i=0;i<ghosts.length;i++){
			ghosts[i] = placeGhost(i, ghostDelay);
			ghosts[i].setOpaque(false);
			ghosts[i].setSize(mapWidth, mapHeight);
			multiBoard.add(ghosts[i],new Integer(2));
			ghosts[i].setFocusable(true);
		}
		
		// Places Pacman on the board.
		pacman = placePacman(playerType);
		initPacmanProperties();
	}
	private void initPacmanProperties() {
		pacman.setOpaque(false);
		pacman.setSize(mapWidth, mapHeight);
		multiBoard.add(pacman,new Integer(3));
		pacman.setFocusable(true);
	}
	private void removeComponents() {;
		//user
		currentUser="";
		
		//characters
		if(pacman!=null){
			stopMovement();
			pacman=null;
			for(int i=0; i<ghosts.length;i++)
				ghosts[i] = null;
		}
		//board
		currentBoard=null;
	}
	private Ghost placeGhost(int i, int delay) {
		if(i == 0)
			return new WeakGhost(this, 14*squareWidth,15*squareHeight, 1, delay);
		if(i == 1)
			return new StrongGhost(this, 12*squareWidth,15*squareHeight, 3, delay);
		if(i == 2)
			return new WeakGhost(this, 13*squareWidth,15*squareHeight, 1, delay);
		if(i == 3)
			return new StrongGhost(this, 15*squareWidth,15*squareHeight, 3, delay);
		return null;
	}
	private void CreateItemBoard(){
		try {
			itemsBoard = new JPanel(){
				private static final long serialVersionUID = 1L;
				private Image food = ImageIO.read(getClass().getClassLoader().getResource("Img/NormalPoint.png"));
				private Image mightyfood = ImageIO.read(getClass().getClassLoader().getResource("Img/mightyFood.png"));
				private Image superfood = ImageIO.read(getClass().getClassLoader().getResource("Img/superFood.png"));
				public void paint (Graphics g){
					super.paint(g);
					for (int i=0; i<currentBoard.getWidth(); i++)
						for (int j=0; j<currentBoard.getHeight(); j++) {
							if(currentBoard.getCellType(i,j).getCellType() == 2){
								if(i==1 && j==1)
									g.drawImage(mightyfood, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
								else if(i==1 && j==26)
									g.drawImage(superfood, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
								else if(i==29 && j==1)
									g.drawImage(mightyfood, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
								else if(i==29 && j==26)
									g.drawImage(superfood, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
								else
									g.drawImage(food, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
							}
						}
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Pacman placePacman(int playerType){
		initPacX = 14;
		initPacY = 23;
		return new RegularPacman(this, initPacX*squareWidth,initPacY*squareHeight, playerType,0,0);
	}
	public int getMapWidth(){
		return mapWidth;
	}
	public int getMapHeight(){
		return mapHeight;
	}
	public void addItemToMultiBoard(Component comp , Integer num){
		multiBoard.add(comp,num);
	}
	public boolean canMove(double currX, double currY) {
		int xCoor = (int)Math.round((currX + pacman.getDeltaX())/squareWidth);
		int yCoor = (int)Math.round((currY + pacman.getDeltaY())/squareHeight);		

		if(collision((int)Math.round((currX)/squareWidth),(int)Math.round((currY)/squareHeight)) == true){ //Collision			
			if(currentBoard.getCuurentLives() == 3){ //First Dec
				currentBoard.setFirstDecrementTimer(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
			}
			currentBoard.decrementLives();
			stopMovement();
			if(currentBoard.getCuurentLives()>0) //Keep Playing
				Reorganize();
			else{
				GameOver();
				currentBoard.setFinalTimer(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
				if(currentUser != "")
					try {
						UpdateUserStatistics();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		if(currentBoard.getCellType(yCoor,xCoor).getCellType() == 1){
			return false;
		}
		if(xCoor != initPacX || yCoor != initPacY)
			movedBefore = true;
		if(currentBoard.getCellType(yCoor,xCoor) instanceof FoodCell && movedBefore){
			currentBoard.setCellType(yCoor,xCoor,E);
			if(yCoor==1 && xCoor==1 || yCoor==29 && xCoor==1){ //Mighty
				currentBoard.addToCuurentScore(15);
				pacman.destroyPacman();
				pacman = new MightyPacman(this, pacman.getXIndex(), pacman.getYIndex(), pacman.getPlayerType(), pacman.getDeltaX(), pacman.getDeltaY());
				initPacmanProperties();
			}
			else if(yCoor==1 && xCoor==26 || yCoor==29 && xCoor==26){ //Super
				currentBoard.addToCuurentScore(15);
				pacman.destroyPacman();
				pacman = new SuperPacman(this, pacman.getXIndex(), pacman.getYIndex(), pacman.getPlayerType(), pacman.getDeltaX(), pacman.getDeltaY());
				initPacmanProperties();
			}
			else
				currentBoard.addToCuurentScore(3);

			JLabel score = (JLabel)gameDataPanel.getComponent(4);
			
			score.setText(Integer.toString(currentBoard.getCurrentScore()));
			itemsBoard.repaint();
		}
		if(currentBoard.getFoodCurrentNumber() == 0){ //Win
			currentBoard.addToCuurentScore(20 * currentBoard.getCuurentLives());
			stopMovement();
			WinGame();
			currentBoard.setFinalTimer(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
			if(currentUser != "")
				try {
					UpdateUserStatistics();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		if(xCoor == currentBoard.getHeight() - 1 && pacman.getDeltaX() == 1)
			pacman.setXindex(0);
		if(xCoor == 0 && pacman.getDeltaX() == -1)
			pacman.setXindex(squareWidth * (currentBoard.getHeight() - 1));
		return true;
	}
	private void UpdateUserStatistics() throws ParseException {
		User userRecord = getUser(currentUser);		
		String currScore = Integer.toString(currentBoard.getCurrentScore());
		DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
		Date date1 = df.parse(currentBoard.getStartTimer());
		Date date2 = df.parse(currentBoard.getFinalTimer());
		long diff = date2.getTime() - date1.getTime();			
		String currBestTime =  Long.toString(diff / 1000); //In Seconds
		String tempDecTime = currentBoard.getFirstDecrementTimer();
		String currFirstDecTime;
		if(tempDecTime == ""){
			currFirstDecTime = currBestTime;
		}
		else{
			Date date3 = df.parse(tempDecTime);
			long diff2 = date3.getTime() - date1.getTime();
			currFirstDecTime =  Long.toString(diff2 / 1000);
		}
		if(userRecord.getScore() < Double.parseDouble(currScore))
			userRecord.setScore(Double.parseDouble(currScore));
		if(userRecord.getBestTime() < Integer.parseInt(currBestTime))
			userRecord.setBestTime(Integer.parseInt(currBestTime));
		if(userRecord.getBestColTime() < Integer.parseInt(currFirstDecTime))
			userRecord.setBestColTime(Integer.parseInt(currFirstDecTime));
	}
	private void sortScores() {
		Collections.sort(users, new PacmanComparator());
	}
	private void WinGame() {
		multiBoard.removeAll();
		multiBoard.repaint();
		try {
			winGamePanel = new JPanel(){
				private static final long serialVersionUID = 1L;
				private Image bg = ImageIO.read(getClass().getClassLoader().getResource("Img/win-image.jpg"));
				public void paint (Graphics g){
					super.paint(g);
					g.drawImage(bg,0,0,frameWidth, frameHeight, null);
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		winGamePanel.setLayout(null);
		winGamePanel.setSize(frameWidth, frameHeight);
		JButton backBtn = new JButton("Back");
		backBtn.setBounds(frameWidth /2 - 40, frameHeight - 80, 80, 20);
		backBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        setBoardMainScreen();
		    }
		});
		multiBoard.add(winGamePanel,new Integer(0));
		multiBoard.add(backBtn, new Integer(1));
		winGamePanel.setFocusable(true);
		multiBoard.repaint();
	}
	private void Reorganize() {
		resetPacman();
		pacman.replace();
		pacman.zeroDeltaXY();
		pacman.setDirectionToTrue();
		for(int i=0;i<ghosts.length;i++){
			ghosts[i].replace();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gameDataPanel.getComponent(currentBoard.getCuurentLives()).hide();
		gameDataPanel.repaint();
		pacman.restart();
		for(int i=0;i<ghosts.length;i++){
			ghosts[i].restart();
			ghosts[i].resetRoundDelay();
		}
	}
	private void GameOver() {
		multiBoard.removeAll();
		multiBoard.repaint();
		try {
				gameOverPanel = new JPanel(){
				private static final long serialVersionUID = 1L;
				private Image bg = ImageIO.read(getClass().getClassLoader().getResource("Img/game-over.jpg"));
				public void paint (Graphics g){
					super.paint(g);
					g.drawImage(bg,0,0,frameWidth, frameHeight, null);
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JButton backBtn = new JButton("Back");
		backBtn.setBounds(frameWidth /2 - 40, frameHeight - 80, 80, 20);
		backBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        setBoardMainScreen();
		    }
		});
		
		gameOverPanel.setLayout(null);
		gameOverPanel.setOpaque(false);
		gameOverPanel.setSize(frameWidth, frameHeight);
		multiBoard.add(gameOverPanel,new Integer(0));
		multiBoard.add(backBtn,new Integer(1));
		gameOverPanel.setFocusable(true);
		multiBoard.repaint();
	}
	private void stopMovement() {
		pacman.stop();
		for(int i=0;i<ghosts.length;i++)
			ghosts[i].stop();
	}
	private boolean collision(int xCoor, int yCoor) {
		Point[] ghostsCoor = new Point[ghosts.length];
		for(int i=0;i<ghosts.length;i++){
			ghostsCoor[i] = new Point((int)Math.round((ghosts[i].getXIndex())/squareWidth), (int)Math.round((ghosts[i].getYIndex())/squareHeight));
			if((int)ghostsCoor[i].getX() == xCoor && (int)ghostsCoor[i].getY() == yCoor){
				if(ghosts[i].eats(pacman))
					return true;
				else{ //reset eaten ghost coordinates
					updateGhostPosition(i);
				}
			}
		}
		return false;
	}
	private void updateGhostPosition(int i) {
		ghosts[i].replace();
		ghosts[i].resetRoundDelay();
	}
	public Board getBoard(){
		return currentBoard;
	}
	public double getSquareHeight(){
		return squareHeight;
	}
	public double getSquareWidth(){
		return squareWidth;
	}
	public Pacman getPacman(){
		return pacman;
	}
	public void resetPacman(){
		pacman.destroyPacman();
		pacman = new RegularPacman(this, pacman.getXIndex(), pacman.getYIndex(), pacman.getPlayerType(), pacman.getDeltaX(), pacman.getDeltaY());
		initPacmanProperties();
	}

    private void setLabelProperties(JLabel lblTitle, String color, int posX, int posY, int width, int height) {
                if(color.equals("White"))
		lblTitle.setForeground(Color.WHITE);
                else if(color.equals("Green")) { 
                    
                }else if(color.equals("Black")) {
                    
                }
                else {
                    
                }
		lblTitle.setBounds(posX, posY, width, height);   
    }

    private void defineBackButtonEvent(JButton btnBack, int lblWidth, int lblHeight) {
        btnBack.setBounds(currentWidth /4 + lblWidth + 15, currentHeight/4 + 240, lblWidth, lblHeight);
                        btnBack.addActionListener(new ActionListener()
                        {
                            public void actionPerformed(ActionEvent e)
                            {
                                setBoardMainScreen();
                            }
                        });
                            }

    private void setBtnProperties(JButton realPlayerBtn, int posX, int posY, int width, int height) {
		realPlayerBtn.setBounds(posX , posY,width, height);
    }
}
