package org.clustering.nkher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/*  Class: FacebookNode
 * 
 *  Description: This class is used to store a Node in an Ego Network and its related information.
 *  
 *  Data Members:
 *  1. nodeNumber (DataType:String) --> Stores the node number of the FacebookNode
 *  2. similarityMap (DataType:HashMap) --> Stores the similarity value of the Node with all other Nodes in the Ego Network based on Jaccard Coeff
 *  3. topTenMap (DataType:LinkedHashMap) --> Stores the Top Ten Nodes for the Node Number based on the similarity value
 *  4. edgeList (DataType:ArrayList) --> Store the edges inside the Ego Network for a Node
 *  5. egoList (DataType:ArrayList) --> Store the nodes in its own Ego Network
 */

public class FacebookNode {
	String nodeNumber;
	HashMap<String, Double> similarityMap = new HashMap<String, Double>();
	HashMap<String, Double> commonMap = new HashMap<String , Double>();
	LinkedHashMap<String, Double> weightMap=new LinkedHashMap<String, Double>();
	ArrayList<String> edgeList = new ArrayList<String>();
	ArrayList<String> egoList = new ArrayList<String>();
	Boolean isSelcted = false;
}

	