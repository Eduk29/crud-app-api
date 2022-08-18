package br.com.crud.app.backend.utils;

import br.com.crud.app.backend.model.Role;
import br.com.crud.app.backend.model.User;

import java.util.ArrayList;
import java.util.List;

public class RoleUtils {

    public static User removeRoleDataFromUser(User user, boolean removeRoleDataFromUser) {

            List<Role> roles = user.getRoles();
            user.setRoleList(convertRoleListInStringList(roles));

            if(removeRoleDataFromUser == true) {
                user.setRoles(null);
            }
        return user;
    }

    public static List<String> convertRoleListInStringList(List<Role> roles) {
        List<String> roleList = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            roleList.add(roles.get(i).getCode());
        }
        return roleList;
    }
}
