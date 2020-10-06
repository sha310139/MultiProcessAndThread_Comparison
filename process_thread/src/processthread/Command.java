package processthread;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Command {
	
	static void readData( String fileName, List<Integer> sortFile ) throws Throwable {
		String classpath = System.getProperty("java.class.path");
		FileReader fr = new FileReader(classpath + "\\" + fileName + ".txt");
		Scanner fileScan = new Scanner(fr);
		Work.input( fileScan, sortFile );
		fr.close();
		fileScan.close();
	} // readFile()
	
	static void do_one_bubble( String fileName ) throws Throwable {
		List<Integer> sortFile = new ArrayList<Integer>();
		readData( fileName, sortFile );
		
		Work.deleteFile( fileName );
		Sort.bubbleSort( sortFile, 0, sortFile.size() );
		Work.writeFile( fileName, sortFile );
	} // one_process_one_bubble()
	
	static void do_one_merge( String fileName ) throws Throwable {
		List<Integer> sortFile = new ArrayList<Integer>();
		List<Integer> sortFile1 = new ArrayList<Integer>();
		List<Integer> sortFile2 = new ArrayList<Integer>();
		
		// 讀入第一個檔案
		readData( "1", sortFile1 );
		
		// 讀入第二個檔案
		readData( fileName, sortFile2 );
		
		Work.deleteFile( "1" );
		Work.deleteFile( fileName );
		
		int mid = sortFile1.size();  // 兩個檔案的分界點
		
		sortFile.addAll( sortFile1 );
		sortFile.addAll( sortFile2 );
		
		Sort.merge( sortFile, 0, mid , sortFile.size() );
		Work.writeFile( "1", sortFile );  // 每次merge完的結果就放在1.txt裡
		
	} // do_one_merge()
	
	static void do_k_bubble_and_merge( int k, String fileName ) throws Throwable {
		List<Integer> sortFile = new ArrayList<Integer>();
		List<Integer> vIndex = new ArrayList<Integer>();
		
		String classpath = System.getProperty("java.class.path");
		FileReader fr = new FileReader(classpath + "\\" + fileName + ".txt");
		Scanner fileScan = new Scanner(fr);
		
		fileScan.nextInt();  // 讀掉cmd
		Work.input( fileScan, sortFile );
		Work.allocation( k, sortFile.size(), vIndex );
		
		System.out.println( "Do bubble sort ..." );
		
		for ( int i = 0 ; i < vIndex.size()-1 ; i++ ) {  // 跑k次
			// 因為第一組可以從0 ~ 4999
			// 但第二組開始要從4999+1 ~ 9999
			Sort.bubbleSort(sortFile, vIndex.get(i), vIndex.get(i+1));
		} // for
		
		System.out.println( "Finish bubble sort !" );
		System.out.println( "Do merge ..." );

		for ( int i = 0 ; i < vIndex.size()-2 ; i++ ) {  // 跑k-1次
			Sort.merge( sortFile, 0, vIndex.get(i+1), vIndex.get(i+2) );
		} // for
		
		System.out.println( "Finish merge !" );
		
		Work.writeFile( "1", sortFile );  // 結果放在1.txt裡
	} // do_k_bubble_and_merge()
	

	public static void main( String[] args ) throws Throwable {
		// 讀進三個參數 : 指令Num, 要讀的檔案名稱,  要執行的function名 / 切分的數量
		int cmd = Integer.parseInt(args[0]);
		String fileName = args[1];
		
		if ( cmd == 3 ) {
			// 第三個參數是要執行的function名
			String funcName = args[2];
			
			if ( funcName.equals("bubbleSort") ) {
				do_one_bubble( fileName );
			} // if
			else if ( funcName.equals("merge") ) {
				do_one_merge( fileName );
			} // else if
			else {
				System.out.println("Function Name Error !");
			} // else
			
		} // if
		else if ( cmd == 4 ) {
			// 第三個參數是切分的數量 = k
			
			int k = Integer.parseInt(args[2]);
			do_k_bubble_and_merge(k, fileName);
		} // else if
		else {
			System.out.println("Command Error !");
		} // else

	} // main()
	
} // class Command
