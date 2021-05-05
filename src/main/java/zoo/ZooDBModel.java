package zoo;

import javax.persistence.*;

@Entity
@Table(name = "zoo")
public class ZooDBModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    public String name;

    private int amount;

    public ZooDBModel() {
    }
}
