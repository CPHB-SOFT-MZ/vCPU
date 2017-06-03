package dk.cphbusiness.virtualcpu;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the awesome CPU program");
        //Program program = new Program("00101001", "00001111", "10101010", "MOV B +3");
        //(5 + 11)*-3
        Program add = new Program("01001010", "00010000", "01010110", "00010000", 
                "01100110", "00010000", "00001100", "11001010",
                "00010010", "00001111", "00110111", "00110100", "00000001",
                "00110011", "00000010", "00100011", "00011010");
        
        Program fact = new Program("01001010", "00010000", "00001100", "11000110",
                "00010010", "00001111", "00110010", "00000111", "10001100", 
                "01000010", "00100001", "00011000", "00010000", "00010111", 
                "00010000", "00001100", "11000110", "00010011", "00010010", 
                "00000010", "00100001", "00011000");
        
        
        //Tail recursive factorial of 5
        Program tailFact = new Program("01000010", "00010000", "01001010", "00010000", 
                "00001100", "11001000", "00010010", "00001111", 
                "00110010", "00000111", "10001100", "00011001", 
                "00110101", "00000010", "00100010", "00110010", "00010111", "00100001", "00001100", "10001000");
        
        Program tailFactInstr = new Program("MOV 1 A", "PUSH A", "MOV 5 A", "PUSH A", "ALWAYS", 
                "CALL #8", "POP A", "HALT", "MOV +1 A", "NZERO", "JMP #12", "RTN +1", "MOV +2 B", "MUL",
                "MOV A +2", "MOV +1 A", "DEC", "MOV A +1", "ALWAYS", "JMP #8");
        
        Program normFactInstr = new Program("MOV 5 A", "PUSH A", "ALWAYS", "CALL #6", "POP A", "HALT", "MOV +1 A", 
                "NZERO", "JMP #12", "MOV 1 A", "MOV A +1", "RTN +0", "PUSH A", "DEC", "PUSH A", "ALWAYS", "CALL #6", 
                "POP B", "POP A", "MUL", "MOV A +1", "RTN +0");
        
        Machine machine = new Machine();
        
        
        machine.load(normFactInstr);
        machine.print(System.out);
        
        while(true){
            machine.tick();
            machine.print(System.out);
        }
        
    }

}
