package b1373;

import java.io.*;

public class B1373 {
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		int oct = 1;
		StringBuilder sb = new StringBuilder();
		int a = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			a += ((int) s.charAt(i) - 48) * oct;
			if (oct == 4 || i == 0) {
				oct = 1;
				sb.append(a);
				a = 0;
			} else
				oct *= 2;
		}
		System.out.println(sb.reverse());
	}
}
