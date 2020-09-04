package ru.skillbench.tasks.text;

public class Test {
    public static void main(String[] args) {
        ContactCard contactCard = new ContactCardImpl();
//        contactCard.getInstance(
//                "BEGIN:VCARD\r\n" +
//                "FN:Forrest Gump\r\n" +
//                "ORG:Bubba Gump Shrimp Co.\r\n" +
//                "BDAY:06-06-1944\r\n" +
//                "TEL;TYPE=WORK,VOICE:4951234567\r\n" +
//                "TEL;TYPE=CELL,VOICE:9150123456\r\n" +
//                "END:VCARD");

        contactCard.getInstance(
                "BEGIN:VCARD\r\n" +
                "FN:Анастасия Иванова\r\n" +
                "ORG:Подружка\r\n" +
                "GENDER:F\r\n" +
                "END:VCARD");

        System.out.println(contactCard.getFullName());
        System.out.println(contactCard.getOrganization());
        System.out.println(contactCard.isWoman());
//        System.out.println(contactCard.getBirthday());
//        System.out.println(contactCard.getAge());
//        System.out.println(contactCard.getAgeYears());
//        System.out.println(contactCard.getPhone("CELL,VOICE"));
    }
}