package xx.anonymauthor.semanticsod.rolemodel;

import java.util.HashSet;
import java.util.Set;

public class Role extends AbstractEntity{

    /**
     * Represents the role hierarchy
     */
    private final Set<Role> parentRoles;

    private final Set<Role> childRoles;
    private final Set<Permission> childPermissions;



    private SoDClass soDClass;

    /* During analysing we will change the sod Class. We use this for tracking changes in our results */
    private SoDClass initialSoDClass;

    public Role(String identifier, String displayName) {
        super(identifier, displayName);
        parentRoles = new HashSet<>();
        childRoles = new HashSet<>();
        childPermissions = new HashSet<>();
    }

    public SoDClass getSoDClass() {
        return soDClass;
    }

    public void setSoDClass(SoDClass soDClass) {
        this.soDClass = soDClass;
    }

    public Set<Role> getParentRoles() {
        return parentRoles;
    }

    public Set<Permission> getChildPermissions() {
        return childPermissions;
    }

    public Set<Role> getChildRoles() {
        return childRoles;
    }

    public SoDClass getInitialSoDClass() {
        return initialSoDClass;
    }

    public void setInitialSoDClass(SoDClass initialSoDClass) {
        this.initialSoDClass = initialSoDClass;
    }
}
