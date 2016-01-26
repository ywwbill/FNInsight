package fn.insight2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import cc.mallet.util.Randoms;
import fn.FNCfg;
import utility.MathUtil;
import cc.mallet.optimize.LimitedMemoryBFGS;

public class IPM
{
	public ArrayList<Integer> labels;
	public int numBills;
	
	public ArrayList<String> voterNames;
	public int numVoters;
	
	public double a[],b[],x[],nu;
	
	public ArrayList<HashMap<Integer, Integer>> votes;
	
	public static Randoms randoms;
	
	public void readLabels(String labelFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(labelFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			labels.add(Integer.valueOf(line));
		}
		br.close();
		numBills=labels.size();
		
		a=new double[numBills];
		b=new double[numBills];
		for (int bill=0; bill<numBills; bill++)
		{
			a[bill]=randoms.nextGaussian();
			b[bill]=randoms.nextGaussian();
		}
	}
	
	public void readVoters(String voterFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(voterFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			voterNames.add(line);
		}
		br.close();
		numVoters=voterNames.size();
		
		x=new double[numVoters];
		for (int voter=0; voter<numVoters; voter++)
		{
			x[voter]=randoms.nextGaussian();
		}
	}
	
	public void readVotes(String voteFileName) throws Exception
	{
		for (int bill=0; bill<numBills; bill++)
		{
			votes.add(new HashMap<Integer, Integer>());
		}
		
		BufferedReader br=new BufferedReader(new FileReader(voteFileName));
		String line,seg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split("\t");
			if (seg.length!=3) continue;
			int bill=Integer.valueOf(seg[0]);
			int voter=Integer.valueOf(seg[1]);
			int vote=Integer.valueOf(seg[2]);
			votes.get(bill).put(voter, vote);
		}
		br.close();
	}
	
	public void computeValue()
	{
		double value=0.0;
		for (int bill=0; bill<numBills; bill++)
		{
			for (int voter : votes.get(bill).keySet())
			{
				double prob=MathUtil.sigmoid(computeWeight(bill, voter));
				value+=(votes.get(bill).get(voter)==1? Math.log(prob) : Math.log(1.0-prob));
			}
		}
		System.out.println(value);
	}
	
	public double computeWeight(int bill, int voter)
	{
		return a[bill]*x[voter]+b[bill];
	}
	
	public void optimize()
	{
		computeValue();
		
		IPMFunction optimizable=new IPMFunction(this);
		LimitedMemoryBFGS lbfgs=new LimitedMemoryBFGS(optimizable);
		lbfgs.optimize();
		for (int bill=0; bill<numBills; bill++)
		{
			a[bill]=optimizable.parameters[bill];
			b[bill]=optimizable.parameters[bill+numBills];
		}
		for (int voter=0; voter<numVoters; voter++)
		{
			x[voter]=optimizable.parameters[voter+numBills*2];
		}
		
		computeValue();
	}
	
	public void writeResults(String aFileName, String bFileName, String xFileName) throws Exception
	{
		BufferedWriter bwa=new BufferedWriter(new FileWriter(aFileName));
		for (int bill=0; bill<numBills; bill++)
		{
			bwa.write(a[bill]+"");
			bwa.newLine();
		}
		bwa.close();
		
		BufferedWriter bwb=new BufferedWriter(new FileWriter(bFileName));
		for (int bill=0; bill<numBills; bill++)
		{
			bwb.write(b[bill]+"");
			bwb.newLine();
		}
		bwb.close();
		
		BufferedWriter bwx=new BufferedWriter(new FileWriter(xFileName));
		for (int voter=0; voter<numVoters; voter++)
		{
			bwx.write(x[voter]+"");
			bwx.newLine();
		}
		bwx.close();
	}
	
	static
	{
		randoms=new Randoms();
	}
	
	public IPM(double nu)
	{
		labels=new ArrayList<Integer>();
		voterNames=new ArrayList<String>();
		votes=new ArrayList<HashMap<Integer, Integer>>();
		this.nu=nu;
	}
	
	public static void main(String args[]) throws Exception
	{
		IPM ipm=new IPM(1.0);
		ipm.readLabels(FNCfg.in2LabelFileName);
		ipm.readVoters(FNCfg.in2VoterFileName);
		ipm.readVotes(FNCfg.in2VoteFileName);
		ipm.optimize();
		ipm.writeResults(FNCfg.in2aFileName, FNCfg.in2bFileName, FNCfg.in2xFileName);
	}
}
