package org.clustering.silent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class RoleMining {
	
	public static void main(String[] args) throws IOException {
		
		// Creating objects for different classes 
		ReadFiles readFiles = new ReadFiles();
		UtilityFunctions utilityFunctions = new UtilityFunctions();
		Cluster cluster = new Cluster();
		Scanner scanner  =  new Scanner(System.in);
		
		// Taking input from the user
		System.out.println("Please enter any one Ego node network number from the following list and say enter");
		System.out.println("Your choice is ");
		int egoNetworkNodeNumber = scanner.nextInt();
		scanner.close(); // Closing the scanner object
		
		final long startTime = System.currentTimeMillis(); // Declaring a long variable to note the Start time
		
		ArrayList<FacebookNode> allNodeSimilarityList = new ArrayList<FacebookNode>(); // Creating a ArrayList of FacebookNodes		
		
		// Reading the '.feat' file and storing it in the double dimensional array		
		int adjacencyMatrix[][] = readFiles.getAdjacencyNodeFeatureMatrix(egoNetworkNodeNumber); 
		// Calculating the Jaccard Coefficient for each node in the ego network
		allNodeSimilarityList = utilityFunctions.calculateJacardCoefficient(adjacencyMatrix); 
		utilityFunctions.writeFaceBookNodesToFile(allNodeSimilarityList); // Write all the FacebookNodes Numbers and respective top ten similarities to File
		System.out.println("Total number of nodes is Ego Network " + egoNetworkNodeNumber + " are " + allNodeSimilarityList.size());
		
		allNodeSimilarityList = readFiles.getEdges(allNodeSimilarityList, egoNetworkNodeNumber); // Get the connections of each node and store it in the edge list of a FacebookNode object
		allNodeSimilarityList = readFiles.getEgos(allNodeSimilarityList, egoNetworkNodeNumber);//Get the nodes of egos
		utilityFunctions.calculateCommon(allNodeSimilarityList);
//		allNodeSimilarityList = utilityFunctions.sortArrayListByEdgeCount(allNodeSimilarityList); // Sort the array list of facebooknodes by their Edge list count  
		utilityFunctions.writeFaceBookEgosToFile(allNodeSimilarityList);
		
		ArrayList<Circle> allCircles = new ArrayList<Circle>();
		allCircles = cluster.clusterData(allNodeSimilarityList);
		utilityFunctions.writeCirclesToFile(allCircles);
	
		final long endTime = System.currentTimeMillis(); // Declaring a long variable to note the Start time
		System.out.println("Total execution time - " + (endTime - startTime) + " Milliseconds"); // Printing the total execution time
	}	
	
}
