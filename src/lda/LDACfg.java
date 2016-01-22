package lda;

import java.io.File;

import cfg.Cfg;

public class LDACfg
{	
	public static boolean crossValid=false;
	public static int numFold=5;
	
	public static int numTrainIters=100;
	public static int numTestIters=20;
	public static boolean SLModel=true;
	
	public static String dataPath=Cfg.dataPath+"lda"+File.separator;
	public static String cvPath=dataPath+"cv"+File.separator;
	public static String modelPath=dataPath+"model"+File.separator;
	public static String blockPath=dataPath+"block"+File.separator;
	public static String synPath=dataPath+"synthetic"+File.separator;
	
	public static String vocabFileName=dataPath+"vocab.txt";
	public static String rawCorpusFileName=dataPath+"raw_corpus.txt";
	public static String corpusFileName=dataPath+"corpus.txt";
	public static String trainCorpusFileName=dataPath+"train_corpus.txt";
	public static String testCorpusFileName=dataPath+"test_corpus.txt";
	public static String trainGraphFileName=dataPath+"train_graph.txt";
	public static String testGraphFileName=dataPath+"test_graph.txt";
	
	//input
	public static String getTrainCorpusFileName(int fold)
	{
		return cvPath+"train_corpus_cv"+fold+".txt";
	}
	
	public static String getTestCorpusFileName(int fold)
	{
		return cvPath+"test_corpus_cv"+fold+".txt";
	}
	
	public static String getTrainGraphFileName(int fold)
	{
		return cvPath+"train_graph_cv"+fold+".txt";
	}
	
	public static String getTestGraphFileName(int fold)
	{
		return cvPath+"test_graph_cv"+fold+".txt";
	}
	
	//output
	public static String getModelFileName(String modelName)
	{
		return modelPath+modelName+"_model.txt";
	}
	
	public static String getModelFileName(String modelName, int fold)
	{
		return modelPath+modelName+"_model_cv"+fold+".txt";
	}
	
	public static String getBlockFileName(String modelName)
	{
		return blockPath+modelName+"_blocks.txt";
	}
	
	public static String getBlockFileName(String modelName, int fold)
	{
		return blockPath+modelName+"_blocks_cv"+fold+".txt";
	}
	
	//synthetic data
	public static String getSynParamFileName(String modelName)
	{
		return synPath+modelName+"_syn_param.txt";
	}
	
	public static String getSynModelFileName(String modelName)
	{
		return synPath+modelName+"_syn_model.txt";
	}
	
	public static String getSynCorpusFileName(String modelName)
	{
		return synPath+modelName+"_syn_corpus.txt";
	}
	
	public static String getSynGraphFileName(String modelName)
	{
		return synPath+modelName+"_syn_graph.txt";
	}
	
	public static String getSynBlockGraphFileName(String modelName)
	{
		return synPath+modelName+"_syn_block_graph.txt";
	}
}
