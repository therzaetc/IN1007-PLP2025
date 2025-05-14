# Paradigmas de Linguagem de Programação 2025.1 - IN1007
# Alunas:

Maria Luisa Barreto de Gois - mlbg

Maria Thereza Carlos Rodrigues - mtcr

# Atividade

Este projeto tem como objetivo atender à disciplina de Paradigmas de Linguagens de Programação, ofertado pelo CIN na UFPE no semestre 2025.1.

O objetivo será estender uma linguagem de programação funcional com operadores básicos para manipulação de strings, adicionando funcionalidades como verificação de conformidade com o formato JSON, preenchimento de padrões pré-definidos, verificação de padrões e substituição de ocorrências de substrings.

# Contextualização
Este projeto estende a linguagem Funcional 1 com operadores para manipulação de Strings e validações com JSON. As funções são as seguintes:


- Verifica a conformidade com o formato JSON: avalia uma expressão e verifica se o seu valor tem o formato básico de JSON válido.

```
Input: {"nome": "João"}

Output: true

Input: {nome: "Maria", idade: 20}

Output: false
```

- Preenche um padrão pré-definido: realiza a substituição de placeholders {} dentro de uma string com valores fornecidos dinamicamente.

```
Input: "Olá, {}, você tem {} mensagens." ["Lucas", "5"]

Output: "Olá, Lucas, você tem 5 mensagens."
  
Input: "Resultado: {} + {} = {}" ["2", "3", "5"]

Output: "Resultado: 2 + 3 = 5"
```

- Verifica padrão customizado: permite comparar uma string contra um template de caracteres especiais (como "DllD"), e retorna um valor booleano indicando se o texto obedece ao padrão.

```
Input: "Abe9" "DllD"

Output: true

Input: "abc123" "lllDDD"

Output: true
```

- Substitui ocorrências de uma substring: substitui substrings dentro de uma string.

```
Input: "banana" "na" "ma"

Output: "bamama"

Input: "abcabc" "a" "z"

Output: "zbczbc"
```
# Implementação

Esta seção resume a implementação de quatro funcionalidades de manipulação de strings na Linguagem Funcional 1.

