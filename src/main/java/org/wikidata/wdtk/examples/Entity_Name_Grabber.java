package org.wikidata.wdtk.examples;

import java.io.BufferedReader;
import java.io.FileReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class Entity_Name_Grabber 
{
	public static void main(String[] args) throws Exception 
    {
    	
    	BufferedReader br = new BufferedReader(new FileReader("/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02/1639121_output.xml"));
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
            
            

//               String word = doc.select("block.full_text.p.PERSON").text();
//               
//               //String word = Jsoup.parse("block.full_text.p.PERSON").text(); 
//	 		   
//	 		   System.out.println("here it is:" + word);
	 		   
                for( Element item : doc.getElementsByTag("p") )
	 		    {
                	for( Element rslt : item.getElementsByTag("PERSON") )
    	 		    {
                		System.out.println( "here it is:" + rslt );
    	 		    }
	 		    }

            
    	}
    	finally 
    	{
            br.close();
        }
    }
            
	

}
