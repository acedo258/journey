def UAB_E(skey, m):
    """
    Cifra un entero m con una clave simétrica skey construyendo una subclave de 16 bits
    (repitiendo cada uno de los 4 bits menos significativos) y aplicando XOR.
    """
    num = bin(skey)
    k = num[-4:]   # k3 k2 k1 k0

    # clau de 16 bits replicant cada bit 4 cops
    k_bin = ""
    for bit in k:
        k_bin += bit * 4

    k_16 = int(k_bin, 2)

    c = UAB_xor(m, k_16)
    return c


def UAB_generate_RSA_key_pair():
    """
    Genera un par de claves RSA pequeñas: devuelve (n, e, d) con n = p*q y e, d inversos módulo φ(n).
    """
    # dos primers de 8 bits
    p = random_prime(2**prime_bits, False, 2**(prime_bits-1))
    q = random_prime(2**prime_bits, False, 2**(prime_bits-1))

    n = p * q
    phi = (p - 1) * (q - 1)

    e = randint(2, phi - 1)
    while gcd(e, phi) != 1:  # e coprimer amb phi
        e = randint(2, phi - 1)

    d = inverse_mod(e, phi)   # clau privada

    return (n, e, d)


def UAB_generate_RSA_public_key():
    """
    Genera solo una clave pública RSA pequeña: devuelve el par (n, e).
    """
    p = random_prime(2**prime_bits, False, 2**(prime_bits-1))
    q = random_prime(2**prime_bits, False, 2**(prime_bits-1))

    n = p * q
    phi = (p - 1) * (q - 1)

    e = randint(2, phi - 1)
    while gcd(e, phi) != 1:
        e = randint(2, phi - 1)

    return (n, e)


def UAB_f(n, e, x):
    """
    Función de cifrado RSA adaptada al esquema del enunciado: cifra x usando la clave pública (n, e),
    fragmentando en q y r para mantener el resultado dentro de 16 bits si es posible.
    """
    q = x // n
    r = x % n

    # comprobamos si el resultado cabe en 16 bits
    if (q + 1) * n <= 2**bits:
        fr = power_mod(r, e, n)        # f(r) = r^e mod n
        return int(q * n + fr)         # g(x) = q*n + f(r)
    else:
        # si es más grande que 16 bits → RSA directo
        return int(power_mod(x, e, n))


def UAB_f_inv(n, d, y):
    """
    Inversa de UAB_f: descifra y usando la clave privada (n, d) y reconstruye el entero original.
    """
    q = y // n
    r = y % n

    # miramos si entra en 16 bits
    if (q + 1) * n <= 2**bits:
        fr = power_mod(r, d, n)        # f^{-1}(r) = r^d mod n
        return int(q * n + fr)
    else:
        # f^{-1}(m) = m^d mod n
        return int(power_mod(y, d, n))


class Scenario_struct():
    """
    Estructura que contiene todos los parámetros necesarios para generar una firma de anillo:
    claves del signante, lista de claves públicas, listas de x_i y y_i.
    """
    # Initialize
    def __init__(self, length, signer_pos):
        # Ring length
        self.length = length
        
        # Signer position
        if signer_pos < 1:
            print("ERROR: Position can't be lower than 1")
        self.signer_pos = signer_pos

        # Signer's public key (signer_n,signer_e) and private key (signer_d)
        self.signer_n, self.signer_e, self.signer_d = UAB_generate_RSA_key_pair()
        
        # List of tuples (n,e) of all ring members' public keys [(n1,e1),(n2,e2)...].
        # This list has to include the signer's public key in the position='signer_pos'
        self.publicKeyList = []
        for i in range(1, length + 1):
            if i == self.signer_pos:
                self.publicKeyList.append((self.signer_n, self.signer_e))
            else:
                n_i, e_i = UAB_generate_RSA_public_key()
                self.publicKeyList.append((n_i, e_i))
                
        # List of 'x' values assigned to signers. It must be initialized with 'None' in the signer's position.
        self.xList = []
        for i in range(1, length + 1):
            if i == self.signer_pos:
                self.xList.append(None)
            else:
                self.xList.append(randint(0, 2**bits - 1))

        '''
        # Test values → PARA PROVAR UAB_solve_C DESCOMENTA
        self.signer_n,self.signer_e,self.signer_d = 39407,26077,27013
        self.publicKeyList=[(28907,18541),(41917,22491),(39407,26077),(32743,17539)]
        self.xList=[25816,11546,None,28447]
        '''
        
        # List of 'y' values assigned to signers. It must be initialized with 'None' in the signer's position.
        self.yList = []
        for i in range(1, length + 1):
            if i == self.signer_pos:
                self.yList.append(None)
            else:
                n_i, e_i = self.publicKeyList[i - 1]
                self.yList.append(UAB_f(n_i, e_i, self.xList[i - 1]))
                
    def printme(self):
        """
        Imprime por pantalla todos los parámetros del escenario de firma de anillo.
        """
        print("Longitud anell =", self.length, "Posició signant =", self.signer_pos)
        print("Clau pública n =", self.signer_n, " e=", self.signer_e, " d=", self.signer_d)
        print("Llista pubKeys =", self.publicKeyList)
        print("Llista xs =", self.xList)
        print("Llista ys =", self.yList)



