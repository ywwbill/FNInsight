package lda.slda;

import cc.mallet.optimize.Optimizable.ByGradientValue;
import utility.MathUtil;

public class LexSLDAFunction implements ByGradientValue
{
	public double parameters[];
	public LexSLDA slda;
	
	public LexSLDAFunction(LexSLDA sldaInst)
	{
		this.slda=sldaInst;
		parameters=new double[slda.param.numTopics+slda.param.numVocab];
		for (int topic=0; topic<slda.param.numTopics; topic++)
		{
			parameters[topic]=slda.eta[topic];
		}
		for (int vocab=0; vocab<slda.param.numVocab; vocab++)
		{
			parameters[slda.param.numTopics+vocab]=slda.tau[vocab];
		}
	}
	
	public double getValue()
	{
		double value=0.0;
		for (int doc=0; doc<slda.numDocs; doc++)
		{
			if (slda.labels.get(doc)==-1) continue;
			if (slda.labels.get(doc)==1)
			{
				value+=Math.log(MathUtil.sigmoid(computeWeight(doc)));
			}
			else
			{
				value+=Math.log(1.0-MathUtil.sigmoid(computeWeight(doc)));
			}
//			value-=MathUtil.sqr(slda.labels.get(doc)-computeWeight(doc)/slda.param.nu)/2.0;
		}
		for (int i=0; i<parameters.length; i++)
		{
			value-=MathUtil.sqr(parameters[i]/slda.param.nu)/2.0;
		}
		return value;
	}
	
	public void getValueGradient(double gradient[])
	{
		for (int i=0; i<gradient.length; i++)
		{
			gradient[i]=0.0;
		}
		for (int doc=0; doc<slda.numDocs; doc++)
		{
			if (slda.labels.get(doc)==-1) continue;
			double weight=computeWeight(doc);
			for (int topic=0; topic<slda.param.numTopics; topic++)
			{
				double zdk=slda.corpus.get(doc).topicCounts[topic]/slda.corpus.get(doc).docLength();
				if (slda.labels.get(doc)==1)
				{
					gradient[topic]+=zdk*Math.exp(-weight)/(1.0+Math.exp(-weight));
				}
				else
				{
					gradient[topic]-=zdk/(1.0+Math.exp(-weight));
				}
			}
			for (int vocab : slda.wordCount.get(doc).keySet())
			{
				double wdk=slda.wordCount.get(doc).get(vocab)/slda.corpus.get(doc).docLength();
				if (slda.labels.get(doc)==1)
				{
					gradient[slda.param.numTopics+vocab]+=wdk*Math.exp(-weight)/(1.0+Math.exp(-weight));
				}
				else
				{
					gradient[slda.param.numTopics+vocab]-=wdk/(1.0+Math.exp(-weight));
				}
			}
//			for (int topic=0; topic<slda.param.numTopics; topic++)
//			{
//				gradient[topic]+=(slda.labels.get(doc)-weight)*slda.corpus.get(doc).topicCounts[topic]/
//						slda.corpus.get(doc).docLength()/MathUtil.sqr(slda.param.nu);
//			}
//			for (int vocab : slda.wordCount.get(doc).keySet())
//			{
//				gradient[slda.param.numTopics+vocab]+=(slda.labels.get(doc)-weight)*slda.wordCount.get(doc).get(vocab)/
//						slda.corpus.get(doc).docLength()/MathUtil.sqr(slda.param.nu);
//			}
		}
		for (int i=0; i<gradient.length; i++)
		{
			gradient[i]-=parameters[i]/(2.0*MathUtil.sqr(slda.param.nu));
		}
	}
	
	public double computeWeight(int doc)
	{
		double weight=0.0;
		for (int topic=0; topic<slda.param.numTopics; topic++)
		{
			weight+=parameters[topic]*slda.corpus.get(doc).topicCounts[topic]/slda.corpus.get(doc).docLength();
		}
		for (int vocab : slda.wordCount.get(doc).keySet())
		{
			weight+=parameters[slda.param.numTopics+vocab]*slda.wordCount.get(doc).get(vocab)/
					slda.corpus.get(doc).docLength();
		}
		return weight;
	}
	
	public int getNumParameters()
	{
		return parameters.length;
	}
	
	public double getParameter(int i)
	{
		return parameters[i];
	}
	
	public void getParameters(double buffer[])
	{
		for (int i=0; i<parameters.length; i++)
		{
			buffer[i]=parameters[i];
		}
	}
	
	public void setParameter(int i, double r)
	{
		parameters[i]=r;
	}
	
	public void setParameters(double newParameters[])
	{
		for (int i=0; i<parameters.length; i++)
		{
			parameters[i]=newParameters[i];
		}
	}
}
