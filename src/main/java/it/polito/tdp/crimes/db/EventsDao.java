package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> getCategorie() {
		final String sql = "SELECT DISTINCT(offense_category_id) AS of " + 
				"FROM events";
		List<String> categorie = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				categorie.add(res.getString("of"));
			}
			conn.close();
			return categorie;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}

	public List<Integer> getAnni() {
		final String sql = "SELECT DISTINCT(YEAR(reported_date)) AS d " + 
				"FROM events " + 
				"ORDER BY YEAR(reported_date) ASC";
		List<Integer> anni = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				anni.add(res.getInt("d"));
			}
			conn.close();
			return anni;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}

	public List<String> getVertici(String categoria, Integer anno) {
		final String sql = "SELECT DISTINCT(offense_type_id) AS of " + 
				"FROM events " + 
				"WHERE YEAR(reported_date)=? " + 
				"AND offense_category_id=?";
		List<String> categorie = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, categoria);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				categorie.add(res.getString("of"));
			}
			conn.close();
			return categorie;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}

	public List<Adiacenza> getAdiacenze(String categoria, Integer anno) {
		final String sql="SELECT e1.offense_type_id AS of1,e2.offense_type_id AS of2, COUNT(DISTINCT(e1.district_id)) AS peso " + 
				"FROM events AS e1, events AS e2 " + 
				"WHERE e1.offense_type_id<>e2.offense_type_id " + 
				"AND e1.district_id=e2.district_id " + 
				"AND YEAR(e1.reported_date)=? " + 
				"AND YEAR(e2.reported_date)=? " + 
				"AND e1.offense_category_id=? " + 
				"AND e2.offense_category_id=? " + 
				"GROUP BY e1.offense_type_id,e2.offense_type_id";
		List<Adiacenza> adiacenze = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setString(3, categoria);
			st.setString(4, categoria);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				Adiacenza a = new Adiacenza(res.getString("of1"),res.getString("of2"),res.getInt("peso"));
				if(a.getPeso()>0) {
					adiacenze.add(a);
				}
			}
			conn.close();
			return adiacenze;
		}catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
		
	}

}
