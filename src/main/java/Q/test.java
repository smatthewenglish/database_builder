package Q;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test 
{
    public static void main(String[] args) throws Exception 
    {
    	String home = "Q7747";
    	String away = "Q159";
    	
    	
 		String URL_czech = "http://milenio.dcc.uchile.cl/sparql?default-graph-uri=&query=PREFIX+%3A+%3Chttp%3A%2F%2Fwww.wikidata.org%2Fentity%2F%3E%0D%0ASELECT+*+WHERE+%7B%0D%0A+++%3A" 
			       + home + "+%3FsimpleProperty+%3A" 
			       + away + "%0D%0A%7D%0D%0A&format=text%2Fhtml&timeout=0&debug=on";

URL wikidata_page = new URL(URL_czech);
HttpURLConnection wiki_connection = (HttpURLConnection)wikidata_page.openConnection();
InputStream wikiInputStream = null;


	try 
	{
	    // try to connect and use the input stream
	    wiki_connection.connect();
	    wikiInputStream = wiki_connection.getInputStream();
	} 
	catch(IOException error) 
	{
	    // failed, try using the error stream
	    wikiInputStream = wiki_connection.getErrorStream();
	}
// parse the input stream using Jsoup
Document docx = Jsoup.parse(wikiInputStream, null, wikidata_page.getProtocol()+"://"+wikidata_page.getHost()+"/");



Elements link_text = docx.select("table.sparql > tbody > tr:nth-child(2) > td > a");
//link_text.text();
for (Element l : link_text) 
{
	String output = l.text();
	System.out.println( output );
}
    }
}
