#include "tauler.hpp"
#include "Fitxa.h"
#include "Posicio.h"
#include <algorithm>
#include <fstream>
#include <iostream>
#include <queue>
#include <sstream>
#include "GraphicManager.h"
#include "info_joc.hpp"

// Inicialitza a partir d'un fitxer
void Tauler::inicialitza(const string& nomFitxer) {
    std::ifstream file(nomFitxer);
    if (!file.is_open()) {
        std::cerr << "Could not open file: " << nomFitxer << std::endl;
        return;
    }

    char kind;
    string pos;

    while (file >> kind >> pos) {
        Posicio posicio(pos);  // Crear la posición a partir de 'pos'
        m_tauler[posicio.getFila()][posicio.getColumna()] = new Fitxa(kind);
    }

    file.close();  // Cerrar el archivo después de procesarlo
}

Tauler::Tauler() {
    m_tauler = new Fitxa**[N_FILES];
    Fitxa empty{};
    for (int i = 0; i < N_FILES; i++)
    {
        m_tauler[i] = new Fitxa * [N_COLUMNES];
        for (int j = 0; j < N_COLUMNES; j++)
        {
            m_tauler[i][j] = new Fitxa();
        }
    }
}

Tauler::~Tauler() {
    for (int i = 0; i < N_FILES; i++)
    {
        for (int j = 0; j < N_COLUMNES; j++)
        {
            if (m_tauler[i][j])
            {
                delete m_tauler[i][j];
            }
        }
        delete m_tauler[i];
    }
}
// Inicialitza per defecte.
void Tauler::inicialitza() {
    for (int i = N_FILES - 3; i < N_FILES; i++) {
        for (int j = 0; j < N_COLUMNES; j++) {
            int index = i + j;
            bool isBlack = index & 1;
            if (isBlack) {
                setFitxa({ j, i }, { Color::White, Type::Normal });
            }
        }
    }

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < N_COLUMNES; j++) {
            int index = i + j;
            bool isBlack = index & 1;
            if (isBlack) {
                setFitxa({ j, i }, { Color::Black, Type::Normal });
            }
        }
    }
}

bool Tauler::haAcabat(Color colorActual)
{
    bool fitxaTrobada = false;
    for (int i = 0; i < NUM_FILES_TAULER; i++)
    {
        for (int j = 0; j < NUM_COLS_TAULER; j++)
        {
            if (getFitxa({j,i})->getColor() == colorActual)
            {
                fitxaTrobada = true;
            }
        }
    }
    return !fitxaTrobada;
}

// Selecciona una fitxa a partir de les posicions del cursor a la pantalla (en píxels).
// Retorna true si s'ha fet un moviment.
bool Tauler::seleccionaFitxa(int screenx, int screeny, Color colorActual, CuaMoviments& moviments) {
    bool movimentFet = false;
    int x = (screenx - POS_X_TAULER - CASELLA_INICIAL_X) / AMPLADA_CASELLA; //dividim per obtenir l'índex de la columna
    int y = (screeny - POS_Y_TAULER - CASELLA_INICIAL_Y) / ALCADA_CASELLA; //dividim per obtenir l'índex de la fila
    
    const Fitxa* fitxa = getFitxa({ x, y });

    // Això assegura que només es puguin seleccionar fitxes del jugador actual.
    if (fitxa->getType() != Type::Empty && fitxa->getColor() == colorActual) {
        // Si hi ha una fitxa seleccionada trobem si la casella que hem clicat
        // està dins de les posicionsValides (m_fitxaSeleccionada).
        m_hiHaFitxaSeleccionada = true;
        m_fitxaSeleccionada = { x, y };
        m_fitxaSeleccionadaPosicionsValides =
            getPosicionsPossiblesVec(m_fitxaSeleccionada);
    }
    else {
        if (m_hiHaFitxaSeleccionada) {
            // Si hi ha una fitxa seleccionada trobem si la casella que hem clicat
            // està dins de les posicionsValides (m_fitxaSeleccionada).
            bool found = false;
            for (int i = 0; i < m_fitxaSeleccionadaPosicionsValides.size(); i++) {
                if (m_fitxaSeleccionadaPosicionsValides[i] == Posicio(x, y)) {
                    found = true;
                    break;
                }
            }
            // Si hem clicat en una casella marcada en groc (una de les
            // m_fitxaSeleccionadaPosicionsValides).
            if (found) {
                actualitzaMovimentsValids(colorActual);
                if (mouFitxa(m_fitxaSeleccionada, { x, y })) {
                    moviments.afegeix({m_fitxaSeleccionada, {x, y}});
                    movimentFet = true;
                }
            }
        }
        m_hiHaFitxaSeleccionada = false;
    }
    return movimentFet;
}

