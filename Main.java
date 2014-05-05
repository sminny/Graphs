package Tutorial3;

import java.util.PriorityQueue;

import Tutorial3.BinarySearchTree.Node;
import Tutorial3.Graph.Edge;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		TreePlotter tp = new TreePlotter(bst);
//		try {
//			tp.plot();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		Node root = new Node(6,new Node(3,new Node(2,null,null),new Node(4,null,new Node(5,null,null))),new Node(8,null,new Node(9,null,null)));
		for(int i=0;i<5;i++){
			bst.add(i);
		}
		System.out.println(AverageTreeDepth.recursiveTreeDepth(root, bst.size()));
	}

}
