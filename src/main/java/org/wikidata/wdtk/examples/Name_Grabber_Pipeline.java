package org.wikidata.wdtk.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Name_Grabber_Pipeline 
//public class Name_Grabber
{
    public static void main(String[] args) throws Exception 
    {
    	
    	String dirStart = "/Users/matthew/Workbench/Data/NYTimes/NYTimesCorpus_2";
    	//String dirStart = "/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02";

    	Path root = Paths.get(dirStart);
    	
    	Files.walkFileTree(root.toAbsolutePath().normalize(), new SimpleFileVisitor<Path>() 
    	{
    	    @Override
    	    public FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException 
    	    {
    	    	
    	    	
	        try(InputStream inputStream = Files.newInputStream(file);
	        		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
	        {
	                 	
    	        	
    	        	// SOUP PART
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
    	            
    	            
    	            Elements contents = doc.getElementsByTag("p");

    	            
    		        for (Element content : contents) 
    		        {
    		        	
    		        	Elements pers = content.select("PERSON");
    		        	Elements locs = content.select("LOCATION");
    		        	Elements orgs = content.select("ORGANIZATION");
    		        	
    		        	for (Element per : pers)
    		        	{
    		        		String linkText_per = per.text();
    			        	//System.out.println("PERSON");

    			        	//System.out.println(linkText_per);
    		        		
    		        		FileWriter fileWriter = new FileWriter( "/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02/test/people.txt" , true);
    			            //FileWriter fileWriter = new FileWriter( "/Users/matthew/Workbench/Data/NYTimes/Entity_Lists/persons.txt" , true);

    			            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    			            bufferedWriter.write( linkText_per + "\n" );
    			            bufferedWriter.close();
    		
    		        	}
    		        	
    		        	for (Element loc : locs)
    		        	{
    		        		String linkText_loc = loc.text();
    			        	//System.out.println("LOCATION");

    			        	//System.out.println(linkText_loc);
    			        	
    			            FileWriter fileWriter = new FileWriter( "/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02/test/locations.txt" , true);
    			            //FileWriter fileWriter = new FileWriter( "/Users/matthew/Workbench/Data/NYTimes/Entity_Lists/locations.txt" , true);

    			            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    			            bufferedWriter.write( linkText_loc + "\n" );
    			            bufferedWriter.close();
    		        	}
    		        	
    		        	for (Element org : orgs)
    		        	{
    		        		String linkText_org = org.text();
    			        	//System.out.println("ORGANIZATION");
			        	
    			        	//System.out.println(linkText_org);
    			        	
    			            FileWriter fileWriter = new FileWriter( "/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02/test/organizations.txt" , true);
    			            //FileWriter fileWriter = new FileWriter( "/Users/matthew/Workbench/Data/NYTimes/Entity_Lists/organizations.txt" , true);

    			            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    			            bufferedWriter.write( linkText_org + "\n" );
    			            bufferedWriter.close();

    		        	}

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

  
