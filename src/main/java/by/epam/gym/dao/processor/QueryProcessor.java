package by.epam.gym.dao.processor;

import by.epam.gym.entities.Entity;
import by.epam.gym.exceptions.DAOException;

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
public class QueryProcessor<T extends Entity>{

    private static final String QUERY_SPLIT_REGEX = " ";
    private static final String COLUMN_NAME_REGEX_PART = "=\\?,?";

    private static final int DEFAULT_VALUE_OF_PARAMETER_INDEX = -1;

    private String sqlQuery;
    private Connection connection;
    private T entity;

    /**
     * Instantiates a new QueryProcessor.
     *
     * @param sqlQuery   the sql query.
     * @param connection the connection to database.
     * @param entity     the entity.
     */
    public QueryProcessor(String sqlQuery, Connection connection, T entity){
        this.sqlQuery = sqlQuery;
        this.connection = connection;
        this.entity = entity;
    }

    /**
     * This method insert entity to database.
     *
     * @throws DAOException object if execution of query is failed.
     */
    public void processInsertQuery() throws DAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
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

            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new DAOException("Unexpected result during insert query.");
            }

        } catch (IllegalAccessException | SQLException | InvocationTargetException exception) {
            throw new DAOException("Exception during insert query. ", exception);
        }
    }

    /**
     * This method update insert in database.
     *
     * @throws DAOException object if execution of query is failed.
     */
    public void processUpdateQuery() throws DAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
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

            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new DAOException("Unexpected result during update query.");
            }
        } catch (IllegalAccessException | SQLException | InvocationTargetException exception) {
            throw new DAOException("Exception during update query. ", exception);
        }

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

    private List<Method> findGetterMethods(Class clazz) {
        Method[] allMethods = clazz.getMethods();
        List<Method> sortedMethods = new ArrayList<>();

        for (Method method : allMethods) {
            boolean isGetter = isMethodGetter(method);

            if (isGetter) {
                sortedMethods.add(method);
            }
        }

        return sortedMethods;
    }

    private boolean isMethodGetter(Method method) {
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
