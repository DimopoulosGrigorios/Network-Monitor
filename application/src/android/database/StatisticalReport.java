package android.database;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class StatisticalReport implements KvmSerializable {
	// H klash auth ulopoih ton tupo Statistical Report kai periexei ena diplo
	// array gia ta Malicious Patterns Reports//
	// Kai ena diplo array gia ta Malicious Ip Reports opws kai to UUID apo thn
	// suskeuh pou stalthike//
	// ------------------------------------------------------------------------------------------------------------//
	private String PcUUID;
	private String[][] MPreport;
	private String[][] MIPreport;

	// ------------------------------------------------------------------------------------------------------------//
	// Constructors tou Statistical Report//
	public StatisticalReport() {
	}

	public StatisticalReport(Integer np, Integer nip) {
		MPreport = new String[np][4];
		MIPreport = new String[nip][4];
	}

	public StatisticalReport(String pcUUID, Integer np, Integer nip) {
		setPcUUID(pcUUID);
		MPreport = new String[np][4];
		MIPreport = new String[nip][4];
	}

	public StatisticalReport(String UUID, String[][] MP, String[][] MIP) {
		if (MP == null) {
			MP = new String[0][4];
		}
		if (MIP == null) {
			MIP = new String[0][4];
		}
		MIPreport = MIP;
		MPreport = MP;
		PcUUID = UUID;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Getters kai Setters twn pediwn//
	public String[][] getMPreport() {
		return MPreport;
	}

	public void setMPreport(String[][] mPreport) {
		MPreport = mPreport;
	}

	public String[][] getMIPreport() {
		return MIPreport;
	}

	public void setMIPreport(String[][] mIPreport) {
		MIPreport = mIPreport;
	}

	public String getPcUUID() {
		return PcUUID;
	}

	public void setPcUUID(String pcUUID) {
		PcUUID = pcUUID;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Adders gia ta duo pedia//
	public void ADDtoMPreport(String[] MP, Integer i) {
		MPreport[i] = MP;
	}

	public void ADDtoMIPreport(String[] MIP, Integer i) {
		MIPreport[i] = MIP;
	}

	// ------------------------------------------------------------------------------------------------------------//
	public String toString() {
		String rtrn = "PCUUID:" + PcUUID + "\n";
		rtrn += "----------------Malicious_Patterns------------------ \n";
		for (int i = 0; i < MPreport.length; i++) {
			for (int j = 0; j < 4; j++) {
				rtrn += "||" + MPreport[i][j];
			}
			rtrn += "\n";

		}
		rtrn += "----------------Malicious_IPs--------------------- \n";
		for (int i = 0; i < MIPreport.length; i++) {
			for (int j = 0; j < 4; j++) {
				rtrn += "||" + MIPreport[i][j];
			}
			rtrn += "\n";
		}
		return rtrn;
	}

	@Override
	public Object getProperty(int arg0) {
		if (arg0 == 0)
			return PcUUID;
		else if (arg0 == 1)
			return MPreport;
		else if (arg0 == 2)
			return MIPreport;

		return null;
	}

	@Override
	public int getPropertyCount() {

		return 3;
	}

	@Override
	public void getPropertyInfo(int arg0,
			@SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		if (arg0 == 0) {
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "PcUUID";
		} else if (arg0 == 1) {
			arg2.type = PropertyInfo.VECTOR_CLASS;
			arg2.name = "MPreport";
		} else if (arg0 == 2) {
			arg2.type = PropertyInfo.VECTOR_CLASS;
			arg2.name = "MIPreport";
		}

	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		if (arg0 == 0)
			PcUUID = arg1.toString();
		else if (arg0 == 1)
			MPreport = (String[][]) arg1;
		else if (arg0 == 2)
			MIPreport = (String[][]) arg1;

	}
	// ------------------------------------------------------------------------------------------------------------//
}
