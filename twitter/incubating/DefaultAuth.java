package twitter.incubating;

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

public class DefaultAuth {
    public static void main(String args[]) throws Exception{


        PersistAccessToken token = new PersistAccessToken();
        PersistConsumerKey consumer = new PersistConsumerKey();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer.getApikey())
                .setOAuthConsumerSecret(consumer.getApisecret());
                //.setOAuthAccessToken(token.getToken())
                //.setOAuthAccessTokenSecret(token.getSecretToken());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();





        // The factory instance is re-useable and thread safe.
        //Twitter twitter = TwitterFactory.getSingleton();
        //twitter.setOAuthConsumer("[consumer key]", "[consumer secret]");
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            String pin = br.readLine();
            try{
                if(pin.length() > 0){
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                }else{
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if(401 == te.getStatusCode()){
                    System.out.println("Unable to get the access token.");
                }else{
                    te.printStackTrace();
                }
            }
        }
        //persist to the accessToken for future reference.
        storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
        Status status = twitter.updateStatus(args[0]);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
        System.exit(0);
    }
    private static void storeAccessToken(long useId, AccessToken accessToken){
        //store accessToken.getToken()
        //store accessToken.getTokenSecret()
    }

}