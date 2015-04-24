package model;

/**
 * @author: Akshay Jain Bohara
 * @package: model
 * @project: gpa-calc
 */
public class Course {

    String name;

    double gradePoint;

    double creditHour;

    public Course(String name, double gradePoint, double creditHour){
        this.name = name;
        this.gradePoint = gradePoint;
        this.creditHour = creditHour;
    }


    /**
     * weighted = gradePoint * credit hours
     * @return weighted grade point for the course
     */
    public double getWeighted(){
        return this.gradePoint * this.creditHour;
    }
}
