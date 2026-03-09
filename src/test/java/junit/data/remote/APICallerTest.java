package junit.data.remote;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.enums.APIField;
import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.models.objets.Etablissement;
import fr.itii.apiweb.domain.tools.Backend;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class APICallerTest {

    APICaller api = new APICaller();
    Backend backend = new Backend();

    @Test
    public void testApiCallByNameExpectsResponseNotEmpty() {
        JsonNode response = api.getCommunes(APIField.Commune.NOM,"Beauvais", 100);
        assertNotEquals(0, response.size());
        System.out.println(response);
    }

    @Test
    public void testApiCallByNameExpectsEmpty() {
        JsonNode response = api.getCommunes(APIField.Commune.NOM,"zzz", 100);
        assertNotEquals(0, response.size());
        System.out.println(response);
    }

    @Test
    public void testApiCallByCodePostalExpectsResponseNotEmpty() {
        JsonNode response = api.getCommunes(APIField.Commune.CODE_POSTAL,"60000", 100);
        assertNotEquals(0, response.size());
        System.out.println(response);
    }


    @Test
    public void testApiCallByCodePostalExpectsEmpty() {
        JsonNode response = api.getCommunes(APIField.Commune.CODE_POSTAL, "00", 100);
        assertNotEquals(0, response.size());
        System.out.println(response);
    }

    @Test
    public void testApiCallByCodeDepartmentExpectsResponseNotEmpty() {
        JsonNode communes = api.getCommunes(APIField.Commune.CODE_DEPARTEMENT, "60", 100);
        assertNotEquals(0, communes.size());
    }

    @Test
    public void testFromBackendByName(){
        List<Commune> communes = backend.searchCommuneFromAPIByNom("Beauvais");
        assertEquals("Beauvais", communes.getFirst().getNom());
    }


    @Test
    public void testGetEtablissementsCodeDepartement2Numbers(){
        JsonNode response = api.getEtablissements(APIField.Etablissement.CODE_DEPARTEMENT, "60", 100);
        assertNotEquals(0, response.size());
    }

    @Test
    public void testGetEtablissementsCodeDepartement3Numbers(){
        JsonNode response = api.getEtablissements(APIField.Etablissement.CODE_DEPARTEMENT, "60", 100);
        assertEquals(80, response.size());
        System.out.println(response);
    }

    @Test
    public void testGetEtablissementsCodeCommune(){
        List<Etablissement> etab = backend.searchEtablissementFromAPIByCodePostal("60000");
        assertEquals("Beauvais", etab.getFirst().getNomCommune());
    }
}
