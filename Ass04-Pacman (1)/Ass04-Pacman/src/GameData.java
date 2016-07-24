import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameData {
	private int currentLives;
	private int currentScore;
	private String startTimer;
	private String finalTimer;
	private String timerFirstDecreasing;
	
	public GameData(int numOfLives, int startScore){
		this.currentLives=numOfLives;
		this.currentScore=startScore;
		this.startTimer = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		this.finalTimer="";
		this.timerFirstDecreasing="";
	}
        public void setFinalTimer(String finalTimer){
		this.finalTimer = finalTimer;
	}
	public String getStartTimer(){
		return startTimer;
	}
	public String getFinalTimer(){
		return finalTimer;
	}
	public String getFirstDecreasingTimer(){
		return timerFirstDecreasing;
	}
	public void setFirstDecreasingTimer(String dec){
		this.timerFirstDecreasing = dec;
	}
	
	public int getCurrentLives(){
		return currentLives;
	}
	public void decrementLives(){
		currentLives--;
	}
	public int getCurrentScore(){
		return currentScore;
	}
	public void addToScore(int scoreToAdd){
		currentScore=currentScore+scoreToAdd;
	}
	
}
