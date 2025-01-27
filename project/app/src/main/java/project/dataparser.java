package project;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class dataparser {
    String id;
    String filepathlocation = "app/src/main/java/project/config.json";

    public dataparser(String id){
     id= this.id;
      
    }

    public ArrayList find() throws IOException{

        ObjectMapper mapper = new ObjectMapper();

     
        File jsonFile = new File(filepathlocation);
        System.out.println(jsonFile);
        JsonNode rootNode = mapper.readTree(jsonFile);

   
        JsonNode idNode = rootNode.get("id");
            
            ObjectNode idObjectNode = (ObjectNode) idNode;

         
            JsonNode PCAtargetNode = idObjectNode.get("a");
      
            ArrayNode PCAtargetArray = (ArrayNode) PCAtargetNode;

 



        ArrayList obArrayList = new ArrayList<>();
        return obArrayList;
    }




    
}
