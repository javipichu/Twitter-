package twitter.incubating;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter.persistence.PersistConsumerKey;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
public class JTwit {



    JTwit() throws TwitterException {
        Twitter twitter;
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        String token = ""; //Los almaceno en un string, ya que pueden variar según la cuenta
        String tokenSecret = "";
        File archivo = null;
        FileReader fileR = null;
        BufferedReader lecturaTeclado = null;
        try {
// Apertura del fichero y creacion de BufferedReader
            archivo = new File("auth_file.txt");
            fileR = new FileReader(archivo);
            lecturaTeclado = new BufferedReader(fileR);
// Lectura del fichero
            String linea = "";
            int n = 1;
            while ((linea = lecturaTeclado.readLine()) != null) {
                if (n == 1) { //La primera línea es el Access Token
                    System.out.println(linea);
                    token = linea;
                } else if (n == 2) { //La segunda línea es el Access Token Secret
                    System.out.println(linea);
                    tokenSecret = linea;
                }
                n++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
// En el finally cerramos el fichero, para asegurarnos
// que se cierra tanto si todo va bien como si salta
// una excepción.
            try {
                if (null != fileR) {
                    fileR.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        PersistConsumerKey consumer = new PersistConsumerKey();
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer.getApikey())
                .setOAuthConsumerSecret(consumer.getApisecret())
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(tokenSecret);
        twitter = new TwitterFactory(configBuilder.build()).getInstance();
//Recuperar listado de ultimos tweets escritos
        Paging pagina = new Paging();
        pagina.setCount(50);
        ResponseList listado = twitter.getHomeTimeline(pagina);
        for (int i = 0; i < listado.size(); i++) {
            System.out.println(listado.get(i).toString());
        }
//Actualizar tu estado
        Status tweetEscrito = twitter.updateStatus("[TUTORIAL] Twitter+Java @geekytheory www.geekytheory.com");
    }
    public static void main(String ar[]) throws TwitterException {
        try {
            Auth aut = new Auth();
        } catch (IOException | TwitterException ex) {
            Logger.getLogger(JTwit.class.getName()).log(Level.SEVERE, null, ex);
        }
        JTwit twitter = new JTwit();
    }
}