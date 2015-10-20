package malscan;

import malmem.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;



//import java.util.Scanner;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.PcapSockAddr;
import org.jnetpcap.packet.format.FormatUtils;

import server.AdderService;

//Thread 1
public class InterfaceFinder implements Runnable {
	Mpsm mpsm;
	Smpsm smpsm;
	boolean isRunning = true;
	private List<PcapIf> Interfaces = new ArrayList<PcapIf>();
	private List<PcapIf> OldInterfaces = new ArrayList<PcapIf>();
	private Map<String, Thread> PFinder_threads = new HashMap<String, Thread>();
	private Map<String, PacketFinder> PFinder_classes = new HashMap<String, PacketFinder>();
	private StringBuilder errorBuffer = new StringBuilder();
	private boolean displayMalTracking = false;
	private String UUID;
	private AdderService server;

	// private Scanner input;

	public InterfaceFinder(Mpsm mpsm, Smpsm smpsm, AdderService server,
			String UUID) {
		this.mpsm = mpsm;
		this.smpsm = smpsm;
		this.server = server;
		this.UUID = UUID;

	}

	@SuppressWarnings("deprecation")
	public void run() {
			while (isRunning) {
				
					
				Interfaces = new ArrayList<PcapIf>();
				List<PcapIf> Temp = new ArrayList<PcapIf>();
				int n = Pcap.findAllDevs(Interfaces, errorBuffer);
				//filtrarisma interfaces
				for (PcapIf dev : Interfaces) {

					String devIP = "";
					for (PcapAddr address : dev.getAddresses()) {
						if (address.getAddr().getFamily() == PcapSockAddr.AF_INET) {
							devIP = FormatUtils.ip(address.getAddr().getData());
							break;
						}
					}
					if (devIP.equals(""))
						Temp.add(dev);
					if(dev.getName().equals("lo"))
						Temp.add(dev);
				}
				Interfaces.removeAll(Temp);
				if (n == Pcap.NOT_OK || Interfaces.isEmpty()) {
					System.err.printf("Error: " + errorBuffer.toString());
					return;
				}

				// Ean pirame kainouria interfaces tsekaroume ti allages uparxoun
				if (!OldInterfaces.isEmpty()) {
					Iterator<PcapIf> it1 = OldInterfaces.iterator();
					Iterator<PcapIf> it2 = Interfaces.iterator();
					List<String> OldNames = new LinkedList<String>();
					List<String> CurNames = new LinkedList<String>();
					while (it1.hasNext()) {
						OldNames.add(it1.next().getName());
					}
					while (it2.hasNext()) {
						CurNames.add(it2.next().getName());
					}
					// ean exoun allaksei ta interfaces
					if (!CurNames.equals(OldNames)) {
						List<String> Differences = new LinkedList<String>();
						Differences.addAll(CurNames);
						Differences.removeAll(OldNames);

						// periptwsh poy exoyme kainouria ksekiname ta antistixa
						// thread pou kanoun
						// trace ta paketa
						if (!Differences.isEmpty()) {
							System.out
									.println("\n|Found new active interfaces...|\n");
							Iterator<String> itDif = Differences.iterator();
							while (itDif.hasNext()) {
								String name = itDif.next();
								Iterator<PcapIf> it3 = Interfaces.iterator();
								while (it3.hasNext()) {
									PcapIf device = it3.next();
									String devName = device.getName();
									if (devName.equals(name)) {
										PacketFinder pfinderClass = new PacketFinder(
												name, device, mpsm, smpsm,
												displayMalTracking, server, UUID);
										Thread pfinderThread = new Thread(
												pfinderClass, name);
										pfinderThread.start();
										PFinder_threads.put(name, pfinderThread);
										PFinder_classes.put(name, pfinderClass);

									}
								}

							}
							// se periptwsi pou kapoia interfaces dn iparxoun pia
							// stamatame ta antistixa threads tous
						} else {
							Differences = new LinkedList<String>();
							Differences.addAll(OldNames);
							Differences.removeAll(CurNames);
							if (!Differences.isEmpty()) {
								System.out
										.println("\n|Found inactive interfaces. Shuting down some devices...|\n");
								Iterator<String> itDif = Differences.iterator();
								while (itDif.hasNext()) {
									String name = itDif.next();
									Iterator<PcapIf> it3 = OldInterfaces.iterator();
									while (it3.hasNext()) {
										PcapIf device = it3.next();
										String devName = device.getName();
										if (devName.equals(name)) {

											System.out.println(devName
													+ ": Closing device");
											PFinder_classes.get(devName).end();
											PFinder_threads.get(devName)
													.interrupt();

										}
									}
								}
							}
						}
					}
					// periptwsi prwtis epanalipsis pou exoume nea interfaces
					// ksekiname ola ta interfaces tis listas
				} else {
					Iterator<PcapIf> it1 = Interfaces.iterator();
					System.out.println("\n|Found new active interfaces...|\n");
					while (it1.hasNext()) {

						PcapIf device = it1.next();
						String name = device.getName();

						PacketFinder pfinderClass = new PacketFinder(name, device,
								mpsm, smpsm, displayMalTracking, server, UUID);
						Thread pfinderThread = new Thread(pfinderClass, name);
						pfinderThread.start();
						PFinder_threads.put(name, pfinderThread);
						PFinder_classes.put(name, pfinderClass);

					}

				}
				OldInterfaces = new ArrayList<PcapIf>(Interfaces);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
			}
	}

	// kata ton termatismo ginetai stamatima
	// olwn twn thread pou trexoun
	public void end() {
		isRunning = false;
		Iterator<PcapIf> it = Interfaces.iterator();
		while (it.hasNext()) {
			PcapIf device = it.next();
			String devName = device.getName();
			System.out.println("Closing device: " + devName);
			PFinder_classes.get(devName).end();
			PFinder_threads.get(devName).interrupt();
		}
	}

}
