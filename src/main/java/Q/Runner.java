package Q;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Runner 
{
    static Map<String, HashSet<String> > q_valMap = new HashMap<String, HashSet<String> >();
    
	 public static void main(String[] args) throws Exception 
	    {
	    	//System.out.println("whut the fuck");
	    	
		    String name_list_file = "/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/02/test/people_test.txt";
	    	
		    String single_name;
		    
		    try (   
	    			// read in the original file, list of names, w/e
	    	        InputStream stream_for_name_list_file = new FileInputStream( name_list_file );
	    	        InputStreamReader stream_reader = new InputStreamReader( stream_for_name_list_file , Charset.forName("UTF-8"));
	    	        BufferedReader line_reader = new BufferedReader( stream_reader );
	    	    ) 
	    	{
			    while (( single_name = line_reader.readLine() ) != null) 
			    {
			    	//replace this by a URL encoder
			    	//String associated_alias = single_name.replace(' ', '+');
			    	String associated_alias = URLEncoder.encode( single_name , "UTF-8");
			    	
			    	String platonic_key = single_name;
			    	
			    	Wikidata_Q_Reader.getQ( platonic_key, associated_alias, q_valMap );
			    }
	    	}
		    
	        // \\ // ! PRINT IT ! // \\ // \\ // \\ // \\ // \\ // \\
	        for (Map.Entry<String, HashSet<String> > entry : q_valMap.entrySet()) 
	        {
	            System.out.println(entry.getKey()+" : " + Arrays.deepToString(q_valMap.entrySet().toArray()) );
	        }
	    }
}