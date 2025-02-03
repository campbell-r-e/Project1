package project;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;

import java.nio.charset.StandardCharsets;

public class Host {
    private String hostID,macAddress;
    private InetAddress switchIP;
    private int switchport, hostport;
    private DatagramSocket socket;

    public Host(String hostID, String configFile) {
        this.hostID=hostID;
        this.macAddress=hostID;
        try{
            dataparser parser=new dataparser();
            ArrayList<JsonNode> hostData=parser.find(hostID);
            for (JsonNode node: hostData){
                if (node.has("port")) {
                    this.hostport = node.get("port").asInt();
                }
                if(node.has("switchIP")){
                    this.switchIP=InetAddress.getByName(node.get("switchIP").asText());
                }
                if(node.has("switchport")){
                    this.switchport=node.get("switchport").asInt();
                }
                
            }
            if(this.switchIP== null || this.switchport==0){
                System.out.println("Invalid Config!");
                System.exit(1);
            }
            this.socket=new DatagramSocket(hostport);
            System.out.println("Host: " + hostID + "initialized at " + hostport);
            System.out.println("connected to switch " + switchIP + "at" + switchport);
        }catch (Exception e) {
            System.err.println("Error initializing host: " + e.getMessage());
            System.exit(1); 
        }
    }
    private byte[]createFrame(String destMac, String Message){
        byte[] sourceMacBytes = binaryConversion(macAddress, 6);  // Fixed 6 bytes for MAC
        byte[] destMacBytes = binaryConversion(destMac, 6);
        byte[] messageBytes = binaryConversion(Message, 64);
        byte[]frame=new byte[sourceMacBytes.length+destMacBytes.length+messageBytes.length];
        System.arraycopy(sourceMacBytes,0,frame,0,sourceMacBytes.length);
        System.arraycopy(destMacBytes,0,frame,sourceMacBytes.length,destMacBytes.length);
        System.arraycopy(messageBytes, 0, frame, sourceMacBytes.length + destMacBytes.length, messageBytes.length);
        return frame;
    }

    private void frameSender(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            System.out.println("Enter Destination MAC ADDRESS: ");
            String destMac=scanner.nextLine().trim();
            System.out.println("Enter Message");
            String Message=scanner.nextLine().trim();
            byte[]frame=createFrame(destMac, Message);
            DatagramPacket packet=new DatagramPacket(frame,frame.length,switchIP,switchport);
            try{
                socket.send(packet);
                System.out.println("Sent to: "+ destMac);
            } catch(Exception e){
                System.out.println("Error Sending frame! \n Message: "+ e.getMessage());
            }

        }
    }
    private byte[]binaryConversion(String input, int length){
        byte[]binaryData=new byte[length];
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(inputBytes, 0, binaryData, 0, Math.min(inputBytes.length, length));
        return binaryData;

    }

    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("usage: Java Host <HostID> <ConfigFile>");
            return;
        }
        Host host=new Host(args[0], args[1]);
        host.frameSender();
    }








}
    

    

