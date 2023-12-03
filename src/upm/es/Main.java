package upm.es;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        dummy(); //TODO: Remove it. Simply to test console input/output
    }

    private static void dummy() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first integer: ");
        int num1 = scanner.nextInt();
        System.out.print("Enter the second integer: ");
        int num2 = scanner.nextInt();
        int sum = addNumbers(num1, num2);
        System.out.println("Sum: " + sum);
        scanner.close();
    }

    private static int addNumbers(int a, int b) {
        return a + b;
    }
}