def UAB_solve_C(k, v, sc_struct):
    """
    Dado k, v y el escenario sc_struct, calcula el valor y del signante que hace que
    la función de combinación C_{k,v}(y1,...,yr) devuelva v.
    """
    L = sc_struct.length      # tamaño del anillo
    s = sc_struct.signer_pos  # posición del signant
    yList = sc_struct.yList   # lista y_i (None en la del signant)

    # z empieza en v
    z = int(v)

    # empezamos por el miembro siguiente al signant
    i = s % L

    # recorremos todo el anillo excepto el signant
    while (i + 1) != s:
        yi = yList[i]
        if yi is not None:
            z = UAB_xor(UAB_E(k, z), yi)
        i = (i + 1) % L

    # ys = Ek(zs) ⊕ v
    y_signer = UAB_xor(UAB_E(k, z), int(v))
    return int(y_signer)


def UAB_sign_ring(m, v, sc_struct):
    """
    Genera una firma de anillo del mensaje m con valor inicial v y el escenario sc_struct,
    construyendo un objeto Sigma_struct con las x_i completas (incluyendo la del signante).
    """
    k = int(m)                               # 1. k = m 
    y_signer = UAB_solve_C(k, v, sc_struct)  # 2. Calcular y_s que cierra el anillo

    n_s = sc_struct.signer_n                 # 3. Clave privada del signant
    d_s = sc_struct.signer_d
    x_signer = UAB_f_inv(n_s, d_s, y_signer)  # 4. x_s tal que g(x_s) = y_s

    idx = sc_struct.signer_pos - 1           # 5. Posición 0-based del signant
    sc_struct.xList[idx] = int(x_signer)     # 6. Completar xList
    sc_struct.yList[idx] = int(y_signer)     # 7. Completar yList

    sigma = Sigma_struct(sc_struct.publicKeyList, int(v), sc_struct.xList)  # 8. Objeto firma
    return sigma


def UAB_verify_ring_signature(m, sigma_struct):
    """
    Verifica una firma de anillo sobre el mensaje m recomputando y_i y aplicando la
    función de combinación C_{k,v}; devuelve True si el z final coincide con v.
    """
    k = int(m)                               # k = m
    v = int(sigma_struct.v_inicial)         # Valor inicial de la firma
    pub_keys = sigma_struct.pub_keys_list   # Claves públicas del anillo
    x_list = sigma_struct.x_list            # Lista de x_i de la firma
    L = len(pub_keys)                       # Tamaño del anillo

    # Recalcular y_list
    y_list = []
    for i in range(L):
        n_i, e_i = pub_keys[i]              # Clave pública del miembro i
        x_i = x_list[i]                     # x_i de la firma
        y_i = UAB_f(n_i, e_i, x_i)          # y_i = g_i(x_i)
        y_list.append(int(y_i))

    # Funció de combinació C_{k,v} aplicada a tot l'anell
    z = v                                   # z empieza en v
    for i in range(L):
        z = UAB_xor(UAB_E(k, z), y_list[i]) # z ← E_k(z) ⊕ y_i

    # És vàlida si z = v
    return z == v


