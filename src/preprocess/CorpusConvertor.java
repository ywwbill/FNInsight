package preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;

public class CorpusConvertor
{
	public void collectVocab(String corpusFileName, String vocabFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(corpusFileName));
		String line,seg[];
		HashSet<String> vocab=new HashSet<String>();
		ArrayList<String> vocabList=new ArrayList<String>();
		while ((line=br.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0 || vocab.contains(seg[i])) continue;
				vocab.add(seg[i]);
				vocabList.add(seg[i]);
			}
		}
		br.close();
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(vocabFileName));
		for (String word : vocabList)
		{
			bw.write(word);
			bw.newLine();
		}
		bw.close();
	}
	
	public void word2Index(String vocabFileName, String wordCorpusFileName,
			String indexCorpusFileName) throws Exception
	{
		BufferedReader brVocab=new BufferedReader(new FileReader(vocabFileName));
		HashMap<String, Integer> vocab2id=new HashMap<String, Integer>();
		String line;
		while ((line=brVocab.readLine())!=null)
		{
			vocab2id.put(line, vocab2id.size());
		}
		brVocab.close();
		
		int count[]=new int[vocab2id.size()],len;
		BufferedReader br=new BufferedReader(new FileReader(wordCorpusFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(indexCorpusFileName));
		String seg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<vocab2id.size(); i++)
			{
				count[i]=0;
			}
			len=0;
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0 || !vocab2id.containsKey(seg[i])) continue;
				count[vocab2id.get(seg[i])]++;
				len++;
			}
			bw.write(len+"");
			for (int i=0; i<vocab2id.size(); i++)
			{
				if (count[i]==0) continue;
				bw.write(" "+i+":"+count[i]);
			}
			bw.newLine();
		}
		br.close();
		bw.close();
	}
	
	public void index2Word(String vocabFileName, String indexCorpusFileName,
			String wordCorpusFileName) throws Exception
	{
		BufferedReader brVocab=new BufferedReader(new FileReader(vocabFileName));
		String line;
		ArrayList<String> vocab=new ArrayList<String>();
		while ((line=brVocab.readLine())!=null)
		{
			vocab.add(line);
		}
		brVocab.close();
		
		BufferedReader br=new BufferedReader(new FileReader(indexCorpusFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(wordCorpusFileName));
		String seg[],segseg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=1; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				segseg=seg[i].split(":");
				if (segseg.length!=2) continue;
				int word=Integer.valueOf(segseg[0]);
				int count=Integer.valueOf(segseg[1]);
				if (word<0 || word>=vocab.size()) continue;
				for (int j=0; j<count; j++)
				{
					bw.write(vocab.get(word)+" ");
				}
			}
			bw.newLine();
		}
		br.close();
		bw.close();
	}
}
