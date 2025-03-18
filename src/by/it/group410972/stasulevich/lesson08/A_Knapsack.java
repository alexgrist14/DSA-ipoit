package by.it.group410972.stasulevich.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt();
        int n = scanner.nextInt();
        int[] gold = new int[n];

        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        int[] dp = new int[W + 1];

        for (int i = 1; i <= W; i++) {
            for (int j = 0; j < n; j++) {
                if (gold[j] <= i) {
                    dp[i] = Math.max(dp[i], dp[i - gold[j]] + gold[j]);
                }
            }
        }

        return dp[W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}