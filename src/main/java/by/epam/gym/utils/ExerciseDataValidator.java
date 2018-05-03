package by.epam.gym.utils;


import by.epam.gym.entities.exercise.Exercise;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.epam.gym.entities.exercise.ExerciseDifficultyLevel.*;

/**
 * Util class to validate exercise's data.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.exercise.Exercise
 */
public class ExerciseDataValidator {

    private static final String EXERCISE_NAME_PATTERN = "[A-Za-zА-Яа-я0-9., \\-]*";
    private static final String EXERCISE_DESCRIPTION_PATTERN = ".*<.*>+.*";

    private static final int MAXIMUM_EXERCISE_COUNT = 15;

    private static final int MINIMUM_SETS_COUNT = 1;
    private static final int MINIMUM_REPEATS_COUNT = 1;

    /**
     * This method validates exercise's data.
     *
     * @param name        the exercise's name.
     * @param levelValue  the level value.
     * @param description the exercise's description.
     * @return true if data is valid and false otherwise.
     */
    public boolean checkData(String name, String levelValue, String description) {

        return checkName(name)
                && checkLevel(levelValue)
                && checkDescription(description);

    }

    /**
     * This method checks exercise's count during add operation.
     *
     * @param exercises    the exercises per day.
     * @param setsCount    the exercise's sets count.
     * @param repeatsCount the exercise's repeats count.
     * @return true if count is valid and false otherwise.
     */
    public boolean checkExerciseCountDuringAddOperation(List<Exercise> exercises, int setsCount, int repeatsCount) {
        return exercises.size() < MAXIMUM_EXERCISE_COUNT
                && setsCount >= MINIMUM_SETS_COUNT
                && repeatsCount >= MINIMUM_REPEATS_COUNT;
    }

    /**
     * This method checks input exercise for unique. You can't have two same exercises in your training program.
     *
     * @param exerciseId the input exercise's id.
     * @param exercises  the current exercises in training program.
     * @return true if exercise is unique and false otherwise.
     */
    public boolean checkExerciseForUniqueInTrainingProgram(int exerciseId, List<Exercise> exercises) {
        for (Exercise currentExercise : exercises) {
            int currentExerciseId = currentExercise.getId();
            if (exerciseId == currentExerciseId) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks exercise's count during edit operation.
     *
     * @param setsCount    the exercise's sets count.
     * @param repeatsCount the exercise's repeats count.
     * @return true if count is valid and false otherwise.
     */
    public boolean checkExerciseDuringEditOperation(int setsCount, int repeatsCount) {
        return setsCount > MINIMUM_SETS_COUNT && repeatsCount > MINIMUM_REPEATS_COUNT;
    }

    private boolean checkLevel(String levelValue) {

        if (String.valueOf(BEGINNER).equals(levelValue)) {
            return true;
        }
        if (String.valueOf(EXPERT).equals(levelValue)) {
            return true;
        }
        if (String.valueOf(PRO).equals(levelValue)) {
            return true;
        }

        return false;
    }

    private boolean checkName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(EXERCISE_NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    private boolean checkDescription(String description) {
        if (description == null) {
            return false;
        }
        if (description.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(EXERCISE_DESCRIPTION_PATTERN);
        Matcher matcher = pattern.matcher(description);

        return !matcher.matches();
    }

}
