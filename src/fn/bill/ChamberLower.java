package fn.bill;

public class ChamberLower
{
	public String yes_vote[];
	public String date;
	public String no_vote[];
	public boolean passed;
	public String other_vote[];
	
	public String toString()
	{
		String str="";
		str+=arrayToString(yes_vote, "yes_vote");
		str+="\t\tdate: "+date+"\n";
		str+=arrayToString(no_vote, "no_vote");
		str+="\t\tpassed: "+passed+"\n";
		str+=arrayToString(other_vote, "other_vote");
		return str;
	}
	
	public String arrayToString(String array[], String name)
	{
		String str="";
		if (array==null || array.length==0)
		{
			str+="\t\t"+name+": null\n";
		}
		else
		{
			str+="\t\t"+name+":";
			for (int i=0; i<array.length; i++)
			{
				str+=" "+array[i]+(i<array.length-1? "," : "");
			}
			str+="\n";
		}
		return str;
	}
}
