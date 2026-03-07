#pragma once
#include "Posicio.h"
#include <vector>
#include <cassert>

struct Step {
  bool isMove; //indica si és desplaçament o kill
  Posicio kill; //posició de la fitxa que es mejna
  Posicio dest; //posició d'on arriba el moviment
};

class Moviment {
  Posicio m_origen;
  std::vector<Step> m_posicions;
  bool m_isMove = true;

public:
  const std::vector<Step>& getPosicions() const { return m_posicions; }

  Moviment(Posicio origen, bool isMove) : m_origen(origen), m_isMove(isMove) {}
  // Afegeix una posició nova al moviment.
  // También dice si es un movimiento normal o estamos matando una ficha.
  void addJump(const Step &step) {
    if (m_isMove) {
      assert(!step.isMove);
	}
    m_isMove = step.isMove;
    m_posicions.push_back(step);
  }
  // Retorna l'última posició del moviment.
  Posicio getFinal() const {
    if (m_posicions.empty())
      return m_origen;
    return m_posicions.back().dest;
  };
  // Retorna la quantitat de salts que fa el moviment.
  int size() const { return m_posicions.size(); }
  // Retorna si el moviment es un moviment simple (no inclou matar)
  bool isMove() const { return m_isMove; };
  // Indica si no hi ha cap pas afegit
  bool empty() const { return m_posicions.empty(); }
  // Retorna el nombre de fitxes que es maten en aquest moviment.
  int getNKills() const;
  // Retorna la posició de totes les fitxes que es maten en aquest moviment.
  std::vector<Posicio> getKills() const;
  // Fa una cerca per saber si la posicio p està dins del moviment.
  bool contains(Posicio p) const;
  bool hasKill(Posicio p) const;
};