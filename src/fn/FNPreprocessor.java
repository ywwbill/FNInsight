package fn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;

import preprocess.Tokenizer;
import preprocess.POSTagger;
import preprocess.Lemmatizer;
import preprocess.StopList;
import preprocess.CorpusConvertor;

public class FNPreprocessor
{
	public void tokenize(String srcFileName, String destFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		Tokenizer tokenizer=new Tokenizer();
		String line,seg[];
		int total=0;
		while ((line=br.readLine())!=null)
		{
			seg=tokenizer.tokenize(line);
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				bw.write(seg[i]+" ");
			}
			bw.newLine();
			total++;
			if (total%100==0)
			{
				System.out.println(total);
			}
		}
		br.close();
		bw.close();
	}
	
	public void postag(String srcFileName, String destFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		String line,sentences[],pos[],sentence[];
		POSTagger tagger=new POSTagger();
		int total=0;
		while ((line=br.readLine())!=null)
		{
			sentences=line.split(" ");
			int prev=-1;
			for (int k=0; k<sentences.length; k++)
			{
				if (!sentences[k].equals(".")) continue;
				sentence=new String[k-prev];
				for (int j=prev+1; j<=k; j++)
				{
					sentence[j-prev-1]=sentences[j];
				}
				pos=tagger.tag(sentence);
				for (int j=0; j<sentence.length; j++)
				{
					if (sentence[j].length()==0) continue;
					bw.write(sentence[j]+"#"+pos[j]+" ");
				}
				prev=k;
			}
			if (prev!=sentences.length-1)
			{
				sentence=new String[sentences.length-1-prev];
				for (int j=prev+1; j<sentences.length; j++)
				{
					sentence[j-prev-1]=sentences[j];
				}
				pos=tagger.tag(sentence);
				for (int j=0; j<sentence.length; j++)
				{
					if (sentence[j].length()==0) continue;
					bw.write(sentence[j]+"#"+pos[j]+" ");
				}
			}
			bw.newLine();
			bw.flush();
			total++;
			if (total%100==0)
			{
				System.out.println(total);
			}
		}
		br.close();
		bw.close();
	}
	
	public void lemmatize(String srcFileName, String destFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		String line,sentence[],wordPos[];
		Lemmatizer lemmatizer=new Lemmatizer();
		int total=0;
		while ((line=br.readLine())!=null)
		{
			sentence=line.split(" ");
			for (int i=0; i<sentence.length; i++)
			{
				if (sentence[i].length()==0) continue;
				wordPos=sentence[i].split("#");
				if (wordPos.length!=2) continue;
				bw.write(lemmatizer.lemmatize(wordPos[0], wordPos[1])+" ");
			}
			bw.newLine();
			bw.flush();
			total++;
			if (total%100==0)
			{
				System.out.println(total);
			}
		}
		br.close();
		bw.close();
	}
	
	public void addBigrams(String srcFileName, String destFileName, int threshold) throws Exception
	{
		BufferedReader br1=new BufferedReader(new FileReader(srcFileName));
		String line,seg[];
		StopList stopList=new StopList();
		HashMap<String, Integer> bigramCounter=new HashMap<String, Integer>();
		while ((line=br1.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length-1; i++)
			{
				if (seg[i].length()==0 || seg[i+1].length()==0) continue;
				if (hasNonLetter(seg[i]) || hasNonLetter(seg[i+1])) continue;
				if (stopList.contains(seg[i]) || stopList.contains(seg[i+1])) continue;
				String bigram=seg[i]+"_"+seg[i+1];
				if (!bigramCounter.containsKey(bigram))
				{
					bigramCounter.put(bigram, 0);
				}
				bigramCounter.put(bigram, bigramCounter.get(bigram)+1);
			}
		}
		br1.close();
		
		HashSet<String> bigramSet=new HashSet<String>();
		for (String bigram : bigramCounter.keySet())
		{
			if (bigramCounter.get(bigram)>=threshold)
			{
				bigramSet.add(bigram);
			}
		}
		
		BufferedReader br2=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		while ((line=br2.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; )
			{
				if (i+1<seg.length && bigramSet.contains(seg[i]+"_"+seg[i+1]))
				{
					bw.write(seg[i]+"_"+seg[i+1]+" ");
					i+=2;
				}
				else
				{
					bw.write(seg[i]+" ");
					i++;
				}
			}
			bw.newLine();
		}
		br2.close();
		bw.close();
	}
	
	public boolean hasNonLetter(String s)
	{
		for (int i=0; i<s.length(); i++)
		{
			if (!Character.isLetter(s.charAt(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hasLetter(String s)
	{
		for (int i=0; i<s.length(); i++)
		{
			if (Character.isLetter(s.charAt(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public void removeStopWords(String srcFileName, String destFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		String line,seg[];
		StopList stopList=new StopList();
		for (int i=1; i<=50; i++)
		{
			stopList.addWords(intToRoman(i));
		}
		while ((line=br.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				if (!stopList.contains(seg[i]) && !hasNonLetter(seg[i]))
				{
					bw.write(seg[i]+" ");
				}
			}
			bw.newLine();
		}
		br.close();
		bw.close();
	}
	
	public String intToRoman(int num)
	{
		String roman="";
		
		if (num>=50)
		{
			roman+="L";
			num-=50;
		}
		
		if (num>=40)
		{
			roman+="XL";
			num-=40;
		}
		
		while (num>=10)
		{
			roman+="X";
			num-=10;
		}
		
		if (num>=9)
		{
			roman+="IX";
			num-=9;
		}
		
		if (num>=5)
		{
			roman+="V";
			num-=5;
		}
		
		if (num>=4)
		{
			roman+="IV";
			num-=4;
		}
		
		while (num>=1)
		{
			roman+="I";
			num--;
		}
		
		return roman.toLowerCase();
	}
	
	public void removeLowFreqWords(String srcFileName, String destFileName, int threshold) throws Exception
	{
		BufferedReader br1=new BufferedReader(new FileReader(srcFileName));
		String line,seg[];
		HashSet<String> docSet=new HashSet<String>();
		HashMap<String, Integer> unigramCounter=new HashMap<String, Integer>();
		while ((line=br1.readLine())!=null)
		{
			seg=line.split(" ");
			docSet.clear();
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				docSet.add(seg[i]);
			}
			for (String word : docSet)
			{
				if (!unigramCounter.containsKey(word))
				{
					unigramCounter.put(word, 0);
				}
				unigramCounter.put(word, unigramCounter.get(word)+1);
			}
		}
		br1.close();
		
		BufferedReader br2=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		while ((line=br2.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				if (unigramCounter.get(seg[i])>=threshold)
				{
					bw.write(seg[i]+" ");
				}
			}
			bw.newLine();
		}
		br2.close();
		bw.close();
	}
	
	public void collectPassed(String srcFileName, String destFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			if (line.equals("true"))
			{
				bw.write("1");
			}
			else
			{
				if (line.equals("false"))
				{
					bw.write("0");
				}
				else
				{
					bw.write("-1");
				}
			}
			bw.newLine();
		}
		br.close();
		bw.close();
	}
	
	public void removeHighFreqWords(String srcFileName, String destFileName, int threshold) throws Exception
	{
		BufferedReader br1=new BufferedReader(new FileReader(srcFileName));
		String line,seg[];
		HashMap<String, Integer> wordCount=new HashMap<String, Integer>();
		while ((line=br1.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				if (!wordCount.containsKey(seg[i]))
				{
					wordCount.put(seg[i], 0);
				}
				wordCount.put(seg[i], wordCount.get(seg[i])+1);
			}
		}
		br1.close();
		
		BufferedReader br2=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		while ((line=br2.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0 || wordCount.get(seg[i])>threshold) continue;
				bw.write(seg[i]+" ");
			}
			bw.newLine();
		}
		br2.close();
		bw.close();
	}
	
	public static void main(String args[]) throws Exception
	{
		FNPreprocessor preprocessor=new FNPreprocessor();
//		preprocessor.tokenize(FNCfg.documentFileName, FNCfg.tempPath+"document-tokenized");
//		preprocessor.postag(FNCfg.tempPath+"document-tokenized", FNCfg.tempPath+"document-postag");
//		preprocessor.lemmatize(FNCfg.tempPath+"document-postag", FNCfg.tempPath+"document-lemmatized");
//		preprocessor.addBigrams(FNCfg.tempPath+"document-lemmatized", FNCfg.tempPath+"document-bigram", 750);
//		preprocessor.removeStopWords(FNCfg.tempPath+"document-bigram", FNCfg.tempPath+"document-no-stopwords");
//		preprocessor.removeLowFreqWords(FNCfg.tempPath+"document-no-stopwords", FNCfg.tempPath+"document-cleaned", 750);
		preprocessor.collectPassed(FNCfg.passedFileName, FNCfg.tempPath+"label");
//		preprocessor.removeHighFreqWords(FNCfg.tempPath+"document-cleaned",
//				FNCfg.tempPath+"document-no-high-freq", 14769);
		
		CorpusConvertor convertor=new CorpusConvertor();
		convertor.collectVocab(FNCfg.tempPath+"document-no-high-freq", FNCfg.tempPath+"vocab");
		convertor.word2Index(FNCfg.tempPath+"vocab", FNCfg.tempPath+"document-cleaned",
				FNCfg.tempPath+"document-indexed");
	}
}
