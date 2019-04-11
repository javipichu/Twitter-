package Twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * @author jalvarezotero
 */
public class EstadodeActualizacion {
    public static void main(String[] args) {
        args = new String[]{"txt"};
        if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.tweets.UpdateStatus [text]");
            System.exit(-1);
        }
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            try {
              
                RequestToken requestToken = twitter.getOAuthRequestToken();
                System.out.println("Got request token.");
                System.out.println("Request token: " + requestToken.getToken());
                System.out.println("Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                    System.out.println("Abre la siguiente URL y dale acceso a su cuenta:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Introduce el PIN(si es correcto) y pulsa enter después de que hayas concedido el acceso.[PIN]:");
                    String pin = br.readLine();
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            System.out.println("Incapaz de obtener el acceso token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                System.out.println("Tengo acceso token.");
                System.out.println("Acceso a s token: " + accessToken.getToken());
                System.out.println("Acceso a token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                
                if (!twitter.getAuthorization().isEnabled()) {
                    System.out.println("DefaultAuth consumer key/secret is not set.");
                    System.exit(-1);
                }
            }
            Status status = twitter.updateStatus(args[0]);
            System.out.println("Se actualizó con éxito el estado a [" + status.getText() + "].");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Error al obtener la línea de tiempo: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Error al leer la entrada del sistema.");
            System.exit(-1);
        }
        
    }
}
