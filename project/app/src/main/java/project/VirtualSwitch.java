package project;

import java.io.IOException;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;

public class VirtualSwitch {
    private String switchID;
    private Map<String, String> macTable = new HashMap<>(); // Maps MAC to incoming port
    private Map<String, InetSocketAddress> ports = new HashMap<>(); // Neighboring devices

    public VirtualSwitch(String switchID, String configFile) throws IOException {
        this.switchID = switchID;
        DataParser parser = new DataParser();
        List<JsonNode> neighbors = parser.find(switchID);
        
        for (JsonNode node : neighbors) {
            List<JsonNode> details = parser.parser(Collections.singletonList(node));
            String neighborID = node.asText();
            String ip = details.get(0).asText();
            int port = details.get(1).asInt();
            ports.put(neighborID, new InetSocketAddress(ip, port));
        }
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(ports.get(switchID).getPort())) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Switch " + switchID + " started.");

            while (true) {
                socket.receive(packet);
                processFrame(packet, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFrame(DatagramPacket packet, DatagramSocket socket) throws IOException {
        byte[] data = packet.getData();
        String srcMac = new String(Arrays.copyOfRange(data, 0, 6));
        String destMac = new String(Arrays.copyOfRange(data, 6, 12));
        String message = new String(Arrays.copyOfRange(data, 12, packet.getLength()));
        
        String incomingPort = packet.getAddress().getHostAddress() + ":" + packet.getPort();
        macTable.put(srcMac, incomingPort);

        if (macTable.containsKey(destMac)) {
            String outPort = macTable.get(destMac);
            sendFrame(socket, outPort, data);
        } else {
            floodFrame(socket, incomingPort, data);
        }
    }

    private void sendFrame(DatagramSocket socket, String outPort, byte[] data) throws IOException {
        InetSocketAddress target = ports.get(outPort);
        DatagramPacket packet = new DatagramPacket(data, data.length, target);
        socket.send(packet);
    }

    private void floodFrame(DatagramSocket socket, String excludePort, byte[] data) throws IOException {
        for (Map.Entry<String, InetSocketAddress> entry : ports.entrySet()) {
            if (!entry.getKey().equals(excludePort)) {
                DatagramPacket packet = new DatagramPacket(data, data.length, entry.getValue());
                socket.send(packet);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java VirtualSwitch <SwitchID>");
            return;
        }
        new VirtualSwitch(args[0], "config.json").start();
    }
}
