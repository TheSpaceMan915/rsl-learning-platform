package app.services.interfaces;

/**
 * Defines a generic interface for service classes that fetch content from the Strapi CMS.
 * This interface ensures that any service class retrieving content from Strapi can do so through a standardized method.
 *
 * <p>The generic type {@code T} represents the type of content that is retrieved, which allows for flexibility in the type of data each service deals with.</p>
 *
 * <p>This interface includes a constant for the base URL of the Strapi server to ensure uniform access across all implementing services.</p>
 *
 * @author  Nikita Kolychev
 */
public interface Contained<T> {

    /**
     * The base URL for the Strapi CMS from which content is fetched.
     */
    String BASE_URL = "http://51.250.105.110:1337";

    /**
     * Retrieves all content from Strapi corresponding to the generic type {@code T}.
     * This method is responsible for accessing the CMS and fetching the content that is then processed by the implementing service.
     *
     * @return an instance of type {@code T} containing all requested content from Strapi.
     */
    T getAllContent();
}
