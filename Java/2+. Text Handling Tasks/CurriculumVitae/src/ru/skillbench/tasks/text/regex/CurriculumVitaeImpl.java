package ru.skillbench.tasks.text.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurriculumVitaeImpl implements CurriculumVitae {

    private String text;
    private HashMap<String, String> hidden = new HashMap<String, String>();

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
//         Если текст резюме не был задан путем вызова setText(String)
        if (text == null) {
            throw new IllegalStateException();
        }
        return text;
    }

    public List<Phone> getPhones() {
//         Если текст резюме не был задан путем вызова setText(String).
        if (text == null) {
            throw new IllegalStateException();
        }

        List<Phone> phones = new ArrayList<>();

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(getText());

        while (matcher.find()) {
//            Инициализируем необязательные переменные в соответсвии с описанием класса Phone
            int areaCode = -1;
            int extension = -1;
//            areaCode
            if (matcher.group(2) != null) {
                areaCode = Integer.parseInt(matcher.group(2));
            }
//            extension
            if (matcher.group(7) != null) {
                extension = Integer.parseInt(matcher.group(7));
            }
            Phone newPhone = new Phone(matcher.group(), areaCode, extension);
            phones.add(newPhone);
        }
        return phones;
    }

    public String getFullName() {
//         Если текст резюме не был задан путем вызова setText(String)
        if (text == null) {
            throw new IllegalStateException();
        }

        String namePattern = "([A-Z][a-z]*[.]*)\\s([A-Z][a-z]*[.]*)?\\s*([A-Z][a-z]*[.]*)";
        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
//            fullName
            return matcher.group().trim();
        } else {
//            Если резюме не содержит полного имени, которое удовлетворяет критериям
            throw new NoSuchElementException();
        }
    }

    public String getFirstName() {
        String fullName = getFullName();
//        Необходимые исключения уже выбрасываются методом getFullName
        return fullName.split(" ")[0];
    }

    public String getMiddleName() {
        String fullName = getFullName();
//        Необходимые исключения уже выбрасываются методом getFullName
        if (fullName.split(" ").length == 3) {
            return fullName.split(" ")[1];
        } else {
            return null;
        }
    }

    public String getLastName() {
        String fullName = getFullName();
//        Необходимые исключения уже выбрасываются методом getFullName
        return fullName.split(" ")[fullName.split(" ").length - 1];
    }

    public void updateLastName(String newLastName) {
        String lastName = getLastName();
//        Необходимые исключения уже выбрасываются методом getFullName
        text = text.replace(lastName, newLastName);
    }

    public void updatePhone(Phone oldPhone, Phone newPhone) {
//        Если текст резюме не был задан путем вызова setText(String)
        if (this.text == null) {
            throw new IllegalStateException();
        }

        List<Phone> phones = getPhones();
//        Если резюме не содержит текста, равного oldPhone.getNumber()
        if (!text.contains(oldPhone.getNumber())) {
            throw new IllegalArgumentException();
        }

        text = text.replace(oldPhone.getNumber(), newPhone.getNumber());
    }

    public void hide(String piece) {
//        Если текст резюме не был задан путем вызова setText(String)
        if (text == null) {
            throw new IllegalStateException();
        }
//        Если резюме не содержит текста, равного piece
        if (!text.contains(piece)) {
            throw new IllegalArgumentException();
        }

        String sourceString = piece.replaceAll("[^. @]", "X");
        hidden.put(sourceString, piece);
        text = text.replace(piece, sourceString);
    }

    public void hidePhone(String phone) {
//        Если текст резюме не был задан путем вызова setText(String)
        if(text == null) {
            throw new IllegalStateException();
        }
//        Если резюме не содержит текста, равного phone
        if(!text.contains(phone)) {
            throw new IllegalArgumentException();
        }

        String sourcePhone = phone.replaceAll("[\\d]", "X");
        hidden.put(sourcePhone, phone);
        text = text.replace(phone, sourcePhone);
    }

    public int unhideAll() {
//        Если текст резюме не был задан путем вызова setText(String)
        if(text == null) {
            throw new IllegalStateException();
        }

        int replacedNum = 0;
        for(HashMap.Entry<String, String> entry : hidden.entrySet()) {
            text = text.replaceAll(entry.getKey().replaceAll("\\(", "\\\\\\(").replaceAll("\\)", "\\\\\\)"), entry.getValue());
            replacedNum++;
        }
        return replacedNum;
    }
}