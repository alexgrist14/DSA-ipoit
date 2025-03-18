package by.it.group410972.stasulevich.lesson06;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_LongNotUpSubSeq {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_LongNotUpSubSeq.class.getResourceAsStream("dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] m = new int[n];

        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        int[] dp = new int[n];
        int[] prev = new int[n];
        int maxLength = 0;
        int maxIndex = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Минимальная длина подпоследовательности для каждого элемента
            prev[i] = -1; // Изначально предыдущий элемент отсутствует

            for (int j = 0; j < i; j++) {
                if (m[j] >= m[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }

            if (dp[i] > maxLength) {
                maxLength = dp[i];
                maxIndex = i;
            }
        }

        List<Integer> sequence = new ArrayList<>();
        while (maxIndex != -1) {
            sequence.add(maxIndex + 1); // Индексация с 1
            maxIndex = prev[maxIndex];
        }

        System.out.println(maxLength);
        for (int i = sequence.size() - 1; i >= 0; i--) {
            System.out.print(sequence.get(i) + " ");
        }

        return maxLength;
    }
}