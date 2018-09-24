import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.relique.jdbc.csv.CsvDriver;


public class DirWorker {
	
    //å¾—åˆ°è·¯å¾„ä¸‹æ‰€æœ‰CSVæ–‡ä»¶
	public static List<File> getFiles( String path ,List<File> fileList) {
		File root = new File( path );
		if(fileList==null) {
			fileList=new ArrayList<File>();

		}
		if(root.isDirectory()) {
			
			File[] list = root.listFiles();
			if(list!=null) {
				
				for ( File f : list ) {
					if ( f.isDirectory() ) {
						getFiles( f.getAbsolutePath() ,fileList);
					}
					else if(f.isFile()){
						String[] cFiles = f.getName().split("\\.");
						if(cFiles.length >= 1 && cFiles[1].equals("csv")) {

							fileList.add(f);
						}
					}
				}
			}			
		}if(root.isFile()) {
			fileList.add(root);

		}
		
		return fileList;
	}
	
	
	//è¯»å�–æ–‡ä»¶ä¸­éœ€è¦�çš„å�‚æ•°
	public static  void readDataAndWriteFile(File file,PrintStream writer) {
		try {
			// Load the driver.
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			// Create a connection. The first command line parameter is
			// the directory containing the .csv files.
			// A single connection is thread-safe for use by several threads.
			Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + file.getParent());

			// Create a Statement object to execute the query with.
			// A Statement is not thread-safe.
			Statement stmt = conn.createStatement();

			// Select the ID and NAME columns from sample.csv
			//SELECT **,** FROM sampleFilæŒ‰ç…§æ­¤æ ¼å¼�å¡«å……å­—æ®µå�³å�¯å¾—åˆ°å¯¹åº”å­—æ®µçš„æ•°æ�®ç»“æžœ 
			ResultSet results = stmt.executeQuery("SELECT ID,RefID FROM sampleFile");

			// Dump out the results to a CSV file with the same format
			// using CsvJdbc helper function
			boolean append = true;
			CsvDriver.writeToCsv(results, writer, append);

			// Clean up
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//æ–‡ä»¶è§£æž�(å¯¹åº”å­—æ®µ) è¦�ç”¨æ­¤æ–¹æ³• è¯·è‡ªå·±æµ‹è¯•ï¼Œæ³¨æ„�æ˜¯å�¦å�«è¡¨å¤´
	public static List<ArrayList<String>> parseFile(File file) {
		Reader in;
		List<ArrayList<String>> content=new ArrayList<ArrayList<String>>();
		try {
			in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			for (CSVRecord record : records) {
				String[] title = {"First Name","Last Name","Street Number","Street","City","Province","Postal Code","Country","Phone Number","email Address"};
                 ArrayList<String> eachLine = new ArrayList<String>();
                 //å¦‚æžœå�«è¡¨å¤´è¯·ä¿®æ”¹æ­¤å¤„
				for(String c:title) {
                	String string = record.get(c);
                	eachLine.add(string);
                	
                }
				content.add(eachLine);
			}			
			
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return content;

	}
	//æ–‡ä»¶è§£æž�(å¯¹åº”ä¸‹æ ‡ï¼‰
	public static List<ArrayList<String>> parseFileIndex(File file) {
		Reader in;
		List<ArrayList<String>> content=new ArrayList<ArrayList<String>>();
		try {
			in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			String string = "";
			for (CSVRecord record : records) {
				
				String[] title = {"First Name","Last Name","Street Number","Street","City","Province","Postal Code","Country","Phone Number","email Address"};
				ArrayList<String> eachLine = new ArrayList<String>();
				//ä¸�å�–è¡¨å¤´
				for (int i = 0; i < title.length; i++) {
					if(record.size() == title.length)
					{
						string = record.get(i);
						eachLine.add(string);
					}
					
				}
				
				content.add(eachLine);
			}			
			
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return content;
		
	}
	
	public static void main(String[] args) {
		System.setProperty("java.util.logging.config.file",
	            "./logging.properties");
		FileWriter fileWriter=null;
		final long startTime = System.currentTimeMillis();
		
		//æ€»è¡Œæ•°
		int countNumber=0;
		//æœ‰æ•ˆè¡Œæ•°
		int validNumber=0;
		//è·³è¿‡è¡Œæ•°
		int skippedNumber=0;
		//æ­¤å¤„å¡«å†™ä½ è¦�è¯»å�–æ–‡ä»¶çš„è·¯å¾„
		String filePath="C:\\Users\\85415\\eclipse-workspace\\Assignment1\\Sample Data";
		//æ­¤å¤„å¡«å†™ä½ æƒ³ç”Ÿæˆ�çš„ç»“æžœæ–‡ä»¶è·¯å¾„
		String csvFile = "C:\\Users\\85415\\eclipse-workspace\\Assignment1\\Output\\res.csv";
		try {
	        File outputCsvFile = new File(csvFile);
	        if(!outputCsvFile.exists()) {

	        	outputCsvFile.createNewFile();
	        }
	        //å¾—åˆ°æ–‡ä»¶è¾“å‡ºæµ� å¹¶å†™å…¥title
	         fileWriter = new FileWriter(outputCsvFile,true);
	        String[] title = {"First Name","Last Name","Street Number","Street","City","Province","Postal Code","Country","Phone Number","email Address"};
	       for (int i = 0; i < title.length; i++) {
	    	   fileWriter.write("\"");
	    	   fileWriter.write(title[i]);
	    	   fileWriter.write("\",");
	    	   if(i==title.length-1) {
//	    		   fileWriter.write("\"");
//	    		   fileWriter.write(title[i]);
//	    		   fileWriter.write("\",");
	    		   fileWriter.write("\n");
	    		   
	    	   }
			
		}
	        for (String string : title) {
		}
	       fileWriter.write("\n");
		//å¾—åˆ°è¯¥ç›®å½•ä¸‹æ‰€æœ‰æ–‡ä»¶
		List<File> files = getFiles(filePath,new ArrayList<File>());
		//é��åŽ†æ¯�ä¸ªæ–‡ä»¶å¹¶è§£æž�

		for (File file : files) {
			//å¾—åˆ°æ–‡ä»¶å†…å®¹

			List<ArrayList<String>> content = parseFileIndex(file);
			List<ArrayList<String>> _content = new ArrayList<ArrayList<String>>();
			
			//
			//åˆ¤æ–­æ–‡ä»¶å†…å®¹æ˜¯å�¦ç¬¦å�ˆè¦�æ±‚
			for (ArrayList<String> eachLine : content) {
				    countNumber++;
				    boolean flag=true;
				for (String string : eachLine) {
					if(string==null||string.equals("")) {
						skippedNumber++;
						flag=false;
						continue;
					}
					
					
				}
				if(flag) {
					validNumber++;
					_content.add(eachLine);
				}
			}
			//å¡«å……æœ‰æ•ˆæ•°æ�®åˆ°æ–‡ä»¶
			for(ArrayList<String> eachLine : _content) {
				for (int i = 0; i < eachLine.size(); i++) {
					fileWriter.write("\"");
					fileWriter.write(eachLine.get(i));
					fileWriter.write("\",");
					if(i==eachLine.size()-1) {
//						fileWriter.write("\"");
//						fileWriter.write(eachLine.get(i));
//						fileWriter.write("\"");
						fileWriter.write("\n");
						
					}
					
				}
			}
			
		}
		
		
		
} catch (Exception e) {
	e.printStackTrace();
	Logger.getLogger("main").log(Level.INFO,e.getMessage());
}finally {
	if(fileWriter!=null) {
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
//		finally {			
//
//			fileWriter=null;
//		}
	}
}
		
		
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) +" ms" + "\n" + "valid rows number:" + validNumber + "\n skipped rows number:" + skippedNumber);
		Logger.getLogger("main").log(Level.INFO,"Total execution time: " + (endTime - startTime) +" ms" + "\n" + "valid rows number:" + validNumber + "\n skipped rows number:" + skippedNumber);

    

		
		
	}

}
