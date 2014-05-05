package Tutorial3;

import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

import junit.framework.TestCase;

public class BinarySearchTreeTest extends TestCase {
	private BinarySearchTree<Integer> bst;
	private Random rand = new Random();
	protected void setUp() throws Exception {
		super.setUp();
		bst = new BinarySearchTree<Integer>();
		
		for(int i=0;i<50;i++){
			bst.add(i);
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testAdd(){
		
		assertEquals("Adding was not successful", 50,bst.size());
		for(int i=0;i<10;i++){
			bst.add(i);
		}
		assertEquals("The BinarySearchTree does not provide uniqueness",50,bst.size());
	}
	@Test
	public void testRemove(){
		bst.remove(0);
		assertEquals("Problem with removing",49,bst.size());
		bst.remove(100);
		assertEquals("Size changed when removing a number not in the Tree",49,bst.size());
	}
	@Test
	public void testContains(){
		for(int i=0;i<10;i++){
			assertTrue("Tree should have contained "+i*5,bst.contains(i*5));
		}
	}
	@Test
	public void testIterator(){
		Iterator it = bst.iterator();
		assertNotNull("Iterator returned is null", bst.iterator());
		int i =0;
		while(it.hasNext()){
			assertEquals("Iterator returns wrong number",it.next(),i++);
		}
	}

}
