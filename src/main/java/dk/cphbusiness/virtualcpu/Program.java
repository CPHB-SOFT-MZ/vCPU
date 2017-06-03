package dk.cphbusiness.virtualcpu;

import java.util.Iterator;

public class Program implements Iterable<Integer> {

    private String[] lines;

    public Program(String... lines) {
        this.lines = lines;
    }

    public int get(int index) {
        String line = lines[index];
        if (line.charAt(0) == '0' || line.charAt(0) == '1') {
            return Integer.parseInt(line, 2);
        } else if (line.startsWith("MOV ")) {
            String[] parts = line.split(" ");
            
            if(parts[1].contains("+")){
                int r = parts[2].equals("B") ? 1 : 0;
                int o = Integer.parseInt(parts[1].replace("+", ""));
                return 0b0011_0000 | (o << 1) | r;
            } else if (parts[2].contains("+")) {
                int r = parts[1].equals("B") ? 1 : 0;
                int o = Integer.parseInt(parts[2].replace("+", ""));
                return 0b0010_0000 | (r << 3) | o;
            } else {
                int r = parts[2].equals("B") ? 1 : 0;
                int v = Integer.parseInt(parts[1]);

                return 0b0100_0000 | (v << 1) | r;
            }
            
        } else if (line.equals("ADD")) {
            return 0b0000_0001;
        } else if (line.equals("NOP")){
            return 0b0000_0000;
        } else if (line.equals("MUL")){
            return 0b0000_0010;
        } else if (line.equals("DIV")){
            return 0b0000_0011;
        } else if (line.equals("ZERO")){
            return 0b0000_0100;
        } else if (line.equals("NEG")){
            return 0b0000_0101;
        } else if (line.equals("POS")){
            return 0b0000_0110;
        } else if (line.equals("NZERO")){
            return 0b0000_0111;
        } else if (line.equals("EQ")){
            return 0b0000_1000;
        } else if (line.equals("LT")){
            return 0b0000_1001;
        } else if (line.equals("GT")){
            return 0b0000_1010;
        } else if (line.equals("NEQ")){
            return 0b0000_1011;
        } else if (line.equals("ALWAYS")){
            return 0b0000_1100;
        } else if (line.equals("HALT")){
            return 0b0000_1111;
        } else if (line.startsWith("PUSH ")){
            String[] parts = line.split(" ");
            int r = parts[1].equals("B") ? 1 : 0;
            
            return 0b0001_0000 | r;
            
        } else if (line.startsWith("POP ")){
            String[] parts = line.split(" ");
            int r = parts[1].equals("B") ? 1 : 0;
            
            return 0b0001_0010 | r;
        } else if (line.equals("INC")){
            return 0b0001_0110;
        } else if (line.equals("DEC")){
            return 0b0001_0111;
        } else if(line.equals("MOV A B")){
            return 0b0001_0100;
        } else if (line.equals("MOV B A")){
            return 0b0001_0101;
        } else if (line.startsWith("RTN ")){
            String[] parts = line.split(" ");
            int o = Integer.parseInt(parts[1].replace("+", ""));
            return 0b0001_1000 | o;
        } else if (line.equals("RTN")){
            return 0b0001_1000;
        } else if (line.startsWith("JMP ")){
            String[] parts = line.split(" ");
            int a = Integer.parseInt(parts[1].replace("#", ""));
            return 0b1000_0000 | a;
        } else if (line.startsWith("CALL ")){
            String[] parts = line.split(" ");
            int a = Integer.parseInt(parts[1].replace("#", ""));
            return 0b1100_0000 | a;
        }
        else {
            throw new UnsupportedOperationException("Don't know " + line);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new ProgramIterator();
    }

    private class ProgramIterator implements Iterator<Integer> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < lines.length;
        }

        @Override
        public Integer next() {
            return get(current++);
        }

    }

}
