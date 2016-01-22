package utility;

import java.util.ArrayList;

import cc.mallet.util.Randoms;

public class MathUtil
{
	public static Randoms randoms=new Randoms();
	
	//sampling
	public static int selectDiscrete(double score[])
	{
		double sum=0.0;
		for (int i=0; i<score.length; i++)
		{
			sum+=score[i];
		}
		
		double sample=randoms.nextDouble()*sum;
		int index=-1;
		while (sample>0 && index<score.length-1)
		{
			index++;
			sample-=score[index];
		}
		
		return index;
	}
	
	public static int selectLogDiscrete(double score[])
	{
		double max=selectMax(score);
		for (int i=0; i<score.length; i++)
		{
			score[i]=Math.exp(score[i]-max);
		}
		return selectDiscrete(score);
	}
	
	//array and matrix math
	public static double selectMax(double score[])
	{
		double max=Double.NEGATIVE_INFINITY;
		for (int i=0; i<score.length; i++)
		{
			if (score[i]>max)
			{
				max=score[i];
			}
		}
		return max;
	}
	
	public static double average(double nums[])
	{
		double avg=0.0;
		for (int i=0; i<nums.length; i++)
		{
			avg+=nums[i];
		}
		return avg/(double)nums.length;
	}
	
	public static double average(ArrayList<Double> nums)
	{
		double avg=0.0;
		for (int i=0; i<nums.size(); i++)
		{
			avg+=nums.get(i);
		}
		return avg/(double)nums.size();
	}
	
	public static double sum(double nums[])
	{
		double result=0.0;
		for (int i=0; i<nums.length; i++)
		{
			result+=nums[i];
		}
		return result;
	}
	
	public static double matrixAbsDiff(double m1[][], double m2[][])
	{
		if (m1.length!=m2.length) return Double.MAX_VALUE;
		int numElements=0;
		double diff=0.0;
		for (int i=0; i<m1.length; i++)
		{
			if (m1[i].length!=m2[i].length) return Double.MAX_VALUE;
			numElements+=m1[i].length;
			for (int j=0; j<m1[i].length; j++)
			{
				diff+=Math.abs(m1[i][j]-m2[i][j]);
			}
		}
		if (numElements==0) return 0.0;
		return diff/numElements;
	}
	
	public static double matrixKLDivergence(double m1[][], double m2[][])
	{
		if (m1.length!=m2.length) return Double.MAX_VALUE;
		double avgKL=0.0;
		for (int i=0; i<m1.length; i++)
		{
			if (m1[i].length!=m2[i].length) return Double.MAX_VALUE;
			avgKL+=vectorKLDivergence(m1[i], m2[i]);
		}
		return avgKL/m1.length;
	}
	
	public static double vectorAbsDiff(double v1[], double v2[])
	{
		if (v1.length!=v2.length) return Double.MAX_VALUE;
		double diff=0.0;
		for (int i=0; i<v1.length; i++)
		{
			diff+=Math.abs(v1[i]-v2[i]);
		}
		return diff/v1.length;
	}
	
	public static double vectorKLDivergence(double v1[], double v2[])
	{
		if (v1.length!=v2.length) return Double.MAX_VALUE;
		double kl=0.0;
		for (int i=0; i<v1.length; i++)
		{
			kl+=v1[i]*Math.log(v1[i]/v2[i]);
		}
		return kl;
	}
	
	//useful functions
	public static double logFactorial(int n)
	{
		double result=0.0;
		for (int i=1; i<=n; i++)
		{
			result+=Math.log((double)i);
		}
		return result;
	}
	
	public static double sigmoid(double x)
	{
		return 1.0/(1.0+Math.exp(-1.0*x));
	}
	
	public static double sqr(double x)
	{
		return x*x;
	}
	
	public static int sqr(int x)
	{
		return x*x;
	}
	
	//generation from distribution(s)
	public static double sampleIG(double muIG, double lambdaIG)
	{
		double v=randoms.nextGaussian();   
		double y=v*v;
		double x=muIG+(muIG*muIG*y)/(2*lambdaIG)-(muIG/(2*lambdaIG))*Math.sqrt(4*muIG*lambdaIG*y + muIG*muIG*y*y);
		double test=randoms.nextDouble();
		if (test<=(muIG)/(muIG+x))
		{
			return x;
		}
		return (muIG*muIG)/x;
	}
	
	public static double[] sampleDir(double alpha, int size)
	{
		double alphaVector[]=new double[size];
		for (int i=0; i<size; i++)
		{
			alphaVector[i]=alpha;
		}
		return sampleDir(alphaVector);
	}
	
	public static double[] sampleDir(double alpha[])
	{
		double v[]=new double[alpha.length];
		for (int i=0; i<alpha.length; i++)
		{
			v[i]=randoms.nextGamma(alpha[i]);
			while (v[i]==0.0)
			{
				v[i]=randoms.nextGamma(alpha[i]);
			}
		}
		double sumV=sum(v);
		for (int i=0; i<alpha.length; i++)
		{
			v[i]/=sumV;
		}
		return v;
	}
}
