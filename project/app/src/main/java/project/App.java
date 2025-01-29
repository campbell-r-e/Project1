
package project;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.Scanner;

public class App {
     public static void main(String[] args) throws Exception {
        String filepathlocation = "app/src/main/java/project/config.json";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter PC A IP address");
        String PCIP = scanner.nextLine();
        System.out.println("Enter PC A port number ");
        String PCAPort = scanner.nextLine();
        int PCAportfinal = Integer.parseInt(PCAPort);


        System.out.println("Enter PC B IP address");
        String PCBIP = scanner.nextLine();
        System.out.println("Enter PC B port number ");
        String PCBPort = scanner.nextLine();
        int PCBportfinal = Integer.parseInt(PCBPort);


        System.out.println("Enter PC c IP address");
        String PCcIP = scanner.nextLine();
        System.out.println("Enter PC c port number ");
        String PCcPort = scanner.nextLine();
        int PCcportfinal = Integer.parseInt(PCcPort);
        
       
        System.out.println("Enter PC D IP address");
        String PCDIP = scanner.nextLine();
        System.out.println("Enter PC D port number ");
        String PCDPort = scanner.nextLine();
        int PCDportfinal = Integer.parseInt(PCDPort);

       // S1 

       System.out.println("Enter S1 IP address");
       String S1IP = scanner.nextLine();
       System.out.println("Enter S1 port number ");
       String S1Port = scanner.nextLine();
       int S1portfinal = Integer.parseInt(S1Port);

       // S2
       System.out.println("Enter S2 IP address");
       String S2IP = scanner.nextLine();
       System.out.println("Enter S2 port number ");
       String S2Port = scanner.nextLine();
       int S2portfinal = Integer.parseInt(S2Port);




       //S3

       System.out.println("Enter S3 IP address");
       String S3IP = scanner.nextLine();
       System.out.println("Enter S3 port number ");
       String S3Port = scanner.nextLine();
       int S3portfinal = Integer.parseInt(S3Port);




       //








        scanner.close();

     // modified from chatgpt snippet 
     ObjectMapper mapper = new ObjectMapper();

     
        File jsonFile = new File(filepathlocation);
        System.out.println(jsonFile);
        JsonNode rootNode = mapper.readTree(jsonFile);

   
        JsonNode idNode = rootNode.get("id");
            
            ObjectNode idObjectNode = (ObjectNode) idNode;

         
            JsonNode PCAtargetNode = idObjectNode.get("a");
      
            ArrayNode PCAtargetArray = (ArrayNode) PCAtargetNode;

                // Update the elements in the array
            PCAtargetArray.set(0, mapper.convertValue(PCIP, JsonNode.class)); // Update IP
            PCAtargetArray.set(1, mapper.convertValue(PCAportfinal, JsonNode.class));         // Update port
            
          //PCB
          JsonNode PCBtargetNode = idObjectNode.get("b");
      
          ArrayNode PCBtargetArray = (ArrayNode) PCBtargetNode;

              // Update the elements in the array
          PCBtargetArray.set(0, mapper.convertValue(PCBIP, JsonNode.class)); // Update IP
          PCBtargetArray.set(1, mapper.convertValue(PCBportfinal, JsonNode.class));         // Update port
          
   

          //

         //PC C
         JsonNode PCCtargetNode = idObjectNode.get("c");
      
         ArrayNode PCCtargetArray = (ArrayNode) PCCtargetNode;

             // Update the elements in the array
         PCCtargetArray.set(0, mapper.convertValue(PCcIP, JsonNode.class)); // Update IP
         PCCtargetArray.set(1, mapper.convertValue(PCcportfinal, JsonNode.class));         // Update port
         
  

         //




         //PC D
         JsonNode PCDtargetNode = idObjectNode.get("d");
      
         ArrayNode PCDtargetArray = (ArrayNode) PCDtargetNode;

             // Update the elements in the array
         PCDtargetArray.set(0, mapper.convertValue(PCDIP, JsonNode.class)); // Update IP
         PCDtargetArray.set(1, mapper.convertValue(PCDportfinal, JsonNode.class));         // Update port
         
  

         //




         
         //S1 
         JsonNode S1targetNode = idObjectNode.get("s1");
      
         ArrayNode S1targetArray = (ArrayNode) S1targetNode;

             // Update the elements in the array
         S1targetArray.set(0, mapper.convertValue(S1IP, JsonNode.class)); // Update IP
         S1targetArray.set(1, mapper.convertValue(S1portfinal, JsonNode.class));         // Update port
         
  

         //

         


           //S2
           JsonNode S2targetNode = idObjectNode.get("s2");
      
           ArrayNode S2targetArray = (ArrayNode) S2targetNode;
  
               // Update the elements in the array
           S2targetArray.set(0, mapper.convertValue(S2IP, JsonNode.class)); // Update IP
           S2targetArray.set(1, mapper.convertValue(S2portfinal, JsonNode.class));         // Update port
           
    
  
           //



           
           //S3
           JsonNode S3targetNode = idObjectNode.get("s3");
      
           ArrayNode S3targetArray = (ArrayNode) S3targetNode;
  
               // Update the elements in the array
           S3targetArray.set(0, mapper.convertValue(S3IP, JsonNode.class)); // Update IP
           S3targetArray.set(1, mapper.convertValue(S3portfinal, JsonNode.class));         // Update port
           
    
  
           //





        // Write the updated JSON back to the file
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);

      







     //

    
    }
}
