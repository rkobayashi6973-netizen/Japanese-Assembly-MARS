# ============================================
# Japanese Assembly - Complete Test Program
# ============================================
# 10 Basic Instructions + 11 Unique Instructions = 21 Total
# ============================================

.data
    testValue: .word 50

.text
main:
    # ========================================
    # Basic Instructions (10 Instructions)
    # ========================================
    
    # 1. ireru - Load immediate value into register
    ireru $t0, 100          # $t0 = 100
    ireru $t1, 50           # $t1 = 50
    
    # 2. tasu - Addition
    tasu $t2, $t0, $t1      # $t2 = $t0 + $t1 = 150
    
    # 3. hiku - Subtraction
    hiku $t3, $t0, $t1      # $t3 = $t0 - $t1 = 50
    
    # 4. kakeru - Multiplication
    kakeru $t4, $t0, $t1    # $t4 = $t0 * $t1 = 5000
    
    # 5. soshite - Bitwise AND
    ireru $t5, 15           # $t5 = 0b1111
    ireru $t6, 7            # $t6 = 0b0111
    soshite $t7, $t5, $t6   # $t7 = 15 & 7 = 7
    
    # 6. matawa - Bitwise OR
    matawa $s0, $t5, $t6    # $s0 = 15 | 7 = 15
    
    # 7-8. kaku / yomu - Memory store/load
    ireru $s1, 200          # Value to store
    kaku $s1, 0($gp)        # Store to memory
    yomu $s2, 0($gp)        # Load from memory ($s2 = 200)
    
    # 9. onaji - Conditional branch (branch if equal)
    ireru $s3, 10
    ireru $s4, 10
    onaji $s3, $s4, equal_test  # $s3 == $s4, so branch
    ireru $s5, 999          # Should be skipped
    tobu after_equal
    
equal_test:
    ireru $s5, 111          # $s5 = 111 (branch successful)
    
after_equal:
    # 10. tobu - Unconditional jump (already used above)

    # ========================================
    # Unique Instructions (11 Instructions)
    # ========================================
    
    # 1. omikuji - Fortune slip (random 0-4)
    omikuji $t0             # Display random fortune
    
    # 2. sushi_roll - Sushi roll (increment by 1)
    ireru $t1, 10
    sushi_roll $t1          # $t1 = 11
    
    # 3. sushi_combo - Sushi combo (random multiplier)
    ireru $t2, 100
    sushi_combo $t3, $t2    # $t3 = 100 * (1, 2, or 3)
    
    # 4. tsunami - Tsunami (random damage)
    ireru $t4, 100
    tsunami $t4             # Random damage to $t4
    
    # 5. ninja - Ninja hide (50% chance to skip next instruction)
    ireru $t5, 0
    ninja                   # 50% chance to skip next
    ireru $t5, 1            # May be skipped
    
    # 6. sumo - Sumo match (stronger usually wins, 20% upset chance)
    ireru $t6, 200          # Wrestler A
    ireru $t7, 100          # Wrestler B
    sumo $s0, $t6, $t7      # Winner's value stored in $s0
    
    # 7. henshin - Transform (random power-up)
    ireru $s1, 50
    henshin $s2, $s1        # Random transformation
    
    # 8. onsen - Hot spring (heal +10 if $t9 is 0)
    ireru $t9, 0            # No tattoo
    ireru $s3, 80           # HP
    onsen $s3               # $s3 = 90 (healed, no tattoo)
    
    ireru $t9, 1            # Has tattoo!
    ireru $s4, 80           # HP
    onsen $s4               # $s4 = 80 (no healing, tattoo present)
    
    # 9. taiko_boost - Taiko drum boost (combo counter)
    ireru $s5, 10
    taiko_boost $s5         # $s5 = 10 * 1 (1st hit)
    ireru $s6, 10
    taiko_boost $s6         # $s6 = 10 * 2 (2nd hit)
    ireru $s7, 10
    taiko_boost $s7         # $s7 = 10 * 3 (3rd hit)
    
    # 10. katana_slash - Katana slash (random 1-4 hits damage)
    ireru $t0, 100          # HP
    ireru $t1, 10           # Damage per hit
    katana_slash $t0, $t1   # $t0 = 100 - (10 * 1~4)
    
    # 11. pachinko - Pachinko gamble
    ireru $t2, 100          # Bet amount
    pachinko $t3, $t2       # Result: 0, 200, or 1000

    # ========================================
    # Program End
    # ========================================
done:
    tobu done               # Infinite loop to end

# ============================================
# Instruction Summary
# ============================================
# Basic Instructions:
#   tasu     - Addition
#   hiku     - Subtraction
#   kakeru   - Multiplication
#   soshite  - Bitwise AND
#   matawa   - Bitwise OR
#   yomu     - Load from memory
#   kaku     - Store to memory
#   ireru    - Load immediate
#   onaji    - Branch if equal
#   tobu     - Unconditional jump
#
# Unique Instructions:
#   omikuji      - Fortune slip (random 0-4)
#   sushi_roll   - Increment (+1)
#   sushi_combo  - Random multiplier (x1, x2, x3)
#   tsunami      - Random damage
#   ninja        - 50% chance to skip next instruction
#   sumo         - Sumo match (stronger wins, 20% upset)
#   henshin      - Transform power-up
#   onsen        - Hot spring heal (+10 if $t9=0)
#   taiko_boost  - Taiko drum combo boost
#   katana_slash - Katana slash (random hits)
#   pachinko     - Pachinko gambling
# ============================================
