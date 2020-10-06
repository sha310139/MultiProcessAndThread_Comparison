package processthread;
import java.util.*;
import java.io.File;
import java.io.FileReader;



class ProcessWithThread {

	public static void main( String[] args ) throws Throwable {

		String classpath = System.getProperty("java.class.path");
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Default path for read input file : ");
		System.out.println(classpath + "\\");
		System.out.print("Please input file name (quit : \"0\") : ");
		String fileName = "";
		fileName = scanner.next();
		
		int cmd = 0, k = 0;
		FileReader fr = null;
		File file = null;
		Scanner fileScan = null;
		
		List<Integer> sortFile = new ArrayList<Integer>();
		List<Integer> vIndex = new ArrayList<Integer>(); 
		

		while ( !fileName.equals("0") ) {
			
			file = new File(classpath + "\\" + fileName + ".txt");
			
			if ( file.exists() ) {
				fr = new FileReader(classpath + "\\" + fileName + ".txt");
				fileScan = new Scanner(fr);
				
				cmd = fileScan.nextInt();
				System.out.println();
				System.out.println( "The command you choose is : " + cmd );
				System.out.println();
				
				long startT = 0, endT = 0;
				
				if (cmd == 1) {
					Work.input( fileScan, sortFile );
					startT = System.currentTimeMillis();
					Work.cmd_1( sortFile, vIndex );
					Work.writeFile( fileName+"_output", sortFile);
					endT = System.currentTimeMillis();
					Work.writeTime( fileName+"_output", (endT-startT)/1000 );
				} // if
				else if (cmd == 2) {
					Work.input( fileScan, sortFile );
					k = Work.getK( scanner );
					Work.allocation( k, sortFile.size(), vIndex );
					startT = System.currentTimeMillis();
					Work.cmd_2( sortFile, vIndex );
					Work.writeFile( fileName+"_output", sortFile);
					endT = System.currentTimeMillis();
					Work.writeTime( fileName+"_output", (endT-startT)/1000 );
				} // else if
				else if (cmd == 3) {
					Work.input( fileScan, sortFile );
					k = Work.getK( scanner );
					Work.allocation( k, sortFile.size(), vIndex );
					Work.generateFiles( sortFile, vIndex );
					startT = System.currentTimeMillis();
					Work.cmd_3( sortFile, vIndex, fileName );
					endT = System.currentTimeMillis();

					List<Integer> result = new ArrayList<Integer>();
					File file_1 = new File(classpath + "\\" + "1.txt");
					Scanner fr_1 = new Scanner(file_1);
					Work.input( fr_1, result );
					Work.writeFile( fileName+"_output", result);
					Work.writeTime( fileName+"_output", (endT-startT)/1000 );
				} // else if
				else if (cmd == 4) {
					k = Work.getK( scanner );
					startT = System.currentTimeMillis();
					Work.cmd_4( k, fileName );
					endT = System.currentTimeMillis();
					
					List<Integer> result = new ArrayList<Integer>();
					File file_1 = new File(classpath + "\\" + "1.txt");
					Scanner fr_1 = new Scanner(file_1);
					Work.input( fr_1, result );
					Work.deleteFile( "1" );
					Work.writeFile( fileName+"_output", result);
					Work.writeTime( fileName+"_output", (endT-startT)/1000 );
				} // else if
				else {
					System.out.println("Error !");
				} // else
				
				
				
				System.out.println("執行時間 : " + (endT-startT)/1000 + " s");
				fr.close();
				fileScan.close();
				sortFile.clear();
				vIndex.clear();
				System.out.println();
			} // if
			else {
				System.out.println();
				System.out.println("File is not exist, please enter again !");
				System.out.println();
			} // else
			
			System.out.print("Please input file name (quit : \"0\") : ");
			fileName = scanner.next();
		} // while
		
		
		scanner.close();
		
		System.out.println("Bye bye !");
	} // main()
	
	

} // class ProcessWithThread
