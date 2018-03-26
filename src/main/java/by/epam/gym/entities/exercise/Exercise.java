package by.epam.gym.entities.exercise;

import by.epam.gym.dao.processor.ColumnName;
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
    @ColumnName(name = "name", parameterIndex = 1)
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
    @ColumnName(name = "level", parameterIndex = 2)
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
    @ColumnName(name = "description", parameterIndex = 3)
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
    @ColumnName(name = "sets_count")
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
    @ColumnName(name = "repeats_count")
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
    @ColumnName(name = "day_number")
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
    @ColumnName(name = "execution_number")
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
}
