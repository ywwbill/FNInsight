package preprocess;

import java.io.FileInputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Tokenizer
{
	public TokenizerME tokenizer;
	
	public String[] tokenize(String sentence)
	{
		return tokenizer.tokenize(sentence);
	}
	
	public Tokenizer() throws Exception
	{
		this(PreprocessCfg.tokenModelFileName);
	}
	
	public Tokenizer(String modelFileName) throws Exception
	{
		tokenizer=new TokenizerME(new TokenizerModel(new FileInputStream(modelFileName)));
	}
}
