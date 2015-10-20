package interfacepack;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class FreqList 
{
	/*Ylopoihsh mias klasshs tupou listas me monadikes eggrafes kai syxnothtes se kathe mia apo autes*/
	// ------------------------------------------------------------------------------------------------------------//
	private HashMap<String,Integer> objectlist;
	// ------------------------------------------------------------------------------------------------------------//
	/*constructor*/
	public FreqList()
	{
		objectlist=new HashMap<String,Integer>();
	}
	// ------------------------------------------------------------------------------------------------------------//
	public HashMap<String,Integer> get()
	{
		return objectlist;
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void set(HashMap<String,Integer> input)
	{
		objectlist=new HashMap<String,Integer>(input);
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Auksisi syxnothtas antikeimenou alliws dhmiourgia me thn dosmenh syxnothta*/
	public void RaiseFrequency(String object,Integer frequency)
	{
		if (objectlist.containsKey(object))
		{
			objectlist.put(object,objectlist.get(object)+frequency);
		}
		else
		{
			objectlist.put(object,frequency);
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh enos string apeikonishs ths klasshs*/
	public String toString()
	{
		 String finalstring="";
		 Set<String> setOfKeys = objectlist.keySet();
		 Iterator<String> iterator =setOfKeys.iterator();
		 while (iterator.hasNext()) 
		 {
			 String key = (String) iterator.next();
			 Integer value = (Integer)objectlist.get(key);
			 finalstring+=key+"\t\t\t\t\t\t"+value+"\n";
		 }
		 return finalstring;
	}
	// ------------------------------------------------------------------------------------------------------------//

}
