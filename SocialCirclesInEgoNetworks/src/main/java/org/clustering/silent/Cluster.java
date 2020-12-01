package org.clustering.silent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;


public class Cluster {
	UtilityFunctions utilityFunctions = new UtilityFunctions();
	
	public ArrayList<Circle> clusterData(ArrayList<FacebookNode> allNodeSimilarityList) {

		int noOfRows = allNodeSimilarityList.size();
		ArrayList<Circle> allCircles = new ArrayList<Circle>();
		for (int s = 0; s < noOfRows; s++) {
			/*
			 * Creating a new circle and giving its number and also adding the
			 * (sth)node to the list
			 */
			boolean flag = false;
			FacebookNode newNode = allNodeSimilarityList.get(s);
			if (newNode.isSelcted)
				continue;
			for (Entry<String, Double> entry : newNode.weightMap.entrySet()) {
				if(flag) break;
				FacebookNode tmpNode = utilityFunctions.getFBNode(allNodeSimilarityList, entry.getKey());
				if(tmpNode.isSelcted)
					continue;
				for (Entry<String, Double> entry2 : tmpNode.weightMap.entrySet()){
					FacebookNode tmpNode2 = utilityFunctions.getFBNode(allNodeSimilarityList, entry2.getKey());
					if(tmpNode2.isSelcted)
						continue;
					if(entry2.getKey().equals(newNode.nodeNumber) && tmpNode.edgeList.contains(entry2.getKey())){
						Circle circle = new Circle();
						circle.circleNumber = "Circle" + (s + 1);
						circle.list.add(entry2.getKey());
						circle.list.add(entry.getKey());
						circle = utilityFunctions.calculateWeights(allNodeSimilarityList,circle);
						allCircles.add(circle); // Adding the circle to the array list of
						newNode.isSelcted=true;
						utilityFunctions.fetchFBNode(allNodeSimilarityList, entry.getKey()).isSelcted=true;
					}
					flag=true;
					break;
				}
			}
			
			
//			for (int i = s + 1; i < noOfRows; i++) {
//				FacebookNode currentNode = allNodeSimilarityList.get(i);
//				if (currentNode.isSelcted)
//					continue;
//				/*
//				 * Code to check similarity between current Node and nodes in
//				 * Circle - Start
//				 */
//				boolean flag = true;
//				for (int k = 0; k < circle.list.size(); k++) {
//					FacebookNode compareNode = utilityFunctions.getFBNode(allNodeSimilarityList, circle.list.get(k));
//					if (!compareNode.weightMap.containsKey(currentNode.nodeNumber)
//							|| compareNode.weightMap.get(currentNode.nodeNumber) < 0.2) {
//						flag = false;
//						break;
//					}
//
//				}
//				if (flag) {
//					circle.list.add(currentNode.nodeNumber);
//					currentNode.isSelcted = true;
//					continue;
//				}
//				/*
//				 * Code to check similarity between current Node and nodes in
//				 * Circle - Finish
//				 */
//			} // End of connection strength if loop

		} // End of connection value if loop
		for (int s = 0; s < noOfRows; s++) {
			FacebookNode newNode = allNodeSimilarityList.get(s);
			if (newNode.isSelcted)
				continue;
			Circle circle = new Circle();
			circle.circleNumber = "Circle" + (s + 1);
			circle.list.add(newNode.nodeNumber);
			for (Entry<String, Double> entry : newNode.weightMap.entrySet()) {
				circle.weights.put(entry.getKey(), entry.getValue());
			}
			newNode.isSelcted = true;
			allCircles.add(circle);
		}
		int fla_tmp = 0;
		while(true){
			for (int i = 0; i < allCircles.size(); i++) {
				Circle circle1 = allCircles.get(i);
				if(circle1.isDelete) continue;
				for (int j = i+1; j < allCircles.size(); j++) {
					Circle circle2 = allCircles.get(j);
					if(circle2.isDelete) continue;
					if(calculateConnectionStrength(circle1,circle2,allNodeSimilarityList)>=0.0 && utilityFunctions.isCombine(circle1,circle2)){
						circle2.isDelete= true;
						circle1.list.addAll(circle2.list);
						circle1 = utilityFunctions.calculateWeights(allNodeSimilarityList, circle1);
					}
				}
			}
			ArrayList<Circle> tmpList = new ArrayList<Circle>();
			for (Circle circle : allCircles) {
				if(circle.isDelete)
					tmpList.add(circle);
			}
			fla_tmp++;
			if(tmpList.isEmpty())
				break;
			allCircles.removeAll(tmpList);
			allCircles.sort(new Comparator<Circle>() {
				public int compare(Circle c1, Circle c2) {
					return c2.list.size()-c1.list.size();
				}
			});
		}
		
//		for (int s = 0; s < noOfRows; s++) {
//			FacebookNode newNode = allNodeSimilarityList.get(s);
//			if (newNode.isSelcted)
//				continue;
//			Circle circle = new Circle();
//			circle.circleNumber = "Circle" + (s + 1);
//			circle.list.add(newNode.nodeNumber);
//			allCircles.add(circle);
//		}
		System.out.println(fla_tmp);
		return allCircles;
	}
	
	private double calculateConnectionStrength(Circle circle1, Circle circle2,ArrayList<FacebookNode> allNodeSimilarityList) {
		double connectionStrength = 0;
		int m = circle1.list.size();
		int n = circle2.list.size();
		int k = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				FacebookNode fbNode = utilityFunctions.getFBNode(allNodeSimilarityList, circle1.list.get(i));
				if (fbNode.edgeList.contains(circle2.list.get(j)))
					k++;
			}
		}
		connectionStrength = (double) k/(m+n) ;
		return connectionStrength;
	}
	
}
