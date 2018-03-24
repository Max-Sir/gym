package by.epam.gym.entities;

import by.epam.gym.dao.processor.ColumnName;

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
    @ColumnName(name = "id")
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
