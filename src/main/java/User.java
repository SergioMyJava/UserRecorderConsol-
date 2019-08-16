import java.util.LinkedList;
import java.util.List;

public class User {
    String name;
    String lastNmae;
    List<String> phoneNumber;
    String mail;
    List<String> rol;

    User(String name, String lastNmae, List phoneNumber, String mail, List rol){
        this.name = name;
        this.lastNmae = lastNmae;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.rol = rol;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastNmae='" + lastNmae + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", mail='" + mail + '\'' +
                ", rol=" + rol +
                '}';
    }
}
