package main;

import java.util.Scanner;

import javax.xml.ws.Endpoint; 

import server.AdderServiceImpl;


public class ServerMain 
{
    public static void main(String[] args) 
    {  
    	
    	System.out.println("Enter the Server address: ");
		Scanner scanner = new Scanner(System.in);
		String adress = scanner.nextLine();
        Endpoint.publish(adress, new AdderServiceImpl());
        //H dieuthunsh pou prepei na dinetai se client kai server prepei na einai ths morfhs 
        //http://0.0.0.0:9999/Add/
        scanner.close();
    }
}
