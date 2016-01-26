package fn.insight1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import fn.FNCfg;

public class TopicVocabCollector
{
	public ArrayList<String> topics;
	public ArrayList<Double> topicScores;
	public ArrayList<String> vocab;
	public ArrayList<Double> vocabScores;
	
	public void readTopics(String topicFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(topicFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			topics.add(line);
		}
		br.close();
	}
	
	public void readVocab(String vocabFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(vocabFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			vocab.add(line);
		}
		br.close();
	}
	
	public void readScores(String scoreFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(scoreFileName));
		String line;
		for (int i=0; i<topics.size(); i++)
		{
			line=br.readLine();
		}
		for (int i=0; i<topics.size(); i++)
		{
			line=br.readLine();
			topicScores.add(Double.valueOf(line));
		}
		for (int i=0; i<vocab.size(); i++)
		{
			line=br.readLine();
			vocabScores.add(Double.valueOf(line));
		}
		br.close();
	}
	
	public void writeTopics(String posTopicFileName, String negTopicFileName) throws Exception
	{
		BufferedWriter bwPos=new BufferedWriter(new FileWriter(posTopicFileName));
		BufferedWriter bwNeg=new BufferedWriter(new FileWriter(negTopicFileName));
		for (int i=0; i<topics.size(); i++)
		{
			if (topicScores.get(i)>1.0)
			{
				bwPos.write(topics.get(i)+"\t"+topicScores.get(i));
				bwPos.newLine();
			}
			if (topicScores.get(i)<-1.0)
			{
				bwNeg.write(topics.get(i)+"\t"+topicScores.get(i));
				bwNeg.newLine();
			}
		}
		bwPos.close();
		bwNeg.close();
	}
	
	public void writeVocab(String posVocabFileName, String negVocabFileName) throws Exception
	{
		BufferedWriter bwPos=new BufferedWriter(new FileWriter(posVocabFileName));
		BufferedWriter bwNeg=new BufferedWriter(new FileWriter(negVocabFileName));
		for (int i=0; i<vocab.size(); i++)
		{
			if (vocabScores.get(i)>1.0)
			{
				bwPos.write(vocab.get(i)+"\t"+vocabScores.get(i));
				bwPos.newLine();
			}
			if (vocabScores.get(i)<-1.0)
			{
				bwNeg.write(vocab.get(i)+"\t"+vocabScores.get(i));
				bwNeg.newLine();
			}
		}
		bwPos.close();
		bwNeg.close();
	}
	
	public TopicVocabCollector()
	{
		topics=new ArrayList<String>();
		topicScores=new ArrayList<Double>();
		vocab=new ArrayList<String>();
		vocabScores=new ArrayList<Double>();
	}
	
	public static void main(String args[]) throws Exception
	{
		TopicVocabCollector collector=new TopicVocabCollector();
		collector.readTopics(FNCfg.in1ResultFileName);
		collector.readVocab(FNCfg.in1VocabFileName);
		collector.readScores(FNCfg.in1PhiFileName);
		collector.writeTopics(FNCfg.in1PosTopicFileName, FNCfg.in1NegTopicFileName);
		collector.writeVocab(FNCfg.in1PosVocabFileName, FNCfg.in1NegVocabFileName);
	}
}
