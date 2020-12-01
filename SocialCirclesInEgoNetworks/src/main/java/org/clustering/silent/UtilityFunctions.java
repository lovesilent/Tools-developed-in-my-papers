package org.clustering.silent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class UtilityFunctions {
	
	public ArrayList<FacebookNode> calculateJacardCoefficient(int adjacencyMatrix[][]){
		
		ArrayList<FacebookNode> allNodeSimilarityList = new ArrayList<FacebookNode>();
		int noOfRows = adjacencyMatrix.length; // This determines the number of nodes in the array
		double jaccardCoeff=0; // Local variable to store the J-C similarity value
		
		for(int k=0;k<noOfRows;k++){ // Iterating over all the nodes starting from 0th Node (For loop 1)
			
			FacebookNode newNode = new FacebookNode(); // Creating a new Node
			newNode.nodeNumber = "N"+adjacencyMatrix[k][0]; // Giving the new node a name
			
			for(int i=0;i<noOfRows;i++){ // Iterating over all the nodes once again (For loop 2)
				
				/* Calculating the Jaccard - Coefficient - Start */
				
				HashMap<String, Integer> tempHash = new HashMap<String, Integer>(); // Hash Map to help calculate the J-C similarity value 
				
				if(i != k) // Condition to avoid calculating a nodes similarity value with itself 
				{
					for(int j=0;j<adjacencyMatrix[i].length;j++){ // Iterating over the columns of the node with which the J-C value is to be calculated
						
							if(j==0){ // Initially adding zero for all values in the hash map
								tempHash.put("00", 0);
								tempHash.put("10", 0);
								tempHash.put("01", 0);
								tempHash.put("11", 0);
							}
							else{ // Logic to icrement the keys of the hashmap based on the features in both the nodes
								if(adjacencyMatrix[k][j] == 0 && adjacencyMatrix[i][j] == 0)
									tempHash.put("00", tempHash.get("00") + 1);
								else if(adjacencyMatrix[k][j] == 1 && adjacencyMatrix[i][j] == 0)
									tempHash.put("10", tempHash.get("10") + 1);
								else if(adjacencyMatrix[k][j] == 0 && adjacencyMatrix[i][j] == 1)
									tempHash.put("01", tempHash.get("01") + 1);
								else
									tempHash.put("11", tempHash.get("11") + 1);
							}
					}
				}
				else
					continue;
				
				jaccardCoeff = (double)tempHash.get("11")/(tempHash.get("11") + tempHash.get("10") + tempHash.get("01"));
				jaccardCoeff = (double)Math.round(jaccardCoeff * 10000)/10000;
				
				/* Adding the node number and its similarity value to the 
				 * similarity map of the node for which the FacebookNode object was made
				 */
				
				newNode.similarityMap.put("N"+adjacencyMatrix[i][0], jaccardCoeff);	
				
				/* Calculating the Jaccard - Coefficient - End */
				
			} // End of for loop 2
			
			/* Adding the newNode which filled similarityMap and topN Map to the list of Facebook Nodes*/
			allNodeSimilarityList.add(newNode); 
			
		} // End of for loop 1
		
		return allNodeSimilarityList;
	}
	
	public void writeFaceBookNodesToFile(ArrayList<FacebookNode> allFBNodes) throws IOException {
		
		File file = new File("Output/TwitterNode_List.txt"); // Taking the filename as a string parameter into a file type object
		FileWriter fw = new FileWriter(file); // Passing the file object to a FileWriter instance
		BufferedWriter bw = new BufferedWriter(fw); // Passing the FileWriter object to a BufferedWriter instance
		
		/* Iterating over the list of FacebookNodes and printing them onto a file */	
		for(FacebookNode fbNode: allFBNodes){
			bw.write("Node number : " + fbNode.nodeNumber + " ");
			bw.newLine();
			for(Entry<String, Double> entry : fbNode.similarityMap.entrySet())
				bw.write(entry.getKey() + " --> " + entry.getValue() + ", ");
			bw.newLine();
			bw.newLine();
			bw.write("**------------------------------------------------------------**");
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeCirclesToFile(ArrayList<Circle> allCircles) throws IOException {
		
		System.out.println("Number of Circles : " + allCircles.size());
		File file = new File("Output/Circles.txt"); // Taking the filename as a string parameter into a file type object
		FileWriter fw = new FileWriter(file); // Passing the file object to a FileWriter instance
		BufferedWriter bw = new BufferedWriter(fw); // Passing the FileWriter object to a BufferedWriter instance
		int j;
		
		/* Iterating over the list of circles and printing them onto a file */
		for(Circle circle: allCircles){
				bw.write(circle.circleNumber + " ");
				bw.newLine();
				bw.write("Nodes in the circle are : ");
				for(j=0;j<circle.list.size();j++)
					bw.write(circle.list.get(j) + " ");
				bw.newLine();
//				for(Entry<String, Double> entry : circle.weights.entrySet())
//					bw.write(entry.getKey() + " --> " + entry.getValue() + ", ");
				bw.newLine();
				bw.write("**------------------------------------------------------------**");
				bw.newLine();
		}
		bw.close();
	}
	
	public FacebookNode getFBNode(ArrayList<FacebookNode> allNodeSimilarityList, String nodeNumber){
		FacebookNode fbNode = new FacebookNode(); 
		for(FacebookNode facebookNode : allNodeSimilarityList){
			if(facebookNode.nodeNumber.equalsIgnoreCase(nodeNumber)){
				fbNode.nodeNumber = facebookNode.nodeNumber;
				fbNode.similarityMap = facebookNode.similarityMap;
				fbNode.commonMap = facebookNode.commonMap;
				fbNode.weightMap = facebookNode.weightMap;
				fbNode.edgeList = facebookNode.edgeList;
				fbNode.egoList = facebookNode.egoList;
				fbNode.isSelcted = facebookNode.isSelcted;
				break;
			}
		}
		return fbNode;
	}
	
	public FacebookNode fetchFBNode(ArrayList<FacebookNode> allNodeSimilarityList, String nodeNumber){
		for(FacebookNode facebookNode : allNodeSimilarityList){
			if(facebookNode.nodeNumber.equalsIgnoreCase(nodeNumber)){
				return facebookNode;
			}
		}
		return null;
	}
	
	public ArrayList<FacebookNode> sortArrayListByEdgeCount(ArrayList<FacebookNode> allNodeSimilarityList){
		
		int i, j;
		int listSize = allNodeSimilarityList.size();
		
		/* Applying a Bubble sort in Descending order */
		
		for(i=0;i<=listSize-1;i++){
			for(j=0;j<listSize-i-1;j++){
				
				/* Swapping the Facebook Nodes if the jth node's edgelist size is less than (j+1)st node's edgelist size */
				if(allNodeSimilarityList.get(j).edgeList.size() < allNodeSimilarityList.get(j+1).edgeList.size()){
					FacebookNode tempFacebookNode = new FacebookNode();
					tempFacebookNode = allNodeSimilarityList.get(j);
					allNodeSimilarityList.set(j, allNodeSimilarityList.get(j+1));
					allNodeSimilarityList.set(j+1, tempFacebookNode);
				}
			}
		}
		return allNodeSimilarityList;	
	}
	
	
	public ArrayList<FacebookNode> sortArrayListByWeight(ArrayList<FacebookNode> allNodes){
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = 0; j < allNodes.size()-i-1; j++) {
				if(allNodes.get(j).weightMap.size() < allNodes.get(j+1).weightMap.size()){
					FacebookNode tempFacebookNode = new FacebookNode();
					tempFacebookNode = allNodes.get(j);
					allNodes.set(j, allNodes.get(j+1));
					allNodes.set(j+1, tempFacebookNode);
				}
			}
		}
		return allNodes;
	}
	
	public void writeFaceBookEgosToFile(ArrayList<FacebookNode> allFBNodes) throws IOException {
		
		File file = new File("Output/TwitterEgos_List.txt"); // Taking the filename as a string parameter into a file type object
		FileWriter fw = new FileWriter(file); // Passing the file object to a FileWriter instance
		BufferedWriter bw = new BufferedWriter(fw); // Passing the FileWriter object to a BufferedWriter instance
		
		/* Iterating over the list of FacebookNodes and printing them onto a file */	
		for(FacebookNode fbNode: allFBNodes){
			bw.write("Node number : " + fbNode.nodeNumber + " ");
			bw.newLine();
//			for (String facebookEgos : fbNode.egoList) {
//				bw.write(facebookEgos + ", ");
//			}
			for(Entry<String, Double> entry : fbNode.weightMap.entrySet())
				bw.write(entry.getKey() + " --> " + entry.getValue() + ", ");
			bw.newLine();
			bw.newLine();
			bw.write("**------------------------------------------------------------**");
			bw.newLine();
		}
		bw.close();
	}
	
	public ArrayList<FacebookNode> calculateCommon(ArrayList<FacebookNode> allFBNodes){
		int size = allFBNodes.size();
		for (int i = 0; i < size; i++) {
			HashMap<String, Double> temMap = new HashMap<String, Double>();
			for (int j = 0; j < size; j++) {
				if(j!=i){
					double result = calCommon(allFBNodes.get(i).egoList,allFBNodes.get(j).egoList);
					allFBNodes.get(i).commonMap.put(allFBNodes.get(j).nodeNumber, (double) result);
					double weight = result + allFBNodes.get(i).similarityMap.get(allFBNodes.get(j).nodeNumber);
					if(weight>0.0)
						temMap.put(allFBNodes.get(j).nodeNumber, weight/2);
				}
			}
			Set<Entry<String, Double>> set = temMap.entrySet();
			ArrayList<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(set);
			Collections.sort(list, new Comparator<Map.Entry<String, Double>>() 
			{
				public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)
				{
					return (o2.getValue()).compareTo(o1.getValue());
				}
			});
			/* Adding to the Linked HashMap - Start */
			for (Map.Entry<String, Double> entry : list){
				allFBNodes.get(i).weightMap.put(entry.getKey(), entry.getValue());
			}
		}
		return allFBNodes;
	}

	private Double calCommon(ArrayList<String> egoList, ArrayList<String> egoList2) {
		double sum =0.0;
		for (int i = 0; i < egoList.size(); i++) {
			for (int j = 0; j < egoList2.size(); j++) {
				if(egoList.get(i).equals(egoList2.get(j))){
					sum+=1;
				}
			}
		}
		int min = egoList.size() < egoList2.size()? egoList.size() : egoList2.size();
		if(min==0) return 0.0;
		return sum/min;
	}
	
	public Circle calculateWeights(ArrayList<FacebookNode> allNodeSimilarityList, Circle circle){
		HashMap<String, Double> temMap = new HashMap<String, Double>();
		for (String fbNodeName : circle.list) {
			FacebookNode fbNode = getFBNode(allNodeSimilarityList, fbNodeName);
			LinkedHashMap<String, Double> weightMap = fbNode.weightMap;
			for (Entry<String, Double> entry : weightMap.entrySet()) {
				if(!temMap.containsKey(entry.getKey()) || entry.getValue()>temMap.get(entry.getKey())){
					temMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		for (String nodeNumber : circle.list) {
			temMap.remove(nodeNumber);
		}
		
		Set<Entry<String, Double>> set = temMap.entrySet();
		ArrayList<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() 
		{
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)
			{
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		circle.weights.clear();
		/* Adding to the Linked HashMap - Start */
		for (Map.Entry<String, Double> entry : list){
			circle.weights.put(entry.getKey(), entry.getValue());
		}
		return circle;
	}


	public boolean isCombine(Circle circle1, Circle circle2) {
//		Entry<String, Double> entry1 = circle1.weights.entrySet().iterator().next();
//		Entry<String, Double> entry2 = circle2.weights.entrySet().iterator().next();
//		if(entry1.getValue()>0.5 && entry2.getValue()>0.5){
//			
//		}
		int m = 0,n = 0,count1 = 0,count2 =0;
		for (Entry<String, Double> entry1 : circle1.weights.entrySet()) {
			if(m==circle2.list.size()) break;
			for (int j = 0; j < circle2.list.size(); j++) {
				if(entry1.getKey().equals(circle2.list.get(j)))
					count1++;
			}
			m++;
		}
		for (Entry<String, Double> entry2 : circle2.weights.entrySet()) {
			if(n==circle1.list.size()) break;
			for (int i = 0; i < circle1.list.size(); i++) {
				if(entry2.getKey().equals(circle1.list.get(i)))
					count2++;
			}
			n++;
		}
			
		if(count2==0) return false;
		if(m>1){
			if(count1>=m/2 && count2>=n/2)
				return true;
		}else{
			if(count2>=n/2) return true;
		}
		
			
//		if(entry1.getValue()>=0.0 && entry2.getValue()>=0.0){
//			for (int i = 0; i < circle1.list.size(); i++) {
//				for (int j = 0; j < circle2.list.size(); j++) {
//					if(entry2.getKey().equals(circle1.list.get(i)) && entry1.getKey().equals(circle2.list.get(j)))
//						return true;
//				}
//			}
//		}
		
		return false;
	}
	
}
