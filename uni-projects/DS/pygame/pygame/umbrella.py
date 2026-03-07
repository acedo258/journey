import pygame, random, os
from pygame.locals import RLEACCEL
from screen import Screen
from prototype import Prototype


class Umbrella(pygame.sprite.Sprite, Prototype):
    def __init__(self):
        super().__init__()

        base_path = os.path.dirname(os.path.abspath(__file__))
        image_path = os.path.join(base_path, "icons", "umbrella.png")
        self.surf = pygame.image.load(image_path).convert_alpha()


        self.surf.set_colorkey((255, 255, 255), RLEACCEL)
        self.rect = self.surf.get_rect(
            center=(random.randint(0, Screen.width), -50)
        )
        self.speed = random.randint(3, 6)

    def update(self):
        # go down
        self.rect.move_ip(0, self.speed)
        
        
