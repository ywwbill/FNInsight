package fn.insight3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import fn.FNCfg;
import preprocess.StopList;

public class GramCounter
{
	public static final int DEMOCRATIC=0;
	public static final int REPUBLICAN=1;
	
	public ArrayList<Integer> primaryParty;
	
	public StopList stoplist;
	
	public void readPrimaryParty(String primaryPartyFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(primaryPartyFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			primaryParty.add(Integer.valueOf(line));
		}
		br.close();
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
	
	public void countUnigrams(String documentFileName, String wordFileName, double probThresh, double ratioThresh) throws Exception
	{
		HashMap<String, Integer> demCounter=new HashMap<String, Integer>();
		HashMap<String, Integer> repCounter=new HashMap<String, Integer>();
		BufferedReader br=new BufferedReader(new FileReader(documentFileName));
		String line,seg[];
		int numDemWords=0,numRepWords=0;
		for (int i=0; i<primaryParty.size(); i++)
		{
			line=br.readLine();
			if (primaryParty.get(i)==-1) continue;
			seg=line.split(" ");
			for (int j=0; j<seg.length; j++)
			{
				if (seg[j].length()==0) continue;
				if (stoplist.contains(seg[j]) || hasNonLetter(seg[j])) continue;
				if (primaryParty.get(i)==DEMOCRATIC)
				{
					if (!demCounter.containsKey(seg[j]))
					{
						demCounter.put(seg[j], 0);
					}
					demCounter.put(seg[j], demCounter.get(seg[j])+1);
					numDemWords++;
				}
				else
				{
					if (!repCounter.containsKey(seg[j]))
					{
						repCounter.put(seg[j], 0);
					}
					repCounter.put(seg[j], repCounter.get(seg[j])+1);
					numRepWords++;
				}
			}
		}
		br.close();
		
		HashMap<String, Double> demProb=new HashMap<String, Double>();
		for (String word : demCounter.keySet())
		{
			demProb.put(word, (double)demCounter.get(word)/(double)numDemWords);
		}
		HashMap<String, Double> repProb=new HashMap<String, Double>();
		for (String word : repCounter.keySet())
		{
			repProb.put(word, (double)repCounter.get(word)/(double)numRepWords);
		}
		
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(wordFileName));
		bw.write("Democratic");
		bw.newLine();
		for (String word : demProb.keySet())
		{
			if (demProb.get(word)<probThresh) continue;
			if (!repProb.containsKey(word)) continue;
			if (repProb.get(word)<probThresh) continue;
			if (demProb.get(word)/repProb.get(word)>ratioThresh)
			{
				bw.write(word);
				bw.newLine();
			}
		}
		bw.newLine();
		bw.write("Republican");
		bw.newLine();
		for (String word : repProb.keySet())
		{
			if (repProb.get(word)<probThresh) continue;
			if (!demProb.containsKey(word)) continue;
			if (demProb.get(word)<probThresh) continue;
			if (repProb.get(word)/demProb.get(word)>ratioThresh)
			{
				bw.write(word);
				bw.newLine();
			}
		}
		bw.close();
	}
	
	public GramCounter() throws Exception
	{
		primaryParty=new ArrayList<Integer>();
		stoplist=new StopList();
	}
	
	public static void main(String args[]) throws Exception
	{
		GramCounter counter=new GramCounter();
		counter.readPrimaryParty(FNCfg.in3PrimaryFileName);
		counter.countUnigrams(FNCfg.tempPath+"document-lemmatized", FNCfg.in3WordFileName, 0.00001, 4.0);
	}
}
