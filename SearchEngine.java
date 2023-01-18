import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> terms = new ArrayList<String>(); 
    public String listStr(ArrayList<String> a){
        StringBuilder str = new StringBuilder();
        for (Object o : terms){
            str.append(o.toString());
            str.append("\t");
        }
        return str.toString();
    }
    

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return listStr(terms);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}