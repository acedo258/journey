#pragma once
#include <ostream>
#include <string>

class Posicio {
public:
  Posicio() {}
  Posicio(int x, int y) : m_x(x), m_y(y) {}
  
  //Constructor que rep una string com "a1", "d4", etc.
  // Converteix la lletra en un número (columna: 'a' ? 0, 'b' ? 1, ...), 
  // (fila: '1' ? 0, '2' ? 1, etc.)
  Posicio(const std::string &posicio) {
    char x_char = posicio[0];
    char y_char = posicio[1];
    m_x = x_char - 'a';
    m_y = y_char - '1';
  }

  bool operator==(const Posicio &posicio) const {
    return posicio.m_x == m_x && posicio.m_y == m_y;
  }
  bool operator!=(const Posicio &posicio) const { return !(*this == posicio); }

  int getX() const { return m_x; }
  void setX(const int &v) { m_x = v; }
  int getY() const { return m_y; }
  void sety(const int &v) { m_y = v; }

  int getColumna() const { return m_x; }
  void setColumna(const int &v) { m_x = v; }
  int getFila() const { return m_y; }
  void setFila(const int &v) { m_y = v; }

  friend std::ostream &operator<<(std::ostream &out, const Posicio &p) {
      char x_char = (char)p.getX() + 'a';
      char y_char = (char)p.getY() + '1';
      out << x_char << y_char;
      return out;
  }

private:
  int m_x = 0;
  int m_y = 0;
};