package org.secondTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Integer> numbers = readFile("sample.txt");
        System.out.println("Минимальное: " + _min(numbers));
        System.out.println("Максимальное: " + _max(numbers));
        System.out.println("Сумма: " + _sum(numbers));
        System.out.println("Произведение: " + _mult(numbers));
    }

    public static Integer _min(List<Integer> nums) {
        return nums.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
    }

    public static Integer _max(List<Integer> nums) {
        return nums.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
    }

    public static Integer _sum(List<Integer> nums) {
        return nums.stream().mapToInt(v -> v).sum();
    }

    public static Integer _mult(List<Integer> nums) {
        return nums.stream().reduce(1, (multiplied, el) -> multiplied * el);
    }

    public static List<Integer> readFile(String filename) throws IOException {
        BufferedReader reader;
        List<Integer> numbers = new ArrayList<>();
        reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        numbers.addAll(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList());
        reader.close();
        return numbers;
    }
}
