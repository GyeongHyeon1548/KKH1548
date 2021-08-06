package b14467;

import java.io.*;
import java.util.StringTokenizer;

public class B14467 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		int cow;
		int ch;
		int cnt = 0;
		int[] CS = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

		for (int i = 0; i < tc; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cow = Integer.parseInt(st.nextToken()) - 1;

			ch = Integer.parseInt(st.nextToken());
			if (CS[cow] == -1) {
				CS[cow] = ch;
			} else if (CS[cow] != ch) {
				cnt++;
				CS[cow] = ch;
			}
		}
		System.out.println(cnt);
	}
}
