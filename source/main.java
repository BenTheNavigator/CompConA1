import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;


public class main {
    public static void main(String[] args) throws IOException{

		
	// we expect exactly one argument: the name of the input file
	if (args.length!=1) {
	    System.err.println("\n");
	    System.err.println("Please give as input argument a filename\n");
	    System.exit(-1);
	}
	String filename="cc.txt";

	// open the input file
	CharStream input = CharStreams.fromFileName(filename);
	    //new ANTLRFileStream (filename); // depricated
	
	// create a lexer/scanner
	ccLexer lex = new ccLexer(input);
	
	// get the stream of tokens from the scanner
	CommonTokenStream tokens = new CommonTokenStream(lex);
	
	// create a parser
	ccParser parser = new ccParser(tokens);
	
	// and parse anything from the grammar for "start"
	ParseTree parseTree = parser.start();

	// Construct an interpreter and run it on the parse tree
	Interpreter prettyprint = new Interpreter();


	String result = prettyprint.visit(parseTree);
	System.out.println(result);
    }
}

// We write an interpreter that implements interface
// "implVisitor<T>" that is automatically generated by ANTLR
// This is parameterized over a return type "<T>" which is in our case

class Interpreter extends AbstractParseTreeVisitor<String>
                  implements ccVisitor<String> {

	private StringBuilder html;

	public Interpreter() {
		this.html = new StringBuilder();
	}

     @Override
    public String visitStart(ccParser.StartContext ctx) {
		// boilerplate html code
		html.append("<!DOCTYPE html>\n<html><head><title>")
            .append("TITLE")  // Replace this with the actual title if available
            .append("</title>\n<script src=\"https://polyfill.io/v3/polyfill.min.js?features=es6\"></script>\n")
            .append("<script type=\"text/javascript\" id=\"MathJax-script\"\nasync src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\">\n")
            .append("</script></head><body>\n");

        // <h1> tag hardware:
		html.append("<h1>").append(visit(ctx.hardwareResult())).append("</h1>\n");

		// <h2> tags
		html.append("<h2> Inputs </h2>\n").append(visit(ctx.inputsResult())).append("\n");
		html.append("<h2> Outputs </h2>\n").append(visit(ctx.outputsResult())).append("\n");
		html.append("<h2> Latches </h2>\n").append(visit(ctx.latchesResult())).append("\n");
		// html.append("<h2> Definitions </h2>\n").append(visit(ctx.defResult())).append("\n");
        html.append("<h2> Updates </h2>\n").append(visit(ctx.updatesResult())).append("\n");
        html.append("<h2> Simulation inputs </h2>\n").append(visit(ctx.simInputsResult())).append("\n");







        // End the HTML
        html.append("</body></html>");

        return html.toString();
    }
	
	@Override
    public String visitAND(ccParser.ANDContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitDefRes(ccParser.DefResContext ctx) {
		return ctx.x.getText();
    }

    @Override
    public String visitFUNCTION(ccParser.FUNCTIONContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitHardwareRes(ccParser.HardwareResContext ctx) {
		return ctx.x.getText();
    }

    @Override
    public String visitIDENT(ccParser.IDENTContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitIDENTAPOSTROPHE(ccParser.IDENTAPOSTROPHEContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitInputsRes(ccParser.InputsResContext ctx) {
		return ctx.x.getText();
    }

    @Override
    public String visitLatchesRes(ccParser.LatchesResContext ctx) {
        return ctx.x.getText();
    }

    @Override
    public String visitNOT(ccParser.NOTContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitOR(ccParser.ORContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitOutputsRes(ccParser.OutputsResContext ctx) {
        return ctx.x.getText();
    }

    @Override
    public String visitPARENTHESES(ccParser.PARENTHESESContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitSignalListRes(ccParser.SignalListResContext ctx) {
        // TODO Auto-generated method stub
        return "i am here";
    }

    @Override
    public String visitSimInputsRes(ccParser.SimInputsResContext ctx) {
        return ctx.getText();
    }

   

    @Override
    public String visitUpdatesRes(ccParser.UpdatesResContext ctx) {
        return "i am here";
    }

	@Override
	public String visitEXPRESSIONS(ccParser.EXPRESSIONSContext ctx) {
		// TODO Auto-generated method stub
		return "exp";
	}


}

