package serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.ServeurInterface;
import interfaces.UpdateParamInterface;

public class Serveur implements ServeurInterface extends Thread {

	private int info;
	
	public void run(){
		String args = "5869";
		int portNumber = Integer.parseInt(args);
		
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
		    while (true) {
		    	if (inputLine != null){
		    		inputLine = in.readLine()
		    		System.out.println(inputLine);
		    		info = inputLine;
		    	}
		        
		    }
		}
	}
	
	@Override
	public int getVolume() {
		return (int) (info/10);
	}

	@Override
	public void initServeurModule(UpdateParamInterface engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStyle() {
		return info%10;
	}

	@Override
	public boolean isConnected() {
	    return false;
	
	}

}
