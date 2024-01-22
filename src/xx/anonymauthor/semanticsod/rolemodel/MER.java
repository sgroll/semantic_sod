package xx.anonymauthor.semanticsod.rolemodel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MER {

    private final Set<Role> roles;

    private final Role r1;
    private final Role r2;


    public MER(Role r1, Role r2) {
        this.r1 = r1;
        this.r2 = r2;
        roles = new HashSet<>();
        roles.add(r1);
        roles.add(r2);
    }


    @Override
    public boolean equals(Object other) {
        if (other instanceof MER mer) {
            return Objects.equals(roles, mer.roles);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return roles.hashCode();
    }

    @Override
    public String toString() {
        return r1.getDisplayName() + " - " + r2.getDisplayName();
    }

    public Role getR1() {
        return r1;
    }

    public Role getR2() {
        return r2;
    }
}
