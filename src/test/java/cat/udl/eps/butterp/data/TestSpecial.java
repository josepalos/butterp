/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.environment.NestedMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author josep
 */
public class TestSpecial {
    
    @Test
    public void special_returns_itself_when_eval(){
        Special s = new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Environment env = new NestedMap();
        
        assertEquals(s, s.eval(env));
    }
}
