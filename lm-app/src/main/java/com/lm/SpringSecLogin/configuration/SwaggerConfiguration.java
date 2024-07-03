package com.lm.SpringSecLogin.configuration;

import io.swagger.v3.oas.models.Components;                                 // Import of Components class. We use Components class to define the security schemes and security requirements for the API.
import io.swagger.v3.oas.models.OpenAPI;                                    // Import of OpenAPI class. We use OpenAPI class to define the metadata for the API.
import io.swagger.v3.oas.models.info.Info;                                  // Import of Info class. Where we define all the relevant info about our project.
import io.swagger.v3.oas.models.info.License;                               // Import of License class. We use License class to define the license information for the API.
import org.springframework.context.annotation.Bean;                         // Import of Bean class. We use Bean class to define a method that returns an object that should be registered as a bean in the Spring application context.
import org.springframework.context.annotation.Configuration;                // Import of Configuration class. We use Configuration class to define a class as a configuration class in Spring application context.
import io.swagger.v3.oas.models.info.Contact;                               // Import of Contact class. We use Contact class to define the contact information for the API.
import io.swagger.v3.oas.models.security.SecurityRequirement;               // Given that we are using JWT, we need to define the security requirements for the API (i.e., the security schemes that are applied to the API).
import io.swagger.v3.oas.models.security.SecurityScheme;                    // Our API uses JWT for authentication, so we need to define the security scheme for the API.
import lombok.Getter;                                                       // We use Lombok's Getter annotation to automatically generate getter methods for the properties in the class.
import lombok.Setter;                                                       // We use Lombok's Setter annotation to automatically generate setter methods for the properties in the class.
import org.springframework.boot.context.properties.ConfigurationProperties; // Import of ConfigurationProperties class. Needed in order to get the properties from the application.yml file.

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;   // Import of HTTP class. We use HTTP class to define the type of security scheme used in the API.

/*
    * SwaggerConfiguration class.
    * This class is used to configure the Swagger API documentation for the application.
    * We define the metadata for the API, the security schemes, and the security requirements for the API.
 */

@Configuration
@ConfigurationProperties(prefix = "swagger")                                // This means that we can set the properties in the application.yml file under the "swagger prefix".
@Getter
@Setter
public class SwaggerConfiguration
{
    private String appName;
    private String appDescription;
    private String appVersion;
    private String appLicense;
    private String appLicenseUrl;
    private String contactName;
    private String contactUrl;
 

    // We define the OpenAPI object that contains all the metadata for the API.
    @Bean
    public OpenAPI customOpenAPI()
    {
        final Info apiInformation = getApiInformation();                // We get the API information from the application.yml file.
        final Components components = new Components();                 // We define the components for the API.

        final String schemeName = "bearerAuth";                         // We define the security scheme name for the API.

        // We define the security scheme for the API.
        /*
            * The security scheme is defined as a bearer token scheme, with name "bearerAuth", type "HTTP", scheme "bearer", and bearer format "JWT".
         */
        components.addSecuritySchemes(schemeName, new SecurityScheme().name(schemeName).type(HTTP).scheme("bearer").bearerFormat("JWT"));

        final OpenAPI openAPI = new OpenAPI();                          // We create our OpenAPI object.
        openAPI.setInfo(apiInformation);                                // We set the API information we got before.
        openAPI.setComponents(components);                              // We set the components for the API (i.e., the security schemes and security requirements).
        openAPI.addSecurityItem(new SecurityRequirement().addList(schemeName)); // We add the security requirements for the API.

        return openAPI;
    }

    // We get the API information from the application.yml file.
    private Info getApiInformation()
    {
        final License license = new License();
        license.setName(appLicense);
        license.setUrl(appLicenseUrl);

        final Contact contact = new Contact();
        contact.setName(contactName);
        contact.setUrl(contactUrl);

        final Info info = new Info();
        info.setTitle(appName);
        info.setDescription(appDescription);
        info.setVersion(appVersion);
        info.setLicense(license);
        info.setContact(contact);

        return info;
    }
}