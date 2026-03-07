import pygame
import random
import os
from pygame.locals import RLEACCEL

from screen import Screen
from prototype import Prototype



# Define the cloud object extending pygame.sprite.Sprite
# Use an image for a better looking sprite
class Cloud(pygame.sprite.Sprite, Prototype):
    def __init__(self):
        super(Cloud, self).__init__()

        # Base folder where this file is located
        base_path = os.path.dirname(os.path.abspath(__file__))

        # Load cloud image
        image_path = os.path.join(base_path, "icons", "cloud.png")
        self.surf = pygame.image.load(image_path).convert()
        self.surf.set_colorkey((0, 0, 0), RLEACCEL)

        # The starting position is randomly generated
        self.rect = self.surf.get_rect(
            center=(
                random.randint(Screen.width + 20, Screen.width + 100),
                random.randint(0, Screen.height),
            )
        )

    # Move the cloud based on a constant speed
    # Remove it when it passes the left edge of the screen
    def update(self):
        self.rect.move_ip(-5, 0)
        if self.rect.right < 0:
            self.kill()