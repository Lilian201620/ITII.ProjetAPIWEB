package junit.domain.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.enums.APIField;
import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.models.objets.Etablissement;
import fr.itii.apiweb.domain.tools.JSONSerializer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONSerializerTest {

    APICaller api = new APICaller();

    @Test
    public void toListCommuneSerializerTest() throws JsonProcessingException {
        JSONSerializer js = new JSONSerializer();
        JsonNode json = api.getCommunes(APIField.Commune.NOM, "Beauvais",10);

        List<Commune> communes = js.toCommunes(json);
        assertEquals(communes.getFirst().getNom(),"Beauvais");
    }

    @Test
    public void toListEtablissementSerializerTest() throws JsonProcessingException {
        JSONSerializer js = new JSONSerializer();
        JsonNode json = api.getEtablissements(APIField.Etablissement.CODE_POSTAL,"60000",10);

        List<Etablissement> etab = js.toEtablissements(json);
        assertEquals(etab.getFirst().getNomCommune(),"Beauvais");
    }
}
