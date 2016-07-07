import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by victor on 7/7/2016.
 *
 */
public class Chromosome {

    private String id;
    private List<Gene> genes;

    public Chromosome(String id) {

        this.id = id;
        genes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void addGene(Gene g){
        if (genes.size() == 0){
            genes.add(g);
            return;
        }
        int i = 0;
        while (i < genes.size() && genes.get(i).getStart() < g.getStart()){
            i++;
        }

        if (i == genes.size()){
            genes.add(g);
            return;
        }
        genes.add(i, g);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.id);
        builder.append(": \n");
        for (Gene g : genes){
            builder.append("\tID: ");
            builder.append(g.getId());
            builder.append(" SIGN: ");
            builder.append(g.getSign());
            builder.append(" SIZE: ");
            builder.append(g.getEnd() - g.getStart());
            builder.append("\n");
        }

        return builder.toString();
    }

    public String getList() {
        StringBuilder builder = new StringBuilder();
        for (Gene g : genes){
            builder.append(" ");
            String appSign = g.getSign().equals("-") ? g.getSign() : "";
            builder.append(appSign);
            builder.append(Long.parseLong(g.getId()));
        }
        return builder.substring(1);
    }
}
