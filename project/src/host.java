import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class host {
    public static void main(String[] args) {
        String result;

        Gson gson = new Gson();

        FileReader reader;
        {
            try {
                reader = new FileReader("project/lib/config.json");
                JsonObject jobj = new Gson().fromJson(reader, JsonObject.class);
                result = jobj.get("id").toString();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println(result);
        }
    }


}
