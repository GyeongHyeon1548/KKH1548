package b4949;

import java.io.*;
import java.util.Stack;

public class B4949 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Character> stack = new Stack<>();
		String match = "";
		while (true) {
			stack.clear();
			match = br.readLine();
			if (match.equals("."))
				break;
			String flag = "yes";
			for (int i = 0; i < match.length(); i++) {
				char m = match.charAt(i);
				if (m == '(' || m == '[') {
					stack.push(m);
				} else if (m == ')' || m == ']') {
					if (stack.isEmpty()) {
						flag = "no";
						break;
					} else {
						char c = stack.pop();
//					if (!(m == ')' && c == '(' || m == ']' && c == '[' || m == '}' && c == '{'
//					|| m == '>' && c == '<')) {
						if (!(m == ')' && c == '(' || m == ']' && c == '[')) {
							flag = "no";
							break;
						}
					}
				}
			}
			if (!stack.isEmpty())
				flag = "no";
			System.out.println(flag);
		}
	}
}