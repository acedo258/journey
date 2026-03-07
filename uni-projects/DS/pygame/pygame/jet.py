import pygame, random, os
from pygame.locals import RLEACCEL
from screen import Screen
from prototype import Prototype


class Jet(pygame.sprite.Sprite, Prototype):
    def __init__(self):
        super().__init__()
        base_path = os.path.dirname(os.path.abspath(__file__))
        image_path = os.path.join(base_path, "icons", "jet.png")
        self.surf = pygame.image.load(image_path).convert()
        self.surf.set_colorkey((255, 255, 255), RLEACCEL)
        self.rect = self.surf.get_rect(
            center=(random.randint(Screen.width + 20, Screen.width + 100),
                    random.randint(0, Screen.height))
        )
        self.speed = random.randint(12, 14)

    def update(self):
        self.rect.move_ip(-self.speed, 0)
        if self.rect.right < 0:
            self.kill()
