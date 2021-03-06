package twitter;

import twitter.persistence.PersistAccessToken;
import twitter.persistence.PersistConsumerKey;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;

class Session {

    private final Twitter twitter;
    private final PersistConsumerKey consumer;
    private final PersistAccessToken token;


    public Session() throws TwitterException {
        this(false);
    }

    public Session(boolean persist) throws TwitterException {
        consumer = new PersistConsumerKey();
        try {
            consumer.readKey();
            //System.out.println("Consumer read from file.");
        } catch (IOException e) {
            //System.out.println("Consumer file not found");
            consumer.setDefault();
            //consumer.saveKey();
            System.out.println("Defaults consumer set!");
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //token.setDefault();TwitterException ex
            //e.printStackTrace();
        }

        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer.getApikey())
                .setOAuthConsumerSecret(consumer.getApisecret())
                .setOAuthAccessToken(token.getToken())
                .setOAuthAccessTokenSecret(token.getSecretToken());
        twitter = new TwitterFactory(configBuilder.build()).getInstance();

        System.out.println("Welcome @" + twitter.showUser(twitter.getScreenName()).getScreenName());
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
        assert listado != null;
        for (Status status : listado) {
            System.out.printf("%30s | %15s | %100s %n", status.getCreatedAt().toString(), ("@" + status.getUser().getScreenName()), status.getText());
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
