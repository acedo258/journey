def UAB_generate_ElGamal_keys(nBits):
"""
Genera un parell de claus d'ElGamal de mida nBits (clau pública i privada).
"""
   
    #num primer entre 2^nBits i 2^(nBits-1)
    p = random_prime(2**nBits-1, None, 2**(nBits-1))   #//nose si None o False


    alpha = primitive_root(p)
    d = randint(2, p-2)
    beta = power_mod(alpha, d, p) #//alpha^d mod p


    k_pub = [p, alpha, beta]
    k_priv = [p, alpha, d]


    return [k_pub, k_priv]




def UAB_ElGamal_sign(k_priv, m, h):
"""
Calcula la signatura digital ElGamal del missatge m usant la clau privada k_priv i el nonce h.

"""

    p, alfa, d = k_priv


    if gcd(h, p - 1) != 1:
        return (None, None)


    r = power_mod(alfa, h, p)
    h_inv = inverse_mod(h, p - 1)
    s = ((m - r * d) * h_inv) % (p - 1)


    return (r, s)




def UAB_ElGamal_verify(sig, k_pub, m):
"""
Verifica si la signatura sig és vàlida per al missatge m amb la clau pública k_pub en ElGamal.
"""

    r, s = sig
    p, alfa, beta = k_pub
    
    if r < 1 or r > p - 1:
        return False


    v1 = (power_mod(beta, r, p) * power_mod(r, s, p)) % p
    v2 = power_mod(alfa, m, p)


    return v1 == v2




def UAB_solve_mod_eq(a, b, m):
"""

Resol l'equació modular b*x ≡ a (mod m) retornant totes les solucions, o [-1] si no n'hi ha.

Volem resoldre b⋅x ≡ a (mod m) 
1 → Calculem el màxim comú divisor de b i m
2 → Si a no és múltiple de g, no hi ha solució → retornem -1
3 → Simplifiquem l’equació dividint per g. Calculem l’invers =  multiplicant +modul
"""
    g = gcd(b, m)
    if a % g != 0:
        return [-1]
    
    a_ = a // g
    b_ = b // g
    m_ = m // g


    b_inv = inverse_mod(b_, m_)
    x0 = (a_ * b_inv) % m_
    solutions = [(x0 + k * m_) % m for k in range(g)]
    solutions = sorted(solutions) #para que salga en orden
    return 
    
    
def UAB_extract_private_key(k_pub, m1, sig1, m2, sig2):
"""
Extreu la clau privada d'ElGamal aprofitant dues signatures (sig1, sig2) del mateix nonce sobre m1 i m2.
"""
    if m1 == m2:
        return -1
    
    p, alpha, beta = k_pub
    r1, s1 = sig1
    r2, s2 = sig2


    if r1 != r2:
        return -1


    r = r1


    dmensajes = (m1 - m2) % (p-1)
    dfirmas = (s1 - s2) % (p-1)


    posibles_h = UAB_solve_mod_eq(dmensajes, dfirmas, (p-1))
    #si hay h candidatas las miramos --> si es -1 esta mal
    if posibles_h == [-1]:
        return -1
    
    for h in posibles_h:
           x= (m1 - (s1 * h) % (p-1)) % (p-1)


        posibles_d = UAB_solve_mod_eq(x, r % (p-1), (p-1))


        if posibles_d != [-1]:


            for d in posibles_d:


                # validamos la clave privada candidata
                if pow(alpha, d, p) == beta:
                    return (p, alpha, d)


    # Si nunguno funciona
    return -1
