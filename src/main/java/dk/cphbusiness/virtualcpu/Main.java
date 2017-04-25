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
        Machine machine = new Machine();
        
        
        machine.load(add);
        machine.print(System.out);
        while(true){
            machine.tick();
            machine.print(System.out);
        }
        
       /* 
        for (int line : add) {
            System.out.println(">>> " + line);
        }
*/
    }

}
