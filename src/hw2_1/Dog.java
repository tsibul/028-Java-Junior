package hw2_1;

public class Dog extends Animal{

    public int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Dog(String name, int age, int height) {
        super(name, age);
        this.height = height;
    }

    public void makeSound(){
        System.out.println("Гав");
    }

}
