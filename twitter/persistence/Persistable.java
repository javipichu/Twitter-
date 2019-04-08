package twitter.persistence;

import java.io.File;
import java.io.IOException;

public interface Persistable {

    File file = null;

    public void setDefault();

    public void saveKey() throws IOException ;

    public void readKey() throws IOException ;
}
