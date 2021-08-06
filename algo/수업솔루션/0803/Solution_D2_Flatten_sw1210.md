# [S/W 문제해결 기본] 1일차 - Flatten (swexpert 1210번)



- 배열에 저장된 높이의 최고점과 최저점을 찾아서 최고점을 1감소, 최저점을 1증가시킴
- 횟수 안에 끝나지 않을시 max와 min을 출력시켜줌
- max와 min의 값이 같아지면 평탄화 완료이므로 즉시 종료함



```java
package com.ssafy.d2;

import java.io.*;
import java.util.StringTokenizer;

public class Solution_D2_Flatten {
//입력은 res폴더 참조
    
public static void main(String[] args) throws NumberFormatException, IOException {
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	in = new BufferedReader(new StringReader(str));

	StringBuilder sb = new StringBuilder();
	next: for (int tc = 1; tc <= 10; tc++) {
		sb.append("#").append(tc).append(" ");
		int maxDump = Integer.parseInt(in.readLine());
		int[] num = new int[100];
		int max = 0;
		int min = 0;
		StringTokenizer s = new StringTokenizer(in.readLine(), " ");
		for (int i = 0; i < 100; i++) {
			num[i] = Integer.parseInt(s.nextToken());
			if (num[i] > num[max]) {
				max = i;
			} else if (num[i] < num[min]) {
				min = i;
			}
		}

		for (int r = 0; r < maxDump; r++) {
			num[max]--;
			num[min]++;
			for (int i = 0; i < 100; i++) {
				if (num[i] > num[max]) {
					max = i;
				} else if (num[i] < num[min]) {
					min = i;
				}
			}

			if (num[max] - num[min] == 0 )
				continue next;
		}
		for (int i = 0; i < 100; i++) {
			if (num[i] > num[max]) {
				max = i;
			} else if (num[i] < num[min]) {
				min = i;
			}
		}
		sb.append(num[max] - num[min]);
		sb.append("\n");
	}
	System.out.println(sb);
}

```

