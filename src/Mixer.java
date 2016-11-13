

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;


public class Mixer extends Thread{
	int idUser;
	InetAddress ipUser;
	int portUser;
	byte buffer[][];
	byte bufferOut[];
	BufferCenter bufferCenter;
    
    public Mixer(int id,InetAddress IP,int port,BufferCenter buff){
    	idUser = id;
    	ipUser = IP;
        portUser = port;
        buffer = new byte[5][512];
        bufferOut = new byte[512];
        bufferCenter = buff;
    }
    
    public void run(){
    	
    	try {
			DatagramSocket sOut = new DatagramSocket();
			DatagramPacket pOut;
			while(true){
				
	    		for(int i = 0; i < buffer.length; i++){ //Enche o buffer

	    			if(i >= bufferCenter.getNumUsers()){
	    				//System.out.println("N�mero de usu�rios: "+ bufferCenter.getNumUsers());
	    				break;
	    			}
	    			
	    			if(i != idUser){
		    			//System.out.println("Mixer "+idUser+" getbuffer em "+i);
	    				byte temp[] = bufferCenter.getBuffer(i);
	    				if(temp == null){
	    					i--;
	    					continue;
	    				}
	    				buffer[i] = temp.clone();
	    				//System.out.println("Buffer "+buffer[i][1]);
	    			}

	    		}
	    		
	    		//System.out.println("----------------->Passou");
	    		
//	    		if(idUser == 0) bufferOut = buffer[1].clone();
//	    		else if(idUser == 1) bufferOut = buffer[0].clone();
	    		for(int i = 0; i < buffer[0].length; i++){
	    			bufferOut[i] = (byte) (buffer[0][i] + buffer[1][i] + buffer[2][i] 
	    									+ buffer[3][i] + buffer[4][i]);
	    		}
	    		
	    		
	    		//Enviar pacote mixado
	    		pOut = new DatagramPacket(bufferOut, bufferOut.length, ipUser, portUser+1);
	    		sOut.send(pOut);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    	
    		
    		
    		
    		
    		
    		
    		
    	
    	
    }
}