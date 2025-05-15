package lf1.plp.functional1.expression;

import java.util.ArrayList;
import java.util.List;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions1.util.TipoPrimitivo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class PatternFillExpression implements Expressao {
    private Expressao basePattern;
    private List<Expressao> valores;

    public PatternFillExpression(Expressao basePattern, List<Expressao> valores) {
        this.basePattern = basePattern;
        this.valores = valores;
    }

    @Override
    public Valor avaliar(AmbienteExecucao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        Valor valorBase = basePattern.avaliar(ambiente);
        
        if (!(valorBase instanceof ValorString)) {
            return new ValorString("");
        }

        String resultado = ((ValorString) valorBase).valor();
        List<String> valoresStr = new ArrayList<>();
        
        // Avaliar cada expressão da lista e converter para string
        for (Expressao exp : valores) {
            Valor valor = exp.avaliar(ambiente);
            valoresStr.add(valor.toString());
        }

        // Substituir cada {} pelos valores em ordem
        for (String valor : valoresStr) {
            resultado = resultado.replaceFirst("\\{\\}", valor);
        }

        return new ValorString(resultado);
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        boolean tipoBase = basePattern.checaTipo(ambiente) && basePattern.getTipo(ambiente).eString();
        
        // Verificar se todos os valores da lista são expressões válidas
        for (Expressao exp : valores) {
            if (!exp.checaTipo(ambiente)) {
                return false;
            }
        }
        
        return tipoBase;
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        return TipoPrimitivo.STRING;
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