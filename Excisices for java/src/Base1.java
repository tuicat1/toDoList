abstract class Person {
    protected String name;
    public Person () {}
    public Person(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public abstract void eat ();
    public String toString(){
        return String.format("%s(%s)", getClass().getName(), name);
    }
}
class Student extends Person {
    public Student(String n){
        super(n);
    }
    public void eat(){
        System.out.println("eating fried rice!");
    }
}
class CollegeStudent extends Student {
    public CollegeStudent(String n){
        super(n);
    }
    public void eat(){
        System.out.println("eating Subway Sub of the Day!");
    }
}
class TertiaryStudent  extends Student {
    public TertiaryStudent (String n){
        super(n);
    }
    public void eat(){
        System.out.println("eating roast chicken!");
    }
}
class Employee   extends Person {
    public Employee(String n) {
        super(n);
    }

    public void eat() {
        System.out.println("eating buffet!");
    }
}
public static void printEatMessages(Person[] array) {
    for (Person p : array){
        System.out.printf("%s, ", p.getName());
        p.eat();
    }
    }

