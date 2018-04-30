package by.epam.gym.utils;

import by.epam.gym.entities.exercise.Exercise;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class ExerciseDataValidatorTest {

    private static final ExerciseDataValidator EXERCISE_DATA_VALIDATOR = new ExerciseDataValidator();

    private static final int VALID_EXERCISES_COUNT = 13;
    private static final int NOT_VALID_EXERCISES_COUNT = 15;
    private static final int VALID_SETS_COUNT = 4;
    private static final int VALID_REPEATS_COUNT = 10;

    private static List<Exercise> exercisesForTest;

    @BeforeClass
    public static void setExercisesForTest() {
        exercisesForTest = new ArrayList<>();

        Exercise exercise = new Exercise();
        exercise.setId(1);
        exercisesForTest.add(exercise);

        Exercise exerciseSecond = new Exercise();
        exerciseSecond.setId(2);
        exercisesForTest.add(exerciseSecond);

        Exercise exerciseThird = new Exercise();
        exerciseThird.setId(3);
        exercisesForTest.add(exerciseThird);
    }

    @DataProvider
    public static Object[][] validExerciseValues() {
        return new Object[][]{
                {"Приседания.", "PRO", "Приседания. Сесть и встать."},
                {"Гак-приседания.", "PRO", "Приседания. Сесть и встать."},
                {"Dead lift.", "EXPERT", "No description."},
                {"Explosive jumps.", "BEGINNER", "No description."}
        };
    }

    @DataProvider
    public static Object[][] notValidExerciseValues() {
        return new Object[][]{
                {"Приседания.!", "PRO", "Приседания. Сесть и встать."},
                {"Dead lift.", "MASTER", "No description."},
                {"Гак-приседания.", null, "Приседания. Сесть и встать."},
                {"Explosive jumps.", "BEGINNER", ""},
                {"", "PRO", "Приседания. Сесть и встать."},
                {"Dead_lift.", "EXPERT", "No description."},
                {null, "EXPERT", "No description."},
                {"Explosive jumps.", "BEGINNER", null},
                {"Explosive jumps.", "BEGINNER", "<alert(bug)>"}
        };
    }

    @DataProvider
    public static Object[][] notValidDataForAddOperation() {
        return new Object[][]{
                {NOT_VALID_EXERCISES_COUNT, 5, 5},
                {13, 0, 2},
                {10, 2, 0}
        };
    }

    @DataProvider
    public static Object[][] validDataForCheckForUniqueOperation() {
        return new Object[][]{
                {1},
                {2},
                {3}
        };
    }

    @DataProvider
    public static Object[][] notValidDataForEditOperation() {
        return new Object[][]{
                {0, 1},
                {1, 0},
                {-2, 30},
                {10, -2}
        };
    }

    @Test
    @UseDataProvider("validExerciseValues")
    public void shouldExerciseDataBeValid(String name, String levelValue, String description) {
        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkData(name, levelValue, description);
        Assert.assertTrue(actualResult);
    }

    @Test
    @UseDataProvider("notValidExerciseValues")
    public void shouldExerciseDataBeNotValid(String name, String levelValue, String description) {
        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkData(name, levelValue, description);
        Assert.assertFalse(actualResult);
    }

    @Test
    public void shouldAddExercisesOperationBeSuccessful() {
        ArrayList<Exercise> exercises = mock(ArrayList.class);
        when(exercises.size()).thenReturn(VALID_EXERCISES_COUNT);
        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkExerciseCountDuringAddOperation(exercises, VALID_SETS_COUNT, VALID_REPEATS_COUNT);

        Assert.assertTrue(actualResult);
    }

    @Test
    @UseDataProvider("notValidDataForAddOperation")
    public void shouldAddExerciseOperationFail(int exercisesSize, int repeatsCount, int setsCount) {
        ArrayList<Exercise> exercises = mock(ArrayList.class);
        when(exercises.size()).thenReturn(exercisesSize);

        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkExerciseCountDuringAddOperation(exercises, setsCount, repeatsCount);

        Assert.assertFalse(actualResult);
    }

    @Test
    @UseDataProvider("validDataForCheckForUniqueOperation")
    public void shouldCheckExerciseForUniqueFail(int exerciseId) {
        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkExerciseForUniqueInTrainingProgram(exerciseId, exercisesForTest);

        Assert.assertFalse(actualResult);
    }

    @Test
    public void shouldCheckExerciseForUniqueBeSuccessful() {
        int exerciseIdUnique = 4;
        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkExerciseForUniqueInTrainingProgram(exerciseIdUnique, exercisesForTest);

        Assert.assertTrue(actualResult);
    }

    @Test
    public void shouldEditOperationBeSuccessful() {
        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkExerciseDuringEditOperation(VALID_SETS_COUNT, VALID_REPEATS_COUNT);

        Assert.assertTrue(actualResult);
    }

    @Test
    @UseDataProvider("notValidDataForEditOperation")
    public void shouldEditOperationFail(int setsCount, int repeatsCount) {
        boolean actualResult = EXERCISE_DATA_VALIDATOR.checkExerciseDuringEditOperation(setsCount, repeatsCount);

        Assert.assertFalse(actualResult);
    }
}
