package mars.mips.instructions.customlangs;

import mars.simulator.*;
import mars.mips.hardware.*;
import mars.*;
import mars.util.*;
import mars.mips.instructions.*;
import java.util.Random;

public class JapaneseAssembly extends CustomAssembly{
    private static Random random = new Random();
    private static int taikoComboCounter = 0; 

    @Override
    public String getName(){
        return "Japan Assembly";
    }

    @Override
    public String getDescription(){
        return "A Japanese culture-inspired assembly language";
    }

    @Override
    protected void populate(){

    // 10 Basic Instructions
    instructionList.add(
        new BasicInstruction("tasu $t1, $t2, $t3",
        "Add the value in $t2 and $t3, and store the result in $t1",
        BasicInstructionFormat.R_FORMAT,
        "000000 sssss ttttt fffff 00000 100000",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                // get the value in the $t2
                int v1 = RegisterFile.getValue(operands[1]); // the value in $t2
                int v2 = RegisterFile.getValue(operands[2]); // the value in $t3
                int sum = v1 + v2;
                RegisterFile.updateRegister(operands[0], sum); // store the sum into the $t1
            }
        }));

    instructionList.add(
        new BasicInstruction("hiku $t1, $t2, $t3",
        "subtract $t3 from $t2, and store the result in $t1",
        BasicInstructionFormat.R_FORMAT,
        "000000 sssss ttttt fffff 00000 100010",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int v1 = RegisterFile.getValue(operands[1]); 
                int v2 = RegisterFile.getValue(operands[2]);
                int difference = v1 - v2;
                RegisterFile.updateRegister(operands[0], difference);
            }
        }));
    
    instructionList.add(
        new BasicInstruction("kakeru $t1, $t2, $t3",
        "multiply $t2 and $t3, store the result in $t1",
        BasicInstructionFormat.R_FORMAT,
        "000000 sssss ttttt fffff 00000 011000",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int v1 = RegisterFile.getValue(operands[1]);
                int v2 = RegisterFile.getValue(operands[2]);
                int product = v1 * v2;
                RegisterFile.updateRegister(operands[0], product);
            }
        }));

     instructionList.add(
        new BasicInstruction("soshite $t1, $t2, $t3",
        "perform bitwise AND on $t2 and $t3, store result in $t1",
        BasicInstructionFormat.R_FORMAT,
        "000000 sssss ttttt fffff 00000 100100",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int v1 = RegisterFile.getValue(operands[1]);
                int v2 = RegisterFile.getValue(operands[2]);
                int bitsum = v1 & v2; 
                RegisterFile.updateRegister(operands[0], bitsum);
            }
        }));
    
    instructionList.add(
        new BasicInstruction("matawa $t1, $t2, $t3",
        "perform bitwise OR on $t2 and $t3, store result in $t1",
        BasicInstructionFormat.R_FORMAT,
        "000000 sssss ttttt fffff 00000 100101",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int v1 = RegisterFile.getValue(operands[1]);
                int v2 = RegisterFile.getValue(operands[2]);
                int bitdifference = v1 | v2;
                RegisterFile.updateRegister(operands[0], bitdifference);
            }
        }));

    instructionList.add(
        new BasicInstruction("yomu $t1, imm($t2)",
        "load a data from memory address($t2 + imm) into $t1",
        BasicInstructionFormat.I_FORMAT,
        "100011 ttttt fffff ssssssssssssssss",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                // memory access requires try-catch
                try { 
                    int rs = RegisterFile.getValue(operands[2]); // the value in $t2
                    int immediate = operands[1]; 
                    int address = rs + immediate;
                    // get value from the memory 
                    int data = Globals.memory.getWord(address); // the value in the address 
                    RegisterFile.updateRegister(operands[0], data); // operands[0]= $t1 <- deta
                } catch (AddressErrorException e) {
                    throw new ProcessingException(statement, e);
                }  
            }
        }));

    instructionList.add(
        new BasicInstruction("kaku $t1, imm($t2)",
        "store the data in $t1 to memory address($t2 + imm)",
        BasicInstructionFormat.I_FORMAT,
        "101011 ttttt fffff ssssssssssssssss",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                try {  
                    int immediate = operands[1]; // imm
                    int rt = RegisterFile.getValue(operands[0]); // the value in $t1
                    int rs = RegisterFile.getValue(operands[2]); // the value in $t2
                    int address = rs + immediate; // memory address
                    Globals.memory.setWord(address, rt); // store rt into the address
                } catch (AddressErrorException e) {
                    throw new ProcessingException(statement, e);
                }  
            }
        }));

    instructionList.add(
        new BasicInstruction("ireru $t1, -100",
        "load the immediate value directly into register $t1",
        BasicInstructionFormat.I_FORMAT,
        "001111 00000 fffff ssssssssssssssss",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                RegisterFile.updateRegister(operands[0], operands[1]); // operands[0] = St1, opetands[1] = imm(number)
            }
        }));

    instructionList.add(
        new BasicInstruction("onaji $t1, $t2, label",
        "if $t1 equals $t2, branch to the target label",
        BasicInstructionFormat.I_FORMAT,
        "000100 ttttt fffff ssssssssssssssss",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int rs = RegisterFile.getValue(operands[0]); // the value in $t1
                int rt = RegisterFile.getValue(operands[1]); // the value in $t2
                int offset = operands[2]; //　offset = the distance til Label
                if (rs == rt){
                    Globals.instructionSet.processBranch(offset);
                }
            }   
        }));

    instructionList.add(
        new BasicInstruction("tobu label",
        "Unconditionally jump to the target label",
        BasicInstructionFormat.J_FORMAT,
        "000010 ffffffffffffffffffffffffff",
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                Globals.instructionSet.processJump(
                    (RegisterFile.getProgramCounter() & 0xF0000000) | (operands[0] << 2));
            }   
        }));

    // 10 Unique Instructions
    instructionList.add(
        new BasicInstruction("omikuji $t1", 
        "A random result from 0-4 determines your luck, like drawing a fortune slip at a shrine.", 
        BasicInstructionFormat.R_FORMAT, 
        "011111 00000 00000 fffff 00000 000001", 
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int fortune = random.nextInt(5); // 0, 1, 2, 3, 4
                RegisterFile.updateRegister(operands[0], fortune); // the vallue in $t1 is now 0/1/2/3/4

                if (fortune == 4){
                    System.out.println("Great Fortune!");
                } else if (fortune == 3){
                    System.out.println("Good Fortune!");
                } else if (fortune == 2){
                    System.out.println("Small Fortune");
                } else if (fortune == 1){
                    System.out.println("Fortune");
                } else {
                    System.out.println("bad Fortune...");
                }
            }
        }));

    instructionList.add(
        new BasicInstruction("sushi_roll $t1", 
        "increment the value by 1, like rolling sushi", 
        BasicInstructionFormat.R_FORMAT, 
        "011111 00000 00000 fffff 00000 000011", 
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int value = RegisterFile.getValue(operands[0]);
                RegisterFile.updateRegister(operands[0], value + 1); 
            }
        }));

    instructionList.add(
        new BasicInstruction("sushi_combo $t1, $t2", 
        "Mystery sushi combo: $t1 = $t2 * random multiplier (1x=regular, 2x=large, 3x=super size!)", 
        BasicInstructionFormat.R_FORMAT, 
        "011111 sssss 00000 fffff 00000 000010", 
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int size = random.nextInt(3); // 0, 1, 2
                int value = RegisterFile.getValue(operands[1]);

                if (size == 0){
                    RegisterFile.updateRegister(operands[0], value);
                } else if (size == 1){
                    RegisterFile.updateRegister(operands[0], value*2);
                } else {
                    RegisterFile.updateRegister(operands[0], value*3);
                } 
            }
        }));  

    instructionList.add(
        new BasicInstruction("tsunami $t1", 
        "Tsunami disaster: random damage to the value in $t1", 
        BasicInstructionFormat.R_FORMAT, 
        "011111 00000 00000 fffff 00000 000100", 
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int luck = random.nextInt(10); // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
                int value = RegisterFile.getValue(operands[0]);
                if (luck  < 2){ 
                    value = value;
                } else if (luck < 5){
                    value = value - 10; 
                } else if (luck < 8){
                    value = value - 20; 
                } else if (luck== 8){
                    value = value - 30;
                } else {
                    value = 0; 
                }
                RegisterFile.updateRegister(operands[0], value);
            }
        }));

    instructionList.add(
        new BasicInstruction("ninja", 
        "Ninja hide: 50% chance to skip the next instruction", 
        BasicInstructionFormat.R_FORMAT, 
        "011111 00000 00000 00000 00000 000101", 
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int hide = random.nextInt(2); // 0, 1
                int pc = RegisterFile.getProgramCounter();
                if (hide == 1){
                    RegisterFile.setProgramCounter(pc + 4);
                } 
            }
        }));


    instructionList.add(
        new BasicInstruction("sumo $t1, $t2, $t3", 
        "Sumo match: stronger usually wins, but 20% chance of upset", 
        BasicInstructionFormat.R_FORMAT, 
        "011111 sssss ttttt fffff 00000 000110", 
        new SimulationCode(){
            public void simulate(ProgramStatement statement) throws ProcessingException {
                int[] operands = statement.getOperands();
                int v1 = RegisterFile.getValue(operands[1]); // the value in $t2
                int v2 = RegisterFile.getValue(operands[2]); // the value in $t3
                int luck = random.nextInt(10);
                int winner; 

                if (v1 > v2 && luck < 8){
                    winner = v1; 
                } else if (v1 < v2 && luck < 8){
                    winner = v2;
                } else if ( v1 == v2 && luck < 5){
                    winner = v1;
                } else {
                    winner = v2; 
                }
                RegisterFile.updateRegister(operands[0], winner);
            }
        }));

    instructionList.add(
        new BasicInstruction("henshin $t1, $t2", 
            "Henshin transform: random power-up form (Flame +10, Speed x2, Shield |0xFF, Final x3)", 
            BasicInstructionFormat.R_FORMAT, 
            "011111 sssss 00000 fffff 00000 000111", 
            new SimulationCode(){
                public void simulate(ProgramStatement statement) throws ProcessingException {
                    int[] operands = statement.getOperands();
                    int value = RegisterFile.getValue(operands[1]);
                    int form = random.nextInt(4);
                    int result;
                    
                    if (form == 0){
                        result = value + 10;     // FLAME FORM
                    } else if (form == 1){
                        result = value * 2;      // SPEED FORM
                    } else if (form == 2){
                        result = value | 0xFF;   // SHIELD FORM
                    } else {
                        result = value * 3;      // FINAL FORM
                    }
                    
                    RegisterFile.updateRegister(operands[0], result);
                }
            }));

    instructionList.add(
        new BasicInstruction("onsen $t1", 
            "Onsen heal: add 10 HP if $t9 is 0 (no tattoo). Tattoos not allowed!", 
            BasicInstructionFormat.R_FORMAT, 
            "011111 00000 00000 fffff 00000 001000", 
            new SimulationCode(){
                public void simulate(ProgramStatement statement) throws ProcessingException {
                    int[] operands = statement.getOperands();
                    int tattoo = RegisterFile.getValue(25); // $t9 = register 25
                    int hp = RegisterFile.getValue(operands[0]);
                    
                    if (tattoo == 0){
                        hp = hp + 10; 
                    }
                    
                    RegisterFile.updateRegister(operands[0], hp);
                }
            }));

    instructionList.add(
        new BasicInstruction("taiko_boost $t1", 
            "Taiko drum boost: multiply by combo counter. More drums = more power!", 
            BasicInstructionFormat.R_FORMAT, 
            "011111 00000 00000 fffff 00000 001001", 
            new SimulationCode(){
                public void simulate(ProgramStatement statement) throws ProcessingException {
                    int[] operands = statement.getOperands();
                    taikoComboCounter++;
                    int value = RegisterFile.getValue(operands[0]);
                    RegisterFile.updateRegister(operands[0], value * taikoComboCounter);
                }
            }));

    instructionList.add(
        new BasicInstruction("katana_slash $t1, $t2", 
            "Katana slash: $t1 takes damage of $t2 x random hits (1-4)", 
            BasicInstructionFormat.R_FORMAT, 
            "011111 sssss 00000 fffff 00000 001010", 
            new SimulationCode(){
                public void simulate(ProgramStatement statement) throws ProcessingException {
                    int[] operands = statement.getOperands();
                    int hp = RegisterFile.getValue(operands[0]);
                    int damage = RegisterFile.getValue(operands[1]);
                    int hits = random.nextInt(4) + 1; // 1, 2, 3, 4
                    int totalDamage = damage * hits;
                    RegisterFile.updateRegister(operands[0], hp - totalDamage);
                }
            }));

    instructionList.add(
        new BasicInstruction("pachinko $t1, $t2", 
            "Pachinko gamble: 10% jackpot (x10), 40% small win (x2), 50% lose all!", 
            BasicInstructionFormat.R_FORMAT, 
            "011111 sssss 00000 fffff 00000 001011", 
            new SimulationCode(){
                public void simulate(ProgramStatement statement) throws ProcessingException {
                    int[] operands = statement.getOperands();
                    int bet = RegisterFile.getValue(operands[1]);
                    int luck = random.nextInt(10);
                    int result;
                    
                    if (luck == 7){
                        result = bet * 10;   // JACKPOT 777!
                    } else if (luck >= 5){
                        result = bet * 2;    // Small win
                    } else {
                        result = 0;          // Lose everything...
                    }
                    
                    RegisterFile.updateRegister(operands[0], result);
                }
            }));
    }   
}