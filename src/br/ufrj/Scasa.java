package br.ufrj;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map.Entry;
/**
 * Servlet implementation class Scasa
 */
@WebServlet("/Scasa")
public class Scasa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Scasa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dispatcher dis = new Dispatcher();
        String classe = "br.ufrj." + request.getParameter("classe");
        String metodo = null;
        Map<String, String> parametros = new TreeMap<>();
        String pagina = null;       
        for(Entry<String, String[]> x : request.getParameterMap().entrySet()) {
        	if(x.getKey().equals("metodo")) 
        		metodo = x.getValue()[0].toLowerCase();
        	else if (! x.getKey().equals("classe"))  	
        		parametros.put(x.getKey(), x.getValue()[0]);  
       	}       
 
        try {
            Object o = dis.cria( classe );
            Method m = dis.buscaMetodo( o.getClass(), metodo );
            
            if (m.getParameterCount() == 0) {
            	Map<String, ArrayList<Object>> resultado = (Map<String, ArrayList<Object>>) m.invoke(o);
            	for(Entry<String, ArrayList<Object>> x : resultado.entrySet()) {
            		pagina = x.getKey();
            		request.setAttribute("resultado", x.getValue());
            	}	            	 
            }
            
            else {
            	Object form = dis.instanciaForm( m ); 
            	dis.preencheForm( form, parametros );                       
            	ArrayList<String> resultado = (ArrayList<String>) m.invoke(o, form);
            	pagina = resultado.get(0);
            	resultado.remove(0);            	
            	request.setAttribute("resultado", resultado);
            }	
            request.getRequestDispatcher( pagina ).forward( request, response );                              
        } catch( Exception e ) {
        	request.setAttribute("resultado", e.getMessage());
        	request.getRequestDispatcher( request.getParameter("classe").substring(0,1).toLowerCase().concat(request.getParameter("classe").substring(1) ) + ".jsp").forward( request, response );   
        }
	}

}
