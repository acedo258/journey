//
//  CurrentGame.cpp
//  LearnChess
//
//  Created by Enric Vergara on 21/2/22.
//

#include "joc.hpp"
#include <iostream>
#include <fstream>
#include "GraphicManager.h"

using namespace std;

bool Joc::actualitza(int mousePosX, int mousePosY, bool mouseStatus)
{
    bool tornAcabat = false;

    if (m_mode == MODE_JOC_NORMAL)
    {
        if (mouseStatus)
        {
            // Comprova si el clic del ratolí està dins dels límits del tauler, calculant les coordenades amb offsets i dimensions del tauler.
            if ((mousePosX >= (POS_X_TAULER + CASELLA_INICIAL_X)) &&
                (mousePosY >= POS_Y_TAULER + CASELLA_INICIAL_Y) &&
                (mousePosX < (POS_X_TAULER + CASELLA_INICIAL_X + AMPLADA_CASELLA * NUM_COLS_TAULER)) &&
                (mousePosY < (POS_Y_TAULER + CASELLA_INICIAL_Y + ALCADA_CASELLA * NUM_FILES_TAULER)))
            {
                if (m_tauler.seleccionaFitxa(mousePosX, mousePosY, m_colorJugadorActual, m_moviments)) {
                    
                    tornAcabat = true;
                }
            }
        }
    }
    else {
        // En mode replay, només processa un clic si el ratolí no està premut contínuament. 
        // m_mouseContinuallyPressed evita processar múltiples moviments amb un sol clic mantingut.
        if (mouseStatus && !m_mouseContinuallyPressed) {
            
            if (!m_moviments.buida())
            {
                Pas mov = m_moviments.first();
                m_moviments.treu();
                m_tauler.mouFitxa(mov.inicial, mov.final);
                // En mode replay, extreu el següent moviment de la cua i l'aplica al tauler.
            }
        }
        m_mouseContinuallyPressed = mouseStatus;
        // Actualitza l'estat del ratolí per rastrejar si està premut contínuament.
        // És una mica estrany perquè aquesta variable només s'usa en mode replay per evitarprocesar clics repetits.
    }
    m_tauler.dibuixa();

    int posTextX = POS_X_TAULER;
    int posTextY = POS_Y_TAULER + (ALCADA_CASELLA * NUM_FILES_TAULER) + 120;
    string msg = "PosX: " + to_string(mousePosX) + ", PosY: " + to_string(mousePosY);
    
    // Dibuixa la posició del ratolí a la pantalla
    GraphicManager::getInstance()->drawFont(FONT_WHITE_30, posTextX, posTextY, 0.8, msg);

    posTextX = POS_X_TAULER;
    posTextY = POS_Y_TAULER + (ALCADA_CASELLA * NUM_FILES_TAULER) + 150;
    msg = "Jugador actual: ";
    if (m_colorJugadorActual == Color::White)
    {
        msg += "Blanc";
    }
    else {
        msg += "Negre";
    }
    // Dibuixa el text del jugador actual
    GraphicManager::getInstance()->drawFont(FONT_WHITE_30, posTextX, posTextY, 0.8, msg);
    

    posTextX = POS_X_TAULER;
    posTextY = POS_Y_TAULER + (ALCADA_CASELLA * NUM_FILES_TAULER) + 180;
    msg = "Mode: ";
    if (m_mode == MODE_JOC_NORMAL)
    {
        msg += "Normal";
    }
    else {
        if (m_moviments.buida())
        {
            msg = "No queden moviments per reproduir";
        }
        else {
            msg += "Replay";
        }
    }
    // Dibuixa el mode de joc actual, amb un missatge especial si no queden moviments en mode replay.
    GraphicManager::getInstance()->drawFont(FONT_WHITE_30, posTextX, posTextY, 0.8, msg);
    
    //Comprova si el joc ha acabat per al jugador oposat (no l'actual).
    if (m_tauler.haAcabat(canviaColorJugador())) {
        msg = "GUANYADOR: ";
        if (m_colorJugadorActual == Color::White)
        {
            msg += "BLANC";
        }
        else {
            msg += "NEGRE";
        }
        
        // Dibuixa el missatge de guanyador amb una escala més gran (2.0).
        GraphicManager::getInstance()->drawFont(FONT_WHITE_30, 60, 300, 2.0, msg);
        return true;
    }
    else {
        if (tornAcabat)
        {
            m_colorJugadorActual = canviaColorJugador();
        }
        return false;
    }
}

Color Joc::canviaColorJugador() {
    if (m_colorJugadorActual == Color::White) {
        return Color::Black;
    }
    else {
        return Color::White;
    }
}

void Joc::inicialitza(ModeJoc mode, const string& nomFitxerTauler, const string& nomFitxerMoviments) {
    m_tauler.inicialitza(nomFitxerTauler);
    m_mode = mode;
    m_fitxerGuardar = nomFitxerMoviments;
    if (mode == MODE_JOC_REPLAY)
    {
        llegeixMoviments(nomFitxerMoviments);
        // En mode replay, llegeix els moviments del fitxer especificat.
    }
}

void Joc::guardaMoviments(const string& nomFitxerMoviments) {
    ofstream file(nomFitxerMoviments);
    if (file.is_open())
    {
        string posInicial;
        string posFinal;
        while (!m_moviments.buida())
        {
            Pas pas = m_moviments.first();
            m_moviments.treu();
            file << pas.inicial << " " << pas.final << endl;
            //Escriu un moviment al fitxer (dues posicions separades per un espai "a1 b2").
        }
    }
}

void Joc::llegeixMoviments(const string& nomFitxerMoviments) {
    ifstream file(nomFitxerMoviments);
    string posInicial;
    string posFinal;
    while (file >> posInicial >> posFinal) {
        Posicio inicial(posInicial);
        Posicio final(posFinal);
        m_moviments.afegeix({ inicial, final });
        //Afegeix un moviment a la cua com un 'Pas' format per dues posicions (inicial i final).
    }
}

void Joc::finalitza() {
    if (m_mode == MODE_JOC_NORMAL)
    {
        guardaMoviments(m_fitxerGuardar);
        // Guarda els moviments al fitxer només en mode normal.
    }
}