package xx.anonymauthor.semanticsod.rolemodel;

import java.util.*;

public class RoleModel {


    private final Map<String, Role> roles;

    private final Map<String, Permission> permissions;

    private final Map<String, SoDClass> soDClasses;

    private final SoDMatrix soDMatrix;

    private final Set<MER> mers;

    public RoleModel() {

        roles = new HashMap<>();
        permissions = new HashMap<>();
        soDClasses = new HashMap<>();
        mers = new HashSet<>();
        soDMatrix = new SoDMatrix();

    }

    public void addRole(Role role) {
        roles.put(role.getIdentifier(), role);
    }

    public void addPermission(Permission permission) {
        permissions.put(permission.getIdentifier(), permission);
    }

    public void addSoDlass(SoDClass soDClass) {
        soDClasses.put(soDClass.getIdentifier(), soDClass);
    }

    public SoDClass getSoDClass(String identifier) {
        return soDClasses.get(identifier);
    }
    public Permission getPermission(String identifier) {
        return permissions.get(identifier);
    }

    public Role getRole(String identifier) {
        return roles.get(identifier);
    }




    /**
     *  Returns the roles of the mode, must not be modified.
     */
    public Set<Role> getRoles() {
        return Set.copyOf(roles.values());
    }

    /**
     *  Returns the permissions of the mode, must not be modified.
     */
    public Set<Permission> getPermission() {
        return Set.copyOf(permissions.values());
    }

    /**
     *  Returns the sod classes of the mode, must not be modified.
     */
    public Set<SoDClass> getSoDClasses() {
        return Set.copyOf(soDClasses.values());
    }

    public SoDMatrix getSoDMatrix() {
        return soDMatrix;
    }

    public Set<MER> getMers() {
        return mers;
    }
}
