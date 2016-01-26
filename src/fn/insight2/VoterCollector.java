package fn.insight2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

import fn.FNCfg;

public class VoterCollector
{
	public ArrayList<String> voterList;
	public HashSet<String> voterSet;
	
	public void collectVoter(String voterFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(voterFileName));
		String line,seg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split("#");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				if (!voterSet.contains(seg[i]))
				{
					voterSet.add(seg[i]);
					voterList.add(seg[i]);
				}
			}
		}
		br.close();
	}
	
	public void writeVoter(String voterFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(voterFileName));
		for (String name : voterList)
		{
			bw.write(name);
			bw.newLine();
		}
		bw.close();
	}
	
	public VoterCollector()
	{
		voterList=new ArrayList<String>();
		voterSet=new HashSet<String>();
	}
	
	public static void main(String args[]) throws Exception
	{
		VoterCollector collector=new VoterCollector();
		collector.collectVoter(FNCfg.yesVoteFileName);
		collector.collectVoter(FNCfg.noVoteFileName);
		collector.writeVoter(FNCfg.in2VoterFileName);
	}
}
