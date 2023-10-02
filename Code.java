public class Code {

    public String dest(String dest) {
        String bDest = "";

        if (dest != null) {
            switch (dest) {
                case "M" -> bDest = "001";
                case "D" -> bDest = "010";
                case "DM", "MD" -> bDest = "011";
                case "A" -> bDest = "100";
                case "AM" -> bDest = "101";
                case "AD" -> bDest = "110";
                case "ADM", "AMD" -> bDest = "111";
            }
        } else {
            bDest = "000";
        }
        return bDest;
    }

    public String comp(String comp) {
        String bComp = "";

        switch (comp) {
            case "0" -> bComp = "0101010";
            case "1" -> bComp = "0111111";
            case "-1" -> bComp = "0111010";
            case "D" -> bComp = "0001100";
            case "A" -> bComp = "0110000";
            case "!D" -> bComp = "0001101";
            case "!A" -> bComp = "0110001";
            case "-D" -> bComp = "0001111";
            case "-A" -> bComp = "0110011";
            case "D+1" -> bComp = "0011111";
            case "A+1" -> bComp = "0110111";
            case "D-1" -> bComp = "0001110";
            case "A-1" -> bComp = "0110010";
            case "D+A" -> bComp = "0000010";
            case "D-A" -> bComp = "0010011";
            case "A-D" -> bComp = "0000111";
            case "D&A" -> bComp = "0000000";
            case "D|A" -> bComp = "0010101";
            case "M" -> bComp = "1110000";
            case "!M" -> bComp = "1110001";
            case "-M" -> bComp = "1110011";
            case "M+1" -> bComp = "1110111";
            case "M-1" -> bComp = "1110010";
            case "D+M" -> bComp = "1000010";
            case "D-M" -> bComp = "1010011";
            case "M-D" -> bComp = "1000111";
            case "D&M" -> bComp = "1000000";
            case "D|M" -> bComp = "1010101";
        }

        return bComp;
    }

    public String jump(String jump) {
        String bJump = "";

        if (jump != null) {
            switch (jump) {
                case "JGT" -> bJump = "001";
                case "JEQ" -> bJump = "010";
                case "JGE" -> bJump = "011";
                case "JLT" -> bJump = "100";
                case "JNE" -> bJump = "101";
                case "JLE" -> bJump = "110";
                case "JMP" -> bJump = "111";
            }
        } else {
            bJump = "000";
        }

        return bJump;
    }
}
