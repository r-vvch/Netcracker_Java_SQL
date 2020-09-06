package ru.skillbench.tasks.text;

import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounterImpl implements WordCounter {
    private String text;

    public WordCounterImpl() {
        text = null;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

//    для getWordCounts
    private String deleteComments(String str) {
        Pattern pattern = Pattern.compile("<[^><]*>");
        Matcher matcher = pattern.matcher(str);
        String output = matcher.replaceAll("");
        if (!str.equals(output)) {
            return deleteComments(output);
        } else {
            return output;
        }
    }

    public Map<String, Long> getWordCounts() {
//        Если не задан текст для анализа
        if (this.getText() == null || this.getText().equals("")) {
            throw new IllegalStateException();
        }

        String output = deleteComments(this.getText().trim());
        Pattern pattern = Pattern.compile("\\p{Space}+");
        String[] words = pattern.split(output);

        Map<String, Long> map = new TreeMap<>();
        for (String str: words) {
            String strLower = str.toLowerCase();
            Long count = map.get(strLower);
            if (count != null && count != 0) {
                map.remove(strLower);
                map.put(strLower, ++count);
            } else {
                map.put(strLower, 1L);
            }
        }
        return map;
    }

    public List<Map.Entry<String, Long>> getWordCountsSorted() {
        //        Если не задан текст для анализа
        if (this.getText() == null || this.getText().equals("")) {
            throw new IllegalStateException();
        }

        Map<String, Long> textMap = getWordCounts();
        Comparator<Map.Entry<String, Long>> cmp = new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };
        return this.sort(textMap, cmp);
    }

    public <K extends Comparable<K>, V extends Comparable<V>> List<Map.Entry<K, V>> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(comparator);
        return list;
    }

    public <K, V> void print(List<Map.Entry<K, V>> entries, PrintStream ps) {
        List<Map.Entry<String, Long>> list = this.getWordCountsSorted();
        if (list == null) {
            return;
        }
        for (Map.Entry<String, Long> item : list) {
            ps.println(item.getValue() + " " + item.getKey());
        }
    }
}
