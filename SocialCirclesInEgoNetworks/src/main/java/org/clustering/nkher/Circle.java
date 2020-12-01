package org.clustering.nkher;
import java.util.ArrayList;
import java.util.LinkedHashMap;


/*  Class: Circle
 * 
 *  Description:  This class is used to represent a Circle in an Ego Network and its related information
 *                like the circle number and the nodes that are inside it.
 *  
 *  Data Members:
 *  1. cirleNumber (DataType:String) --> Stores the node number of the FacebookNode
 *  2. list (DataType:ArrayList)--> Store the node numbers which are in the circle
 *  3. attribute (DataType:int []) -->Store the attribute of circle 
 */

public class Circle {
	String circleNumber;
	ArrayList<String> list = new ArrayList<String>();
	LinkedHashMap<String, Double> weights=new LinkedHashMap<String, Double>();
	Boolean isDelete = false;
//	int[] attribute;
}
