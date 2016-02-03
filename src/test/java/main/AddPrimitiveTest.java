/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cat.udl.eps.butterp.data.Function;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.main.Primitives;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.NestedMap;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Usuari
 */
public class AddPrimitiveTest {
    private static Environment env;
    
    @BeforeClass
    public static void bindPrimitives(){
	env = new NestedMap();
	Primitives.loadPrimitives(env);
    }
    
    @Test
    public void add_with_no_args_return_0(){
	Function add = (Function) env.find(new Symbol("add"));
	assertEquals(new Integer(0), add.apply(Symbol.NIL, env));
    }
    
    @Test
    public void add_with_args_1_2_3_return_6(){
	Function add = (Function) env.find(new Symbol("add"));
	SExpression args = ListOps.list(new Integer(1), new Integer(2), new Integer(3));
	assertEquals(new Integer(6), add.apply(ListOps.list(args), env));
    }
}
