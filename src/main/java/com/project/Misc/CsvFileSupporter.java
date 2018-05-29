package com.project.Misc;

import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class CsvFileSupporter {
    private static String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private static final String DEFAULT_STRING = "raport" + currentDate;

    @Getter private String fileName;
    private Map<String, String> addCosts;
    private Map<String, String> generalData;

    public CsvFileSupporter(Map addCosts, Map generalData){
        this.fileName = DEFAULT_STRING;
        this.addCosts = addCosts;
        this.generalData = generalData;
    }

    public void generateCsv() throws IOException {
        File file = new File(fileName + ".csv");
        FileWriter fileWriter = new FileWriter(file);

        for(Iterator<?> iterator = generalData.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry object = (Map.Entry)iterator.next();
            fileWriter.append(String.valueOf(object.getKey())).append(",").append(String.valueOf(object.getValue()));
            fileWriter.append("\n");
        }

        for(Iterator<?> iterator = addCosts.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry object = (Map.Entry)iterator.next();
            fileWriter.append(String.valueOf(object.getKey())).append(",").append(String.valueOf(object.getValue()));
            fileWriter.append("\n");
        }

        fileWriter.flush();
        fileWriter.close();
    }
}
