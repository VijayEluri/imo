/* Generated By:JavaCC: Do not edit this line. IMoParser.java */
package jp.gr.java_conf.mizu.imo.parser;
import java.util.*;
import jp.gr.java_conf.mizu.imo.Ast;
import static jp.gr.java_conf.mizu.imo.Ast.*;

public class IMoParser implements IMoParserConstants {
  /*
   * convenience method to create a Location
   */
  private static Pos p(Token t) {
    return new Pos(t.beginLine, t.beginColumn);
  }

  /*
   * convenience method to create a Location
   */
  private static Pos p(int line, int column) {
    return new Pos(line, column);
  }

  /*
   * convenience method to get image of a Token
   */
  private static String c(Token t) {
    return t.image;
  }

  /*
   * converience method to create a List
   */
  private static List list() {
    return new ArrayList();
  }

  /*
   * converience method to create substring
   */
  private static String sub(String s, int sindex, int eindex) {
    return s.substring(sindex, eindex);
  }

  /*
   * Returns a String which last character of s is omitted.
   */
  private static String chop(String s) {
        return s.substring(0, s.length() - 1);
  }

  /*
   * Returns a String which first and last character of s is omitted.
   */
  private static String chopEdge(String s) {
    return s.substring(1, s.length() - 1);
  }

  private static char unescapeChar(char c) {
    switch(c){
    case 'n' : return '\n';
    case 't' : return '\t';
    case 'b' : return '\b';
    case 'r' : return '\r';
    case 'f' : return '\f';
    case '\\': return '\\';
    case '\'': return '\'';
    case '"' : return '"';
    default  : return c;
    }
  }

  private static String unescape(String s) {
        StringBuffer b = new StringBuffer();
        int len = s.length();
        for(int i = 0; i < len; i++){
      char c = s.charAt(i);
      if(c != '\\'){
        b.append(c);
        continue;
      }
      i++;
      b.append(unescapeChar(s.charAt(i)));
        }
        return new String(b);
  }

  final public Program program() throws ParseException {
                   Fun fun; List<Fun> funs = new ArrayList<Fun>();
    label_1:
    while (true) {
      fun = function();
                    funs.add(fun);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case K_DEF:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
    }
                                         {if (true) return new Program(p(1, 1), funs);}
    throw new Error("Missing return statement in function");
  }

  final public Fun function() throws ParseException {
  Token t; Arg arg; List<Arg> args = new ArrayList<Arg>();
  Type ty; Exp e;
    jj_consume_token(K_DEF);
    t = jj_consume_token(ID);
    jj_consume_token(LPAREN);
    label_2:
    while (true) {
      arg = formal_parameter();
                                             args.add(arg);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
    }
    jj_consume_token(RPAREN);
    jj_consume_token(COLON);
    ty = type();
    jj_consume_token(ASSIGN);
    e = expression();
    {if (true) return new Fun(p(t), sym(t.image), args, ty, e);}
    throw new Error("Missing return statement in function");
  }

  final public Type type() throws ParseException {
             Type ty1, ty2;
    ty1 = basicType();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case RARROW:
      jj_consume_token(RARROW);
      ty2 = type();
                                     ty1 = Type.fun(ty1, ty2);
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    {if (true) return ty1;}
    throw new Error("Missing return statement in function");
  }

