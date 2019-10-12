package ProjectManagement;
import java.util.ArrayList;

public class User implements Comparable<User> {
	String name;

	public User(String s){
		this.name = s;
	}


    @Override
    public int compareTo(User user) {
        return this.name.compareTo(user.name);
    }
}
