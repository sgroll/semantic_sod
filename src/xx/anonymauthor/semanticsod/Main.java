package xx.anonymauthor.semanticsod;

import xx.anonymauthor.semanticsod.algorithms.AssignSoDClassesToRoles;
import xx.anonymauthor.semanticsod.algorithms.TranslateSemanticSoDs;
import xx.anonymauthor.semanticsod.importexport.Exporter;
import xx.anonymauthor.semanticsod.importexport.Importer;
import xx.anonymauthor.semanticsod.rolemodel.*;

public class Main {


    public static void main(String[] args) {

        /* Import (this step will need to be adjusted for each company) */
        RoleModel roleModel = new Importer().doImport();

        long benchmark = System.currentTimeMillis();

        System.out.println("\n");
        /* Execute algorithm 1 */
        new AssignSoDClassesToRoles(roleModel).assignSoDClasses();
        System.out.println("Algorithm 1 needed " + (System.currentTimeMillis() - benchmark) + " milliseconds.");

        /* Execute algorithm 2 */
        benchmark = System.currentTimeMillis();
        new TranslateSemanticSoDs(roleModel).translateSemanticSoDs();
        System.out.println("Algorithm 2 needed " + (System.currentTimeMillis() - benchmark) + " milliseconds.");

        /* Results and write exports */
        Results results = new Results();

        results.calculateStatistics(roleModel);

        System.out.println(results.toString());


        new Exporter(roleModel, results).export();

    }




}
