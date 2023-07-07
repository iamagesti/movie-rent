package movierent.entities;

public class UserEntity {
    private Integer users_id;
    private String username;
    private String password;
    private Integer role_id;
    private String role_name;

    public Integer getUserID(){
        return users_id;
    }
    public void setUserID(Integer users_id){
        this.users_id = users_id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    } 
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleid() {
        return role_id;
    }

    public void setRoleid(Integer role_id) {
        this.role_id = role_id;
    }
    public String getRoleName() {
        return role_name;
    }

    public void setRoleName(String role_name) {
        this.role_name = role_name;
    }
}
