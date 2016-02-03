/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.udl.eps.butterp.data;

import static cat.udl.eps.butterp.data.ListOps.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author biancapadurean
 */
public class ListOpsTest {
    
    public ListOpsTest() {
    }

    @Test 
    public void test_list_varargs_nil_arg(){
       SExpression s =  Symbol.NIL;
       ConsCell expected = new ConsCell(Symbol.NIL, Symbol.NIL);
       assertEquals(expected, list(s));
    }
    
    @Test
    public void test_list_varargs_one_arg(){
        SExpression expected = new ConsCell(new Integer(0), new ConsCell(new Integer(1), Symbol.NIL));
        assertEquals(expected, list(new Integer(0), new Integer(1)));
    }
}
