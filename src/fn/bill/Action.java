package fn.bill;

public class Action
{
	public String vetoed;
	public String resolution_passed;
	public String passed_lower;
	public String failed;
	public String passed_upper;
	public String introduced;
	public String enacted;
	
	public String toString()
	{
		String str="";
		str+="\tvetoed: "+vetoed+"\n";
		str+="\tresolution_passed: "+resolution_passed+"\n";
		str+="\tpassed_lower: "+passed_lower+"\n";
		str+="\tfailed: "+failed+"\n";
		str+="\tpassed_upper: "+passed_upper+"\n";
		str+="\tintroduced: "+introduced+"\n";
		str+="\tenacted: "+enacted+"\n";
		return str;
	}
}
