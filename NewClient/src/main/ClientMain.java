package main;

import java.rmi.RemoteException;
import java.util.Scanner;

import malscan.*;
import malmem.*;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import server.AdderService;
import server.AdderServiceImplServiceLocator;

public class ClientMain {

	public static void main(String[] args) throws InterruptedException {
		AdderServiceImplServiceLocator service;
		final AdderService server;

		try {

			// Thread.currentThread().interrupt();
			System.out.println("Enter the server http address: ");
			Scanner scanner = new Scanner(System.in);
			String adress = scanner.nextLine();
			service = new AdderServiceImplServiceLocator(adress
					+ "AdderService?WSDL", new QName("http://server/",
					"AdderServiceImplService"));
			server = service.getAdderServiceImplPort();
			scanner.close();
			Mpsm mpsm;
			Smpsm smpsm;
			System.out
					.println("|--------------------------------------------|");
			System.out
					.println("| Starting Network Monitoring Application... |");
			System.out
					.println("|--------------------------------------------|\n");
			// Arxikopoihsh tou thread poy eggrafei to mixanima ston athroisth
			final PCRegister PCReg = new PCRegister(server);
			final Thread PCReg_thread = new Thread(PCReg);
			PCReg_thread.start();
			final String UUID = PCReg.getUuid();
			// Arxikopoihsh tou thread arxikopoihshs koinwn mnhmwn
			final MemoryAllocator memAllocator = new MemoryAllocator(server,
					UUID);
			final Thread memAllocator_thread = new Thread(memAllocator);
			memAllocator_thread.start();
			mpsm = memAllocator.getThread5Mpsm();
			smpsm = memAllocator.getThread5Smpsm();
			// Arxikopoihsh toy thread poy diavazei thn mnhmh mpsm apo arxeio
			final MemoryUpdater memUpdater = new MemoryUpdater(mpsm,
					PCReg.getUuid(), server);
			final Thread memUpdater_thread = new Thread(memUpdater);
			memUpdater_thread.start();
			// Arxikopoihsh toy thread poy anixneuei ta network interfaces
			final InterfaceFinder IntFinder = new InterfaceFinder(mpsm, smpsm,
					server, PCReg.getUuid());
			final Thread IntFinder_thread = new Thread(IntFinder);
			IntFinder_thread.start();
			// Auto tha energopoihthei otan teleiwsei to programma i erthei
			// kapoio sima termatismou
			boolean flag = true;
			String uuids = "";
			while (flag) {
				try {

					uuids = server.terminateClient();
					if (uuids.contains(PCReg.getUuid())) {
						flag = false;
						System.out
								.println("\n|Shutting down application...|\n");
						try {
							server.unregister(UUID);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						memAllocator.end();
						memAllocator_thread.interrupt();
						memUpdater.end();
						memUpdater_thread.interrupt();
						IntFinder.end();
						IntFinder_thread.interrupt();
						try {
							while (memAllocator_thread.isAlive())
								memAllocator_thread.join();
							while (memUpdater_thread.isAlive())
								memUpdater_thread.join();
							while (IntFinder_thread.isAlive())
								IntFinder_thread.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						System.out.println("\n|-----|");
						System.out.println("| End |");
						System.out.println("|-----|\n");
						System.exit(0);
					}
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				Thread.sleep(5000);
			}

			Runtime.getRuntime().addShutdownHook(new Thread() {

				public void run() {
					System.out.println("\n|Shutting down application...|\n");
					try {
						server.unregister(UUID);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					memAllocator.end();
					memAllocator_thread.interrupt();
					memUpdater.end();
					memUpdater_thread.interrupt();
					IntFinder.end();
					IntFinder_thread.interrupt();
					try {
						while (memAllocator_thread.isAlive())
							memAllocator_thread.join();
						while (memUpdater_thread.isAlive())
							memUpdater_thread.join();
						while (IntFinder_thread.isAlive())
							IntFinder_thread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("\n|-----|");
					System.out.println("| End |");
					System.out.println("|-----|\n");

				}
			});
			try {
				Thread.currentThread().join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

}
