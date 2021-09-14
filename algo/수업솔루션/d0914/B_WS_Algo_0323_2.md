# B_WS_Algo_0323_2



- DP 방식으로 풀면, Ncm를 이루는 경우의 수는 N-1cm의 경우의 수에 파란막대를 더한것, N-1cm의 경우의 수에 노란막대를 더한것, N-2cm의 경우의 수에 빨간 막대를 더한것이 된다.
- 0,1cm는 초기값을 설정해주고 그 뒤로는 DP방식으로 계산한다.



```java
package com.ssafy.d0914;

import java.io.*;

public class B_WS_Algo_0323_2 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		int N = Integer.parseInt(br.readLine());
	
		int[] length = new int[N + 1];
	
		length[0] = 1;
		length[1] = 2;
	
		for (int i = 2; i <= N; i++) {
			int cnt = 0;
			cnt += length[i - 1]; // 파란막대
			cnt += length[i - 1]; // 노란막대
			cnt += length[i - 2]; // 빨간막대
			length[i] = cnt;
		}
	
		System.out.println(N + "cm를 만드는 경우의 수는 " + length[N] + "개");
	}

}
```

