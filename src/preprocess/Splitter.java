package preprocess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class Splitter
{
	public Random random;
	
	public void splitCV(int n, int numFolds, String splitFileName) throws Exception
	{
		int numExamples[]=new int[numFolds];
		for (int i=0; i<numFolds; i++)
		{
			numExamples[i]=n/numFolds;
		}
		for (int i=0; i<n%numFolds; i++)
		{
			numExamples[i]++;
		}
		
		int fold[]=new int[n],next;
		for (int i=0; i<n; i++)
		{
			fold[i]=-1;
		}
		for (int i=0; i<numFolds-1; i++)
		{
			for (int j=0; j<numExamples[i]; j++)
			{
				do
				{
					next=random.nextInt(n);
				}while (fold[next]!=-1);
				fold[next]=i;
			}
		}
		for (int i=0; i<n; i++)
		{
			fold[i]=(fold[i]==-1? numFolds-1 : fold[i]);
		}
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(splitFileName));
		for (int i=0; i<n; i++)
		{
			bw.write(fold[i]+"");
			bw.newLine();
		}
		bw.close();
	}
	
	public Splitter()
	{
		random=new Random();
	}
}
