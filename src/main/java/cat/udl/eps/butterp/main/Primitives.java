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

	Function add = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		try {
		    if (evargs.equals(Symbol.NIL)) {
			return new Integer(0);
		    }

		    ConsCell argsCC = (ConsCell) evargs;
		    Integer i = (Integer) argsCC.car.eval(env);
		    Integer rec = (Integer) this.apply(argsCC.cdr, env);
		    return new Integer(i.value + rec.value);
		} catch (ClassCastException e) {
		    throw new EvaluationError("ADD should get only integer arguments");
		}
	    }
	};

	Function mult = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		try {
		    if (evargs.equals(Symbol.NIL)) {
			return new Integer(1);
		    }

		    ConsCell argsCC = (ConsCell) evargs;
		    Integer i = (Integer) argsCC.car.eval(env);
		    Integer rec = (Integer) this.apply(argsCC.cdr, env);
		    return new Integer(i.value * rec.value);
		} catch (ClassCastException e) {
		    throw new EvaluationError("MULT should get only integer arguments");
		}
	    }
	};

	Function cons = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if (!(evargs instanceof ConsCell) || ListOps.length(evargs) != 2) {
		    throw new EvaluationError("CONS needs two arguments.");
		}
		SExpression second = ListOps.nth(evargs, 1);
		if( second instanceof ConsCell || second.equals(Symbol.NIL)){
		    return new ConsCell( ListOps.car(evargs), second.eval(env));
		}else{
		    throw new EvaluationError("CONS second argument should be list.");
		}
	    }
	};

	Function car = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if( ListOps.length(evargs) == 1){
		    SExpression list = ListOps.car(evargs).eval(env);
		    if(! (list instanceof ConsCell) ){
			throw new EvaluationError("CAR needs a list argument");
		    }
		    return ListOps.car(list);
		}else{
		    throw new EvaluationError("CAR needs an argument.");
		}
	    }
	};
	
	Function cdr;
	Function list;
	Function eq;
	Function eval;
	Function apply;

	Special define = new Special() {
	    @Override
	    public SExpression applySpecial(SExpression args, Environment env) {
		if (!(args instanceof ConsCell) || ListOps.length((ConsCell) args) != 2) {
		    throw new EvaluationError("DEFINE should have two arguments");
		}
		ConsCell c = (ConsCell) args;
		Symbol alias;

		try {
		    alias = (Symbol) c.car;
		} catch (ClassCastException e) {
		    throw new EvaluationError("DEFINE's first argument should be a symbol");
		}

		SExpression val = c.cdr.eval(env);

		env.bindGlobal(alias, val);

		return Symbol.NIL;
	    }
	};

	Special quote = new Special() {
	    @Override
	    public SExpression applySpecial(SExpression args, Environment env) {
		if( ListOps.length(args) == 1 )
		    return ListOps.car(args);
		else
		    throw new EvaluationError("QUOTE needs an argument.");
	    }
	};
	Special ifsp;
	Special lambda;

	bindGlobal(env, "add", add);
	bindGlobal(env, "mult", mult);
	bindGlobal(env, "define", define);
	bindGlobal(env, "quote", quote);
	bindGlobal(env, "cons", cons);
	bindGlobal(env, "car", car);
//	bindGlobal(env, "cdr", cdr);
//	bindGlobal(env, "list", list);
//	bindGlobal(env, "eq", eq);
//	bindGlobal(env, "if", ifsp);
//	bindGlobal(env, "lambda", lambda);
//	bindGlobal(env, "eval", eval);
//	bindGlobal(env, "apply", apply);

    }

    private static void bindGlobal(Environment env, String name, SExpression exp) {
	env.bindGlobal(new Symbol(name), exp);
    }
}
