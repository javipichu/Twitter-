package twitter.persistence;

import java.io.*;
import java.util.Scanner;

public class PersistConsumerKey implements Persistable, Serializable {

    String apikey;
    String apisecret;


    File file = new File("twitter/persistence/consumer.txt");

    public PersistConsumerKey() {
    }

    public PersistConsumerKey(String apikey, String apisecret) {
        this.apikey = apikey;
        this.apisecret = apisecret;
    }

    public void setDefault() {
        apikey = "VK9nTYQvKx76Doj6fAPPZdGmm";
        apisecret = "8TvU3Sf5YwgCWbqvBdVDqGZppiOk3TUZcQgbH88xmxeeD4ATib";
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }

    public void saveKey() {
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println(apikey);
            pw.println(apisecret);
        } catch (IOException e) {
            System.out.println("IOException on saving consumers ***");
            e.printStackTrace();
        }
    }

    public void readKey() throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        this.apikey = scan.nextLine();
        this.apisecret = scan.nextLine();
        System.out.println("Read consumer OK");
    }

    @Override
    public String toString() {
        return "PersistConsumerKey{" +
                "apikey='" + apikey + '\'' +
                ", apisecret='" + apisecret + '\'' +
                ", file=" + file +
                ", exists=" + file.exists() +
                '}';
    }
}