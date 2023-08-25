package apiPOJOTemplates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostNewUserKraft {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("terms")
    @Expose
    private String terms;

    /**
     * No args constructor for use in serialization
     *
     */
    public PostNewUserKraft() {
    }

    /**
     *
     * @param password
     * @param terms
     * @param name
     * @param about
     * @param email
     */
    public PostNewUserKraft(String name, String email, String password, String about, String terms) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.about = about;
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    @Override
    public String toString() {
        return "PostNewUserKraft{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", about='" + about + '\'' +
                ", terms='" + terms + '\'' +
                '}';
    }
}
