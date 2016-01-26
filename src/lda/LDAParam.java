package lda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LDAParam
{
	//for topic model
	public double alphaSum=1.0;
	public double _alphaSum=2.0;
	public double beta=0.1;
	public int numTopics=21;
	
	public boolean updateAlpha=false;
	public int showTopicInterval=10;
	public int updateAlphaInterval=10;
	
	public ArrayList<String> vocabulary;
	public int numVocab;
	
	//for hinge loss
	public double c=1.0;
	
	//for rtm
	public double nu=1.0;
	public int showPLRInterval=20;
	public boolean negEdge=true;
	public double negEdgeRatio=0.1;
	
	//for wsbm
	public boolean directed=false;
	public double a=1.0;
	public double b=1.0;
	public double gamma=1.0;
	public int numTrainBlocks=20;
	public int numTestBlocks=20;
	
	public LDAParam(String vocabFileName) throws Exception
	{
		vocabulary=new ArrayList<String>();
		BufferedReader br=new BufferedReader(new FileReader(vocabFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			vocabulary.add(line);
		}
		br.close();
		numVocab=vocabulary.size();
	}
}
