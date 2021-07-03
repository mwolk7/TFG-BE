package tools.mtsuite.core.core.keycloakSync.dto;


import java.util.List;

public class ModifyEntityDto {

    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ModifyEntityDto{" +
                "users=" + users +
                '}';
    }
}
