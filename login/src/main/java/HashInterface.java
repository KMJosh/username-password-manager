package main.java;

interface HashInterface {
    void set(String key, String value);

    String get(String key);

    boolean isEmpty();

    int size();

    void remove(String key);

    void clear();
}
