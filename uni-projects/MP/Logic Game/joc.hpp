#ifndef JOC_H
#define JOC_H

#include <stdio.h>
#include "info_joc.hpp"
#include "Tauler.hpp"

using namespace std;

class Joc 
{
public:
    Joc() {
        m_tauler.inicialitza();
    };
    
    bool actualitza(int mousePosX, int mousePosY, bool mouseStatus);
    void inicialitza(ModeJoc mode, const string& nomFitxerTauler, const string& nomFitxerMoviments);
    void finalitza();

private:
    void guardaMoviments(const string& nomFitxerMoviments);
    void llegeixMoviments(const string& nomFitxerMoviments);

    Color canviaColorJugador();
    Color m_colorJugadorActual = Color::White;
    ModeJoc m_mode = MODE_JOC_NORMAL;
    std::string m_fitxerGuardar = "default.txt";
    CuaMoviments m_moviments;
    Tauler m_tauler;
    bool m_mouseContinuallyPressed = false;
};

#endif 
