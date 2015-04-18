package org.wikidata.wdtk.examples;

import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class URLConnectionReader 
{
    public static void main(String[] args) throws Exception 
    {
        //URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=Mikhail+Bakunin&submit=Search");
        //URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=Thomas+Edward+Lawrence&submit=Search");
        URL site = new URL("https://www.wikidata.org/wiki/Special:ItemByTitle?site=en&page=Russia&submit=Search");
        URLConnection yc = site.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

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
                    System.out.println(first_part);
                }

        }
        in.close();
    }
}
