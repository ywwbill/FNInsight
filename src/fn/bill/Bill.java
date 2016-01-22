package fn.bill;

public class Bill
{
	public Vote votes;
	public Document documents;
	public Committee committees;
	public String description;
	public String title;
	public String bill_type;
	public Person sponsors[];
	public Action actions;
	public String chamber;
	public String state;
	public String session;
	public int id;
	
	public String toString()
	{
		String str="";
		str+="votes:\n"+votes;
		str+="documents:\n"+documents;
		str+="committees:\n"+committees;
		str+="description: "+description+"\n";
		str+="title: "+title+"\n";
		str+="bill_type: "+bill_type+"\n";
		if (sponsors==null || sponsors.length==0)
		{
			str+="sponsors: null\n";
		}
		else
		{
			str+="sponsors:\n";
			for (int i=0; i<sponsors.length; i++)
			{
				str+=sponsors[i];
			}
		}
		str+="actions: \n"+actions;
		str+="chamber: "+chamber+"\n";
		str+="state: "+state+"\n";
		str+="session: "+session+"\n";
		str+="id: "+id+"\n";
		return str;
	}
}
