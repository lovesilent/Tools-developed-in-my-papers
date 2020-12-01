package com.wuyu.tool;

import java.util.Scanner;

public class Generator {

	public static void main(String[] args) {

		ReadFiles readFiles = new ReadFiles();
		Utility utility = new Utility();
		
		Scanner scanner  =  new Scanner(System.in);
		
		// Taking input from the user
		System.out.println("Please enter any one Ego node network number from the following list and say enter");
		System.out.println("Your choice is ");
		System.out.println("1. 0\n2. 107\n3. 1684\n4. 1912\n5. 3437\n6. 348\n7. 3980\n8. 414\n9. 686\n10. 698");
		int egoNetworkNodeNumber = scanner.nextInt();
		int[][] matrix = readFiles.getAdjacencyMatrix();
		utility.writeAdjacencyEgos(egoNetworkNodeNumber, matrix);
		
		//utility.writeGraph();
	}
}
