package twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import twitter4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author fsancheztemprano
 */
public class Auth {

    Auth() throws IOException, TwitterException { //Constructor de la clase
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey("1b305HBgN4142WYIoy3GhJhry")//deprecated
                .setOAuthConsumerSecret("GoXjVEyLHAB122MNJ0OkQMTgFjxxo2WLTsg3UjeJR8soup1Krg");//deprecated
        Twitter OAuthTwitter = new TwitterFactory(configBuilder.build()).getInstance();
        RequestToken requestToken = null;
        AccessToken accessToken = null;
        String url = null;
        
        do {
            try {
                requestToken = OAuthTwitter.getOAuthRequestToken();
                System.out.println("Request Token obtenido con éxito.");
                System.out.println("Request Token: " + requestToken.getToken());
                System.out.println("Request Token secret: " + requestToken.getTokenSecret());
                url = requestToken.getAuthorizationURL();
                System.out.println("URL:");
                System.out.println(requestToken.getAuthorizationURL());
            } catch (TwitterException ex) {
                //Logger.getLogger(TwitterJavaGT.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader lectorTeclado = new BufferedReader(new InputStreamReader(System.in));
//Abro el navegador. Firefox, en este caso.
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("firefox " + url);
            } catch (Exception e) {
            }
//Nos avisa de que introduciremos el PIN a continuación
            System.out.print("Introduce el PIN del navegador y pulsa intro.nn PIN: ");
//Leemos el PIN
            String pin = lectorTeclado.readLine();
            if (pin.length() > 0) {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken, pin);
            } else {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken);
            }
        } while (accessToken == null);
    }

}
