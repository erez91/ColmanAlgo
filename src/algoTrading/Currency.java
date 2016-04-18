package algoTrading;

public class Currency 
{
	private String date = "";
	private String type = "EUR-USD";
	private double value=-1;
	private double command = Paramenters.waitAt;


	public void Currency(String s_type,String s_date, double d_value, double d_command )
	{
		type = s_type;
		date = s_date;
		value= d_value;
		command = d_command;
	}
	
	public String getType()
	{
		return type;
	}
	public String getDate()
	{
		return date;		
	}
	public double getValue()
	{
		return value;
	}
	public double getCommand()
	{
		return command;
	}
	
	public void setType(String s_type)
	{
		type = s_type;
	}
	public void setDate(String s_date)
	{
		date= s_date;
	}
	public void setValue(double d_value)
	{
		value = d_value;
	}
	public void setCommand(double d_command)
	{
		command = d_command;
	}
}
