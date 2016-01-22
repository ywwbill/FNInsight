package preprocess;

import java.io.FileInputStream;

import opennlp.tools.lemmatizer.SimpleLemmatizer;

public class Lemmatizer
{
	public SimpleLemmatizer lemmatizer;
	
	public String lemmatize(String word, String pos)
	{
		return lemmatizer.lemmatize(word, pos);
	}
	
	public Lemmatizer() throws Exception
	{
		this(PreprocessCfg.lemmaDictFileName);
	}
	
	public Lemmatizer(String dictFileName) throws Exception
	{
		lemmatizer=new SimpleLemmatizer(new FileInputStream(dictFileName));
	}
}
