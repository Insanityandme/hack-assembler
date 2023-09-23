import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HackAssembler {
    private final SymbolTable symbolTable;

    public HackAssembler(String file) {
        symbolTable = new SymbolTable();

        Parser firstPassParser = new Parser(file);
        Parser secondPassParser = new Parser(file);

        firstPass(firstPassParser);
        secondPass(secondPassParser, file);
    }

    private void firstPass(Parser parser) {
        int lineNumber = 0;
        parser.advance();

        while (parser.hasMoreLines()) {
            parser.advance();

            InstructionType instructionType = parser.instructionType();

            if (instructionType == InstructionType.C_INSTRUCTION || instructionType == InstructionType.A_INSTRUCTION) {
                lineNumber += 1;
            } else if (instructionType == InstructionType.L_INSTRUCTION) {
                String symbol = parser.symbol();

                symbolTable.addEntry(symbol, lineNumber + 1);
            }
        }
    }

    private void secondPass(Parser parser, String file) {
        String fileOut = file.substring(0, file.lastIndexOf(".")) + ".hack";
        int nextAvailableAddress = 16;
        FileWriter fileWriter;

        Code code = new Code();

        try {
            fileWriter = new FileWriter(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter printWriter = new PrintWriter(fileWriter);

        parser.advance();

        while (parser.hasMoreLines()) {
            parser.advance();

            InstructionType instructionType = parser.instructionType();

            String dest = parser.dest();
            String bDest = code.dest(dest);

            String comp = parser.comp();
            String bComp = code.comp(comp);

            String jump = parser.jump();
            String bJump = code.jump(jump);

            if (instructionType == InstructionType.A_INSTRUCTION) {
                String symbol = parser.symbol();
                int symbolNumber;

                if (symbolTable.contains(symbol)) {
                    symbolNumber = symbolTable.getAddress(symbol);
                    String bInstructionA = decToBinary(symbolNumber);
                    printWriter.println(bInstructionA);
                }
                /*
                else if (!symbolTable.contains(symbol)) {
                    symbolTable.addEntry(symbol, nextAvailableAddress);
                    System.out.println(symbol);
                    nextAvailableAddress++;

                    symbolNumber = symbolTable.getAddress(symbol);
                    String bInstructionA = decToBinary(symbolNumber);
                    printWriter.println(bInstructionA);
                 }
                 */
                else {
                    symbolNumber = Integer.parseInt(parser.symbol());
                    String bInstructionA = decToBinary(symbolNumber);
                    printWriter.println(bInstructionA);
                }
            } else if (instructionType == InstructionType.C_INSTRUCTION) {
                String bInstructionC = "111" + bComp + bDest + bJump;
                printWriter.println(bInstructionC);
            }
        }

        try {
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String decToBinary(int n) {
        StringBuilder b = new StringBuilder();

        for (int i = 15; i >= 0; i--) {
            int k = n >> i;

            if ((k & 1) > 0) {
                b.append("1");
            } else
                b.append("0");
        }

        return b.toString();
    }
}
