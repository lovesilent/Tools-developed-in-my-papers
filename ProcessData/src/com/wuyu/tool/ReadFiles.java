package com.wuyu.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFiles {
	
	public int[][] getAdjacencyMatrix() {
		File file = new File("Facebook_Data/facebook_combined.txt");
		int[][] matrix = new int[4039][4039];
		String line ="";
		try {
			Scanner scan = new Scanner(new FileReader(file));
			while(scan.hasNextLine()){
				line = scan.nextLine();
				String[] numbers = line.split(" ");
				int i = Integer.parseInt(numbers[0]);
				int j = Integer.parseInt(numbers[1]);
				matrix[i][j]=1;
				matrix[j][i]=1;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("1There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
		return matrix;
	}
	
	/**
	public ArrayList<String> getEdgeList(int egoNetworkNodeNumber){
		File file = new File("Facebook_Data/"+egoNetworkNodeNumber+".edges");
		String line = "";
		ArrayList<String> edgeList = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(new FileReader(file));
			while(scan.hasNextLine()){
				line = scan.nextLine();
				String[] numbers = line.split(" ");
				edgeList.add(numbers[1]);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("2There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
		return edgeList;
	}
	**/
}
