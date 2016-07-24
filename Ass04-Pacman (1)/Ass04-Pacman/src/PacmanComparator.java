import java.util.Comparator;

public class PacmanComparator implements Comparator<User>{
    @Override
	public int compare(User user1, User user2) {
        if(user1.getUserScore() < user2.getUserScore())
        	return 1;
        else if(user2.getUserScore() < user1.getUserScore())
        	return -1;
        return 0;
    }
}
