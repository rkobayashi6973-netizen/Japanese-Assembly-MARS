# Japanese Assembly for MARS LE

A Japanese culture-inspired custom assembly language extension for MARS LE.

---

## Basic Instructions (10)

### tasu
| Property | Value |
|----------|-------|
| Name Meaning | add |
| Syntax | `tasu rd, rs, rt` |
| Instruction Type | R-type |
| Binary Representation | `000000 rs(5) rt(5) rd(5) 00000 100000` |
| Functionality | `rd = rs + rt` |

Add the values in registers rs and rt, and store the result in rd.

---

### hiku
| Property | Value |
|----------|-------|
| Name Meaning | subtract |
| Syntax | `hiku rd, rs, rt` |
| Instruction Type | R-type |
| Binary Representation | `000000 rs(5) rt(5) rd(5) 00000 100010` |
| Functionality | `rd = rs - rt` |

Subtract the value in register rt from rs, and store the result in rd.

---

### kakeru
| Property | Value |
|----------|-------|
| Name Meaning | multiply |
| Syntax | `kakeru rd, rs, rt` |
| Instruction Type | R-type |
| Binary Representation | `000000 rs(5) rt(5) rd(5) 00000 011000` |
| Functionality | `rd = rs * rt` |

Multiply values in rs and rt, store the result in rd.

---

### soshite
| Property | Value |
|----------|-------|
| Name Meaning | and (bitwise) |
| Syntax | `soshite rd, rs, rt` |
| Instruction Type | R-type |
| Binary Representation | `000000 rs(5) rt(5) rd(5) 00000 100100` |
| Functionality | `rd = rs AND rt` |

Perform bitwise AND on rs and rt, store result in rd.

---

### matawa
| Property | Value |
|----------|-------|
| Name Meaning | or (bitwise) |
| Syntax | `matawa rd, rs, rt` |
| Instruction Type | R-type |
| Binary Representation | `000000 rs(5) rt(5) rd(5) 00000 100101` |
| Functionality | `rd = rs OR rt` |

Perform bitwise OR on rs and rt, store result in rd.

---

### yomu
| Property | Value |
|----------|-------|
| Name Meaning | read |
| Syntax | `yomu rt, imm(rs)` |
| Instruction Type | I-type |
| Binary Representation | `100011 rs(5) rt(5) immediate(16)` |
| Functionality | `rt = Memory[rs + imm]` |

Load a word from memory address (rs + immediate) into rt.

---

### kaku
| Property | Value |
|----------|-------|
| Name Meaning | write |
| Syntax | `kaku rt, imm(rs)` |
| Instruction Type | I-type |
| Binary Representation | `101011 rs(5) rt(5) immediate(16)` |
| Functionality | `Memory[rs + imm] = rt` |

Store the word in rt to memory address (rs + immediate).

---

### ireru
| Property | Value |
|----------|-------|
| Name Meaning | insert |
| Syntax | `ireru rt, imm` |
| Instruction Type | I-type |
| Binary Representation | `001111 00000 rt(5) immediate(16)` |
| Functionality | `rt = immediate` |

Load the immediate value directly into register rt.

---

### onaji
| Property | Value |
|----------|-------|
| Name Meaning | same |
| Syntax | `onaji rs, rt, label` |
| Instruction Type | I-type |
| Binary Representation | `000100 rs(5) rt(5) offset(16)` |
| Functionality | `if (rs == rt) branch to label` |

If rs equals rt, branch to the target label.

---

### tobu
| Property | Value |
|----------|-------|
| Name Meaning | jump |
| Syntax | `tobu target` |
| Instruction Type | J-type |
| Binary Representation | `000010 address(26)` |
| Functionality | `PC = target address` |

Unconditionally jump to the target address.

---

## Unique Instructions (11)

### omikuji
| Property | Value |
|----------|-------|
| Name Meaning | fortune slip |
| Syntax | `omikuji rt` |
| Instruction Type | R-type |
| Binary Representation | `011111 00000 00000 rt(5) 00000 000001` |
| Functionality | `rt = random() % 5` |

**Results:**
- 0 = Bad Fortune...
- 1 = Fortune
- 2 = Small Fortune
- 3 = Good Fortune
- 4 = Great Fortune!

A random result from 0-4 determines your luck, like drawing a fortune slip at a shrine.

---

### sushi_roll
| Property | Value |
|----------|-------|
| Name Meaning | sushi roll |
| Syntax | `sushi_roll rt` |
| Instruction Type | R-type |
| Binary Representation | `011111 00000 00000 rt(5) 00000 000011` |
| Functionality | `rt = rt + 1` |

Increment the value by 1, like rolling sushi one piece at a time.

---

