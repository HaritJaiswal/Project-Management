package ProjectManagement;


public class Project {
	String name;
	int budget, priority;
	public Project(String s, String priority, String budget){
		this.name = s;
		this.priority = Integer.parseInt(priority);
		this.budget = Integer.parseInt(budget);
	}
}
