import javafx.scene.control.Button;
import org.example.camping2.Mapa.MapaCamping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapaCampingLogicaTest {

    private MapaCamping mapa;

    @BeforeEach
    public void setUp() {
        mapa = new MapaCamping();
    }

    @Test
    public void testActualizarColoresCasas_NoLanzaExcepcion() {
        // Verificamos que llamar a actualizarColoresCasas no lanza ninguna excepción
        assertDoesNotThrow(() -> mapa.actualizarColoresCasas());
    }
}
