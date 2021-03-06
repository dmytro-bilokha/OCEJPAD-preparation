package bilokhado.exceptionexplorer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The test item primitive entity
 */
@Entity
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "id")
    private Integer id;

    public Item() {}

    public Item(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
