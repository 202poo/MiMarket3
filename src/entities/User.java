package entities;

import java.nio.charset.StandardCharsets;
import util.EncryptorAesGcmPassword;


/**
 *
 * @author Asullom
 */
public class User {

    private int id;
    private int rol;
    private String pin;
    private String username;
    
    public void encriptarPass() throws Exception
    {
        this.pin=EncryptorAesGcmPassword.encrypt(this.pin.getBytes(StandardCharsets.UTF_8), "");
    }
    
    public void desencriptarPass() throws Exception
    {          
        String desEncrypted  = EncryptorAesGcmPassword.decrypt(this.pin, "");
        this.pin=desEncrypted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




   
}
