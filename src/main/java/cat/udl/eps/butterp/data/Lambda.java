package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Lambda extends Function {

    private final Environment definition_env;
    private final SExpression params;
    private final SExpression body;

    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
	definition_env = definitionEnv;
	this.params = params;
	this.body = body;
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        Environment local_environment = definition_env.extend();
	bindAll(local_environment, params, evargs);
	return body.eval(local_environment);
    }
    
    private void bindAll(Environment e, SExpression symbols_list, SExpression values) {
	if (!symbols_list.equals(Symbol.NIL)) {
	    e.bind(
		    (Symbol) ListOps.car(symbols_list),
		    ListOps.car(values)
	    );
	    bindAll(e, ListOps.cdr(symbols_list), ListOps.cdr(values));
	}
    }
}
