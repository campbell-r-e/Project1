//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package project;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class Switch {
    private String switchID;
    private Map<String, String> macTable = new HashMap();
    private Map<String, InetSocketAddress> ports = new HashMap();

    public Switch(String switchID, String configFile) throws IOException {
        this.switchID = switchID;
        dataparser parser = new dataparser();
        List<JsonNode> neighbors = parser.find(switchID);
        Iterator var6 = neighbors.iterator();

        while(var6.hasNext()) {
            JsonNode node = (JsonNode)var6.next();
            List<JsonNode> details = parser.parser((ArrayList<JsonNode>) Collections.singletonList(node));
            String neighborID = node.asText();
            String ip = ((JsonNode)details.get(0)).asText();
            int port = ((JsonNode)details.get(1)).asInt();
            this.ports.put(neighborID, new InetSocketAddress(ip, port));
        }

    }

    public void start() throws Throwable {
        try {
            Throwable var1 = null;
            Object var2 = null;

            try {
                DatagramSocket socket = new DatagramSocket(((InetSocketAddress)this.ports.get(this.switchID)).getPort());

                try {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    System.out.println("Switch " + this.switchID + " started.");

                    while(true) {
                        socket.receive(packet);
                        this.processFrame(packet, socket);
                    }
                } finally {
                    if (socket != null) {
                        socket.close();
                    }

                }
            } catch (Throwable var13) {
                if (var1 == null) {
                    var1 = var13;
                } else if (var1 != var13) {
                    var1.addSuppressed(var13);
                }

                throw var1;
            }
        } catch (IOException var14) {
            var14.printStackTrace();
        }
    }

    private void processFrame(DatagramPacket packet, DatagramSocket socket) throws IOException {
        byte[] data = packet.getData();
        String srcMac = new String(Arrays.copyOfRange(data, 0, 6));
        String destMac = new String(Arrays.copyOfRange(data, 6, 12));
        new String(Arrays.copyOfRange(data, 12, packet.getLength()));
        String var10000 = packet.getAddress().getHostAddress();
        String incomingPort = var10000 + ":" + packet.getPort();
        this.macTable.put(srcMac, incomingPort);
        if (this.macTable.containsKey(destMac)) {
            String outPort = (String)this.macTable.get(destMac);
            this.sendFrame(socket, outPort, data);
        } else {
            this.floodFrame(socket, incomingPort, data);
        }

    }

    private void sendFrame(DatagramSocket socket, String outPort, byte[] data) throws IOException {
        InetSocketAddress target = (InetSocketAddress)this.ports.get(outPort);
        DatagramPacket packet = new DatagramPacket(data, data.length, target);
        socket.send(packet);
    }

    private void floodFrame(DatagramSocket socket, String excludePort, byte[] data) throws IOException {
        Iterator var5 = this.ports.entrySet().iterator();

        while(var5.hasNext()) {
            Map.Entry<String, InetSocketAddress> entry = (Map.Entry)var5.next();
            if (!((String)entry.getKey()).equals(excludePort)) {
                DatagramPacket packet = new DatagramPacket(data, data.length, (SocketAddress)entry.getValue());
                socket.send(packet);
            }
        }

    }

    public static void main(String[] args) throws IOException, Throwable {
        if (args.length != 1) {
            System.out.println("Usage: java VirtualSwitch <SwitchID>");
        } else {
            (new Switch(args[0], "config.json")).start();
        }
    }
}
