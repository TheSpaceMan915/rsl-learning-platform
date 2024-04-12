package app.services.interfaces;

public interface Contained<T> {

    String BASE_URL = "http://51.250.105.110:1337";

    T getAllContent();
}
