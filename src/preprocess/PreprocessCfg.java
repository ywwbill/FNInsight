package preprocess;

import java.io.File;

import cfg.Cfg;

public class PreprocessCfg
{
	public static String dictPath=Cfg.dataPath+"dict"+File.separator;
	
	public static String lemmaDictFileName=dictPath+"en-lemmatizer.txt";
	public static String tokenModelFileName=dictPath+"en-token.bin";
	public static String posModelFileName=dictPath+"en-pos-maxent.bin";
	public static String stopListFileName=dictPath+"stoplist.txt";
}
