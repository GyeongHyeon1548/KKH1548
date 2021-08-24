# Contact (swexpert 1238)



- 방향 그래프의 시작점부터 단계별 진행하면서 마지막 단계중 가장 큰 값을 찾는 문제

- //1 정점의 숫자와 단계를 나타내는 배열을 저장하는 큐
- //2 방향 그래프 생성
- //3 시작 정점과 단계를 큐에 넣고 시작
- //4 현재 정점의 단계가 이전 정점의 단계보다 크면 max값을 현재 단계값으로 바꿔줌.
- //5 현재 정점에서 갈 수 있는 모든 정점중 방문되지 않은 정점들을 단계를 1 증가시켜서 큐에 넣음
- //6 max값 을 구해줌. 4~6을 모든 정점을 탐색해서 큐에 남은 정점이 없을때까지 반복



```java
package com.ssafy.d0823;

import java.io.*;
import java.util.*;

class Node {
	int num;
	Node next;

	@Override
	public String toString() {
		return "Node [num=" + num + ", next=" + next + "]";
	}

	public Node(int num, Node next) {
		super();
		this.num = num;
		this.next = next;
	}

}

public class SW1238_Contact {

	static int max;
	static int level;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		for (int tc = 1; tc <= 10; tc++) {
			sb.append("#").append(tc).append(" ");
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int start = Integer.parseInt(st.nextToken());
			Node[] list = new Node[101];
			boolean[] isVisited = new boolean[101];
	
			Queue<Integer[]> Q1 = new LinkedList<Integer[]>();	// 1
	
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N / 2; i++) {	// 2
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				list[from] = new Node(to, list[from]);
			}
	
			Q1.offer(new Integer[] { start, 1 });// 3
			isVisited[start] = true;
			call(isVisited, list, Q1);
			sb.append(max).append("\n");
		}
		System.out.println(sb);
	}

	static void call(boolean[] isVisited, Node[] list, Queue<Integer[]> Q1) {
		level = 1;
		max = 0;
		while (!Q1.isEmpty()) {
			int num = Q1.peek()[0];
			int cnt = Q1.peek()[1];
			Q1.poll();
			Node n = list[num];
			if (cnt > level) {	// 4
				level = cnt;
				max = num;
			}
			while (n != null) {
				if (isVisited[n.num] == false) {	// 5
					isVisited[n.num] = true;
					Q1.offer(new Integer[] { n.num, cnt + 1 });
				}
				n = n.next;
			}
			max = Math.max(max, num);	// 6
		}
	}

}
```

