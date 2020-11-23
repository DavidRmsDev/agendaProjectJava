package modelo.conexion;

public class ConexionXML {

    private String dir;
    private String user;
    private String password;

    public ConexionXML() {
        this.dir = null;
        this.user = null;
        this.password = null;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
