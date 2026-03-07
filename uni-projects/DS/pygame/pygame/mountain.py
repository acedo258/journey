import pygame, random, os
from pygame.locals import RLEACCEL
from screen import Screen
from prototype import Prototype


class Mountain(pygame.sprite.Sprite, Prototype):
    def __init__(self):
        super().__init__()
        base_path = os.path.dirname(os.path.abspath(__file__))
        image_path = os.path.join(base_path, "icons", "mountain.png")
        self.surf = pygame.image.load(image_path).convert()
        self.surf.set_colorkey((0, 0, 0), RLEACCEL)
        self.rect = self.surf.get_rect(
            bottomleft=(Screen.width + random.randint(0, 50), Screen.height)
            
        )
        self.speed = 2

    def update(self):
        self.rect.move_ip( -self.speed,0)
        if self.rect.right < 0:
            self.kill()
