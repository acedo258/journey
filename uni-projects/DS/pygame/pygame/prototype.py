import copy

class Prototype:
    def clone(self):
        return self.__class__()