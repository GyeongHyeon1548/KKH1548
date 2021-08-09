

# 한빈이와 Spot Mart (swexpert 9229)



- N개의 과자봉지중 2개를 고르고, 둘의 무게를 더한것이 무게제한(M)보다 작은것들중 최대값을 찾는 문제
- 재귀로 조합을 구현하면 쉽게 해결됨.
- 무게제한을 맞출 수 있는 조합이 없을때는 max를 -1로 초기화하여 -1이 나오게 한다.



```java
package com.ssafy;

import java.io.*;
import java.util.StringTokenizer;

public class SW9229 {

	static int max;
	static int M;
	static int N;
	static int[] snack;
	static int[] ch;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("input_sw9229.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
	
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			sb.append("#").append(tc).append(" ");
			max = -1;
			ch = new int[2];
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
	
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
	
			st = new StringTokenizer(br.readLine(), " ");
			snack = new int[N];
			for (int i = 0; i < N; i++) {
				snack[i] = Integer.parseInt(st.nextToken());
			}
	
			calc(0, 0);
			sb.append(max).append("\n");
		}
		System.out.println(sb);
	
	}
	
	public static void calc(int cnt, int start) {
	
		if (cnt == 2) {
			int sum = ch[0] + ch[1];
			if (sum <= M) {
				if (sum >= max) {
					max = sum;
					return;
				}
				return;
			}
			return;
		}
		for (int i = start; i < N; i++) {
			ch[cnt] = snack[i];
			calc(cnt + 1, i + 1);
		}
	
	}

}
```

