package hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> random = randomArray(8, 10);
        System.out.println(random);
//1. Напишите программу, которая использует Stream API для обработки списка чисел.
// Программа должна вывести на экран среднее значение всех четных чисел в списке.
        System.out.println(
                random.stream().filter(i -> i % 2 == 0).mapToInt(Integer::intValue).average()
        );
//2. *Дополнительная задча: Переработать метод балансировки корзины товаров cardBalancing() с использованием Stream API

    }

    public static ArrayList<Integer> randomArray(Integer arrayLength, Integer arrayRange) {
        ArrayList<Integer> randomArr = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < arrayLength; i++) {
            randomArr.add(random.nextInt(1, arrayRange));
        }
        return randomArr;
    }
}