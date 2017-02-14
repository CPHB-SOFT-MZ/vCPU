package dk.cphbusiness.virtualcpu;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the awesome CPU program");
        //Program program = new Program("00101001", "00001111", "10101010", "MOV B +3");
        Program add = new Program("01001010", "00010000", "01010110", "00010000", 
                "01000110", "00010000", "00001100", "11001010",
                "00010010", "00001111", "00110111", "00110100", "00000001",
                "00110011", "00000010", "00100011", "00011010");
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
