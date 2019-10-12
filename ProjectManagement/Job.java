package ProjectManagement;

public class Job implements Comparable<Job> {
	public int status; // 0 = REQUESTED && 1 = COMPLETED
	public String job_name, project_name, user_name;
	public int run_time, priority, completed_time;
	public int timeAdded = 0;

	public Job(String name, String project, String user, String run_time, int timeAdded ){
		this.job_name = name;
		this.project_name = project;
		this.user_name = user;
		this.run_time = Integer.parseInt(run_time);
		this.status = 0;
		this.timeAdded = timeAdded;
	}

	public String name(){
		return job_name;
	}

    @Override
    public int compareTo(Job job) {
    	if((this.priority-job.priority)!=0){
        	return (this.priority - job.priority);
    	}
    	else{
    		return (job.timeAdded - this.timeAdded);
    	}

    }

    public String toString(){//////HAVE TO BE DELETED IN THE END
    	return "job's name: "+job_name+" project's name: "+project_name+" priority: "+priority+"  timeAdded: "+timeAdded;
    }
}