package server;

import java.util.List;

import internalmemory.InternalMemory;
import internalmemory.MaliciousPatterns;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import android.AvailableNodes;
import databasesystem.*;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface AdderService {
	@WebMethod
	public InternalMemory getMemory();

	public void print();

	public void insertIpInMemory(String ip, String UUID);

	public void insertPatternInMemory(String pattern, String UUID);

	public boolean register(String nodeID);

	public MaliciousPatterns maliciousPatternRequest(String nodeID);

	public boolean unregister(String nodeID);

	public boolean logout(@WebParam(name = "username") String username,
			@WebParam(name = "password") String password);

	public void maliciousPatternsStatisticalReport(String nodeID,
			StatisticalReport m);

	// -----------------------//
	public boolean register_android(@WebParam(name = "name") String username,
			@WebParam(name = "pass") String password,
			@WebParam(name = "nodes") AvailableNodes nodes);

	public List<StatisticalReport> retrieveStatistics(String username,
			String password);

	public String retrieveMaliciousPatterns(String username, String password);

	public String terminateClient();

	public void insertMaliciousPatterns(
			@WebParam(name = "username") String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "maliciousIP") String maliciousIP,
			@WebParam(name = "maliciousPatterns") String maliciousPatterns);

	public int login(@WebParam(name = "name") String name,
			@WebParam(name = "pass") String pass);

	public boolean deletePC(@WebParam(name = "username") String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "uuid") String uuid);
}