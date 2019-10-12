package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
        this.name = trim;
        this.marks = parseInt;
    }


    @Override
    public int compareTo(Student student) {
        return this.marks-student.marks;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        String s = ("Student{name='"+name+"', marks="+marks+"}");
        return s;
    }
}
