package by.epam.gym.utils;

import by.epam.gym.entities.exercise.Exercise;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.TreeMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class TrainingProgramDataValidatorTest {

    private static final TrainingProgramDataValidator TRAINING_PROGRAM_DATA_VALIDATOR = new TrainingProgramDataValidator();
    private static final int MINIMAL_DAYS_COUNT = 2;
    private static final int MAXIMUM_DAYS_COUNT = 7;

    private static TreeMap<Integer, List<Exercise>> daysAndExercisesValid;
    private static TreeMap<Integer, List<Exercise>> daysAndExercisesNotValid;

    @BeforeClass
    public static void setDaysAndExercisesValid() {
        daysAndExercisesValid = new TreeMap<>();
        List<Exercise> firstCount = mock(List.class);
        when(firstCount.size()).thenReturn(3);
        daysAndExercisesValid.put(1, firstCount);
        daysAndExercisesValid.put(2, firstCount);
    }

    @BeforeClass
    public static void setDaysAndExercisesNotValid() {
        daysAndExercisesNotValid = new TreeMap<>();
        List<Exercise> firstCount = mock(List.class);
        when(firstCount.size()).thenReturn(2);
        daysAndExercisesNotValid.put(1, firstCount);
    }

    @DataProvider
    public static Object[][] validDataForAddDayOperation() {
        return new Object[][]{
                {1},
                {2},
                {3},
                {4},
                {5},
                {6}
        };
    }

    @DataProvider
    public static Object[][] validDataForDeleteDayOperation() {
        return new Object[][]{
                {7},
                {6},
                {5},
                {4},
                {3},
        };
    }

    @Test
    public void shouldCheckDaysAndExercisesBeSuccessful() {
        boolean actualResult = TRAINING_PROGRAM_DATA_VALIDATOR.checkDaysAndExercisesCount(daysAndExercisesValid);

        Assert.assertTrue(actualResult);
    }

    @Test
    public void shouldCheckDaysAndExercisesFail() {
        boolean actualResult = TRAINING_PROGRAM_DATA_VALIDATOR.checkDaysAndExercisesCount(daysAndExercisesNotValid);

        Assert.assertFalse(actualResult);
    }

    @Test
    @UseDataProvider("validDataForAddDayOperation")
    public void shouldCheckDaysCountForAddOperationBeSuccessful(int daysCount) {
        TreeMap<Integer, List<Exercise>> daysAndExercises = mock(TreeMap.class);
        when(daysAndExercises.size()).thenReturn(daysCount);

        boolean actualResult = TRAINING_PROGRAM_DATA_VALIDATOR.checkDaysCountForAddOperation(daysAndExercises);

        Assert.assertTrue(actualResult);
    }

    @Test
    public void shouldCheckDaysCountForAddOperationFail() {
        TreeMap<Integer, List<Exercise>> daysAndExercises = mock(TreeMap.class);
        when(daysAndExercises.size()).thenReturn(MAXIMUM_DAYS_COUNT);

        boolean actualResult = TRAINING_PROGRAM_DATA_VALIDATOR.checkDaysCountForAddOperation(daysAndExercises);

        Assert.assertFalse(actualResult);
    }

    @Test
    @UseDataProvider("validDataForDeleteDayOperation")
    public void shouldCheckDaysCountForDeleteOperationBeSuccessful(int daysCount) {
        TreeMap<Integer, List<Exercise>> daysAndExercises = mock(TreeMap.class);
        when(daysAndExercises.size()).thenReturn(daysCount);

        boolean actualResult = TRAINING_PROGRAM_DATA_VALIDATOR.checkDaysCountForDeleteOperation(daysAndExercises);

        Assert.assertTrue(actualResult);
    }

    @Test
    public void shouldCheckDaysCountForDeleteOperationFail() {
        TreeMap<Integer, List<Exercise>> daysAndExercises = mock(TreeMap.class);
        when(daysAndExercises.size()).thenReturn(MINIMAL_DAYS_COUNT);

        boolean actualResult = TRAINING_PROGRAM_DATA_VALIDATOR.checkDaysCountForDeleteOperation(daysAndExercises);

        Assert.assertFalse(actualResult);
    }

}
