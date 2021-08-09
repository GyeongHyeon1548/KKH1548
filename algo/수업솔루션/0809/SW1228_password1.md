# 암호문 (swexpert 1228)



- 숫자가 나열된 암호문의 중간에 새로운 숫자를 삽입하는 문제
- 배열을 사용한다면 복잡하고 오래걸리지만 LinkedList를 사용하면 쉽게 해결 가능하다.
- 삽입할 문자열의 위치와 갯수, 문자열이 주어지므로 for문으로 쉽게 구현했음



```java
package com.ssafy.d0809;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class SW1228_password1 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_sw1228.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
	
		for (int tc = 1; tc <= 10; tc++) {
			int lang = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine(), " ");
			LinkedList<Integer> list = new LinkedList<Integer>();
			sb.append("#").append(tc).append(" ");
			for (int i = 0; i < lang; i++) {
				list.add(Integer.parseInt(st.nextToken()));
			}

			int com = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine(), " ");
	
			for (int i = 0; i < com; i++) {
				st.nextToken();
				int start = Integer.parseInt(st.nextToken());
				int end = Integer.parseInt(st.nextToken());
				for (int j = 0; j < end; j++) {
					list.add(start++, Integer.parseInt(st.nextToken()));
				}	
			}
			for (int j = 0; j < 10; j++) {
				sb.append(list.get(j)).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}

}
```

