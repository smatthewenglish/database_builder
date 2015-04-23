package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;



public class Line_Slicer 
{

	 public static void main(String[] args) throws Exception 
    {
		 String name_list_file = "/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02/test/people_test.txt";
		 
		 stripDuplicatesFromFile( name_list_file );
    }
	
	 public static void stripDuplicatesFromFile(String filename) throws IOException 
	 {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    Set<String> lines = new HashSet<String>(10000); // maybe should be bigger
	    String line;
	    while ((line = reader.readLine()) != null) 
	    {
	        lines.add(line);
	    }
	    reader.close();
	    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	    for (String unique : lines) 
	    {
	        writer.write(unique);
	        writer.newLine();
	    }
	    writer.close();
	}
}