### sushi_combo
| Property | Value |
|----------|-------|
| Name Meaning | sushi combo |
| Syntax | `sushi_combo rd, rs` |
| Instruction Type | R-type |
| Binary Representation | `011111 rs(5) 00000 rd(5) 00000 000010` |
| Functionality | `rd = rs * random(1, 2, or 3)` |

**Results:**
- 1x = Regular size
- 2x = Large size
- 3x = Super size!

Mystery sushi combo with random multiplier.

---

### tsunami
| Property | Value |
|----------|-------|
| Name Meaning | tsunami |
| Syntax | `tsunami rt` |
| Instruction Type | R-type |
| Binary Representation | `011111 00000 00000 rt(5) 00000 000100` |
| Functionality | `rt = rt - random_damage` |

**Results:**
- 20% chance: No damage
- 30% chance: -10 damage
- 30% chance: -20 damage
- 10% chance: -30 damage
- 10% chance: Total destruction (0)

Tsunami disaster causes random damage to the value.

---

### ninja
| Property | Value |
|----------|-------|
| Name Meaning | ninja |
| Syntax | `ninja` |
| Instruction Type | R-type |
| Binary Representation | `011111 00000 00000 00000 00000 000101` |
| Functionality | `50% chance to skip next instruction` |

Ninja hide: 50% chance to skip the next instruction, like a ninja vanishing.

---

### sumo
| Property | Value |
|----------|-------|
| Name Meaning | sumo |
| Syntax | `sumo rd, rs, rt` |
| Instruction Type | R-type |
| Binary Representation | `011111 rs(5) rt(5) rd(5) 00000 000110` |
| Functionality | `rd = winner(rs, rt)` |

**Results:**
- 80% chance: Stronger wrestler wins
- 20% chance: Upset! Weaker wrestler wins

Sumo match between two values. The stronger usually wins, but upsets happen!

---

### henshin
| Property | Value |
|----------|-------|
| Name Meaning | transform |
| Syntax | `henshin rd, rs` |
| Instruction Type | R-type |
| Binary Representation | `011111 rs(5) 00000 rd(5) 00000 000111` |
| Functionality | `rd = transform(rs)` |

**Results:**
- Form 0: Flame Form (rs + 10)
- Form 1: Speed Form (rs × 2)
- Form 2: Shield Form (rs | 0xFF)
- Form 3: Final Form (rs × 3)

Random power-up transformation, inspired by Japanese tokusatsu heroes.

---

### onsen
| Property | Value |
|----------|-------|
| Name Meaning | hot spring |
| Syntax | `onsen rt` |
| Instruction Type | R-type |
| Binary Representation | `011111 00000 00000 rt(5) 00000 001000` |
| Functionality | `if ($t9 == 0) rt = rt + 10` |

**Condition:** $t9 must be 0 (no tattoo)

Onsen heal: Add 10 HP if $t9 is 0. In Japan, tattoos are not allowed in most hot springs!

---

### taiko_boost
| Property | Value |
|----------|-------|
| Name Meaning | taiko drum |
| Syntax | `taiko_boost rt` |
| Instruction Type | R-type |
| Binary Representation | `011111 00000 00000 rt(5) 00000 001001` |
| Functionality | `rt = rt * combo_counter++` |

Taiko drum boost: Each call increases the combo counter. More drums = more power!

---

### katana_slash
| Property | Value |
|----------|-------|
| Name Meaning | katana slash |
| Syntax | `katana_slash rd, rs` |
| Instruction Type | R-type |
| Binary Representation | `011111 rs(5) 00000 rd(5) 00000 001010` |
| Functionality | `rd = rd - (rs * random(1-4))` |

Katana slash: rd takes damage of rs × random hits (1-4). Swift samurai strikes!

---

### pachinko
| Property | Value |
|----------|-------|
| Name Meaning | pachinko |
| Syntax | `pachinko rd, rs` |
| Instruction Type | R-type |
| Binary Representation | `011111 rs(5) 00000 rd(5) 00000 001011` |
| Functionality | `rd = gamble(rs)` |

**Results:**
- 10% chance: JACKPOT 777! (rs × 10)
- 40% chance: Small win (rs × 2)
- 50% chance: Lose everything (0)

Pachinko gambling: High risk, high reward!

---

## How to Run/Test

1. **Compile** `JapaneseAssembly.java` into a JAR file
2. **Place** the JAR file in `mars/mips/instructions/customlangs/`
3. **Launch** MARS LE
4. **Select** Settings → Custom Assembly Language → "Japan Assembly"
5. **Open** `JapaneseAssemblyTest.asm` and assemble/run

## Files

- `JapaneseAssembly.java` - Main source code for the custom language
- `JapaneseAssemblyTest.asm` - Test program demonstrating all instructions
- `JapanAssembly.jar` - Compiled JAR file
