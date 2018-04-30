package by.epam.gym.entities.exercise;

import by.epam.gym.entities.Entity;

/**
 * Class describes training exercise.
 *
 * @author Eugene Makarenko
 * @see ExerciseDifficultyLevel
 * @see Entity
 */
public class Exercise extends Entity {


    private String name;
    private ExerciseDifficultyLevel level;
    private String description;
    private int setsCount;
    private int repeatsCount;
    private int dayNumber;
    private int executionNumber;

    /**
     * Instantiates a new Exercise.
     */
    public Exercise() {
    }

    /**
     * Gets exercise's name.
     *
     * @return the exercise's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets exercise's name.
     *
     * @param name the exercise's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets exercise's difficulty level.
     *
     * @return the exercise's difficulty level.
     */
    public ExerciseDifficultyLevel getLevel() {
        return level;
    }

    /**
     * Sets exercise's difficulty level.
     *
     * @param level the exercise's difficulty level.
     */
    public void setLevel(ExerciseDifficultyLevel level) {
        this.level = level;
    }

    /**
     * Gets exercise's description.
     *
     * @return the exercise's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets exercise's description.
     *
     * @param description the exercise's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets sets count of exercise.
     *
     * @return the sets count.
     */
    public int getSetsCount() {
        return setsCount;
    }

    /**
     * Sets count of exercise's sets.
     *
     * @param setsCount the exercise's sets count.
     */
    public void setSetsCount(int setsCount) {
        this.setsCount = setsCount;
    }

    /**
     * Gets repeats count of exercise.
     *
     * @return the repeats count.
     */
    public int getRepeatsCount() {
        return repeatsCount;
    }

    /**
     * Sets repeats count of exercise.
     *
     * @param repeatsCount the repeats count of exercise.
     */
    public void setRepeatsCount(int repeatsCount) {
        this.repeatsCount = repeatsCount;
    }

    /**
     * Gets day number.
     *
     * @return the day number.
     */
    public int getDayNumber() {
        return dayNumber;
    }

    /**
     * Sets the day number.
     *
     * @param dayNumber the day number.
     */
    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    /**
     * Gets execution number.
     *
     * @return the execution number.
     */
    public int getExecutionNumber() {
        return executionNumber;
    }

    /**
     * Sets the execution number.
     *
     * @param executionNumber the execution number.
     */
    public void setExecutionNumber(int executionNumber) {
        this.executionNumber = executionNumber;
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
        if (!super.equals(object)) {
            return false;
        }

        Exercise exercise = (Exercise) object;

        if (setsCount != exercise.setsCount) {
            return false;
        }
        if (repeatsCount != exercise.repeatsCount) {
            return false;
        }
        if (dayNumber != exercise.dayNumber) {
            return false;
        }
        if (executionNumber != exercise.executionNumber) {
            return false;
        }
        if (name != null ? !name.equals(exercise.name) : exercise.name != null) {
            return false;
        }
        if (level != exercise.level) {
            return false;
        }
        return description != null ? description.equals(exercise.description) : exercise.description == null;
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + setsCount;
        result = 31 * result + repeatsCount;
        result = 31 * result + dayNumber;
        result = 31 * result + executionNumber;
        return result;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", setsCount=" + setsCount +
                ", repeatsCount=" + repeatsCount +
                ", dayNumber=" + dayNumber +
                ", executionNumber=" + executionNumber +
                '}';
    }
}
