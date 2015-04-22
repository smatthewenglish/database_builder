package org.wikidata.wdtk.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class SentenceBounds_Pipe
{
    public static void main(String[] args) throws Exception 
    {
    	
    	String dirStart = "/home/matthias/Workbench/SUTD/nytimes_corpus/18/CoreNLP/stanford-corenlp-full-2015-01-29/test";
    	//String dirStart = "/Users/matthew/Workbench/Data/NYTimes/NYTimesCorpus_4/Sentence_Boundaries_Stripped";

    	Path root = Paths.get(dirStart);
    	
    	Files.walkFileTree(root.toAbsolutePath().normalize(), new SimpleFileVisitor<Path>() 
    	{
    	    @Override
    	    public FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException 
    	    {
    	    	
    	    	
    	        try(InputStream inputStream = Files.newInputStream(file);
    	        		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
    	        {
    	            // get the name of the file we're working with
    	            String print_file = file.getFileName().toString();
    	            //get its path
    	            Path folder = file.getParent(); 
    	            
    	            //don't process the same file twice
    	            if( ! print_file.toUpperCase().contains("bounds".toUpperCase() ) )
      	          	{
    	            	//get the file name without extention
	    	            String fileNameWithOutExt = print_file.replaceFirst("[.][^.]+$", "");
	    	            
	    	            //keep track of where we are
	    	            System.out.println(" :: " +  folder + "/" + fileNameWithOutExt + "_bounds.txt" );
	    	            
	    	            //change output to new file
	    	            PrintWriter writer = new PrintWriter( folder + "/" + fileNameWithOutExt + "_bounds.txt" , "UTF-8" );
	    	            
	    	        	// SOUP PARSE
	    	            StringBuilder sb = new StringBuilder();
	    	            String line = bufferedReader.readLine();
	    	            
	    	            while (line != null) 
	    	            {
	    	                sb.append(line);
	    	                sb.append(System.lineSeparator());
	    	                line = bufferedReader.readLine();
	    	            }
	    	            String everything = sb.toString();
	
	    	            Document doc = Jsoup.parse(everything);
	    		 		
    			 	   for( Element item : doc.select("sentence") )
    			 	   {
    			 		  //System.out.println( "<sentence> " + index );
    			 		  writer.println( "<sentence> " );
    			 		   
    			 		   String word = item.select("word").text();
    			 		   
    			 		   //System.out.println(word);
    			 		   writer.println(word);
    			 		   
    			 		  //System.out.println( "</sentence>" + "\n" );
    			 		  writer.println( "</sentence>" + "\n" );

    			 	   }
	    	        	writer.close();
	    	        	
	    	        	Files.delete(file);
    	        
      	          	}

    	            
    	        } 
    	        catch (IOException e) 
    	        {
    	        	e.printStackTrace();
    	        }
    	        
    	        return FileVisitResult.CONTINUE;
    	    }
    	}); 
    	
    	
    }
}
