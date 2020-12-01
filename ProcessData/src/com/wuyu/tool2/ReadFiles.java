package com.wuyu.tool2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class ReadFiles {
	public boolean[][] getAdjacencyMatrix(HashMap<String,Integer> nodes_map) {
		File file = new File("Twitter_Data/twitter_combined.txt");
		boolean[][] matrix = new boolean[81306][81306];
		String line ="";
		int count = 0;
		int i,j;
		try {
			Scanner scan = new Scanner(new FileReader(file));
			while(scan.hasNextLine()){
				line = scan.nextLine();
				String[] numbers = line.split(" ");
				if(nodes_map.containsKey(numbers[0])){
					i = nodes_map.get(numbers[0]);
				}else{
					i = count++;
					nodes_map.put(numbers[0], i);
				}
				if(nodes_map.containsKey(numbers[1])){
					j = nodes_map.get(numbers[1]);
				}else{
					j = count++;
					nodes_map.put(numbers[1], j);
				}	
				matrix[i][j]=true;
			}
			System.out.println(count);
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("1There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
		return matrix;
	}
	
}
