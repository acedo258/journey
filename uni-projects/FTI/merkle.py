def esPotenciade2(n):
    """
    Indica si n es una potencia de 2 usando una comprobación bit a bit.
    """
    return n > 0 and (n & (n - 1)) == 0

def seguentPotenciade2(n):
    """
    Calcula la menor potencia de 2 mayor o igual que n.
    """
    p = 1
    while p < n:
        p *= 2
    return p

def UAB_compute_merkle_root(tx_list):
    """
    Calcula la raíz de Merkle de una lista de transacciones,
    duplicando la última hoja si el número de nodos en un nivel no es potencia de 2.
    """
    # no hi ha transaccions -> hash string buit
    if len(tx_list) == 0:
        return UAB_btc_hash("")

    # només hi ha una -> el seu hash
    if len(tx_list) == 1:
        return tx_list[0].get_hash()
    
    nivell_actual = []

    for tx in tx_list:
        nivell_actual.append(tx.get_hash())

    # si no es potencia de dos, dupliquem ultima fins que ho sigui
    if not esPotenciade2(len(nivell_actual)):
        pot2 = seguentPotenciade2(len(nivell_actual))
        while len(nivell_actual) < pot2:
            nivell_actual.append(nivell_actual[-1])

    # construim arbre
    while len(nivell_actual) > 1:
        nivell_superior = []

        # hash de 2 en 2
        for i in range(0, len(nivell_actual), 2):
            left = nivell_actual[i]
            right = nivell_actual[i+1]
            parent_hash = UAB_btc_hash(left + right)
            nivell_superior.append(parent_hash)

        nivell_actual = nivell_superior

    # ultim hash -> arrel
    merkle_root = nivell_actual[0]
    return merkle_root

def UAB_validate_inclusion_simplified(tx, merkle_root, merkle_path):
    """
    Verifica si la transacción tx está incluida en el árbol cuya raíz es merkle_root,
    recomputando la ruta de Merkle a partir de merkle_path.
    """
    # hash inicial -> hash de la transaccio
    hash_actual = tx.get_hash()

    # recorrem merkle path des de la fulla fins arrel
    for i, h in merkle_path:

        # hash del cami es fill esquerre
        if i == 1:
            parent_hash = h + hash_actual
        # hash del cami es fill dret
        else:
            parent_hash = hash_actual + h

        hash_actual = UAB_btc_hash(parent_hash)

    return hash_actual == merkle_root

def UAB_generate_merkle_path(tx, tx_list):
    """
    Genera la ruta de Merkle (lista de pares (posicion, hash_hermano)) para tx dentro de tx_list
    y devuelve la raíz de Merkle junto con ese camino.
    """
    # Comprovem  la transacció
    if tx not in tx_list:
        return (-1, "Error")

    # Acomodar longitud a potencia de 2 duplicando última transacción
    if not esPotenciade2(len(tx_list)):
        pot2 = seguentPotenciade2(len(tx_list))
        while len(tx_list) < pot2:
            tx_list.append(tx_list[-1])

    idx = tx_list.index(tx)
    nodes = [t.get_hash() for t in tx_list]
    
    merkle_path = []

    while len(nodes) > 1:
        # Determinem si el germà és a la dreta (0) o a l'esquerra (1)
        if idx % 2 == 0:
            idx_germa = idx + 1
            pos = 0  # dret (0)
        else:
            idx_germa = idx - 1
            pos = 1  # esquerre (1)
            
        # Si el germà no existeix perquè estem al final, duplicar el node
        if idx_germa >= len(nodes):
            hash_germa = nodes[idx]
        else:
            hash_germa = nodes[idx_germa]

        # Afegim el tuple (posició, hash germà) al camí
        merkle_path.append((pos, hash_germa))

        # Preparem el següent nivell
        idx //= 2
        nou_nodes = []

        for i in range(0, len(nodes), 2):
            left = nodes[i]
            if i+1 < len(nodes):
                right = nodes[i+1]
            else:
                right = nodes[i]
            nou_nodes.append(UAB_btc_hash(left + right))
            
        nodes = nou_nodes

    # Retornem arrel i camí
    return (nodes[0], merkle_path)


