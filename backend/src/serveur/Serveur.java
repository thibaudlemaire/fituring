package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.ServeurInterface;
import interfaces.UpdateParamInterface;

public class Serveur implements ServeurInterface, Runnable {

	private int info;
	
	public void run(){
		String args = "5872";
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
		    out.println(outputLine);
		    System.out.println(outputLine);
		    while (true) {
		    	inputLine = in.readLine();
		    	if (inputLine != null){		    		
		    		//System.out.println(inputLine);
		    		info = Integer.parseInt(inputLine);
		    	}
		        
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
