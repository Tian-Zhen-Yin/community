package life.majiang.community.dto;

public class GithubUser {
    private String name;
    private long id;
    private String blo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBlo() {
        return blo;
    }

    public void setBlo(String blo) {
        this.blo = blo;
    }

    @Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", blo='" + blo + '\'' +
                '}';
    }
}
