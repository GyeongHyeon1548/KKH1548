package com.ssafy.d3;

import java.io.*;

public class Farm_2805 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_2805.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			sb.append("#").append(tc).append(" ");
			int SIZE = Integer.parseInt(br.readLine());
			int[][] farm = new int[SIZE][SIZE];

			for (int i = 0; i < SIZE; i++) {
				String s = br.readLine();
				for (int j = 0; j < SIZE; j++) {
					farm[i][j] = (int) s.charAt(j) - 48;
				}
			}

			int h = SIZE / 2;
			int sum = 0;
			for (int i = 0; i < SIZE; i++) {
				for (int j = Math.abs(h); j < SIZE - Math.abs(h); j++) {
					sum += farm[i][j];
				}
				h--;
			}
			sb.append(sum).append("\n");
		}
		System.out.println(sb);
	}
}