  final public Type basicType() throws ParseException {
                  Type ty;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case K_BOOLEAN:
      jj_consume_token(K_BOOLEAN);
                           {if (true) return Type.BOOL;}
      break;
    case K_INT:
      jj_consume_token(K_INT);
                           {if (true) return Type.INT;}
      break;
    case K_STRING:
      jj_consume_token(K_STRING);
                           {if (true) return Type.STRING;}
      break;
    case K_UNIT:
      jj_consume_token(K_UNIT);
                           {if (true) return Type.UNIT;}
      break;
    case K_IO:
      jj_consume_token(K_IO);
      jj_consume_token(LPAREN);
      ty = type();
      jj_consume_token(RPAREN);
                           {if (true) return Type.io(ty);}
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      ty = type();
      jj_consume_token(RPAREN);
                           {if (true) return ty;}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Arg formal_parameter() throws ParseException {
                        Token t; Type ty;
    t = jj_consume_token(ID);
    jj_consume_token(COLON);
    ty = type();
                         {if (true) return new Arg(p(t), sym(t.image), ty);}
    throw new Error("Missing return statement in function");
  }

  final public Exp expression() throws ParseException {
                  Token t; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case K_LET:
      e = let_expression();
                              {if (true) return e;}
      break;
    case K_IF:
      e = if_expression();
                              {if (true) return e;}
      break;
    case K_FALSE:
    case K_NOT:
    case K_TRUE:
    case PLUS:
    case MINUS:
    case LPAREN:
    case INTEGER:
    case STRING:
    case ID:
      e = bindable();
                              {if (true) return e;}
      break;
    case LAMBDA:
      e = anonymous_function();
                              {if (true) return e;}
      break;
    case K_RETURN:
      t = jj_consume_token(K_RETURN);
      e = expression();
                              {if (true) return new Return(p(t), e);}
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp anonymous_function() throws ParseException {
                          Token t; Arg arg; Exp e;
    t = jj_consume_token(LAMBDA);
    arg = formal_parameter();
    jj_consume_token(DOT);
    e = expression();
    {if (true) return new AnonFun(p(t), arg, e);}
    throw new Error("Missing return statement in function");
  }

  final public Exp bindable() throws ParseException {
                Token t; Exp a, b;
    a = apply();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BIND:
      case CONCAT:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BIND:
        t = jj_consume_token(BIND);
        b = apply();
                        a = new Bind(p(t), a, b);
        break;
      case CONCAT:
        t = jj_consume_token(CONCAT);
        b = apply();
                        a = new Concat(p(t), a, b);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp apply() throws ParseException {
             Exp f; Exp arg;
    f = logical_or();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DOLLAR:
      jj_consume_token(DOLLAR);
      arg = apply();
                                    f = app(f, arg);
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
                                                          {if (true) return f;}
    throw new Error("Missing return statement in function");
  }

  final public Exp logical_or() throws ParseException {
                  Token t; Exp a, b;
    a = logical_and();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case K_OR:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_4;
      }
      t = jj_consume_token(K_OR);
      b = logical_and();
                                            a = new Or(p(t), a, b);
    }
    {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp logical_and() throws ParseException {
                    Token t; Exp a, b;
    a = equal();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case K_AND:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_5;
      }
      t = jj_consume_token(K_AND);
      b = equal();
                                 a = new And(p(t), a, b);
    }
    {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp equal() throws ParseException {
             Token t; Exp a, b;
    a = comparative();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQ:
      case NOTEQ:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQ:
        t = jj_consume_token(EQ);
        b = comparative();
                              a = new Equal(p(t), a, b);
        break;
      case NOTEQ:
        t = jj_consume_token(NOTEQ);
        b = comparative();
                              a = new NotEqual(p(t), a, b);
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp comparative() throws ParseException {
                   Token t; Exp a, b;
    a = additive();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LT:
      case GT:
      case LEQ:
      case GEQ:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_7;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LEQ:
        t = jj_consume_token(LEQ);
        b = additive();
                         a = app2(ref(p(t), "<="), a, b);
        break;
      case GEQ:
        t = jj_consume_token(GEQ);
        b = additive();
                         a = app2(ref(p(t), ">="), a, b);
        break;
      case LT:
        t = jj_consume_token(LT);
        b = additive();
                         a = app2(ref(p(t), "<" ), a, b);
        break;
      case GT:
        t = jj_consume_token(GT);
        b = additive();
                         a = app2(ref(p(t), ">" ), a, b);
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp additive() throws ParseException {
                 Token t; Exp a, b;
    a = unary_prefix();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[14] = jj_gen;
        break label_8;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        t = jj_consume_token(PLUS);
        b = unary_prefix();
                             a = app2(ref(p(t), "+"), a, b);
        break;
      case MINUS:
        t = jj_consume_token(MINUS);
        b = unary_prefix();
                             a = app2(ref(p(t), "-"), a, b);
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp unary_prefix() throws ParseException {
                     Token t; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
      t = jj_consume_token(PLUS);
      e = unary_prefix();
                             e = app(ref(p(t), "u+"),  e);
      break;
    case MINUS:
      t = jj_consume_token(MINUS);
      e = unary_prefix();
                             e = app(ref(p(t), "u-"),  e);
      break;
    case K_NOT:
      t = jj_consume_token(K_NOT);
      e = unary_prefix();
                             e = app(ref(p(t), "not"), e);
      break;
    case K_FALSE:
    case K_TRUE:
    case LPAREN:
    case INTEGER:
    case STRING:
    case ID:
      e = multitive();
      break;
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp multitive() throws ParseException {
                  Token t; Exp a, b;
    a = primary_suffix();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASTER:
      case SLASH:
      case PERCENT:
        ;
        break;
      default:
        jj_la1[17] = jj_gen;
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASTER:
        t = jj_consume_token(ASTER);
        b = primary_suffix();
                               a = app2(ref(p(t), "*"), a, b);
        break;
      case SLASH:
        t = jj_consume_token(SLASH);
        b = primary_suffix();
                               a = app2(ref(p(t), "/"), a, b);
        break;
      case PERCENT:
        t = jj_consume_token(PERCENT);
        b = primary_suffix();
                               a = app2(ref(p(t), "%"), a, b);
        break;
      default:
        jj_la1[18] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
      {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp primary_suffix() throws ParseException {
                      Exp a, b;
    a = primary();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case K_FALSE:
      case K_TRUE:
      case LPAREN:
      case INTEGER:
      case STRING:
      case ID:
        ;
        break;
      default:
        jj_la1[19] = jj_gen;
        break label_10;
      }
      b = primary();
                             a = app(a, b);
    }
                                                 {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Exp primary() throws ParseException {
               Token t; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      t = jj_consume_token(ID);
                                          {if (true) return new Ref(p(t), sym(t.image));}
      break;
    case INTEGER:
      e = int_literal();
                                          {if (true) return e;}
      break;
    case STRING:
      e = string_literal();
                                          {if (true) return e;}
      break;
    case K_FALSE:
    case K_TRUE:
      e = bool_literal();
                                          {if (true) return e;}
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      e = expression();
      jj_consume_token(RPAREN);
                                          {if (true) return e;}
      break;
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp int_literal() throws ParseException {
                   Token t;
    t = jj_consume_token(INTEGER);
                {if (true) return new Int(p(t), Integer.parseInt(t.image));}
    throw new Error("Missing return statement in function");
  }

  final public Exp string_literal() throws ParseException {
                      Token t;
    t = jj_consume_token(STRING);
               {if (true) return new Str(p(t), unescape(chopEdge(t.image)));}
    throw new Error("Missing return statement in function");
  }

  final public Exp bool_literal() throws ParseException {
                    Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case K_TRUE:
      t = jj_consume_token(K_TRUE);
              {if (true) return new Bool(p(t), true);}
      break;
    case K_FALSE:
      t = jj_consume_token(K_FALSE);
              {if (true) return new Bool(p(t), false);}
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Def bind() throws ParseException {
            Token t; Exp e;
    t = jj_consume_token(ID);
    jj_consume_token(ASSIGN);
    e = expression();
                              {if (true) return new Def(p(t), sym(t.image), e);}
    throw new Error("Missing return statement in function");
  }

  final public Exp let_expression() throws ParseException {
  Token t; List<Def> defs = new ArrayList<Def>(); Def def = null; Exp e;
    t = jj_consume_token(K_LET);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      def = bind();
                 defs.add(def);
      label_11:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[22] = jj_gen;
          break label_11;
        }
        jj_consume_token(COMMA);
        def = bind();
                                                    defs.add(def);
      }
      break;
    default:
      jj_la1[23] = jj_gen;
      ;
    }
    jj_consume_token(K_IN);
    e = expression();
    {if (true) return new Let(p(t), defs, e);}
    throw new Error("Missing return statement in function");
  }

  final public Exp if_expression() throws ParseException {
                      Token t; Exp e1, e2, e3;
    t = jj_consume_token(K_IF);
    jj_consume_token(LPAREN);
    e1 = expression();
    jj_consume_token(RPAREN);
    e2 = expression();
    jj_consume_token(K_ELSE);
    e3 = expression();
    {if (true) return new If(p(t), e1, e2, e3);}
    throw new Error("Missing return statement in function");
  }

  public IMoParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[24];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
      jj_la1_0();
      jj_la1_1();
      jj_la1_2();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x400,0x0,0x0,0x1460200,0x6a8e000,0x0,0x0,0x40000000,0x100000,0x100,0x0,0x0,0x80000000,0x80000000,0x6000000,0x6000000,0x6882000,0x38000000,0x38000000,0x802000,0x802000,0x802000,0x0,0x0,};
   }
   private static void jj_la1_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x8,0x80000,0x86080000,0x60,0x60,0x0,0x0,0x0,0x300,0x300,0x7,0x7,0x0,0x0,0x84080000,0x0,0x0,0x84080000,0x84080000,0x0,0x200000,0x0,};
   }
   private static void jj_la1_2() {
      jj_la1_2 = new int[] {0x0,0x1,0x0,0x0,0x1,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x0,0x0,0x1,0x1,0x0,0x0,0x1,};
   }

  public IMoParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public IMoParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new IMoParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  public IMoParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new IMoParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  public IMoParser(IMoParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  public void ReInit(IMoParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[66];
    for (int i = 0; i < 66; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 24; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 66; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}