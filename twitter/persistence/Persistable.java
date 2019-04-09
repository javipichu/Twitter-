package twitter.persistence;

import java.io.File;
import java.io.IOException;

public interface Persistable {

    File file = null;

    void setDefault();

    void saveKey() throws IOException;

    void readKey() throws IOException;
}
