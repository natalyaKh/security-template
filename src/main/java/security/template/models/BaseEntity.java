package security.template.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * The Class BaseEntity.
 */
@MappedSuperclass
public class BaseEntity {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO)
    private long id;

    /**
     * Gets the id.
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the new id
     */
    public void setId(final long id) {
        this.id = id;
    }
}
