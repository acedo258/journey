#include "Moviment.h"
#include <algorithm>

using namespace std;

// Retorna el nombre de fitxes que es maten en aquest moviment.
int Moviment::getNKills() const {
  if (m_isMove) {
    return 0;
  } else {
    return m_posicions.size();
  }
}

// Retorna la posició de totes les fitxes que es maten en aquest moviment.
std::vector<Posicio> Moviment::getKills() const {
  if (isMove()) {
    return {};
  } else {
    vector<Posicio> kills(m_posicions.size());
    for (int i = 0; i < m_posicions.size(); i++) {
      assert(!m_posicions[i].isMove);
      kills[i] = m_posicions[i].kill;
    }
    return kills;
  }
}

// Fa una cerca per saber si la posicio p està dins del moviment.
bool Moviment::contains(Posicio p) const {
  if (p == m_origen) {
    return true;
  } else {
        //find_if busca el primer element del rang, on [&] defineix la condició
      auto pos = std::find_if(std::begin(m_posicions), std::end(m_posicions),
                            [&](const Step &step) { return p == step.dest; });
        //si ha trobat una coincidència, pos serà diferent al final del vector
      return pos != std::end(m_posicions);
  }
}

bool Moviment::hasKill(Posicio p) const {
  auto pos = find_if(begin(m_posicions), end(m_posicions),
                     [&](const Step &step) { return step.kill == p; });
  return pos != end(m_posicions);
}