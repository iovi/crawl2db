package settings;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Конфигурация подключения к базе данных
 */
@XmlRootElement (name="DBConfiguration")
public class DBConfiguration {
    /** строка подключения - например jdbc:postgresql://localhost:5432/crawler_db1 */
    String connectionUrl;
    /** имя пользователя, рбаотающего с базой*/
    String user;
    /**пароль пользователя {@link #user}*/
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
