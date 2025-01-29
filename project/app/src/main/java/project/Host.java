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






}
    

    