// Dibuixa tauler.
void Tauler::dibuixa() {
    //Dibuixa el fons de la pantalla a les coordenades (0,0).
    GraphicManager::getInstance()->drawSprite(GRAFIC_FONS, 0, 0);
    //TODO 1.3: Dibuixar a pantalla el gràfic amb el tauler i una fitxa blanca a la posició (fila, columna ) del tauler
    //Dibuixa el tauler del joc a les coordenades especificades.
    GraphicManager::getInstance()->drawSprite(GRAFIC_TAULER, POS_X_TAULER, POS_Y_TAULER);
    int fila = 2;
    int columna = 3;

    // Primer dibuixar el tauler.
    for (int i = 0; i < N_FILES; i++)
    {
        for (int j = 0; j < N_COLUMNES; j++)
        {
            const Fitxa* f = getFitxa({ j,i });
            f->visualitza({j,i});
            
        }
    }

    // Finalment, si hi ha una fitxa seleccionada marquem en groc tots els moviments que pot fer.
    if (m_hiHaFitxaSeleccionada) {
        for (auto& p : m_fitxaSeleccionadaPosicionsValides) {
            int posX = POS_X_TAULER + CASELLA_INICIAL_X + ((p.getColumna()) * AMPLADA_CASELLA);
            int posY = POS_Y_TAULER + CASELLA_INICIAL_Y + ((p.getFila()) * ALCADA_CASELLA);
            
            // Dibuixa un sprite groc a les coordenades calculades.
            GraphicManager::getInstance()->drawSprite(GRAFIC_POSICIO_VALIDA, posX, posY);
        }
    }
}


// Per cada fitxa, calcula tots els seus possibles moviments i troba la
// quantitat màxima de fitxes que una fitxa pot matar.
void Tauler::actualitzaMovimentsValids(Color colorActual) {
    m_maxPossibleKills = 0;
    Color color_seleccionat;

    if (m_hiHaFitxaSeleccionada) {
        color_seleccionat = getFitxa(m_fitxaSeleccionada)->getColor();
    }
    else {
        color_seleccionat = Color::White;
    }

    // Per cada fitxa, si es del color de la fitxa seleccionada actualment,
    // calculem els seus moviments possibles.
    for (int i = 0; i < N_FILES; i++) {
        for (int j = 0; j < N_COLUMNES; j++) {
            // Si la fitxa es del color del jugador actual...
            const Fitxa* fitxa = getFitxa({ j, i });
            if (fitxa->getColor() == color_seleccionat) {
                const auto& movements = calculateMovements(j, i);

                // Per cada moviment calculem el nombre màxim que pot matar
                // i trobem i guardem la fitxa que pot matar mes per "bufar-la"
                // (treure-la) si no aprofitem i fem un moviment pitjor.
                // (com diu a la presentació del projecte).
                for (int k = 0; k < movements.size(); k++) {
                    int kills = movements[k].getNKills();
                    if (m_maxPossibleKills < kills) {
                        m_maxPossibleKills = kills;
                        m_maxPossibleKillsPos = { j, i };
                    }
                }
            }
        }
    }
}

