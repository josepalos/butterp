/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cat.udl.eps.butterp.data.ConsCell;
import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.Function;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.main.Primitives;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.NestedMap;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Usuari
 */
public class AddPrimitiveTest {
    private static Environment env;
    private Function add;
    
    @BeforeClass
    public static void bindPrimitives(){
	env = new NestedMap();
	Primitives.loadPrimitives(env);
    }
    
    @Before
    public void setupAddFunction(){
	add = (Function) env.find(new Symbol("add"));
    }
    
    @Test
    public void add_with_no_args_return_0(){
	assertEquals(new Integer(0), add.apply(Symbol.NIL, env));
    }
    
    @Test
    public void add_with_args_1_2_3_return_6(){
	//TODO -- change args initialization to ListOps.list(...)
	SExpression args = new ConsCell(
				    new Integer(1),
				    new ConsCell(
				        new Integer(2),
				        new ConsCell(
						new Integer(3),
						Symbol.NIL
					    )
					)
				);
	
	assertEquals(new Integer(6), add.apply(args, env));
    }
    
    @Test (expected = EvaluationError.class)
    public void add_with_arguments_no_integers_throws_EvaluationError(){
	SExpression args = add;
	add.apply(args, env);
    }
}
