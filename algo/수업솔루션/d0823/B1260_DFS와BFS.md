# DFS와BFS (boj 1260)



- 그래프를 DFS와 BFS로 탐색한 결과를 비교하는 문제

- //1 숫자 두개를 받아서 연결리스트 형태로 무향 그래프를 생성, 리스트 생성시에 오름차순으로 리스트가 생성되도록 함.
- //2 DFS와 BFS실행함.



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

public class B1260_DFS와BFS {

	static boolean[] visited;
	static int N, M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		StringTokenizer st = new StringTokenizer(br.readLine());	
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int V = Integer.parseInt(st.nextToken());	
		Node[] p = new Node[N + 1];
	
		for (int i = 0; i < M; i++) {	// 1
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			Node n = p[from];
			if (n == null) {
				p[from] = new Node(to, null);
			} else if (n.num > to) {
				p[from] = new Node(to, p[from]);
			} else {
				while (true) {
					if (n.next == null) {
						n.next = new Node(to, null);
						break;
					} else if (n.next.num > to) {
						n.next = new Node(to, n.next);
						break;
					}
					n = n.next;
				}
			}
	
			n = p[to];
			if (n == null) {
				p[to] = new Node(from, null);
			} else if (n.num > from) {
				p[to] = new Node(from, p[to]);
			} else {
				while (true) {
					if (n.next == null) {
						n.next = new Node(from, null);
						break;
					} else if (n.next.num > from) {
						n.next = new Node(from, n.next);
						break;
					}
					n = n.next;
				}
			}
		}
		visited = new boolean[N + 1];

		dfs(p, V, visited);	// 2
		System.out.println();
		bfs(p, V, visited);
	}

	static void bfs(Node[] p, int start, boolean[] visited) {
		Queue<Integer> Q = new LinkedList<Integer>();
		visited = new boolean[N + 1];
		Q.offer(start);
		visited[start] = true;
		while (!Q.isEmpty()) {
			int t = Q.poll();
			System.out.print(t + " ");
			Node pt = p[t];
			while (pt != null) {
				if (!visited[pt.num]) {
					Q.offer(pt.num);
					visited[pt.num] = true;
				}
				pt = pt.next;
			}
		}
	}
	
	static void dfs(Node[] p, int t, boolean[] visited) {
		visited[t] = true;
		System.out.print(t + " ");
		Node pt = p[t];
		while (pt != null) {
			if (!visited[pt.num]) {
				dfs(p, pt.num, visited);
			}
			pt = pt.next;
		}
	}
}
```

