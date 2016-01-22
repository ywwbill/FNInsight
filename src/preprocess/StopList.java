package preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class StopList
{
	public HashSet<String> stopWordSet;
	
	public boolean contains(String word)
	{
		return stopWordSet.contains(word);
	}
	
	public void addStemmedWords()
	{
		Stemmer stemmer=new Stemmer();
		HashSet<String> stemmedWordSet=new HashSet<String>();
		for (String word : stopWordSet)
		{
			stemmedWordSet.add(stemmer.stem(word));
		}
		for (String word : stemmedWordSet)
		{
			stopWordSet.add(word);
		}
	}
	
	public void addWords(String word)
	{
		stopWordSet.add(word);
	}
	
	public StopList() throws Exception
	{
		this(PreprocessCfg.stopListFileName);
	}
	
	public StopList(String dictFileName) throws Exception
	{
		stopWordSet=new HashSet<String>();
		BufferedReader br=new BufferedReader(new FileReader(dictFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			stopWordSet.add(line);
		}
		br.close();
	}
}
