package fn.insight1;

import lda.LDAParam;
import lda.slda.LexSLDA;
import fn.FNCfg;

public class SLDAInsight
{
	public static void main(String args[]) throws Exception
	{
		LexSLDA slda=new LexSLDA(new LDAParam(FNCfg.in1VocabFileName));
		slda.readCorpus(FNCfg.in1CorpusFileName);
		slda.getWordCount();
		slda.readLabels(FNCfg.in1LabelFileName);
		slda.sample(100);
		slda.writeModel(FNCfg.in1PhiFileName);
		slda.writeResult(FNCfg.in1ResultFileName, 20);
		slda.writeTheta(FNCfg.in1ThetaFileName);
		slda.writeTopicCounts(FNCfg.in1TopicCountsFileName);
	}
}
