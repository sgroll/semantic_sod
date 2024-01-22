package xx.anonymauthor.semanticsod.algorithms;

import xx.anonymauthor.semanticsod.rolemodel.MER;
import xx.anonymauthor.semanticsod.rolemodel.Role;
import xx.anonymauthor.semanticsod.rolemodel.RoleModel;
import xx.anonymauthor.semanticsod.rolemodel.SoDClass;

public class TranslateSemanticSoDs {

    private RoleModel roleModel;

    public TranslateSemanticSoDs(RoleModel roleModel) {
        this.roleModel = roleModel;
    }


    public void translateSemanticSoDs() {

        // Transform roles to an array
        int index = 0;
       Role[] roles = new Role[roleModel.getRoles().size()];
        for (Role role : roleModel.getRoles()) {
            roles[index++] = role;
        }

        for(int i = 0; i < roles.length-1; ++i) {
            if(SoDClass.neutral.equals(roles[i].getSoDClass()) || SoDClass.invalid.equals(roles[i].getSoDClass())) {
                continue;
            }

            for(int j = i +1; j < roles.length; ++j) {

                if(SoDClass.neutral.equals(roles[j].getSoDClass()) || SoDClass.invalid.equals(roles[j].getSoDClass())) {
                    continue;
                }

                if(roleModel.getSoDMatrix().hasConflict(roles[i].getSoDClass(), roles[j].getSoDClass())) {
                    roleModel.getMers().add(new MER(roles[i], roles[j]));
                }
            }
        }

    }

}
