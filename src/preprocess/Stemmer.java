package preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import utility.PorterStemmer;

public class Stemmer
{
	public PorterStemmer stemmer;
	
	public void stem(String srcFileName, String destFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(srcFileName));
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFileName));
		String line,seg[];
		while ((line=br.readLine())!=null)
		{
			seg=line.split(" ");
			for (int i=0; i<seg.length; i++)
			{
				if (seg[i].length()==0) continue;
				bw.write(stem(seg[i])+" ");
			}
			bw.newLine();
		}
		br.close();
		bw.close();
	}
	
	public String stem(String word)
	{
		return stemmer.stem(word);
	}
	
	public Stemmer()
	{
		stemmer=new PorterStemmer();
	}
}
