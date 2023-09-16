package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;


import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	
	private GOsalesDAO dao;
	private Graph<Retailers,DefaultWeightedEdge> graph;
	List<Retailers> allNodes;
	
	public Model() {
		this.dao=new GOsalesDAO();
		this.allNodes=new ArrayList<>();
	}
	
	public void buildGraph(String country,Integer y,Integer m) {
		this.allNodes.clear();
		this.loadNodes(country);
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph,this.allNodes);
		for(Retailers r1:this.graph.vertexSet()) {
			for(Retailers r2:this.graph.vertexSet()) {
				if(r1.getCode()!=r2.getCode() && this.graph.getEdge(r1,r2)==null && this.getWeight(country,y,r1.getCode(),r2.getCode())>=m) {
					Graphs.addEdge(this.graph,r1,r2,this.getWeight(country,y,r1.getCode(),r2.getCode()));
				}
			}
		}
	}
	
	private Integer getWeight(String c,Integer y,Integer id1,Integer id2) {
		Set<Integer> peso=new HashSet<>();
		for(int i1:this.dao.getProductOfIn(c,y,id1)) {
			for(int i2:this.dao.getProductOfIn(c,y,id2)) {
				if(i1==i2) {
					peso.add(i1);
				}
			}
		}
		return peso.size();
	}
	
	private void loadNodes(String country) {
		if(this.allNodes.isEmpty())
			this.allNodes.addAll(this.dao.getRetailersOf(country));
	}
	
	public List<Integer> getAnni(){
		List<Integer> anni=new ArrayList<>();
		anni.add(2015);
		anni.add(2016);
		anni.add(2017);
		anni.add(2018);
		return anni;
	}
	
	public List<String> getNazioni(){
		List<String> nazioni=new ArrayList<>();
		nazioni.addAll(this.dao.getAllCountry());
		return nazioni;
	}

	public Integer getVsize() {
		// TODO Auto-generated method stub
		return this.graph.vertexSet().size();
	}
	public Integer getEsize() {
		// TODO Auto-generated method stub
		return this.graph.edgeSet().size();
	}
	public List<Retailers> getVenditori(){
		List<Retailers> ordinati=new ArrayList<>(this.graph.vertexSet());
		Collections.sort(ordinati);
		return ordinati;
	}
	
	public List<Archi> getArchi(){
		List<Archi> archi=new ArrayList<>();
		Set<DefaultWeightedEdge> e=new HashSet<>();
		for(Retailers r1:this.graph.vertexSet()) {
			for(Retailers r2:this.graph.vertexSet()) {
				if(this.graph.getEdge(r1, r2)!=null && !e.contains(this.graph.getEdge(r1, r2))) {
					e.add(this.graph.getEdge(r1, r2));
					archi.add(new Archi(r1.getName(),r2.getName(),(int)this.graph.getEdgeWeight(this.graph.getEdge(r1, r2))));
				}
			}
		}
		Collections.sort(archi);
		return archi;
	}
	
	public ComponenteConnessa getComponenteConnessa(Retailers r){
		DepthFirstIterator<Retailers,DefaultWeightedEdge> iterator=new DepthFirstIterator<>(this.graph,r);
    	List<Retailers> compConnessa=new ArrayList<>();
    	int peso=0;
    	Set<DefaultWeightedEdge> e=new HashSet<>();
    	while(iterator.hasNext()) {
    		compConnessa.add(iterator.next());
    	}
    	for(Retailers r1:compConnessa) {
    		for(Retailers r2:compConnessa) {
    			if(this.graph.getEdge(r1, r2)!=null && !e.contains(this.graph.getEdge(r1, r2))) {
    			    e.add(this.graph.getEdge(r1, r2));
    			    peso+=this.graph.getEdgeWeight(this.graph.getEdge(r1, r2));
    			}
        	}
    	}
    	ComponenteConnessa cc=new ComponenteConnessa(compConnessa.size(),peso);
    	return cc;
	}
	
}
