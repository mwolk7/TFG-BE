package tools.mtsuite.core.core.keycloakSync.dto;


import java.util.List;

public class AllEntityDto {

    List<User> users;
    String usersId;
    String groupsId;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(String groupsId) {
        this.groupsId = groupsId;
    }


    @Override
    public String toString() {
        return "AllEntityDto{" +
                "users=" + users +
                ", usersId='" + usersId + '\'' +
                ", groupsId='" + groupsId + '\'' +
                '}';
    }
}
