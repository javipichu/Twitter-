package twitter.persistence;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.Scanner;

public class PersistAccessToken implements Persistable, Serializable {

    public static final File file = new File("token.dat");
    private String token;
    private String secretToken;
   
    private Scanner scan;

    public PersistAccessToken() {
    }

    public PersistAccessToken(String token, String secretTk) {
        this.token = token;
        this.secretToken = secretTk;
    }

    public void setDefault() {
        token = "**************************************************";
        secretToken = "*********************************************";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public void saveKey() {
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println(token);
            pw.println(secretToken);
        } catch (IOException e) {
            System.out.println("IOException on saving tokens ***");
            e.printStackTrace();
        }
        System.out.println("Token guardado.");
    }

    public void readKey() throws FileNotFoundException {
        scan = new Scanner(file);
        this.token = scan.nextLine();
        this.secretToken = scan.nextLine();
        System.out.println("Read token OK");
    }

    public void removeKey() {
        if (scan != null)
            scan.close();
        file.delete();
      
    }


    public void createAccessToken(PersistConsumerKey consumer) throws IOException, TwitterException {
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer.getApikey())
                .setOAuthConsumerSecret(consumer.getApisecret());
        Twitter OAuthTwitter = new TwitterFactory(configBuilder.build()).getInstance();
        RequestToken requestToken = null;
        AccessToken accessToken = null;
        String url = null;
        do {
            try {
                requestToken = OAuthTwitter.getOAuthRequestToken();
                
                url = requestToken.getAuthorizationURL();
                System.out.println("URL:");
                System.out.println(requestToken.getAuthorizationURL());
            } catch (TwitterException ex) {
               
            }
            BufferedReader lectorTeclado = new BufferedReader(new InputStreamReader(System.in));
           
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("firefox " + url);
            } catch (Exception e) {
                System.out.println("Cant find firefox. Copy console link;");
            }
            //Nos avisa de que introduciremos el PIN a continuación
            System.out.print("\n\nOAuth PIN: ");
          
            String pin = lectorTeclado.readLine();
            if (pin.length() > 0) {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken, pin);
            } else {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken);
            }
        } while (accessToken == null);
        System.out.println("\n\nAccess Tokens OK\n Access Granted!\n");
        token = accessToken.getToken();
        secretToken = accessToken.getTokenSecret();
     
    }
}
