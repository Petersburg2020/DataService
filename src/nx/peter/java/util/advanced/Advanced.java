package nx.peter.java.util.advanced;

import nx.peter.java.json.reader.JsonArray;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.Util;

import java.lang.reflect.*;
import java.util.*;

public class Advanced {

    public static <T> ObjectDetail<T> getObjectDetail(T model) {
        if (model == null)
            return new ObjectDetail<>();
        Class<T> clazz = (Class<T>) model.getClass();
        Field[] fields = clazz.getFields();
        List<String> names = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        List<ObjectDetail.Type> types = new ArrayList<>();
        for (Field field : fields) {
            names.add(field.getName());
            Class type = field.getType();
            try {
                if (field.isEnumConstant()) {
                    values.add(field.get(model));
                    types.add(ObjectDetail.Type.Enum);
                } else if (type.equals(int.class)) {
                    values.add(field.getInt(model));
                    types.add(ObjectDetail.Type.Integer);
                } else if (type.equals(boolean.class)) {
                    values.add(field.getBoolean(model));
                    types.add(ObjectDetail.Type.Boolean);
                } else if (type.equals(double.class)) {
                    values.add(field.getDouble(model));
                    types.add(ObjectDetail.Type.Double);
                } else if (type.equals(float.class)) {
                    values.add(field.getFloat(model));
                    types.add(ObjectDetail.Type.Float);
                } else if (type.equals(long.class)) {
                    values.add(field.getLong(model));
                    types.add(ObjectDetail.Type.Long);
                } else if (type.equals(String.class)) {
                    values.add(field.get(model).toString());
                    types.add(ObjectDetail.Type.String);
                } else if (type.equals(Map.class)) {
                    values.add(field.get(model));
                    types.add(ObjectDetail.Type.Map);
                } else if (type.equals(List.class)) {
                    values.add(field.get(model));
                    types.add(ObjectDetail.Type.List);
                } else {
                    values.add(field.get(model));
                    types.add(ObjectDetail.Type.Object);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                System.out.println(e.getMessage());
                System.out.println("An error occurred!");
            }
        }
        return new ObjectDetail<>(names, values, types, clazz);
    }

    public static <T> T getObject(Map<String, Object> detail, Class<T> clazz) {
        if (detail == null | clazz == null)
            return null;
        Class[] parameters = null;
        List<String> names = null;
        for (Constructor<?> c : clazz.getConstructors())
            if (equals(clazz, c)) {
                parameters = new Class[c.getParameterCount()];
                names = getSortedParameterNames(c, clazz);
                int index;
                for (Parameter p : c.getParameters()) {
                    index = Integer.parseInt(p.getName().substring(3));
                    parameters[index] = p.getType();
                }
                break;
            }
        boolean compat = parameters != null;
        if (compat)
            try {
                return clazz.getConstructor(parameters).newInstance(sortByName(detail, names).toArray());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | ClassCastException | IllegalArgumentException | IllegalAccessException e) {
                System.out.println(e.getMessage());
                return null;
            }
        return null;
    }

    public static <T> JsonObject<?> toJson(Map<String, T> map) {
        if (map != null) {
            nx.peter.java.json.JsonObject object = new nx.peter.java.json.JsonObject();
            for (String name : map.keySet()) {
                Object value = map.get(name);
                if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float || value instanceof Boolean || value instanceof String || value instanceof Byte)
                    object.add(name, value);
                else if (value instanceof Object[])
                    object.add(name, toJson((Object[]) value));
                else if (value instanceof List)
                    object.add(name, toJson(Util.toObjectList((List<?>) value)));
                else if (value instanceof Map)
                    object.add(name, toJson(Util.toObjectMap((Map<?, ?>) value)));
                else
                    object.add(name, toJson(value));
            }
            return object;
        }
        return null;
    }

    @SafeVarargs
    public static <T> JsonArray<?> toJson(T... value) {
        return toJson(Arrays.asList(value));
    }

