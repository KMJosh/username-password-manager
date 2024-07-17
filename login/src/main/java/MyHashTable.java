package main.java;

import java.util.ArrayList;

public class MyHashTable implements HashInterface {
    private int size = 7;
    private Node[] dataMap;

    public MyHashTable() {
        dataMap = new Node[size];
    }

    class Node {
        private String key;
        private String value;
        private Node next;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hash(String key) {
        int hash = 0;
        char[] keyChars = key.toCharArray();
        for (int i = 0; i < keyChars.length; i++) {
            int ascii = keyChars[i];
            hash = (hash + ascii * 11) % dataMap.length;
        }
        return hash;
    }

    public void set(String key, String value) {
        int index = hash(key);
        Node newNode = new Node(key, value);
        if (dataMap[index] == null) {
            dataMap[index] = newNode;
        } else {
            Node temp = dataMap[index];
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public String get(String key) {
        int index = hash(key);
        Node temp = dataMap[index];
        while (temp != null) {
            if (temp.key.equals(key)) {
                return temp.value;
            } else {
                temp = temp.next;
            }
        }
        return null;
    }

    public ArrayList<String> keys() {
        ArrayList<String> allKeys = new ArrayList<>();
        for (int i = 0; i < dataMap.length; i++) {
            Node temp = dataMap[i];
            while (temp != null) {
                allKeys.add(temp.key);
                temp = temp.next;
            }
        }
        return allKeys;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        for (int i = 0; i < dataMap.length; i++) {
            if (dataMap[i] != null) {
                return false;
            }
        }
        return true;
    }

    public void remove(String key) {
        int index = hash(key);
        Node current = dataMap[index];
        Node prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    dataMap[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public void clear() {
        for (int i = 0; i < dataMap.length; i++) {
            dataMap[i] = null;
        }
        size = 0;
    }
}
