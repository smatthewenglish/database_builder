package org.wikidata.wdtk.examples;

import java.io.BufferedReader;
import java.io.FileReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SentenceBounds 
{
	
	 public static void main(String[] args) throws Exception 
	    {
		 
		 	String manipulate_file = "/home/matthias/Workbench/SUTD/nytimes_corpus/18/CoreNLP/stanford-corenlp-full-2015-01-29/1638673_output.txt.xml";
			 
		 	BufferedReader br = new BufferedReader( new FileReader( manipulate_file ) );
	
		 	try 
		 	{
		 		StringBuilder sb = new StringBuilder();
		 		String line = br.readLine();

		 		while (line != null) 
		 		{
		 			sb.append(line);
		 			sb.append(System.lineSeparator());
		 			line = br.readLine();
		 		}
		 		String everything = sb.toString();
		 		//System.out.println(everything);
		 		Document doc = Jsoup.parse(everything);
 
		 		int index = 0; 
		 		
		 	   for( Element item : doc.select("sentence") )
		 	   {
		 		   System.out.println("<sentence> " + index );
		 		   
		 		   String word = item.select("word").text();
		 		   
		 		   System.out.println(word);
		 		   
		 		  System.out.println("</sentence>" + "\n");
		 		   
		 		   index++;
		 	   }


		 	}
		 	finally 
		 	{
		 		br.close();
		 	}
	    }
	 
}
	

