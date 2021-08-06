package com.ssafy.d3;

import java.io.*;
import java.util.StringTokenizer;

public class Battlefield_1873 {

	static int tankX;
	static int tankY;
	static int tankD;
	static int H;
	static int W;
	static char[][] map;
	static int[][] delta = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_1873.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			sb.append("#").append(tc).append(" ");
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			map = new char[H][W];
			for (int i = 0; i < H; i++) {
				String s = br.readLine();
				for (int j = 0; j < W; j++) {
					map[i][j] = s.charAt(j);
					if (CheckTank(map[i][j])) {
						tankX = j;
						tankY = i;
					}
				}
			}

			int size = Integer.parseInt(br.readLine());
			String task = br.readLine();
			for (int i = 0; i < size; i++) {
				if (task.charAt(i) == 'U' || task.charAt(i) == 'D' || task.charAt(i) == 'L' || task.charAt(i) == 'R') {
					tankMove(task.charAt(i));
				} else if (task.charAt(i) == 'S') {
					tankShoot(tankX, tankY);
				}
			}

			for (int i = 0; i < H; i++) {
				sb.append(map[i]).append("\n");
			}
		}
		System.out.println(sb);
	}

	private static void tankShoot(int tankX, int tankY) {
		int bulletX = tankX;
		int bulletY = tankY;
		while (bulletX + delta[tankD][1] >= 0 && bulletX + delta[tankD][1] < W && bulletY + delta[tankD][0] >= 0
				&& bulletY + delta[tankD][0] < H) {
			bulletX = bulletX + delta[tankD][1];
			bulletY = bulletY + delta[tankD][0];
			if (map[bulletY][bulletX] == '*') {
				map[bulletY][bulletX] = '.';
				return;
			} else if (map[bulletY][bulletX] == '#') {
				return;
			}
		}

	}

	private static void tankMove(char c) {
		if (c == 'U') {
			tankD = 0;
			if (tankY - 1 >= 0 && map[tankY - 1][tankX] == '.') {
				map[tankY--][tankX] = '.';
			}
			map[tankY][tankX] = '^';

		} else if (c == 'D') {
			tankD = 2;
			if (tankY + 1 < H && map[tankY + 1][tankX] == '.') {
				map[tankY++][tankX] = '.';
			}
			map[tankY][tankX] = 'v';

		} else if (c == 'L') {
			tankD = 3;
			if (tankX - 1 >= 0 && map[tankY][tankX - 1] == '.') {
				map[tankY][tankX--] = '.';
			}
			map[tankY][tankX] = '<';

		} else if (c == 'R') {
			tankD = 1;
			if (tankX + 1 < W && map[tankY][tankX + 1] == '.') {
				map[tankY][tankX++] = '.';
			}
			map[tankY][tankX] = '>';
		}
	}

	private static boolean CheckTank(char c) {
		if (c == '<') {
			tankD = 3;
			return true;
		} else if (c == '>') {
			tankD = 1;
			return true;
		} else if (c == 'v') {
			tankD = 2;
			return true;
		} else if (c == '^') {
			tankD = 0;
			return true;
		} else
			return false;

	}
}
