# 햄버거 다이어트(swexpert 5215)



- 햄버거 재료 조합의 경우의 수를 구하고, 제한된 재료 칼로리안에서 가장 큰 가중치를 찾는 문제
- 재귀를 통해 재료의 부분집합을 구하고, 부분집합들의 칼로리와 가중치를 구해서 기준에 맞는 부분집합중 최대의 가중치를 탐색함.
- 위 코드는 간단하게 작성, 실행시간이 오래걸림.
- 아래 코드는 인자값을 추가하여 기준 미달인 부분집합을 빠르게 스킵하여 실행시간 단축함



```java
package com.ssafy.d0809;

import java.io.*;
import java.util.StringTokenizer;

public class SW5215_hamberger {

	static int max = 0;
	static int num;
	static int limit;
	static int[][] kcal;
	static int[][] select;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_sw5215.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			max = 0;
			sb.append("#").append(tc).append(" ");
	
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			num = Integer.parseInt(st.nextToken());
			limit = Integer.parseInt(st.nextToken());
	
			kcal = new int[num][3];
			select = new int[num][3];
	
			for (int i = 0; i < num; i++) {
				int item[] = new int[3];
				st = new StringTokenizer(br.readLine(), " ");
				kcal[i][0] = Integer.parseInt(st.nextToken());
				kcal[i][1] = Integer.parseInt(st.nextToken());
				kcal[i][2] = 0;
	
			}
			subset(0);
			sb.append(max).append("\n");
		}
		System.out.println(sb);
	}
	
	static void subset(int cnt) {
	
		if (cnt == num) {
			int sum = 0;
			int tsum = 0;
			for (int i = 0; i < num; i++) {
				if (kcal[i][2] == 1) {
					sum += kcal[i][1];
					tsum += kcal[i][0];
				}
			}
			if (sum <= limit) {
				if (tsum >= max) {
					max = tsum;
				}
			}
			return;
	
		}
		kcal[cnt][2] = 1;
		subset(cnt + 1);
		kcal[cnt][2] = 0;
		subset(cnt + 1);
	}

}
```



```java
package com.ssafy.d0809;

import java.io.*;
import java.util.StringTokenizer;

public class SW5215_hamberger {

	static int max = 0;
	static int num;
	static int limit;
	static int[][] kcal;

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_sw5215.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			max = 0;

			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			num = Integer.parseInt(st.nextToken());
			limit = Integer.parseInt(st.nextToken());
			kcal = new int[num][2];

			for (int i = 0; i < num; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				kcal[i][0] = Integer.parseInt(st.nextToken());
				kcal[i][1] = Integer.parseInt(st.nextToken());

			}
			subset(0, 0, 0);
			sb.append("#").append(tc).append(" ").append(max).append("\n");
		}
		System.out.println(sb);
	}

	static void subset(int cnt, int kcal_n, int tsum) {

		if (kcal_n > limit)
			return;
		if (tsum >= max)
			max = tsum;
		if (cnt == num)
			return;
		subset(cnt + 1, kcal_n + kcal[cnt][1], tsum + kcal[cnt][0]);
		subset(cnt + 1, kcal_n, tsum);
	}
}
```

