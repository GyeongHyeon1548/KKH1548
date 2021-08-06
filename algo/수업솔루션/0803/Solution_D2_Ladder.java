package com.ssafy.d2;

import java.io.*;
import java.util.StringTokenizer;

public class Solution_D2_Ladder {

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/LadderInput.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		next1: for (int n = 0; n < 10; n++) {
			int tc = Integer.parseInt(br.readLine());
			sb.append("#").append(tc).append(" ");
			int[][] ladder = new int[100][100];
			for (int i = 0; i < 100; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 100; j++) {
					ladder[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			next2: for (int i = 0; i < 100; i++) {
				if (ladder[0][i] == 1) {
					int a = 0;
					int b = i;
					while (true) {
						a++;
						if (ladder[a][b] == 2) {
							sb.append(i);
							sb.append("\n");
							continue next1;
						} else if (a == 99) {
							continue next2;
						} else if (b - 1 >= 0 && ladder[a][b - 1] == 1) {
							while (b - 1 >= 0 && ladder[a][b - 1] == 1) {
								b--;
							}
						} else if (b + 1 <= 99 && ladder[a][b + 1] == 1) {
							while (b + 1 <= 99 && ladder[a][b + 1] == 1) {
								b++;
							}

						}
					}
				}
			}
		}
		System.out.println(sb);
	}
}
