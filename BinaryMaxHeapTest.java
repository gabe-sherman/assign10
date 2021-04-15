package assign10;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class conducts a series of tests on the FindKLargest class and the
 * BinaryMaxHeap class.
 * 
 * @author Gabe Sherman & Teigen Edmundson
 * @version 4/13/2021
 */
class BinaryMaxHeapTest {

	BinaryMaxHeap<String> simpleHeap;
	BinaryMaxHeap<Integer> listHeap;
	BinaryMaxHeap<Integer> compHeap;
	List<Integer> KList;

	@BeforeEach
	void setUp() throws Exception {
		simpleHeap = new BinaryMaxHeap<String>();
		KList = new ArrayList<Integer>();
		for (int i = 999; i >= 0; i--) {
			KList.add(i);
		}

		List<Integer> addList = new ArrayList<Integer>();

		for (int i = 499; i >= 0; i--) {
			addList.add(i);
		}

		listHeap = new BinaryMaxHeap<Integer>(addList);

		compHeap = new BinaryMaxHeap<Integer>((i1, i2) -> (i2 - i1));
	}

	@Test
	void testToArray() {
		Integer[] addList = new Integer[500];

		for (int i = 499; i >= 0; i--) {
			addList[499 - i] = i;
		}

		assertEquals(Arrays.toString(addList), Arrays.toString(listHeap.toArray()));

	}

	@Test
	void testSimpleAdd() {
		simpleHeap.add("hello");
		assertEquals("[hello]", Arrays.toString(simpleHeap.toArray()));
	}

	@Test
	void testListAdd() {
		listHeap.add(600);
		assertEquals(listHeap.extractMax(), 600);
	}

	@Test
	void testCompAdd() {
		compHeap.add(-5);
		assertEquals(compHeap.extractMax(), -5);
	}

	@Test
	void testSimpleExtractMax() {
		simpleHeap.add("hello");
		simpleHeap.add("world");
		simpleHeap.add("zebra");
		simpleHeap.add("dog");
		simpleHeap.add("cat");
		assertEquals(simpleHeap.extractMax(), "zebra");
	}

	@Test
	void testListHeapExtractMax() {
		assertEquals(listHeap.extractMax(), 499);
	}

	@Test
	void testCompPeek() {
		compHeap.add(-5);
		compHeap.add(100);
		assertEquals(compHeap.peek(), -5);
	}

	@Test
	void testSimplePeek() {
		simpleHeap.add("hello");
		simpleHeap.add("world");
		simpleHeap.add("zebra");
		simpleHeap.add("dog");
		simpleHeap.add("cat");
		assertEquals(simpleHeap.peek(), "zebra");
	}

	@Test
	void testListHeapPeek() {
		assertEquals(listHeap.peek(), 499);
	}

	@Test
	void testCompHeapExtractMax() {
		for (int i = 0; i < 500; i++) {
			compHeap.add(i);
		}
		assertEquals(compHeap.extractMax(), 0);
	}

	@Test
	void testSize() {
		assertEquals(listHeap.size(), 500);
	}

	@Test
	void testClear() {
		listHeap.clear();
		assertEquals(listHeap.size(), 0);
	}

	@Test
	void testClearThenAdd() {
		listHeap.clear();
		for (int i = 0; i < 500; i++) {
			listHeap.add(i * -1);
		}
		assertEquals(listHeap.extractMax(), 0);
	}

	@Test
	void testIsEmptyFalse() {
		assertFalse(listHeap.isEmpty());
	}

	@Test
	void testIsEmptyTrue() {
		listHeap.clear();
		assertTrue(listHeap.isEmpty());
	}

	// FindKLargest tests

	@Test
	void testKLargestHeapNoComp() {
		assertEquals(FindKLargest.findKLargestHeap(KList, 500).toString(), KList.subList(0, 500).toString());
	}

	@Test
	void testKLargestHeapComp() {
		ArrayList<Integer> backwardsKList = new ArrayList<Integer>();
		for (int i = 0; i < 500; i++) {
			backwardsKList.add(i);
		}
		assertEquals(FindKLargest.findKLargestHeap(KList, 500, (i1, i2) -> (i2 - i1)).toString(),
				backwardsKList.toString());
	}

	@Test
	void testKLargestSortNoComp() {
		ArrayList<Integer> forwardKList = new ArrayList<Integer>();
		for (int i = 999; i >= 499; i--) {
			forwardKList.add(i);
		}
		assertEquals(FindKLargest.findKLargestSort(KList, 500).toString(), forwardKList.subList(0, 500).toString());
	}

	@Test
	void testKLargestSortComp() {
		ArrayList<Integer> backwardsKList = new ArrayList<Integer>();
		for (int i = 0; i < 500; i++) {
			backwardsKList.add(i);
		}
		assertEquals(FindKLargest.findKLargestSort(KList, 500, (i1, i2) -> (i2 - i1)).toString(),
				backwardsKList.toString());
	}
}