// Intenta moure una fitxa desde la posició d'origen a la posicio de desti
// Retorna false si no es possible.
bool Tauler::mouFitxa(const Posicio& origen, const Posicio& desti) {
    m_hiHaFitxaSeleccionada = true;
    m_fitxaSeleccionada = origen;
    const Fitxa* target = getFitxa(origen);

    if (!target) {
        std::cerr << "Error: Out of bounds or invalid position for origen: " << origen << std::endl;
        return false;  // Devolver false si la posición es inválida
    }

    const auto& movements = calculateMovements(origen.getColumna(), origen.getFila());

    bool found = false;
    int maxima = -100;
    for (int i = 0; i < movements.size(); i++) {
        if (movements[i].contains(desti)) {
            found = true;
            if (maxima == -100 || movements[i].getNKills() > movements[maxima].getNKills())
            {
                maxima = i;
            }
        }
    }

    // Si el destino está dentro de los movimientos posibles...
    if (found) {
        // troba el moviment concret que fa que arribi al destí
        const auto moviment = movements[maxima];
        int nKills = 0;

        // Actualizamos los movimientos de todas las fichas para saber el número máximo
        // de piezas que se pueden matar (por "bufar" una pieza si no aprovechamos el mejor movimiento).
        actualitzaMovimentsValids(target->getColor());

        // Si el movimiento mata...
        if (!moviment.isMove()) {
            const auto& steps = moviment.getPosicions();
            auto iter = steps.begin();
            // Recorremos los pasos del movimiento hasta llegar al destino
            while (iter->dest != desti) {
                // Eliminamos las piezas que matamos
                setFitxa(iter->kill, {});
                iter++;
                nKills += 1;
            }
            setFitxa(iter->kill, {});
            nKills += 1;
        }

        // Creamos la nueva ficha (puede ser una dama si se llega al final)
        Fitxa nueva{ target->getColor(), target->getType() };
        if (desti.getFila() == 0 || desti.getFila() == N_FILES - 1) {
            nueva.ferDama();  // Convertimos a dama si es el último rango
        }
        // Movemos la ficha a la nueva posición
        setFitxa(desti, nueva);

        // Si la ficha movida no es la que puede matar más, la eliminamos ("bufamos")
        if (m_maxPossibleKills != nKills) {
            setFitxa(m_maxPossibleKillsPos, Fitxa());
            if (m_maxPossibleKillsPos == origen) {
                setFitxa(desti, {});
            }
        }

        // Eliminamos la ficha de la posición original
        setFitxa(origen, {});
        return true;
    }
    else {
        return false;
    }
}


// Guarda una imatge del tauler a una string.
string Tauler::toString() const {
    std::stringstream res; //ho guarda com una cadena
    for (int i = N_FILES - 1; i >= 0; i--) {
        res << i + 1 << ":";
        for (int j = 0; j < N_COLUMNES; j++) {
            res << " " << m_tauler[i][j]->toChar();
        }
        res << std::endl;
    }
    res << "   a b c d e f g h" << std::endl;
    return res.str();
}

// Hace lo mismo que la otra función pero devuelve un vector en vez de
// rellenar el array de los argumentos.
std::vector<Posicio> Tauler::getPosicionsPossiblesVec(const Posicio& origen) {
    int i = origen.getFila();
    int j = origen.getColumna();
    const auto& movements = calculateMovements(j, i);
    int N = movements.size();
    // Crea un vector para guardar las posiciones finales posibles,
    // una por cada movimiento.
    std::vector<Posicio> posicionsPossibles;
    // Llena el vector con la posición final de cada movimiento posible
    for (int i = 0; i < N; i++) {
        //si mov mata 0 o 1, agafa el final directament
        if (movements[i].getNKills() <= 1) {
            posicionsPossibles.push_back(movements[i].getFinal());
        }
        //mov mata 2 o + (s'han d'afegir els passos intermitjos
        else {
            const auto& poss = movements[i].getPosicions();
            for (int j = 0; j < poss.size(); j++) {
                posicionsPossibles.push_back(poss[j].dest);
            }
        }
    }
    return posicionsPossibles;
}

// Para una sola ficha en la posición origen, calcula todos los movimientos
// posibles. Guarda el resultado en el array pasado por parámetros
void Tauler::getPosicionsPossibles(const Posicio& origen, int& nPosicions,
    Posicio posicionsPossibles[]) {
    int i = origen.getFila();
    int j = origen.getColumna();
    const auto& movements = calculateMovements(j, i);
    nPosicions = 0;
    // Llena el array con la posición final de cada movimiento posible
    for (int k = 0; k < movements.size(); k++) {
        const auto& mov = movements[k];
        if (mov.getNKills() <= 1) {
            posicionsPossibles[nPosicions] = mov.getFinal();
            nPosicions += 1;
        }
        else {
            const auto& poss = mov.getPosicions();
            for (int j = 0; j < poss.size(); j++) {
                posicionsPossibles[nPosicions] = poss[j].dest;
                nPosicions += 1;
            }
        }
    }
}


