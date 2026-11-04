package FemSpirit.fiwcheckbackend.stupo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "stupo")
public class Stupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stupoId;

    @Column(nullable = false)
    private Integer gueltigAb;

    public Stupo() {
    }

    public Long getStupoId() {
        return stupoId;
    }

    public void setStupoId(Long stupoId) {
        this.stupoId = stupoId;
    }

    public Integer getGueltigAb() {
        return gueltigAb;
    }

    public void setGueltigAb(Integer gueltigAb) {
        this.gueltigAb = gueltigAb;
    }
}