    public static JsonArray<?> toJson(List<?> list) {
        nx.peter.java.json.JsonArray array = new nx.peter.java.json.JsonArray();
        if (list != null)
            // System.out.println("We are array!");
            for (Object value : list)
                if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float || value instanceof Boolean || value instanceof String || value instanceof Byte)
                    array.add(value);
                else if (value instanceof Object[])
                    array.add(toJson((Object[]) value));
                else if (value instanceof List)
                    array.add(toJson(Util.toObjectList((List<?>) value)));
                else if (value instanceof Map)
                    array.add(toJson(Util.toObjectMap((Map<?, ?>) value)));
                else
                    array.add(toJson(value));
        return array;
    }

    public static <T> JsonObject<?> toJson(T model) {
        nx.peter.java.json.JsonObject object = new nx.peter.java.json.JsonObject();
        if (model != null) {
            ObjectDetail<T> detail = getObjectDetail(model);
            for (String name : detail.names) {
                Object value = detail.get(name);
                if (isRawData(value))
                    object.add(name, value);
                else if (Util.isArr(value))
                    object.add(name, toJson(Util.toArray(value)));
                else if (value instanceof List)
                    object.add(name, toJson(Util.toObjectList((List<?>) value)));
                else if (value instanceof Map)
                    object.add(name, toJson(Util.toObjectMap((Map<?, ?>) value)));
                else
                    object.add(name, toJson(value));
            }
            object.add("className", model.getClass().getName());
        }
        return object;
    }

    private static boolean isRawData(Object value) {
        return value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float || value instanceof Boolean || value instanceof String || value instanceof Byte;
    }

    public static <T> T fromJson(JsonObject<?> json, Class<T> clazz) {
        if (json.containsKey("className")) {
            Map<String, Object> detail = new LinkedHashMap<>();
            try {
                if (!clazz.equals(Class.forName(json.getString("className"))))
                    return null;
                for (String key : json.getKeys())
                    if (!key.contentEquals("className")) {
                        Object value = json.get(key);
                        if (isRawData(value))
                            detail.put(key, value);
                        else if (value instanceof nx.peter.java.json.JsonObject) {
                            Map<String, Object> map = new LinkedHashMap<>();
                            for (String k : json.getObject(key).getKeys())
                                map.put(k, json.getObject(key).get(k));
                            detail.put(key, map);
                        } else if (value instanceof JsonArray) {
                            List<Object> list = new ArrayList<>();
                            for (Object v : json.getArray(key))
                                list.add(v);
                            detail.put(key, list);
                        }
                    }
                return getObject(detail, clazz);
            } catch (ClassNotFoundException | ClassCastException e) {
                return null;
            }
        }
        return null;
    }


    public static Node<?, ?> toPIS(Map<String, Object> map) {

        return null;
    }

    @SafeVarargs
    public static <T> Node<?, ?> toPIS(T... value) {
        return toPIS(Arrays.asList(value));
    }

    public static Node<?, ?> toPIS(List<Object> list) {
        nx.peter.java.pis.Node node = new nx.peter.java.pis.Node();
        if (list != null) {
            for (Object value : list)
                if (isRawData(value))
                    node.addNode("list-item", value);
                else if (value instanceof Object[] && Util.isArr(value))
                    node.addNode("list-item", toPIS((Object[]) value));
                else if (value instanceof List)
                    node.addNode("list-item", toPIS(Util.toObjectList((List<?>) value)));
                else if (value instanceof Map)
                    node.addNode("list-item", toPIS(Util.toObjectMap((Map<?, ?>) value)));
                else
                    node.addNode("list-item", toPIS(value));
        }
        return node;
    }

    public static <T> Node<?, ?> toPIS(T model) {
        nx.peter.java.pis.Node node = new nx.peter.java.pis.Node();
        if (model != null) {
            ObjectDetail<T> detail = getObjectDetail(model);
            for (String name : detail.names) {
                Object value = detail.get(name);
                if (isRawData(value))
                    node.addNode(name, value);
                else if (Util.isArr(value))
                    node.addNode(name, toPIS(Util.toArray(value)));
                else if (value instanceof List)
                    node.addNode(name, toPIS(Util.toObjectList((List<?>) value)));
                else if (value instanceof Map)
                    node.addNode(name, toPIS(Util.toObjectMap((Map<?, ?>) value)));
                else
                    node.addNode(name, toPIS(value));
            }
            node.addAttr("className", model.getClass().getName());
        }
        return node;
    }

    public static <T> T fromPIS(Node<?, ?> node, Class<T> clazz) {
        if (node.containsAttr("className")) {
            Map<String, Object> detail = new LinkedHashMap<>();
            try {
                if (!clazz.equals(Class.forName(node.getAttribute("className").getString()))) return null;
                for (Node<?, ?> n : node.getChildren())
                    detail.put(n.getTag(), n.get());
                return getObject(detail, clazz);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        return null;
    }


    public static List<Object> sortByName(Map<String, Object> detail, List<String> names) {
        if (detail == null | names == null)
            return new ArrayList<Object>();
        List<Object> vals = new ArrayList<>();
        // System.out.println("Sort");
        for (String name : names)
            vals.add(detail.get(name));
        // System.out.println(detail.get(name) + ": " + name);
        return vals;
    }

    public static boolean isCompatibile(Set<String> params, Class clazz) {
        List<String> fields = getFieldNames(clazz);
        boolean good = fields.size() == params.size();
        if (good) {
            for (String field : fields)
                if (!params.contains(field))
                    return false;
            return true;
        }
        return false;
    }

    public static List<String> getFieldNames(Class clazz) {
        Field[] fields = clazz.getFields();
        List<String> names = new ArrayList<>();
        for (Field field : fields)
            names.add(field.getName());
        return names;
    }

    public static List<Field> getFields(Class clazz) {
        return new ArrayList<>(Arrays.asList(clazz.getFields()));
    }

    public static boolean equals(List<String> fields, List<String> parameters) {
        if (fields.size() == parameters.size()) {
            for (int index = 0; index < fields.size(); index++)
                if (!fields.get(index).equals(parameters.get(index)))
                    return false;
            return true;
        }
        return false;
    }

    public static boolean equals(Class clazz, Constructor constructor) {
        if (clazz.getFields().length == constructor.getParameters().length) {
            for (Field field : clazz.getFields())
                if (!contains(constructor, field))
                    return false;
            return true;
        }
        return false;
    }

    public static List<String> getSortedParameterNames(Constructor constructor, Class clazz) {
        if (clazz == null | constructor == null)
            return new ArrayList<String>();
        List<String> names = new ArrayList<>();
        if (equals(clazz, constructor)) {
            for (Parameter param : constructor.getParameters()) {
                for (Field field : clazz.getFields()) {
                    if (param.getType().equals(field.getType()) && !names.contains(field.getName()))
                        names.add(field.getName());
                }
            }
        }
        return names;
    }

    public static boolean contains(Constructor constructor, Field field) {
        if (field == null | constructor == null)
            return false;
        // System.out.println(getParameterTypes(constructor));
        // System.out.println(field.getType());
        // System.out.println(getParameterNames(constructor));
        // System.out.println(field.getName());
        // System.out.println("Has type (" + field.getType() + ": " + getParameterTypes(constructor).contains(field.getType()));
        // System.out.println("Has name: " + getParameterNames(constructor).contains(field.getName()));
        return getParameterTypes(constructor).contains(field.getType()) /*&& getParameterNames(constructor).contains(field.getName())*/;
    }

    public static List<Class> getParameterTypes(Constructor constructor) {
        if (constructor == null)
            return new ArrayList<>();
        Parameter[] parameters = constructor.getParameters();
        List<Class> types = new ArrayList<>();
        for (Parameter field : parameters)
            types.add(field.getType());
        return types;
    }

    public static List<String> getParameterNames(Constructor constructor) {
        if (constructor == null)
            return new ArrayList<>();
        Parameter[] parameters = constructor.getParameters();
        List<String> names = new ArrayList<>();
        for (Parameter param : parameters)
            names.add(param.getName());
        return names;
    }

    public static String getName(Class clazz) {
        if (clazz == null)
            return "";
        String name = clazz.getName();
        if (name.contains("."))
            name = name.substring(name.lastIndexOf(".") + 1);
        return name;
    }


    public static ObjectDetail.Type getType(Object obj) {
        if (obj == null) return ObjectDetail.Type.None;
        // Class type = obj.getClass();
        if (Util.isInt(obj)) return ObjectDetail.Type.Integer;
        else if (Util.isBoolean(obj)) return ObjectDetail.Type.Boolean;
        else if (Util.isFloat(obj)) return ObjectDetail.Type.Float;
        else if (Util.isDouble(obj)) return ObjectDetail.Type.Double;
        else if (Util.isLong(obj)) return ObjectDetail.Type.Long;
        else if (Util.isString(String.class)) return ObjectDetail.Type.String;
        else if (Util.isObject(Map.class)) return ObjectDetail.Type.Map;
        else if (Util.isArray(List.class)) return ObjectDetail.Type.List;
        else return ObjectDetail.Type.Object;
    }

    public static int toInt(Object obj, int def) {
        if (getType(obj).equals(ObjectDetail.Type.Integer))
            return Integer.parseInt(obj.toString());
        return def;
    }

    public static double toDouble(Object obj, double def) {
        if (getType(obj).equals(ObjectDetail.Type.Double))
            return Double.parseDouble(obj.toString());
        return def;
    }

    public static float toFloat(Object obj, float def) {
        if (getType(obj).equals(ObjectDetail.Type.Double))
            return Float.parseFloat(obj.toString());
        return def;
    }

    public static long toLong(Object obj, long def) {
        if (getType(obj).equals(ObjectDetail.Type.Double))
            return Long.parseLong(obj.toString());
        return def;
    }

    public static boolean toBoolean(Object obj, boolean def) {
        if (getType(obj).equals(ObjectDetail.Type.Double))
            return Boolean.parseBoolean(obj.toString());
        return def;
    }


    public static class ObjectDetail<T> {
        public final List<String> names;
        public final List<Object> values;
        public final List<Type> types;

        public final Class<T> clazz;


        public enum Type {
            Boolean,
            Double,
            Enum,
            Float,
            Integer,
            List,
            Long,
            Map,
            None,
            Object,
            String
        }

        public ObjectDetail() {
            this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
        }

        public ObjectDetail(List<String> names, List<Object> values, List<Type> type, Class<T> clazz) {
            this.names = names;
            this.values = values;
            this.types = type;
            this.clazz = clazz;
        }

        public Object get(String name) {
            return containsName(name) ? values.get(indexOf(name)) : null;
        }

        public List getList(String name) {
            return getType(name).equals(Type.List) ? (List) values.get(indexOf(name)) : null;
        }

        public Map getMap(String name) {
            return getType(name).equals(Type.Map) ? (Map) values.get(indexOf(name)) : null;
        }

        public String getString(String name) {
            return getType(name).equals(Type.String) ? (String) get(name) : null;
        }

        public double getDouble(String name, double defaultValue) {
            return getType(name).equals(Type.Double) ? (double) get(name) : defaultValue;
        }

        public float getFloat(String name, float defaultValue) {
            return getType(name).equals(Type.Float) ? (float) get(name) : defaultValue;
        }

        public Enum getEnum(String name, Enum type) {
            return getType(name).equals(Type.Enum) ? ((Enum) get(name)) : null;
        }

        public long getLong(String name, long defaultValue) {
            return getType(name).equals(Type.Long) ? (long) get(name) : defaultValue;
        }

        public int getInt(String name, int defaultValue) {
            return getType(name).equals(Type.Integer) ? (int) get(name) : defaultValue;
        }

        public boolean getBoolean(String name, boolean defaultValue) {
            return getType(name).equals(Type.Boolean) ? (boolean) get(name) : defaultValue;
        }

        public Type getType(String name) {
            return containsName(name) ? types.get(indexOf(name)) : Type.None;
        }

        public boolean containsName(String name) {
            return names.contains(name);
        }

        public int indexOf(String name) {
            return names.indexOf(name);
        }

        public boolean isEmpty() {
            return names.isEmpty() || values.isEmpty();
        }

        public boolean containsValue(Object value) {
            return values.contains(value);
        }

        public Iterator<String> names() {
            return names.iterator();
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            for (String name : names)
                map.put(name, get(name));
            return map;
        }

        @Override
        public String toString() {
            return "Names: " + names.toString() + "\nValues: " + values.toString();
        }

    }

}

