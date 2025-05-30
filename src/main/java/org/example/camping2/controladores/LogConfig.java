package org.example.camping2.controladores;
import java.io.IOException;
import java.util.logging.*;

public class LogConfig {
    public static Logger configurarLogger(Class<?> clase) {
        Logger logger = Logger.getLogger(clase.getName());
        try {
            Handler fileHandler = new FileHandler("logs/app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.WARNING);  // Solo warnings y errores

            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.WARNING);      // Solo warnings y errores también en el logger

        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}
