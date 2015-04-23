package org.wikidata.wdtk.examples;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Entity_Q_Grabber_Test 
{
	 //hold Q values for all names
     Map<String, String> q_valMap = new HashMap<String, String>();
    
	 public static void main(String[] args) throws Exception 
	    {
	    	
//	    	String line;
//	    	try (   // read in the original file, list of names, w/e
//	    	        InputStream fis = new FileInputStream( "/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02/test/people.txt" );
//	    	        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
//	    	        BufferedReader br = new BufferedReader(isr);
//	    	    ) 
//	    	{
//		    	while ((line = br.readLine()) != null) 
//		    	{
		    		
			    	//while you still have names
		        	//String variable_entity_nospace = line.replace(' ', '+');
		            URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=" + "Reid" + "&submit=Search");
		            URLConnection yc = site.openConnection();
			        BufferedReader in = new BufferedReader(
			                                new InputStreamReader(
			                                    yc.getInputStream())); 
			        String inputLine;

			        while ((inputLine = in.readLine()) != null) 
			        {
			        	//the first thing we should try to do is get the Q value, only do this shit if its a disambig
			        	//but actually we can see first if its a disambig
			        	 
			        	Pattern p = Pattern.compile("<div class=\"wikibase-entitytermsview-heading-description \">Wikipedia disambiguation page</div>");
			        	Matcher m = p.matcher(inputLine);
		                if (m.matches()) 
		                {
		                	System.out.println("this is a disambig page");
		                	//if it's a disambig page we know we can go right to the wikipedia

		                	
					    	//get it's normal wiki disambig page
		                	
		                	//String name = "Bush";
		                	String name = "Reid";
		                	//String name = "Corzine";
		    	            //Document docx = Jsoup.connect( "https://en.wikipedia.org/wiki/" ).get();
		    	            Document docx = Jsoup.connect( "https://en.wikipedia.org/wiki/" + name ).get();
		    	            
		    	            

		    	            
//		    	            boolean moreStructure = false;
//		    	            // first czech if it's more structured
//		    	            //for more complex structures entities, a la 'Bush' or 'Caplan'
//		    	            Elements elements = docx.select("span.mw-headline, li > a");
//		    	            boolean inPeopleSection = false;
//		    	            for (Element elem : elements) 
//		    	            {
//		    	                if (elem.className().equals("mw-headline")) 
//		    	                {
//		    	                    // It's a headline
//		    	                    inPeopleSection = elem.id().equals("People");
//		    	                    
//		    	                    moreStructure = true;
//		    	                } 
//		    	                else 
//		    	                {
//		    	                    // It's a link
//		    	                    if (inPeopleSection)
//		    	                    {
//		    	                        System.out.println(elem.text());
//		    	                    }
//		    	                }
//		    	            }
		    	            
//		    	            
//		    	            if(!moreStructure)
//		    	            {
			    	            //this can handle the less structured ones. 
					            //Elements linx = docx.select("a:contains("+ name +")");
					            Elements linx = docx.select( "p:contains("+ name +") ~ ul a:eq(0)" );
					            
					            for (Element linq : linx) 
					            {
					            	System.out.println(linq.text());
					            	//now search wikidata for these names.
					            	
					            	
					        	}
//		    	            }
		    	            

		    	}
	    	}
			in.close();

        	
//	        	}
//	        }
	        
	    	
	    }

}

//Pattern p = Pattern.compile("<!-- wikibase-toolbar --><span class=\"wikibase-toolbar-container\"><span class=\"wikibase-toolbar-item " +
//        "wikibase-toolbar \">\\[<span class=\"wikibase-toolbar-item wikibase-toolbar-button wikibase-toolbar-button-edit\"><a " +
//        "href=\"/wiki/Special:SetSiteLink/(.*?)\">edit</a></span>\\]</span></span>");
//    String line = inputLine;
//
//    Matcher m = p.matcher(line);
//    if (m.matches()) 
//    {
//        String first_part = m.group(1);
//        //System.out.println(first_part);
//        
//        q_valMap.put( first_part , variable_entity);
//    }







//for less complex structures entities, a la 'Corzine'
//System.out.println(docx);
//Elements table = docx.select("ul");
//Elements links = table.select("li");
//
//
//
//
//for (Element link: links) 
//{
//    String url = link.attr("href");
//    String text = link.text();
//    System.out.println(text + ", " + url);
//}











//		
//		<p><b>Corzine</b> may refer to:</p> 
//		<ul> 
//		 <li><a href="/wiki/Dave_Corzine" title="Dave Corzine">Dave Corzine</a> (born 1956), basketball player</li> 
//		 <li><a href="/wiki/Jon_Corzine" title="Jon Corzine">Jon Corzine</a> (born 1947), former CEO of <a href="/wiki/MF_Global" title="MF Global">MF Global</a>, former Governor on New Jersey, former CEO of <a href="/wiki/Goldman_Sachs" title="Goldman Sachs">Goldman Sachs</a></li> 
//		</ul> 
//		<table id="setindexbox" class="metadata plainlinks dmbox dmbox-setindex" style="" role="presentation"> 
//		
//		


















////THIS SORT OF WORKS!
//Elements table = docx.select("ul");
//Elements links = table.select("li");
//for (Element link: links) 
//{
//    String url = link.attr("href");
//    String text = link.text();
//    System.out.println(text + ", " + url);
//}




//
//URL site_two = new URL("https://en.wikipedia.org/wiki/Bush");
//URLConnection ycb = site_two.openConnection();
//BufferedReader inb = new BufferedReader(
//                        new InputStreamReader(
//                        ycb.getInputStream()));
//
//StringBuilder sb = new StringBuilder();
//
//while ((inputLine = inb.readLine()) != null) 
//{
//	//get the disambig
//	//System.out.println(inputLine);
//    	
//    sb.append(inputLine);
//    sb.append(System.lineSeparator());
//    inputLine = inb.readLine();
//}
//
//String everything = sb.toString();
////System.out.println(everything);
//Document doc = Jsoup.parse(everything);




//Document docx = Jsoup.connect("https://en.wikipedia.org/wiki/Corzine").get();
////Document docx = Jsoup.connect("https://en.wikipedia.org/wiki/Caplan").get();
////Document docx = Jsoup.connect("https://en.wikipedia.org/wiki/Smith").get();

