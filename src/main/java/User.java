import java.util.List;

public class User {
    String name;
    String lastName;

    List<String> phoneNumber;
    String mail;
    List<String> rol;

    User(String name, String lastName, List phoneNumber, String mail, List rol){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.rol = rol;
    }
    @Override
    public String toString() {
        return  "Name:" + name +
                " LastName:" + lastName  +
                " PhoneNumber:" + phoneNumber.toString() +
                " Mail:" + mail +
                " Rol:" + rol.toString();
    }

    public String getName(){
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public List<String> getRol() {
        return rol;
    }
}
