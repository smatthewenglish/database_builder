package Q;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class Label_Assigner 
{

	 @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception 
    {
		    // GET JSON DATA
	        File f = new File("/home/matthias/Workbench/SUTD/nytimes_corpus/wdtk-parent/wdtk-examples/JSON_Output/user666.json");
	        String jsonTxt = null;
	        
	        if (f.exists())
	        {
	            InputStream is = new FileInputStream("/home/matthias/Workbench/SUTD/nytimes_corpus/wdtk-parent/wdtk-examples/JSON_Output/user666.json");
	            jsonTxt = IOUtils.toString(is);
	        }
	        //reformat
	        jsonTxt = ( jsonTxt.substring(1, jsonTxt.length()-1) ).replace("\\","");
	        
	        Gson json = new Gson();
	        Map<String, ArrayList<String>> massive_Q_storage_map = new HashMap<String, ArrayList<String>>();
	        massive_Q_storage_map = (Map<String, ArrayList<String>>) json.fromJson(jsonTxt, massive_Q_storage_map.getClass());
	        System.out.println(massive_Q_storage_map);
	        
	        


	        // Read in all the sentences, that are in files, in this folder
	        final File folder = new File("/home/matthias/Workbench/SUTD/nytimes_corpus/wdtk-parent/wdtk-examples/JSON_Output/");
	        
            for (final File fileEntry : folder.listFiles()) 
            {
            	Document doc = null;
            	
            	BufferedReader br = new BufferedReader(new FileReader(fileEntry));
    	    	try 
    	    	{
    	    		//Store the filename
	                //System.out.println(fileEntry.getName());
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
		            
		            doc = Jsoup.parse(everything);
    	    	}
    	    	finally 
    	    	{
    	            br.close();
    	        }
		            
	            Elements sentences = doc.getElementsByTag("sentence");
	            
	            for (Element sentence : sentences) 
		        {
		        	//hold the q values we need to search over
	            	List<String> Q_value_references_for_sentence_entities = new ArrayList<String>();
		        	
	            	//store the sentence number
	            	String sentence_number = sentence.select("sentence").text();
	            	sentence_number = sentence_number.substring(0, sentence_number.indexOf(" ")); 
	            	System.out.println(sentence_number);
	            	
	            	//get all the entities in this sentence
		        	Elements pers = sentence.select("PERSON");
		        	Elements locs = sentence.select("LOCATION");
		        	Elements orgs = sentence.select("ORGANIZATION");

		        	//collect all the elements to a list, all the elements of one sentence
		        	List<String> sentence_entity_list = new ArrayList<String>();
		        	
		        	for (Element per : pers) 
			        {
		        		sentence_entity_list.add(per.text().trim());
			        }
		        	for (Element loc : locs) 
			        {
		        		sentence_entity_list.add(loc.text().trim());
			        }
		        	for (Element org : orgs) 
			        {
		        		sentence_entity_list.add(org.text().trim());
			        }

			        	
		        	// for the list of Q values to keys
		        	for (Entry<String, ArrayList<String>> DB__key_to_value_item : massive_Q_storage_map.entrySet()) 
		        	{
		        		for (String entity : sentence_entity_list)
		        		{
			        		if (DB__key_to_value_item.getKey().contains(entity)) 
			        		{
			        		    //for all the entries( Qs) associated with that key
			        			//add them to Q_value_references_for_sentence_entities
			        			
			        			for (String Q_value : DB__key_to_value_item.getValue()) 
			        			{
			        				Q_value_references_for_sentence_entities.add( Q_value );
			        			}
			        		}
		        		}
		        	}
				        		    
				        		    
				        		    
                    //czeher
		        	HashSet<String> list_of_relation_URLs = new HashSet<String>();

                    for (String home : Q_value_references_for_sentence_entities) 
                    {
                      for (String away : Q_value_references_for_sentence_entities) 
                      {
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
								output = output.substring(0, output.length()-1);
								list_of_relation_URLs.add( output );
						    }
                        }		    
			        }
                    
                    //now we're back at the sentence level
					//iter hash
				    for (String url : list_of_relation_URLs) 
				    {
				    	//System.out.println( url );
				
				    	//go to relation url

				 		
				 		String afterP= url.substring(url.lastIndexOf('P'));
				 		
				 		System.out.println(afterP);
				 		
				 		///home/matthias/Workbench/SUTD/nytimes_corpus/wdtk-parent/wdtk-examples/wikidata-properties-dumper-master/outputs/properties-es.json

				 		
				    }
						
						 
						  
				
						
				    
				    	
				}

    	    	
            }




	        

        
	          
	}
	 
	 
	

}       
	


	          
