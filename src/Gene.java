/**
 * Created by victor on 7/7/2016.
 */
public class Gene implements Comparable<Gene> {

    private String id;
    private String sign;
    private long start;
    private long end;

    public Gene(String id, String sign, long start, long end) {
        this.id = id;
        this.sign = sign;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public String getSign() {
        return sign;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public int compareTo(Gene other){
        if (other == null){
            return 1;
        }

        if (this.start - other.start < 0) {
            return -1;
        }
        if (this.start - other.start > 0){
            return 1;
        }
        return 0;
    }
}
