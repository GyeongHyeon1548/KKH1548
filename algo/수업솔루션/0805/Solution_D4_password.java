package com.ssafy.d4;

import java.io.*;
import java.util.*;

public class Solution_D4_password {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		for (int TC = 0; TC < 10; TC++) {
			int tc = Integer.parseInt(br.readLine());
			sb.append("#").append(tc).append(" ");
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			Queue<Integer> pass = new LinkedList<Integer>();
			for (int i = 0; i < 8; i++) {
				pass.offer(Integer.parseInt(st.nextToken()));
			}

			int n = 1;
			int t = pass.peek();
			while (t > 0) {
				t = pass.poll();
				t = t - n++;
				if (t < 0) {
					t = 0;
				}
				pass.offer(t);
				if (n > 5)
					n = 1;
			}

			for (int i = 0; i < 8; i++) {
				sb.append(pass.poll()).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
