package com.ssafy.d4;

import java.io.*;
import java.util.Stack;

public class Solution_D4_Match {

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_d5match.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Character> stack = new Stack<>();
		StringBuilder sb = new StringBuilder();
		for (int tc = 1; tc <= 11; tc++) {
			stack.clear();
			sb.append("#").append(tc).append(" ");
			int size = Integer.parseInt(br.readLine());
			String match = br.readLine();
			int flag = 1;
			for (int i = 0; i < size; i++) {
				char m = match.charAt(i);
				if (m == '(' || m == '{' || m == '[' || m == '<') {
					stack.push(m);
				} else {
					char c = stack.pop();
//					if (!(m == ')' && c == '(' || m == ']' && c == '[' || m == '}' && c == '{'
//					|| m == '>' && c == '<')) {
					if (!(m - 1 == c || m - 2 == c)) {
						flag = 0;
						break;
					}
				}
			}
			sb.append(flag).append("\n");
		}
		System.out.println(sb);
	}
}
