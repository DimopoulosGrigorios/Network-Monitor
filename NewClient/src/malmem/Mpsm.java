package malmem;
import strutils.*;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Mpsm {
	/*Klash koinhs mnhmhes mpsm*/
	/*Ylopoihsh kleidwmatwn me readwrite lock gia thread ths java*/
	/*Ylopoihsh ths mnhmhs san disdiastato pinaka o opoios periexei duo listes h mia ek twn
	  opoiwn periexei ta malicious ip h deuterh lista periexei ta malicious patterns */
	private ArrayList<ArrayList<String>> mpsm;
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	// ------------------------------------------------------------------------------------------------------------//
	public Mpsm() {

		ArrayList<String> mpsm0, mpsm1;
		mpsm0 = new ArrayList<String>();
		mpsm1 = new ArrayList<String>();
		mpsm = new ArrayList<ArrayList<String>>();
		mpsm.add(mpsm0);
		mpsm.add(mpsm1);
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Epistrofh mnhmhs*/
	public ArrayList<ArrayList<String>> getMpsm() {
		return mpsm;
	}

	// ------------------------------------------------------------------------------------------------------------//
	/*Prosthikh antikeimenou sthn mnhmh*/
	/*n:0 gia malicious ip 1 gia malicious patern add */
	public void addtoMpsm(String malicious, int n)
	{
		readWriteLock.writeLock().lock();
		//Safe zone
		mpsm.get(n).add(malicious);
		//Safe zone
		readWriteLock.writeLock().unlock();
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Synarthsh pou tupwnei me stoixhsh thn mnhmh*/
	public void printMpsm() {
		readWriteLock.readLock().lock();
		// Safe zone
		int i = 0;
		System.out.println("[Malicious IPs]");
		while (i < mpsm.get(0).size()) {
			if (i < (mpsm.get(0).size()))
				System.out.println(mpsm.get(0).get(i) + "\t");
			i++;
		}
		i = 0;
		System.out.println("\n[Malicious String Patterns]");
		while(i < mpsm.get(1).size()){
			if (i < (mpsm.get(1).size()))
				System.out.println(mpsm.get(1).get(i));
			i++;
		}
		// Safe zone
		readWriteLock.readLock().unlock();
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Synarthsh pou psaxnei an kati uparxei mesa sthn mnhmh*/
	/*n:0 gia malicious ip 1 gia malicious patern*/
	public boolean searchinMpsm(String str, int n) {
		readWriteLock.readLock().lock();
		// Safe zone
		int i = 0;
		boolean flag = false;
		while (i < mpsm.get(n).size()) {
			if (str.equals(mpsm.get(n).get(i))) {
				flag = true;
				break;
			}
			i++;
		}
		// Safe zone
		readWriteLock.readLock().unlock();
		return flag;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Synarthsh pou psaxnei an mia malicious Ip uparxei mesa sthn mnhmh*/
	public boolean IP_searchinMpsm(String str) {
		readWriteLock.readLock().lock();
		// Safe zone
		int i = 0;
		boolean flag = false;
		while (i < mpsm.get(0).size()) {
			if (str.equals(mpsm.get(0).get(i))) {
				flag = true;
				break;
			}
			i++;
		}
		// Safe zone
		readWriteLock.readLock().unlock();
		return flag;
	}
	// ------------------------------------------------------------------------------------------------------------//
	/*Synarthsh pou epistrefei posa kai poia malicious pattern ths mnhmhs briskontai se mia sumboloseira*/
	public Map<String, ArrayList<String>> Pattern_searchinMpsm(String str,
			String interface_name, String interface_ip) {
		readWriteLock.readLock().lock();
		// Safe zone
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		int count;
		int i = 0;
		while (i < mpsm.get(1).size()) {
			ArrayList<String> lista = new ArrayList<String>();
			count = StringUtils.countMatches(str, mpsm.get(1).get(i));
			if (count != 0) {
				lista.add(Integer.toString(count));
				lista.add(interface_name);
				lista.add(interface_ip);
				map.put(mpsm.get(1).get(i), lista);
			}
			i++;
		}
		// Safe zone
		readWriteLock.readLock().unlock();
		return map;
	}
	// ------------------------------------------------------------------------------------------------------------//
}
