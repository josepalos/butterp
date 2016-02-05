package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.ConsCell;
import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.Function;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.Special;
import cat.udl.eps.butterp.environment.Environment;

public class Primitives {

    public static void loadPrimitives(Environment env) {
        env.bindGlobal(Symbol.NIL, Symbol.NIL);
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);

        /*

        An example of a predefined Function:

        env.bindGlobal(new Symbol("function"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                throw new UnsupportedOperationException("not implemented yet");
            }
        });

        */

        /*

        An example of a predefined Special:

        env.bindGlobal(new Symbol("special"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                throw new UnsupportedOperationException("not implemented yet");
            }
        });

        */
	
	Function add = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		try{
		    if(evargs.equals(Symbol.NIL)) return new Integer(0);

		    ConsCell argsCC = (ConsCell) evargs;
		    Integer i = (Integer) argsCC.car.eval(env);
		    Integer rec = (Integer) this.apply(argsCC.cdr, env);
		    return new Integer(i.value + rec.value);
		}catch(ClassCastException e){
		    throw new EvaluationError("ADD should get only integer arguments");
		}
	    }
	};
	
	Function mult = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		try{
		    if(evargs.equals(Symbol.NIL)) return new Integer(1);

		    ConsCell argsCC = (ConsCell) evargs;
		    Integer i = (Integer) argsCC.car.eval(env);
		    Integer rec = (Integer) this.apply(argsCC.cdr, env);
		    return new Integer(i.value * rec.value);
		}catch(ClassCastException e){
		    throw new EvaluationError("MULT should get only integer arguments");
		}
	    }
	};
	
	Special define = new Special() {
	    @Override
	    public SExpression applySpecial(SExpression args, Environment env) {
		if( !(args instanceof ConsCell) || ListOps.length((ConsCell)args) != 2){
		    throw new EvaluationError("DEFINE should have two arguments");
		}
		ConsCell c = (ConsCell) args;
		Symbol alias;
		
		try{
		    alias = (Symbol) c.car;
		}catch(ClassCastException e){
		    throw new EvaluationError("DEFINE's first argument should be a symbol");
		}
		
	        SExpression val = c.cdr.eval(env);
		
		env.bindGlobal(alias, val);
		
		return Symbol.NIL;
	    }
	};

	
	
	env.bindGlobal(new Symbol("add"), add);
	env.bindGlobal(new Symbol("mult"), mult);
	env.bindGlobal(new Symbol("define"), define);
    }
}
