import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;


public class main {


    public static void main(String[] args) throws IOException {

        // please provide the input file name as an argument with make run ARGS="filename"
        if (args.length != 1) {
            System.err.println("\n");
            System.err.println("Please give as input argument a filename\n");
            System.exit(-1);
        }
        String filename = args[0];

        // open the input file
        CharStream input = CharStreams.fromFileName(filename);

        // create a lexer/scanner
        ccLexer lex = new ccLexer(input);

        // get the stream of tokens from the scanner
        CommonTokenStream tokens = new CommonTokenStream(lex);

        // create a parser
        ccParser parser = new ccParser(tokens);

        // parse from start rule
        ParseTree parseTree = parser.start();

        // Construct an interpreter and run it on the parse tree
        Interpreter interpreter = new Interpreter();
        String result = interpreter.visit(parseTree);
        System.out.println(result);

        // write the result to a html file
        String outputFilename = "output.html";
        try (FileWriter fileWriter = new FileWriter(outputFilename)) {
            fileWriter.write(result);
        }
        System.out.println("HTML file generated: " + outputFilename);

    }
}

// We write an interpreter that implements interface
// "implVisitor<T>" that is automatically generated by ANTLR
// This is parameterized over a return type "<T>" which is in our case
// simply a Double.

class Interpreter extends AbstractParseTreeVisitor<String>
        implements ccVisitor<String> {


    @Override
    public String visitStart(ccParser.StartContext ctx) {
        String html_header = "<!DOCTYPE html>\n<html>\n<head>\n<title>Hardware Description</title>\n"
                + "<script src=\"https://polyfill.io/v3/polyfill.min.js?features=es6\"></script>\n"
                + "<script type=\"text/javascript\" id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\"></script>\n"
                + "<style>\n"
                + "  body {\n"
                + "    background-color: powderblue;\n"
                + "    display: flex;\n"
                + "    justify-content: center;\n"
                + "    align-items: center;\n"
                + "    font-family: Arial, sans-serif;\n"
                + "  }\n"
                + "  .content {\n"
                + "    text-align: center;\n"
                + "  }\n"
                + "</style>\n"
                + "</head>\n<body>\n"
                + "<div class=\"content\">\n";


        String htmlOutput = html_header;
        htmlOutput += visit(ctx.hardwareResult()) + "\n";
        htmlOutput += visit(ctx.inputsResult()) + "\n";
        htmlOutput += visit(ctx.outputsResult()) + "\n";
        htmlOutput += visit(ctx.latchesResult()) + "\n";
        for (ccParser.DefResultContext defContext : ctx.defResult()) {
            htmlOutput += visit(defContext) + "\n";
        }
        htmlOutput += visit(ctx.updatesResult()) + "\n";
        htmlOutput += visit(ctx.simInputsResult()) + "\n";
        htmlOutput += "</div></body>\n</html>";

        return htmlOutput;
    }

    @Override
    public String visitHardwareRes(ccParser.HardwareResContext ctx) {
        return "<h1>" + ctx.x.getText() + "</h1>\n";
    }

    @Override
    public String visitInputsRes(ccParser.InputsResContext ctx) {
        String inputHeader = "<h2>" + ctx.i.getText() + "</h2>\n";
        String inputSignals = visit(ctx.signalList());
        return inputHeader + inputSignals;
    }

    @Override
    public String visitOutputsRes(ccParser.OutputsResContext ctx) {
        String outputHeader = "<h2>" + ctx.o.getText() + "</h2>\n";
        String outputSignals = visit(ctx.signalList());
        return outputHeader + outputSignals;
    }

    @Override
    public String visitLatchesRes(ccParser.LatchesResContext ctx) {
        String latchesHeader = "<h2>" + ctx.l.getText() + "</h2>\n";
        String latchesSignals = visit(ctx.signalList());
        return latchesHeader + latchesSignals;
    }

    @Override
    public String visitUpdatesRes(ccParser.UpdatesResContext ctx) {
        String updatesHeader = "<h2>" + ctx.u.getText() + "</h2>\n";
        List<TerminalNode> updateIdents = ctx.IDENT();
        List<ccParser.ExpContext> updatesExp = ctx.exp();

        StringBuilder updateList = new StringBuilder();

        for (int i = 0; i < updatesExp.size(); i++) {
            updateList.append("\\(\\mathrm{" + updateIdents.get(i).getText() + "}\\)")
                    .append(" &larr; ")
                    .append(visit(ctx.exp(i)))
                    .append("<br>");
        }

        return updatesHeader + updateList;
    }


    @Override
    public String visitDefRes(ccParser.DefResContext ctx) {
        String defHeader = "<h2>" + ctx.d.getText() + "</h2>";
        StringBuilder defList = new StringBuilder();

        String defIdent = "\\(\\mathit{" + ctx.x.getText() + "}\\)";

        List<ccParser.SignalListContext> signals = ctx.signalList();

        for (int i = 0; i < signals.size(); i++) {
            defList.append("(" + visit(signals.get(i)) + ")");
        }

        List<ccParser.ExpContext> expressions = ctx.exp();
        for (int i = 0; i < expressions.size(); i++) {
            defList.append(" = ").append(visit(ctx.e));

        }

        return defHeader + defIdent + defList;
    }


    @Override
    public String visitSimInputsRes(ccParser.SimInputsResContext ctx) {
        String simInputsHeader = "<h2>" + ctx.s.getText() + "</h2>\n";

        List<TerminalNode> simInputsIdents = ctx.IDENT();
        List<TerminalNode> simInputsNumbers = ctx.NUMBER();

        StringBuilder simInputsList = new StringBuilder();
        for (int i = 0; i < simInputsIdents.size(); i++) {
            simInputsList.append("\\(\\mathrm{" + simInputsIdents.get(i).getText() + "}\\)")
                    .append(" = ")
                    .append(simInputsNumbers.get(i).getText())
                    .append("<br>");
        }
        return simInputsHeader + simInputsList;
    }

    @Override
    public String visitSignalListRes(ccParser.SignalListResContext ctx) {
        StringBuilder signalList = new StringBuilder();
        List<TerminalNode> signals = ctx.IDENT();
        for (int i = 0; i < signals.size(); i++) {
            if (i == signals.size() - 1) {
                signalList.append("\\(" + "\\mathrm{" + signals.get(i).getText() + "}" + "\\)");
            } else {
                signalList.append("\\(" + "\\mathrm{" + signals.get(i).getText() + "}" + "\\)").append(", ");
            }
        }
        return signalList.toString();
    }

    @Override
    public String visitNOT(ccParser.NOTContext ctx) {
        return "\\(\\neg\\)" + visit(ctx.exp());
    }

    @Override
    public String visitOR(ccParser.ORContext ctx) {
        return "(" + visit(ctx.e1) + ")" + " \\(\\vee\\) " + "(" + visit(ctx.e2) + ")";
    }

    @Override
    public String visitPARENTHESES(ccParser.PARENTHESESContext ctx) {
        return "(" + visit(ctx.e1) + ")";
    }

    @Override
    public String visitIDENT(ccParser.IDENTContext ctx) {
        return ctx.IDENT().getText();
    }

    @Override
    public String visitAND(ccParser.ANDContext ctx) {
        return "(" + visit(ctx.e1) + ")" + " \\(\\wedge\\) " + "(" + visit(ctx.e2) + ")";
    }


    @Override
    public String visitFUNCTION(ccParser.FUNCTIONContext ctx) {
        // italicize identifier.
        return "\\(\\mathit{" + ctx.x.getText() + "}\\)" + visit(ctx.e1);
    }

    @Override
    public String visitIDENTAPOSTROPHE(ccParser.IDENTAPOSTROPHEContext ctx) {
        return "\\(\\mathrm{" + ctx.IDENTAPOSTROPHE().getText() + "}\\)";
    }

    @Override
    public String visitEXPRESSIONS(ccParser.EXPRESSIONSContext ctx) {
        return "\\(\\mathrm{" + ctx.getText() + "}\\)";
    }
}

