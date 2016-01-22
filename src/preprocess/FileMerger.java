package preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileMerger
{
	public BufferedWriter bw;
	
	public void merge(String fileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(fileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			bw.write(line);
			bw.newLine();
			bw.flush();
		}
		br.close();
	}
	
	public void finalize() throws Exception
	{
		bw.flush();
		bw.close();
	}
	
	public FileMerger(String destFileName) throws Exception
	{
		bw=new BufferedWriter(new FileWriter(destFileName));
	}
}
