package ru.skillbench.tasks.text.regex;

public class Test {
    public static void main(String[] args) {
        String test1 = "John A. Smith john@hp.com (123)456 7890 495 926-93-47 ext.1846 800 250 0890";
        String test2 = "John Smith john@hp.com (123)456 7890 495 926-93-47 ext.1846 800 250 0890";
        String test3 = "Julia Smith[\n" +
                "\n" +
                "Date of Birth: 22nd August 1985\n" +
                "Mobile: 07724168424\n" +
                "Office: 07712345678 ext.1234\n" +
                "Home:  111-123-45-67\n" +
                "E-mail: julia.smith@gmail.com \n" +
                "Dr]";
        String test4 = "[]Jonathan A. Smith john@hp.com (123)456 7890 495 926-93-47 ext.1846 800 250 0890";

        CurriculumVitae cv = new CurriculumVitaeImpl();

        cv.setText(test4);
        System.out.println(cv.getText());
        System.out.println(cv.getPhones());
        System.out.println(cv.getFullName());
        System.out.println(cv.getFirstName());
        System.out.println(cv.getLastName());
        System.out.println(cv.getMiddleName());

        cv.updateLastName("Baker");
        System.out.println(cv.getFullName());

        CurriculumVitae.Phone oldPhone = new CurriculumVitae.Phone("(123)456 7890", -1, -1);
        CurriculumVitae.Phone newPhone = new CurriculumVitae.Phone("(916)125-4171", -1, -1);
        System.out.println("Old phones: " + cv.getPhones());
        cv.updatePhone(oldPhone, newPhone);
        System.out.println("New phones: " + cv.getPhones());

        cv.updatePhone(newPhone, oldPhone);
        System.out.println("Get back:   " + cv.getPhones());

        cv.hide("Jonathan A. Baker");
        System.out.println(cv.getText());

        cv.hide("john@hp.com");
        System.out.println(cv.getText());

        cv.hidePhone("(123)456 7890");
        System.out.println(cv.getText());

        System.out.println(cv.unhideAll());
        System.out.println(cv.getText());


    }
}