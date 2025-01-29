package project;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class dataparser {
   
     String filepathlocation = "app/src/main/java/project/config.json";
      
       
    
        public ArrayList<JsonNode> find(String id) throws IOException{
    
            ObjectMapper mapper = new ObjectMapper();
    
         
            File jsonFile = new File(filepathlocation);
        System.out.println(jsonFile);
        JsonNode rootNode = mapper.readTree(jsonFile);

   
        JsonNode idNode = rootNode.get("topology");
            
            ObjectNode idObjectNode = (ObjectNode) idNode;

         
            JsonNode PCAtargetNode = idObjectNode.get(id);
      
            ArrayNode PCAtargetArray = (ArrayNode) PCAtargetNode;

 


            

            
            ArrayList<JsonNode> obArrayList = new ArrayList<>();
            PCAtargetArray.forEach(obArrayList::add);
    
            return obArrayList;
    }
    public ArrayList<JsonNode> parser(ArrayList<JsonNode> obArrayList) throws IOException{
        ObjectMapper mapper = new ObjectMapper();

     
        File jsonFile = new File(filepathlocation);
        System.out.println(jsonFile);
        ArrayList<JsonNode> data = new ArrayList<>();
        JsonNode rootNode = mapper.readTree(jsonFile);

   
        JsonNode idNode = rootNode.get("id");
            
            ObjectNode idObjectNode = (ObjectNode) idNode;
            for(JsonNode id:obArrayList){
                String ids=id.toPrettyString();

                JsonNode PCAtargetNode = idObjectNode.get(ids);
      
                ArrayNode PCAtargetArray = (ArrayNode) PCAtargetNode;
                PCAtargetArray.forEach(obArrayList::add);
            }
         
            

 


            

            
         
            


            return data;

    }




    
}
