package preprocess;

import java.io.FileInputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class POSTagger
{
	public POSTaggerME tagger;
	
	public String[] tag(String[] tokens)
	{
		return tagger.tag(tokens);
	}
	
	public POSTagger() throws Exception
	{
		this(PreprocessCfg.posModelFileName);
	}
	
	public POSTagger(String modelFileName) throws Exception
	{
		tagger=new POSTaggerME(new POSModel(new FileInputStream(modelFileName)));
	}
}
