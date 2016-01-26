package fn.insight2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import fn.FNCfg;

public class VoterCounter
{
	public static final int DEMOCRATIC=0;
	public static final int REPUBLICAN=1;
	
	public ArrayList<String> names;
	public ArrayList<Double> idealPoint;
	public ArrayList<Integer> party;
	
	public void readInfo(String infoFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(infoFileName));
		String line,seg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split("\t");
			if (seg.length!=3) continue;
			if (seg[2].equals("DEMOCRATIC") || seg[2].equals("REPUBLICAN"))
			{
				names.add(seg[0]);
				idealPoint.add(Double.valueOf(seg[1]));
				party.add(seg[2].equals("DEMOCRATIC")? DEMOCRATIC : REPUBLICAN);
			}
		}
		br.close();
	}
	
	public void countVoters()
	{
		count(Integer.MIN_VALUE, -10);
		for (int i=-10; i<2; i++)
		{
			count(i, i+1);
		}
		count(2, Integer.MAX_VALUE);
	}
	
	public void count(int left, int right)
	{
		int numDemo=0,numRep=0;
		for (int i=0; i<idealPoint.size(); i++)
		{
			if (idealPoint.get(i)>left && idealPoint.get(i)<right)
			{
				if (party.get(i)==DEMOCRATIC)
				{
					numDemo++;
				}
				else
				{
					numRep++;
				}
			}
		}
		
		if (numDemo+numRep==0) return;
		System.out.println("("+left+","+right+"): Demo "+numDemo+" Rep: "+numRep);
	}
	
	public VoterCounter()
	{
		names=new ArrayList<String>();
		idealPoint=new ArrayList<Double>();
		party=new ArrayList<Integer>();
	}
	
	public static void main(String args[]) throws Exception
	{
		VoterCounter counter=new VoterCounter();
		counter.readInfo(FNCfg.in2Path+"info");
		counter.countVoters();
	}
}
