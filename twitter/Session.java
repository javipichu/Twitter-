package twitter;

import twitter.persistence.PersistAccessToken;
import twitter.persistence.PersistConsumerKey;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;

public class Session {

    Twitter twitter;
    PersistConsumerKey consumer;
    PersistAccessToken token;


    public Session() {
        this(false);
    }

    public Session(boolean persist) {
        consumer = new PersistConsumerKey();
        try {
            consumer.readKey();
            System.out.println("Consumer read from file.");
        } catch (IOException e) {
            System.out.println("Consumer file not found");
            consumer.setDefault();
            consumer.saveKey();
            System.out.println("Defaults consumer set and saved!");
        }

        token = new PersistAccessToken();
        if (!persist)
            token.removeKey();
        try {
            token.readKey();
            System.out.println("Token read from file.");
        } catch (IOException e) {
            System.out.println("Token file not found -> OAuth");
            try {
                token.createAccessToken(consumer);
            } catch (IOException | TwitterException ex) {
                ex.printStackTrace();
            }
            //token.setDefault();
            //e.printStackTrace();
        }

        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer.getApikey())
                .setOAuthConsumerSecret(consumer.getApisecret())
                .setOAuthAccessToken(token.getToken())
                .setOAuthAccessTokenSecret(token.getSecretToken());
        twitter = new TwitterFactory(configBuilder.build()).getInstance();


        try {
            System.out.println("Welcome @" + twitter.showUser(twitter.getScreenName()).getScreenName());

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void printTimeline() {
        Paging pagina = new Paging();
        pagina.setCount(50);
        ResponseList<Status> listado = null;
        try {
            listado = twitter.getHomeTimeline(pagina);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listado.size(); i++) {
            System.out.printf("%30s | %15s | %100s %n", listado.get(i).getCreatedAt().toString(), ("@" + listado.get(i).getUser().getScreenName()), listado.get(i).getText());
        }
    }

    public void updateStatus(String string) {
        try {
            twitter.updateStatus(string);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void saveSession() {
        token.saveKey();
    }

    public void clearSession() {
        token.removeKey();
    }

}
