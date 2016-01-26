package fn.insight2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import fn.FNCfg;

public class DataCollector
{
	public static final int DEMOCRATIC=0;
	public static final int REPUBLICAN=1;
	public static final int DUAL=2;
	
	public ArrayList<String> voterName;
	public ArrayList<Double> idealPoint;
	public HashMap<String, Integer> party;
	
	public void readVoters(String voterFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(voterFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			voterName.add(line);
		}
		br.close();
	}
	
	public void readIdealPoint(String ipFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(ipFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			idealPoint.add(Double.valueOf(line));
		}
		br.close();
		assert(idealPoint.size()==voterName.size());
	}
	
	public void readParty(String partyFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(partyFileName));
		String line,seg[],segseg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split("#");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				segseg=seg[i].split("\\$");
				assert(segseg.length==3);
				if (segseg.length!=3) continue;
				if (!party.containsKey(segseg[2]))
				{
					if (segseg[0].indexOf("Democratic")!=-1)
					{
						party.put(segseg[2], DEMOCRATIC);
					}
					if (segseg[0].indexOf("Republican")!=-1)
					{
						party.put(segseg[2], REPUBLICAN);
					}
				}
				else
				{
					if (segseg[0].indexOf("Democratic")!=-1)
					{
						if (party.get(segseg[2])!=DEMOCRATIC)
						{
							party.put(segseg[2], DUAL);
						}
					}
					if (segseg[0].indexOf("Republican")!=-1)
					{
						if (party.get(segseg[2])!=REPUBLICAN)
						{
							party.put(segseg[2], DUAL);
						}
					}
				}
			}
		}
		br.close();
	}
	
	public void writeInfo(String infoFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(infoFileName));
		for (int i=0; i<voterName.size(); i++)
		{
			bw.write(voterName.get(i)+"\t"+idealPoint.get(i)+"\t");
			if (party.containsKey(voterName.get(i)))
			{
				if (party.get(voterName.get(i))==DEMOCRATIC)
				{
					bw.write("DEMOCRATIC");
				}
				if (party.get(voterName.get(i))==REPUBLICAN)
				{
					bw.write("REPUBLICAN");
				}
				if (party.get(voterName.get(i))==DUAL)
				{
					bw.write("DUAL");
				}
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public DataCollector()
	{
		voterName=new ArrayList<String>();
		idealPoint=new ArrayList<Double>();
		party=new HashMap<String, Integer>();
	}
	
	public static void main(String args[]) throws Exception
	{
		DataCollector collector=new DataCollector();
		collector.readVoters(FNCfg.in2VoterFileName);
		collector.readIdealPoint(FNCfg.in2xFileName);
		collector.readParty(FNCfg.sponsorFileName);
		collector.writeInfo(FNCfg.in2Path+"info");
	}
}
