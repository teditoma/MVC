package ro.teamnet.zth.api.database;

/**
 * Created by Raluca.Russindilar on 02.07.2016.
 */
public interface DBProperties {
    //TODO de inlocuit cu IP-ul din Docker
    String IP = "docker.local";
    String PORT = "49161";
    //TODO de inlocuit cu utilizatorul vostru
    String USER = "oanam";
    //TODO de inlocuit cu parola voastra
    String PASS = "oanam";
    String SID = "xe";
    String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";

}
