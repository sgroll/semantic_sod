package xx.anonymauthor.semanticsod.algorithms;

import xx.anonymauthor.semanticsod.rolemodel.Permission;
import xx.anonymauthor.semanticsod.rolemodel.Role;
import xx.anonymauthor.semanticsod.rolemodel.RoleModel;
import xx.anonymauthor.semanticsod.rolemodel.SoDClass;

public class AssignSoDClassesToRoles {


    private RoleModel roleModel;

    public AssignSoDClassesToRoles(RoleModel roleModel) {

        this.roleModel = roleModel;
    }


    public void assignSoDClasses() {


        int count = 0;

        for (Role role : roleModel.getRoles()) {

            role.setSoDClass(SoDClass.neutral);

            for (Permission childPermission : role.getChildPermissions()) {
                if(SoDClass.neutral.equals(childPermission.getSoDClass()))  {
                    continue;
                }

                if(!childPermission.getSoDClass().equals(role.getSoDClass()))
                {
                    if(SoDClass.neutral.equals(role.getSoDClass())) {
                        role.setSoDClass(childPermission.getSoDClass());
                    } else {
                        role.setSoDClass(SoDClass.invalid);
                        break;
                    }
                }
            }

            if(!SoDClass.neutral.equals(role.getSoDClass())) {
                propagateRoleHierarchyRec(role);
            }

        }


    }

    private void propagateRoleHierarchyRec(Role child) {

        // used to track errors in results

        for (Role parentRole : child.getParentRoles()) {

            if(!SoDClass.invalid.equals(parentRole.getSoDClass())) {
                continue;
            } else if(!parentRole.getSoDClass().equals(child.getSoDClass())){
                if(SoDClass.neutral.equals(parentRole.getSoDClass())) {
                    parentRole.setSoDClass(child.getSoDClass());
                } else{
                    parentRole.setSoDClass(SoDClass.invalid);
                }
            }
            propagateRoleHierarchyRec(parentRole);

        }


    }


}
