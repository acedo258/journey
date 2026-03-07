def UAB_right_shift(n, v):
    """
    Desplaza circularmente la lista v n posiciones a la derecha (shift cíclico sobre GF(2)).
    """
    novaLlista = []
    if not v:  # llista buida, retornar com està
        novaLlista = v
        return novaLlista

    lon = len(v)
    n = n % lon  # nomes importa mod perque una volta sencera no fa res

    if n == 0:  # n era multiple per tant la llista queda igual (nomes ha fet voltes senceres)
        novaLlista = v
    else:
        novaLlista = v[-n:] + v[:-n]  # posar els ultims s elements davant de la resta

    return novaLlista


def UAB_code_is_cyclic(M):
    """
    Comprueba si el código definido por las palabras de M es un código cíclico binario.
    """
    if len(M) == 0:  # si el codi esta buit es ciclic
        return True

    n = len(M[0])

    for w in M:
        if len(w) != n:  # si una paraula no te mateixa lon no es ciclic
            return False
        rotada = UAB_right_shift(1, list(w))
        v = vector(GF(2), rotada)
        if v not in M:
            return False

    return True


def UAB_Generator_of_length_n(gx, n, k):
    """
    Determina si gx es un polinomio generador de un código cíclico binario de longitud n y dimensión k.
    """
    nx = x**n + 1

    # Condición 1: gx debe dividir a x^n + 1
    divisible = (nx % gx == 0)

    # Condición 2: grado de gx debe ser n - k
    grau = (gx.degree() == n - k)

    return divisible and grau


def UAB_gen_matrix(gx, n):
    """
    Calcula una matriz generadora de un código cíclico binario de longitud n a partir del polinomio gx.
    """
    # primero de todo añadimos 0’s al polinomio hasta llegar a longitud n
    g_list = gx.list()  # añadimos el principio

    i = n - len(g_list)  # calculamos cuantos 0’s hay que añadir + los guardamos
    g_list = g_list + [0] * i

    # Calcular k , asi sabemos las veces que tiene que hacer shift
    k = n - gx.degree()

    G = []  # lista donde lo guardademos todo

    # Primera fila es g(x)
    fila = g_list
    G.append(fila)

    # hacemos k-1 shift y vamos guardando
    for i in range(1, k):
        fila = UAB_right_shift(1, fila)
        G.append(fila)

    # Convertir a matriz sobre F2
    return matrix(F2, G)

def UAB_con_matrix(gx, n):
    """
    Calcula el polinomio de control h(x) y la matriz de control H del código cíclico binario de longitud n.
    """
    # Polinomio x^n + 1
    nx = x**n + 1

    # Polinomio de control
    hx = nx // gx

    # Polinomi reciproc para hacer la matriu de control
    hx_list = list(reversed(hx.list()))
    hx_reciproc = Z2X(hx_list)

    # Generamos la matriz de control H
    H = UAB_gen_matrix(hx_reciproc, n)

    return hx, H



def UAB_check_codeword_hx(gx, n, v):
    """
    Comprueba si la palabra v pertenece al código usando que h(x)*v(x) ≡ 0 (mod x^n+1).
    """
    hx, h = UAB_con_matrix(gx, n)
    hx1 = Z2X(hx)
    v1 = Z2X(v)
    res = hx1 * v1
    nx = x**n + 1
    r = res % nx
    if r == 0:
        return True
    else:
        return False


def UAB_check_codeword_H(gx, n, v):
    """
    Comprueba si v es palabra-código calculando la síndrome H·v^T y verificando que sea el vector cero.
    """
    vT = Matrix(GF(2), v).transpose()
    hx, h = UAB_con_matrix(gx, n)
    if (h * vT) == 0:
        return True
    else:
        return False



