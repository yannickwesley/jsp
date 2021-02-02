package utilisateurs.modeles;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Bean JAXB qui implementent le modele de User.
 * @author ndelafor
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
    "id",
    "login",
    "lastname",
    "firstname"
})
@XmlRootElement(name = "user")
public class User {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "lastname")
    private String lastname;
    @XmlElement(name = "firstname")
    private String firstname;
    @XmlElement(name = "login")
    private String login;

    public User() {
    }

    public User(final String login, final String lastname, final String firstname) throws UnsupportedEncodingException {
        this.login = login;
        this.lastname = lastname;
        this.firstname = firstname;
        this.id = createUserId(login);
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastname;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
        this.id = createUserId(login);
    }

    public void setLastName(final String lastname) {
        this.lastname = lastname;
    }

    public void setFirstName(final String firstname) {
        this.firstname = firstname;
    }

    public static String createUserId(final String login) {
        String str = "";
        try {
            str = URLEncoder.encode(login, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
