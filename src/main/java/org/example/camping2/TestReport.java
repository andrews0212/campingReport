package org.example.camping2;

import net.sf.jasperreports.engine.*;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.io.InputStream;

public class TestReport {
    public static void main(String[] args) {

        URL resourceURL = TestReport.class.getResource("/Camping/Flower.jasper");
        if (resourceURL != null) {
            System.out.println("Ruta del recurso: " + resourceURL.getPath());
        } else {
            System.out.println("No se encuentra el archivo.");
        }



    }
}
