package ru.skillbench.tasks.text;

public class Test {
    public static void main(String[] args) {
        String text = "damn damn damn boi boi he thicc thicc thicc thicc";
        WordCounter wordCounter = new WordCounterImpl();
        wordCounter.setText(text);
        System.out.println(wordCounter.getText());
        System.out.println(wordCounter.getWordCounts());
        System.out.println(wordCounter.getWordCountsSorted());
        wordCounter.print(wordCounter.getWordCountsSorted(), System.out);
    }
}
