package db;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="DBConfiguration")
public class DBConfiguration {
    String connectionUrl;
    String user;
    String password;

    public String getConnectionUrl() {
        return connectionUrl;
    }
    public String getPassword() {
        return password;
    }
    public String getUser() {
        return user;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