// Intenta mover (sin matar) la ficha en la dirección (dx,dy), devuelve la
// nueva posición.
// En caso de que el movimiento no sea posible, devuelve un opcional vacío
// ({}).
bool Tauler::tryMove(int x, int y, int dx, int dy, Posicio& result) const {
    //Comprova si hi ha una fitxa a la posició inicial
    const Fitxa* actual = getFitxa({ x, y });
    if (!actual || actual->getType() == Type::Empty) {
        return false;
    }

    const Fitxa* destination = getFitxa({ x + dx, y + dy });
    if (destination && destination->getType() == Type::Empty) {
        result = Posicio(x + dx, y + dy);
        return true;
    }
    return false;
}


// Intenta matar la ficha en la dirección (dx,dy), devuelve la nueva posición y
// la ficha que se ha matado. En caso de que el movimiento no sea posible,
// devuelve un opcional vacío
// ({}).
bool Tauler::tryKill(const Moviment& mov, int x, int y, int dx, int dy,
    Color color, int dist,
    Posicio& destPos, Posicio& victimPos) const {
    if (mov.isMove()) {
        return false;
    }

    //Comprova si hi ha una fitxa a la posició d’origen
    const Fitxa* actual = getFitxa({ x, y });
    if (!actual) return false;

    victimPos = { x + dist * dx, y + dist * dy };
    const Fitxa* victim = getFitxa(victimPos);

    //Comprova que hi hagi una fitxa i que sigui de l’altre color
    if (victim && victim->getType() != Type::Empty && victim->getColor() != color) {
        //Calcula on aniria la fitxa un cop feta la captura (una posició més enllà de la víctima)
        destPos = { x + (dist + 1) * dx, y + (dist + 1) * dy };
        const Fitxa* destination = getFitxa(destPos);
        if (destination && destination->getType() == Type::Empty) {
            if (!mov.hasKill(victimPos)) {
                return true;
            }
        }
    }
    return false;
}


// Fa que la reina en posicio (x,y) de color queenColor es mogui en totes les
// direccions possibles. Guarda els moviments possibles en pos. La reina s'ha de
// poder moure totes les caselles que vulgui en totes les direccions i tambè pot
// matar a les fitxes que hi ha en diagonal.
void Tauler::queenTryMoveAndKill(std::vector<Step>& posicions, int x, int y,
    Color queenColor, int dirX, int dirY,
    const Moviment& moviment) const {
    int dist = 1;
    Posicio result;  // Añadido el resultado para la función tryMove
    //guarda totes les pos possibles
    while (auto pos = tryMove(x, y, dirX * dist, dirY * dist, result)) {
        posicions.push_back(Step{ true, {}, result });
        dist++;
    }

    dist = 1;
    //Obté la fitxa que hi ha a la primera casella diagonal (una posició davant de la reina)
    const Fitxa* fitxa = getFitxa({ x + dirX * dist, y + dirY * dist });
    while (fitxa && fitxa->getType() == Type::Empty) {
        dist += 1;
        fitxa = getFitxa({ x + dirX * dist, y + dirY * dist });
    }

    if (fitxa) {
        Posicio dst, kill;
        //victima de l'enemic, casella post buida, no ha mort en aquest mov
        if (tryKill(moviment, x, y, dirX, dirY, queenColor, dist, dst, kill)) {
            posicions.push_back(Step{ false, kill, dst });
        }
    }
}


// Intenta moure la fitxa en la posició (x,y) de color pieceColor en la direcció
// (dirX, dirY) i guarda el moviment en posicions si es possible.
void Tauler::normalTryMoveAndKill(std::vector<Step>& posicions, int x, int y,
    Color pieceColor, int dirX, int dirY,
    const Moviment& moviment) const {

    Posicio result;  // Añadido el resultado para la función tryMove
    if (auto pos = tryMove(x, y, dirX, dirY, result)) {
        posicions.push_back(Step{ true, {}, result });
    }
    //si no pot moure's normal prova a capturar una peça
    else {
        Posicio dest, kill;
        if (tryKill(moviment, x, y, dirX, dirY, pieceColor, 1, dest, kill)) {
            posicions.push_back(Step{ false, kill, dest });
        }
    }
}



