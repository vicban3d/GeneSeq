/**
 * Created by victor on 7/7/2016.
 */
public class Gene implements Comparable<Gene> {

    private String id;
    private String sign;
    private String chrId;
    private long start;
    private long end;



    public Gene(String id, String chrId, String sign, long start, long end) {
        this.id = id;
        this.chrId = chrId;
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

    public String getChrId() {
        return chrId;
    }

    @Override
    public String toString() {
        return this.chrId + " " + this.start + " " + (this.end - this.start) + " " + this.sign;
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

    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Gene && this.id.equals(((Gene) other).id);

    }
}
