import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by victor on 7/7/2016.
 *
 */
public class MGRMapper {

    private static HashMap<String, String> geneIdMap = new HashMap<>();
    private static HashMap<String, List<Gene>> allGenes = new HashMap<>();

    public static void main(String[] args){
        if (args.length < 2){
            System.err.println("Invalid arguments, please provide at least 2 input files.");
            return;
        }

        System.out.println("Started");
        try {
            generateGeneMapping(args);
        } catch (IOException e) {
            System.err.println("Failed to read input files!");
        }
        try {
            WriteOutput(allGenes, args);
        } catch (IOException e) {
            System.err.println("Failed to generate output file!");
        }
        System.out.println("Done!");

    }

    private static void generateGeneMapping(String[] args) throws IOException {
        for (String inFile : args) {
            System.out.println("Reading " + inFile.split(".txt")[0].split("_")[1] + " genes...");
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            // skip first line
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split("\t");
                String blockIndex = parts[0];
                String geneId = parts[1].split("_")[1];
                String chrId = parts[2];
                String sign = parts[3];
                String start = parts[4];
                String end = parts[5];
                String mappedId;

                if (geneIdMap.containsKey(geneId)) {
                    mappedId = String.valueOf(geneIdMap.get(geneId));
                } else {
                    mappedId = String.valueOf(geneIdMap.keySet().size() + 1);
                    geneIdMap.put(geneId, String.valueOf(geneIdMap.keySet().size() + 1));
                }

                Gene gene = new Gene(mappedId, chrId, sign.substring(0, 1), Long.parseLong(start), Long.parseLong(end));
                String mapKey = blockIndex;
//                String mapKey = geneId + "_" + blockIndex;
                if (!allGenes.keySet().contains(mapKey)) {
                    allGenes.put(mapKey, new ArrayList<Gene>());
                }
                allGenes.get(mapKey).add(gene);

                // get next line
                line = reader.readLine();
            }
        }
    }

    private static void WriteOutput(HashMap<String, List<Gene>> allGenes, String[] files) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"));
        int i = 0;
        for (String inFile : files) {
            bw.write("# genome" + i++ + ": " + inFile.split(".txt")[0].split("_")[1] + "\n");
        }
        bw.write("\n");
        System.out.println("Initialized output file");
        System.out.println("Writing output...");
        long uniqueGenes = 0;
        long multipleGenes = 0;
        long coupledGenes = 0;
        for (String mapKey : allGenes.keySet()) {
            if (allGenes.get(mapKey).size() != 2){
                if (allGenes.get(mapKey).size() == 1){
                    uniqueGenes++;
                } else {
                    multipleGenes++;
                }
//                continue;
            } else {
                coupledGenes++;
            }

            bw.write(mapKey + " ");
            for (Gene g : allGenes.get(mapKey)) {
                bw.write(g.toString());
                bw.write(" ");
            }
            bw.write("\n");
        }

        System.out.println("Unique Genes: " + uniqueGenes);
        System.out.println("Multiple Genes: " + multipleGenes);
        System.out.println("Coupled Genes: " + coupledGenes);

    }
}