package fn.insight3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import fn.FNCfg;

public class PrimaryCollector
{
	public void collect(String sponsorFileName, String primaryFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(sponsorFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(primaryFileName));
		String line,seg[],segseg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split("#");
			boolean findPrimaryParty=false;
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				segseg=seg[i].split("\\$");
				if (segseg.length!=3) continue;
				if (segseg[1].equals("primary"))
				{
					if (segseg[0].indexOf("Democratic")!=-1)
					{
						bw.write("0");
						bw.newLine();
						findPrimaryParty=true;
						break;
					}
					if (segseg[0].indexOf("Republican")!=-1)
					{
						bw.write("1");
						bw.newLine();
						findPrimaryParty=true;
						break;
					}
				}
			}
			if (!findPrimaryParty)
			{
				bw.write("-1");
				bw.newLine();
			}
		}
		br.close();
		bw.close();
	}
	
	public static void main(String args[]) throws Exception
	{
		PrimaryCollector collector=new PrimaryCollector();
		collector.collect(FNCfg.sponsorFileName, FNCfg.in3PrimaryFileName);
	}
}
