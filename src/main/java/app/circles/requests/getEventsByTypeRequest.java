package app.circles.requests;

import java.util.ArrayList;
import java.util.List;

public class getEventsByTypeRequest {
    List<String> typesNames = new ArrayList<>();

    public List<String> getTypesNames() {
        return typesNames;
    }
}
