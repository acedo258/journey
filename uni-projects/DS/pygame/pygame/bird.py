import pygame
import random
import math
import os
from pygame.locals import RLEACCEL

from screen import Screen
from prototype import Prototype



# Define the enemy object extending pygame.sprite.Sprite
# Instead of a surface, we use an image for a better looking sprite
class Bird(pygame.sprite.Sprite, Prototype):
    Max_Speed = 10
    Min_Speed = 5

    def __init__(self):
        super(Bird, self).__init__()

        # Base folder where this file is located
        base_path = os.path.dirname(os.path.abspath(__file__))

        # Load bird image
        image_path = os.path.join(base_path, "icons", "bird.png")
        self.surf = pygame.image.load(image_path).convert()
        self.surf.set_colorkey((255, 255, 255), RLEACCEL)

        # The starting position is randomly generated, as is the speed
        self.rect = self.surf.get_rect(
            center=(
                random.randint(Screen.width + 20, Screen.width + 100),
                random.randint(0, Screen.height),
            )
        )

        self.speed = random.randint(self.Min_Speed, self.Max_Speed)
        self.time = 0

    # Move the bird based on speed
    # Remove it when it passes the left edge of the screen
    def update(self):
        self.time += 1
        speed_x = -self.speed
        speed_y = 0.75 * self.speed * math.cos(
            2 * math.pi * self.time / (0.05 * Screen.width)
        )
        self.rect.move_ip(speed_x, speed_y)

        if self.rect.right < 0:
            self.kill()
