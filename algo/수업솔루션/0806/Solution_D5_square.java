package com.ssafy.d5;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution_D5_square {

	Object o = new int[5];
	

	public static void main(String[] args) throws NumberFormatException, IOException { 
		System.setIn(new FileInputStream("res/input_d5square.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			sb.append("#").append(tc).append(" ");
			int N = Integer.parseInt(br.readLine());
			int[][] squ = new int[N][N];
			int[][] index = new int[N * N + 1][2];
			int[][] delta = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < N; j++) {
					squ[i][j] = Integer.parseInt(st.nextToken());
					index[squ[i][j]][0] = i;
					index[squ[i][j]][1] = j;
				}
			}

			int start = 0;
			int max = 0;
			for (int i = N * N; i > 0; i--) {
				int y = index[i][0];
				int x = index[i][1];

				int cnt = 1;

				while (true) {
					int flag = 1;
					for (int j = 0; j < 4; j++) {
						int ny = y + delta[j][0];
						int nx = x + delta[j][1];
						if (ny >= 0 && nx >= 0 && ny < N && nx < N) {
							if (squ[y][x] - squ[ny][nx] == 1) {
								cnt++;
								y = ny;
								x = nx;
								flag = 0;
								i--;
								break;
							}
						}
					}
					
					

					if (cnt >= max) {
						max = cnt;
						start = squ[y][x];
					}
					if (flag == 1)
						break;
				}
			}
			sb.append(start).append(" ").append(max).append("\n");
		}
		System.out.println(sb);
	}
}
