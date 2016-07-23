import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameData {
	private int lives;
	private int score;
	private String startTimer;
	private String endTimer;
	private String firstDecTimer;
	
	public GameData(int lives, int score){
		this.lives=lives;
		this.score=score;
		this.startTimer = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		this.endTimer="";
		this.firstDecTimer="";
	}
	
	public int getLives(){
		return lives;
	}
	public void decLives(){
		lives--;
	}
	public int getScore(){
		return score;
	}
	public void addScore(int i){
		score=score+i;
	}
	public void setEndTimer(String end){
		this.endTimer = end;
	}
	public String getStartTimer(){
		return startTimer;
	}
	public String getEndTimer(){
		return endTimer;
	}
	public String getFirstDecTimer(){
		return firstDecTimer;
	}
	public void setFirstDecTimer(String dec){
		this.firstDecTimer = dec;
	}
}
