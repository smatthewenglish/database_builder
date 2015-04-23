package org.wikidata.wdtk.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetQ 
{
    //public static String[] getQ(String variable_entity) throws Exception
    public static void getQ(String variable_entity) throws Exception
    {
    	String variable_entity_nospace = variable_entity.replace(' ', '+');
    	
        //URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=Mikhail+Bakunin&submit=Search");
        //URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=Thomas+Edward+Lawrence&submit=Search");
        URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=" + variable_entity_nospace + "&submit=Search");
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
        
//        String[] result = q_valMap.values().toArray(new String[0]);
//        
//        return result;
        
    }
}
