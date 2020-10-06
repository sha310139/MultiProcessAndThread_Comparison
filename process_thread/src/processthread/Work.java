package processthread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Work {
	
	static void input( Scanner scanner, List<Integer> sortFile ) throws Throwable {
		
		while (scanner.hasNext()) {
			sortFile.add(scanner.nextInt());
		} // while
		
	} // input()
	
	static int getK( Scanner scanner ) throws Throwable {
		
		System.out.print("Input k : ");
		int k = scanner.nextInt();
		while ( k <= 0 ) {
			System.out.println("k should not less than or equal to 0, please enter again.");
			System.out.println();
			System.out.print("Input k : ");
			k = scanner.nextInt();
		} // while
		return k;
		
	} // input()
	
	static void allocation( int k, int size, List<Integer> vIndex ) throws Throwable {
		
		int partNum = size / k ;
		
		for (int i = 0; i < k ; i++) {
			// 加入0, 跟中間的分割點
			vIndex.add(i*partNum);
		} // for
		
		vIndex.add(size); // 加入最後一個size-1的點
	} // allocation()
	
	static void generateFiles( List<Integer> sortFile, List<Integer> vIndex ) throws Throwable {
		
		String classpath = System.getProperty("java.class.path");
		
		String fileName = "0";
		int first = 0, last = 0;
		for (int i = 0 ; i < vIndex.size()-1 ; i++) {  // 跑k次
			// 因為第一組可以從0 ~ 4999
			// 但第二組開始要從4999+1 ~ 9999
			fileName = Integer.toString(i+1);
			FileWriter fw = new FileWriter(classpath + "\\" + fileName + ".txt");
			
			first = vIndex.get(i);
			last = vIndex.get(i+1);
			
			for ( int j = first ; j < last ; j++ ) {  // 把第i+1份寫入txt檔
				fw.write(Integer.toString(sortFile.get(j)));
				if ( j != last - 1 ) {
					fw.write(" ");
				} // if
			} // for
			
			fw.flush();
			fw.close();
		} // for
		
	} // generateFiles()
	
	
	static void deleteFile( String fileName ) throws Throwable {
		String classpath = System.getProperty("java.class.path");
		File file = new File(classpath + "\\" + fileName + ".txt");
		file.delete();
	} // deleteFile()
	
	static void writeFile( String fileName, List<Integer> sortFile ) throws Throwable {
		String classpath = System.getProperty("java.class.path");
		FileWriter fw = new FileWriter(classpath + "\\" + fileName + ".txt");
		
		for ( int i = 0 ; i < sortFile.size() ; i++ ) {  // 把資料寫入txt檔
			fw.write(Integer.toString(sortFile.get(i)));
			if ( i != sortFile.size()-1 ) {
				fw.write(" ");
			} // if
		} // for
			
		fw.flush();
		fw.close();
		
	} // writeFile()
	
	static void writeTime( String fileName, long time ) throws Throwable {
		String classpath = System.getProperty("java.class.path");
		FileWriter fw = new FileWriter(classpath + "\\" + fileName + ".txt", true);
		
		fw.write("\n");
		fw.write("執行時間 :  " + Long.toString(time) + " s");
			
		fw.flush();
		fw.close();
	} // writeTime()
	
	static void fileRename( String originalName, String newName ) throws Throwable {
		String classpath = System.getProperty("java.class.path");
		File file = new File(classpath + "\\" + originalName + ".txt");
		deleteFile( newName );
		file.renameTo(new File( newName + ".txt" )); //改名
	} // writeFile()
	
	
	
	
	static void cmd_1( List<Integer> sortFile, List<Integer> vIndex ) throws Throwable {
		System.out.println( "Do bubble sort ..." );
		Sort.bubbleSort( sortFile, 0, sortFile.size() );
		System.out.println( "Finish bubble sort !" );
	} // cmd_1()
	
	static void cmd_2( List<Integer> sortFile, List<Integer> vIndex ) throws Throwable {
		List<MyThread> th_bubble = new ArrayList<MyThread>();
		List<MyThread> th_merge = new ArrayList<MyThread>();
		
		System.out.println( "Do bubble sort ..." );
		for (int i = 0 ; i < vIndex.size()-1 ; i++) {  // 跑k次
			th_bubble.add(new MyThread("bubbleSort", sortFile, vIndex.get(i), vIndex.get(i+1)));
		} // for

		try {
			for (int i = 0 ; i < vIndex.size()-1 ; i++) {  // 跑k次
				th_bubble.get(i).start();
				th_bubble.get(i).join();
			} // for
		} catch (InterruptedException e) {
		} // catch
		
		System.out.println( "Finish bubble sort !" );
		System.out.println( "Do merge ..." );
		
		for (int i = 0 ; i < vIndex.size()-2 ; i++) {  // 跑k-1次
			th_merge.add(new MyThread("merge", sortFile, 0, vIndex.get(i+1), vIndex.get(i+2)));
		} // for
		
		
		try {
			for (int i = 0 ; i < vIndex.size()-2 ; i++) {  // 跑k-1次
				th_merge.get(i).start();
				th_merge.get(i).join();
			} // for
		} catch (InterruptedException e) {
		} // catch
		
		System.out.println( "Finish merge !" );

	} // cmd_2()
	
	static void cmd_3( List<Integer> sortFile, List<Integer> vIndex, String fileName ) throws Throwable {
		
		String cmdNum = "", funcName = "", fileNum = "";
		String classpath = System.getProperty("java.class.path");
		String command = "java " +
				   "-classpath " +
				   classpath +
				   " processthread.Command" ;

		String s;
		cmdNum = "3";
		funcName = "bubbleSort";
		
		System.out.println( "Do bubble sort ..." );
		
		for ( int i = 0 ; i < vIndex.size()-1 ; i++ ) {
			
			fileNum = Integer.toString(i+1);

			try {
				// 接在後面三個參數 : 指令Num, 要讀的檔案名稱,  要執行的function名
	            Process process= Runtime.getRuntime().exec(command + " " + cmdNum + " " + fileNum + " " + funcName);
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            while((s=bufferedReader.readLine()) != null)
	            	System.out.println(s);
	            bufferedReader.close();
	        } catch (Exception e) {
	        	System.out.print("*");
	        }
		} // for
		
		System.out.println( "Finish bubble sort !" );
		
		funcName = "merge";
		
		System.out.println( "Do merge ..." );
		
		for ( int i = 0 ; i < vIndex.size()-2 ; i++ ) {
			
			fileNum = Integer.toString(i+2);

			try {
				// 接在後面三個參數 : 指令Num, 要讀的檔案名稱,  要執行的function名
	            Process process= Runtime.getRuntime().exec(command + " " + cmdNum + " " + fileNum + " " + funcName);
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            while((s=bufferedReader.readLine()) != null)
	            	System.out.println(s);
	            bufferedReader.close();
	        } catch (Exception e) {
	        	System.out.print("*");
	        }
		} // for
		
		System.out.println( "Finish merge !" );
		//fileRename( "1", fileName + "_output" );
	} // cmd_3()
	
	static void cmd_4( int k, String fileName ) throws Throwable {
		
		String cmdNum = "", fileNum = "";
		String classpath = System.getProperty("java.class.path");
		String command = "java " +
				   "-classpath " +
				   classpath +
				   " processthread.Command" ;

		String s;
		cmdNum = "4";
		fileNum = fileName;
		
		try {
			// 接在後面三個參數 : 指令Num, 要讀的檔案名稱,  切分的數量
			Process process= Runtime.getRuntime().exec(command + " " + cmdNum + " " + fileNum + " " + k);
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        while((s=bufferedReader.readLine()) != null)
	        	System.out.println(s);
	        bufferedReader.close();
		} catch (Exception e) {
			System.out.print("*");
		}
		
		//fileRename( "1", fileName + "_output" );
	} // cmd_4()
	
	
} // class Work
