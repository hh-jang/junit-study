package ch12;

public class Address {
    public final String road;
    public final String city;
    public final String state;
    public final String zip;
    public final String houseNumber;

    public Address(String houseNumber, String road, String city, String state, String zip) {
        this.road = road;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return road + " " + city + " " + state + " " + zip + " " + houseNumber;
    }
}
