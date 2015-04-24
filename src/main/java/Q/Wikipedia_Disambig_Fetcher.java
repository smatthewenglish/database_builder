package Q;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wikipedia_Disambig_Fetcher 
{
	static Set<String> urlsVisited = new HashSet<String>();
	static Set<String> linqVisited = new HashSet<String>();
	
    public static void all_possibilities( String platonic_key, String associated_alias ) throws Exception
    {

    	urlsVisited.add(platonic_key + associated_alias);
    	
		System.out.println("this is a disambig page");
		//if it's a disambig page we know we can go right to the wikipedia

		
		//get it's normal wiki disambig page
		String URL_czech = "https://en.wikipedia.org/wiki/" + associated_alias;
		URL wikidata_page = new URL(URL_czech);
		HttpURLConnection wiki_connection = (HttpURLConnection)wikidata_page.openConnection();
		InputStream wikiInputStream = null;
		
	        
		try 
		{
		    // try to connect and use the input stream
		    wiki_connection.connect();
		    wikiInputStream = wiki_connection.getInputStream();
		} 
		catch(IOException e) 
		{
		    // failed, try using the error stream
		    wikiInputStream = wiki_connection.getErrorStream();
		}
		// parse the input stream using Jsoup
		Document docx = Jsoup.parse(wikiInputStream, null, wikidata_page.getProtocol()+"://"+wikidata_page.getHost()+"/");
		
		
        //this can handle the less structured ones. 
        Elements linx = docx.select( "p:contains(" + associated_alias + ") ~ ul a:eq(0)" );
            
        //System.out.println("This is linx");
        //System.out.println(linx.text());
        
        for (Element linq : linx) 
        {
        	System.out.println(linq.text());
        	
        	
        	String linq_nospace = URLEncoder.encode( linq.text() , "UTF-8");
        	
        	//make sure we're still on track, the reference actually contains the name of the entity we're
        	//considering.
        	if ( linq_nospace.toLowerCase().contains(platonic_key.toLowerCase()) ) 
        	{
        		Wikidata_Q_Reader.getQ( platonic_key, linq_nospace );
        	}
        	
        	urlsVisited.add(platonic_key + linq_nospace);
    	}
 

	    

        
    }
    
 



}

