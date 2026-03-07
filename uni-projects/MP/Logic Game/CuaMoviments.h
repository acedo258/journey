#pragma once

#include <iostream>
#include "Posicio.h"

using namespace std;

struct Pas {
    Posicio inicial; // Posicio de inici del movimient
    Posicio final;   // Posicio de desti del movimient
};

struct Node {
    Pas val;        //(un movimiento)
    Node* next;     

    // Constructor 
    Node(Pas value) {
        val = value;
        next = nullptr;
    }
};

// Clase cola de llista
class CuaMoviments {
private:
    Node* primer = nullptr; 
    Node* ultim = nullptr;  

public:
    // Destructor: 
    ~CuaMoviments() {
        while (!buida()) {
            treu();
        }
    }

    
    bool buida() const {
        return primer == nullptr;
    }

    // Añade un nuevo movimiento al final de la cola
    void afegeix(Pas v) {
        Node* newNode = new Node(v); // Crea un nuevo nodo amb el movimient
        if (buida()) {
            // Si la cola está vacía, el nou node es el primero y el último
            primer = ultim = newNode;
        }
        else {
            // Enlaza el nou node al final + actualiza el puntero ultim
            ultim->next = newNode;
            ultim = newNode;
        }
    }

    // Elimina el primer movimiento de la cola
    void treu() {
        if (!buida()) { 
            Node* tmp = primer;      
            primer = primer->next;   
            if (primer == nullptr) { 
                ultim = nullptr;
            }
            delete tmp; // Llibera memoria !!!
            
        }
    }

    // Devuelve el primer movimiento de la cola sin eliminar
    Pas first() const {
        // Si no hay nada en la cola, devolvemos un movimiento vacío
        if (primer == nullptr) {
            Pas movimentBuit; // Creamos un Pas vacío
            movimentBuit.inicial = Posicio(); // Posición inicial vacía
            movimentBuit.final = Posicio();   // Posición final vacía
            return movimentBuit;
        }
        // Si hay algo --> RETURN
        return primer->val;
    }
};