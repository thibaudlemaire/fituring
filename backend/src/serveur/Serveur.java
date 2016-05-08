package serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.ServeurInterface;
import interfaces.UpdateParamInterface;

public class Serveur implements ServeurInterface  {

	String args = "5869";
	int portNumber = Integer.parseInt(args);
	
	@Override
	public int getVolume() {
		if(serveur.isConnected()){
			
		}
	}

	@Override
	public void initServeurModule(UpdateParamInterface engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMusicalStyle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isConnected() {
		try ( 
			    ServerSocket serverSocket = new ServerSocket(portNumber);
			    Socket clientSocket = serverSocket.accept();
			    PrintWriter out =
			        new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			        new InputStreamReader(clientSocket.getInputStream()));
			){
			    String inputLine, outputLine;
			    outputLine = "Hello";
			    out.println(inputLine);
			    System.out.println(outputLine);
			    while ((inputLine = in.readLine()) != null) {
			        out.println(outputLine);
			        if (outputLine.equals("OK"))
			            return true;
			    }
			    return false;
		}
	
	}

}
