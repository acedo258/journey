import pygame
import os

from pygame.locals import (
    RLEACCEL,
    K_UP,
    K_DOWN,
    K_LEFT,
    K_RIGHT,
)

from screen import Screen


# Define the Player object extending pygame.sprite.Sprite
class Player(pygame.sprite.Sprite):
    def __init__(self):
        super(Player, self).__init__()

        # Base folder where this file is located
        base_path = os.path.dirname(os.path.abspath(__file__))

        # Load helicopter image
        image_path = os.path.join(base_path, "icons", "helicopter.png")
        self.surf = pygame.image.load(image_path).convert()
        self.surf.set_colorkey((255, 255, 255), RLEACCEL)

        # Initial position
        self.rect = self.surf.get_rect(
            center=(Screen.width // 4, Screen.height // 2)
        )

        # Load movement sounds
        self.move_up_sound = pygame.mixer.Sound(
            os.path.join(base_path, "sounds_music", "Rising_putter.ogg")
        )
        self.move_up_sound.set_volume(0.5)

        self.move_down_sound = pygame.mixer.Sound(
            os.path.join(base_path, "sounds_music", "Falling_putter.ogg")
        )
        self.move_down_sound.set_volume(0.5)

    # Move the sprite based on key presses
    def update(self, pressed_keys):
        if pressed_keys[K_UP]:
            self.rect.move_ip(0, -5)
            self.move_up_sound.play()
        if pressed_keys[K_DOWN]:
            self.rect.move_ip(0, 5)
            self.move_down_sound.play()
        if pressed_keys[K_LEFT]:
            self.rect.move_ip(-5, 0)
        if pressed_keys[K_RIGHT]:
            self.rect.move_ip(5, 0)

        # Keep player inside the screen boundaries
        if self.rect.left < 0:
            self.rect.left = 0
        elif self.rect.right > Screen.width:
            self.rect.right = Screen.width
        if self.rect.top <= 0:
            self.rect.top = 0
        elif self.rect.bottom >= Screen.height:
            self.rect.bottom = Screen.height

    # Stop movement sounds
    def stop_move_sounds(self):
        self.move_up_sound.stop()
        self.move_down_sound.stop()
