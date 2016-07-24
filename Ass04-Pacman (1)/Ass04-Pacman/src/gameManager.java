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
		
		LoadUsersFromFile();
		LoadAllPreviousStatistics();
		setBoardMainScreen();

		// Final stuff
		multiBoard.setVisible(true);
		this.add(multiBoard);
	}
	private void LoadUsersFromFile() {
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
		JPanel mainScreenBackground = createMainPanelBackground();
		mainScreenBackground.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
		multiBoard.add(mainScreenBackground, new Integer(0));
		
		JPanel mainScreenButtons = createGeneralButtonsForMain();
		setMainScreenButtons(mainScreenButtons);
		
	}

    private void setMainScreenButtons(JPanel mainScreenButtons) {
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
                String test = "";           
                for(int i = 1 ; i<30 ; i++) {                                 
                }
		JLabel lblTitle = new JLabel("Sign Up!");
                setLabelProperties(lblTitle,"White",currentWidth / 2 -30, currentHeight / 8 + 100, 230, 40);	
		JLabel lblName = new JLabel("Name:");
                setLabelProperties(lblName,"White",currentWidth / 4, currentHeight / 4 +135, lblWidth, lblHeight);		
		JLabel lblSex = new JLabel("Sex: M/F");
                setLabelProperties(lblSex,"White",currentWidth / 4, currentHeight / 4 + 160, lblWidth, lblHeight);
		JLabel lblAge = new JLabel("Age:");
                setLabelProperties(lblAge,"White",currentWidth / 4, currentHeight / 4 + 180, lblWidth, lblHeight);
		JLabel lblPass = new JLabel("Password:");
                setLabelProperties(lblPass,"White",currentWidth / 4, currentHeight / 4 + 200, lblWidth, lblHeight);
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
		setSignUpEventListener(signUpBtn, txtName, txtSex, txtAge, txtPass, signPanel);               	
		JButton btnBack = new JButton("Back");
                defineBackButtonEvent(btnBack,lblWidth,lblHeight);  
		addControlsToPanel(signPanel, lblTitle, lblName, lblSex, lblAge, lblPass, txtName, txtSex, txtAge, txtPass, signUpBtn, btnBack);			
		return signPanel;
	}

    private void setSignUpEventListener(JButton signUpBtn, JTextField txtName, JTextField txtSex, JTextField txtAge, JPasswordField txtPass, JPanel signPanel) {
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
    }

    private void addControlsToPanel(JPanel signPanel, JLabel lblTitle, JLabel lblName, JLabel lblSex, JLabel lblAge, JLabel lblPass, JTextField txtName, JTextField txtSex, JTextField txtAge, JPasswordField txtPass, JButton signUpBtn, JButton btnBack) {
        signPanel.add(lblTitle);
        signPanel.add(txtName);
        signPanel.add(txtSex);
        signPanel.add(txtAge);
        signPanel.add(txtPass);
        signPanel.add(signUpBtn);
        signPanel.add(btnBack);
        signPanel.add(lblName);
        signPanel.add(lblSex);
        signPanel.add(lblAge);
        signPanel.add(lblPass);
  
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
		JButton btnComputer = new JButton("Computer");
                setBtnProperties(btnComputer,frameWidth /4 + labelWidth + 20, frameHeight/2 - 100, labelWidth +10, labelHeight);		
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
		JButton easyLevelBtn = new JButton("Easy");
                easyLevelBtn.setContentAreaFilled(true);
                setBtnProperties(easyLevelBtn,frameWidth /4 , frameHeight/2 - 60, labelWidth +10, labelHeight);
		JButton hardBtn = new JButton("Hard");
                                hardBtn.setContentAreaFilled(true);
                                setBtnProperties(hardBtn,frameWidth /4 + labelWidth + 20, frameHeight/2 - 60 , labelWidth +10, labelHeight);

		
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
		btnStart.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(checkConfigurationsDetails(gamePanel) == true){
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
                gamePanel.add(btnComputer);
		gamePanel.add(easyLevelBtn);
		gamePanel.add(hardBtn);
		gamePanel.add(lblTimer);
		gamePanel.add(btnStart);
		gamePanel.add(backBtn);
		gamePanel.add(lblTitle);
                gamePanel.add(txtBTimer);
		gamePanel.add(btnRealPlayer);
		
		
		return gamePanel;
	}
	private boolean checkConfigurationsDetails(JPanel panel){
		if(playerMode!= 0 && gameMode != 0){
                        if(playerMode != 1){
                                currentUser = "@demo";
                                addNewMember(currentUser,"d","0","0");
                                statistics.set(2, statistics.get(2)+1);
                                return true;
                        }	
                        else{ //Real Player                                                       
                                        String username = JOptionPane.showInputDialog(null, "Enter Username:", "Username", 1);
                                        String password = JOptionPane.showInputDialog(null, "Enter Password:", "Username", 1);
                                if(userMemberIsValid(username,password) == true){
                                        currentUser = username;
                                        return true;
                                }
                                else
                                        JOptionPane.showMessageDialog(panel, "Check cradentials again", "Error Window",0);
                        }
                }
                else{
                         JOptionPane.showMessageDialog(panel, "Make Sure You Chose Player & Game Mode", "Error Window",0);
                }
                
                return false;
        }
	private JPanel createHighestGradeScorePanel(){
		sortAllScores();
		int[] ageArrayCountUsers = new int[7];
                //start high score preferences.
		JPanel highestGradeScorePanel = new JPanel();
		highestGradeScorePanel.setLayout(null);
		highestGradeScorePanel.setBackground(Color.BLACK);
                //finish high score proferenes
		
		JLabel lblTitle = new JLabel("High Scores");
                setLabelProperties(lblTitle,"White",currentWidth / 2 - 65, currentHeight / 8 - 50, 200, 40);
		JLabel nameLabel = new JLabel("User Name");
                setLabelProperties(nameLabel,"Blue",frameWidth / 10 - 20, currentHeight / 8 , 100, 20);
		
		JLabel scoreLabel = new JLabel("Score");
                setLabelProperties(scoreLabel,"Blue",(currentWidth / 10) + 80 , (currentHeight / 8) , 80, 20);
		
		JLabel bestTimeLabel = new JLabel("Best Time");
                setLabelProperties(bestTimeLabel,"Blue",(currentWidth / 10) + 140, currentHeight / 8 , 100, 20);
		
		JLabel bestTimeUntilCollision = new JLabel("Collision Best Time");
                setLabelProperties(bestTimeUntilCollision,"Blue",(currentWidth / 10) + 220, currentHeight / 8 , 150, 20);
		highestGradeScorePanel.add(bestTimeUntilCollision);
                highestGradeScorePanel.add(lblTitle);	
                highestGradeScorePanel.add(nameLabel);
                highestGradeScorePanel.add(scoreLabel);
                highestGradeScorePanel.add(bestTimeLabel);




		
		for(int i=0; i < users.size(); i++){
			JLabel currLabelName = new JLabel();
                        setColor(currLabelName,"White");
			JLabel currLabelScore = new JLabel();
                        setColor(currLabelScore,"White");
			JLabel currBestTimeLabel = new JLabel();
                        setColor(currBestTimeLabel,"White");
                        JLabel currBestCollisionTime = new JLabel();
                        setColor(currBestCollisionTime,"White");			
			currLabelName.setText(users.get(i).getUsername());
                        //meaning its not a computer
			if( users.get(i).getUserScore() != 0 && !currLabelName.getText().equals("@demo")){
				int counterPlace = (int)((users.get(i).getUserAge()-1)/10);
				if(counterPlace > 6)
					ageArrayCountUsers[6]++;
				else
					ageArrayCountUsers[counterPlace]++;
			}
			currLabelScore.setText(users.get(i).getUserScore() + "");
			fillUserRow(currBestTimeLabel, i, currBestCollisionTime, currLabelName, currLabelScore);    
			highestGradeScorePanel.add(currLabelName);
			highestGradeScorePanel.add(currLabelScore);
			highestGradeScorePanel.add(currBestTimeLabel);
			highestGradeScorePanel.add(currBestCollisionTime);
		}
		JButton myGameStatistics = new JButton("Game Statistics");
		myGameStatistics.setBounds(currentWidth /2 - 60, currentHeight - 110, 160, 20);
		myGameStatistics.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	double allNumberOfGames = statistics.get(0) + statistics.get(1) + statistics.get(2);
		        String genderStatistics = "Gender:: Men: 0% Women: 0% Computers: 0%";
                        double first = Math.round((double)statistics.get(0)/allNumberOfGames)*100;
                        double second = Math.round((double)statistics.get(1)/allNumberOfGames)*100;
                        double third = Math.round((double)statistics.get(2)/allNumberOfGames)*100;
		        String ageStatistics = "Ages: 0-10: " + ageArrayCountUsers[0] + " 11-20: "+ ageArrayCountUsers[1] +" 21-30: "+ ageArrayCountUsers[2] +" 31-40: "+ ageArrayCountUsers[3] +" 41-50: "+ ageArrayCountUsers[4] +" 51-60: "+ ageArrayCountUsers[5] +" 60+: "+ ageArrayCountUsers[6];
		        if(allNumberOfGames != 0)
		        	genderStatistics = "Gender: Men: " + first + "% Women: " + second + "% Computers: " + third + "%";
		        JOptionPane.showMessageDialog(highestGradeScorePanel, genderStatistics + "\n" + ageStatistics, "Statistics Window", 3);
		    }
		});
		highestGradeScorePanel.add(myGameStatistics);
                String resultTest = "";
                
                for(int i=1 ; i<10 ; i++) {
                    
                    
                }
		
		JButton returnButton = new JButton("Return");
                setBtnProperties(returnButton,currentWidth /2 - 40, currentHeight - 80, 80, 20);
		returnButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        setBoardMainScreen();
		    }
		});
		highestGradeScorePanel.add(returnButton);	
		return highestGradeScorePanel;
	}

    private void fillUserRow(JLabel currBestTimeLabel, int i, JLabel currBestCollisionTime, JLabel currLabelName, JLabel currLabelScore) {
        int newCurrentHeight = currentHeight /8;
        currBestTimeLabel.setText(users.get(i).getUserBestTime()+"");
        currBestCollisionTime.setText(users.get(i).getUserBestCollisionTime()+"");
        currLabelName.setBounds(currentWidth / 10 - 10, newCurrentHeight + 50 * (i+1), 100, 20);
        currLabelScore.setBounds((currentWidth / 10) + 70 , newCurrentHeight + 50 * (i+1), 80, 20);
        currBestTimeLabel.setBounds((currentWidth / 10) + 150, newCurrentHeight + 50 * (i+1), 100, 20);
        currBestCollisionTime.setBounds((currentWidth / 10) + 250, newCurrentHeight + 50 * (i+1), 150, 20);
    }
	private JPanel createGeneralButtonsForMain() {
		int buttonWidth = 140;
		int buttonHeight = 25;
		JPanel newButtonPanel = new JPanel();
		newButtonPanel.setLayout(null);

		JButton btnNewGame = new JButton("New Game");
                setBtnProperties(btnNewGame,currentWidth/2 - buttonWidth/2, currentHeight/2 - buttonHeight, buttonWidth, buttonHeight);
		
		JButton registrationBtn = new JButton("Sign Up");
                setBtnProperties(registrationBtn,currentWidth/2 - buttonWidth/2, currentHeight/2 - buttonHeight + 1*buttonHeight, buttonWidth, buttonHeight);
                
                JButton returnExitButton = new JButton("Exit");
                setBtnProperties(returnExitButton,currentWidth/2 - buttonWidth/2, currentHeight/2 - buttonHeight + 3*buttonHeight, buttonWidth, buttonHeight);
		
		
		JButton btnHighScores = new JButton("High Scores");
                setBtnProperties(btnHighScores,currentWidth/2 - buttonWidth/2, currentHeight/2 - buttonHeight + 2*buttonHeight, buttonWidth, buttonHeight);
                
		btnHighScores.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        multiBoard.removeAll();
		        multiBoard.repaint();
		        JPanel highScoresPanel = createHighestGradeScorePanel();
		        highScoresPanel.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
				multiBoard.add(highScoresPanel, new Integer(0));
		    }
		});
                registrationBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        multiBoard.removeAll();
		        multiBoard.repaint();
		        JPanel registerPanel = CreateRegistrationLPanel();
		        registerPanel.setBounds(0, 0, mapWidth, mapHeight + bottomPanelHeight);
				multiBoard.add(registerPanel, new Integer(0));
		    }
		});
                	btnNewGame.addActionListener( new ActionListener()
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
		

		returnExitButton.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	SaveStatistics();
		        System.exit(0);
		    }
		});
		
		newButtonPanel.add(btnNewGame);
		newButtonPanel.add(registrationBtn);
		newButtonPanel.add(btnHighScores);
		newButtonPanel.add(returnExitButton);
		return newButtonPanel;
	}
	private JPanel createMainPanelBackground() {
		try {
			initMainBackground();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mainBackground;
	}

    private void initMainBackground() throws IOException {
        mainBackground = new JPanel(){
            private static final long serialVersionUID = 1L;
            private Image img = ImageIO.read(getClass().getClassLoader().getResource("Img/newWelcomeScreen.jpg"));
            public void paint( Graphics g ) {
                super.paint(g);
                g.drawImage(img,0,0,frameWidth , frameHeight, null);
            }
        };
    }
	private JPanel createBoardBackground(){
		try {
			initalizeBoardBackground();
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setVisible(true);
		return background;
	}

    private void initalizeBoardBackground() throws IOException {
        background = new JPanel(){
            private static final long serialVersionUID = 1L;
            //init pictures
            private Image wallImg = ImageIO.read(getClass().getClassLoader().getResource("Img/myWall.jpg"));
            private Image emptyImg = ImageIO.read(getClass().getClassLoader().getResource("Img/emptyImage.jpg"));
            //drawing all empty cell and walls
            public void paint( Graphics g ) {
                super.paint(g);
                for (int i=0; i<currentBoard.getWidth(); i++)
                    for (int j=0; j<currentBoard.getHeight(); j++) {
                        if(currentBoard.getCellType(i,j).getCellType() == 3){
                            g.drawImage(emptyImg, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareHeight, (int) squareHeight,null);
                        }
                        else if(currentBoard.getCellType(i,j).getCellType() == 1){
                            g.drawImage(wallImg, (int)(j*(squareWidth)), (int)(i*(squareHeight)),(int)squareHeight, (int) squareHeight,null);
                        }
                        
                    }
            }
        };
    }
	private JPanel createGameDataPanel() {
		gameDataPanel = new JPanel();
		gameDataPanel.setLayout(null);
		gameDataPanel.setBackground(Color.BLACK);
		gameDataPanel.setVisible(true);
		try {
			drawLifeToBoard();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel lblScore = new JLabel("Score: ");
		JLabel scoreValue = drawScoreOnBoard(lblScore);
		
		gameDataPanel.add(lblScore,3);
		gameDataPanel.add(scoreValue,4);
		
		JButton returnBtn = new JButton("Return");
		drawReturnButtonInMainPanel(returnBtn);

		gameDataPanel.add(returnBtn);
		
		return gameDataPanel;
	}

    private void drawReturnButtonInMainPanel(JButton returnBtn) {
        returnBtn.setFont(new Font("Arial",Font.BOLD, 10));
        returnBtn.setMargin(new Insets (0, 0, 0, 0));
        returnBtn.setBounds(frameWidth - 67, 2, 50, 20);
        returnBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                resetPacmanAndGhostComponents();
                setBoardMainScreen();
            }
        });
    }

    private JLabel drawScoreOnBoard(JLabel lblScore) {
        lblScore.setBounds(frameWidth/2 - 50, 0, 50, 20);
        JLabel scoreValue = new JLabel("0");
        scoreValue.setBounds(frameWidth/2, 0, 100, 20);
        lblScore.setForeground(Color.GREEN);
        scoreValue.setForeground(Color.GREEN);
        return scoreValue;
    }

    private void drawLifeToBoard() throws IOException {
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
    }
	//////////////////////////////END-JPanels///////////////////////////////
	private boolean addNewMember(String name, String sex, String age, String password) {
		if(name == "" || sex=="" || (!name.equals("@demo") && !(sex.toLowerCase().equals("m") || sex.toLowerCase().equals("f"))) ||age == ""|| password == "")
			return false;
		for(int i=0; i<users.size(); i++){
			if(users.get(0).getUsername().equals(name)){
				if(name.equals("@demo")){
					return false;
				}
				else{
					JOptionPane.showMessageDialog(null, "Username is already Exist", "Error Window",0);
					return false;
				}
			}
		}
                int parsedAge = Integer.parseInt(age);
                char sexChar = sex.charAt(0); 
		users.add(new User(name, sexChar, parsedAge, password));
		return true;
	}
	private void LoadAllPreviousStatistics(){
		try {
			File statisticsFile = new File(statisticsPath);
			if(!statisticsFile.exists()){
                            statisticsFile.createNewFile();
				FileWriter fWriter = new FileWriter(new File(statisticsPath),false);
				fWriter.write("0;0;0"); //Men - Women - Comp
				fWriter.close();
				for(int j=0;j<3;j++)
					statistics.add(j, new Integer(0));
			}
			else{
                                                        
                            
				BufferedReader bReader = new BufferedReader(new FileReader(statisticsFile));
				String[] currentStatistics = bReader.readLine().split(";");
				bReader.close();
				for(int i=0;i<currentStatistics.length;i++)
					statistics.add(Integer.parseInt(currentStatistics[i]));
				
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
			FileWriter fWriter = new FileWriter(new File(membersPath),false); //New Content
			for(int j=0; j< users.size(); j++){
				fWriter.write(users.get(j).getString() + "\n");
			}
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileWriter fWriter = new FileWriter(new File(statisticsPath),false);
			String newStatistics = "";
			for (int s : statistics)
			{
				newStatistics += s + ";";
			}
			newStatistics = newStatistics.substring(0, newStatistics.length()-1);
			fWriter.write(newStatistics);
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	private User getCurrentUser(String userName) {
            
		for(int i=0; i < users.size(); i++){
			if(users.get(i).getUsername().equals(userName))
				return users.get(i);
		}
		return null;
	}
	private boolean tryToParseIntFromString(String currentValue) {  
	     try {  
	         Integer.parseInt(currentValue);
                 for(int i=0;i<users.size();i++) { 
                if(users.get(i).getUsername().equals("")) {            
                }
        }
	         return true;  
	      } catch (Exception e) {  
	         return false;  
	      }  
	}
	private boolean userMemberIsValid(String username, String password) {
		User MatchingUser = getCurrentUser(username);
		if(MatchingUser != null){
			if(password.equals(MatchingUser.getUserPassword())){
				if(MatchingUser.getUserSex() == 'm')
	    			statistics.set(0, statistics.get(0)+1);
				else if(MatchingUser.getUserSex() == 'f')
	    			statistics.set(1, statistics.get(1)+1);
				return true;
			}
		}
		else{
                    return false;
			
		}
		return false;
	}
	private void setGameScreen(int typeOfPlayer, int modeGameSelected, int delayGhostSelected) {
		removeAndCreateNewBoard();
		gameDataPanel = createGameDataPanel();
		gameDataPanel.setBounds(0,496,currentWidth,currentHeight);		
		JPanel panelBackground = createBoardBackground();
		panelBackground.setBounds(0, 0, mapWidth, mapHeight);
		panelBackground.setBackground(Color.BLACK);
		CreateItemBoard();
		itemsBoard.setOpaque(false);
		itemsBoard.setSize(mapWidth, mapHeight);
		itemsBoard.setBackground(null);
		multiBoard.add(itemsBoard,new Integer (1));
                multiBoard.add(panelBackground, new Integer(0));
                multiBoard.add(gameDataPanel, new Integer(4));		
		// Places Ghosts on the board
		if(modeGameSelected == 2) //Easy Mode
			ghosts = new Ghost[4];
		else if(modeGameSelected == 1)
			ghosts = new Ghost[2];		
                placeGhostsInMap(delayGhostSelected);		
		// Places Pacman on the board.
		pacman = placePacman(typeOfPlayer);
		initPacmanProperties();
                
	}

    private void removeAndCreateNewBoard() {
        multiBoard.removeAll();
        multiBoard.repaint();
        currentBoard = new Board(initialBoard);
    }

    private void placeGhostsInMap(int delayGhostSelected) {
        for(int i=0;i<ghosts.length;i++){
            ghosts[i] = placeGhostsInsideMap(i, delayGhostSelected);
            ghosts[i].setOpaque(false);
            ghosts[i].setSize(mapWidth, mapHeight);
            multiBoard.add(ghosts[i],new Integer(2));
            ghosts[i].setFocusable(true);
        }
    }
	private void initPacmanProperties() {
		pacman.setOpaque(false);
		pacman.setSize(mapWidth, mapHeight);
		multiBoard.add(pacman,new Integer(3));
		pacman.setFocusable(true);
	}
	private void resetPacmanAndGhostComponents() {;
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
	private Ghost placeGhostsInsideMap(int typeOfGhost, int chosenDelay) {
		if(typeOfGhost == 0)
			return new WeakGhost(this, 14*squareWidth,15*squareHeight, 1, chosenDelay);
		if(typeOfGhost == 1)
			return new StrongGhost(this, 12*squareWidth,15*squareHeight, 3, chosenDelay);
		if(typeOfGhost == 2)
			return new WeakGhost(this, 13*squareWidth,15*squareHeight, 1, chosenDelay);
		if(typeOfGhost == 3)
			return new StrongGhost(this, 15*squareWidth,15*squareHeight, 3, chosenDelay);
		return null;
	}
	private void CreateItemBoard(){
		try {
			itemsBoard = new JPanel(){
				private static final long serialVersionUID = 1L;
                                private Image mightyfood = ImageIO.read(getClass().getClassLoader().getResource("Img/fruit1.png"));
				private Image superfood = ImageIO.read(getClass().getClassLoader().getResource("Img/fruit0.png"));
				private Image food = ImageIO.read(getClass().getClassLoader().getResource("Img/NormalPoint.png"));

				public void paint (Graphics g){
					super.paint(g);
					for (int xCellNumber=0; xCellNumber<currentBoard.getWidth(); xCellNumber++)
						for (int yCellNumber=0; yCellNumber<currentBoard.getHeight(); yCellNumber++) {
							if(currentBoard.getCellType(xCellNumber,yCellNumber).getCellType() == 2){                                                            
                                                             drawMightyAndSuperFood(xCellNumber, yCellNumber, g);
							}
						}
				}

                            private void drawMightyAndSuperFood(int xCellNumber, int yCellNumber, Graphics g) {
                                if(xCellNumber==1 && yCellNumber==1)
                                    g.drawImage(mightyfood, (int)(yCellNumber*(squareWidth)), (int)(xCellNumber*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
                                else if(xCellNumber==1 && yCellNumber==26)
                                    g.drawImage(superfood, (int)(yCellNumber*(squareWidth)), (int)(xCellNumber*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
                                else if(xCellNumber==29 && yCellNumber==1)
                                    g.drawImage(mightyfood, (int)(yCellNumber*(squareWidth)), (int)(xCellNumber*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
                                else if(xCellNumber==29 && yCellNumber==26)
                                    g.drawImage(superfood, (int)(yCellNumber*(squareWidth)), (int)(xCellNumber*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
                                else
                                    g.drawImage(food, (int)(yCellNumber*(squareWidth)), (int)(xCellNumber*(squareHeight)),(int)squareWidth, (int)squareHeight, null);
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
	public boolean canMove(double currXPosition, double currYPosition) {
		int xCordination = (int)Math.round((currXPosition + pacman.getDeltaX())/squareWidth);
		int yCordination = (int)Math.round((currYPosition + pacman.getDeltaY())/squareHeight);
                int xCordForCollision = (int)Math.round((currXPosition)/squareWidth);
                int yCordForCollision = (int)Math.round((currYPosition)/squareHeight);
		if(collisionHappendWithGhost(xCordForCollision,yCordForCollision) == true){ 			
			if(currentBoard.getCuurentLives() == 3){ //first time life decreasings
				currentBoard.setFirstDecrementTimer(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
			}
			currentBoard.decrementLives();
			stopMovement();
			if(currentBoard.getCuurentLives()>0) //Keep Playing
				backToStartPlace();
			else{
				playerLose();
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
                //there is a wall
                
		if(currentBoard.getCellType(yCordination,xCordination).getCellType() == 1){
			return false;
		}
		if(xCordination != initPacX || yCordination != initPacY)
			movedBefore = true;
                
                
                
		if(currentBoard.getCellType(yCordination,xCordination) instanceof FoodCell && movedBefore){
			currentBoard.setCellType(yCordination,xCordination,E);
                        didEatSpecialFood(yCordination, xCordination);
			JLabel currentScore = (JLabel)gameDataPanel.getComponent(4);
			
			currentScore.setText(Integer.toString(currentBoard.getCurrentScore()));
			itemsBoard.repaint();
		}
                 checkIfWin();
		
		if(xCordination == currentBoard.getHeight() - 1 && pacman.getDeltaX() == 1)
			pacman.setXIndexPosition(0);
		if(xCordination == 0 && pacman.getDeltaX() == -1)
			pacman.setXIndexPosition(squareWidth * (currentBoard.getHeight() - 1));
		return true;
	}

    private void checkIfWin() {
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
    }

    private void didEatSpecialFood(int yCordination, int xCordination) {
        if(yCordination==1 && xCordination==26 || yCordination==29 && xCordination==26){ //Super
            currentBoard.addToCuurentScore(15);
            pacman.deletePacmanFromMap();
            pacman = new SuperPacman(this, pacman.getXIndexPosition(), pacman.getYIndexPosition(), pacman.getCurrentPlayerType(), pacman.getDeltaX(), pacman.getDeltaY());
            initPacmanProperties();
        }
        
        else if(yCordination==1 && xCordination==1 || yCordination==29 && xCordination==1){ //Mighty
            currentBoard.addToCuurentScore(15);
            pacman.deletePacmanFromMap();
            pacman = new MightyPacman(this, pacman.getXIndexPosition(), pacman.getYIndexPosition(), pacman.getCurrentPlayerType(), pacman.getDeltaX(), pacman.getDeltaY());
            initPacmanProperties();
        }
        
        else
            currentBoard.addToCuurentScore(3);
    }
	private void UpdateUserStatistics() throws ParseException {
		User currentUserPlaying = getCurrentUser(currentUser);		
		String userCurrScore = Integer.toString(currentBoard.getCurrentScore());
		DateFormat statisticsDataFormat = new java.text.SimpleDateFormat("hh:mm:ss");
		Date firstDate = statisticsDataFormat.parse(currentBoard.getStartTimer());
		Date secondDate = statisticsDataFormat.parse(currentBoard.getFinalTimer());
		long differenceBetweenDates = secondDate.getTime() - firstDate.getTime();			
		String bestTimeUntilNow =  Long.toString(differenceBetweenDates / 1000); 
		String tempDecrementTime = currentBoard.getFirstDecrementTimer();
		String currFirstDecTime;
		if(tempDecrementTime != ""){
                    
			Date thirdDate = statisticsDataFormat.parse(tempDecrementTime);
			long secondDifferenceBetweenDates = thirdDate.getTime() - firstDate.getTime();
			currFirstDecTime =  Long.toString(secondDifferenceBetweenDates / 1000);
		}
		else{
                        currFirstDecTime = bestTimeUntilNow;

		}
		if(currentUserPlaying.getUserScore() < Double.parseDouble(userCurrScore))
			currentUserPlaying.setUserScore(Double.parseDouble(userCurrScore));
		if(currentUserPlaying.getUserBestTime() < Integer.parseInt(bestTimeUntilNow))
			currentUserPlaying.setUserBestTime(Integer.parseInt(bestTimeUntilNow));
		if(currentUserPlaying.getUserBestCollisionTime() < Integer.parseInt(currFirstDecTime))
			currentUserPlaying.setBestUserCollisionTime(Integer.parseInt(currFirstDecTime));
	}
	private void sortAllScores() {
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
		winGamePanel.setSize(currentWidth, currentHeight);
		JButton returnButton = new JButton("Back");
                setBtnProperties(returnButton,frameWidth /2 - 40, frameHeight - 80, 80, 20);
		returnButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        setBoardMainScreen();
		    }
		});
                winGamePanel.setFocusable(true);
		multiBoard.repaint();
		multiBoard.add(winGamePanel,new Integer(0));
		multiBoard.add(returnButton, new Integer(1));
		
	}
	private void backToStartPlace() {
		resetPacman();
		pacman.replace();
		pacman.resetDeltaXAndY();
		pacman.setDirectionToTrue();
		for(int ghostIndex=0;ghostIndex<ghosts.length;ghostIndex++){
			ghosts[ghostIndex].replace();
		}
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gameDataPanel.getComponent(currentBoard.getCuurentLives()).hide();
		gameDataPanel.repaint();
		pacman.restartTimer();
		for(int ghostIndex=0;ghostIndex<ghosts.length;ghostIndex++){
			ghosts[ghostIndex].restartTimer();
			ghosts[ghostIndex].resetRoundTripDelay();
		}
	}
	private void playerLose() {
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
		
		JButton returnButton = new JButton("Back");
                setBtnProperties(returnButton,frameWidth /2 - 40, frameHeight - 80, 80, 20);
		returnButton.addActionListener(new ActionListener()
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
		multiBoard.add(returnButton,new Integer(1));
		gameOverPanel.setFocusable(true);
		multiBoard.repaint();
	}
	private void stopMovement() {
		pacman.stopTimer();
		for(int ghostIndex=0;ghostIndex<ghosts.length;ghostIndex++)
			ghosts[ghostIndex].stopTimer();
	}
	private boolean collisionHappendWithGhost(int xPosition, int yPosition) {
		Point[] ghostsCordinations = new Point[ghosts.length];
		for(int i=0;i<ghosts.length;i++){
                        int ghostxCord = (int)Math.round((ghosts[i].getXIndexPosition())/squareWidth);
                        int ghostYCord = (int)Math.round((ghosts[i].getYIndexPosition())/squareHeight);
			ghostsCordinations[i] = new Point(ghostxCord, ghostYCord);
			if((int)ghostsCordinations[i].getX() == xPosition && (int)ghostsCordinations[i].getY() == yPosition){
				if(ghosts[i].eats(pacman))
					return true;
				else{ 
					updateGhostPosition(i);
				}
			}
		}
		return false;
	}
	private void updateGhostPosition(int i) {
		ghosts[i].replace();
		ghosts[i].resetRoundTripDelay();
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
		pacman.deletePacmanFromMap();
		pacman = new RegularPacman(this, pacman.getXIndexPosition(), pacman.getYIndexPosition(), pacman.getCurrentPlayerType(), pacman.getDeltaX(), pacman.getDeltaY());
		initPacmanProperties();
	}

    private void setLabelProperties(JLabel lblTitle, String color, int posX, int posY, int width, int height) {
                if(color.equals("White"))
		lblTitle.setForeground(Color.WHITE);
                else if(color.equals("Green")) { 
                    		lblTitle.setForeground(Color.GREEN);                
                }else if(color.equals("Black")) {
                    		lblTitle.setForeground(Color.BLACK);               
                }
                else if(color.equals("Blue")) {
                    		lblTitle.setForeground(Color.BLUE);          
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

    private void setColor(JLabel currLabelName, String color) {
        if(color.equals("White"))
	currLabelName.setForeground(Color.WHITE);
        else if(color.equals("black")) { 
            	currLabelName.setForeground(Color.BLACK);

        }
    }
}
