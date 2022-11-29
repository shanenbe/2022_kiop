package tests;

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        s.getBestFriendOf(new Student());
        s.getBestFriendOf(new Person());
    }

}

class Person {
    Person getBestFriendOf(Student s) {
        System.out.println("Student");
        return null; }
}

class Student extends Person {
    Student getBestFriendOf(Person p) {
        System.out.println("Person");
        return null; }
}