package ru.skillbench.tasks.basics.entity;

public class Test {
    public static void main(String[] args) {
        Location locCountry = new LocationImpl();
        locCountry.setName("Россия");
        locCountry.setType(Location.Type.COUNTRY);

        Location locCity = new LocationImpl();
        locCity.setName("Москва");
        locCity.setType(Location.Type.CITY);
        locCity.setParent(locCountry);

        Location locStreet = new LocationImpl();
        locStreet.setName("Институтский пер.");
        locStreet.setType(Location.Type.STREET);
        locStreet.setParent(locCity);

        Location locBuilding = new LocationImpl();
        locBuilding.setName("10 к. 1");
        locBuilding.setType(Location.Type.BUILDING);
        locBuilding.setParent(locStreet);

        Location locApartment = new LocationImpl();
        locApartment.setName("кв. 213");
        locApartment.setType(Location.Type.APARTMENT);
        locApartment.setParent(locBuilding);

        System.out.println(locApartment.getName());
        System.out.println(locApartment.getType());
        System.out.println(locApartment.getParentName());
        System.out.println(locApartment.getTopLocation().getName());
        System.out.println(locApartment.isCorrect());
        System.out.println(locApartment.getAddress());
        System.out.println(locApartment.toString());

    }
}
