import java.util.HashMap;

public class SymbolTable {
    HashMap<String, Integer> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<>();
        populateWithPredefinedSymbols();
    }

    private void populateWithPredefinedSymbols() {
        // Predefined symbols
        // (R0, R1, ..., R15)
        for (int i = 0; i < 16; i++) {
            String symbol = String.format("R%d", i);
            symbolTable.put(symbol, i);
        }

        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
    }

    public void addEntry(String symbol, int address) {
        symbolTable.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return symbolTable.get(symbol);
    }
}