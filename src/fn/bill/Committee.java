package fn.bill;

public class Committee
{
	public String committee_upper;
	public String committee_lower;
	
	public String toString()
	{
		String str="";
		str+="\tcommittee_upper: "+committee_upper+"\n";
		str+="\tcommittee_lower: "+committee_lower+"\n";
		return str;
	}
}
