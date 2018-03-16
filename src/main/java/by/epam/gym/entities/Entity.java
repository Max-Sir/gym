package by.epam.gym.entities;

/**
 * The type Entity.
 *
 * @author Eugene Makarenko
 */
public abstract class Entity {

    private int id;

    /**
     * Gets entity's id.
     *
     * @return the entity's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets entity's id.
     *
     * @param id the entity's id.
     */
    public void setId(int id) {
        this.id = id;
    }
}
