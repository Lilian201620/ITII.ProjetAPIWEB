package data.remote;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.remote.APICaller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class APICallerTest {

    // Test sur byName avec des resultats
    @Test
    public void testApiCallByNameExpectsResponseNotEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByName("paris");
        assertNotEquals(0, response.size());
    }

    // Test sur byName sans resultats
    @Test
    public void testApiCallByNameExpectsEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByName("zzzzz");
        assertEquals(0, response.size());
    }

    // Test sur byCodePostal avec des resultats
    @Test
    public void testApiCallByCodePostalExpectsResponseNotEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByCodePostal("60130");
        assertNotEquals(0, response.size());
    }

    // Test sur byCodePostal sans resultats
    @Test
    public void testApiCallByCodePostalExpectsEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByCodePostal("zzzzz");
        assertEquals(0, response.size());
    }

    // Test sur byCodePostal avec des resultats
    @Test
    public void testApiCallByCodeDepartementExpectsResponseNotEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByDepartement("60");
        assertNotEquals(0, response.size());
    }

    // Test sur byCodePostal sans resultats
    @Test
    public void testApiCallByCodeDepartementExpectsEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByDepartement("zzzzz");
        assertEquals(0, response.size());
    }

}
