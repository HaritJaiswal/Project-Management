package ProjectManagement;

import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.*;
import Trie.*;
import PriorityQueue.*;

import java.io.*;
import java.net.URL;
import java.util.Queue;
import java.util.ArrayList;

public class Scheduler_Driver extends Thread implements SchedulerInterface {
    RBTree<String, String> user_tree = new RBTree();
    Trie<Project> project_trie = new Trie();
    MaxHeap<Job> job_queue = new MaxHeap();
    RBTree<String, Job> NotReady_tree = new RBTree();
    Trie<Job> job_trie = new Trie();

    int global_time = 0;
    int counter = 0;
    boolean end = false;
    ArrayList<Job> done_list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();

        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File file) throws IOException {

        URL url = Scheduler_Driver.class.getResource("INP");
        file = new File(url.getPath());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

            switch (cmd[0]) {
                case "PROJECT":
                    handle_project(cmd);
                    break;
                case "JOB":
                    handle_job(cmd);
                    break;
                case "USER":
                    handle_user(cmd[1]);
                    break;
                case "QUERY":
                    handle_query(cmd[1]);
                    break;
                case "":
                    handle_empty_line();
                    break;
                case "ADD":
                    handle_add(cmd);
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }

        end = true;
        run_to_completion();

        print_stats();

    }

    private boolean check_in_done_list(String key){
        boolean flag = false;
        for(int i=0;i<done_list.size();i++){
            Job j = done_list.get(i);
            if(j.name().compareTo(key)==0){
                flag = true;
                return flag;
            }
        }

        return flag;
    }

    private Job job_search(String key, MaxHeap<Job> pq){
        for (int i=0;i<pq.list.size();i++) {
            if(pq.list.get(i).obj.name().compareTo(key)==0){
                return pq.list.get(i).obj;
            }
        }
        return null;
    }

    private boolean project_exists(String key){
        Object search = project_trie.search(key);
        if(search==null){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean user_exists(String key){
        RedBlackNode search = user_tree.search(key);
        if(search.key==null){
            return false;
        }
        else{
            return true;
        }
    }


    private void dfs(RedBlackNode node){
        if(node==null){
            return;
        }

        this.dfs(node.left);

        for(int i=0;i<node.list.size();i++){
            Job j = (Job)node.list.get(i);
            job_queue.insert(j);
        }

        this.dfs(node.right);
    }


    private void fin(){
        this.dfs(NotReady_tree.root);
    }


    @Override
    public void run() {
        // till there are JOBS
        schedule();
    }


    @Override
    public void run_to_completion() {

        while(job_queue.list.size()>0){
            System.out.println("Running code");
            System.out.println("Remaining jobs: "+job_queue.list.size());

            this.schedule();
        }
       
    }

    @Override
    public void handle_project(String[] cmd) {
        String name = cmd[1];
        String priority = cmd[2];
        String budget = cmd[3];
        Project p = new Project(name, priority, budget);
        project_trie.insert(name, p);
        System.out.println("Creating project");
    }

    @Override
    public void handle_job(String[] cmd) {
        String name = cmd[1];
        String project = cmd[2];
        String user = cmd[3];
        String run_time = cmd[4];

        if(!project_exists(project)){
            System.out.println("Creating job");
            System.out.println("No such project exists. "+project);
            return;
        }

        if(!user_exists(user)){
            System.out.println("Creating job");
            System.out.println("No such user exists: "+user);
            return;
        }

        Job j = new Job(name, project, user, run_time, counter);
        Project corresp = (Project)(project_trie.search(project).obj);
        j.priority = corresp.priority;
        counter++;
        job_queue.insert(j);
        job_trie.insert(name,j);
        user_tree.insert(user, name); //UPDATING user_tree
        System.out.println("Creating job");  //+" name:"+j.name()+" project's name: "+project+" priority: "+j.priority+" --timeAdded:"+j.timeAdded
    }

    @Override
    public void handle_user(String name) {
        User u = new User(name);
        user_tree.insert(name,null);
        System.out.println("Creating user");
    }

    @Override
    public void handle_query(String key) {
        TrieNode<Job> jNode = this.job_trie.search(key);
        
        System.out.println("Querying");

        if(jNode!=null){
            Job j = (Job)jNode.obj;

            if(j!=null && j.status==0){
               System.out.println(key+": NOT FINISHED"); 
            }

            else if(j!=null && j.status==1){
                System.out.println(key+": COMPLETED"); 
            }
        }else{
            System.out.println(key+": NO SUCH JOB");
        }
    
    }

    @Override
    public void handle_empty_line() {
        System.out.println("Running code");
        System.out.println("Remaining jobs: "+job_queue.list.size());
        this.schedule();
    }

    @Override
    public void handle_add(String[] cmd) {
        String project_name = cmd[1];
        int add = Integer.parseInt(cmd[2]);

        if(!project_exists(project_name)){
            System.out.println("No such project exists. "+project_name);
            return;
        }
        else{
            TrieNode<Project> tNode = project_trie.search(project_name);
            Project p = (Project)tNode.obj;
            p.budget = p.budget + add;
            System.out.println("ADDING Budget");
            RedBlackNode rbNode = NotReady_tree.search(project_name);
            int budget = p.budget;

            int index = 0;

            while(rbNode!=null && index<rbNode.list.size()){
                Job rem_job = (Job)rbNode.list.get(index); 
                if(rem_job!=null && rem_job.run_time<=budget){
                    rbNode.list.remove(index);
                    // System.out.println("@@@@@@@@@@@ IN handle_add: "+rem_job.name()+" project: "+rem_job.project_name+" priority: "+rem_job.priority+" timeADDED= "+rem_job.timeAdded);
                    job_queue.insert(rem_job);
                    index--;
                }
                index++;
            }

        }
    }

    @Override
    public void print_stats() {
        System.out.println("--------------STATS---------------");
        System.out.println("Total jobs done: "+done_list.size());

        int index = 0;
        while(index<done_list.size()){
            Job j = done_list.get(index);
            System.out.println("Job{user='"+j.user_name+"', project='"+j.project_name+"', jobstatus=COMPLETED, execution_time="+j.run_time+", end_time="+j.completed_time+", name='"+j.job_name+"'}");
            index++;
        }

        System.out.println("------------------------");
        System.out.println("Unfinished jobs: ");
        this.fin();
        int numUnfinished = job_queue.list.size();

        for (int i=0;i<numUnfinished;i++) {
            Job unfinished_j = job_queue.extractMax();
            System.out.println("Job{user='"+unfinished_j.user_name+"', project='"+unfinished_j.project_name+"', jobstatus=REQUESTED, execution_time="+unfinished_j.run_time+", end_time=null"+", name='"+unfinished_j.job_name+"'}");
        }

        System.out.println("Total unfinished jobs: "+numUnfinished);
        System.out.println("--------------STATS DONE---------------");
    }

    @Override
    public void schedule() {
        Job j = job_queue.extractMax();
        if(j==null){
            if(!end){
                System.out.println("Execution cycle completed");
            }
            else{
                System.out.println("System execution completed");
            }
            return;
        }
        // System.out.println("Just extracted job: "+j.name()+" priority: "+j.priority+"  timeAdded: "+j.timeAdded);

        // for(int k=0;k<job_queue.list.size();k++){
        //   System.out.println("Jobs in heap in array order: "+job_queue.list.get(k).obj.name()+ " project's name: "+job_queue.list.get(k).obj.project_name+" priority: "+job_queue.list.get(k).obj.priority+" TimeAdded: "+job_queue.list.get(k).obj.timeAdded);
        // }

        Project p = (Project)project_trie.search(j.project_name).obj;
        System.out.println("Executing: "+j.job_name+" from: "+p.name);

        if(job_queue.list.size()<0){

            return;
        }

        else if(j.run_time<=p.budget){
            // System.out.println("job run_time: "+j.run_time+"|||  project's budget: "+p.budget);
            j.status = 1;

            Job jx = (Job)this.job_trie.search(j.name()).obj;
            jx.status = 1;

            global_time += j.run_time;
            j.completed_time = global_time;
            p.budget -= j.run_time;
            System.out.println("Project: "+p.name+" budget remaining: "+p.budget);
            if(!end){
                System.out.println("Execution cycle completed");
            }
            else{
                System.out.println("System execution completed");
            }

            done_list.add(j);
            
            return;
        }
        else if(j.run_time>p.budget){
            NotReady_tree.insert(p.name, j);
            System.out.println("Un-sufficient budget.");
            this.schedule();
        }

        return;
    }
}
