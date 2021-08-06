package b2309;

import java.io.*;
import java.util.Arrays;

public class B2309 {

	static boolean flag = false;
	static int sum = 0;
	static int N = 9;
	static int[] nan7 = new int[7];
	static int[] nan9 = new int[9];
	static boolean[] isSelected = new boolean[9];

	static void sum(int cnt, int start) {
		if (flag)
			return;
		if (cnt == 7) {
			int sum = 0;
			for (int i = 0; i < 7; i++) {
				sum += nan7[i];
			}
			if (sum == 100) {
				for (int i = 0; i < 7; i++) {
					System.out.println(nan7[i]);
				}
				flag = true;
			}

		} else {
			for (int i = start; i < N; i++) {// i: 인덱스
				nan7[cnt] = nan9[i];
				sum(cnt + 1, i + 1);
			}
		}

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		nan9 = new int[9];
		for (int i = 0; i < 9; i++) {
			nan9[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(nan9);
		sum(0, 0);

	}
}
