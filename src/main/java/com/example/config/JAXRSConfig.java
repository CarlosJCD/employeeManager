package com.example.config;  // Ubicación de tu clase de configuración

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")  // Define el prefijo base para todas las rutas de la API
public class JAXRSConfig extends Application {
    // No es necesario añadir más código, solo esta anotación es suficiente
}
