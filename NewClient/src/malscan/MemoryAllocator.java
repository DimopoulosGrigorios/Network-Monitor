package malscan;
import java.rmi.RemoteException;

import server.AdderService;
import malmem.*;
import databasesystem.*;
//Thread 5

public class MemoryAllocator implements Runnable {
	/*
	 * To sygkekrimeno thread dhmiourgei tis koines mnhmes smpsm kai mpsm kai
	 * analambanei na ths moirazetai taktika me ton athroisth(dhladh na tis
	 * tupwnei ana 1 sec)
	 */
	// ------------------------------------------------------------------------------------------------------------//
	private Mpsm mpsm;
	private Smpsm smpsm;
	private Boolean isRunning = true;
	private AdderService server;
	private String PcUuid;

	// ------------------------------------------------------------------------------------------------------------//
	/* O constructor tou thread 5 kai h dhmiourgia twn koinwn mnhmwn */
	public MemoryAllocator(AdderService srvr,String pcuuid) {
		mpsm = new Mpsm();
		smpsm = new Smpsm();
		setServer(srvr);
		PcUuid=pcuuid;
	}

	// ------------------------------------------------------------------------------------------------------------//
	/* Epistrofh ths mnhmhs mpsm tou thread5 */
	public Mpsm getThread5Mpsm() {
		return mpsm;
	}

	// ------------------------------------------------------------------------------------------------------------//
	/* Epistrofh ths mnhmhs smspsm tou thread5 */
	public Smpsm getThread5Smpsm() {
		return smpsm;
	}

	// ------------------------------------------------------------------------------------------------------------//
	/* Ektupwsh twn koinwn mnoimwn */
	public void run() {
		while (isRunning) {
			/*System.out.println("\n\n|Printing shared memory segments every 30 seconds...|");
			System.out.println("\n||----MPSM Memory----||\n");
			mpsm.printMpsm();
			System.out.println("");
			System.out.println("\n||----S-MSPSM Memory----||\n");
			smpsm.print();
			System.out.println("");*/
			try 
			{
				StatisticalReport REP =smpsm.toStatisticalReport(PcUuid);
				smpsm.clear();
				server.StatisticalReport A=new server.StatisticalReport(REP.getMIPreport(),REP.getMPreport(),PcUuid);
				server.maliciousPatternsStatisticalReport(PcUuid, A);
			}	
			catch (RemoteException e) 
			{
				e.printStackTrace();
			}
			// perimene 1 sec prin ksana steileis
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e) 
			{
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	public void end() {
		isRunning = false;
	}

	public AdderService getServer() {
		return server;
	}

	public void setServer(AdderService server) {
		this.server = server;
	}
}
