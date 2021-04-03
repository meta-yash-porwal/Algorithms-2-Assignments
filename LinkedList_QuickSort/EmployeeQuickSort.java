package LinkedList_QuickSort;

/**
 * this class provide method to sort linked list using quick sort
 * @author yash.porwal_metacube
 *
 */
public class EmployeeQuickSort {
	
	/**
	 * this class(node of linked list) contains salary and age of employee
	 * and at 2nd part next address of the other employee
	 * @author yash.porwal_metacube
	 */
	static class Node {
		int sal;
		int age;
		Node next;

		/**
		 * constructor takes salary & age as an input
		 * and instantiate it into object created
		 * @param sal of the employee as an integer
		 * @param age of the employee as an integer
		 */
		Node(int sal, int age) {
			this.sal = sal;
			this.age = age;
			this.next = null;
		}
	}

	Node head;

	/**
	 * Method to add new Employee in LinkedList
	 * @param sal of the employee
	 * @param age of the employee
	 */
	void addNode(int sal, int age) {
		if (head == null) {
			head = new Node(sal, age);
			return;
		}

		Node curr = head;
		while (curr.next != null)
			curr = curr.next;
		Node newNode = new Node(sal, age);
		curr.next = newNode;
	}

	/**
	 * Method to print whole list
	 * @param link:head of linked list
	 */
	void printList(Node link) {
		if(link==null)
			throw new AssertionError("Empty List");
		System.out.println("Salary\t\tAge");
		while (link != null) {
			System.out.println(link.sal+"\t\t"+link.age);
			link = link.next;
		}
	}


	/**
	 * Method to partition list i.e., to place pivot at its right position
	 * takes first and last node of partition node,
	 * but do not break any links in the whole linked list
	 * @param start - starting node address
	 * @param end - ending node address (maybe of sub linked list)
	 * @return pivot_prev - index just previous to pivot position
	 */
	Node partionList(Node start, Node end) {
		if (start == end || start == null || end == null)
			return start;

		Node pivot_prev = start;
		Node curr = start;
		int pivot = end.sal;
		int ages = end.age;
		
		// iterate till one before the end,because end is pivot
		while (start != end) {
			if (start.sal < pivot) {
				// keep tracks of last modified item
				pivot_prev = curr;
				int temp = curr.sal;
				int temp1 = curr.age;
				curr.sal = start.sal;
				curr.age = start.age;
				start.sal = temp;
				start.age = temp1;
				curr = curr.next;
			} else if (start.sal == pivot) {
				if (start.age > ages) {
					int temp = curr.sal;
					int temp1 = curr.age;
					curr.sal = start.sal;
					curr.age = start.age;
					start.sal = temp;
					start.age = temp1;
					curr = curr.next;
				}
			}
			start = start.next;
		}

		// swap the position of curr i.e.next suitable index and pivot
		int temp = curr.sal;
		int temp1 = curr.age;
		curr.sal = pivot;
		curr.age = ages;
		end.sal = temp;
		end.age = temp1;

		// return one previous to current because current is now pointing to pivot
		return pivot_prev;
	}

	/**
	 * This method sorts the whole list by recursive calls in partitions
	 * this method sort the list and
	 * divide the list into two parts from pivot
	 * @param start address of the linked list
	 * @param end address of the linked list
	 */
	void sort(Node start, Node end) {
		if (start == null || start == end || start == end.next)
			return;

		// split list and recurse through partition
		Node pivot_prev = partionList(start, end);
		sort(start, pivot_prev);

		// if pivot is picked and moved to the start, that means start and pivot is same so pick from next of pivot
		if (pivot_prev != null && pivot_prev == start)
			sort(pivot_prev.next, end);

		// if pivot is in between of the list, start from next of pivot, since we have pivot_prev, so we move two nodes
		else if (pivot_prev != null && pivot_prev.next != null)
			sort(pivot_prev.next.next, end);
	}

	/**
	 * Method reverses the node to sort in ascending order
	 * @param node
	 * @return node
	 */
	Node reverse(Node node) {
		Node prev = null;
		Node current = node;
		Node next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		node = prev;
		return node;
	}

	//main method
	public static void main(String[] arg) {
		EmployeeQuickSort qs = new EmployeeQuickSort();
		qs.addNode(12000, 27);
		qs.addNode(22000, 25);
		qs.addNode(42000, 22);
		qs.addNode(28000, 20);
		qs.addNode(12000, 22);
		qs.addNode(15000, 20);
		qs.addNode(25000, 25);
		Node n = qs.head;
		while (n.next != null)
			n = n.next;
		System.out.println("Before sorting:");
		qs.printList(qs.head);
		qs.sort(qs.head, n);
		
		System.out.println("\nAfter sorting:");
		qs.head = qs.reverse(qs.head);
		qs.printList(qs.head);
	}
}