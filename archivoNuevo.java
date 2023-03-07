
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lucas
 */
public class AlgoritmoAEstrela {

    private Map<Integer, NoSolucao> estadosAbertos;
    private Map<Integer, NoSolucao> estadosFechados;
    private Map<Integer, NoSolucao> estadosFinais;
    private ArrayList<NoSolucao> sucessores;
            
    public AlgoritmoAEstrela(NoSolucao noRaiz) {
        estadosAbertos = new HashMap<>();
        estadosFechados = new HashMap<>();
        estadosFinais = new HashMap<>();
        estadosAbertos.put(noRaiz.getTabuleiro().getHashCode(), noRaiz);

        NoSolucao noSolucao;
        noSolucao = new NoSolucao(Tabuleiro.solucao);
        estadosFinais.put(noSolucao.getTabuleiro().getHashCode(), noSolucao);

//        estadosAbertos.remove(noRaiz.getTabuleiro().getHashCode()); Como retirar
    }
    
    public int encontreASolucao(){
        NoSolucao noSolucao;
        noSolucao = null;
        
        while (!estadosAbertos.isEmpty()) {
            NoSolucao no;
            no = this.oMenorNosAbertos();
            
            estadosAbertos.remove(no.getTabuleiro().getHashCode());
            estadosFechados.put(no.getTabuleiro().getHashCode(), no);
            
            if (estadosFinais.containsKey(no.getTabuleiro().getHashCode())){
                return no.getQuantidadeDeMovimentos();
            }
            
            
            
            
        }
        
        if (noSolucao == null)
            return -1;
        
        return noSolucao.getQuantidadeDeMovimentos();
    }
    
    public NoSolucao oMenorNosAbertos(){
        NoSolucao no;
        
        no = null;
        
        for (Map.Entry<Integer, NoSolucao> noCompar : estadosAbertos.entrySet()) {            
            if (estadosFinais.containsKey(noCompar.getKey())){
                return noCompar.getValue();
            }
            if (no == null || noCompar.getValue().custo() < no.custo())
                no = noCompar.getValue();            
        }
        return no;
    }

    private void geradorDeSucessores(NoSolucao no) {

        Celula celulaDeControle = no.getTabuleiro().getCelulaDeControle();
        if (celulaDeControle.podeSubir()) {
            NoSolucao novoNo = new NoSolucao(no.getTabuleiro(), no.getQuantidadeDeMovimentos() + 1, no);
            novoNo.getTabuleiro().movaPeca(novoNo.getTabuleiro().getCelula((byte) (novoNo.getTabuleiro().getCelulaDeControle().getPosicaoX() - 1), novoNo.getTabuleiro().getCelulaDeControle().getPosicaoY()));
            no.addFilho(novoNo);

        }

        if (celulaDeControle.podeDescer()) {
            NoSolucao novoNo = new NoSolucao(no.getTabuleiro(), no.getQuantidadeDeMovimentos() + 1, no);
            novoNo.getTabuleiro().movaPeca(novoNo.getTabuleiro().getCelula((byte) (novoNo.getTabuleiro().getCelulaDeControle().getPosicaoX() + 1), novoNo.getTabuleiro().getCelulaDeControle().getPosicaoY()));
            no.addFilho(novoNo);

        }

        if (celulaDeControle.podeEsquerda()) {
            NoSolucao novoNo = new NoSolucao(no.getTabuleiro(), no.getQuantidadeDeMovimentos() + 1, no);
            novoNo.getTabuleiro().movaPeca(novoNo.getTabuleiro().getCelula(novoNo.getTabuleiro().getCelulaDeControle().getPosicaoX(), (byte) (novoNo.getTabuleiro().getCelulaDeControle().getPosicaoY() - 1)));
            no.addFilho(novoNo);

        }

        if (celulaDeControle.podeDireita()) {
            NoSolucao novoNo = new NoSolucao(no.getTabuleiro(), no.getQuantidadeDeMovimentos() + 1, no);
            novoNo.getTabuleiro().movaPeca(novoNo.getTabuleiro().getCelula(novoNo.getTabuleiro().getCelulaDeControle().getPosicaoX(), (byte) (novoNo.getTabuleiro().getCelulaDeControle().getPosicaoY() + 1)));
            no.addFilho(novoNo);

        }

    }

}

/*package practica2021a;

import practica2021a.DataStructures.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @authos L. Mandow
 * @date   11/marzo/2021
 * Agente resolutor de problemas usando A*.
 */

public class AgenteA{ 
	
	/**
	 * Resuelve un problema dado el estado inicial y el objetivo.
	 * 
	 */

	public List<Estado> resuelve(Estado salida, Estado objetivo){

		Arbol<Estado> arbol = new ArbolHashMap<>();
		Abiertos<Estado> abiertos = new AbiertosList<>();

		Estado e = salida;
		Nodo<Estado> n = new Nodo<>(e, 0, null);
		arbol.put(n);
		abiertos.offer(e.h(objetivo), e);
		
		while (!abiertos.isEmpty()){
			e = abiertos.poll();
			n = arbol.get(e);
			
			if(e.equals(objetivo)){     //si es el objetivo, terminamos
				return recuperaSolucion(n, arbol);
			} else { //generamos los sucesores no repetidos y los a�adimos al �rbol y a abiertos
				int ge = n.getG();
				for(Estado e2: e.calculaSucesores()){
					int nuevoCoste = ge + e.coste(e2);
					
					if (!arbol.containsKey(e2)){	//nuevon nodo: simplemente a�adimos e2 al �rboly a abiertos
						arbol.put(new Nodo<>(e2, nuevoCoste, n));
						abiertos.offer(nuevoCoste + e2.h(objetivo),  e2);
					} else {
						Nodo<Estado> n2 = arbol.get(e2);
						int antiguoCoste = n2.getG();
						if (nuevoCoste < antiguoCoste) { // mejor camino: redirigimos el puntero de e2 y actualizamos abiertos
							n2.setPadre(n);
							n2.setG(nuevoCoste);
							
							abiertos.remove(e2);
							abiertos.offer(nuevoCoste + e2.h(objetivo), e2);
						}
					}
				
				}//for e2
			}//if-else
		}//while
		
		return null;		 //la búsqueda termina con fracaso
	}
	
	ArrayList<Estado> recuperaSolucion(Nodo<Estado> n, Arbol<Estado> arbol){
		ArrayList<Estado> solucion = new ArrayList<Estado>();
		
		Nodo<Estado> n2 = n;
		
		while(n2  != null){
			solucion.add(n2.getEstado());
			n2 = n2.getPadre();
		}//while
		
		return solucion;

	}
} */