package hw2_1;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        Animal[] animals = new Animal[5];
        animals[0] = new Cat("Barsik", 3, 43);
        animals[1] = new Cat("Pushok", 2, 40);
        animals[2] = new Dog("Burek", 5, 45);
        animals[3] = new Dog("Sharik", 3, 32);
        animals[4] = new Dog("Polkan", 5, 65);

        for(int i = 0; i < animals.length; i++){
            Class<?> clazz = animals[i].getClass();
            Field [] fields = clazz.getFields();
            for (Field field : fields){
                field.setAccessible(true);
                System.out.print(field.getName() + " " + field.get(animals[i]) + " ");
            }
            System.out.println();
            try {
                Method method = clazz.getMethod("makeSound");
                method.invoke(animals[i]);
            }
            catch (Exception e){
                System.out.println("No sound");
            }
        }
    }
}