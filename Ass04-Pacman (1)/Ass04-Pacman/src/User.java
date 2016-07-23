
public class User {
	private String uName;
	private int age;
	private String uPassword;
	private char sex;
	private double score;
	private int bestTime;
	private int bestColTime;
	
	public User(String uname, char sex, int age, String pass){
		this.uName = uname;
		this.age = age;
		this.sex = sex;
		this.uPassword = pass;
		this.score = 0;
		this.bestColTime = 0;
		this.bestTime = 0;
	}
	
	public User(String uname, char sex,int age, String pass, double score, int bestTime, int bestColTime){
		this.uName = uname;
		this.age = age;
		this.sex = java.lang.Character.toLowerCase(sex);
		this.uPassword = pass;
		this.score = score;
		this.bestColTime = bestTime;
		this.bestTime = bestColTime;
	}
	
	public String getUsername(){
		return uName;
	}
	public double getScore(){
		return score;
	}
	public int getAge(){
		return age;
	}
	public int getBestTime(){
		return bestTime;
	}
	public int getBestColTime(){
		return bestColTime;
	}
	public String getPassword(){
		return uPassword;
	}
	public char getSex(){
		return sex;
	}
	public void setScore(double score){
		this.score = score;
	}
	public void setBestTime(int bestTime) {
		this.bestTime = bestTime;
	}
	public void setBestColTime(int bestColTime) {
		this.bestColTime = bestColTime;
	}
	public String getString(){
		return uName + ";" + sex + ";" + age + ";" + uPassword + ";" + score + ";" + bestTime + ";" + bestColTime;
	}
}
