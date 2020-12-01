package org.clustering.silent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FacebookNode {
	String nodeNumber;
	HashMap<String, Double> similarityMap = new HashMap<String, Double>();
	HashMap<String, Double> commonMap = new HashMap<String , Double>();
	LinkedHashMap<String, Double> weightMap=new LinkedHashMap<String, Double>();
	ArrayList<String> edgeList = new ArrayList<String>();
	ArrayList<String> egoList = new ArrayList<String>();
	Boolean isSelcted = false;
}
