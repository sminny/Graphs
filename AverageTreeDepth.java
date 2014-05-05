package Tutorial3;

import Tutorial3.BinarySearchTree.Node;

public class AverageTreeDepth {
	public static double recursiveTreeDepth(Node root, int size) {
		return averageTreeDepth(root, 0) / size;
	}

	
	public static double averageTreeDepth(Node root, double level) {
		double averageDepth=0;
		int n = 0;
		if (root.getLeft() != null) {
			averageDepth += averageTreeDepth(root.getLeft(), level + 1);
			n++;
		}
		if (root.getRight() != null) {
			averageDepth += averageTreeDepth(root.getRight(), level + 1);
			n++;
			
		}
		//System.out.println(root + " depth:" + averageDepth + " level:" + level);
		if(n == 0)
			return level;
		else
			return averageDepth + level;
	}
	
}

