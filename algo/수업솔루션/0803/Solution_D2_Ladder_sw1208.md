# [S/W 문제해결 기본] 2일차 - Ladder1 (swexpert 1208번)



- 사다리를 탐색해서 목적지 위치를 찾는 문제

- 맨 위에서 탐색하다가 1번이 있으면 사다리이므로 탐색 시작

- 사다리 한칸씩 내려가면서 좌우를 탐색, 배열 밖이거나 0이면 무시, 1이면 해당 방향으로 0이 나오기 전까지 전진함

- 2가 나오면 목적지 도착, 끝까지 안나오면 다시 위에서 다음 사다리 탐색

  맨 아래서 2번 찾아서 올라가면 더 빨리 해결됨(문제 잘못읽고 맨위에서만 내려가야하는줄 착각함)

  

```java
package com.ssafy.d2;

import java.io.*;
import java.util.StringTokenizer;

public class Solution_D2_Ladder {

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/LadderInput.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
	
		next1: for (int n = 0; n < 10; n++) {
			int tc = Integer.parseInt(br.readLine());
			sb.append("#").append(tc).append(" ");
			int[][] ladder = new int[100][100];
			for (int i = 0; i < 100; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 100; j++) {
					ladder[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			next2: for (int i = 0; i < 100; i++) {
				if (ladder[0][i] == 1) {
					int a = 0;
					int b = i;
					while (true) {
						a++;
						if (ladder[a][b] == 2) {
							sb.append(i);
							sb.append("\n");
							continue next1;
						} else if (a == 99) {
							continue next2;
						} else if (b - 1 >= 0 && ladder[a][b - 1] == 1) {
							while (b - 1 >= 0 && ladder[a][b - 1] == 1) {
								b--;
							}
						} else if (b + 1 <= 99 && ladder[a][b + 1] == 1) {
							while (b + 1 <= 99 && ladder[a][b + 1] == 1) {
								b++;
							}
	
						}
					}
				}
			}
		}
		System.out.println(sb);
	}

}
```
