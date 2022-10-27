package nx.peter.java.db;

import nx.peter.java.util.advanced.Advanced;
import nx.peter.java.util.advanced.Advanced.ObjectDetail;

import java.sql.*;
import java.util.List;

public class MySQLDatabase {
    public enum DataState {
        GreaterThan, LessThan, EqualTo, GreaterThanEqualTo, LessThanEqualTo
    }

    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/";
    public static final String DEFAULT_DATABASE = "piweb";

    private final String username, databaseUrl, password;
    private String table;
    private Connection connection;

    public MySQLDatabase(CharSequence username, CharSequence password) {
        this(DEFAULT_DATABASE, username, password);
    }

    public MySQLDatabase(CharSequence database, CharSequence username, CharSequence password) {
        this.username = username.toString();
        this.databaseUrl = MYSQL_URL + (database != null ? database.toString() : "");
        this.password = password.toString();
        connection = null;
        table = "";
    }

    public void setTable(CharSequence table) {
        this.table = table != null ? table.toString() : "";
    }

    public String getTable() {
        return table;
    }

    public String getUsername() {
        return username;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        if (isConnected()) {
            try {
                return connection.createStatement();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

    public void createConnection() {
        // Check if class exists
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public boolean hasTable() {
        return !table.isEmpty();
    }

    public ResultSet executeQuery(CharSequence query) {
        if (isConnected()) {
            try {
                return getStatement().executeQuery(query != null ? query.toString() : "");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

    public boolean isValid(ResultSet result) {
        return result != null;
    }


    public UpdateFeedback executeUpdate(CharSequence query) {
        query = query != null ? query : "";
        boolean table = query.toString().toLowerCase().startsWith("drop table") || query.toString().toLowerCase().startsWith("create table");
        if (isConnected()) {
            try {
                int count = connection.createStatement().executeUpdate(query.toString());
                // System.out.println(count);
                // System.out.println(dropTable);
                return new UpdateFeedback("Operation was " + ((table ? count > -1 : count > 0) ? "" : "not ") + "Successful!", count);
            } catch (SQLException e) {
                // System.out.println(e.getMessage());
                return new UpdateFeedback(e.getMessage(), table ? -1 : 0);
            }
        }
        return new UpdateFeedback("No Database was Connected!", table ? -1 : 0);
    }


    public TableUpdateFeedback createTable(Object data) {
        Advanced.ObjectDetail<?> detail = Advanced.getObjectDetail(data);
        StringBuilder query = new StringBuilder("create table " + detail.getName() + "(");

        for (DataType type : detail.getDataTypes()) {
            if (type.type.equals("OBJECT") || type.type.isEmpty()) {
                // createTable(detail.get(type.name));
                continue;
            }
            query.append(type.name).append(" ").append(type.type).append(", ");
        }
        if (query.toString().contains(","))
            query = new StringBuilder(query.substring(0, query.lastIndexOf(",")));
        query.append(") ");

        UpdateFeedback u = executeUpdate(query.toString());
        TableUpdateFeedback d = new TableUpdateFeedback(u.message, u.count + 1, u.count > -1);
        d.setType("Created");
        return d;
    }

    public TableUpdateFeedback deleteTable() {
        return deleteTable(table);
    }

    public TableUpdateFeedback deleteTable(CharSequence table) {
        String query = "drop table " + (table != null ? table : "");
        UpdateFeedback u = executeUpdate(query);
        TableUpdateFeedback t = new TableUpdateFeedback(u.message, u.count + 1, u.count > -1);
        t.setType("Deleted");
        return t;
    }



    public DataUpdateFeedback update(IdSpec where, Object data) {
        return updateFrom(table, where, data);
    }

    public DataUpdateFeedback update(CharSequence whereKey, int where, DataState spec, Object data) {
        return updateFrom(table, whereKey, where, spec, data);
    }

    public DataUpdateFeedback updateFrom(CharSequence table, CharSequence whereKey, int where, DataState spec, Object data) {
        if (whereKey == null || data == null)
            return new DataUpdateFeedback("Invalid data was inserted", 0, false);
        return updateFrom(table, new IdSpec(whereKey.toString(), where, spec), data);
    }

    public DataUpdateFeedback updateFrom(CharSequence table, IdSpec where, Object data) {
        StringBuilder query = new StringBuilder("update " + table + " set ");
        Advanced.ObjectDetail<?> detail = Advanced.getObjectDetail(data);
        for (String name : detail.names) {
            Object value = detail.get(name);
            query.append(name).append(" = ").append(value instanceof String ? "'" + value + "'" : value).append(", ");
        }
        if (query.toString().contains(","))
            query = new StringBuilder(query.substring(0, query.lastIndexOf(",")));
        query.append(" where ").append(where.key).append(" ").append(where.getSpec()).append(" ").append(where.id);

        // System.out.println(query);
        UpdateFeedback u = executeUpdate(query.toString());
        return new DataUpdateFeedback(u.message, u.count, u.count > 0);
    }


    public DataUpdateFeedback update(DataSpec where, Object data) {
        return updateFrom(table, where, data);
    }

    public DataUpdateFeedback update(CharSequence whereKey, CharSequence where, Object data) {
        return updateFrom(table, whereKey, where, data);
    }

    public DataUpdateFeedback updateFrom(CharSequence table, CharSequence whereKey, CharSequence where, Object data) {
        if (whereKey == null || data == null || where == null || table == null)
            return new DataUpdateFeedback("Invalid data was inserted", 0, false);
        return updateFrom(table, new DataSpec(whereKey.toString(), where.toString()), data);
    }

    public DataUpdateFeedback updateFrom(CharSequence table, DataSpec where, Object data) {
        StringBuilder query = new StringBuilder("update " + table + " set ");
        Advanced.ObjectDetail<?> detail = Advanced.getObjectDetail(data);
        for (String name : detail.names) {
            Object value = detail.get(name);
            query.append(name).append(" = ").append(value instanceof String ? "'" + value + "'" : value).append(", ");
        }
        if (query.toString().contains(","))
            query = new StringBuilder(query.substring(0, query.lastIndexOf(",")));
        query.append(" where ").append(where.key).append(" = '").append(where.value).append("'");

        UpdateFeedback u = executeUpdate(query.toString());
        return new DataUpdateFeedback(u.message, u.count, u.count > 0);
    }


    public DataUpdateFeedback insert(Object data) {
        return insertTo(table, data);
    }

    public DataUpdateFeedback insert(ObjectDetail<?> data) {
        return insertTo(table, data);
    }

    public DataUpdateFeedback insert(List<Object> data) {
        return insertTo(table, data);
    }

    public DataUpdateFeedback insertTo(CharSequence table, Object data) {
        return insertTo(table, Advanced.getObjectDetail(data));
    }

    public DataUpdateFeedback insertTo(CharSequence table, ObjectDetail<?> data) {
        StringBuilder query = new StringBuilder("insert into " + table + " (");
        for (String key : data.names)
            query.append(key).append(", ");
        query = new StringBuilder(query.substring(0, query.lastIndexOf(","))); // remove trailing comma
        query.append(") values (");
        for (Object value : data.values) {
            if (value instanceof String)
                value = "'" + value + "'";
            query.append(value).append(", ");
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf(","))); // remove trailing comma
        query.append(") ");
        // Main.println(query);
        UpdateFeedback u = executeUpdate(query);
        DataUpdateFeedback d = new DataUpdateFeedback(u.message, u.count, u.count > 0);
        d.setType("Inserted");
        return d;
    }

    public DataUpdateFeedback insertTo(CharSequence table, List<Object> data) {
        StringBuilder query = new StringBuilder("insert into " + table + " values");
        for (Object value : data) {
            if (value instanceof String)
                value = "'" + value + "'";
            query.append(value).append(", ");
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf(","))); // remove trailing comma
        query.append(") ");
        UpdateFeedback u = executeUpdate(query);
        DataUpdateFeedback d = new DataUpdateFeedback(u.message, u.count, u.count > 0);
        d.setType("Inserted");
        return d;
    }


    public DataUpdateFeedback delete(CharSequence key, int id, DataState spec) {
        return deleteFrom(table, key, id, spec);
    }

    public DataUpdateFeedback delete(IdSpec idSpec) {
        return deleteFrom(table, idSpec);
    }

    public DataUpdateFeedback deleteFrom(CharSequence table, CharSequence key, int id, DataState spec) {
        return deleteFrom(table, new IdSpec(key != null ? key.toString() : "", id, spec));
    }

    public DataUpdateFeedback deleteFrom(CharSequence table, CharSequence from, int fromId, DataState fromSpec, CharSequence to, int toId, DataState toSpec) {
        return deleteFrom(table, new IdSpec(from != null ? from.toString() : "", fromId, fromSpec), new IdSpec(to != null ? to.toString() : "", toId, toSpec));
    }


    public DataUpdateFeedback delete(DataSpec data) {
        return deleteFrom(table, data);
    }

    public DataUpdateFeedback delete(CharSequence key, CharSequence value) {
        return deleteFrom(table, key, value);
    }

    public DataUpdateFeedback deleteFrom(CharSequence table, CharSequence key, CharSequence value) {
        String query = "delete from " + table + " where " + key + " = " + value;
        UpdateFeedback u = executeUpdate(query);
        DataUpdateFeedback d = new DataUpdateFeedback(u.message, u.count, u.count > 0);
        d.setType("Deleted");
        return d;
    }

    public DataUpdateFeedback deleteFrom(CharSequence table, DataSpec data) {
        return deleteFrom(table, data.key, data.value);
    }


    public DataUpdateFeedback delete(CharSequence key, int fromId, DataState fromSpec, int toId, DataState toSpec) {
        return deleteFrom(table, key, fromId, fromSpec, key, toId, toSpec);
    }

    public DataUpdateFeedback delete(IdSpec fromSpec, IdSpec toSpec) {
        return deleteFrom(table, fromSpec, toSpec);
    }

    public DataUpdateFeedback deleteFrom(CharSequence table, IdSpec idSpec) {
        String query = "delete from " + table + " where " + idSpec.key + " " + idSpec.getSpec() + " " + idSpec.id;
        UpdateFeedback u = executeUpdate(query);
        DataUpdateFeedback d = new DataUpdateFeedback(u.message, u.count, u.count > 0);
        d.setType("Deleted");
        return d;
    }

    public DataUpdateFeedback deleteFrom(CharSequence table, IdSpec fromSpec, IdSpec toSpec) {
        String query = "delete from " + table + " where " + fromSpec.key + " " + fromSpec.getSpec() + " " + fromSpec.id + " and " + toSpec.getSpec() + " " + toSpec.id;
        UpdateFeedback u = executeUpdate(query);
        DataUpdateFeedback d = new DataUpdateFeedback(u.message, u.count, u.count > 0);
        d.setType("Deleted");
        return d;
    }


    // This record "IdSpec" helps stores the id and the operand indicator
    public record IdSpec(String key, int id, DataState spec) {

        public String getSpec() {
            return switch (spec) {
                case GreaterThan -> ">";
                case LessThan -> "<";
                case EqualTo -> "=";
                case GreaterThanEqualTo -> ">=";
                case LessThanEqualTo -> "<=";
            };
        }
    }

    public record DataSpec(String key, String value) {}

    // Update query feedback
    public record UpdateFeedback(String message, int count) {}

    // Database value type
    public record DataType(String name, String type) {}

    public record TableUpdateFeedback(String message, int updatedTableCount, boolean isUpdated) {
        private static String type = "Updated";

        public void setType(String type) {
            TableUpdateFeedback.type = type;
        }

        @Override
        public String toString() {
            return type + " Table Count: " + updatedTableCount + ", Was Table " + type + ": " + isUpdated + "; MESSAGE: " + message;
        }
    }

    public record DataUpdateFeedback(String message, int updatedDataCount, boolean isUpdated) {
        private static String type = "Updated";

        public void setType(String type) {
            DataUpdateFeedback.type = type;
        }

        @Override
        public String toString() {
            return type + " Data Count: " + updatedDataCount + ", Was Data " + type + ": " + isUpdated + "; MESSAGE: " + message;
        }
    }

}
