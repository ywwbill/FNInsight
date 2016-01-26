package lda.slda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import lda.LDA;
import lda.LDAParam;
import lda.util.LDADoc;
import utility.MathUtil;
import utility.Util;
import cc.mallet.optimize.LimitedMemoryBFGS;

public class LexSLDA extends LDA
{
	public ArrayList<HashMap<Integer, Integer>> wordCount;
	
	public ArrayList<Integer> labels;
	
	public double eta[];
	public double tau[];
	public double tempScore;
	public double error;
	
	public void readLabels(String labelFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(labelFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			labels.add(Integer.valueOf(line));
		}
		br.close();
		assert(labels.size()==numDocs);
	}
	
	public void getWordCount()
	{
		for (int doc=0; doc<numDocs; doc++)
		{
			HashMap<Integer, Integer> tempCount=new HashMap<Integer, Integer>();
			for (int pos=0; pos<corpus.get(doc).docLength(); pos++)
			{
				int token=corpus.get(doc).getWord(pos);
				if (!tempCount.containsKey(token))
				{
					tempCount.put(token, 0);
				}
				tempCount.put(token, tempCount.get(token)+1);
			}
			wordCount.add(tempCount);
		}
	}
	
	public void sample(int numIters)
	{
		for (int iteration=1; iteration<=numIters; iteration++)
		{
			for (int doc=0; doc<numDocs; doc++)
			{
				sampleDoc(doc);
			}
			optimize();
			computeError();
			computeLogLikelihood();
			perplexity=Math.exp(-logLikelihood/numTestWords);
			Util.println("<"+iteration+">"+"\tLog-LLD: "+logLikelihood+"\tPPX: "+perplexity+"\tError: "+error);
			if (iteration%param.showTopicInterval==0 && type==TRAIN)
			{
				for (int topic=0; topic<param.numTopics; topic++)
				{
					Util.println(topWords(topic, 10));
				}
			}
		}
	}
	
	public void sampleDoc(int docIdx)
	{
		int word,oldTopic,newTopic;
		double topicScores[]=new double[param.numTopics];
		LDADoc doc=corpus.get(docIdx);
		
		int interval=getSampleInterval();
		for (int token=0; token<doc.docLength(); token+=interval)
		{
			word=doc.getWord(token);
			oldTopic=doc.getTopicAssign(token);
			if (topics.get(oldTopic).totalTokens==0) continue;
			
			doc.topicCounts[oldTopic]--;
			topics.get(oldTopic).totalTokens--;
			topics.get(oldTopic).vocabCounts[word]--;
			tempScore=computeWeight(docIdx);
			
			for (int topic=0; topic<param.numTopics; topic++)
			{
				topicScores[topic]=topicUpdating(docIdx, topic, word);
			}
			
			newTopic=MathUtil.selectDiscrete(topicScores);
			
			doc.setTopicAssign(token, newTopic);
			doc.topicCounts[newTopic]++;
			topics.get(newTopic).totalTokens++;
			topics.get(newTopic).vocabCounts[word]++;
		}
	}
	
	public double topicUpdating(int docIdx, int topic, int vocab)
	{
		double score=(alpha[topic]+corpus.get(docIdx).topicCounts[topic])*
				(param.beta+topics.get(topic).vocabCounts[vocab])/
				(param.beta*param.numVocab+topics.get(topic).totalTokens);
//		score*=Math.exp(-MathUtil.sqr(labels.get(docIdx)-tempScore-eta[topic]/corpus.get(docIdx).docLength())/
//				(2*param.nu*param.nu));
		if (labels.get(docIdx)==1)
		{
			score*=MathUtil.sigmoid(tempScore+eta[topic]/corpus.get(docIdx).docLength());
		}
		if (labels.get(docIdx)==0)
		{
			score*=1.0-MathUtil.sigmoid(tempScore+eta[topic]/corpus.get(docIdx).docLength());
		}
		return score;
	}
	
	public double computeWeight(int doc)
	{
		double weight=0.0;
		if (corpus.get(doc).docLength()==0) return weight;
		for (int topic=0; topic<param.numTopics; topic++)
		{
			weight+=eta[topic]*corpus.get(doc).topicCounts[topic]/corpus.get(doc).docLength();
		}
		for (int vocab : wordCount.get(doc).keySet())
		{
			weight+=tau[vocab]*wordCount.get(doc).get(vocab)/corpus.get(doc).docLength();
		}
		return weight;
	}
	
	public void optimize()
	{
		LexSLDAFunction optimizable=new LexSLDAFunction(this);
		LimitedMemoryBFGS lbfgs=new LimitedMemoryBFGS(optimizable);
		try
		{
			lbfgs.optimize();
		}
		catch (Exception e)
		{
			return;
		}
		for (int topic=0; topic<param.numTopics; topic++)
		{
			eta[topic]=optimizable.parameters[topic];
		}
		for (int vocab=0; vocab<param.numVocab; vocab++)
		{
			tau[vocab]=optimizable.parameters[param.numTopics+vocab];
		}
	}
	
	public void computeError()
	{
		error=0.0;
		int numVotes=0;
		for (int doc=0; doc<numDocs; doc++)
		{
			double prob=MathUtil.sigmoid(computeWeight(doc));
			if (labels.get(doc)==1)
			{
				error+=1.0-prob;
				numVotes++;
			}
			if (labels.get(doc)==0)
			{
				error+=prob;
				numVotes++;
			}
		}
		error/=(double)numVotes;
	}
	
	public void writeModel(String modelFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(modelFileName));
		Util.writeMatrix(bw, phi);
		Util.writeVector(bw, eta);
		Util.writeVector(bw, tau);
		bw.close();
	}
	
	public void getNumTestWords()
	{
		numTestWords=numWords;
	}
	
	public int getStartPos()
	{
		return 0;
	}
	
	public int getSampleSize(int docLength)
	{
		return docLength;
	}
	
	public int getSampleInterval()
	{
		return 1;
	}
	
	public void initVariables()
	{
		super.initVariables();
		labels=new ArrayList<Integer>();
		wordCount=new ArrayList<HashMap<Integer, Integer>>();
	}
	
	public LexSLDA(LDAParam param)
	{
		super(param);
		eta=new double[param.numTopics];
		for (int topic=0; topic<param.numTopics; topic++)
		{
			eta[topic]=randoms.nextGaussian(0.0, param.nu*param.nu);
		}
		tau=new double[param.numVocab];
		for (int vocab=0; vocab<param.numVocab; vocab++)
		{
			tau[vocab]=randoms.nextGaussian(0.0, param.nu*param.nu);
		}
	}
}
