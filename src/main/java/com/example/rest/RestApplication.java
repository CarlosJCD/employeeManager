package com.example.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api") // Este será el prefijo para todos tus servicios REST
public class RestApplication extends Application {
}
