dir=/Users/matthew/Workbench
for f in $dir/Data/NYTimes/NYTimesCorpus_4/*/*/*/*.txt; do
    [[ $f == *.xml ]] && continue # skip output files
    java -cp "*" -Xmx2g edu.stanford.nlp.pipeline.StanfordCoreNLP -annotators tokenize,ssplit,pos,lemma,ner,parse,dcoref -filelist "$f" -outputDirectory .  
done
