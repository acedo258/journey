#include "Fitxa.h"
#include "Moviment.h"
#include "Posicio.h"
#include "tauler.hpp"
#include "GraphicManager.h"
#include "info_joc.hpp"

// Dibuixa una fitxa al tauler gràfic
void Fitxa::visualitza(Posicio p) const {
    // La columna representa la coordenada horitzontal del tauler.
    int j = p.getColumna();
    // La fila representa la coordenada vertical del tauler.
    int i = p.getFila();

    int posX = POS_X_TAULER + CASELLA_INICIAL_X + ((j)*AMPLADA_CASELLA);
    int posY = POS_Y_TAULER + CASELLA_INICIAL_Y + ((i)*ALCADA_CASELLA);

    if (getType() == Type::Normal)
    {
        if (getColor() == Color::Black)
        {
            // Dibuixa un sprite de fitxa negra a les coordenades (posX, posY).
            GraphicManager::getInstance()->drawSprite(GRAFIC_FITXA_NEGRA, posX, posY);
        }
        else if (getColor() == Color::White)
        {
            // Dibuixa un sprite de fitxa blanca a les coordenades (posX, posY).
            GraphicManager::getInstance()->drawSprite(GRAFIC_FITXA_BLANCA, posX, posY);
        }
    }
    else if (getType() == Type::Queen) {
        if (getColor() == Color::Black)
        {
            // Dibuixa un sprite de dama negra a les coordenades (posX, posY).
            GraphicManager::getInstance()->drawSprite(GRAFIC_DAMA_NEGRA, posX, posY);
        }
        else if (getColor() == Color::White)
        {
            // Dibuixa un sprite de dama blanca a les coordenades (posX, posY).
            GraphicManager::getInstance()->drawSprite(GRAFIC_DAMA_BLANCA, posX, posY);
        }
    }
}

Fitxa::Fitxa(char type) {
    switch (type) {
    case 'O':
        m_type = Type::Normal;
        m_color = Color::White;
        break;
    case 'X':
        m_type = Type::Normal;
        m_color = Color::Black;
        break;
    case 'D':
        m_type = Type::Queen;
        m_color = Color::White;
        break;
    case 'R':
        m_type = Type::Queen;
        m_color = Color::Black;
        break;
    default:
        // En lugar de assert, mostramos un mensaje de error y asignamos valores por defecto
        std::cerr << "Error: Tipo de ficha no valido: " << type << std::endl;
        m_type = Type::Empty; // Valor por defecto
        m_color = Color::White; // Valor por defecto (podría ser otro)
        break;
    }
}

char Fitxa::toChar() const {
    if (m_type == Type::Empty) {
        return '_';
    }
    else if (m_type == Type::Normal) {
        if (m_color == Color::White)
            return 'O';
        else if (m_color == Color::Black)
            return 'X';
        else {
            // En lugar de assert, mostramos un mensaje de error y devolvemos un valor por defecto
            std::cerr << "Error: Color no valido para ficha normal" << std::endl;
            return '_'; // Valor por defecto
        }
    }
    else if (m_type == Type::Queen) {
        if (m_color == Color::White)
            return 'D';
        else if (m_color == Color::Black)
            return 'R';
        else {
            // En lugar de assert, mostramos un mensaje de error y devolvemos un valor por defecto
            std::cerr << "Error: Color no valido para dama" << std::endl;
            return '_'; // Valor por defecto
        }
    }
    // En lugar de assert, mostramos un mensaje de error y devolvemos un valor por defecto
    std::cerr << "Error: Tipo de ficha no valido" << std::endl;
    return '_'; // Valor por defecto
}