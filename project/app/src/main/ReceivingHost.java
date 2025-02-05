package project;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;

public class ReceivingHost {
    private String hostID, macAddress;
    private DatagramSocket socket;

    public ReceivingHost(String hostID, String configFile) {
        this.hostID = hostID;
        this.macAddress = hostID; //same value -> pass as arg
// constructor receivHst reads host if & port  from config file 

        try {
            dataparser parser = new dataparser();
            ArrayList<JsonNode> hostData = parser.find(hostID);
// init data parser & get host data from config file 

            int hostPort = 0;
            for (JsonNode node : hostData) {
                if (node.has("port")) {
                    hostPort = node.get("port").asInt();
                }
            }
// lopp hostID in config to find its port number 


            if (hostPort == 0) {
                System.out.println("Port not found");
                System.exit(1);
            }
// if port not found-> exit program. 

            this.socket = new DatagramSocket(hostPort); //-> bind socket to get msgs
// make udp socket in host's port 

            System.out.println("Receiving host: " + hostID + " listening on port " + hostPort);
        } catch (Exception e) {
            System.err.println("Error initializing host: " + e.getMessage());
            System.exit(1);
        }
    }

    private void listenForFrames() {
        byte[] buffer = new byte[1024]; // mem for packets
        while (true) { //always listening 
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // recive packet
//make udp packet to store data + wait for packet to put in buffer
                
                // extract vf + decode binaries 
                byte[] frame = packet.getData();
                String sourceMac = new String(frame, 0, 10, StandardCharsets.UTF_8).trim();//1st 10 bytes 
                String destMac = new String(frame, 10, 10, StandardCharsets.UTF_8).trim();//next 10
                String message = new String(frame, 20, frame.length - 20, StandardCharsets.UTF_8).trim();//remining 

                // check if frame is mine 
                if (destMac.equals(macAddress)) {
                    System.out.println("Received message from " + sourceMac + ": " + message);
                } else {
                    System.out.println("Frame Ignored.");
                }

            } catch (Exception e) {
                System.out.println("Error receiving frame \nError: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ReceivingHost <HostID> <ConfigFile>");
            return;
        }
        ReceivingHost receiver = new ReceivingHost(args[0], args[1]);
        receiver.listenForFrames(); // start listening for frames
    }
}