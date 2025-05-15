package lf1.plp.functional1.expression;

import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions1.util.TipoPrimitivo;

public class PatternMatchExpression implements Expressao {
    private Expressao texto;
    private Expressao padrao;

    public PatternMatchExpression(Expressao texto, Expressao padrao) {
        this.texto = texto;
        this.padrao = padrao;
    }

    @Override
    public Valor avaliar(AmbienteExecucao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        Valor valorTexto = texto.avaliar(ambiente);
        Valor valorPadrao = padrao.avaliar(ambiente);
        
        if (!(valorTexto instanceof ValorString) || !(valorPadrao instanceof ValorString)) {
            return new ValorBooleano(false);
        }

        String textoStr = ((ValorString) valorTexto).valor();
        String padraoStr = ((ValorString) valorPadrao).valor();
        
        return new ValorBooleano(verificaPadrao(textoStr, padraoStr));
    }

    private boolean verificaPadrao(String texto, String padrao) {
        if (texto.length() != padrao.length()) {
            return false;
        }

        for (int i = 0; i < texto.length(); i++) {
            char charPadrao = padrao.charAt(i);
            char charTexto = texto.charAt(i);

            switch (charPadrao) {
                case 'L': // Letra maiúscula
                    if (!Character.isUpperCase(charTexto)) {
                        return false;
                    }
                    break;
                case 'l': // Letra minúscula
                    if (!Character.isLowerCase(charTexto)) {
                        return false;
                    }
                    break;
                case 'D': // Dígito
                    if (!Character.isDigit(charTexto)) {
                        return false;
                    }
                    break;
                case 'A': // Qualquer letra (maiúscula ou minúscula)
                    if (!Character.isLetter(charTexto)) {
                        return false;
                    }
                    break;
                case 'X': // Qualquer caractere
                    break;
                default: // Caractere literal
                    if (charPadrao != charTexto) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        return texto.checaTipo(ambiente) && 
               padrao.checaTipo(ambiente) &&
               texto.getTipo(ambiente).eString() &&
               padrao.getTipo(ambiente).eString();
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