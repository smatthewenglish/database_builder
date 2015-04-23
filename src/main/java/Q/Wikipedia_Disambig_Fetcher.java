package Q;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wikipedia_Disambig_Fetcher 
{
    public static void all_possibilities( String variable_entity ) throws Exception
    {
		System.out.println("this is a disambig page");
		//if it's a disambig page we know we can go right to the wikipedia
	
		
		//get it's normal wiki disambig page
	    Document docx = Jsoup.connect( "https://en.wikipedia.org/wiki/" + variable_entity ).get();
	
	   

	        //this can handle the less structured ones. 
	        Elements linx = docx.select( "p:contains(" + variable_entity + ") ~ ul a:eq(0)" );
	        
	        for (Element linq : linx) 
	        {
	        	System.out.println(linq.text());
	        	String linq_nospace = linq.text().replace(' ', '+');
	        	Wikidata_Q_Reader.getQ( linq_nospace );

	    	}

    }
}
