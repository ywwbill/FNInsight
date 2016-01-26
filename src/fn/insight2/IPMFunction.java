package fn.insight2;

import cc.mallet.optimize.Optimizable.ByGradientValue;
import utility.MathUtil;

public class IPMFunction implements ByGradientValue
{
	public double parameters[];
	public IPM ipm;
	
	public IPMFunction(IPM ipmInst)
	{
		this.ipm=ipmInst;
		parameters=new double[ipm.numBills*2+ipm.numVoters];
		for (int bill=0; bill<ipm.numBills; bill++)
		{
			parameters[bill]=ipm.a[bill];
			parameters[bill+ipm.numBills]=ipm.b[bill];
		}
		for (int voter=0; voter<ipm.numVoters; voter++)
		{
			parameters[voter+ipm.numBills*2]=ipm.x[voter];
		}
	}
	
	public double getValue()
	{
		double value=0.0;
		for (int bill=0; bill<ipm.numBills; bill++)
		{
			for (int voter : ipm.votes.get(bill).keySet())
			{
				double prob=MathUtil.sigmoid(computeWeight(bill, voter));
				value+=(ipm.votes.get(bill).get(voter)==1? Math.log(prob) : Math.log(1.0-prob));
			}
		}
		for (int i=0; i<parameters.length; i++)
		{
			value-=MathUtil.sqr(parameters[i]/ipm.nu)/2.0;
		}
		return value;
	}
	
	public double computeWeight(int bill, int voter)
	{
		return parameters[bill]*parameters[voter+ipm.numBills*2]+parameters[bill+ipm.numBills];
	}
	
	public void getValueGradient(double gradient[])
	{
		for (int i=0; i<gradient.length; i++)
		{
			gradient[i]=0.0;
		}
		for (int bill=0; bill<ipm.numBills; bill++)
		{
			for (int voter : ipm.votes.get(bill).keySet())
			{
				double weight=computeWeight(bill, voter);
				if (ipm.votes.get(bill).get(voter)==1)
				{
					double commonTerm=Math.exp(-weight)/(1.0+Math.exp(-weight));
					gradient[bill]+=commonTerm*parameters[voter+ipm.numBills*2];
					gradient[bill+ipm.numBills]+=commonTerm;
					gradient[voter+ipm.numBills*2]+=commonTerm*parameters[bill];
				}
				else
				{
					double commonTerm=1.0/(1.0+Math.exp(-weight));
					gradient[bill]-=commonTerm*parameters[voter+ipm.numBills*2];
					gradient[bill+ipm.numBills]-=commonTerm;
					gradient[voter+ipm.numBills*2]-=commonTerm*parameters[bill];
				}
			}
		}
		for (int i=0; i<gradient.length; i++)
		{
			gradient[i]-=parameters[i]/(ipm.nu*ipm.nu);
		}
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
