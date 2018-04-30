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

    /**
     * This method equals two objects.
     *
     * @param object the object.
     * @return true if objects are equal and false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Entity entity = (Entity) object;

        return id == entity.id;
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        return 31 * id;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
