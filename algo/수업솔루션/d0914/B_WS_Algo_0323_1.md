# B_WS_Algo_0323_1



- 파란색은 노란색 다음에만 올수있고 노란색은 노란색, 파란색 뒤에 올 수 있으므로, 다음 단계의 노란색을 칠하는 경우의수는 현재 전체의 경우의수(YB[0]+YB[1]), 파란색을 칠하는 경우의 수는 현재 노란색인 경우의 수(YB[0])이다.



```java
package com.ssafy.d0914;

import java.io.*;

public class B_WS_Algo_0323_1 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		int N = Integer.parseInt(br.readLine());
		int[] YB = { 1, 1 };
	
		for (int i = 1; i < N; i++) {
			int a = YB[0] + YB[1];
			int b = YB[0];
			YB[0] = a;
			YB[1] = b;
		}
	
		System.out.println("총 경우의 수는 " + (YB[0] + YB[1]));
	}

}
```

