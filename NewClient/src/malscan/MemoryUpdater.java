package malscan;

import java.rmi.RemoteException;

import malmem.Mpsm;
import server.AdderService;
//import server.MaliciousPatterns;
import server.MaliciousPatterns;

//Thread 3
public class MemoryUpdater implements Runnable {
	/*
	 * To sygkekrimeno thread epikoinwnei me ton athroisth(to arxeio Mal.txt)
	 * kai ana takta xronika(3 seconds) diastimata diabazei apo auton nea
	 * malicious ips kai nea malicious patterns ta opoia kai prosthetei sthn
	 * mnhmh
	 */
	// ------------------------------------------------------------------------------------------------------------//
	private Mpsm mpsm;
	private Boolean isRunning = true;
	private String UUID;
	private AdderService server;

	// ------------------------------------------------------------------------------------------------------------//
	/* O constructor tou thread */
	public MemoryUpdater(Mpsm mpsm, String UUID, AdderService server) {
		this.mpsm = mpsm;
		this.UUID = UUID;
		this.server = server;
	}

	// ------------------------------------------------------------------------------------------------------------//
	public void run() {
		while (isRunning) {
			try {
				
				MaliciousPatterns update = null;
				update = server.maliciousPatternRequest(UUID);
				
				if (update != null) {
					if (update.getMalIPs() != null) {
						String[] ips = update.getMalIPs();
						if (ips != null && ips.length != 0)
							for (String temp : ips) {
								mpsm.addtoMpsm(temp, 0);
							}
					}
					if (update.getMalPatterns() != null) {
						String []ips = update.getMalPatterns();
						if (ips != null && ips.length != 0)
							for (String temp : ips) {
								mpsm.addtoMpsm(temp, 1);
							}
					}
				}
				Thread.sleep(3000);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e) {
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	public void end() {
		isRunning = false;
	}
}
