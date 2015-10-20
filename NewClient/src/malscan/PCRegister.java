package malscan;
import java.rmi.RemoteException;
import java.util.UUID;

import server.AdderService;

//Thread 2

public class PCRegister implements Runnable {
	/*
	 * To sygkekrimeno thread kanei register ston athroisth me ena monadiko UUID
	 * pou kanei generate me tyxaiothta
	 */
	private AdderService server;
	private String pc_uuid;
	
	public PCRegister(AdderService server){
		UUID uuid = UUID.randomUUID();
		String uuidstr1 = uuid.toString();
		UUID uid2 = UUID.randomUUID();
		String uuidstr2 = uid2.toString();
		pc_uuid=uuidstr1 + "-" + uuidstr2;
		this.server = server;
		System.out.println("TERMINAL UUID:"+pc_uuid);
	}
	public String getUuid(){
		return pc_uuid;	
	}
	public void run() {
		try {
			server.register(pc_uuid);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
