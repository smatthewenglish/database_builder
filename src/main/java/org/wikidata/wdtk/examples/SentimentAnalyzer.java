//package org.wikidata.wdtk.examples;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Properties;
//
//import edu.stanford.nlp.ling.CoreAnnotations;
//import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
//import edu.stanford.nlp.pipeline.Annotation;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
//import edu.stanford.nlp.trees.Tree;
//import edu.stanford.nlp.util.CoreMap;
//
//public class SentimentAnalyzer 
//{
//	 
//	/** Annotates a document with our customized pipeline.
//	 * @param text A text to process
//	 * @return The annotated text
//	 * @throws IOException 
//	 */
//	public static void main(String[] args) throws Exception 
//	{
//		
//		/* 
//		 First step is initiating the Stanford CoreNLP pipeline 
//		   (the pipeline will be later used to evaluate the text and annotate it)
//		   Pipeline is initiated using a Properties object which is used for setting all needed entities, 
//		   annotations, training data and so on, in order to customized the pipeline initialization to 
//		   contains only the models you need 
//		   */
//		Properties props = new Properties();
//
//		/* The "annotators" property key tells the pipeline which entities should be initiated with our
//		     pipeline object, See http://nlp.stanford.edu/software/corenlp.shtml for a complete reference 
//		     to the "annotators" values you can set here and what they will contribute to the analyzing process  
//		     */
//		props.put( "annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, dcoref" );
//		StanfordCoreNLP pipeLine = new StanfordCoreNLP( props );
//
//		/* 
//		 * Next we can add customized annotation and trained data 
//		   I will elaborate on training data in my next blog chapter, for now you can comment those lines 
//		   */
//		pipeLine.addAnnotator(new RegexNERAnnotator("some RegexNer structured file"));
//		pipeLine.addAnnotator(new TokensRegexAnnotator(“some tokenRegex structured file”));
//
//		// Next we generate an annotation object that we will use to annotate the text with
//		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
//		String currentTime = formatter.format( System.currentTimeMillis() );
//
//		// inputText will be the text to evaluate in this example
//		String inputText = "some text to evaluate";
//		Annotation document = new Annotation( inputText );
//		document.set( CoreAnnotations.DocDateAnnotation.class, currentTime );
//
//		// Finally we use the pipeline to annotate the document we created
//		pipeLine.annotate( document );
//
//		/* now that we have the document (wrapping our inputText) annotated we can extract the
//		    annotated sentences from it, Annotated sentences are represent by a CoreMap Object 
//		    */
//		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//
//		/* Next we can go over the annotated sentences and extract the annotated words,
//		    Using the CoreLabel Object */
//		for (CoreMap sentence : sentences)
//		{
//		    for (CoreLabel token : sentence.get(TokensAnnotation.class))
//		    {
//		        // Using the CoreLabel object we can start retrieving NLP annotation data
//		        // Extracting the Text Entity
//		        String text = token.getString(TextAnnotation.class);
//
//		        // Extracting Name Entity Recognition 
//		        String ner = token.getString(NamedEntityTagAnnotation.class);
//
//		        // Extracting Part Of Speech
//		        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//
//		        // Extracting the Lemma
//		        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
//		        System.out.println("text=" + text + ";NER=" + ner +
//		                        ";POS=" + pos + ";LEMMA=" + lemma);
//
//		        /* There are more annotation that are available for extracting 
//		            (depending on which "annotators" you initiated with the pipeline properties", 
//		            examine the token, sentence and document objects to find any relevant annotation 
//		            you might need */
//		    }
//
//		    /* Next we will extract the SemanitcGraph to examine the connection 
//		       between the words in our evaluated sentence */
//		    SemanticGraph dependencies = sentence.get
//		                                (CollapsedDependenciesAnnotation.class);
//
//		    /* The IndexedWord object is very similar to the CoreLabel object 
//		        only is used in the SemanticGraph context */
//		    IndexedWord firstRoot = dependencies.getFirstRoot();
//		    List<SemanticGraphEdge> incomingEdgesSorted =
//		                                dependencies.getIncomingEdgesSorted(firstRoot);
//
//		    for(SemanticGraphEdge edge : incomingEdgesSorted)
//		    {
//		        // Getting the target node with attached edges
//		        IndexedWord dep = edge.getDependent();
//
//		        // Getting the source node with attached edges
//		        IndexedWord gov = edge.getGovernor();
//
//		        // Get the relation name between them
//		        GrammaticalRelation relation = edge.getRelation();
//		    }
//		  
//		    // this section is same as above just we retrieve the OutEdges
//		    List<SemanticGraphEdge> outEdgesSorted = dependencies.getOutEdgesSorted(firstRoot);
//		    for(SemanticGraphEdge edge : outEdgesSorted)
//		    {
//		        IndexedWord dep = edge.getDependent();
//		        System.out.println("Dependent=" + dep);
//		        IndexedWord gov = edge.getGovernor();
//		        System.out.println("Governor=" + gov);
//		        GrammaticalRelation relation = edge.getRelation();
//		        System.out.println("Relation=" + relation);
//		   }
//		}
//	}
//	
//}
//		
////		BufferedReader br = new BufferedReader(new FileReader("/home/matthias/Workbench/SUTD/nytimes_corpus/NYTimesCorpus/2005/01/01/1638802_output.txt"));
////    	try 
////    	{
////            StringBuilder sb = new StringBuilder();
////            String line = br.readLine();
////
////            while (line != null) 
////            {
////            	
////                sb.append(line);
////                sb.append(System.lineSeparator());
////                line = br.readLine();
////            }
////            String everything = sb.toString();
////            //System.out.println(everything);
////		
////		    Annotation doc = new Annotation(everything);
////	
////		    StanfordCoreNLP pipeline;
////	
////		    // creates a StanfordCoreNLP object, with POS tagging, lemmatization,
////		    // NER, parsing, and coreference resolution
////		    Properties props = new Properties();
////	
////		    // configure pipeline
////		    props.put(
////		              "annotators", 
////		              "tokenize, ssplit"
////		              );
////		    
////		    pipeline = new StanfordCoreNLP(props);
////	
////		    pipeline.annotate(doc);
////		    
////		    System.out.println( doc );
////		    
////	    }
////	    finally 
////	    {
////	    	br.close();
////	    }
////
////	}
//	
//	
//	
//	
//	
//	
//	
