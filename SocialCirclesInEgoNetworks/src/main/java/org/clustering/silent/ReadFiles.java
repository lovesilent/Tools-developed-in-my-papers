package org.clustering.silent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFiles {
	public int[][] getAdjacencyNodeFeatureMatrix(int egoNetworkNodeNumber)
			throws IOException {
		try {
			/* Code to read the file */
			File file = new File("Twitter_Data/" + egoNetworkNodeNumber + ".feat");
			FileReader fr = new FileReader(file);
			LineNumberReader lnr = new LineNumberReader(fr);
			// To get the number of Attributes of node
			String line = lnr.readLine();
			int attCount = line.split(" ").length;

			/* Code to count number of rows/nodes in the network */
			int rowCount = 1;
			while (lnr.readLine() != null)
				rowCount++;
			lnr.close();

			// Initializing the array that will store the nodes adjacency
			int lineCount = 0;
			int featMatrix[][] = new int[rowCount][attCount];
			Scanner scan = new Scanner(new FileReader(file));
			while (scan.hasNextLine()) {
				// Creating the Adjacency matrix
				line = scan.nextLine();
				String[] numbers = line.split(" ");
//				if(nodes_map.containsKey(numbers[0])){
//					throw new IOException("file error");
//				}
//				nodes_map.put(numbers[0], lineCount);
//				featMatrix[lineCount][0] = lineCount;
				for (int i = 0; i < numbers.length; i++)
					featMatrix[lineCount][i] = Integer.parseInt(numbers[i]);
				lineCount++;
			}
			scan.close();
			lnr.close();
			fr.close();

			return featMatrix;
		} catch (IOException e) {
			System.out.println("There is no such file ! Please give the correct file name.");
			System.exit(0);
		}
		return null;
	}

	public ArrayList<FacebookNode> getEdges(ArrayList<FacebookNode> allNodeSimilarityList, int egoNetworkNodeNumber)
			throws FileNotFoundException {

		UtilityFunctions utilityFunctions = new UtilityFunctions();
		File file = new File("Twitter_Data/" + egoNetworkNodeNumber + ".edges");
		String line = "";
		FacebookNode fbNode = null;
		Scanner scan = new Scanner(new FileReader(file));

		// Read the file line by line and store the connections in the edgeList
		// of a FacebookNode
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			String[] numbers = line.split(" ");
			fbNode = utilityFunctions.getFBNode(allNodeSimilarityList, ("N" + numbers[0]));
			fbNode.edgeList.add("N" + numbers[1]);
		}
		scan.close();
		return allNodeSimilarityList;
	}

	public ArrayList<FacebookNode> getEgos(ArrayList<FacebookNode> allNodeSimilarityList, int egoNetworkNodeNumber)
			throws FileNotFoundException {
		UtilityFunctions utilityFunctions = new UtilityFunctions();
		File file = new File("Twitter_Data/" + egoNetworkNodeNumber + ".egos");
		String line = "";
		FacebookNode fbNode = null;
		Scanner scan = new Scanner(new FileReader(file));
		// Read the file line by line and store the nodes in the ego of a
		// FacebookNode
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			String[] numbers = line.split(" ");
			fbNode = utilityFunctions.getFBNode(allNodeSimilarityList, ("N" + numbers[0]));
			for (String number : numbers) {
				if (number.equals(numbers[0]))
					continue;
				fbNode.egoList.add("N" + number);
			}
		}
		scan.close();
		return allNodeSimilarityList;
	}
}
