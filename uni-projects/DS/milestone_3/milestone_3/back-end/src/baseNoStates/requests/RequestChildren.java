package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.Area;
import baseNoStates.DirectoryAreas;
import baseNoStates.Door;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class RequestChildren implements Request {
    private final String areaId;
    private JSONObject jsonTree; // árbol de 1 nivel: root y sus hijos

    public RequestChildren(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }

    @Override
    public JSONObject answerToJson() {
        return jsonTree;
    }

    @Override
    public String toString() {
        return "RequestChildren{areaId=" + areaId + "}";
    }

    public void process() {
        Area area = DirectoryAreas.getInstance().findAreaById(areaId);
        jsonTree = area.toJson(1); // profundidad 1: solo hijos directos
    }
}
