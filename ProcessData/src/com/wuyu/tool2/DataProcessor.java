package com.wuyu.tool2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class DataProcessor {
	
	public static void main(String[] args){
		ReadFiles readFiles = new ReadFiles();
		Utility utility = new Utility();
//		ArrayList<String> filenames = utility.getAllFileNames();
		HashMap<String,Integer> nodes_map = new HashMap<>();
		boolean[][] matrix = readFiles.getAdjacencyMatrix(nodes_map);
//		for(String filename:filenames){
//			utility.writeAdjacencyEgos(Integer.parseInt(filename), matrix, nodes_map);
//			System.out.println(filename + " Completed!");
//		}

//		utility.writeMaps(nodes_map);
		utility.writeGraph(nodes_map);
		
	}

}
