package malmem;
import interfacepack.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import databasesystem.StatisticalReport;




public class Smpsm 
{
	/*Ylopoihsh ths mnhmhs san lista me dedomena tupou klasshs interface kai suxronismos me readwritelock*/
	// ------------------------------------------------------------------------------------------------------------//
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private ArrayList<Interface> interfaces;
	// ------------------------------------------------------------------------------------------------------------//
	/*Constructor ths mnhmhs smpsm*/
	public Smpsm() 
	{
		interfaces=new ArrayList<Interface>();
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Get ths mnhmhs smpsm*/
	public ArrayList<Interface> get()
	{
		return interfaces;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Set ths mnhmhs smpsm*/
	public void set(ArrayList<Interface> inter_in)
	{
		interfaces=new ArrayList<Interface>(inter_in);
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Auksish suxnothtas malicious kata frequency an den uparxei dhmiourgia*/
	public void RaiseFrequencyIP(String interface_name, String interface_ip,String malicious, Integer frequency)
	{
		readWriteLock.writeLock().lock();
		// Safe zone
		boolean notfound=true;
		for(int i=0;i<interfaces.size();i++)
		{
			if(interfaces.get(i).name().equals(interface_name) && interfaces.get(i).ip().equals(interface_ip))
			{
				interfaces.get(i).RaiseFrequencyIP(malicious, frequency);
				notfound=false;
			}
		}
		if(notfound)
		{
			Interface new_inter=new Interface(interface_name,interface_ip);
			new_inter.RaiseFrequencyPattern(malicious, frequency);
			interfaces.add(new_inter);
		}
		// Safe zone
		readWriteLock.writeLock().unlock();
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Auksish suxnothtas malicious kata frequency an den uparxei dhmiourgia*/
	public void RaiseFrequencyPattern(String interface_name, String interface_ip,String malicious, Integer frequency)
	{
		readWriteLock.writeLock().lock();
		// Safe zone
		boolean notfound=true;
		for(int i=0;i<interfaces.size();i++)
		{
			if(interfaces.get(i).name().equals(interface_name) && interfaces.get(i).ip().equals(interface_ip))
			{
				interfaces.get(i).RaiseFrequencyPattern(malicious, frequency);
				notfound=false;
			}
		}
		if(notfound)
		{
			Interface new_inter=new Interface(interface_name,interface_ip);
			new_inter.RaiseFrequencyPattern(malicious, frequency);
			interfaces.add(new_inter);
		}
		// Safe zone
		readWriteLock.writeLock().unlock();
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh ths klashs se ena string*/
	public String toString()
	{
		readWriteLock.readLock().lock();
		//safe zone
		String final_string="";
		for(int i=0;i<interfaces.size();i++)
		{
			final_string+=interfaces.get(i).toString()+"\n\n\n";
		}
		// Safe zone
		readWriteLock.readLock().unlock();
		return final_string;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Ektypwsh*/
	public void print()
	{
		System.out.println(this.toString());
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Synarthsh gia auksisi syxnothtwn ston pinaka ths mnhmhs me ta malicious patterns apo ena Hashmap pou 
	 exei ws pedio key to malicious paterns kai ws deutero pedio mia lista me interface name ,interface ip,
	 kai poses fores theloume na anebasoume thn syxnothta*/
	public void freq_UPwithMAP_patterns(Map<String, ArrayList<String>> map)
	{
		for (String entry : map.keySet()) 
		{
			String malicious = entry;
			Integer frequency = Integer.parseInt(map.get(malicious).get(0));
			String interface_name = map.get(malicious).get(1);
			String interface_ip = map.get(malicious).get(2);
			RaiseFrequencyPattern(interface_name,interface_ip,malicious, frequency);
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	public boolean delete(String interface_name,String interface_ip)
	{
		readWriteLock.writeLock().lock();
		boolean found=false;
		for(int i=0;i<interfaces.size();i++)
		{
			if(interfaces.get(i).name().equals(interface_name) && interfaces.get(i).ip().equals(interface_ip))
			{
				interfaces.remove(i);
				found=true;
			}
		}
		readWriteLock.writeLock().unlock();
		return found;
	}
    // ------------------------------------------------------------------------------------------------------------//
	//Synarthsh pou metatrepei thn mnhmh smpsm se Statistical Report//
	public StatisticalReport toStatisticalReport(String PcUUID)
	{
		readWriteLock.readLock().lock();
		Integer nip=0,np=0;
		for(int i=0;i<interfaces.size();i++)
		{
			nip+=interfaces.get(i).getIp_mal().get().size();
			np+=interfaces.get(i).getPat_mal().get().size();
		}
		StatisticalReport st=new StatisticalReport(PcUUID,np,nip);
		Integer previoussizeMIP=0, previoussizeMP=0;
		for(int i=0;i<interfaces.size();i++)
		{
			 Set<String> IPs = interfaces.get(i).getIp_mal().get().keySet();
			 Iterator<String> iteratorIPs =IPs.iterator();
			 for(int j=0;j<IPs.size();j++)
			 {
				 String[] iprec=new String[4];
				 String keyIP = iteratorIPs.next();
				 String valueIP = interfaces.get(i).getIp_mal().get().get(keyIP).toString();
				 iprec[0]=(interfaces.get(i).ip());
				 iprec[1]=(interfaces.get(i).name());
				 iprec[2]=(keyIP);
				 iprec[3]=(valueIP);
				 st.ADDtoMIPreport(iprec, previoussizeMIP);
				 previoussizeMIP++;
			 }
			 Set<String> PATs = interfaces.get(i).getPat_mal().get().keySet();
			 Iterator<String> iteratorPATs =PATs.iterator();
			 for(int j=0;j<PATs.size();j++) 
			 {
				 String[] patrec=new String[4];
				 String keyPAT = (String) iteratorPATs.next();
				 String valuePAT = interfaces.get(i).getPat_mal().get().get(keyPAT).toString();
				 patrec[0]=(interfaces.get(i).ip());
				 patrec[1]=(interfaces.get(i).name());
				 patrec[2]=(keyPAT);
				 patrec[3]=(valuePAT);
				 st.ADDtoMPreport(patrec,previoussizeMP);
				 previoussizeMP++;
			 }
		}
		readWriteLock.readLock().unlock();
		return st;
	}
	// ------------------------------------------------------------------------------------------------------------//
	//Synarthsh pou sbhnei thn mnhmh Smpsm
	public void clear()
	{
		readWriteLock.writeLock().lock();
		for(int i=0;i<interfaces.size();i++)
		{
				interfaces.remove(i);
		}
		readWriteLock.writeLock().unlock();
	}
	// ------------------------------------------------------------------------------------------------------------//
}
