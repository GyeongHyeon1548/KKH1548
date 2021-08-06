package b1152;

import java.io.*;

public class B1152 {
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();

		int cnt = 0;
		if (s.charAt(0) == ' ')
			cnt--;
		for (int i = 0; i < s.length(); i++) {

			if (s.charAt(i) == ' ' && i != s.length() - 1)
				cnt++;
		}
		cnt++;
		System.out.println(cnt);

	}
}
