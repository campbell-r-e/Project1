package project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataParser {
    private static final String FILE_PATH = "config.json";

    public List<JsonNode> find(String id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File(FILE_PATH));
        JsonNode topologyNode = rootNode.get("topology").get(id);
        
        List<JsonNode> neighbors = new ArrayList<>();
        if (topologyNode != null && topologyNode.isArray()) {
            topologyNode.forEach(neighbors::add);
        }
        return neighbors;
    }

    public List<JsonNode> parser(List<JsonNode> neighbors) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File(FILE_PATH));
        JsonNode idNode = rootNode.get("id");

        List<JsonNode> details = new ArrayList<>();
        for (JsonNode node : neighbors) {
            JsonNode device = idNode.get(node.asText());
            if (device != null) {
                device.forEach(details::add);
            }
        }
        return details;
    }
}
