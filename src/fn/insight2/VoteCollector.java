package fn.insight2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;

import fn.FNCfg;

public class VoteCollector
{
	public HashMap<String, Integer> voterId;
	public ArrayList<Integer> labels;
	
	public void readVoters(String voterFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(voterFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			voterId.put(line, voterId.size());
		}
		br.close();
	}
	
	public void readLabels(String labelFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(labelFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			labels.add(Integer.valueOf(line));
		}
		br.close();
	}
	
	public void collectVote(String yesVoteFileName, String noVoteFileName,
			String voteFileName, String newLabelFileName) throws Exception
	{
		BufferedWriter bwVote=new BufferedWriter(new FileWriter(voteFileName));
		BufferedWriter bwLabel=new BufferedWriter(new FileWriter(newLabelFileName));
		BufferedReader brYes=new BufferedReader(new FileReader(yesVoteFileName));
		BufferedReader brNo=new BufferedReader(new FileReader(noVoteFileName));
		String line1="",line2="",seg1[],seg2[];
		int k=0;
		for (int i=0; i<labels.size(); i++)
		{
			line1=brYes.readLine();
			line2=brNo.readLine();
			if (labels.get(i)==-1) continue;
			bwLabel.write(labels.get(i)+"");
			bwLabel.newLine();
			
			seg1=line1.split("#");
			for (int j=0; j<seg1.length; j++)
			{
				if (seg1[j].length()==0 && !voterId.containsKey(seg1[j])) continue;
				bwVote.write(k+"\t"+voterId.get(seg1[j])+"\t1");
				bwVote.newLine();
			}
			
			seg2=line2.split("#");
			for (int j=0; j<seg2.length; j++)
			{
				if (seg2[j].length()==0 || !voterId.containsKey(seg2[j])) continue;
				bwVote.write(k+"\t"+voterId.get(seg2[j])+"\t0");
				bwVote.newLine();
			}
			
			k++;
		}
		brYes.close();
		brNo.close();
		bwVote.close();
		bwLabel.close();
	}
	
	public VoteCollector()
	{
		voterId=new HashMap<String, Integer>();
		labels=new ArrayList<Integer>();
	}
	
	public static void main(String args[]) throws Exception
	{
		VoteCollector collector=new VoteCollector();
		collector.readVoters(FNCfg.in2VoterFileName);
		collector.readLabels(FNCfg.in2LabelFileName);
		collector.collectVote(FNCfg.yesVoteFileName, FNCfg.noVoteFileName,
				FNCfg.in2VoteFileName, FNCfg.in2NewLabelFileName);
	}
}
