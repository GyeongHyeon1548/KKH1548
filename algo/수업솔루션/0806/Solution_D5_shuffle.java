package com.ssafy.d5;

import java.io.*;
import java.util.*;

public class Solution_D5_shuffle {

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_d5shuffle.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int TC = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TC; tc++) {
			sb.setLength(0);
			sb.append("#").append(tc).append(" ");

			int t = Integer.parseInt(br.readLine());

			Queue<String> q1 = new LinkedList<String>();
			Queue<String> q2 = new LinkedList<String>();

			st = new StringTokenizer(br.readLine(), " ");

			for (int i = 0; i < t - t / 2; i++)
				q1.offer(st.nextToken());
			for (int i = 0; i < t / 2; i++)
				q2.offer(st.nextToken());

			while (!q2.isEmpty()) {
				sb.append(q1.poll()).append(" ");
				sb.append(q2.poll()).append(" ");
			}
			if (!q1.isEmpty())
				sb.append(q1.poll()).append(" ");

			System.out.println(sb);
		}
	}

}
