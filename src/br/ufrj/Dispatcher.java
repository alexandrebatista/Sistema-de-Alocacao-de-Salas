package br.ufrj;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Dispatcher {
	
    public Object cria( String nomeClasse ) throws Exception {
        Class<?> c = Class.forName( nomeClasse );
        Constructor<?> cs = c.getConstructor();
        return cs.newInstance();
    }
    
    public Method buscaMetodo( Class<?> classe, String nomeMetodo ) {
    	for(Method x : classe.getMethods()) {
    		if (x.getName().equals(nomeMetodo))
    			return x;
    	}
        return null;
    }
    
    public Object instanciaForm( Method m ) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Class<?>[] listaParametros = m.getParameterTypes();
    	Class<?> classe = listaParametros[0];
    	
    	return classe.getConstructor().newInstance();        
    }
    
    public void preencheForm(Object form, Map<String, String> parametros) throws Exception {
    	Map<String, Method> setters = buscaSetters(form.getClass());    	
    	
    	for(Entry<String, String> x : parametros.entrySet()) {
    		Method setter = setters.get(x.getKey().toLowerCase());
    		for(Field f : form.getClass().getDeclaredFields()) {
    			
    			if(f.getName().equals(x.getKey().toLowerCase()) && x.getValue().equals("") && f.getDeclaredAnnotation(Obrigatorio.class).value()) {
    				throw new Exception(setter.getName().substring(3) + " obrigatório(a)!");
    			}
    			
    		}
	   		
    		if(setter != null) { 
       			
    			if(setter.isAnnotationPresent(Consultavel.class) && x.getValue() != "") {   				
    				Object classeForm = cria("br.ufrj.Form" +  x.getKey().substring(0,1).toUpperCase().concat(x.getKey().substring(1)));
    				Object classeCadastro = cria("br.ufrj.Cadastro" +  x.getKey().substring(0,1).toUpperCase().concat(x.getKey().substring(1))); 
    				Method m = buscaMetodo(classeCadastro.getClass(), "consultar");
    				Object form2 = instanciaForm( m );
    				Map<String, Method> settersObjeto = buscaSetters(classeForm.getClass());
    				Map<String, String> parametros2 = new TreeMap<>();
    				for(Entry<String, Method> y : settersObjeto.entrySet()) 
    					parametros2.put(y.getKey(), "");
    				for(Entry<String, Method> y : settersObjeto.entrySet()) {
						parametros2.put(y.getKey(), "");
    					for (Field f : classeForm.getClass().getDeclaredFields()) {
    						if (f.getName().equals(y.getKey()) && f.getDeclaredAnnotation(Obrigatorio.class).value()) {   														   							
    							parametros2.put(y.getKey(), x.getValue());   							     							
    							preencheForm( form2, parametros2 );  
    							m.invoke(classeCadastro, form2);
    							setter.invoke(form, form2);
    							
    						}
    					}
    				}    				
    			}
    			
    			else if(x.getValue() != "") 
    				setter.invoke(form, x.getValue());
    	   		
    		}	
    	}   	
    }
    
	private Map<String, Method> buscaSetters(Class<?> classe) {
		Map<String, Method> setters = new HashMap<String, Method>();
		for(Method x : classe.getMethods()) {
			if(x.getName().toLowerCase().startsWith("set"))
				setters.put(x.getName().toLowerCase().substring(3), x);
		}
		return setters;
	}	
}
