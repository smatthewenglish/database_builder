package org.wikidata.wdtk.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SoupTest 
{

	    public static void main(String[] args) throws Exception 
	    {
	        
	    	
	    	BufferedReader br = new BufferedReader(new FileReader("/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/01/1638749_output.xml"));
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
	            
	            
	            Elements contents = doc.getElementsByTag("p");


	            int sentence = 0;
		        for (Element content : contents) 
		        {
		        	System.out.println("we're on sentence " + sentence + ".");
		        	//String linkText = content.text();
		        	
		        	Elements pers = content.select("PERSON");
		        	Elements locs = content.select("LOCATION");
		        	Elements orgs = content.select("ORGANIZATION");
		        	
		        	for (Element per : pers)
		        	{
		        		String linkText_per = per.text();
			        	System.out.println("PERSON");
			        	//System.out.println(linkText_per);
			        	//System.out.println();
			        	
			        	String linkText_per_nospace = linkText_per.replace(' ', '+');
			        	//System.out.println(linkText_per_nospace);
			        	
			        	getQ(linkText_per_nospace);
			        	
			        	System.out.println();
		        	}
		        	
		        	for (Element loc : locs)
		        	{
		        		String linkText_loc = loc.text();
			        	System.out.println("LOCATION");
			        	//System.out.println(linkText_loc);
			        	//System.out.println();
			        	
			        	String linkText_loc_nospace = linkText_loc.replace(' ', '+');
			        	//System.out.println(linkText_loc_nospace);
			        	
			        	getQ(linkText_loc_nospace);
			        	
			        	System.out.println();
		        	}
		        	
		        	for (Element org : orgs)
		        	{
		        		String linkText_org = org.text();
			        	System.out.println("ORGANIZATION");
			        	//System.out.println(linkText_org);
			        	//System.out.println();
			        	
			        	String linkText_org_nospace = linkText_org.replace(' ', '+');
			        	//System.out.println(linkText_org_nospace);
			        	
			        	getQ(linkText_org_nospace);
			        	
			        	System.out.println();
		        	}

		        	sentence++;
				}


	            
	    	}
	    	finally 
	    	{
	            br.close();
	        }
	    	
	    }
	    
	    
	    public static void getQ(String variable_entity) throws Exception 
	    {
	        //URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=Mikhail+Bakunin&submit=Search");
	        //URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=Thomas+Edward+Lawrence&submit=Search");
	        URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=" + variable_entity + "&submit=Search");
	        URLConnection yc = site.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String inputLine;
	        
	        Map<String, String> q_valMap = new HashMap<String, String>();

	        while ((inputLine = in.readLine()) != null) 
	        {


	        	Pattern p = Pattern.compile("<!-- wikibase-toolbar --><span class=\"wikibase-toolbar-container\"><span class=\"wikibase-toolbar-item " +
	                    "wikibase-toolbar \">\\[<span class=\"wikibase-toolbar-item wikibase-toolbar-button wikibase-toolbar-button-edit\"><a " +
	                    "href=\"/wiki/Special:SetSiteLink/(.*?)\">edit</a></span>\\]</span></span>");
	                String line = inputLine;

	                Matcher m = p.matcher(line);
	                if (m.matches()) 
	                {
	                    String first_part = m.group(1);
	                    //System.out.println(first_part);
	                    
	                    q_valMap.put( first_part , variable_entity);
	                }

	        }
	        in.close();
	        
	        for (Map.Entry<String, String> entry : q_valMap.entrySet()) 
	        {
	            System.out.println(entry.getKey()+" : "+entry.getValue());
	        }
	    }
}


	        


