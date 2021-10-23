import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class Secure {

public static void main(String[] args) {
        try {
                securePersistentMethod();
        } catch (Exception e) {
                e.printStackTrace();
        }

}

        /**
         * Get some random data and persist in database, there's no way this wouldn't work
         */
        private static void securePersistentMethod() throws Exception {
                URL url = new URL("https://some.site.we.totally.trust");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept", "*/*");
                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                String response = mapper.readValue(responseStream, String.class);

                Connection db = DriverManager.getConnection("url");
                Statement statement = db.createStatement();
                String query = "SELECT secret FROM Users WHERE (username = '" + response + "' AND NOT role = 'admin')";
                ResultSet result = statement.executeQuery(query);
        }
}