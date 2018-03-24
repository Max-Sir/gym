package by.epam.gym.dao.processor;

import by.epam.gym.entities.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class of DAO-level to work with entity. Make insert and update methods of DAO interface to work with all types of Entity.
 *
 * @param <T> Entity type.
 */
public class QueryProcessor<T extends Entity> implements AutoCloseable {

    private static final String QUERY_SPLIT_REGEX = " ";
    private static final String COLUMN_NAME_REGEX_PART = "=\\?,?";

    private static final int DEFAULT_VALUE_OF_PARAMETER_INDEX = -1;

    private String sqlQuery;
    private PreparedStatement preparedStatement;
    private T entity;

    /**
     * Instantiates a new QueryProcessor.
     *
     * @param sqlQuery the sql query.
     * @param connection the connection to database.
     * @param entity th entity.
     * @throws SQLException object if execution of query is failed.
     */
    public QueryProcessor(String sqlQuery, Connection connection, T entity) throws SQLException {
        this.sqlQuery = sqlQuery;
        preparedStatement = connection.prepareStatement(sqlQuery);
        this.entity = entity;
    }

    /**
     * This method insert entity to database.
     *
     * @throws SQLException object if execution of query is failed.
     * @throws InvocationTargetException object if method invocation is failed.
     * @throws IllegalAccessException object if access is illegal.
     */
    public void processInsertQuery() throws SQLException, InvocationTargetException, IllegalAccessException {
        Class<T> clazz = (Class<T>) entity.getClass();
        List<Method> methods = findGetterMethods(clazz);

        for (Method method : methods) {
            ColumnName annotation = method.getAnnotation(ColumnName.class);
            if (annotation != null) {
                int id = annotation.parameterIndex();
                if (id != DEFAULT_VALUE_OF_PARAMETER_INDEX) {
                    String value = String.valueOf(method.invoke(entity, null));
                    preparedStatement.setString(id, value);
                }
            }
        }
    }

    /**
     * This method update insert in database.
     *
     * @throws InvocationTargetException object if method invocation is failed.
     * @throws IllegalAccessException object if access is illegal.
     * @throws SQLException object if execution of query is failed.
     */
    public void processUpdateQuery() throws InvocationTargetException, IllegalAccessException, SQLException {
        Class<T> clazz = (Class<T>) entity.getClass();
        List<Method> methods = findGetterMethods(clazz);

        String[] parsedQuery = sqlQuery.split(QUERY_SPLIT_REGEX);
        int currentParameterIndex = 1;

        for (String queryPart : parsedQuery) {

            String resultValue = identifyValue(queryPart, methods, entity);
            if (!resultValue.isEmpty()) {
                preparedStatement.setString(currentParameterIndex, resultValue);

                currentParameterIndex++;
            }
        }
    }

    /**
     * This method clothes PreparedStatement object.
     *
     * @throws Exception object if close method failed.
     */
    @Override
    public void close() throws Exception {
        preparedStatement.close();
    }

    private boolean checkColumnName(String queryPart, String columnName) {
        String fullRegex = columnName + COLUMN_NAME_REGEX_PART;

        Pattern pattern = Pattern.compile(fullRegex);
        Matcher matcher = pattern.matcher(queryPart);

        return matcher.matches();
    }

    private String identifyValue(String queryPart, List<Method> getterMethods, T entity) throws InvocationTargetException, IllegalAccessException {
        String value = "";

        for (Method method : getterMethods) {
            ColumnName columnName = method.getAnnotation(ColumnName.class);

            if (columnName != null) {
                String columnNameValue = columnName.name();

                boolean isTrue = checkColumnName(queryPart, columnNameValue);
                if (isTrue) {
                    value = String.valueOf(method.invoke(entity, null));
                    break;
                }
            }
        }

        return value;
    }

    private List<Method> findGetterMethods(Class clazz){
        Method[] allMethods = clazz.getMethods();
        List<Method> sortedMethods = new ArrayList<>();

        for (Method method : allMethods) {
           boolean isGetter = isMethodGetter(method);

           if (isGetter){
               sortedMethods.add(method);
           }
        }

        return sortedMethods;
    }

    private boolean isMethodGetter(Method method){
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }
}
