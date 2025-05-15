package lf1.plp.functional1.expression;

import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions1.util.TipoPrimitivo;

public class StringReplaceExpression implements Expressao {
    private Expressao texto;
    private Expressao antigo;
    private Expressao novo;

    public StringReplaceExpression(Expressao texto, Expressao antigo, Expressao novo) {
        this.texto = texto;
        this.antigo = antigo;
        this.novo = novo;
    }

    @Override
    public Valor avaliar(AmbienteExecucao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        Valor valorTexto = texto.avaliar(ambiente);
        Valor valorAntigo = antigo.avaliar(ambiente);
        Valor valorNovo = novo.avaliar(ambiente);
        
        if (!(valorTexto instanceof ValorString) || 
            !(valorAntigo instanceof ValorString) || 
            !(valorNovo instanceof ValorString)) {
            return new ValorString("");
        }

        String textoOriginal = ((ValorString) valorTexto).valor();
        String substringAntiga = ((ValorString) valorAntigo).valor();
        String substringNova = ((ValorString) valorNovo).valor();
        
        String resultado = textoOriginal.replace(substringAntiga, substringNova);
        return new ValorString(resultado);
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        return texto.checaTipo(ambiente) && 
               antigo.checaTipo(ambiente) && 
               novo.checaTipo(ambiente) &&
               texto.getTipo(ambiente).eString() &&
               antigo.getTipo(ambiente).eString() &&
               novo.getTipo(ambiente).eString();
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