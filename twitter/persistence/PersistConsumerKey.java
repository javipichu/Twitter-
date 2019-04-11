package twitter.persistence;

import java.io.*;
import java.util.Scanner;

public class PersistConsumerKey implements Persistable, Serializable {

    private final File file = new File("consumer.dat");
    private String apikey;
    private String apisecret;
   


    public PersistConsumerKey() {
    }

    public PersistConsumerKey(String apikey, String apisecret) {
        this.apikey = apikey;
        this.apisecret = apisecret;
    }

    public void setDefault() {
        apikey = "VABL4GvmuxIFNgMOGyFmwW2xZ";
        apisecret = "ZFPKX57M1Ph6s3wbjwTnjE2V0UKiRITRZgHRMdWPb2FAPY5k0Z";
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
            System.out.println(file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("IOException on saving consumers ***");
            e.printStackTrace();
        }
    }

    public void readKey() throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        this.apikey = scan.nextLine();
        this.apisecret = scan.nextLine();
        System.out.println("OK");
       
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
