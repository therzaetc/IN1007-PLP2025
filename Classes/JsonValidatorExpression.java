package lf1.plp.functional1.expression;

import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions1.util.TipoPrimitivo;

public class JsonValidatorExpression implements Expressao {
    private Expressao expressao;

    public JsonValidatorExpression(Expressao expressao) {
        this.expressao = expressao;
    }

    @Override
    public Valor avaliar(AmbienteExecucao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        Valor valor = expressao.avaliar(ambiente);
        
        if (!(valor instanceof ValorString)) {
            return new ValorBooleano(false);
        }

        String conteudo = ((ValorString) valor).valor();
        return new ValorBooleano(isValidJson(conteudo.trim()));
    }

    private boolean isValidJson(String json) {
        if (json.isEmpty()) return false;
        
        // Verificação básica de JSON
        if (json.startsWith("{") && json.endsWith("}")) {
            return validateBraces(json);
        } else if (json.startsWith("[") && json.endsWith("]")) {
            return validateBraces(json);
        }
        
        return false;
    }

    private boolean validateBraces(String json) {
        int braces = 0;
        int brackets = 0;
        
        for (char c : json.toCharArray()) {
            if (c == '{') braces++;
            if (c == '}') braces--;
            if (c == '[') brackets++;
            if (c == ']') brackets--;
            
            if (braces < 0 || brackets < 0) return false;
        }
        
        return braces == 0 && brackets == 0;
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        return expressao.checaTipo(ambiente);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        return TipoPrimitivo.BOOLEANO;
    }

    @Override
    public Expressao reduzir(AmbienteExecucao ambiente) {
        return this;
    }

    @Override
    public Valor clone() {
        return null;  // Esta expressão não é um valor
    }
} 