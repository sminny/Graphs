package Tutorial3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class TreePlotter {
	private BinarySearchTree<Integer> bst ;
	public TreePlotter(BinarySearchTree<Integer> bst){
		this.bst= bst;
	}
	public void plot() throws IOException{
		Random ran = new Random();
		BufferedWriter bw= new BufferedWriter(new FileWriter(new File("averageDepth.txt")));
		for(int i=1000;i<100000;i=i+1000){
			for(int j=0;j<1000;j++){
				bst.add(ran.nextInt(1000000));
				
			}
			System.out.println("writing iteration "+i);
			bw.write(AverageTreeDepth.recursiveTreeDepth(bst.getRoot(), i)+"\n");
		}
		bw.flush();
		bw.close();
	}
}
