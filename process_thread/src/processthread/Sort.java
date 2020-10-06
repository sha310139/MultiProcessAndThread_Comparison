package processthread;
import java.util.*;

public class Sort {
	
	static void bubbleSort( List<Integer> sortFile, int first, int last ) {
		int temp = 0;
		
		for( int i = 0 ; i < last-first ; i++ ) {
			for ( int j = first ; j < last - i - 1 ; j++ ) {
				if ( sortFile.get(j) > sortFile.get(j+1) ) {
					temp = sortFile.get(j);
					sortFile.set(j, sortFile.get(j+1)) ;
					sortFile.set(j+1, temp) ;
				} // if
			} // for
		} // for
	} // bubbleSort()
	
	static void merge( List<Integer> sortFile, int first, int mid, int last ) {
		List<Integer> vLeft = new ArrayList<Integer>();
		List<Integer> vRight = new ArrayList<Integer>();
		
		for (int i = first ; i < mid ; i++) {
			vLeft.add(sortFile.get(i));
		} // for
		
		for (int i = mid ; i < last ; i++) {
			vRight.add(sortFile.get(i));
		} // for
		
		int indexLeft = 0, indexRight = 0 ;
		
		
		for ( int position = first ; 
			  ( indexLeft < vLeft.size() ) || ( indexRight < vRight.size() ) ;
			  position++ ) {
			// 比完之後只會有一組剩下, 且已按大小排序
			// 將剩下的元素排在後面 
			
			if ( indexLeft == vLeft.size() ) {
				sortFile.set(position, vRight.get(indexRight));
				indexRight++ ;
			} // if
			else if ( indexRight == vRight.size() ) {
				sortFile.set(position, vLeft.get(indexLeft));
				indexLeft++ ;
			} // else if
			else if ( vLeft.get(indexLeft) <= vRight.get(indexRight) ) {
				sortFile.set(position, vLeft.get(indexLeft));
				indexLeft++ ;
			} // else if
			else if ( vLeft.get(indexLeft) > vRight.get(indexRight) ) {
				sortFile.set(position, vRight.get(indexRight));
				indexRight++ ;
			} // else if

		} // for	

		
	} // merge()
	
	
} // class Sort
