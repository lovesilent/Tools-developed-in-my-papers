package com.wuyu.tool2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class Utility {
	public void writeAdjacencyEgos(int egoNetworkNodeNumber, boolean[][] matrix, HashMap<String,Integer> nodes_map){
		File file = new File("Output/twitter/"+egoNetworkNodeNumber+".egos");
		ArrayList<Integer> edgeList = new ArrayList<Integer>();
		int ego = nodes_map.get(egoNetworkNodeNumber+"");
		for (int i = 0; i < 81306; i++) {
//			System.out.println(node_maps.get(egoNetworkNodeNumber));
			if(matrix[ego][i])
				edgeList.add(i);
		}
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Integer edgeNo : edgeList) {
				bw.write(findNodeNumber(edgeNo,nodes_map));
				for (int i = 0; i < 81306; i++) {
					if(i!=ego && matrix[edgeNo][i]){
						bw.write(" "+findNodeNumber(i,nodes_map));
					}
						
				}
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("3There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
		
	}
	
	private String findNodeNumber(int id, HashMap<String,Integer> nodes_map){
		for(String getKey: nodes_map.keySet()){
            if(nodes_map.get(getKey).equals(id)){
                return getKey;
            }
        }
		return null;
	}
	
	public void writeMaps(HashMap<String,Integer> nodes_map){
		File file = new File("Output/twitter/nodes_map.txt");
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for(Entry<String,Integer> entry: nodes_map.entrySet()){
				bw.write(entry.getKey()+" "+entry.getValue());
				bw.newLine();
				bw.flush();
			}
			bw.close();
			fw.close();	
		}catch (IOException e) {
			System.out.println("5There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
	}
	
	public void writeGraph(HashMap<String,Integer> nodes_map){
		File file = new File("Twitter_Data/twitter_combined.txt");
		File file2 = new File("Output/twitter/twitter.txt"); 
		String line ="";
		try {
			Scanner scan = new Scanner(new FileReader(file));
			FileWriter fw = new FileWriter(file2);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("81306 1768149");
			bw.newLine();
			while(scan.hasNextLine()){
				line = scan.nextLine();
				String[] numbers = line.split(" ");
				bw.write(nodes_map.get(numbers[0])+" "+nodes_map.get(numbers[1])+" "+0.02+" "+0.02);
				bw.newLine();
				bw.flush();
			}
			scan.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("4There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
	}
	
	public ArrayList<String> getAllFileNames(){
		String path = "C://Users//wuyu//Desktop//数据//twitter//twitter";
		// get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        ArrayList<String> filenames = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                // only take file name
            	String filename = array[i].getName();
            	int sum = filename.lastIndexOf(".");
            	if(filename.substring(sum + 1).equals("edges"))
            		filenames.add(filename.substring(0, sum));
            }
        }
        return filenames;
	}
}
