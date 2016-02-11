package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Lambda extends Function {

    private final Environment local_environment;
    private final SExpression params;
    private final SExpression body;

    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
	local_environment = definitionEnv;
	this.params = params;
	this.body = body;
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
	local_environment.bindAll(params, evargs);
	return body.eval(local_environment);
    }
}
