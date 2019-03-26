package twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * @author fsancheztemprano
 */
public class ShowStatus {
    public static void main(String[] args) {
        args = new String[]{"1110461307801817088"};
        if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.tweets.ShowStatus [status id]");
            System.exit(-1);
        }
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Status status = twitter.showStatus(Long.parseLong(args[0]));
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to show status: " + te.getMessage());
            System.exit(-1);
        }
    }
}
