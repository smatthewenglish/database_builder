package Q;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wikidata_Q_Reader 
{
    static Map<String, HashSet<String> > q_valMap = new HashMap<String, HashSet<String> >();
    
    
    //public static String[] getQ(String variable_entity) throws Exception
    public static void getQ( String variable_entity ) throws Exception
    {

    	//get the corresponding wikidata page
        URL wikidata_page = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=" + variable_entity + "&submit=Search");
        URLConnection wiki_connection = wikidata_page.openConnection();
        BufferedReader wiki_data_pagecontent = new BufferedReader(
                                			   	   new InputStreamReader(
                                						wiki_connection.getInputStream()));
        String line_by_line;
        
        

        while ((line_by_line = wiki_data_pagecontent.readLine()) != null) 
        {
        	// if we can determine it's a disambig page we need to send it off to get all 
        	// the possible senses in which it can be used.
        	Pattern disambig_pattern = Pattern.compile("<div class=\"wikibase-entitytermsview-heading-description \">Wikipedia disambiguation page</div>");
        	Matcher disambig_indicator = disambig_pattern.matcher(line_by_line);
            if (disambig_indicator.matches()) 
            {
            	//off to get the different usages
            	Wikipedia_Disambig_Fetcher.all_possibilities( variable_entity );
            }
            else
            {
	        	//get the Q value off the page by matching
	        	Pattern q_page_pattern = Pattern.compile("<!-- wikibase-toolbar --><span class=\"wikibase-toolbar-container\"><span class=\"wikibase-toolbar-item " +
	                    "wikibase-toolbar \">\\[<span class=\"wikibase-toolbar-item wikibase-toolbar-button wikibase-toolbar-button-edit\"><a " +
	                    "href=\"/wiki/Special:SetSiteLink/(.*?)\">edit</a></span>\\]</span></span>");
	
	            Matcher match_Q_component = q_page_pattern.matcher(line_by_line);
	            if ( match_Q_component.matches() ) 
	            {
	                String Q = match_Q_component.group(1);
	                
	                // 'Q' should be appended to an array, since each entity can hold multiple
	                // Q values on that basis of disambig
	                put_to_hash( variable_entity, Q );
	            }
            }

        }
        wiki_data_pagecontent.close();
        
        // \\ // ! PRINT IT ! // \\ // \\ // \\ // \\ // \\ // \\
        for (Map.Entry<String, HashSet<String> > entry : q_valMap.entrySet()) 
        {
            System.out.println(entry.getKey()+" : " + Arrays.deepToString(q_valMap.entrySet().toArray()) );
        }

        
    }
    
    // add Q values to their arrayList in the hash map at the index of the appropriate entity
    public static HashSet<String> put_to_hash(String key, String value) 
    {
        if (!q_valMap.containsKey(key)) 
        {
            return q_valMap.put(key, new HashSet<String>() );
        }
        HashSet<String> list = q_valMap.get(key);
        list.add(value);
        return q_valMap.put(key, list);
    }
}



