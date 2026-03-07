#pragma once
#include "Moviment.h"
#include "Posicio.h"
#include <optional>
#include <string>
#include <vector>

// enum class es igual que una enumeració normal pero permet accedir amb el nom
// de l'enumeració:
// Color::White
// Type::Empty
enum class Color { Empty, White, Black };
enum class Type { Empty, Normal, Queen };

class Fitxa {
  Color m_color = Color::Empty;
  Type m_type = Type::Empty;
  std::vector<Moviment> m_movimentsValids;

public:
  // dibuixa una fitxa al tauler gràfic
  void visualitza(Posicio p) const;
  Fitxa() {}
  Fitxa(Color color, Type type) : m_color(color), m_type(type) {}
  Fitxa(char type); //constructor a partir d'un caràcter

  void ferDama() { m_type = Type::Queen; }
  Color getColor() const { return m_color; }
  void setColor(const Color &v) { m_color = v; }
  Type getType() const { return m_type; }
  void setType(const Type &v) { m_type = v; }
  char toChar() const; //retorna el caràcter associat

  const std::vector<Moviment> &getMoviments() const {
    return m_movimentsValids;
  }
  void setMoviments(const std::vector<Moviment> &v) { m_movimentsValids = v; }

  // Si la fitxa es negra mover-se cap a endevant significa sumar -1 a la
  // posició de la matriu.
  // Si es blanca significa sumar 1.
  int getForwardDirection() const { return m_color == Color::Black ? 1 : -1; }
};