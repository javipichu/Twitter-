package twitter;

import twitter.persistence.PersistAccessToken;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Session session = null;
        boolean persist = false;
        if(PersistAccessToken.file.exists()){
            System.out.println("Session file found, sign in? (Y/N) : ");
            Boolean answer = null;
            do{
                answer = consoleAssert();
            }while(answer == null);
            if(answer){
                persist = true;
                session = new Session(true);
            }
        }
        if(!persist){
            session = new Session();
        }



        String[] options = {"Timeline", "Tweet", "Exit"};
        while(true) {
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + 1 + ". " + options[i]);
            }
            String opt = "X";
            int n = 0;
            do {
                do {
                    opt = new Scanner(System.in).next();
                } while (!isInteger(opt));
                n = Integer.parseInt(opt);
            } while (n < 1 || n > options.length);

            switch(n){
                case 1:
                    session.printTimeline();
                    break;
                case 2:
                    System.out.println("Enter Tweet: \n");
                    String tweet = new Scanner(System.in).nextLine();
                    tweet = tweet.substring(0, Math.min(139,tweet.length()));
                    session.updateStatus(tweet);
                    break;
                case 3:
                    System.out.println("Save session? (Y/N) : ");
                    Boolean answer = null;
                    do{
                        answer = consoleAssert();
                    }while(answer == null);
                    if(answer)
                        session.saveSession();
                    else
                        session.clearSession();
                    System.out.println("Session " + (answer ? "saved." : "cleared."));
                    System.out.println("\nThank You");
                    System.exit(1);

                default:
                    System.out.println("DefBug");

            }
        }
    }


    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static Boolean consoleAssert() {
        switch ((int) scanChar()) {
            case 121: // 'y'
            case 89:  // 'Y'
                return true;
            case 110: // 'n'
            case 78:  // 'N'
                return false;
        }
        return null;
    }
    public static char scanChar() {
        return new Scanner(System.in).next().charAt(0);
    }
}
