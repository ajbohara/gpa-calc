package model;

import java.util.*;

/**
 * @author: Akshay Jain Bohara
 * @package: model
 * @project: gpa-calc
 * @desc: null
 */
public class GradePointModel extends Observable{

    // Course list
    private ArrayList<Course> courses;

    // Grade Point Average of the Model
    public double gradePointAverage;

    //Grading System
    public static final Map<String, Double> gradePoint;


    static {
        gradePoint = new LinkedHashMap<String, Double>();
        gradePoint.put("A", 4.000);
        gradePoint.put("A-", 3.667);
        gradePoint.put("B+", 3.333);
        gradePoint.put("B", 3.000);
        gradePoint.put("B-", 2.667);
        gradePoint.put("C+", 2.333);
        gradePoint.put("C", 2.000);
        gradePoint.put("C-", 1.667);
        gradePoint.put("D", 1.000);
        gradePoint.put("F", 0.000);

    }

    /**
     *
     */
    public GradePointModel(){
        this.courses = new ArrayList<Course>();
        this.gradePointAverage = 0;
    }

    /**
     * Add courses to the GradePoint model to determine your gpa.
     * @param courseName String representation of the course name
     * @param letterGrade String representation of the letter grade
     * @param creditHours Integer representation of the credit hours
     */
    public void addCourse(String courseName, String letterGrade, double creditHours){
        this.courses.add(new Course(courseName, gradePoint.get(letterGrade), creditHours));
    }

    /**
     * @return final grade point average
     */
    private void calculateGradePoint(){

        double weightedGradePoint = 0;

        double totalCreditHours = 0;

        for (Course course: this.courses){

            weightedGradePoint += course.getWeighted();

            totalCreditHours += course.creditHour;
        }

        this.gradePointAverage =  weightedGradePoint / totalCreditHours;

    }

    public void getGradePoint(){
        calculateGradePoint();

        // Round GPA to 2nd Decimal
        this.gradePointAverage = (Math.round(this.gradePointAverage * 1000.0d) / 1000.0d);

        setChanged();
        notifyObservers();

    }

    public void clear(){
        courses.clear();
        gradePointAverage = 0;
    }
}
