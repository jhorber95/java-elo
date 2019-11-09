package com.software.estudialo.util;

import com.software.estudialo.entities.DataTableJson;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class SimilarResponses {

    public static ResponseEntity<Respuesta> getRespuestaResponseEntity() {
        Respuesta respuesta = new Respuesta();
        respuesta.setExito(true);
        respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
        respuesta.setBody(null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    public static ResponseEntity<RespuestaGeneral> getRespuestaGeneralResponseEntity(List<?> data) {
        RespuestaGeneral rg = new RespuestaGeneral();
        rg.setExito(true);
        rg.setCodigo(200);
        rg.setData(data);
        return new ResponseEntity<>(rg, HttpStatus.OK);
    }

    public static void setDataTableParameters(DataTableJson dataTableJson) {
        String search = dataTableJson.getSearch().getValue();
        int start = dataTableJson.getStart();
        int length = dataTableJson.getLength();
        int draw = dataTableJson.getDraw();
        int posicion = dataTableJson.getOrder().get(0).getPosicion();
    }
}
