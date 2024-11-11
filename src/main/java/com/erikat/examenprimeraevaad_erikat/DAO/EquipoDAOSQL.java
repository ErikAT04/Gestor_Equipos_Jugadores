package com.erikat.examenprimeraevaad_erikat.DAO;

import com.erikat.examenprimeraevaad_erikat.Model.Equipo;
import com.erikat.examenprimeraevaad_erikat.Utils.MySQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAOSQL implements DAO<Equipo, Integer>{
    Connection con;
    public EquipoDAOSQL(){
        con = MySQLUtils.conectar();
    }
    @Override
    public boolean insertar(Equipo obj) {
        String sql = "INSERT INTO EQUIPOS(idEquipo, nombreEquipo, patrocinador, categoria, sancionado) VALUES (?, ?, ?, ?, ?)";
        boolean terminadoCorrectamente = false;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, obj.getIdEquipo());
            ps.setString(2, obj.getNombreEquipo());
            ps.setString(3, obj.getPatrocinador());
            ps.setString(4, obj.getCategoria());
            ps.setBoolean(5, obj.isSancionado());

            int filasActualizadas = ps.executeUpdate();
            terminadoCorrectamente = filasActualizadas==1;
        }catch (SQLException e){
            System.out.println("Error de MySQL:" + e.getMessage());
        }
        return terminadoCorrectamente;
    }

    @Override
    public boolean editar(Equipo obj) {
        String sql = "UPDATE EQUIPOS SET nombreEquipo = ?, patrocinador =?, categoria = ?, sancionado = ? where idEquipo = ?";
        boolean terminadoCorrectamente = false;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(5, obj.getIdEquipo());
            ps.setString(1, obj.getNombreEquipo());
            ps.setString(2, obj.getPatrocinador());
            ps.setString(3, obj.getCategoria());
            ps.setBoolean(4, obj.isSancionado());

            int filasActualizadas = ps.executeUpdate();
            terminadoCorrectamente = filasActualizadas==1;

        }catch (SQLException e){
            System.out.println("Error de MySQL:" + e.getMessage());
        }
        return terminadoCorrectamente;
    }

    @Override
    public boolean eliminar(Equipo obj) {
        String sql = "DELETE FROM EQUIPOS WHERE idEquipo = ?";
        boolean terminadoCorrectamente = false;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, obj.getIdEquipo());

            int filasActualizadas = ps.executeUpdate();

            terminadoCorrectamente = filasActualizadas==1;
        }catch (SQLException e){
            System.out.println("Error de MySQL:" + e.getMessage());
        }
        return terminadoCorrectamente;
    }

    @Override
    public Equipo buscar(Integer valor) {
        Equipo equipo = null;
        String sql = "SELECT * FROM EQUIPOS WHERE idEquipo = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, valor);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                equipo = new Equipo(
                        valor,
                        rs.getString("nombreEquipo"),
                        rs.getString("patrocinador"),
                        rs.getString("categoria"),
                        rs.getBoolean("sancionado")
                );
            }
        }catch (SQLException e){
            System.out.println("Error de MySQL:" + e.getMessage());
        }
        return equipo;
    }

    @Override
    public List<Equipo> listar() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM EQUIPOS";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                equipos.add(new Equipo(
                        rs.getInt("idEquipo"),
                        rs.getString("nombreEquipo"),
                        rs.getString("patrocinador"),
                        rs.getString("categoria"),
                        rs.getBoolean("sancionado")
                ));
            }
        }catch (SQLException e){
            System.out.println("Error de MySQL:" + e.getMessage());
        }
        return equipos;
    }

}
