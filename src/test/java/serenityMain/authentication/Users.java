package serenityMain.authentication;

public enum Users {
    Standard_User("standard_user", "secret_sauce", "a standard user"),
    Locked_Out_User("locked_out_user", "secret_sauce","a locked out user"),
    Problem_User("problem_user", "secret_sauce", "a problem user"),
    Performance_Glitch_User("performance_glitch-user","secret_sauce", "a performance glitch user");
    private final String username;
    private final String password;

    private final String description;

    Users(String username, String password, String description) {
        this.username = username;
        this.password = password;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Users{" +
                "description='" + description + '\'' +
                '}';
    }
}
