#pragma once
#include "Fitxa.h"
#include "Posicio.h"
#include <string>
#include <vector>
#include "CuaMoviments.h"

using namespace std;

// Número de filas del tablero.
const int N_FILES = 8;
// Número de columnas del tablero.
const int N_COLUMNES = 8;
// Mida del cuadrado en pixeles en la pantalla.
const int SQUARE_SIZE_PIXELS = 64;

class Tauler {
    Fitxa*** m_tauler;
    
    /// Número màxim de fitxes que una fitxa pot matar en un sol moviment
    int m_maxPossibleKills = 0;
    
    // Posición de la ficha que más puede matar.
    Posicio m_maxPossibleKillsPos{ 0, 0 };
   
    // Si hi ha o no una fitxa seleccionada.
    bool m_hiHaFitxaSeleccionada = false;
    
    // Ficha que hemos clicado.
    Posicio m_fitxaSeleccionada = { 0, 0 };
    
    // Posiciones a las que se puede mover la ficha seleccionada.
    std::vector<Posicio> m_fitxaSeleccionadaPosicionsValides;

public:
    void inicialitza(const string& nomFitxer);
    Tauler(const Tauler& tauler) = delete;
    Tauler& operator=(const Tauler& tauler) = delete;
    Tauler();
    ~Tauler();
    void inicialitza();
    bool haAcabat(Color colorActual);
    // Per cada fitxa, calcula tots els seus possibles moviments i troba la
    // quantitat màxima de fitxes que pot matar.
    void actualitzaMovimentsValids(Color colorActual);
    
    // Para una sola ficha en la posición origen, calcula todos los movimientos
    // posibles. Guarda el resultado en el array pasado por parámetros
    void getPosicionsPossibles(const Posicio& origen, int& nPosicions,
        Posicio posicionsPossibles[]);
    
    // Hace lo mismo que la otra función pero devuelve un vector en vez de
    // rellenar el array de los argumentos.
    std::vector<Posicio> getPosicionsPossiblesVec(const Posicio& origen);
    
    // Devuelve un punter a la ficha en la posición o nullptr (no válida)
    const Fitxa* getFitxa(const Posicio& pos) const;
    void setFitxa(const Posicio& pos, const Fitxa& fitxa) {
        *(m_tauler[pos.getFila()][pos.getColumna()]) = Fitxa(fitxa);
    }

    // Intenta moure una fitxa desde la posició d'origen a la posicio de desti
    // Retorna false si no es possible.
    bool mouFitxa(const Posicio& origen, const Posicio& desti);
    
    // Guarda una imatge del tauler a una string.
    string toString() const;
    
    // Selecciona una fitxa a partir de les posicions del cursor a la pantalla (en
    // píxels).
    bool seleccionaFitxa(int screenx, int screeny, Color colorActual, CuaMoviments& moviments);

    void dibuixa();

    // Calcula los movimientos posibles (mover normal y matar) para una ficha en
    // posición x,y.
    // Usa el algoritmo visto en clase.
    const std::vector<Moviment>& calculateMovements(int x, int y);

private:
    // Intenta mover (sin matar) la ficha en la dirección (dx,dy) con la que está
    // en (x,y), devuelve la nueva posición si tiene éxito. En caso de que el
    // movimiento no sea posible, devuelve un opcional vacío
    // ({}).
    bool tryMove(int x, int y, int dx, int dy, Posicio& result) const;
    
    // Intenta matar la ficha en la dirección (dx,dy) usando la que está en (x,y),
    // devuelve la nueva posición de la ficha si tiene éxito . En caso de que el
    // movimiento no sea posible, devuelve un opcional vacío  ({}).
    bool tryKill(const Moviment& mov, int x, int y, int dx, int dy,
        Color color, int dist, Posicio& destPos, Posicio& victimPos) const;

    // Intenta mover y matar fichas en todas las direcciones y devuelve los
    // movimientos que son posibles.
    std::vector<Step> saltsValids(Posicio actual, Color color, Type tipus, const Moviment& mov) const;

    // Mou i mata amb una reina en (x,y) en una direcció específica. 
    // Guarda els moviments possibles en pos. 
    // La reina s'ha de poder moure totes les caselles que vulgui en totes les direccions 
    // i tambè pot matar a les fitxes que està tocant en diagonal.
    void queenTryMoveAndKill(std::vector<Step>& posicions, int x, int y,
        Color queenColor, int dirX, int dirY,
        const Moviment& mov) const;

    // Intenta que la fitxa en posicio (x,y) i amb color pieceColor es mogui i
    // mati en la direcció dirX, dirY.
    void normalTryMoveAndKill(std::vector<Step>& posicions, int x, int y,
        Color pieceColor, int dirX, int dirY,
        const Moviment& mov) const;
};