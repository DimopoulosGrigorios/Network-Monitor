package internalmemory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;




public class MaliciousPatterns {

	private  Set <String> MalPatterns;
	private Set <String> MalIPs;
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	public MaliciousPatterns(){
		MalPatterns = new HashSet<String>();
		MalIPs = new HashSet <String>();
	}
	public String print(){
		String ret = "";
		ret = ret + "Malicious Patterns:\n";
		for(String temp: MalPatterns)
			ret = ret + temp +"\n";
		ret = ret + "Malicious IPs:\n";
		for(String temp: MalIPs)
			ret = ret + temp +"\n";
		
		System.out.println(MalPatterns);
		System.out.println(MalIPs);
		return ret;
	}
	
	public Set <String> getMalIPs(){
		Set<String> ret = new HashSet<String>();
		readWriteLock.readLock().lock();
		ret.addAll(MalIPs);
		readWriteLock.readLock().unlock();
		return ret;
	}
	
	public void setMalIPs(Set<String> mals){
		MalIPs = mals;
	}
	
	
	public Set <String> getMalPatterns(){
		Set<String> ret = new HashSet<String>();
		readWriteLock.readLock().lock();
		ret.addAll(MalPatterns);
		readWriteLock.readLock().unlock();
		return ret;
	}
	
	public void setMalPatterns(Set<String> mals){
		MalPatterns = mals;
	}
	
	public void addIP(String IP){
		readWriteLock.writeLock().lock();
		MalIPs.add(IP);
		readWriteLock.writeLock().unlock();
	}
	
	public void addPattern(String Pattern){
		readWriteLock.writeLock().lock();
		MalPatterns.add(Pattern);
		readWriteLock.writeLock().unlock();
	}

	public boolean existsIP(String ip){
		if(MalIPs.contains(ip))
			return true;
		else 
			return false;
	}
	
	public boolean existsPattern(String pattern){
		if(MalPatterns.contains(pattern))
			return true;
		else 
			return false;
	}
	
}
