package serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.ServeurInterface;
import interfaces.UpdateParamInterface;

public class serveur implements ServeurInterface  {

	@Override
	public int getVolume() {
		int portNumber = Integer.parseInt(args[0]);

		try ( 
		    ServerSocket serverSocket = new ServerSocket(portNumber);
		    Socket clientSocket = serverSocket.accept();
		    PrintWriter out =
		        new PrintWriter(clientSocket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(clientSocket.getInputStream()));
		){
		    String inputLine, outputLine;
		    // Initiate conversation with client
		    KnockKnockProtocol kkp = new KnockKnockProtocol();
		    outputLine = kkp.processInput(null);
		    out.println(outputLine);

		    while ((inputLine = in.readLine()) != null) {
		        outputLine = kkp.processInput(inputLine);
		        out.println(outputLine);
		        if (outputLine.equals("Bye."))
		            break;
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
		// TODO Auto-generated method stub
		return false;
	}

}
