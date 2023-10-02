import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private String currentInstruction;
    private Boolean hasMoreLines;
    private InstructionType instructionType;
    private final BufferedReader br;

    /**
     * Opens the input file/stream and gets ready to parse it
     *
     * @param file name of the file you want to parse
     */
    public Parser(String file) {
        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Are there more lines in the input?
     *
     * @return true if there are more lines to parse
     */
    public boolean hasMoreLines() {
        return hasMoreLines;
    }

    public void advance() {
        currentInstruction = "";
        String nextInstruction;

        try {
            nextInstruction = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (nextInstruction != null) {
            hasMoreLines = true;

            // Remove whitespaces
            String cI = nextInstruction.trim();

            if (cI.startsWith("@") || cI.startsWith("M") || cI.startsWith("D")
                    || cI.startsWith("A") || cI.startsWith("(") || cI.startsWith("0")
                    || cI.startsWith("1")) {
                // Makes sure we do not parse any inline comments
                currentInstruction = cI.split(" ")[0];
            }
        }

        if (nextInstruction == null) {
            hasMoreLines = false;
        }
    }

    public InstructionType instructionType() {
        if (currentInstruction.contains("@")) {
            instructionType = InstructionType.A_INSTRUCTION;
        } else if (currentInstruction.contains("(")) {
            instructionType = InstructionType.L_INSTRUCTION;
        } else if (currentInstruction.contains("D") || currentInstruction.contains("M") ||
                currentInstruction.contains("A")) {
            instructionType = InstructionType.C_INSTRUCTION;
        } else {
            instructionType = null;
        }

        return instructionType;
    }

    public String symbol() {
        String symbol = "";

        if (instructionType == InstructionType.A_INSTRUCTION) {
            symbol = currentInstruction.substring(1);
        } else if (instructionType == InstructionType.L_INSTRUCTION) {
            symbol = currentInstruction;
            symbol = symbol.substring(symbol.indexOf("(") + 1);
            symbol = symbol.substring(0, symbol.lastIndexOf(")"));
            // System.out.println("l : " + symbol);
        }

        return symbol;
    }

    public String dest() {
        String dest = "";

        if (instructionType == InstructionType.C_INSTRUCTION) {
            if (currentInstruction.contains("=")) {
                // dest = currentInstruction.substring(0, 1);
                dest = currentInstruction;
                dest = dest.split("=")[0].split(" ")[0];
                // System.out.println("c: dest: " + dest);
            } else {
                dest = null;
            }
        }
        return dest;
    }

    public String comp() {
        String comp = "";

        if (instructionType == InstructionType.C_INSTRUCTION) {
            if (currentInstruction.contains("=")) {
                comp = currentInstruction;
                comp = comp.split("=")[1].split(" ")[0];

                // System.out.println("c: comp: " + comp);
            } else if (currentInstruction.contains(";")) {
                comp = currentInstruction;
                comp = comp.split(";")[0];

                // System.out.println("c: comp: " + comp);
            }
        }
        return comp;
    }

    public String jump() {
        String jump = "";

        if (instructionType == InstructionType.C_INSTRUCTION) {
            if (currentInstruction.contains(";")) {
                jump = currentInstruction;
                jump = jump.split(";")[1].split(" ")[0];
                // System.out.println("c: jump: " + jump);
            } else {
                jump = null;
            }
        }

        return jump;
    }
}
