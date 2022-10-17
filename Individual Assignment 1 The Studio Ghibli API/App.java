import java.io.BufferedReader;
//import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.Scanner;
import java.util.Scanner;

import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class App{
    public static void main(String[] args) {
        try {
            URL url = new URL("https://ghibliapi.herokuapp.com/films/");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(
                httpConn.getInputStream()));

                System.out.println(httpConn.getResponseCode());
                System.out.println(httpConn.getResponseMessage());

                boolean done = false;
                String responseLine = "";
                String updatedMovie = "";
                

                while (!done) {
                    
                    responseLine = in.readLine();

                    if (responseLine == null) {
                        done = true;
                    }
                    else {
                        updatedMovie = updatedMovie + responseLine + " ";
                    }
                }
               
               JsonParser parser = new JsonParser();
                JsonArray movieArray = (JsonArray) parser.parse(updatedMovie.toString());
                
                for(JsonElement movie: movieArray)
            {
                System.out.println("Title: " + movie.getAsJsonObject().get("title").getAsString());
                System.out.println("Description: " + movie.getAsJsonObject().get("description").getAsString());
                System.out.println();

            }

             Scanner scan = new Scanner(System.in);
             System.out.println("Please enter the name of the movie, or enter quit to exit: ");
            String userInput = scan.nextLine();
            System.out.println();
            
            while (!userInput.equalsIgnoreCase("quit")) {
                boolean found = false;

                for (JsonElement movie: movieArray) {
                    if (userInput.equals(movie.getAsJsonObject().get("title").getAsString())) {
                        found = true;
                        System.out.println("Title: " + movie.getAsJsonObject().get("title").getAsString());
                        System.out.println("Description: " + movie.getAsJsonObject().get("description").getAsString());
                        System.out.println("Release Date: " + movie.getAsJsonObject().get("release_date").getAsString());
                        System.out.println("Producer: " + movie.getAsJsonObject().get("producer").getAsString());
                        System.out.println();
                        break;
                    }
                }
                if (found == false) {
                    System.out.println("Movie not found " + " ");
                }
                System.out.println("Please enter the name of the movie, or enter quit to exit: ");
                userInput = scan.nextLine();
            }
        }

        catch(Exception e) {
            System.out.println("Movie not found.");
            e.printStackTrace();
        }
    }
}