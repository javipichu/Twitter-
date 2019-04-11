package Twitter;

import twitter.persistence.PersistAccessToken;
import twitter.persistence.PersistConsumerKey;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DefectoAuth {
    public static void main(String args[]) throws Exception{


        PersistAccessToken token = new PersistAccessToken();
        PersistConsumerKey consumer = new PersistConsumerKey();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer.getApikey())
                .setOAuthConsumerSecret(consumer.getApisecret());
              
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();





       
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Abre la siguiente URL y proporciona acceso a su cuenta:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Ingresa el PIN (si está disponible) o simplemente presiona enter.[PIN]:");
            String pin = br.readLine();
            try{
                if(pin.length() > 0){
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                }else{
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if(401 == te.getStatusCode()){
                    System.out.println("Incapaz de obtener el acceso a token.");
                }else{
                    te.printStackTrace();
                }
            }
        }
      
        storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
        Status status = twitter.updateStatus(args[0]);
        System.out.println("Se actualizó con éxito el estado a [" + status.getText() + "].");
        System.exit(0);
    }
    private static void storeAccessToken(long useId, AccessToken accessToken){
        
    }

}
