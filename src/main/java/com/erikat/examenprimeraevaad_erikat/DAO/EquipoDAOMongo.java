package com.erikat.examenprimeraevaad_erikat.DAO;

import com.erikat.examenprimeraevaad_erikat.Model.Equipo;
import com.erikat.examenprimeraevaad_erikat.Utils.MongoDBUtils;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class EquipoDAOMongo implements DAO<Equipo, Integer>{
    MongoCollection<Document> collection;
    Gson gson;
    public EquipoDAOMongo(){
        collection = MongoDBUtils.getCollection("Equipos");
        gson = new Gson();
    }
    @Override
    public boolean insertar(Equipo obj) {
        boolean terminadoCorrectamente = false;
        try{
            collection.insertOne(Document.parse(gson.toJson(obj)));
            terminadoCorrectamente = true;
        }catch (Exception e){
            System.out.println("Error de MongoDB: " + e.getMessage());
        }
        return terminadoCorrectamente;
    }

    @Override
    public boolean editar(Equipo obj) {
        boolean terminadoCorrectamente = false;
        try{
            Document actualizacion = new Document("$set", new Document("nombreEquipo", obj.getNombreEquipo())
                                                                .append("patrocinador", obj.getPatrocinador())
                                                                .append("categoria", obj.getCategoria())
                                                                .append("sancionado", obj.isSancionado()));
            terminadoCorrectamente = (collection.updateOne(new Document("idEquipo", obj.getIdEquipo()), actualizacion).wasAcknowledged());
            //La función wasAcknlowledged lo que hace es comprobar si se ha actualizado correctamente
        }catch (Exception e){
            System.out.println("Error de MongoDB: " + e.getMessage());
        }
        return terminadoCorrectamente;
    }

    @Override
    public boolean eliminar(Equipo obj) {
        boolean terminadoCorrectamente = false;
        try{
            collection.deleteOne(Filters.eq("idEquipo", obj.getIdEquipo()));
            terminadoCorrectamente = true;
        }catch (Exception e){
            System.out.println("Error de MongoDB: " + e.getMessage());
        }
        return terminadoCorrectamente;
    }

    @Override
    public Equipo buscar(Integer valor) {
        Equipo equipo = null;
        try{
            Document equipoDoc = collection.find(Filters.eq("idEquipo", valor)).first();
            if (equipoDoc!=null) {
                equipo = gson.fromJson(equipoDoc.toJson(), Equipo.class);
            }
        }catch (Exception e){
            System.out.println("Error de MongoDB: " + e.getMessage());
        }
        return equipo;
    }

    @Override
    public List<Equipo> listar() {
        List<Equipo> equipos = new ArrayList<>();
        boolean terminadoCorrectamente = false;
        try(MongoCursor<Document> cursorEquipos = collection.find().iterator()){
            while (cursorEquipos.hasNext()){
                Document equipo = cursorEquipos.next();
                equipos.add(gson.fromJson(equipo.toJson(), Equipo.class));
            }
        }catch (Exception e){
            System.out.println("Error de MongoDB: " + e.getMessage());
        }
        return equipos;
    }
}
