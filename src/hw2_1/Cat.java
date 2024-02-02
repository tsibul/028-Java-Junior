package hw2_1;

public class Cat extends Animal{
    public int length;

    public Cat(String name, int age, int length) {
        super(name, age);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void makeFilth (){
        System.out.println(this.getName() + " made derty trick with your boots");
    }
}
