package strutils;
public class StringUtils 
{
	/*Mia klash pou anaptyxthike gia na fylaei kapoies static synarthseis gia strings*/
	//------------------------------------------------------------------------------------------------------------//
	/*Metraei poses fores to sub brisketai mesa sto str*/
	public static int countMatches(final CharSequence str, final CharSequence sub) 
	{
		if (isEmpty(str) || isEmpty(sub)) 
		{
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx =indexOf(str, sub, idx)) != -1)
		{
		             count++;
		             idx += sub.length();
		}
		return count;
	}
	//------------------------------------------------------------------------------------------------------------//
	/*Mas leei an ena char sequence einai adeio*/
	public static boolean isEmpty(final CharSequence cs) 
	{
		return cs == null || cs.length() == 0;
	}
	//------------------------------------------------------------------------------------------------------------//
	/*Briskei to prwto index sto charsequence pou tairiazei me ton dosmeno xarakthra*/
	static int indexOf(final CharSequence cs, final int searchChar, int start) 
	{
		if (cs instanceof String) 
		{
			return ((String) cs).indexOf(searchChar, start);
		} 
		else 
		{
			final int sz = cs.length();
		    if (start < 0) 
		    {
		    	start = 0;
		    }
		    for (int i = start; i < sz; i++) 
		    {
		    	if (cs.charAt(i) == searchChar) 
		        {
		    		return i;
		        }
		    }
		    return -1;
		}
	}
	//------------------------------------------------------------------------------------------------------------//
	/*Briksei to prwto index sto cs pou tairiazei me to searchar*/
	static int indexOf(final CharSequence cs, final CharSequence searchChar, final int start) 
	{
		return cs.toString().indexOf(searchChar.toString(), start);
	}
	//------------------------------------------------------------------------------------------------------------//
}
