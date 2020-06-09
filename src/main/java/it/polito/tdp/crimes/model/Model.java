package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<String,DefaultWeightedEdge> graph;
	private double bestPeso;
	private List<String> best;
	
	public Model() {
		this.dao = new EventsDao();
	}

	public List<String> getCategorie() {		
		return this.dao.getCategorie();
	}

	public List<Integer> getAnni() {
		return this.dao.getAnni();
	}

	public void createGraph(String categoria, Integer anno) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.dao.getVertici(categoria,anno));
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(categoria, anno);
		for(Adiacenza a : adiacenze) {
			if(this.graph.getEdge(a.getOf1(), a.getOf2()) == null) {
				Graphs.addEdgeWithVertices(this.graph, a.getOf1(), a.getOf2(),a.getPeso());
			}
			
		}
		
	}

	public Integer nVertici() {
		return this.graph.vertexSet().size();
	}
	public Integer nArchi() {
		return this.graph.edgeSet().size();
	}

	public List<Adiacenza> getListaPesiMassimi() {
		double peso = 0.0;
		for(DefaultWeightedEdge e : this.graph.edgeSet()) {
			if(this.graph.getEdgeWeight(e)>peso) {
				peso = this.graph.getEdgeWeight(e);
			}
		}
		List<Adiacenza> lista = new ArrayList<>();
		for(DefaultWeightedEdge e : this.graph.edgeSet()) {
			if(this.graph.getEdgeWeight(e) == peso) {
				lista.add(new Adiacenza(this.graph.getEdgeSource(e),this.graph.getEdgeTarget(e),(int)this.graph.getEdgeWeight(e)));
			}
		}
		return lista;
	}

	public List<String> getCammino(Adiacenza a) {
		this.bestPeso = Double.MAX_VALUE;
		this.best = new ArrayList<>();
		List<String> parziale = new ArrayList<>();
		parziale.add(a.getOf1());
		cerca(parziale,1,a.getOf2());
		return best;
	}

	private void cerca(List<String> parziale, int livello, String finale) {
		//condizione terminale
		if(livello == this.graph.vertexSet().size()
				&& parziale.get(parziale.size()-1).compareTo(finale)==0) {
			if(calcolaPeso(parziale)<this.bestPeso) {
				this.bestPeso = calcolaPeso(parziale);
				this.best = new ArrayList<>(parziale);
				return;
			}
			return;
		}
		
		for(String s : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cerca(parziale,livello+1,finale);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
		
	}

	private double calcolaPeso(List<String> parziale) {
		double peso = 0;
		for(int i = 1;i<parziale.size();i++) {
			peso+=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i)));
		}
		return peso;
	}
	
}
