package interfacepack;

public class Interface 
{
	/*Ylopoihsh mias klasshs perigrafhs stoixeiwn sxetika me ena interface,plhrofories gia to onoma to ip tou opws kai t
	 * ta malicious patterns kai ips pou brethikan apo auto alla kai oi suxnothtes tous*/
	// ------------------------------------------------------------------------------------------------------------//
	private String name;
	private String ip;
	private FreqList mal_pat;
	private FreqList mal_ip;
	// ------------------------------------------------------------------------------------------------------------//
	/*Constructor*/
	public Interface(String name,String ip)
	{
		this.name=name;
		this.ip=ip;
		mal_ip=new FreqList();
		mal_pat=new FreqList();
	}
	public Interface()
	{
		name=null;
		ip=null;
		mal_ip=new FreqList();
		mal_pat=new FreqList();
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Set the name and the ip*/
	public void set(String name_in,String ip_in)
	{
		name=name_in;
		ip=ip_in;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh tou onomatos*/
	public String name()
	{
		return name;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh tou ip*/
	public String ip()
	{
		return ip;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh twn stoixeiwn gia malicious patterns*/
	public FreqList getPat_mal()
	{
		return mal_pat;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh twn stoixeiwn gia malicious ips*/
	public FreqList getIp_mal()
	{
		return mal_ip;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Auksisi ths syxnothtas kata frequency tou pattern kai an den uparxei dhmiourgia*/
	public void RaiseFrequencyPattern(String pattern,Integer frequency)
	{
		mal_pat.RaiseFrequency(pattern, frequency);
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Auksisi ths syxnothtas kata frequency tou pattern kai an den uparxei dhmiourgia*/
	public void RaiseFrequencyIP(String ip,Integer frequency)
	{
		mal_ip.RaiseFrequency(ip, frequency);
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void RaiseFrequencyPattern(String pattern)
	{
		mal_pat.RaiseFrequency(pattern,0);
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void RaiseFrequencyIP(String ip)
	{
		mal_ip.RaiseFrequency(ip,0);
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh tou interface se string*/
	public String toString()
	{
		String finalstring="";
		finalstring+="InterfaceName:"+name+"\n";
		finalstring+="InterfaceIp:"+ip+"\n";
		finalstring+="______________________________________________________________________________\n";
		finalstring+="[---Malicious Ips---]\t\t\t  [---Frequency---]\n";
		finalstring+=mal_ip.toString();
		finalstring+="[----Malicious Patterns---]\t\t  [---Frequency---]\n";
		finalstring+=mal_pat.toString();
		finalstring+="______________________________________________________________________________\n";
		return finalstring;
	}
	// ------------------------------------------------------------------------------------------------------------//
}
