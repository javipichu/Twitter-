package twitter.persistence;

import java.io.File;
import java.io.IOException;

interface Persistable {

    File file = null;

    void setDefault();

    void saveKey();

    void readKey() throws IOException;
}
