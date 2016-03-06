package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.ConsCell;
import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.Function;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.Lambda;
import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.Special;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.environment.NestedMap;
import cat.udl.eps.butterp.reader.Parser;
import java.util.LinkedList;
import java.util.List;

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
		    Integer i = (Integer) argsCC.car;
		    Integer recursive_add = (Integer) this.apply(argsCC.cdr, env);
		    return new Integer(i.value + recursive_add.value);
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
		    Integer i = (Integer) argsCC.car;
		    Integer recursive_mult = (Integer) this.apply(argsCC.cdr, env);
		    return new Integer(i.value * recursive_mult.value);
		} catch (ClassCastException e) {
		    throw new EvaluationError("MULT should get only integer arguments");
		}
	    }
	};

	Function cons = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if (ListOps.length(evargs) != 2) {
		    throw new EvaluationError("CONS needs two arguments.");
		}
		SExpression second = ListOps.nth(evargs, 1);
		if (second instanceof ConsCell || second.equals(Symbol.NIL)) {
		    return new ConsCell(ListOps.car(evargs), second);
		} else {
		    throw new EvaluationError("CONS second argument should be list.");
		}
	    }
	};

	Function car = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if (ListOps.length(evargs) == 1) {
		    SExpression list = ListOps.car(evargs);
		    if (!(list instanceof ConsCell)) {
			throw new EvaluationError("CAR needs a list argument");
		    }
		    return ListOps.car(list);
		} else {
		    throw new EvaluationError("CAR needs an argument.");
		}
	    }
	};

	Function cdr = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if (ListOps.length(evargs) == 1) {
		    SExpression list = ListOps.car(evargs);
		    if (!(list instanceof ConsCell)) {
			throw new EvaluationError("CAR needs a list argument");
		    }
		    return ListOps.cdr(list);
		} else {
		    throw new EvaluationError("CAR needs an argument.");
		}
	    }
	};

	Function list = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		SExpression current = evargs;
		List<SExpression> list = new LinkedList<>();
		while (!current.equals(Symbol.NIL)) {
		    list.add(ListOps.car(current));
		    current = ListOps.cdr(current);
		}
		return ListOps.list(list);
	    }
	};

	Function eq = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if (ListOps.length(evargs) == 2) {
		    SExpression first = ListOps.nth(evargs, 0);
		    SExpression second = ListOps.nth(evargs, 1);
		    return (first.equals(second) ? Symbol.TRUE : Symbol.NIL);
		} else {
		    throw new EvaluationError("EQ needs two arguments");
		}
	    }
	};

	Function eval = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if (ListOps.length(evargs) == 1) {
		    return ListOps.car(evargs).eval(env);
		} else {
		    throw new EvaluationError("EVAL should get only one argument");
		}
	    }
	};

	Function apply = new Function() {
	    @Override
	    public SExpression apply(SExpression evargs, Environment env) {
		if (ListOps.length(evargs) == 2) {
		    SExpression function = ListOps.car(evargs);
		    if (!(function instanceof Function)) {
			throw new EvaluationError("First arg of APPLY should be a function");
		    }
                    
                    SExpression args = ListOps.nth(evargs, 1);

                    return ((Function) function).apply(args, env);
		} else {
		    throw new EvaluationError("APPLY should get two arguments");
		}
	    }
	};

	Special define = new Special() {
	    @Override
	    public SExpression applySpecial(SExpression args, Environment env) {
		if (ListOps.length(args) != 2) {
		    throw new EvaluationError("DEFINE should have two arguments");
		}

		Symbol alias;

		try {
		    alias = (Symbol) ListOps.car(args);
		} catch (ClassCastException e) {
		    throw new EvaluationError("DEFINE's first argument should be a symbol");
		}

		SExpression val = ListOps.nth(args, 1);

		env.bindGlobal(alias, val.eval(env));

		return Symbol.NIL;
	    }
	};

	Special quote = new Special() {
	    @Override
	    public SExpression applySpecial(SExpression args, Environment env) {
		if (ListOps.length(args) == 1) {
		    return ListOps.car(args);
		} else {
		    throw new EvaluationError("QUOTE needs an argument.");
		}
	    }
	};
	
	Special ifsp = new Special() {
	    @Override
	    public SExpression applySpecial(SExpression args, Environment env) {
		if (ListOps.length(args) == 3) {
		    ConsCell arg = (ConsCell) args;
		    SExpression s = arg.car.eval(env);
		    
		    if (s.equals(Symbol.NIL)) {
			return ListOps.nth(args, 2).eval(env);
		    } else {
			return ListOps.nth(args, 1).eval(env);
		    }
		} else {
		    throw new EvaluationError("IF needs condition, then and else parts");
		}
	    }
	};

	Special lambda = new Special() {
	    @Override
	    public SExpression applySpecial(SExpression args, Environment env) {
		if (ListOps.length(args) == 2) {
		    if (!ListOps.isListOf(ListOps.car(args), Symbol.class)) {
			throw new EvaluationError("LAMBDA first argument must be a symbols list");
		    }

		    final SExpression symbols = ListOps.car(args);
		    final SExpression body = ListOps.nth(args, 1);

		    return new Lambda(symbols, body, env);

		} else {
		    throw new EvaluationError("LAMBDA needs two arguments"); //To change body of generated methods, choose Tools | Templates.
		}
	    }
	};

	bindGlobal(env, "add", add);
	bindGlobal(env, "mult", mult);
	bindGlobal(env, "define", define);
	bindGlobal(env, "quote", quote);
	bindGlobal(env, "cons", cons);
	bindGlobal(env, "car", car);
	bindGlobal(env, "cdr", cdr);
	bindGlobal(env, "list", list);
	bindGlobal(env, "eq", eq);
	bindGlobal(env, "if", ifsp);
	bindGlobal(env, "lambda", lambda);
	bindGlobal(env, "eval", eval);
	bindGlobal(env, "apply", apply);

    }

    private static void bindGlobal(Environment env, String name, SExpression exp) {
	env.bindGlobal(new Symbol(name), exp);
    }
}
