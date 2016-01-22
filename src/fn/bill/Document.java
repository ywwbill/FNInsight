package fn.bill;

import com.google.gson.annotations.SerializedName;

public class Document
{
	@SerializedName("0") public String id;
	
	public String toString()
	{
		return "\t0: "+id+"\n";
	}
}
