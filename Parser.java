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

            if (!nextInstruction.contains("//") && !nextInstruction.isBlank() && !nextInstruction.isEmpty()) {
                currentInstruction = nextInstruction;
            }
        }

        if (nextInstruction == null) {
            hasMoreLines = false;
        }
    }

    public InstructionType instructionType() {
        if (currentInstruction.startsWith("@")) {
            instructionType = InstructionType.A_INSTRUCTION;
        } else if (currentInstruction.startsWith("(")) {
            instructionType = InstructionType.L_INSTRUCTION;
        } else if (!currentInstruction.contains("//") && !currentInstruction.isBlank()) {
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
        }

        return symbol;
    }

    public String dest() {
        String dest = "";

        if (instructionType == InstructionType.C_INSTRUCTION) {
            if (currentInstruction.contains("=")) {
                dest = currentInstruction.substring(0, 1);
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
                comp = comp.substring(comp.indexOf("=") + 1);
            } else if (currentInstruction.contains(";")) {
                comp = currentInstruction;
                comp = comp.split(";")[0].trim();
            }
        }
        return comp;
    }

    public String jump() {
        String jump = "";

        if (instructionType == InstructionType.C_INSTRUCTION) {
            if (currentInstruction.contains(";")) {
                jump = currentInstruction;
                jump = currentInstruction.substring(jump.indexOf(";") + 1);
            } else {
                jump = null;
            }
        }

        return jump;
    }
}
