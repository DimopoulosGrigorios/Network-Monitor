package server;

public class AdderServiceProxy implements server.AdderService {
  private String _endpoint = null;
  private server.AdderService adderService = null;
  
  public AdderServiceProxy() {
    _initAdderServiceProxy();
  }
  
  public AdderServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initAdderServiceProxy();
  }
  
  private void _initAdderServiceProxy() {
    try {
      adderService = (new server.AdderServiceImplServiceLocator()).getAdderServiceImplPort();
      if (adderService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)adderService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)adderService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (adderService != null)
      ((javax.xml.rpc.Stub)adderService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public server.AdderService getAdderService() {
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService;
  }
  
  public boolean register(java.lang.String arg0) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.register(arg0);
  }
  
  public void print() throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    adderService.print();
  }
  
  public server.InternalMemory getMemory() throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.getMemory();
  }
  
  public void maliciousPatternsStatisticalReport(java.lang.String arg0, server.StatisticalReport arg1) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    adderService.maliciousPatternsStatisticalReport(arg0, arg1);
  }
  
  public void insertPatternInMemory(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    adderService.insertPatternInMemory(arg0, arg1);
  }
  
  public void insertMaliciousPatterns(java.lang.String username, java.lang.String password, java.lang.String maliciousIP, java.lang.String maliciousPatterns) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    adderService.insertMaliciousPatterns(username, password, maliciousIP, maliciousPatterns);
  }
  
  public server.StatisticalReport[] retrieveStatistics(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.retrieveStatistics(arg0, arg1);
  }
  
  public java.lang.String retrieveMaliciousPatterns(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.retrieveMaliciousPatterns(arg0, arg1);
  }
  
  public server.MaliciousPatterns maliciousPatternRequest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.maliciousPatternRequest(arg0);
  }
  
  public void insertIpInMemory(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    adderService.insertIpInMemory(arg0, arg1);
  }
  
  public int login(java.lang.String name, java.lang.String pass) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.login(name, pass);
  }
  
  public boolean deletePC(java.lang.String username, java.lang.String password, java.lang.String uuid) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.deletePC(username, password, uuid);
  }
  
  public boolean register_android(java.lang.String name, java.lang.String pass, java.lang.String[] nodes) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.register_android(name, pass, nodes);
  }
  
  public java.lang.String terminateClient() throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.terminateClient();
  }
  
  public boolean unregister(java.lang.String arg0) throws java.rmi.RemoteException{
    if (adderService == null)
      _initAdderServiceProxy();
    return adderService.unregister(arg0);
  }
  
  
}