package xx.anonymauthor.semanticsod.rolemodel;

import java.util.HashSet;
import java.util.Set;

public class Permission extends AbstractEntity{

    /**
     * Represents the role - permission assignments
     */
    private final Set<Role> parentRoles;

    private SoDClass soDClass;

    public Permission(String identifier, String displayName) {
        super(identifier, displayName);
        parentRoles =  new HashSet<>();
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
}
