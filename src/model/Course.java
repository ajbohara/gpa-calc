package model;

/**
 * @author: Akshay Jain Bohara
 * @package: model
 * @project: gpa-calc
 * @desc: Course class
 */
public class Course {

    String name;

    double gradePoint;

    double creditHour;

    /**
     * Course Model
     * @param name  course name
     * @param gradePoint    grade point
     * @param creditHour    credit hour
     */
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
