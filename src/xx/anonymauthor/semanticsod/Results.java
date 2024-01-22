package xx.anonymauthor.semanticsod;

import xx.anonymauthor.semanticsod.rolemodel.Permission;
import xx.anonymauthor.semanticsod.rolemodel.Role;
import xx.anonymauthor.semanticsod.rolemodel.RoleModel;
import xx.anonymauthor.semanticsod.rolemodel.SoDClass;

import java.util.HashSet;
import java.util.Set;

public class Results {


    private int analyzedRoles = 0;
    private int analyzedPermissions = 0;

    private int analyzedAssignments = 0;

    private int rolesWithSoDClass = 0;
    private int permissionsWithSoDClass = 0;


    private int permissionsWithoutRole = 0;
    private int createdMers = 0;
    private int rolesWithCorrectSoDClasses = 0;

    private Set<Role> roleSoDClassChanges = new HashSet<>();

    private Set<Role> inhomogeneousRoles = new HashSet<>();



    public void calculateStatistics(RoleModel roleModel) {

        setAnalyzedRoles(roleModel.getRoles().size());
        setAnalyzedPermissions(roleModel.getPermission().size());

        int assignmentsCount = 0;
        int roleWithSoDClass = 0;
        int permissionWithSoDClass = 0;

        createdMers = roleModel.getMers().size();

        for (Role role : roleModel.getRoles()) {
            assignmentsCount += role.getParentRoles().size();
            assignmentsCount += role.getChildPermissions().size();
            if(!SoDClass.neutral.equals(role.getSoDClass())) {
                roleWithSoDClass++;
            }

            if(SoDClass.invalid.equals(role.getSoDClass())) {
                inhomogeneousRoles.add(role);
            }

            if(role.getSoDClass().equals(role.getInitialSoDClass())) {
                rolesWithCorrectSoDClasses++;
            } else {
                roleSoDClassChanges.add(role);
            }

        }
        for (Permission permission : roleModel.getPermission()) {
            if(permission.getParentRoles().isEmpty()) {
                permissionsWithoutRole++;
            }
            if(!SoDClass.neutral.equals(permission.getSoDClass())) {
                permissionWithSoDClass++;
            }
        }

        setPermissionsWithSoDClass(permissionWithSoDClass);
        setRolesWithSoDClass(roleWithSoDClass);
        setAnalyzedAssignments(assignmentsCount);

    }





    public int getAnalyzedRoles() {
        return analyzedRoles;
    }

    public void setAnalyzedRoles(int analyzedRoles) {
        this.analyzedRoles = analyzedRoles;
    }

    public int getAnalyzedPermissions() {
        return analyzedPermissions;
    }

    public void setAnalyzedPermissions(int analyzedPermissions) {
        this.analyzedPermissions = analyzedPermissions;
    }

    public int getAnalyzedAssignments() {
        return analyzedAssignments;
    }

    public void setAnalyzedAssignments(int analyzedAssignments) {
        this.analyzedAssignments = analyzedAssignments;
    }

    public int getRolesWithSoDClass() {
        return rolesWithSoDClass;
    }

    public void setRolesWithSoDClass(int rolesWithSoDClass) {
        this.rolesWithSoDClass = rolesWithSoDClass;
    }

    public int getPermissionsWithSoDClass() {
        return permissionsWithSoDClass;
    }

    public void setPermissionsWithSoDClass(int permissionsWithSoDClass) {
        this.permissionsWithSoDClass = permissionsWithSoDClass;
    }

    public int getCreatedMers() {
        return createdMers;
    }

    public void setCreatedMers(int createdMers) {
        this.createdMers = createdMers;
    }

    public int getFoundHomogeneityErrors() {
        return inhomogeneousRoles.size();
    }



    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("Analysed roles: " + analyzedRoles + "\n");
        builder.append("Analysed permissions: " + analyzedPermissions+ "\n");
        builder.append("Analysed assignments: " + analyzedAssignments+ "\n");

        builder.append("Analysed roles with sod class: " + rolesWithSoDClass+ "\n");
        builder.append("Analysed permissions with sod class: " + permissionsWithSoDClass+ "\n");
        builder.append("Analysed permissions without role: " + permissionsWithoutRole+ "\n");


        builder.append("SoD classes of roles that need to be changed: " + roleSoDClassChanges.size()+ "\n");
        builder.append("SoD classes of roles that do not need to be changed: " + rolesWithCorrectSoDClasses + "\n");
        builder.append("Homogeneity violations: " + getFoundHomogeneityErrors()+ "\n");

        builder.append("Created MERs: " + createdMers+ "\n");


        builder.append("\n ***** Inhomogeneous Roles ***** \n");
        if(inhomogeneousRoles.isEmpty()) {
            builder.append("No inhomogeneous roles found.");
        }

        for (Role inhomogeneousRole : inhomogeneousRoles) {
            builder.append("Role: " + inhomogeneousRole.getDisplayName() + ", " + inhomogeneousRole.getIdentifier()+ "\n");
            for (Permission childPermission : inhomogeneousRole.getChildPermissions()) {
                if(!SoDClass.neutral.equals(childPermission.getSoDClass())) {
                    builder.append(childPermission.getDisplayName() +" -> " + childPermission.getSoDClass() + "\n");
                }
            }
            for (Role childRole : inhomogeneousRole.getChildRoles()) {
                if(!SoDClass.neutral.equals(childRole.getSoDClass())) {
                    builder.append(childRole.getDisplayName() +" -> " + childRole.getSoDClass() + "\n");
                }
            }
            builder.append("\n");
        }
        builder.append("\n");
        builder.append("\n ***** SoD Class Changes ***** \n");
        if(roleSoDClassChanges.isEmpty()) {
            builder.append("No SoD Class Changes needed.");
        }
        for (Role change: roleSoDClassChanges) {
            builder.append("SoD Class of role " + change.getDisplayName()
                    + " should be changed from " + change.getInitialSoDClass() + " to " + change.getSoDClass());
            builder.append("\n");
        }


        return builder.toString();
    }

}