// Intenta mover y matar fichas en todas las direcciones y devuelve los
// movimientos que son posibles.
std::vector<Step> Tauler::saltsValids(Posicio actual, Color color, Type tipus,
    const Moviment& moviment) const {
    const Fitxa* f = getFitxa(actual);
    if (!f) {
        // Si la ficha no existe devolvemos vacío.
        return {};
    }
    int x = actual.getX();
    int y = actual.getY();

    std::vector<Step> posicions;
    //definim cap on és endavant
    int forward = color == Color::Black ? 1 : -1;

    //prova les 4 diagonals possibles de la reina
    if (tipus == Type::Queen) {
        queenTryMoveAndKill(posicions, x, y, color, 1, forward, moviment); //dreta endavant
        queenTryMoveAndKill(posicions, x, y, color, -1, forward, moviment); //esquerra endavant
        queenTryMoveAndKill(posicions, x, y, color, 1, -forward, moviment); //dreta enrere
        queenTryMoveAndKill(posicions, x, y, color, -1, -forward, moviment); //esquerra enrere
    }
    else {
        // (+1, forward) significa: dreta endevant
        // (-1, forward) significa: esquerra endevant
        normalTryMoveAndKill(posicions, x, y, color, +1, forward, moviment);
        normalTryMoveAndKill(posicions, x, y, color, -1, forward, moviment);
    }
    return posicions;
}

// Calcula los movimientos posibles (mover normal y matar) para una ficha en
// posición x,y.
// Usa el algoritmo visto en clase. Es com un depth first search.
const std::vector<Moviment>& Tauler::calculateMovements(int x, int y) {
    const Fitxa* f = getFitxa({ x, y });
    Color color = f->getColor();
    Type tipus = f->getType();

    //no sé si cal
    std::vector<Posicio> posicions;

    std::vector<Moviment> movimentsValids;
    std::queue<Moviment> movimentsPendents;

    Posicio posicioActual(x, y);
    movimentsPendents.push({ Posicio{x, y}, false });
    
    // Algoritme explicat a classe per cercar tots els moviments vàlids.
    do {
        Moviment movimentActual = movimentsPendents.front();
        movimentsPendents.pop();
        
        posicioActual = movimentActual.getFinal();
        std::vector<Step> posValides =
            saltsValids(posicioActual, color, tipus, movimentActual);
        
        // Mentre no quedin més opcions per explorar i el moviment no sigui
        // un moviment simple (sense matar), perquè si fem un moviment simple
        // no podem seguir matant en el mateix torn.
        while (!posValides.empty() && !movimentActual.isMove()) {
            //evitem modificar el mov originial
            Moviment movimentActualCopy(movimentActual);
            
            // Cerca de la primera posició que no genera un bucle (no està repetida).
            // [&](const Step& step) { ... } serveix per declarar una funció sense nom
            // que es crida per cada element de la cerca.
            auto iter = std::find_if(std::begin(posValides), std::end(posValides),
                [&](const Step& step) {
                    return !movimentActual.contains(step.dest);
                });
            
            // Si existe una posición no repetida...
            if (iter != std::end(posValides)) {
                //afegim l'step al mov actual
                movimentActual.addJump(*iter);
                iter++;
                
                // Añadimos todas las posiciones que no estén repetidas a caminos por explorar.
                //es consideren totes les altres opcions diferents de la primera
                while (iter != posValides.end()) {
                    const Step step = *iter;
                    
                    // Evitem bucles buscant que les posicions no es repeteixin i que no
                    // matem a la mateixa fitxa dos cops.
                    if (!movimentActualCopy.contains(step.dest) && 
                        !movimentActualCopy.isMove() &&            
                        !movimentActualCopy.hasKill(step.kill)     
                        ) 
                    {
                        //Crea una nova branca de moviment i la guarda per explorar més tard
                        Moviment movimentPendent(movimentActualCopy);
                        movimentPendent.addJump(step);
                        movimentsPendents.push(movimentPendent);
                    }
                    iter++;
                }
                posicioActual = posValides.front().dest;
                posValides = saltsValids(posicioActual, color, tipus, movimentActual);
            }
            else {
                // Si no hi ha cap posicio que no generi un bucle
                // buidem les posicions valides.
                posValides = {};
            }
        }
        if (!movimentActual.empty()) {
            movimentsValids.push_back(movimentActual);
        }
    } while (!movimentsPendents.empty());

    m_tauler[y][x]->setMoviments(movimentsValids);
    return m_tauler[y][x]->getMoviments();
}

// Devuelve un apuntador a la ficha en la posición o nullptr si la posición no
// es válida.
const Fitxa* Tauler::getFitxa(const Posicio& pos) const {
    if (pos.getFila() >= N_FILES || pos.getFila() < 0 ||
        pos.getColumna() >= N_COLUMNES || pos.getColumna() < 0) {
        return nullptr;
    }
    return m_tauler[pos.getFila()][pos.getColumna()];
}