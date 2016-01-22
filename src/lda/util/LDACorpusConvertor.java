package lda.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import lda.LDACfg;

public class LDACorpusConvertor
{
	public HashMap<String, Integer> vocab2id;
	public ArrayList<String> id2vocab;
	
	public void readCorpus(String corpusFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(corpusFileName));
		String line,seg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				if (!vocab2id.containsKey(seg[i]))
				{
					vocab2id.put(seg[i], vocab2id.size());
					id2vocab.add(seg[i]);
				}
			}
		}
		br.close();
	}
	
	public void readVocab(String vocabFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(vocabFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			vocab2id.put(line, vocab2id.size());
			id2vocab.add(line);
		}
		br.close();
	}
	
	public void writeVocab(String vocabFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(vocabFileName));
		for (String vocab : id2vocab)
		{
			bw.write(vocab);
			bw.newLine();
		}
		bw.close();
	}
	
	public void convertCorpus(String srcCorpusFileName, String destCorpusFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(srcCorpusFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destCorpusFileName));
		String line,seg[];
		int len;
		int wordCount[]=new int[id2vocab.size()];
		while ((line=br.readLine())!=null)
		{
			seg=line.split(" ");
			len=0;
			for (int i=0; i<wordCount.length; i++)
			{
				wordCount[i]=0;
			}
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0 || !vocab2id.containsKey(seg[i])) continue;
				wordCount[vocab2id.get(seg[i])]++;
				len++;
			}
			
			bw.write(len+"");
			for (int i=0; i<wordCount.length; i++)
			{
				if (wordCount[i]>0)
				{
					bw.write(" "+i+":"+wordCount[i]);
				}
			}
			bw.newLine();
		}
		br.close();
		bw.close();
	}
	
	public LDACorpusConvertor()
	{
		vocab2id=new HashMap<String, Integer>();
		id2vocab=new ArrayList<String>();
	}
	
	public static void main(String args[]) throws Exception
	{
		LDACorpusConvertor convertor=new LDACorpusConvertor();
//		convertor.readCorpus(LDACfg.rawCorpusFileName);
//		convertor.writeVocab(LDACfg.vocabFileName);
		convertor.readVocab(LDACfg.vocabFileName);
//		convertor.convertCorpus(LDACfg.rawCorpusFileName, LDACfg.corpusFileName);
		for (int fold=0; fold<LDACfg.numFold; fold++)
		{
			convertor.convertCorpus(LDACfg.getTestCorpusFileName(fold), LDACfg.getTestCorpusFileName(fold)+"2");
		}
	}
}