## Sumário
1. [Validação JSON](#1-validação-json)
2. [Substituição de Strings](#2-substituição-de-strings)
3. [Pattern Matching](#3-pattern-matching)
4. [Pattern Fill](#4-pattern-fill)
5. [Gramática BNF Atualizada](#5-gramática-bnf-atualizada)

## 1. Validação JSON
Implementação da funcionalidade `JsonValidatorExpression` que verifica se uma string representa um JSON válido através das seguintes características:

- Verifica estrutura básica do JSON
- Checa balanceamento de chaves e colchetes
- Retorna resultado booleano
- Inclui testes unitários e testes específicos da linguagem

### Implementação
```java
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
}
```

## 2. Substituição de Strings
Implementação da funcionalidade `StringReplaceExpression` para substituição de substrings através das seguintes características:

- Substitui múltiplas ocorrências
- Case-sensitive
- Suporta caracteres especiais
- Inclui suite de testes abrangente

### Implementação
```java
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
}
```

## 3. Pattern Matching
Implementação da funcionalidade `PatternMatchExpression` para validação de formatos específicos através das seguintes características:

- Suporta padrões como "LLL-DDD"
- Símbolos de padrão:
  - `L`: Letra maiúscula
  - `l`: Letra minúscula
  - `D`: Dígito
  - `A`: Qualquer letra
  - `X`: Qualquer caractere
- Testes para padrões comuns (email, telefone, datas)

### Implementação
```java
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
                case 'L':
                    if (!Character.isUpperCase(charTexto)) {
                        return false;
                    }
                    break;
                case 'l':
                    if (!Character.isLowerCase(charTexto)) {
                        return false;
                    }
                    break;
                case 'D':
                    if (!Character.isDigit(charTexto)) {
                        return false;
                    }
                    break;
                case 'A':
                    if (!Character.isLetter(charTexto)) {
                        return false;
                    }
                    break;
                case 'X':
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
}
```

## 4. Pattern Fill
Implementação da funcionalidade `PatternFillExpression` para preenchimento de padrões com valores através das seguintes características:

- Substitui placeholders `{}` com valores de uma lista
- Suporta diferentes tipos de valores
- Mantém ordem de substituição
- Tratamento de casos especiais (mais/menos valores que placeholders)

### Implementação
```java
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
        
        for (Expressao exp : valores) {
            Valor valor = exp.avaliar(ambiente);
            valoresStr.add(valor.toString());
        }

        for (String valor : valoresStr) {
            resultado = resultado.replaceFirst("\\{\\}", valor);
        }

        return new ValorString(resultado);
    }
}
```

### Exemplo de Uso
```
let
  var padrao = "Olá {}, bem-vindo ao {}!"
  var valores = ["mundo", "PLP"]
in
  fillPattern(padrao, valores)
```

### Testes
```java
@Test
public void testPreenchimentoSimples() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
    List<Expressao> valores = Arrays.asList(
        new ValorString("mundo"),
        new ValorString("PLP")
    );
    
    PatternFillExpression fillPattern = new PatternFillExpression(
        new ValorString("Olá {}, bem-vindo ao {}!"),
        valores
    );
    
    ValorString resultado = (ValorString) fillPattern.avaliar(ambiente);
    assertEquals("Olá mundo, bem-vindo ao PLP!", resultado.valor());
}
```

## 5. Gramática BNF Atualizada
```bnf
Programa ::= Expressao

Expressao ::= Valor
            | ExpUnaria
            | ExpBinaria
            | ExpDeclaracao
            | Id
            | Aplicacao
            | IfThenElse
            | ValidateJson
            | StringReplace
            | PatternMatch
            | PatternFill

Valor ::= ValorConcreto

ValorConcreto ::= ValorInteiro | ValorBooleano | ValorString

ExpUnaria ::= "-" Expressao | "not" Expressao | "length" Expressao

ExpBinaria ::= Expressao "+" Expressao
             | Expressao "-" Expressao
             | Expressao "and" Expressao
             | Expressao "or" Expressao
             | Expressao "==" Expressao
             | Expressao "++" Expressao

ExpDeclaracao ::= "let" DeclaracaoFuncional "in" Expressao

DeclaracaoFuncional ::= DecVariavel
                      | DecFuncao
                      | DecComposta

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListId "=" Expressao

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListId ::= Id  |  Id ListId

Aplicacao:= Id"(" ListExp ")"

ListExp ::= Expressao  |  Expressao, ListExp

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao

// Novas funcionalidades
ValidateJson ::= "validateJson" "(" Expressao ")"

StringReplace ::= "replace" "(" Expressao "," Expressao "," Expressao ")"

PatternMatch ::= "matchPattern" "(" Expressao "," Expressao ")"

PatternFill ::= "fillPattern" "(" Expressao "," "[" ListExp "]" ")"
```

## Integração com o Parser
Trechos adicionados ao arquivo Functional1.jj.

```java
TOKEN : /* TOKENS DE FUNCIONAL 1 */
{
  // ... tokens existentes ...
  < VALIDATE_JSON: "validateJson" >
| < REPLACE: "replace" >
| < MATCH_PATTERN: "matchPattern" >
| < FILL_PATTERN: "fillPattern" >
}

// ... outras regras do parser ...

Expressao ValidateJson() :
{
  Expressao exp;
}
{
  <VALIDATE_JSON> "(" exp = PExpressao() ")"
  { return new JsonValidatorExpression(exp); }
}

Expressao StringReplace() :
{
  Expressao texto, antigo, novo;
}
{
  <REPLACE> "(" texto = PExpressao() "," antigo = PExpressao() "," novo = PExpressao() ")"
  { return new StringReplaceExpression(texto, antigo, novo); }
}

Expressao PatternMatch() :
{
  Expressao texto, padrao;
}
{
  <MATCH_PATTERN> "(" texto = PExpressao() "," padrao = PExpressao() ")"
  { return new PatternMatchExpression(texto, padrao); }
}

Expressao PatternFill() :
{
  Expressao padrao;
  List valores = new ArrayList();
  Expressao valor;
}
{
  <FILL_PATTERN> <LPAREN> padrao = PExpressao() <COMMA> 
  <LBRACKET>
  (
    valor = PExpressao()
    {
      valores.add(valor);
    }
    (
      <COMMA> valor = PExpressao()
      {
        valores.add(valor);
      }
    )*
  )?
  <RBRACKET>
  <RPAREN>
  {
    return new PatternFillExpression(padrao, valores);
  }
}
```

## Notas de Implementação
1. Todas as funcionalidades seguem o paradigma funcional da linguagem
2. Integração completa com o sistema de tipos existente
3. Testes unitários e testes específicos da linguagem para cada funcionalidade
4. Tratamento adequado de erros e casos especiais
5. Documentação completa do código

## Estrutura de Arquivos
Localização dos arquivos adicionados e modificados.

```
Funcional1/
├── src/
│   └── lf1/plp/functional1/
│       ├── expression/
│       │   ├── JsonValidatorExpression.java
│       │   ├── JsonValidatorExpressionTest.java
│       │   ├── StringReplaceExpression.java
│       │   ├── StringReplaceExpressionTest.java
│       │   ├── PatternMatchExpression.java
│       │   ├── PatternMatchExpressionTest.java
│       │   ├── PatternFillExpression.java
│       │   └── PatternFillExpressionTest.java
│       └── parser/
│           └── Functional1.jj
└── Testes/
    ├── TesteJsonFuncional1.txt
    ├── TestePatternMatchFuncional1.txt
    ├── TestePatternFillFuncional1.txt
    └── TesteStringReplaceFuncional1.txt
```
