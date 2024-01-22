package xx.anonymauthor.semanticsod.importexport;

import xx.anonymauthor.semanticsod.Results;
import xx.anonymauthor.semanticsod.rolemodel.MER;
import xx.anonymauthor.semanticsod.rolemodel.RoleModel;

import java.io.*;

public class Exporter {

    String dataOutFolder = "data_out"  + File.separator;


    private RoleModel roleModel;
    private Results results;

    public Exporter(RoleModel roleModel, Results results) {

        this.roleModel = roleModel;
        this.results = results;
    }

    public void export() {
        // Print results
        write("Results.txt", results.toString());

        // print a csv with mers (dirty)
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("RoleId1;DisplayName1;RoleId2;DisplayName2");
        for (MER mer : roleModel.getMers()) {
            stringWriter.append("\n");
            stringWriter.append(mer.getR1().getIdentifier());
            stringWriter.append(";");

            stringWriter.append(mer.getR1().getDisplayName());
            stringWriter.append(";");

            stringWriter.append(mer.getR2().getIdentifier());
            stringWriter.append(";");

           stringWriter.append(mer.getR2().getDisplayName());
        }

        write("generated_mers.csv", stringWriter.toString());


    }


    private void write(String fileName, String msg) {


        BufferedWriter writer =  null;

        try {
            writer  = new BufferedWriter(new FileWriter( dataOutFolder + fileName));
            writer.write(msg);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
