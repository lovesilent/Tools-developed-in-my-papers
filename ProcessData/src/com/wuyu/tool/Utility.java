package com.wuyu.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utility {
	public void writeAdjacencyEgos(int egoNetworkNodeNumber, int[][] matrix){
		File file = new File("Output/"+egoNetworkNodeNumber+".egos");
		ArrayList<Integer> edgeList = new ArrayList<Integer>();
		for (int i = 0; i < 4039; i++) {
			if(matrix[egoNetworkNodeNumber][i]==1)
				edgeList.add(i);
		}
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Integer edgeNo : edgeList) {
				bw.write(edgeNo.toString());
				for (int i = 0; i < 4039; i++) {
					if(i!=egoNetworkNodeNumber && matrix[edgeNo][i]==1)
						bw.write(" "+i);
				}
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("3There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
		
	}
	
	public void writeGraph(){
		File file = new File("Facebook_Data/facebook_combined.txt");
		File file2 = new File("Output/facebook.txt"); 
		String line ="";
		try {
			Scanner scan = new Scanner(new FileReader(file));
			FileWriter fw = new FileWriter(file2);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("4309 88234");
			bw.newLine();
			while(scan.hasNextLine()){
				line = scan.nextLine();
				String[] numbers = line.split(" ");
				bw.write(numbers[0]+" "+numbers[1]+" "+0.01+" "+0.01);
				bw.newLine();
				bw.write(numbers[1]+" "+numbers[0]+" "+0.01+" "+0.01);
				bw.newLine();
				bw.flush();
			}
			scan.close();
		} catch (IOException e) {
			System.out.println("4There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
	}
}
