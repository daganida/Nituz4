
public class User {
	private String uName;
	private int age;
	private String uPassword;
	private char sex;
	private double score;
	private int bestTime;
	private int bestColTime;
	
	public User(String uname, char sex, int age, String pass){
                this.age = age;
		this.sex = sex;
		this.uPassword = pass;
		this.uName = uname;
		
		setStartingGameVariables();
	}

            private void setStartingGameVariables() {
                this.score = 0;
                this.bestColTime = 0;
                this.bestTime = 0;
            }
	
	public User(String uname, char sex,int age, String pass, double score, int bestTime, int bestColTime){
		this.uName = uname;
		this.age = age;
		this.sex = java.lang.Character.toLowerCase(sex);
		this.uPassword = pass;
		setCurrentGameVariables(score, bestTime, bestColTime);
	}

    private void setCurrentGameVariables(double score1, int bestTime1, int bestColTime1) {
        this.score = score1;
        this.bestColTime = bestTime1;
        this.bestTime = bestColTime1;
    }
	
	public String getUsername(){
		return uName;
	}
	public double getUserScore(){
		return score;
	}
	public int getUserAge(){
		return age;
	}
	public int getUserBestTime(){
		return bestTime;
	}
	public int getUserBestCollisionTime(){
		return bestColTime;
	}
	public String getUserPassword(){
		return uPassword;
	}
	public char getUserSex(){
		return sex;
	}
	public void setUserScore(double score){
		this.score = score;
	}
	public void setUserBestTime(int bestTime) {
		this.bestTime = bestTime;
	}
	public void setBestUserCollisionTime(int bestColTime) {
		this.bestColTime = bestColTime;
	}
	public String getString(){
		return uName + ";" + sex + ";" + age + ";" + uPassword + ";" + score + ";" + bestTime + ";" + bestColTime;
	}
}
