package malscan;

import malmem.*;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.PcapSockAddr;
import org.jnetpcap.packet.format.FormatUtils;

import server.AdderService;

//Thread 4

public class PacketFinder implements Runnable {
	private String InterfaceName;
	private PcapIf device;
	private Smpsm smpsm;
	private StringBuilder errbuff;
	private int snaplen;
	private int flags;
	private int timeout;
	private Pcap pcap;
	private PacketHandler jpacketHandler;
	private boolean displayMalTracking;
	private String devIP2;

	// <<------------------------------------------------------------------------------------------------//

	public PacketFinder(String name, PcapIf dev, Mpsm mpsm, Smpsm smpsm,
			boolean mals, AdderService server, String UUID) {
		InterfaceName = name;
		device = dev;
		this.smpsm = smpsm;
		this.errbuff = new StringBuilder();
		this.snaplen = 64 * 1024;
		this.flags = Pcap.MODE_PROMISCUOUS;
		this.timeout = 10 * 1000;
		this.displayMalTracking = mals;

		pcap = Pcap
				.openLive(device.getName(), snaplen, flags, timeout, errbuff);
		if (pcap == null) {
			System.err.printf("error while opening device: "
					+ errbuff.toString());
			return;
		}

		System.out.println(InterfaceName + ": Device opened successfully!");

		// vriskei to ip address tou device
		String devIP = "";
		for (PcapAddr address : device.getAddresses()) {
			if (address.getAddr().getFamily() == PcapSockAddr.AF_INET) {
				devIP = FormatUtils.ip(address.getAddr().getData());
				devIP2 = devIP;// <-------------------------------------------------------------------//
				break;
			}
		}
		// arxikopoihsh handler gia na capturing paketwn
		jpacketHandler = new PacketHandler(mpsm, smpsm, InterfaceName, devIP,
				displayMalTracking, server, UUID);
	}

	public void run() {

		pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler,
				"from network interface: " + device.getName());

	}

	public void end() {
		smpsm.delete(InterfaceName, devIP2);// <<<----------------------------------------------------------------------------------------//
		pcap.breakloop();

	}

}
