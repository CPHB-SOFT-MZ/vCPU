package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;

public class Machine {

    private Cpu cpu = new Cpu();
    private Memory memory = new Memory();

    public void load(Program program) {
        int index = 0;
        for (int instr : program) {
            memory.set(index++, instr);
        }
    }

    public void tick() {
        int instr = memory.get(cpu.getIp());
        if (instr == 0b0000_0000) {
            System.out.println("NOP");
            // 0000 0000  NOP
            cpu.incIp();
            // cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0001) {
            System.out.println("ADD");
            // 0000 0001 ADD A B
            System.out.println("Adding A and B = " + (cpu.getA() + cpu.getB()));
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } // ..
        else if ((instr & 0b1111_0000) == 0b0010_0000) {
            System.out.println("MOV r o");
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++

            // 0010 1 011 MOV B (=1) +3  [SP +3] // Move register B to memory position of SP with offset 3
            // 00101011 finding instruction
            //    and
            // 11110000
            // --------
            // 00100000
            // 00101011 finding offset
            //    and
            // 00000111
            // --------
            // 00000011 = 3
            // 00101011 finding register
            //    and
            // 00001000
            // --------
            // 00001000 = 8
            //    >> 3
            // 00000001 = 1
            int o = (instr & 0b0000_0111);
            int r = (instr & 0b0000_1000) >> 3;
            if (r == cpu.A) {
                memory.set(cpu.getSp() + o, cpu.getA());
            } else {
                memory.set(cpu.getSp() + o, cpu.getB());
            }
            cpu.setIp(cpu.getIp() + 1);
        }
        // MUL: A ← A*B; IP++
        else if (instr == 0b0000_0010){
            System.out.println("MUL");
            cpu.setA(cpu.getA() * cpu.getB());
            cpu.incIp();
        }
        //DIV: A ← A/B; IP++
        else if(instr == 0b0000_0011){
            cpu.setA(cpu.getA() / cpu.getB());
            cpu.incIp();
        }
        //ZERO: F ← A = 0; IP++
        else if(instr == 0b0000_0100){
            System.out.println("ZERO");
            cpu.setFlag(cpu.getA() == 0);
            cpu.incIp();
        }
        //NEG: F ← A < 0; IP++
        else if(instr == 0b0000_0101){
            System.out.println("NEG");
            cpu.setFlag(cpu.getA() < 0);
            cpu.incIp();
        }
        //POS: F ← A > 0; IP++
        else if(instr == 0b0000_0110){
            System.out.println("POS");
            cpu.setFlag(cpu.getA() > 0);
            cpu.incIp();
        }
        //NZERO: F ← A ≠ 0; IP++
        else if(instr == 0b0000_0111){
            System.out.println("NZERO");
            cpu.setFlag(cpu.getA() != 0);
            cpu.incIp();
        }
        //EQ: F ← A = B; IP++
        else if(instr == 0b0000_1000){
            System.out.println("EQ");
            cpu.setFlag(cpu.getA() == cpu.getB());
            cpu.incIp();
        }
        //LT: F ← A < B; IP++
        else if(instr == 0b0000_1001){
            System.out.println("LT");
            cpu.setFlag(cpu.getA() < cpu.getB());
            cpu.incIp();
        }
        //GT: F ← A > B; IP++
        else if(instr == 0b0000_1010){
            System.out.println("GT");
            cpu.setFlag(cpu.getA() > cpu.getB());
            cpu.incIp();
        }
        //NEQ: F ← A ≠ B; IP++
        else if(instr == 0b0000_1011){
            System.out.println("NEQ");
            cpu.setFlag(cpu.getA() != cpu.getB());
            cpu.incIp();
        }
        //ALWAYS: F ← true; IP++
        else if(instr == 0b0000_1100){
            System.out.println("ALWAYS");
            cpu.setFlag(true);
            cpu.incIp();
        }
        //HALT
        else if(instr == 0b0000_1111){
            System.out.println("Halt?");
            System.exit(0);
        }
        //PUSH r: [--SP] ← r; IP++
        //0001 000r
        else if((instr & 0b1111_1110) == 0b0001_0000){
            System.out.println("PUSH r");
            int r = instr & 0b0000_0001;
            cpu.decSp();
            if (r == cpu.A) {
                memory.set(cpu.getSp(), cpu.getA());
            } else {
                memory.set(cpu.getSp(), cpu.getB());
            }
            cpu.incIp();
        }
        //POP r: r ← [SP++]; IP++
        //0001 001r 
        else if((instr & 0b1111_1110) == 0b0001_0010){
            System.out.println("POP r");
            int r = (instr & 0b0000_0001);
            if(r == cpu.A){
                cpu.setA(memory.get(cpu.getSp()));
            } else {
                cpu.setB(memory.get(cpu.getSp()));
            }
            cpu.setSp(cpu.getSp() + 1);
            cpu.incIp();
        }
        //MOV A B: B ← A; IP++
        else if(instr == 0b0001_0100){
            System.out.println("MOV A B");
            cpu.setB(cpu.getA());
            cpu.incIp();
        }
        //MOV B A: A ← B; IP++
        else if(instr == 0b0001_0101){
            System.out.println("MOV B A");
            cpu.setA(cpu.getB());
            cpu.incIp();
        }
        //INC: A++; IP++
        else if(instr == 0b0001_0110){
            System.out.println("INC");
            cpu.setA(cpu.getA() + 1);
            cpu.incIp();
        }
        //DEC: A--; IP++
    else if(instr == 0b0001_0111){
            System.out.println("DEC");
            cpu.setA(cpu.getA() - 1);
            cpu.incIp();    
        }
        //RTN +o: IP ← [SP++]; SP += o; IP++ 
        // 0001 1ooo
        else if((instr & 0b1111_1000) == 0b0001_1000){
            System.out.println("RTN +o");
            int o = (instr & 0b0000_0111);
            cpu.setIp(memory.get(cpu.getSp()) + 1);
            cpu.setSp(cpu.getSp() + o + 1);
        }
        //MOV o r: r ← [SP + o]; IP++
        //0011 ooor
        else if((instr & 0b1111_0000) == 0b0011_0000){
            System.out.println("MOV o r");
            int r = (instr & 0b0000_0001);
            int o = (instr & 0b0000_1110) >> 1;
            System.out.println("o = " + o);
            
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp() + o));
            } else {
                cpu.setB(memory.get(cpu.getSp() + o));
            }
            cpu.incIp();
        }
        
        //MOV v r: r ← v; IP++
        //01vv vvvr
        else if((instr & 0b1100_0000) == 0b0100_0000){
            System.out.println("MOV v r");
            int v = ((instr & 0b0011_1110) >> 1);
            if(((instr & 0b0010_0000)) == 0b0010_0000){
                v = - ((instr & 0b0001_1110) >> 1);
            }
            
            System.out.println("v = " + v);
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(v);
            } else {
                cpu.setB(v);
            }
            cpu.incIp();
        }
        
        //JUMP #a: if F then IP ← a else IP++
        //10aa aaaa
        else if((instr & 0b1100_0000) == 0b1000_0000){
            System.out.println("JUMP #a");
            if(cpu.isFlag()){
                int a = instr & 0b0011_1111;
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
        }
        //CALL #a: if F then [--SP] ← IP; IP ← a else IP++
        //11aa aaaa
        else if((instr & 0b1100_0000) == 0b1100_0000){
            System.out.println("CALL #a");
            if(cpu.isFlag()){
                //Maybe decrease the Stack pointer first?
                cpu.decSp();
                memory.set(cpu.getSp(), cpu.getIp());
                cpu.setIp((instr & 0b0011_1111));
            } else {
                cpu.incIp();
            }
        }
        
    }

    public void print(PrintStream out) {
        memory.print(out);
        out.println("-------------");
        cpu.print(out);
    }

}
