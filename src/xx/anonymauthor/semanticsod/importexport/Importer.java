package xx.anonymauthor.semanticsod.importexport;


import xx.anonymauthor.semanticsod.rolemodel.Permission;
import xx.anonymauthor.semanticsod.rolemodel.Role;
import xx.anonymauthor.semanticsod.rolemodel.RoleModel;
import xx.anonymauthor.semanticsod.rolemodel.SoDClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * The importer is written to suit -exactly- the data format of our test customer.
 *
 * We also provided a small demo data set that fits the importer.
 *
 * It should additionally be noted that the customer data has a simple hierarchy,
 * only roles that contain permissions.
 *
 * If you want to check your own data, you have either convert it in a suitable format or write an own importer,
 * that returns a {@link xx.anonymauthor.semanticsod.rolemodel.RoleModel}
 */

public class Importer {

    String inputFile = "data_in"  + File.separator;



    public RoleModel doImport() throws IllegalStateException {
        RoleModel roleModel = new RoleModel();

        importSoDClasses(roleModel);
        importPermissions(roleModel);
        importRoles(roleModel);

        return roleModel;
    }


    private void importSoDClasses(RoleModel roleModel) {

        Map<SoDClass, Set<Integer>> sodClassToExclusionIndex = new HashMap<>();
        List<SoDClass> soDClassList = new ArrayList<>();


        readLines(inputFile + "sodClasses.csv", line -> {
            String[] split = line.split(";");
            SoDClass soDClass = new SoDClass(split[0], split[0]);

            roleModel.addSoDlass(soDClass);
            soDClassList.add(soDClass);

            Set<Integer> mutualExclusions = new HashSet<>();

            for(int i = 1; i < split.length; ++i) {
                if("x".equals(split[i])) {
                    mutualExclusions.add(i-1);
                }
            }
            sodClassToExclusionIndex.put(soDClass, mutualExclusions);
        });

        // Add exclusions
        for (SoDClass soDClass : roleModel.getSoDClasses()) {
            for (Integer indexInList : sodClassToExclusionIndex.get(soDClass)) {
                SoDClass conflictingSoDClass = soDClassList.get(indexInList);
                roleModel.getSoDMatrix().addConflictingSoDClasses(soDClass, conflictingSoDClass);
            }
        }


    }

    private void importPermissions(RoleModel roleModel) {
        readLines(inputFile + "permissions.csv", line -> {


            String[] split = line.split(";");

            Permission permission = new Permission(split[0], split[1]);

            SoDClass soDClass = SoDClass.neutral;
            if(split.length > 2) {
                soDClass = roleModel.getSoDClass(split[3]);
            }

            if(soDClass == null) {


                if(!split[3].isBlank() && !"Keine SoD Relevanz".equals(split[3])) { // Customer specific adaption
                    System.out.println("Import error: Could not find SoD class: " + split[3]);
                }

                soDClass = SoDClass.neutral; // Fallback..
            }
            permission.setSoDClass(soDClass);
            roleModel.addPermission(permission);
        });

    }

    private void importRoles(RoleModel roleModel) {
        readLines(inputFile + "roles.csv", line -> {

            String[] split = line.split(";");


            String identifier = split[0];
            String displayName = split[1];

            Role role = new Role(identifier, displayName);

            SoDClass soDClass = SoDClass.neutral;
            if(split.length > 2) {

                if(!split[2].isBlank() && !"Keine SoD Relevanz".equals(split[2])) { // Customer specific adaption
                    soDClass = roleModel.getSoDClass(split[2]);
                }
            }
            if(soDClass == null) {
                System.out.println("Import error: Could not find SoD class: " + split[2]);
                soDClass = SoDClass.neutral; // Fallback..
            }
            role.setInitialSoDClass(soDClass);
            role.setSoDClass(soDClass);
            roleModel.addRole(role);

            if(split.length < 4) {
                return;
            }

            String[] permissionIds =  split[3].split(",");


            for (String permissionId : permissionIds) {
                Permission permission = roleModel.getPermission(permissionId);
                if(permission == null) {
                    continue;
                }
                permission.getParentRoles().add(role);
                role.getChildPermissions().add(permission);
            }


        });
    }

    private void readLines(String file, Consumer<String> lineConsumer) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                // Skip header
                line = reader.readLine();
                if(line != null && !line.isBlank()) {
                    lineConsumer.accept(line);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
