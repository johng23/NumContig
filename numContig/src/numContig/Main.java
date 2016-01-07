package numContig;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static int length = 4;
	public static int numAreas = 2;
	
	
	public static int[] tempNode = new int[2];
	public static int[][] s = new int[length][length];
	public static int[][][] id = new int[length][length][3];
	public static long arrayNum = 0; //number of contiguous arrays
	public static boolean[] isUsed = new boolean[numAreas];
	public static void main(String[] args) {
		BigInteger max = new BigInteger(Integer.toString(numAreas));
		max = max.pow(length * length);
		for(BigInteger k = new BigInteger("0"); k.compareTo(max) < 0; k=k.add(BigInteger.ONE)) {
			if(isContig(s)) {
				arrayNum++;
				for(int i = 0; i < length; i++) {
					System.out.println(Arrays.toString(s[i]));
				}
				System.out.println();
			}
			increment(s);
		}
		System.out.println(arrayNum);
//		s = new int[][]{{0,0,0},
//						{0,1,0},
//						{1,1,0}};
//		System.out.println(isContig(s));
//		for(int i = 0; i < length; i++) {
//			for(int j = 0; j < length; j++) {
//				System.out.print(id[i][j][2] + " ");
//			}
//			System.out.println();
//		}
	}
	public static void increment(int[][] in) {
		for(int i = length - 1; i > -1; i--) {
			for(int j = length -1; j > -1; j--) {
				if(in[i][j] + 1 == numAreas) {
					in[i][j] = 0;
				}
				else {
					in[i][j]++;
					return;
				}
			}
		}
	}
	public static boolean isContig(int[][] in) {
		ArrayList<int[]> edges = new ArrayList<int[]>();
		for(int i = 0; i < length - 1; i++) {
			for(int j = 0; j < length; j++) {
				if(in[i][j] == in[i + 1][j]) {
					edges.add(new int[]{i, j, i + 1, j});
				}
			}
		}
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length - 1; j++) {
				if(in[i][j] == in[i][j + 1]) {
					edges.add(new int[]{i, j, i, j + 1});
				}
			}
		}
		return isAreaSetsOfEdges(edges);
	}
	public static boolean isAreaSetsOfEdges(ArrayList<int[]> edges) {
		refreshId();
		int size = edges.size();
		int numGroups = 0;
		for(int i = 0; i < size; i++) {
			union(edges.get(i));
		}
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				if(id[i][j][2] == 0) {
					if(!isUsed[s[i][j]]) {
						isUsed[s[i][j]] = true;
					}
					else {
						return false;
					}
					numGroups++;
				}
			}
		}
		if(numGroups != numAreas) {
			return false;
		}
		return true;
	}
	public static void union(int[] edge) {
		int[] first = root(new int[] {edge[0], edge[1]});
		int[] second = root(new int[] {edge[2], edge[3]});
		if(first[0] != second[0] || first[1] != second[1]) {
			id[second[0]][second[1]][0] = first[0];
			id[second[0]][second[1]][1] = first[1];
			id[second[0]][second[1]][2] = 1;
		}
	}
	public static int[] root(int[] node) { //node is {x,y}
		while(id[node[0]][node[1]][0] != node[0] || id[node[0]][node[1]][1] != node[1]) {
			tempNode = node.clone();
			node[0] = id[tempNode[0]][tempNode[1]][0];
			node[1] = id[tempNode[0]][tempNode[1]][1];
		}
		return node;
	}
	public static void refreshId(){
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				id[i][j][0] = i;
				id[i][j][1] = j;
				id[i][j][2] = 0;
			}
		}
		for(int i = 0; i < numAreas; i++) {
			isUsed[i] = false;
		}
	}
}
