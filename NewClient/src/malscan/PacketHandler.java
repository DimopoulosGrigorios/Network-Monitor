package malscan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import malmem.Mpsm;
import malmem.Smpsm;

import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;

import server.AdderService;

//import org.jnetpcap.protocol.tcpip.Tcp;

public class PacketHandler implements PcapPacketHandler<String> {
	private Mpsm mpsm;
	private Smpsm smpsm;
	private String name;
	private String Interip;
	private boolean displayMalTracking;

	public PacketHandler(Mpsm mpsm, Smpsm smpsm, String name, String ip,
			boolean mals, AdderService server, String UUID) {
		this.mpsm = mpsm;
		this.smpsm = smpsm;
		this.name = name;
		this.Interip = ip;

		this.displayMalTracking = mals;
	}

	@Override
	public void nextPacket(PcapPacket packet, String user) {

		Ip4 ip = new Ip4();
		if (packet.hasHeader(ip)) {
			// vriskoume to payload to paketou
			Payload payload = new Payload();

			String PayloadBuffer = new String(ip.peerPayloadTo(payload)
					.toHexdump());

			// koitame an sto paketo iparxoun malicious patterns
			Map<String, ArrayList<String>> maliciousPatterns = mpsm
					.Pattern_searchinMpsm(PayloadBuffer, name, Interip);

			// an o xrhsths exei energopoihsei ta prints, emfanizontai
			// antistoixa minimata
			if (displayMalTracking) {
				Set<String> pats = new HashSet<String>();
				pats = maliciousPatterns.keySet();
				Iterator<String> it = pats.iterator();
				while (it.hasNext()) {
					String n = it.next();
					ArrayList<String> about = maliciousPatterns.get(n);
					System.out.print("[Found malicious pattern: " + n
							+ ", in packet from network interface: "
							+ about.get(1) + "]\n");
				}
			}
			// ananewnoume thn mnhmh
			smpsm.freq_UPwithMAP_patterns(maliciousPatterns);

			// vriskoume source kai destination ip tou paketou kai psaxnoume
			// an einai malicious
			// ektypwsh mono an to exei energopoihsei o xrhsths
			String sourceIP = FormatUtils.ip(ip.source());

			if (mpsm.searchinMpsm(sourceIP, 0)) {
				if (displayMalTracking) {
					String mal = "[Found malicious ip: " + sourceIP
							+ ", in packet from network interface: " + name
							+ "]\n";
					System.out.print(mal);
				}
				smpsm.RaiseFrequencyIP(name, Interip, sourceIP, 1);

			}
			String destinationIP = FormatUtils.ip(ip.destination());
			if (mpsm.searchinMpsm(destinationIP, 0)) {
				if (displayMalTracking) {
					String mal = "[Found malicious ip: " + destinationIP
							+ ", in packet from network interface: " + name
							+ "]\n";
					System.out.print(mal);
				}

				smpsm.RaiseFrequencyIP(name, Interip, destinationIP, 1);

			}
		}

	}
}
