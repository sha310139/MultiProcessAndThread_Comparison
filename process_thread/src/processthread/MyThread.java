package processthread;

import java.util.List;

public class MyThread extends Thread {
	private String functionName;
	private List<Integer> sortFile;
	private int first, mid, last;
	
	public MyThread( String functionName, List<Integer> sortFile, int first, int last ) {
		this.functionName = functionName;
		this.sortFile = sortFile;
		this.first = first;
		this.last = last;
	} // MyThread()
	
	public MyThread( String functionName, List<Integer> sortFile, int first, int mid, int last ) {
		this.functionName = functionName;
		this.sortFile = sortFile;
		this.first = first;
		this.mid = mid;
		this.last = last;
	} // MyThread()
	
	public void run() {
		if ( functionName.equals("bubbleSort") ) {
			Sort.bubbleSort(sortFile, first, last);
		} // if
		else if ( functionName.equals("merge") ) {
			Sort.merge( sortFile, first, mid, last );
		} // else if
		else {
			System.out.print("Error !\n");
		} // else
	} // run()
	
} // class MyThread


