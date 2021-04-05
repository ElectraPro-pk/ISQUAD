package uk.com.v3softech.online.store;

public class user {

    private String email,password,phone,name;

    public user() {

    }

    public user(String email, String password, String phone, String name) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
    }
    public void setPhone(String ph){ this.phone = ph; };
    public void setEmail(String e){ this.email = e; }
    public void setPassword(String p){
        this.password = p;
    }
    public void setName(String n){ this.name = n; };

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getPhone(){ return phone; }
    public String getName(){ return  name; }

}
