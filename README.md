# Japanese Assembly for MARS LE

A Japanese culture-inspired custom assembly language extension for MARS LE.

## Implemented Instructions

### Basic Instructions (10)
| Instruction | Description | Usage |
|-------------|-------------|-------|
| `tasu` | Addition | `tasu $t1, $t2, $t3` → $t1 = $t2 + $t3 |
| `hiku` | Subtraction | `hiku $t1, $t2, $t3` → $t1 = $t2 - $t3 |
| `kakeru` | Multiplication | `kakeru $t1, $t2, $t3` → $t1 = $t2 * $t3 |
| `soshite` | Bitwise AND | `soshite $t1, $t2, $t3` → $t1 = $t2 & $t3 |
| `matawa` | Bitwise OR | `matawa $t1, $t2, $t3` → $t1 = $t2 \| $t3 |
| `yomu` | Load from memory | `yomu $t1, offset($t2)` |
| `kaku` | Store to memory | `kaku $t1, offset($t2)` |
| `ireru` | Load immediate | `ireru $t1, 100` → $t1 = 100 |
| `onaji` | Branch if equal | `onaji $t1, $t2, label` |
| `tobu` | Unconditional jump | `tobu label` |

### Unique Instructions (11)
| Instruction | Description | Usage |
|-------------|-------------|-------|
| `omikuji` | Fortune slip (random 0-4) | `omikuji $t1` |
| `sushi_roll` | Increment by 1 | `sushi_roll $t1` |
| `sushi_combo` | Random multiplier (x1, x2, x3) | `sushi_combo $t1, $t2` |
| `tsunami` | Random damage | `tsunami $t1` |
| `ninja` | 50% chance to skip next instruction | `ninja` |
| `sumo` | Sumo match (stronger wins, 20% upset) | `sumo $t1, $t2, $t3` |
| `henshin` | Transform power-up | `henshin $t1, $t2` |
| `onsen` | Hot spring heal (+10 if $t9=0) | `onsen $t1` |
| `taiko_boost` | Taiko drum combo boost | `taiko_boost $t1` |
| `katana_slash` | Katana slash (random 1-4 hits) | `katana_slash $t1, $t2` |
| `pachinko` | Pachinko gambling | `pachinko $t1, $t2` |

## How to Run/Test

1. **Compile** `JapaneseAssembly.java` into a JAR file
2. **Place** the JAR file in `mars/mips/instructions/customlangs/`
3. **Launch** MARS LE
4. **Select** Settings → Custom Assembly Language → "Japan Assembly"
5. **Open** `JapaneseAssemblyTest.asm` and assemble/run

## Files

- `JapaneseAssembly.java` - Main source code for the custom language
- `JapaneseAssemblyTest.asm` - Test program demonstrating all instructions
