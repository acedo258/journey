import pygame

class FactorySprites:
    def __init__(self):
        self._registry = {}  # event_type → prototipe + periodo_ms

    def register(self, event_type, prototype, period_ms):
        self._registry[event_type] = (prototype, period_ms)
        pygame.time.set_timer(event_type, period_ms)

    def make(self, event_type):
        if event_type in self._registry:
            prototype, _ = self._registry[event_type]
            return prototype.clone()
        return None

    @property
    def event_types(self):
        return list(self._registry.keys())
