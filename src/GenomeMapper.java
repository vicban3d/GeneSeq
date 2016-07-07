import java.io.*;
import java.util.HashMap;

/**
 * Created by victor on 7/7/2016.
 *
 */
public class GenomeMapper {
    public static void main(String[] args){
        if (args.length < 1){
            System.err.println("Invalid args");
            return;
        }

        HashMap<String, Long> geneMap = new HashMap<>();
        for (String inFile : args) {

            HashMap<String, Chromosome> chromosomes = new HashMap<>();

            try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
                String line;
                System.out.print("Reading " + inFile.split(".txt")[0].split("_")[1] + " genes...");
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\t");

                    String geneId = parts[1].split("_")[1];
                    String chrId = parts[2];
                    String sign = parts[3];
                    String start = parts[4];
                    String end = parts[5];
                    String mappedId;
                    if (geneMap.containsKey(geneId)) {
                        mappedId = String.valueOf(geneMap.get(geneId));
                    } else {
                        mappedId = String.valueOf(geneMap.keySet().size() + 1);
                        geneMap.put(geneId, Long.parseLong(String.valueOf(geneMap.keySet().size() + 1)));
                    }

                    Gene gene = new Gene(mappedId, sign.substring(0, 1), Long.parseLong(start), Long.parseLong(end));

                    if (!chromosomes.containsKey(chrId)) {
                        chromosomes.put(chrId, new Chromosome(chrId));
                    }

                    chromosomes.get(chrId).addGene(gene);
                }
                System.out.println("Done!");
                System.out.print("Writing gene mapping...");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(inFile.split(".txt")[0].split("_")[1] + "_" + "out.txt"))) {
                    for (String key : chromosomes.keySet()) {
                        bw.write(chromosomes.get(key).getList());
                        bw.write("$ ");
                    }
                }
                System.out.println("Done!\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
