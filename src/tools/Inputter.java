package tools;

import java.util.Scanner;

public class Inputter {

    public static Scanner sc = new Scanner(System.in);

    public static String inputString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public static String inputNonEmptyString(String msg) {
        while (true) {
            String s = inputString(msg);
            if (!s.isEmpty()) {
                return s;
            }
        }
    }

    // int >= min
    public static int inputInt(String msg, int min) {
        while (true) {
            try {
                int n = Integer.parseInt(inputString(msg));
                if (n >= min) {
                    return n;
                }
                System.out.println("The value must be at least " + min);
            } catch (Exception e) {
                System.out.println("Enter an integer!");
            }
        }
    }

    // int in [min..max]
    public static int inputIntRange(String msg, int min, int max) {
        while (true) {
            try {
                int n = Integer.parseInt(inputString(msg));
                if (n >= min && n <= max) {
                    return n;
                }
                System.out.println("Please enter a number from " + min + " to " + max);
            } catch (Exception e) {
                System.out.println("Enter an integer!");
            }
        }
    }
}
