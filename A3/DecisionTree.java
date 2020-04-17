import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;
//Zhiyun (David) Deng, 260838166
public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;      // only defined if node is a leaf
		int attribute; // only defined if node is not a leaf
		double threshold;  // only defined if node is not a leaf



		DTNode left, right; //the left and right child of a particular node. (null if leaf)

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}



		// this method takes in a datalist (ArrayList of type datum) and a minSizeInClassification (int) and returns
		// the calling DTNode object as the root of a decision tree trained using the datapoints present in the
		// datalist variable
		// Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
		DTNode fillDTNode(ArrayList<Datum> datalist) {

			//YOUR CODE HERE
			
			if(datalist.size()<minSizeDatalist) {
				leaf = true;
				label = findMajority(datalist);
				return this;
			}
			boolean same = true;
			Datum last = datalist.get(0);
			for(Datum data:datalist) {
				if(data.y!=last.y) {
					same = false;
				}
			}
			if(same) {
				leaf = true;
				label = last.y;
				return this;
			}
			leaf = false;
			
			//find best split
			double bestAvgEntropy = Double.MAX_VALUE;
			int bestAttribute = -1;
			double bestThreshold = -1;
			
			for (int i = 0; i<datalist.get(0).x.length; i++) {
				//ArrayList<Integer> valueList = new ArrayList<Integer>();
				//currently, inefficient; consider sorting list before looping
				for(Datum data:datalist) {
					ArrayList<Datum> list1 = new ArrayList<Datum>();
					ArrayList<Datum> list2 = new ArrayList<Datum>();
					for(Datum innerdata:datalist) {
						if(innerdata.x[i]<data.x[i]) {
							list1.add(innerdata);
						}
						else {
							list2.add(innerdata);
						}
					}
					//if(list1.isEmpty()||list2.isEmpty())continue;
					double w1 = (double)list1.size()/(list1.size()+list2.size());
					double w2 = (double)list2.size()/(list1.size()+list2.size());
					double currentAvgEntropy = w1*calcEntropy(list1)+w2*calcEntropy(list2);
					if(currentAvgEntropy<bestAvgEntropy) {
						bestAttribute = i;
						bestThreshold = data.x[i];
						bestAvgEntropy = currentAvgEntropy;
					}
				}
			}
			attribute = bestAttribute;
			threshold = bestThreshold;
			
			//split the list, create subtrees and fill them recursively
			ArrayList<Datum> list1 = new ArrayList<Datum>();
			ArrayList<Datum> list2 = new ArrayList<Datum>();
			for(Datum data : datalist) {
				if(data.x[attribute]<threshold) {
					list1.add(data);
				}
				else {
					list2.add(data);
				}
			}
			/*if(list1.isEmpty()||list2.isEmpty()) {
				leaf = true;
				label = findMajority(datalist);
				attribute = 0;
				threshold = Double.MAX_VALUE;
				return this;
			}*/

			left = (new DTNode()).fillDTNode(list1);
			right = (new DTNode()).fillDTNode(list2);
				
			return this;
		}



		//This is a helper method. Given a datalist, this method returns the label that has the most
		// occurences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist)
		{
			int l = datalist.get(0).x.length;
			int [] votes = new int[l];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			int max = -1;
			int max_index = -1;
			//find the label with the max occurrences
			for (int i = 0 ; i < l ;i++)
			{
				if (max<votes[i])
				{
					max = votes[i];
					max_index = i;
				}
			}
			return max_index;
		}




		// This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
		// returns its corresponding label, as determined by the decision tree
		int classifyAtNode(double[] xQuery) {
			//YOUR CODE HERE
			if(leaf) {
				return label;
			}
			else {
				if(xQuery[attribute]<threshold) {
					return left.classifyAtNode(xQuery);
				}
				else {
					return right.classifyAtNode(xQuery);
				}
			}
			
		}


		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
		public boolean equals(Object dt2)
		{
			
			//YOUR CODE HERE
			//false if object passed not a node
			DTNode toBeCompared;
			if(dt2 instanceof DTNode) {
				toBeCompared = (DTNode)dt2;
			}
			else {
				return false;
			}
			//whether the branches match; if not, return false;
			if(left == null&& toBeCompared.left != null) return false;
			if(right == null&& toBeCompared.right != null) return false;
			if(left != null&& toBeCompared.left == null) return false;
			if(right != null&& toBeCompared.right == null) return false;
			//we now know either both are external nodes or both are internal nodes
			
			//base case (both are leaves): leaf encountered(left and right are both null). We already know their branches match
			if(leaf) {
				return (label==toBeCompared.label);
			}
			//non-base case(both are internal nodes): check whether they are equal themselves and return true only if their
			//subtrees are equal
			else {
				if(threshold != toBeCompared.threshold) return false;
				if(attribute!=toBeCompared.attribute) return false;
				return left.equals(toBeCompared.left) && right.equals(toBeCompared.right);
			}

			
			
		}
	}



	//Given a dataset, this retuns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist)
	{
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		DTNode node = this.rootDTNode;
		return node.classifyAtNode( xQuery );
	}

    // Checks the performance of a DecisionTree on a dataset
    //  This method is provided in case you would like to compare your
    //results with the reference values provided in the PDF in the Data
    //section of the PDF

    String checkPerformance( ArrayList<Datum> datalist)
	{
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}

    
    
    
    
    
    
	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}
