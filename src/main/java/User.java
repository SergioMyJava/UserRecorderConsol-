import java.util.List;

public class User {
    String name;
    String lastNmae;
    List<String> phoneNumber;
    String mail;
    List<String> rol;

    User(String name,String lastNmae,List phoneNumber,String mail,List rol){
        this.name = name;
        this.lastNmae = lastNmae;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.rol = rol;
    }
}
