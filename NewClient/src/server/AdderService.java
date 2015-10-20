/**
 * AdderService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public interface AdderService extends java.rmi.Remote {
    public boolean register(java.lang.String arg0) throws java.rmi.RemoteException;
    public void print() throws java.rmi.RemoteException;
    public server.InternalMemory getMemory() throws java.rmi.RemoteException;
    public void maliciousPatternsStatisticalReport(java.lang.String arg0, server.StatisticalReport arg1) throws java.rmi.RemoteException;
    public void insertPatternInMemory(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public void insertMaliciousPatterns(java.lang.String username, java.lang.String password, java.lang.String maliciousIP, java.lang.String maliciousPatterns) throws java.rmi.RemoteException;
    public server.StatisticalReport[] retrieveStatistics(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public java.lang.String retrieveMaliciousPatterns(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public server.MaliciousPatterns maliciousPatternRequest(java.lang.String arg0) throws java.rmi.RemoteException;
    public void insertIpInMemory(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public int login(java.lang.String name, java.lang.String pass) throws java.rmi.RemoteException;
    public boolean deletePC(java.lang.String username, java.lang.String password, java.lang.String uuid) throws java.rmi.RemoteException;
    public boolean register_android(java.lang.String name, java.lang.String pass, java.lang.String[] nodes) throws java.rmi.RemoteException;
    public java.lang.String terminateClient() throws java.rmi.RemoteException;
    public boolean unregister(java.lang.String arg0) throws java.rmi.RemoteException;
}
