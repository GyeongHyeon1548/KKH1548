package b2605;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B2605 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList<Integer> a = new ArrayList<>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		for (int i = 1; i <= num; i++) {
			int r = Integer.parseInt(st.nextToken());
			a.add(a.size() - r, i);
		}

		for (int i = 0; i < a.size(); i++) {
			System.out.print(a.get(i) + " ");
		}
	}
}
